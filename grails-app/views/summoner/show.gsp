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
            <tr>
                <th>
                    Blue Team
                </th>
                <th>
                    Red Team
                </th>
            </tr>
            <tr>
                <td>
                    <table>
                        <g:each in="${game.blueTeam}" var="participant">
                            <tr>
                                <td>
                                    <div>
                                        <g:img dir="images/champions" file="${participant.champion + ".png"}" width="40" height="40"/>
                                    </div>
                                </td>

                                <td>
                                    <table>
                                        <tr>
                                            <td>
                                                <g:img dir="images/spells" file="${participant.spell1.key + ".png"}" width="20" height="20"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <g:img dir="images/spells" file="${participant.spell2.key + ".png"}" width="20" height="20"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td>
                                    ${participant.name}
                                </td>
                                <g:if test="${participant.tier!=null}">
                                    <td>
                                        <g:if test="${participant.tier.toLowerCase() == "master"}">
                                            <g:img dir="images/tiers" file="master.png" width="40" height="40"/>
                                        </g:if>
                                        <g:else>
                                            <g:img dir="images/tiers" file="${participant.tier.toLowerCase()+"_"+participant.division.toLowerCase()+".png"}" width="40" height="40"/>
                                        </g:else>
                                    </td>
                                    <td>
                                        ${participant.tier}  ${participant.division}<br/>
                                    </td>>
                                </g:if>
                            </tr>
                        </g:each>
                    </table>
                </td>

                <td>
                    <table>
                        <g:each in="${game.redTeam}" var="participant">
                            <tr>
                                <td>
                                    <g:img dir="images/champions" file="${participant.champion + ".png"}" width="40" height="40"/>
                                </td>
                                <td>
                                    <table>
                                        <tr>
                                            <td>
                                                <g:img dir="images/spells" file="${participant.spell1.key + ".png"}" width="20" height="20"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <g:img dir="images/spells" file="${participant.spell2.key + ".png"}" width="20" height="20"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td>
                                    ${participant.name}
                                </td>
                                <g:if test="${participant.tier!=null}">
                                    <td>
                                        <g:if test="${participant.tier.toLowerCase() == "master"}">
                                            <g:img dir="images/tiers" file="master.png" width="40" height="40"/>
                                        </g:if>
                                        <g:else>
                                            <g:img dir="images/tiers" file="${participant.tier.toLowerCase()+"_"+participant.division.toLowerCase()+".png"}" width="40" height="40"/>
                                        </g:else>
                                    </td>
                                    <td>
                                        ${participant.tier}  ${participant.division}<br/>
                                    </td>>
                                </g:if>
                            </tr>
                        </g:each>
                    </table>
                </td>
            </tr>
        </table>

    </div>
</g:if>

</body>
</html>