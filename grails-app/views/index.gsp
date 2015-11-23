<%@ page import="StatData.Regions" %>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>LoLSTAT</title>
    <r:require modules="core"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'summoner.css')}" type="text/css">
    <r:layoutResources />
</head>
<body>
    <div class="searchBar">
        <g:form name="summonerBasicInfo" controller="summoner">
            <g:select class="region" name="region" id="region" from="${Regions}"/>
            <g:textField id="summonerName" name="summonerName"/>
            <g:actionSubmit value="submit" class="btn btn-primary" action="show"/>
        </g:form>
    </div>
<r:layoutResources />
</body>
</html>
