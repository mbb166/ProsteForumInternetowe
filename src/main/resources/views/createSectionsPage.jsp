<%@page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <style>
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
        #forms {
            margin-top: 3%;
            width: 100%;
            text-align: center;
        }
        .inputs{
            display: block;
            font-family: Arial;
            font-size: 26px;
            margin:25px auto;
            text-align: center;
            width:25%;
        }
        .submitbutton {
            font-family: Arial;
            font-size: 20px;
            text-decoration: none;
            color:black;
        }
    </style>
</head>
<body>
<div class ="logo">
    ProsteForumInternetowe
</div>
<nav>
    <a class="navbutton" href="/search">Szukaj</a>
    <a class="navbutton" href="/messages">
        Wiadomości</a>
    <a class="navbutton" href="/logout">Wyloguj(<sec:authentication property="principal.username"/>)</a>
</nav>
<form action="/createSection" method="POST" id="forms">
    <input class="inputs" type="text" placeholder="Nazwa działu" name="title" maxlength="200"/>
    <input type="hidden" value="${requestScope.sectionId}" name="parentSectionName"/>
    <a class ="submitbutton" href="#" onclick="document.getElementById('forms').submit();">Dodaj dział</a>
</form>
</body>
</html>