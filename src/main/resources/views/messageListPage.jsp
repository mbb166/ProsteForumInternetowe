<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <style>
        body{
            font-family: arial;
        }
        .logo{
            width:100%;
            text-align: center;
            font-family: Arial;
            font-weight: bold;
            font-size: 48px;
            margin-top:50px;
            margin-bottom: 50px;
        }
        nav {
            width:100%;
            text-align: center;
            font-family: Arial;
            font-size: 20px;

        }
        .navbutton{
            display: inline;
            text-decoration: none;
            color:black;
            margin:auto 20px;
        }
        .topictitle{
            width: 60%;
            font-size: 16px;
            text-align: left;
            font-weight: normal;
        }
        .datetitle{
            width:22%;
            font-size: 16px;
            text-align: center;
            font-weight: normal;
        }
        .authortitle{
            font-size: 16px;
            text-align: right;
            font-weight: normal;
        }
        .sections{
            width:50%;
            margin-left:auto;
            margin-right: auto;
            margin-top: 2%;
        }
        .topic{
            width:60%;
            font-size: 20px;
            text-align: left;
            font-weight: normal;
        }
        .date{
            width:22%;
            font-size:20px;
            text-align: center;
            font-weight: normal;
        }
        .author{
            font-size: 20px;
            text-align: right;
            font-weight: normal;
        }
        .row{
            height: 30px;
        }
        .link{
            text-decoration: none;
            color:black;
        }
        .link{
            text-decoration: none;
            color:black;
        }
        .nomessage{
            text-align: center;
            font-size: 26px;
        }
        .sendmessagebutton{
            text-align: right;
            font-size: 24px;
        }
    </style>
</head>
<body>
<div class ="logo">
    ProsteForumInternetowe
</div>
<nav>
    <a class="navbutton" href="/search">Szukaj</a>
    <a class="navbutton" href="/messages">Wiadomości</a>
    <a class="navbutton" href="/logout">Wyloguj(<sec:authentication property="principal.username"/>)</a>
</nav>
<c:if test="${fn:length(requestScope.messageList) > 0}">
<table class="sections">
    <tr>
        <th colspan="3" class="sendmessagebutton">
            <a href="/sendMessage" class="link">Wyślij Wiadomość</a>
        </th>
    </tr>
    <tr class="row">
        <th class="topictitle">
            Temat
        </th>
        <th class="datetitle">
            Data odebrania
        </th>
        <th class= "authortitle">
            Nadawca
        </th>
    </tr>
    <c:forEach items="${requestScope.messageList}" var="message">
    <tr class = "row">
        <th class = "topic">
            <a class ="link" href ="/viewMessage/${message.id}">${message.title}</a>
        </th>
        <th class="date">
            ${message.date}
        </th>
        <th class="author">
            <a class = "link" href="/sendMessage/${message.author}">${message.author}</a>
        </th>
        <th>
            <a class = "link" href="/deleteMessage/${message.id}">X</a>
        </th>
    </tr>
    </c:forEach>
    </c:if>
    <c:if test="${fn:length(requestScope.messageList) ==0}">
        <p class="nomessage">Brak wiadomości</p>
    </c:if>
</table>
</body>
</html>
