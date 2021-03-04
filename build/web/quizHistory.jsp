<%-- 
    Document   : quizHistory
    Created on : Jan 28, 2021, 11:57:43 AM
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

        <main class="container">
            <div class="my-3 p-3 bg-white rounded shadow-sm">
                <h2 class="border-bottom pb-2 mb-0 text-center">Quiz History</h2>
                <div class="my-3 p-2">
                    <form action="searchQuizHistory" method="POST" class="form-group d-flex ">
                        <label class="m-auto">Search: </label>
                        <input type="text" name="txtSearch" value="${param.txtSearch}" class="form-control" />
                        <input type="submit" value="Search" class="btn btn-outline-primary" />
                    </form>
                </div>

                <c:set var="quizHistory" value="${requestScope.LISTQUIZHISTORY}"/>
                <c:if test="${not empty quizHistory}">
                    <table border="1" class="table table-hover">
                        <thead>
                            <tr>
                                <th>No. </th>
                                <th>Subject</th>
                                <th>Result</th>
                                <th>Start Date</th>
                                <th>Finish Date</th>
                                <th>Review</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="quiz" items="${quizHistory}" varStatus="counter">
                                <tr>
                                    <td>${counter.count}</td>
                                    <td>
                                        ${quiz.subjectID}
                                    </td>
                                    <td>
                                        ${quiz.totalPoint}
                                    </td>
                                    <td>
                                        ${quiz.startDate}
                                    </td>
                                    <td>
                                        ${quiz.finishDate}
                                    </td>
                                    <td>
                                        <form action="reviewQuiz" method="POST">
                                            <input type="hidden" name="txtQuizID" value="${quiz.quizID}" />
                                            <input type="submit" value="Review" class="btn btn-outline-success" />
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${empty quizHistory}">
                    <h4>No result ...</h4>
                </c:if>
                <c:set var="noOfPage" value="${requestScope.NOOFPAGE}"/>
                <c:set var="currentPage" value="${requestScope.CURRENTPAGE}"/>
                <c:choose>
                    <c:when test="${not empty param.txtSearch}">
                        <c:set var="type" value="searchQuizHistory"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="type" value="viewQuizHistory"/>
                    </c:otherwise>
                </c:choose>

                <c:if test="${noOfPage != 0}">
                    
                    <table border="1" class="table-bordered mx-auto">
                        <tr>
                            <c:url var="previousPage" value="${type}" >
                                <c:param name="page" value="${currentPage - 1}"/>
                                <c:param name="txtSearch" value="${param.txtSearch}"/>
                            </c:url>
                            <c:if test="${currentPage != 1}">
                                <td>
                                <a href="${previousPage}" class="btn btn-light"> < </a>
                                </td>
                            </c:if>
                            <c:forEach begin="1" end="${noOfPage}" var="i">
                                <c:choose>
                                    <c:when test="${currentPage == i}">
                                        <td><label class="btn btn-primary">${i}</label> </td>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url var="choosePage" value="${type}" >
                                            <c:param name="page" value="${i}"/>
                                            <c:param name="txtSearch" value="${param.txtSearch}"/>
                                        </c:url>
                                        <td> <a href="${choosePage}" class="btn btn-light"> ${i} </a> </td>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:url var="nextPage" value="${type}" >
                                <c:param name="page" value="${currentPage + 1}"/>
                                <c:param name="txtSearch" value="${param.txtSearch}"/>
                            </c:url>
                            <c:if test="${currentPage < noOfPage}">
                                <td>
                                <a href="${nextPage}" class="btn btn-light"> > </a>
                                </td>
                            </c:if>
                        </tr>
                    </table>
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
