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
        .inputs{
            display: block;
            font-size: 26px;
            margin:25px auto;
            text-align: center;
            width:35%;
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
    <a class="navbutton" href="/messages">
        Wiadomości</a>
    <a class="navbutton" href="/logout">Wyloguj(<sec:authentication property="principal.username"/>)</a>
</nav>
<form action="/sendMessage" method="post" id="forms">
    <input class="inputs" type="text" placeholder="Nazwa odbiorcy" name="userId" value="${requestScope.user}"/>
    <input class="inputs" type="text" placeholder="Temat" name="title">
    <textarea name="text" placeholder="treść"></textarea>
    <a class ="submitbutton" href="#" onclick="document.getElementById('forms').submit();">Wyślij wiadomość</a>
</form>
</body>
</html>
