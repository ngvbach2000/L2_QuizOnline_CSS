<%-- 
    Document   : result
    Created on : Jan 25, 2021, 8:24:26 PM
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

    <body>
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

        <c:set var="subject" value="${requestScope.SUBJECT}"/>
        <c:set var="subjectName" value="${subject.subjectName}"/>

        <main class="container">
            <div class="my-3 p-3 bg-white rounded shadow-sm">
                <h2 class="border-bottom pb-2 mb-0">${subjectName}</h2>
                <table border="1" class="table">
                    <thead>
                        <tr>
                            <th>Quiz</th>
                            <th>Grade/10.00</th>
                            <th>Review</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>${subject.subjectID}-${subject.subjectName}</td>
                            <td>${requestScope.RESULT}</td>
                            <td>
                                <form action="reviewQuiz" method="POST">
                                <input type="hidden" name="txtQuizID" value="${requestScope.QUIZID}" />
                                <input type="submit" value="View Review Quiz" class="btn btn-outline-success" />
                                </form>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <form action="takeQuiz" method="POST" class="text-center">
                    <input type="hidden" name="txtSubjectID" value="${subject.subjectID}" />
                    <input type="submit" value="Re-Attempt Quiz" class="btn btn-outline-primary" />
                </form>
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
