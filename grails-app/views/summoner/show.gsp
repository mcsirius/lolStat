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
        <g:img dir="images/champions" file="${participant.champion + ".png"}" width="40" height="40"/>
        ${participant.name}<br/>
        ${participant.tier}  ${participant.division}<br/>
    </g:each>
</g:if>

</body>
</html>