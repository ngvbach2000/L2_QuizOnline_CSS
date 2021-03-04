<%-- 
    Document   : readyToTakeQuiz
    Created on : Jan 30, 2021, 9:55:25 AM
    Author     : ngvba
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Quiz Online</title>

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

        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="css/offcanvas.css">

    </head>
    <body class="bg-light">
        <c:set var="role" value="${sessionScope.ROLE}"/>
        <c:if test="${role == 'admin'}">
            <c:redirect url=""/>
        </c:if>
        <c:set var="name" value="${sessionScope.NAME}"/>
        <c:if test="${empty name}">
            <c:redirect url=""/>
        </c:if>
        
        <nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-dark" aria-label="Main navigation">
          <div class="container-fluid">
            <a class="navbar-brand" href="/L2_QuizOnline_CSS/">Quiz Online</a>
            <button class="navbar-toggler p-0 border-0" type="button" data-bs-toggle="offcanvas" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>

            <div class="navbar-collapse offcanvas-collapse" id="navbarsExampleDefault">
              <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item active">
                  <a class="nav-link" aria-current="page" href="/L2_QuizOnline_CSS/">Dashboard</a>
                </li>
                <li class="nav-item">
                    <form action="viewQuizHistory">
                        <input type="submit" value="History" class="nav-link btn btn-outline"/>
                    </form>
                </li>
              </ul>
                <form action="logout" method="POST" class="d-flex">
                    <c:if test="${not empty name}">
                        <font color="red" class="form-control me-2">
                        @${name}
                        </font>
                    </c:if>
                    <input type="submit" value="Logout" class="btn btn-outline-danger"/>
                </form>
            </div>
          </div>
        </nav>

        <c:set var="subject" value="${sessionScope.SUBJECT}"/>
        <c:set var="subjectID" value="${subject.subjectID}"/>
        <c:set var="subjectName" value="${subject.subjectName}"/>

        <main class="container">
            <div class="my-3 p-3 bg-white rounded shadow-sm">
                <h2 class="border-bottom pb-2 mb-0">${subjectName}</h2>
                <label><h6>Subject ID:</h6></label> ${subjectID}<br/>
                <label><h6>Subject name:</h6></label> ${subjectName}<br/>
                <label><h6>Time limit:</h6></label> ${subject.timeDuration}<br/>
                <label><h6>Number of Question:</h6></label> ${subject.numberOfQuestion}<br/>
                <label><h6>Quiz scale:</h6></label> 10<br/>
                <label><h6>Open day:</h6></label> ${subject.beginDate} <br/>
                <label><h6>Close day:</h6></label> ${subject.endDate} <br/>
                <form action="takeQuiz" method="POST">
                    <input type="hidden" name="txtSubjectID" value="${subjectID}" />
                    <input type="submit" value="Attempt Quiz" class="btn btn-outline-primary" />
                </form>
                <c:set var="err" value="${requestScope.ERRORS}" />
                <c:if test="${not empty err.cannotTakeManyQuiz}">
                    <font color="red">
                    ${err.cannotTakeManyQuiz}
                    </font>
                </c:if>
                <c:if test="${not empty err.quizIsClose}">
                    <font color="red">
                    ${err.quizIsClose}
                    </font>
                </c:if>
                <c:if test="${not empty err.quizDontHaveQuestion}">
                    <font color="red">
                    ${err.quizDontHaveQuestion}
                    </font>
                </c:if>
            </div>
            <hr class="featurette-divider">
        </main>

        <footer class="container">
            <p class="float-end"><a href="#">Back to top</a></p>
            <p>&copy; 2022 Quiz Online, Inc.</p>
        </footer>

        <script type="text/javascript" src="js/offcanvas.js"></script>
        <script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>

    </body>
</html>
