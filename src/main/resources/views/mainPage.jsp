<%@page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>ProsteForumInternetowe</title>
    <style>
        body{
            font-family: Arial;
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
        .sections{
            width:50%;
            margin-left:auto;
            margin-right: auto;
            margin-top: 2%;
        }
        .sectionstitle{
            width:70%;
            text-align: left;
            font-size: 16px;
            font-weight:normal;
        }
        .topiccouttitle{
            font-size: 16px;
            text-align: right;
            font-weight:normal;
        }
        .section{
            font-size: 20px;
            width:70%;
            text-align: left;
            font-weight:normal;
        }
        .row{
            height: 30px;
        }
        .topiccout{
            font-size: 20px;
            text-align: right;
            font-weight:normal;
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
        .addbutton{
            font-size: 20px;
            text-align: right;
            margin-bottom: 4%;
        }
        .link{
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
    <sec:authorize access="isAuthenticated()">
    <a class="navbutton" href="/search">Szukaj</a>
    <a class="navbutton" href="/messages">
        Wiadomości</a>
        <a class="navbutton" href="/logout">Wyloguj (<sec:authentication property="principal.username"/>)</a>
    </sec:authorize>
    <sec:authorize access="isAnonymous()">
    <a class="navbutton" href = "/register">Utwórz Konto</a>
    <a class = "navbutton" href="/login">Zaloguj</a>
    </sec:authorize>
</nav>
<table class="sections">
    <sec:authorize access="hasAnyRole('ADMIN')">
        <tr class="row">
            <th colspan="3" class="addbutton">
                <a class="link" href="/createSection/${requestScope.sectionId}">Dodaj dział</a>
            </th>
        </tr>
    </sec:authorize>
    <c:if test="${fn:length(requestScope.sectionList) != 0}">
    <tr class="row">
        <th class = "sectionstitle">
            Dział
        </th>
        <th class = "topiccouttitle">
            Liczba Tematów
        </th>
    </tr>
    <c:forEach items="${requestScope.sectionList}" var ="sect">
    <tr class="row">
        <th class = "section">
            <a class="link" href="/${sect.name}">${sect.nameWithoutFloors}</a>
        </th>
        <th class = "topiccout">
            ${sect.topicsSize}
        </th>
        <sec:authorize access="hasAnyRole('ADMIN')">
        <th>
            <a class="link" href="/deleteSection/${sect.name}">X</a>
        </th>
        </sec:authorize>
    </tr>
    </c:forEach>
    </c:if>
</table>
<table class ="sections">
        <c:if test="${requestScope.isMainPage == false}">
        <sec:authorize access="isAuthenticated()">
        <tr class="row">
            <th colspan="3" class="addbutton">
                <a class="link" href="/createTopic/${requestScope.sectionId}">Dodaj temat</a>
            </th>
        </tr>
        </sec:authorize>
        </c:if>
        <c:if test="${fn:length(requestScope.topicList)!=0}">
        <tr class = "row">
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
        <c:forEach items="${requestScope.topicList}" var="topic">
        <tr class = "row">
            <th class = "topic">
                <a class="link" href="/topic/${topic.title}">${topic.titleWithoutFloors}</a>
            </th>
            <th class="postcout">
                ${topic.postSize}
            </th>
            <th class="author">
                <a class="link" href ="/sendMessage/${topic.authorName}">${topic.authorName}</a>
            </th>
            <sec:authorize access="hasAnyRole('ADMIN')">
            <th>
                <a class="link" href = "/deleteTopic/${topic.title}">X</a>
            </th>
            </sec:authorize>
        </tr>
        </c:forEach>
        </c:if>
    </table>

</body>
</html>