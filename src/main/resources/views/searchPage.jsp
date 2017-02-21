<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page language="java" pageEncoding="UTF-8" %>
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
            width:35%;
        }
        .submitbutton {
            font-family: Arial;
            font-size: 20px;
            text-decoration: none;
            color:black;
        }
        .topictitle{
            width: 70%;
            font-size: 16px;
            text-align: left;
            font-weight: normal;
        }
        .postcouttitle{
            width:12%;
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
            width:70%;
            font-size: 20px;
            text-align: left;
            font-weight: normal;
        }
        .postcout{
            width:12%;
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
        .noresults{
            text-align: center;
            font-size: 26px;
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
<form action="/search" method="post" id="forms">
    <input class="inputs" type="text" placeholder="Słowa kluczowe" name="tags" value="${requestScope.tags}"/>
    <a class ="submitbutton" href="#" onclick="document.getElementById('forms').submit();">Szukaj</a>
</form>
<c:if test="${requestScope.resultList != null}">
    <c:if test="${fn:length(requestScope.resultList) >0}">
        <table class="sections">
        <tr>
            <th colspan="3" style="text-align:left; font-size:26px; padding-bottom:2%;">
                Wyniki wyszukiwania:
            </th>
        </tr>
    <tr class="row">
        <th class="topictitle">
            Nazwa tematu
        </th>
        <th class="postcouttitle">
            Liczba postów
        </th>
        <th class= "authortitle">
            Autor
        </th>
    </tr>
        <c:forEach items="${requestScope.resultList}" var="topic">
            <tr class = "row">
                <th class = "topic">
                    <a class="link" href="/topic/${topic.title}">${topic.titleWithoutFloors}</a>
                </th>
                <th class="postcout">
                        ${topic.postSize}
                </th>
                <th class="author">
                        ${topic.authorName}
                </th>
                <sec:authorize access="hasAnyRole('ADMIN')">
                    <th>
                        <a class="link" href = "/deleteTopic?title=${topic.title}">X</a>
                    </th>
                </sec:authorize>
            </tr>
        </c:forEach>
    </tr>
        </table>
    </c:if>
    <c:if test="${fn:length(requestScope.resultList) ==0}">
        <p class="noresults">Brak wyników wyszukiwania</p>
    </c:if>
</c:if>
</body>
</html>
