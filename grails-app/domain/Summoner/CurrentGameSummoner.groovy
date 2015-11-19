package Summoner

class CurrentGameSummoner {

    Long id
    String name
    String champion
    List runes
    SummonerSpell spell1
    SummonerSpell spell2
    String tier
    String division
    Long teamId

    static constraints = {
    }
}
