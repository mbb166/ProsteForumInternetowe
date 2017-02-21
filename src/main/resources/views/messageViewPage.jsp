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
        .title{
            width: 45%;
            text-align: left;
        }
        .author{
            width:20%;
            text-align: left;
        }
        .date{
            text-align: right;
        }
        .text{
            font-size:24px;
            text-align: left;
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

<table class="table">
    <tr class="postinfo">
        <th class="title">
                Temat: ${requestScope.title}
        </th>
        <th class="author">
            Nadawca: ${requestScope.authorName}
        </th>
        <th class="date">
                Data: ${requestScope.date}
        </th>
    </tr>
    <tr>
        <th colspan="3" class="text">
                ${requestScope.text}
        </th>
    </tr>
</table>
</body>
</html>