<%-- 
    Document   : login
    Created on : Jan 23, 2021, 6:42:08 PM
    Author     : ngvba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <title>Quiz Online</title>
        <link rel="stylesheet" type="text/css" href="css/login.css">

        <style>
          .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            user-select: none;
          }

          @media (min-width: 768px) {
            .bd-placeholder-img-lg {
              font-size: 3.5rem;
            }
          }
        </style>

    </head>
    <body class="text-center">

        <c:set var="role" value="${sessionScope.ROLE}"/>
        <c:if test="${role == 'student'}">
            <c:redirect url="quizPage"/>
        </c:if>
        <c:set var="role" value="${sessionScope.ROLE}"/>
        <c:if test="${role == 'admin'}">
            <c:redirect url="managerPage"/>
        </c:if>
        <c:if test="${empty sessionScope.LISTSUBJECT}">
            <c:redirect url=""/>
        </c:if>
        
        <c:set var="err" value="${requestScope.ERRORS}"/>
        <form action="login" method="POST" class="form-signin">
            <a href="/L2_QuizOnline_CSS/"><h1 class="mb-4" alt="">Quiz Online</h1></a>

            <h2 class="h3 mb-3 fw-normal" >Login</h2>
            <label for="email" class="visually-hidden"> Email:  </label>
            <input type="text" name="txtEmail" value="${param.txtEmail}" class="form-control" placeholder="Email address"/><br/>

            <c:if test="${not empty err.emailFormatErr}">
                <font color ="red">
                ${err.emailFormatErr}<br/>
                </font>
            </c:if>

            <label for="password" class="visually-hidden">Password: </label>
            <input type="password" name="txtPassword" value="" class="form-control" placeholder="Password"/><br/>
            
            <input type="submit" value="Login" class="w-100 btn btn-lg btn-primary"/>
            <c:if test="${not empty err.loginFailed}">

            <font color ="red">
            Login fail!<br/>
            ${err.loginFailed}<br/>
            </font>
            </c:if>

            <a href="registerPage" class="mt-5 mb-3 text-primary">Register</a>
        </form>

        

    </body>
</html>
