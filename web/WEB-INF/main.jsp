<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="stylesheet_2.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <title>Search Page</title>
    </head>
    <body class="container-fluid">
        <div class="onTop">
            <div class="dropdown">
                <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Search
                    <span class="caret"></span></button>
                <ul class="dropdown-menu">
                    <li><a href="search?loc=ATM">ATM</a></li>
                    <li><a href="search?loc=Restroom">Restroom</a></li>
                    <li><a href="search?loc=Food_Drink">Food and Drink</a></li>
                    <c:if test="${not empty longitude}"><li><a href='main'>Cancel Search</a></li></c:if>
                    </ul>
                </div>
            </div>
            <!--<iframe class="fillHeight" src="//www.google.com/maps/embed/v1/directions?origin=${param.lat},${param.lon}&destination=Boost+mobile&mode=walking&key=AIzaSyDe02GVSLWU6-XCqapmyGLaaicClPtxorM"></iframe>-->
        <c:choose>
            <c:when test="${empty longitude}">
                <iframe class="fillHeight" src="//www.google.com/maps/embed/v1/place?q=${param.lat},${param.lon}
                        &zoom=17
                        &key=AIzaSyDe02GVSLWU6-XCqapmyGLaaicClPtxorM">
                </iframe>
            </c:when>
            <c:otherwise> 
                <iframe class="fillHeight" src="//www.google.com/maps/embed/v1/directions?origin=${param.lat},${param.lon}&destination=${latitude},${longitude}&mode=walking&key=AIzaSyDe02GVSLWU6-XCqapmyGLaaicClPtxorM"></iframe>
                <a href="details"><button type="button" class="btn btn-default onTopBottomCenter">${type} - ${title}</button></a>
            </c:otherwise>
        </c:choose>
        <c:if test="${admin == true}">
            <a href="admin"><img src="admin.png" class="onTopBottomOfPage2"></a>
            </c:if>
            <c:choose>
                <c:when test="${loggedIn == true}">
                <a href="logout"><img src="logout.png" class="onTopBottomOfPage"></a>
                <a href="add_poi"><img src="add.png" class="onTopBottomOfPage1"></a>
                </c:when>
                <c:otherwise>
                <a href="loginFormPage"><img src="login.png" class="onTopBottomOfPage"></a>
                </c:otherwise>
            </c:choose>
    </body>
</html>
