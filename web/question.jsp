<%-- 
    Document   : question
    Created on : Jan 22, 2021, 4:45:37 PM
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
                <h3 class="p-2">Update Subject</h3>
                <c:set var="updateSubjectError" value="${requestScope.UPDATESUBJECTERROR}"/>
                <c:if test="${not empty requestScope.UPDATESUBJECTNOTIFY}">
                    <font color="red">
                    ${requestScope.UPDATESUBJECTNOTIFY}<br/>
                    </font>
                </c:if>
                <c:if test="${not empty updateSubjectError.rangeOfDateError}">
                    <font color="red">
                    ${updateSubjectError.rangeOfDateError}<br/>
                    </font>
                </c:if>
                <form action="updateSubject" method="POST" class="p-2">
                    Begin Date: <input type="date" name="txtBeginDate" value="${subject.beginDate}" /> <br/>
                    <c:if test="${not empty updateSubjectError.beginDateIsEmpty}">
                        <font color="red">
                        ${updateSubjectError.beginDateIsEmpty}<br/>
                        </font>
                    </c:if>
                    End Date: <input type="date" name="txtEndDate" value="${subject.endDate}" /><br/>
                    <c:if test="${not empty updateSubjectError.endDateIsEmpty}">
                        <font color="red">
                        ${updateSubjectError.endDateIsEmpty}<br/>
                        </font>
                    </c:if>
                    Time Limit: <input type="text" name="txtTime" value="${subject.timeDuration}" /><br/>
                    <c:if test="${not empty updateSubjectError.rangeOfTimeDurationError}">
                        <font color="red">
                        ${updateSubjectError.rangeOfTimeDurationError}<br/>
                        </font>
                    </c:if>
                    Number of Question: <input type="number" name="txtNumberOfQuestion" value="${subject.numberOfQuestion}" /><br/>
                    <c:if test="${not empty updateSubjectError.numberOfQuestionLengthError}">
                        <font color="red">
                        ${updateSubjectError.numberOfQuestionLengthError}<br/>
                        </font>
                    </c:if>
                    <input type="hidden" name="txtSubjectID" value="${subjectID}" />
                    <input type="submit" value="Update" class="btn btn-outline-success" />
                </form>
                <hr class="featurette-divider">

                <h3 class="p-2">Search</h3>
                <form action="searchQuestionBySubjectID" class="p-2 form-inline">
                    Name: <input type="text" name="txtSearchName" value="${param.txtSearchName}" />
                    Status: <select name="txtSearchStatus">
                        <option value="">- choose status -</option>
                        <option ${'Activate' == param.txtSearchStatus ? 'selected':''} >Activate</option>
                        <option ${'Deactivate' == param.txtSearchStatus ? 'selected':''} >Deactivate</option>
                    </select>
                    <input type="hidden" name="txtSubjectID" value="${subjectID}" />
                    <input type="submit" value="Search" class="btn btn-outline-success" />
                </form>
                <hr class="featurette-divider">

                <c:choose>
                    <c:when test="${not empty param.txtSearchName or not empty param.txtSearchStatus}">
                        <c:set var="type" value="searchQuestionBySubjectID"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="type" value="loadQuestion"/>
                    </c:otherwise>
                </c:choose>

                <h3>Question</h3>
        
                <c:url var="create" value="createQuestionPage">
                    <c:param name="txtSubjectID" value="${subjectID}"/>
                </c:url>
                <a href="${create}" class="btn btn-outline-primary m-1">Create new Question</a><br/>

                <c:if test="${not empty requestScope.NOTIFY}">
                    <font color="red">
                    ${requestScope.NOTIFY}<br/>
                    </font>
                </c:if>

                <c:set var="err" value="${requestScope.UPDATEQUESTIONERROR}"/>
                <c:if test="${not empty err.questionLengthErr}">
                    <font color="red">
                    ${err.questionLengthErr}<br/>
                    </font>
                </c:if>
                <c:if test="${not empty err.answerLengthErr}">
                    <font color="red">
                    ${err.answerLengthErr}<br/>
                    </font>
                </c:if>
                <c:if test="${not empty err.answerIsDupicate}">
                    <font color ="red">
                    ${err.answerIsDupicate}<br/>
                    </font>
                </c:if>

                <c:set var="listQnA" value="${requestScope.LISTQNA}"/>
                <c:if test="${not empty listQnA}">

                    <table border="1" class="table table-bordered">
                        <thead>
                            <tr>
                                <th>List Question</th>
                                <th>Update</th>
                                <th>Delete</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="QnA" items="${listQnA}" varStatus="counter">
                            <form action="updateQuestion" method="POST">

                                <tr>
                                    <td>
                                        <table border="1" class="table">
                                            <thead>
                                                <tr>
                                                    <th class="w-25">Question: ${counter.count}</td>
                                                    <td>
                                                        <input type="text" name="txtQuestionContent" value="${QnA.key.questionContent}"
                                                               size="80" />
                                                    </td>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>
                                                        <select name="txtStatus">
                                                            <c:choose>
                                                                <c:when test="${QnA.key.status}">
                                                                    <option>Activate</option>
                                                                    <option>Deactivate</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option>Deactivate</option>
                                                                    <option>Activate</option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <c:forEach var="answer" items="${QnA.value}" varStatus="counter">
                                                            <input type="radio" name="correctAns${answer.questionID}" value="${counter.count}" 
                                                                   <c:if test="${answer.answerContent eq QnA.key.correctAnswer}">
                                                                       checked="checked"
                                                                   </c:if>
                                                                   />
                                                            <input type="text" name="txtAnsCont${counter.count}" value="${answer.answerContent}" 
                                                                   size="75"/><br/>
                                                        </c:forEach>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </td>
                                    <td>
                                        <input type="hidden" name="txtQuestionID" value="${QnA.key.questionID}" />
                                        <input type="hidden" name="txtSubjectID" value="${subjectID}" />
                                        <input type="hidden" name="page" value="${requestScope.CURRENTPAGE}" />
                                        <input type="hidden" name="type" value="${type}" />
                                        <input type="hidden" name="txtSearchName" value="${param.txtSearchName}" />
                                        <input type="hidden" name="txtSearchStatus" value="${param.txtSearchStatus}" />
                                        <input type="submit" value="Update" class="btn btn-outline-success" />
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${!QnA.key.status}">
                                                <font color="red">
                                                    Deleted
                                                </font>
                                            </c:when>
                                            <c:otherwise>
                                                <c:url var="delete" value="deleteQuestion">
                                                    <c:param name="page" value="${requestScope.CURRENTPAGE}"/>
                                                    <c:param name="type" value="${type}"/>
                                                    <c:param name="txtSearchName" value="${param.txtSearchName}" />
                                                    <c:param name="txtSearchStatus" value="${param.txtSearchStatus}" />
                                                    <c:param name="txtQuestionID" value="${QnA.key.questionID}"/>
                                                    <c:param name="txtSubjectID" value="${subjectID}"/>
                                                </c:url>
                                                <a href="${delete}" class="btn btn-outline-danger">Delete</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </form>
                        </c:forEach>
                    </tbody>
                </table>


            </c:if>
            <c:if test="${empty listQnA}">
                <h4>No result ...</h4>
            </c:if>

            <!-- paging -->
            <c:set var="noOfPage" value="${requestScope.NOOFPAGE}"/>
            <c:set var="currentPage" value="${requestScope.CURRENTPAGE}"/>

            <c:if test="${noOfPage != 0}">
                <c:url var="previousPage" value="${type}" >
                    <c:param name="txtSearchName" value="${param.txtSearchName}" />
                    <c:param name="txtSearchStatus" value="${param.txtSearchStatus}" />
                    <c:param name="txtSubjectID" value="${subjectID}"/>
                    <c:param name="page" value="${currentPage - 1}"/>
                </c:url>
                

                <c:url var="nextPage" value="${type}" >
                    <c:param name="txtSearchName" value="${param.txtSearchName}" />
                    <c:param name="txtSearchStatus" value="${param.txtSearchStatus}" />
                    <c:param name="txtSubjectID" value="${subjectID}"/>
                    <c:param name="page" value="${currentPage + 1}"/>
                </c:url>
                

                <table border="1" class="table-bordered mx-auto">
                    <tr>
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
                                        <c:param name="txtSearchName" value="${param.txtSearchName}" />
                                        <c:param name="txtSearchStatus" value="${param.txtSearchStatus}" />
                                        <c:param name="txtSubjectID" value="${subjectID}"/>
                                        <c:param name="page" value="${i}"/>
                                    </c:url>
                                    <td> <a href="${choosePage}" class="btn btn-light"> ${i} </a> </td>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
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
