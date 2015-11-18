<%--
  Created by IntelliJ IDEA.
  User: zzhao
  Date: 10/14/15
  Time: 12:19 AM
--%>


<html>
<head>
    <title></title>
</head>

<body>
<g:img dir="images/profileIcons" file="${summoner.profileImage}" width="40" height="40"/>
${summoner.name}<br/>
Summoner Level: ${summoner.summonerLevel}<br/>
Summoner RevisionDate: ${summoner.revisionDate}<br/>

<g:if test="${game!=null}">
    <g:each in="${game.participants}" var="participant">
        Champion: ${participant.champion}<br/>
        Player Name:${participant.name}
        ${participant.tier}  ${participant.division}<br/>
    </g:each>
</g:if>

</body>
</html>