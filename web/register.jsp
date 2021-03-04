<%-- 
    Document   : register
    Created on : Jan 20, 2021, 9:40:29 AM
    Author     : ngvba
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="css/form-validation.css">
        <title>Online Quiz</title>

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
    <body class="bg-light text-center mx-auto">

        <div class="container">
        <main>

            <div class="py-5 text-center">
            <a href="/L2_QuizOnline_CSS/"><h1>Quiz Online</h1></a>
            </div>

            <div class="row g-3 mx-auto">
                <div class="col-md-7 col-lg-8 mx-auto">
                    <h3 class="mb-3 text-center">Register</h3>
                    <c:set var="error" value="${requestScope.REGISTERERR}"/>

                    <form action="register" method="POST" class="needs-validation" novalidate>

                        <div class="row g-3 mx-auto">
                            <div class="col-sm-6 mx-auto">
                                <label for="email" class="form-label">Email*</label>
                                <input type="text" class="form-control" name="txtEmail" id="email" placeholder="Email address" value="${param.txtEmail}" required>
                                <div class="invalid-feedback">
                                    Email is required.
                                </div>
                                <c:if test="${not empty error.emailLengthErr}">
                                    <font color="red">
                                    ${error.emailLengthErr}<br/>
                                    </font>
                                </c:if> 
                                    <c:if test="${not empty error.emailFormatErr}">
                                    <font color="red">
                                    ${error.emailFormatErr}<br/>
                                    </font>
                                </c:if> 
                            </div>    
                        </div>

                        <div class="row g-3 mx-auto">
                            <div class="col-sm-6 mx-auto">
                                <label for="password" class="form-label">Password*</label>
                                <input type="password" class="form-control" name="txtPassword" id="password" placeholder="Password" value="" required>
                                <div class="invalid-feedback">
                                    Password is required.
                                </div>
                                <c:if test="${not empty error.passwordLengthErr}">
                                    <font color="red">
                                    ${error.passwordLengthErr}<br/>
                                    </font>
                                </c:if> 
                            </div>    
                        </div>

                        <div class="row g-3 mx-auto">
                            <div class="col-sm-6 mx-auto">
                                <label for="confirm" class="form-label">Confirm Password*</label>
                                <input type="password" class="form-control" name="txtConfirmPassword" id="confirm" placeholder="Confirm Password" value="" required>
                                <div class="invalid-feedback">
                                    Confirm Password is required.
                                </div>
                                <c:if test="${not empty error.confirmNotMatch}">
                                    <font color="red">
                                    ${error.confirmNotMatch}<br/>
                                    </font>
                                </c:if> 
                            </div>    
                        </div>

                        <div class="row g-3 mx-auto">
                            <div class="col-sm-6 mx-auto">
                                <label for="name" class="form-label">Name*</label>
                                <input type="text" class="form-control" name="txtName" id="name" placeholder="Name" value="${param.txtName}" required>
                                <div class="invalid-feedback">
                                      Name is required.
                                </div>
                                <c:if test="${not empty error.nameLengthErr}">
                                    <font color="red">
                                    ${error.nameLengthErr}<br/>
                                    </font>
                                </c:if>   
                            </div>    
                        </div>

                        <!--
                        Email*: <input type="email" name="txtEmail" value="${param.txtEmail}" /><br/>
                        <c:if test="${not empty error.emailLengthErr}">
                            <font color="red">
                            ${error.emailLengthErr}<br/>
                            </font>
                        </c:if> 
                            <c:if test="${not empty error.emailFormatErr}">
                            <font color="red">
                            ${error.emailFormatErr}<br/>
                            </font>
                        </c:if> 
                            
                        Password*: <input type="password" name="txtPassword" value="" /><br/>
                        <c:if test="${not empty error.passwordLengthErr}">
                            <font color="red">
                            ${error.passwordLengthErr}<br/>
                            </font>
                        </c:if> 
                            
                        Confirm password*: <input type="password" name="txtConfirmPassword" value="" /><br/>
                        <c:if test="${not empty error.confirmNotMatch}">
                            <font color="red">
                            ${error.confirmNotMatch}<br/>
                            </font>
                        </c:if> 
                            
                        Name*: <input type="text" name="txtName" value="${param.txtName}" /><br/>
                        <c:if test="${not empty error.nameLengthErr}">
                            <font color="red">
                            ${error.nameLengthErr}<br/>
                            </font>
                        </c:if> 
                    -->

                        <br/>

                        <input type="submit" value="Register" class="col-sm-6 btn btn-success btn-lg" />
                    </form>

                    <c:if test="${not empty error.emailIsExisted}">
                        <font color="red">
                        ${error.emailIsExisted}<br/>
                        </font>
                    </c:if> 

                    <a href="loginPage">Click here to login</a>
                </div>  
            </div>

        </main>
        </div>

        <script type="text/javascript" src="js/form-validation.js"></script>
        <script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>

    </body>
</html>
