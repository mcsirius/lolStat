<%--
  Created by IntelliJ IDEA.
  User: zzhao
  Date: 10/27/15
  Time: 7:09 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
<g:each in="${participants}" var="participant">
    Champion: ${participant.champion}<br/>
    Player Name:${participant.name}
    ${participant.tier}  ${participant.division}<br/>
</g:each>
</body>
</html>