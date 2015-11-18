package Summoner

import org.apache.jasper.tagplugins.jstl.core.Catch

class SummonerController {

    def summonerService

    def show() {
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


        if (game != null) {
            List <Long> participantIds = game.participants*.id
            List <Summoner> participants = summonerService.getSummonerById(participantIds)
        }

        [summoner:summoner, game:game]
    }
}
