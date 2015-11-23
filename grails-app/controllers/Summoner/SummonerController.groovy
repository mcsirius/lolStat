package Summoner

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

        [summoner:summoner, game:game]
    }
}
