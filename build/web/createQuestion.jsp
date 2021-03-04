<%-- 
    Document   : createQuestion
    Created on : Jan 21, 2021, 7:02:44 PM
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

        <c:set var="subject" value="${sessionScope.SUBJECT}"/>
        <c:set var="subjectID" value="${subject.subjectID}"/>
        <c:set var="subjectName" value="${subject.subjectName}"/>
        
        <main class="container">
            <div class="my-3 p-3 bg-white rounded shadow-sm">
                <h2 class="border-bottom pb-2 mb-0 text-center">${subjectName}</h2>
                <h3 class="p-2">Create New Question</h3>
                <c:set var="err" value="${requestScope.CREATEQUESTIONERROR}"/>

        <form action="createQuestion" method="POST">
            <table border="1" class="table table-bordered">
                <thead>
                    <tr>
                        <th class="w-25">New Question:</th>
                        <td>
                            Question content: <input type="text" name="txtQuestionContent" value="${param.txtQuestionContent}" size="100" /><br/>
                            <c:if test="${not empty err.questionLengthErr}">
                                <font color="red">
                                ${err.questionLengthErr}<br/>
                                </font>
                            </c:if>
                        </td>

                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td></td>
                        <td>
                            a. <input type="text" name="txtAnsCont1" value="${param.txtAnsCont1}" size="90" /><br/>
                            <c:if test="${not empty err.answer1LengthErr}">
                                <font color="red">
                                ${err.answer1LengthErr}<br/>
                                </font>
                            </c:if>
                            b. <input type="text" name="txtAnsCont2" value="${param.txtAnsCont2}" size="90" /><br/>
                            <c:if test="${not empty err.answer2LengthErr}">
                                <font color="red">
                                ${err.answer2LengthErr}<br/>
                                </font>
                            </c:if>
                            c. <input type="text" name="txtAnsCont3" value="${param.txtAnsCont3}" size="90" /><br/>
                            <c:if test="${not empty err.answer3LengthErr}">
                                <font color="red">
                                ${err.answer3LengthErr}<br/>
                                </font>
                            </c:if>
                            d. <input type="text" name="txtAnsCont4" value="${param.txtAnsCont4}" size="90" /><br/>
                            <c:if test="${not empty err.answer4LengthErr}">
                                <font color="red">
                                ${err.answer4LengthErr}<br/>
                                </font>
                            </c:if>
                            Correct Answer: 
                            a. <input type="radio" name="correctAnswer" value="1" checked="checked"/>
                            | b. <input type="radio" name="correctAnswer" value="2" />
                            | c. <input type="radio" name="correctAnswer" value="3" />
                            | d. <input type="radio" name="correctAnswer" value="4" />
                        </td>
                    </tr>
                </tbody>
            </table>
            <input type="hidden" name="txtSubjectID" value="${subjectID}" />
            <input type="submit" value="Create" class="btn btn-primary m-1" />
        </form>
        <c:if test="${not empty err.answerIsDupicate}">
            <font color ="red">
            ${err.answerIsDupicate}<br/>
            Please try again!<br/>
            </font>
        </c:if>

        <form action="loadQuestion" method="POST">
            <input type="hidden" name="txtSubjectName" value="${subjectName}" />
            <input type="hidden" name="txtSubjectID" value="${subjectID}" />
            <input type="submit" value="Return to ${subjectName}" class="btn btn-secondary m-1" />
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
