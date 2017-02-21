<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <style>
        body{
            font-family: Arial;
        }
        .logo{
            width:100%;
            text-align: center;
            font-weight: bold;
            font-size: 48px;
            margin-top:50px;
            margin-bottom: 50px;
        }
        nav {
            width:100%;
            text-align: center;
            font-size: 20px;

        }
        .navbutton{
            display: inline;
            text-decoration: none;
            color:black;
            margin:auto 20px;
        }
        #forms {

            margin-top: 3%;
            width: 100%;
            text-align: center;
        }
        .submitbutton {
            font-size: 20px;
            text-decoration: none;
            color:black;
        }
        textarea{
            display: block;
            font-size: 16px;
            margin:25px auto;
            text-align: left;
            width:35%;
            height:20%;
        }
    </style>
</head>
<body>
<div class ="logo">
    ProsteForumInternetowe
</div>
<nav>
    <a class="navbutton" href="/search">Szukaj</a>
    <a class="navbutton" href="/profile?user=<sec:authentication property="principal.username"/>">
        Profil Użytkownika(<sec:authentication property="principal.username"/>)</a>
    <a class="navbutton" href="/logout">Wyloguj</a>
</nav>
<form action="/editPost" method="post" id="forms">
    <input type="hidden" name="topicName" value="${requestScope.topicName}"/>
    <input type="hidden" name="number" value="${requestScope.number}"/>
    <textarea name="text">${requestScope.text}</textarea>
    <a class ="submitbutton" href="#" onclick="document.getElementById('forms').submit();">Zapisz zmiany</a>
</form>
</body>
</html>
