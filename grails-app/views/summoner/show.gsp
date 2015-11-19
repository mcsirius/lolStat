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
    <div>
        <table>
            <g:each in="${game.blueTeam}" var="participant">
                <tr>
                    <td>
                        <g:img dir="images/champions" file="${participant.champion + ".png"}" width="40" height="40"/>
                    </td>
                    <td>
                        ${participant.name}
                    </td>

                        <g:if test="${participant.tier!=null}">
                            <td>
                            <g:img dir="images/tiers" file="${participant.tier.toLowerCase()+"_"+participant.division.toLowerCase()+".png"}" width="40" height="40"/>
                            </td>
                            <td>
                            ${participant.tier}  ${participant.division}<br/>
                            </td>>
                        </g:if>
                </tr>
            </g:each>
        </table>

        <table>
            <g:each in="${game.redTeam}" var="participant">
                <tr>
                    <td>
                        <g:img dir="images/champions" file="${participant.champion + ".png"}" width="40" height="40"/>
                    </td>
                    <td>
                        ${participant.name}
                    </td>

                    <g:if test="${participant.tier!=null}">
                        <td>
                            <g:img dir="images/tiers" file="${participant.tier.toLowerCase()+"_"+participant.division.toLowerCase()+".png"}" width="40" height="40"/>
                        </td>
                        <td>
                            ${participant.tier}  ${participant.division}<br/>
                        </td>>
                    </g:if>
                </tr>
            </g:each>
        </table>
    </div>
</g:if>

</body>
</html>