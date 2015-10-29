package Summoner
import grails.transaction.Transactional
import groovyx.net.http.ContentType
import groovyx.net.http.Method

@Transactional
class SummonerService {

    def lolHttpClient

    Summoner getSummonerByName(String name) {

        Summoner summoner = new Summoner(name: name)

        lolHttpClient.request(Method.GET, ContentType.JSON) {
            uri.path = "api/lol/na/v1.4/summoner/by-name/" + name
            uri.query = [api_key:"9ce4a1d5-8e7e-445b-8e6d-2e8774f07661"]

            response.success = { resp, summonerInfo ->
                mapToSummoner(summoner, summonerInfo)
            }

            response.failure = { resp, exceptions ->
                errorHandler(exceptions)
            }
        }

        summoner
    }

    List<CurrentGameSummoner> getSummonerById(List<CurrentGameSummoner> summoners) {

        String ids = summoners*.id.join(",")

        lolHttpClient.request(Method.GET, ContentType.JSON) {
            uri.path = "api/lol/na/v1.4/summoner/" + ids
            uri.query = [api_key:"9ce4a1d5-8e7e-445b-8e6d-2e8774f07661"]

            response.success = { resp, summonersInfo ->
                summoners.each { summoner ->
                    summoner.name = summonersInfo.get(summoner.id.toString()).name
                }
            }

            response.failure = { resp, exceptions ->
                errorHandler(exceptions)
            }
        }

        summoners
    }

    def mapToSummoner(Summoner summoner, Map summonerInfo) {
        summoner.id = summonerInfo.get(summoner.name).id
        summoner.profileIconId = summonerInfo.get(summoner.name).profileIconId
        summoner.revisionDate = summonerInfo.get(summoner.name).revisionDate
        summoner.summonerLevel = summonerInfo.get(summoner.name).summonerLevel
    }

    def errorHandler(def exceptions){
        exceptions.message
    }

    CurrentGame getCurrentGameInfo(String summonerName) {

        Long summonerId = getSummonerByName(summonerName).id
        CurrentGame game = new CurrentGame()

        lolHttpClient.request(Method.GET, ContentType.JSON) {
            uri.path = "observer-mode/rest/consumer/getSpectatorGameInfo/NA1/" + summonerId
            uri.query = [api_key:"9ce4a1d5-8e7e-445b-8e6d-2e8774f07661"]

            response.success = { resp, currentGameInfo ->
                game.gameId = currentGameInfo.gameId
                game = mapToCurrentGame(game, currentGameInfo)
            }

            response.failure = { resp, exceptions ->
                errorHandler(exceptions)
            }
        }
        game
    }

    def mapToCurrentGame(CurrentGame game, def currentGameInfo) {
        List participants = currentGameInfo.participants

        List <CurrentGameSummoner> formattedList = new ArrayList<>()

        participants.each{
            participant ->
                String spell1 = SummonerSpells.SPELLS.get(participant.spell1Id)
                String spell2 = SummonerSpells.SPELLS.get(participant.spell12d)
                String champion = getChampionById(participant.championId)

                CurrentGameSummoner summoner = new CurrentGameSummoner(spell1: spell1,
                                                                       spell2:spell2,
                                                                       champion: champion)
                summoner.id = participant.summonerId
                formattedList.add(summoner)
        }

        getSummonerById(formattedList)
        getTiersAndDivisions(formattedList)

        game.participants = formattedList
        game
        print("wtf")
    }

    def fillDetails(CurrentGame game) {

    }

//    String getSpellName(int id) {
//        String spell=""
//        lolHttpClient.request(Method.GET, ContentType.JSON) {
//            uri.path = "api/lol/static-data/na/v1.2/summoner-spell/" + id
//            uri.query = [api_key:"9ce4a1d5-8e7e-445b-8e6d-2e8774f07661"]
//
//            response.success = { resp, spellInfo ->
//                spell = spellInfo.name
//            }
//
//            response.failure = { resp, exceptions ->
//                errorHandler(exceptions)
//            }
//        }
//
//        spell
//    }

    String getChampionById(Long id){
        String champion=""

        lolHttpClient.request(Method.GET, ContentType.JSON) {
            uri.path = "api/lol/static-data/na/v1.2/champion/" + id
            uri.query = [api_key:"9ce4a1d5-8e7e-445b-8e6d-2e8774f07661"]

            response.success = { resp, championInfo ->
                champion = championInfo.name
            }

            response.failure = { resp, exceptions ->
                errorHandler(exceptions)
            }
        }
    }

    def getTiersAndDivisions(List<CurrentGameSummoner> summoners) {

        String ids = summoners*.id.join(",")

        lolHttpClient.request(Method.GET, ContentType.JSON) {
            uri.path = "api/lol/na/v2.5/league/by-summoner/"+ ids +"/entry"
            uri.query = [api_key:"9ce4a1d5-8e7e-445b-8e6d-2e8774f07661"]

            response.success = { resp, leagueInfo ->
                summoners.each { summoner ->
                leagueInfo.get(summoner.id.toString()).each {
                        if (it.queue.equals("RANKED_SOLO_5x5")) {
                            summoner.division = it.entries.get(0).division
                            summoner.tier = it.tier
                        }
                    }
                }
            }

            response.failure = { resp, exceptions ->
                errorHandler(exceptions)
            }
        }
    }
}
