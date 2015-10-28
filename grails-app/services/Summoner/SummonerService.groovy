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

    Summoner getSummonerById(Long id) {
        Summoner summoner = new Summoner(id: id)

        lolHttpClient.request(Method.GET, ContentType.JSON) {
            uri.path = "api/lol/na/v1.4/summoner/" + id
            uri.query = [api_key:"9ce4a1d5-8e7e-445b-8e6d-2e8774f07661"]

            response.success = { resp, summonerInfo ->
                summoner.name = summonerInfo.get(id.toString()).name
            }

            response.failure = { resp, exceptions ->
                errorHandler(exceptions)
            }
        }

        summoner
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

    def getCurrentGameInfo(String summonerName) {

        Long summonerId = getSummonerByName(summonerName).id
        CurrentGame game = new CurrentGame()

        lolHttpClient.request(Method.GET, ContentType.JSON) {
            uri.path = "observer-mode/rest/consumer/getSpectatorGameInfo/NA1/" + summonerId
            uri.query = [api_key:"9ce4a1d5-8e7e-445b-8e6d-2e8774f07661"]

            response.success = { resp, currentGameInfo ->
                game.gameId = currentGameInfo.gameId
                mapToCurrentGame(game, currentGameInfo)
            }

            response.failure = { resp, exceptions ->
                errorHandler(exceptions)
            }
        }

        fillDetails(game)

        game

    }

    def mapToCurrentGame(CurrentGame game, def currentGameInfo) {
        List participants = currentGameInfo.participants

        List <CurrentGameSummoner> formattedList = new ArrayList<>()

        participants.each{
            participant ->
                String spell1 = SummonerSpells.SPELLS.get(participant.spell1Id)
                String spell2 = SummonerSpells.SPELLS.get(participant.spell12d)
                //cacheable
                String name = getSummonerById(participant.summonerId).name
                //enum this or cache it
                String champion = getChampionById(participant.championId)

                Thread.sleep(2000)
                Map tierAndDivision = getSummonerTierAndDivision(participant.summonerId, name)
                CurrentGameSummoner summoner = new CurrentGameSummoner(id: participant.summonerId,
                                                                       name: name,
                                                                       spell1: spell1,
                                                                       spell2:spell2,
                                                                       champion: champion,
                                                                       tier: tierAndDivision.get("tier"),
                                                                       division: tierAndDivision.get("division"))

                formattedList.add(summoner)
        }
        game.participants = formattedList
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

    Map getSummonerTierAndDivision(Long id, String summonerName) {
        def tierAndDivision = [:]
        lolHttpClient.request(Method.GET, ContentType.JSON) {
            uri.path = "api/lol/na/v2.5/league/by-summoner/"+ id +"/entry"
            uri.query = [api_key:"9ce4a1d5-8e7e-445b-8e6d-2e8774f07661"]

            response.success = { resp, leagueInfo ->
                leagueInfo.get(id.toString()).each{
                    if(it.entries.get(0).playerOrTeamName.equals(summonerName.trim()
                            .replaceAll("\\s","").toLowerCase())) {
                        String tier = it.tier
                        String division = it.entries.division
                        tierAndDivision.put("tier", tier)
                        tierAndDivision.put("division", division)
                    }

                }
            }

            response.failure = { resp, exceptions ->
                errorHandler(exceptions)
            }
        }

        tierAndDivision
    }


}
