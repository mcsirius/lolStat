package Summoner
import grails.transaction.Transactional
import groovyx.net.http.ContentType
import groovyx.net.http.Method

@Transactional
class SummonerService {

    def lolHttpClient

    def getSummonerInfoRequest(String name) {

        Summoner summoner = new Summoner()
        summoner.name="themanulove"

        lolHttpClient.request(Method.GET, ContentType.JSON) {
            uri.path = "na/v1.4/summoner/by-name/TheManULove"
            uri.query = [api_key:"9ce4a1d5-8e7e-445b-8e6d-2e8774f07661"]

            response.success = { resp, summonerInfo ->
                mapToSummoner(summoner, summonerInfo)
            }

            response.failure = { resp, exceptions ->
                errorHandler(summoner, exceptions)
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

    def errorHandler(Summoner summoner, def exceptions){
        summoner.name = exceptions.message
    }
}
