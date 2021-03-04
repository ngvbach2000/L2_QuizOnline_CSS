<%-- 
    Document   : reviewQuiz
    Created on : Jan 30, 2021, 7:29:16 PM
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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <script type="text/javascript" src="js/bootstrap.js"></script>

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

        <main class="container">
            <div class="my-3 p-3 bg-white rounded shadow-sm">
                <h2 class="border-bottom pb-2 mb-0 text-center">Review Quiz</h2>
                <c:set var="quiz" value="${requestScope.QUIZ}"/>
                <c:if test="${not empty quiz}">
                    <label><h6>Subject:</h6></label> ${quiz.subjectID}<br/>
                    <label><h6>Time Duration:</h6></label> ${quiz.timeDuration}<br/>
                    <label><h6>Total point:</h6></label> ${quiz.totalPoint}/10<br/>
                    <label><h6>Start date:</h6></label> ${quiz.startDate}<br/>
                    <label><h6>Finish date:</h6></label> ${quiz.finishDate}<br/>
                    <hr class="dropdown-divider">
                    <c:set var="listQuiz" value="${requestScope.LISTQUIZ}"/>
                    <c:if test="${not empty listQuiz}">
                        <c:forEach var="question" items="${listQuiz}" varStatus="counter">
                            <table border="1" class="table">
                                <thead>
                                    <tr>
                                        <th class="w-25">Question: ${counter.count}</th>
                                        <td>
                                            ${question.key.quizQuestionContent}
                                        </td>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                            <c:choose>
                                                <c:when test="${question.key.quizCorrectAnswer eq question.key.selectionAnswer}">
                                                    <font color="green">
                                                    Correct
                                                    </font>
                                                </c:when>
                                                <c:otherwise>
                                                    <font color="red">
                                                    Incorrect
                                                    </font>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:forEach var="answer" items="${question.value}" varStatus="counter">
                                                <input type="radio" name="${question.key.quizQuestionID}" value="${answer.quizAnswerContent}" disabled="disabled"
                                                       <c:if test="${question.key.selectionAnswer eq answer.quizAnswerContent}">
                                                           checked="checked"
                                                       </c:if>
                                                       />
                                                ${answer.quizAnswerContent}
                                                <c:if test="${question.key.quizCorrectAnswer eq answer.quizAnswerContent}">
                                                    <font color="green">
                                                    Correct Answer <i class="fa fa-check"></i>
                                                    </font>
                                                </c:if>
                                                <br/>
                                            </c:forEach>
                                        </td>
                                </tbody>
                            </table>
                        </c:forEach>
                    </c:if>
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
