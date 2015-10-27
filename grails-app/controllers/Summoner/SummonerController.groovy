package Summoner

class SummonerController {

    def summonerService

    def getSummonerInfo(String name) {
        Summoner summoner = summonerService.getSummonerInfoRequest(name)

        render(view: "SummonerStat", model: [summoner:summoner])
    }
}
