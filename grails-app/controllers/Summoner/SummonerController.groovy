package Summoner

class SummonerController {

    def summonerService

    def getSummonerInfo() {
        String name = params.summonerName

        String formattedName = name.trim().replaceAll("\\s","").toLowerCase()

        Summoner summoner = summonerService.getSummonerByName(formattedName)

        render(view: "SummonerStat", model: [summoner:summoner])
    }

    def getCurrentGameInfo() {
        String name = params.currentSummonerName

        String formattedName = name.trim().replaceAll("\\s","").toLowerCase()

        CurrentGame game = summonerService.getCurrentGameInfo(formattedName)

        render(view:"CurrentGameInfo", model:[participants:game.participants])
    }
}
