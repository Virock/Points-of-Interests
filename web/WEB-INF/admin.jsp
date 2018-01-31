<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <title>Administrative Page</title>
    </head>

    <body class="container">
        <h1 class="jumbotron">Administrative Page</h1>
        <div>
            <h2>Users</h2>
            ${display_users}
        </div>
        <div>
            <h2>POIs</h2>
            ${display_pois}
        </div>
        <div>
            <a href="main"><button type="button" class="btn btn-default center-block">Search Page</button></a>
        </div>
    </body>
</html>
