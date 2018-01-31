<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <title>Login/Register</title>
    </head>
    <body class="container">
        <h1 class="jumbotron">Login or Register</h1>
        <c:if test = "${param.error == 'loginFail'}">
            <div class="alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                <span class="sr-only">Error:</span>
                Username/Password doesn't exists
            </div>
        </c:if>
        <c:if test = "${param.error == 'emailExists'}">
            <div class="alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                <span class="sr-only">Error:</span>
                Email Exists! Please choose a different one.
            </div>
        </c:if>
        <c:if test = "${param.error =='usernameExists'}">
            <div class="alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                <span class="sr-only">Error:</span>
                Username Exists! Please choose a different one.
            </div>
        </c:if>
        <c:if test = "${param.error =='invalid_input'}">
            <div class="alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                <span class="sr-only">Error:</span>
                Invalid Input! Please complete all fields for Login.
            </div>
        </c:if>
        <c:if test = "${param.error == 'incomplete_input'}">
            <div class="alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                <span class="sr-only">Error:</span>
                Incomplete Input! Please complete all fields for registration. 
            </div>
        </c:if>
        <div class="row">
            <div class="col-md-5 well">
                <form action="login_user" method="POST">
                    <div class="form-group">
                        <label for="exampleInputEmail1">Email</label>
                        <input type="email" class="form-control" id="exampleInputEmail1" name="email" placeholder="Email" value="${email}">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Password</label>
                        <input type="password" class="form-control" id="exampleInputPassword1" name="password" placeholder="Password" value="${password}">
                    </div>
                    <button type="submit" class="btn btn-primary">Log in</button>
                </form>
            </div>
            <div class="col-md-2">

            </div>
            <div class="col-md-5 well">
                <form action="register_user" method="POST">
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" class="form-control" id="username" name="username" placeholder="Username" value="${registrationUsername}">
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" class="form-control" id="email" name="email" placeholder="Email" value="${registrationEmail}">
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="Password" value="${registrationPassword}">
                    </div>
                    <button type="submit" class="btn btn-primary">Register</button>
                </form>
            </div>
        </div>
        <div class="row">
            <a href="main"><button type="button" class="btn btn-default center-block">Search Page</button></a>
        </div>
    </body>
</html>
