<%@page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
        .table{
            width: 55%;
            margin: auto;
            margin-top: 2%;
        }
        .username{
            width: 20%;
            text-align: left;
        }
        .number{
            width:5%;
            text-align: center;
        }
        .date{
            text-align: right;
        }
        .text{
            text-align: left;
        }
        .options{
            text-align: right;
        }
        .textform{
            display: block;
            width: 35%;
            height: 20%;
            margin:auto;
            margin-top:7%;
            font-size:16px;
            text-align: left;
        }
        .submitbutton {
            font-size: 20px;
            text-decoration: none;
            color:black;
            text-align: center;
            margin-top: 4%;
        }
        #forms{
            text-align:center;
        }
        .postinfo{
            font-size: 20px;
            font-weight: normal;
        }
        .text{
            font-size: 16px;
            font-weight: normal;
            padding-top: 1%;
        }
        .delete{
            margin-right: 1%;
            color:black;
            text-decoration: none;
        }
        .options{
            font-size: 16px;
        }
        .edit {
            color:black;
            text-decoration: none;
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
        <a class="navbutton" href="/logout">Wyloguj(<sec:authentication property="principal.username"/>)</a>
    </sec:authorize>
    <sec:authorize access="isAnonymous()">
        <a class="navbutton" href = "/register">Utwórz Konto</a>
        <a class = "navbutton" href="/login">Zaloguj</a>
    </sec:authorize>
</nav>
<c:forEach items="${requestScope.postList}" var = "post">
    <table class="table">
        <tr class="postinfo">
        <th class="username">
            ${post.authorName}
        </th>
        <th class="number">
            #${post.number}
        </th>
        <th class="date">
            ${post.date}
        </th>
        </tr>
        <tr>
            <th colspan="3" class="text">
            ${post.text}
            </th>
        </tr>
        <tr>
            <th class = "options" colspan="3">
                <sec:authorize access="hasAnyRole('ADMIN')">
                <a class = "delete" href="/deletePost?topicName=${requestScope.topic}&number=${post.number}">Usuń</a>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ADMIN')">
                <a class = "edit" href="/editPost?topicName=${requestScope.topic}&number=${post.number}">Edytuj</a>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('USER')">
                    <c:if test="${requestScope.loggedUserName == post.authorName}">
                    <a class = "edit" href="/editPost?topicName=${requestScope.topic}&number=${post.number}">Edytuj</a>
                    </c:if>
                </sec:authorize>
            </th>
        </tr>
    </table>
</c:forEach>
<sec:authorize access="isAuthenticated()">
<form action="/topic" method="post" id="forms">
    <textarea class ="textform" name ="text"></textarea>
    <input type="hidden" value="${requestScope.topic}" name="topicName"/>
    <a class ="submitbutton" href="#" onclick="document.getElementById('forms').submit();">Dodaj post</a>
</form>
</sec:authorize>
</body>
</html>