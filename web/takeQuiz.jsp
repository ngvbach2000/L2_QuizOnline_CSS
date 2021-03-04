<%-- 
    Document   : takeQuiz
    Created on : Jan 22, 2021, 6:31:04 PM
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

        <c:set var="subject" value="${sessionScope.SUBJECT}"/>
        <c:set var="subjectID" value="${subject.subjectID}"/>
        <c:set var="subjectName" value="${subject.subjectName}"/>

        <main class="container">
            <div class="my-3 p-3 bg-white rounded shadow-sm">

                <c:set var="listQuiz" value="${sessionScope.LISTQUIZ}"/>
                <c:if test="${not empty listQuiz}">

                <c:set var="noOfPage" value="${requestScope.NOOFPAGE}"/>
                <c:set var="currentPage" value="${requestScope.CURRENTPAGE}"/>
                <c:set var="begin" value="${requestScope.BEGIN}"/>
                <c:set var="end" value="${requestScope.END}"/>

                <h2 class="border-bottom pb-2 mb-0 text-center">${subjectName}</h2>
                <div class="row">
                    <div class="col-4 text-center">
                        <table border="1" cellspacing="1" cellpadding="1" class="table-bordered">
                            <thead>
                                <tr>
                                    <th colspan="2" class="text-center">Quiz</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>${subjectID}-${subjectName}</td>
                                    <td>
                                        <label class="text-center mx-auto">Time left:</label><div id="timer"></div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <script>
                        var time = ${requestScope.TIMEDURATION};

                        var hoursStart = Math.floor((time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                        var minutesStart = Math.floor((time % (1000 * 60 * 60)) / (1000 * 60));
                        var secondsStart = Math.floor((time % (1000 * 60)) / 1000);
                        document.getElementById("timer").innerHTML = hoursStart + "h "
                                + minutesStart + "m " + secondsStart + "s ";

                        var x = setInterval(function () {
                            var distance = time;
                            var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                            var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
                            var seconds = Math.floor((distance % (1000 * 60)) / 1000);
                            document.getElementById("timer").innerHTML = hours + "h "
                                    + minutes + "m " + seconds + "s ";
                            time = time - 1000;
                            if (distance < 0) {
                                clearInterval(x);
                                document.getElementById("timer").innerHTML = "TIME OUT";
                            }
                        }, 1000);
                    </script>

                    <div class="col-8">
                        <form action="submitQuiz" method="POST" id="submitQuiz" name="submitQuiz">
                            <c:forEach var="question" items="${listQuiz}" varStatus="counter" begin="${begin}" end="${end}" step="1">
                                <table border="1" class="table" >
                                    <thead>
                                        <tr>
                                            <th class="w-25">Question: ${counter.count}</th>
                                            <td>
                                                <input type="hidden" name="txtQuestionID${counter.count}" value="${question.key.quizQuestionID}" />
                                                ${question.key.quizQuestionContent}
                                            </td>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td></td>
                                            <td>
                                                <c:forEach var="answer" items="${question.value}" varStatus="counter">
                                                    <input type="radio" name="${question.key.quizQuestionID}" value="${answer.quizAnswerContent}"
                                                           <c:if test="${question.key.selectionAnswer eq answer.quizAnswerContent}">
                                                               checked="checked"
                                                           </c:if>
                                                           />
                                                    ${answer.quizAnswerContent}<br/>
                                                </c:forEach>
                                            </td>
                                    </tbody>
                                </table>
                            </c:forEach>
                            <input type="hidden" name="txtSubjectID" value="subjectID" />

                            <input type="submit" value="Submit and Finish" name="btAction" class="btn btn-success" /><br/>
                            <script type="text/javascript">
                                window.onload = function () {
                                    window.setTimeout(function () {
                                        document.submitQuiz.submit();
                                    }, ${requestScope.TIMEDURATION});
                                }
                                ;
                            </script>

                            <input type="hidden" name="page" value="${currentPage}" />
                            <c:if test="${currentPage != 1}">
                                <input type="submit" value="Previous" name="btAction" class="btn btn-outline-info" /><br/>
                            </c:if>
                            <c:if test="${currentPage < noOfPage}">
                                <input type="submit" value="Next" name="btAction" class="btn btn-outline-info" />
                            </c:if>

                            <table border="0">
                                <tr>
                                    <c:forEach begin="1" end="${noOfPage}" var="i">
                                        <c:choose>
                                            <c:when test="${currentPage == i}">
                                                <td><font color="red">${i}</font></td>
                                                </c:when>
                                                <c:otherwise>
                                                <td>${i}</td>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </tr>
                            </table>
                        </c:if>
                        </form>
                    </div>
                </div>
                
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
