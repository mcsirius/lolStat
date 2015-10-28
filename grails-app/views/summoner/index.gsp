<!DOCTYPE html>
<html>
<g:form name="summonerBasicInfo" controller="summoner">
        <label>Summoner Name: </label>
        <g:textField id="summonerName" name="summonerName"/>
        <g:actionSubmit value="submit" class="btn btn-large" action="getSummonerInfo"/>
</g:form>
<g:form name="currentGame" controller="summoner">
    <label>Current Game: </label>
    <br/>
    <label>Summoner Name: </label>
    <g:textField id="currentSummonerName" name="currentSummonerName"/>
    <g:actionSubmit value="submit" class="btn btn-large" action="getCurrentGameInfo"/>
</g:form>

</html>
