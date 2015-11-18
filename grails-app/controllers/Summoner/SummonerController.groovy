package Summoner

import org.apache.jasper.tagplugins.jstl.core.Catch

class SummonerController {

    def summonerService

    def getSummonerInfo() {
        String name = params.summonerName
        CurrentGame game
        Summoner summoner

        String formattedName = name.trim().replaceAll("\\s","").toLowerCase()

        summoner = summonerService.getSummonerByName(formattedName, params.region)

        try {
            game = summonerService.getCurrentGameInfo(summoner.id)
        } catch (Exception e) {
            game = null
        }

        render(view: "SummonerStat", model: [summoner:summoner, game:game])
    }

    def getCurrentGameInfo() {
        String name = params.currentSummonerName

        String formattedName = name.trim().replaceAll("\\s","").toLowerCase()

        CurrentGame game = summonerService.getCurrentGameInfo(formattedName)

        render(view:"CurrentGameInfo", model:[participants:game.participants])
    }
}
