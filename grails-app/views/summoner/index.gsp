<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>LoLSTAT</title>
    <r:require modules="core"/>
    <r:layoutResources />
</head>
<body>
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
    <g:actionSubmit value="submit" class="btn btn-large btn-primary" action="getCurrentGameInfo"/>
</g:form>
<r:layoutResources />
</body>
</html>
