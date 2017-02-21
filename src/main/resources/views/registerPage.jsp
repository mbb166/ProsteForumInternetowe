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
        .captcha {
            display:block;
            margin-bottom: 20px;
        }
        .captchaImage{
            display: inline;
        }
        .captchaLabel{
            display: inline;
            font-family: Arial;
            font-size: 22px;
            margin:auto;
            text-align: center;
        }
        .input{
            display: inline;
            font-family: Arial;
            font-size: 26px;
            text-align: center;
        }
        .p{
            font-family: Arial;
            display: inline;
            font-size: 10px;
            position:absolute;
            margin-left: 10px;
            color:red;
        }
        .info {
            display:block;
        <c:if test="${param.sessionTimeExpired != null || param.success != null}">
            visibility: visible;
        </c:if>
        <c:if test="${param.sessionTimeExpired != null}">
            color: red;
        </c:if>
        <c:if test="${param.success != null}">
            color: green;
        </c:if>
        <c:if test="${param.sessionTimeExpired == null && param.success == null}">
            visibility: hidden;
        </c:if>
            font-family: Arial;
            font-size: 20px;
            text-align: center;
        }
    </style>
</head>
<body>
<div class ="logo">
    ProsteForumInternetowe
</div>
<nav>
    <a class="navbutton" href="/">Strona Główna</a>
    <a class="navbutton" href="/login">Zaloguj</a>
</nav>
<form id = "forms" action = "/register" method="post">
    <c:if test ="${param.success == null}">
    <p class="info">Sesja rejestracji wygasła, wprowadz dane ponownie</p>
    </c:if>
    <c:if test="${param.success != null}">
        <p class="info">Na podany adres został wysłany link aktywacyjny. Dziekujemy za rejestracje</p>
    </c:if>
    <div class = "inputs">
        <input name = "username" placeholder="Nazwa Użytkownika" maxlength="20" class="input" type="text" value="${param.username}"/>
        <c:if test="${param.userExist != null}"><p class="p">Użytkownik o tym nicku już istnieje</p></c:if>
        <c:if test="${param.tooShortUsername!= null}"><p class="p">Nazwa uzytkownika powinna mieć minimalnie 4 litery</p></c:if>
    </div>
    <div class="inputs">
        <input name = "email" placeholder="Adres E-mail" class= "input" type = "email" value = "${param.email}"/>
        <c:if test="${param.badEmail != null}"><p class="p">Nieprawidłowy adres</p></c:if>
        <c:if test="${param.emailExist != null}"><p class ="p">Uzytkownik o podanym adresie już istnieje</p></c:if>
    </div>
    <div class="inputs">
        <input name = "password" placeholder="Haslo" class = "input" type = "password"/>
        <c:if test="${param.tooShortPassword != null}"><p class="p">Hasło powinno mieć minimalnie 6 znaków</p></c:if>
    </div>
    <div class = "captcha">
        <image class ="captchaImage"src="data:image/png;base64,${requestScope.captcha}" width="100" height = "23"/>
        <input name = "captcha" type="text"  placeholder="CAPTCHA" class = "captchaLabel" size="8" maxlength="5"/>
        <c:if test="${param.badCaptcha != null}"><p class="p">Nieprawidłowy kod CAPTCHA</p></c:if>
    </div>
    <a class = "submitbutton" href="#" onclick="document.getElementById('forms').submit();">Rejestruj</a>
</form>
</body>
</html>