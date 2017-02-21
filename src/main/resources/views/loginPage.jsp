<%@page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
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
            margin-bottom: 200px;
        }
        .navbutton{
            display: inline;
            text-decoration: none;
            color:black;
            margin:auto 20px;
        }
        #forms {
            width: 100%;
            text-align: center;
        }
        .inputs{
            display: block;
            font-family: Arial;
            font-size: 26px;
            margin:25px auto;
            text-align: center;
        }
        .submitbutton {
            font-family: Arial;
            font-size: 20px;
            text-decoration: none;
            color:black;
        }
        .error{
            text-align: center;
            font-family: Arial;
            font-size: 26px;
            color:red;
        }
    </style>
</head>
<body>
<div class ="logo">
    ProsteForumInternetowe
</div>
<nav>
    <a class="navbutton" href="/">Strona Główna</a>
    <a class="navbutton" href="/register">Utwórz Konto</a>
</nav>
<c:if test="${param.error != null}">
    <p class = "error">Błędne dane logowania</p>
</c:if>
<c:if test="${param.activateSuccess != null}">
    <p class = "error">Aktywacja powiodla sie, teraz możesz sie zalogować</p>
</c:if>
<c:if test="${param.activateError != null}">
    <p class="error">Link aktywacyjnu jest niepoprawny lub konto zostało już aktywowane</p>
</c:if>
<form id = "forms" action = "/login" method="POST">
    <input name = "username" placeholder="Nazwa Użytkownika" class="inputs" type="text"/>
    <input name = "password" placeholder="Haslo" class = "inputs" type = "password"/>
    <a class = "submitbutton" href="#" onclick="document.getElementById('forms').submit();">Zaloguj</a>
</form>
</body>
</html>