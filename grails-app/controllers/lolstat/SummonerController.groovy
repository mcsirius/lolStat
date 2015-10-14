package lolstat

class SummonerController {

    def summonerService

    def getSummonerInfo(String name) {
        summonerService.getSummonerInfoRequest(name)
    }
}
