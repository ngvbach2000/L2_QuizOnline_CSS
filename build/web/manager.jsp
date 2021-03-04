<%-- 
    Document   : manager
    Created on : Jan 21, 2021, 8:59:53 AM
    Author     : ngvba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <c:if test="${role == 'student'}">
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
                  <a class="nav-link" aria-current="page" href="/L2_QuizOnline_CSS/">Subject Overview</a>
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
                <h2 class="border-bottom pb-2 mb-0">Subject Overview</h2>
                <c:set var="listSubject" value="${sessionScope.LISTSUBJECT}"/>
                <c:if test="${not empty listSubject}">
                    <c:forEach var="subject" items="${listSubject}">
                        <form action="loadQuestion" method="POST">
                            <div class="d-flex text-muted pt-3">
                                <div class="pb-3 mb-0 small lh-sm border-bottom w-100">
                                    <div class="d-flex justify-content-between">
                                        <input type="hidden" name="txtSubjectID" value="${subject.subjectID}" />
                                        <input type="submit" value="${subject.subjectID} - ${subject.subjectName}" class="btn btn-outline-primary" />
                                    </div>
                                  </div>
                              </div>
                        </form>
                    </c:forEach>
                </c:if>
            </div>
        </main>

        <footer class="container">
            <p class="float-end"><a href="#">Back to top</a></p>
            <p>&copy; 2022 Quiz Online, Inc.</p>
        </footer>

        <script type="text/javascript" src="js/offcanvas.js"></script>
        <script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>

    </body>
</html>
