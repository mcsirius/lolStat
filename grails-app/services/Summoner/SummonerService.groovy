package Summoner
import grails.transaction.Transactional
import groovyx.net.http.ContentType
import groovyx.net.http.Method

@Transactional
class SummonerService {

    def lolHttpClient

    Summoner getSummonerByName(String name, String region) {

        Summoner summoner = new Summoner(name: name)

        lolHttpClient.request(Method.GET, ContentType.JSON) {
            uri.path = "api/lol/"+ region +"/v1.4/summoner/by-name/" + name
            uri.query = [api_key:"9ce4a1d5-8e7e-445b-8e6d-2e8774f07661"]

            response.success = { resp, summonerInfo ->
                summonerInfo.each {
                    mapToSummoner(summoner, it)
                }

            }

            response.failure = { resp, exceptions ->
                errorHandler(exceptions)
            }
        }

        summoner
    }

    List<Summoner> getSummonersByIds(List<Long> ids) {

        String idString = ids.join(",")

        List <Summoner> summoners = []
        lolHttpClient.request(Method.GET, ContentType.JSON) {
            uri.path = "api/lol/na/v1.4/summoner/" + idString
            uri.query = [api_key:"9ce4a1d5-8e7e-445b-8e6d-2e8774f07661"]

            response.success = { resp, summonersInfo ->
                summonersInfo.each {
                    Summoner curr = new Summoner()
                    mapToSummoner(curr, it)
                    summoners.add(curr)
                }
            }
            response.failure = { resp, exceptions ->
                errorHandler(exceptions)
            }
        }
        summoners
    }

    def mapToSummoner(Summoner summoner, Map.Entry entry) {
        summoner.id = entry.value.id
        summoner.profileImage = entry.value.profileIconId + ".png"
        summoner.revisionDate = new Date(entry.value.revisionDate)
        summoner.summonerLevel = entry.value.summonerLevel
        summoner.name = entry.value.name
    }

    def errorHandler(def exceptions){
        exceptions.message
    }

    CurrentGame getCurrentGameInfo(long summonerId) {
        CurrentGame game = new CurrentGame()

        lolHttpClient.request(Method.GET, ContentType.JSON) {
            uri.path = "observer-mode/rest/consumer/getSpectatorGameInfo/NA1/" + summonerId
            uri.query = [api_key:"9ce4a1d5-8e7e-445b-8e6d-2e8774f07661"]

            response.success = { resp, currentGameInfo ->
                game.gameId = currentGameInfo.gameId
                game = mapToCurrentGame(game, currentGameInfo)
            }

            response.failure = { resp, exceptions ->
                game.errors = exceptions.message
            }
        }
        game
    }

    def mapToCurrentGame(CurrentGame game, def currentGameInfo) {
        List participants = currentGameInfo.participants

        Map <Long, CurrentGameSummoner> formattedList = new HashMap<>()

        participants.each{
            participant ->
                String spell1 = SummonerSpells.SPELLS.get(participant.spell1Id)
                String spell2 = SummonerSpells.SPELLS.get(participant.spell12d)
                String champion = getChampionById(participant.championId)

                CurrentGameSummoner summoner = new CurrentGameSummoner(spell1: spell1,
                                                                       spell2:spell2,
                                                                       champion: champion,
                                                                       teamId: participant.teamId)
                summoner.id = participant.summonerId
                formattedList.put(summoner.id, summoner)
        }

        List <Summoner> players = getSummonersByIds(formattedList*.getValue()*.id)

        players.each {
            formattedList.get(it.id).name = it.name
            formattedList.put(it.id, formattedList.get(it.id))
        }

        getTiersAndDivisions(formattedList*.getValue())

        List <CurrentGameSummoner> blueTeam = []
        List <CurrentGameSummoner> redTeam = []
        formattedList*.getValue().each {
            if (it.teamId == 100L) {
                blueTeam.add(it)
            } else {
                redTeam.add(it)
            }
        }
        game.blueTeam = blueTeam
        game.redTeam = redTeam

        game
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

        champion.trim().replaceAll("\\s","").replaceAll("\\.","").replaceAll("'","")
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
