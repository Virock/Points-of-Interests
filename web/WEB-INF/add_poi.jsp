<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <Title>Add a Point of interest</Title>
    </head>
    <body class="container">
        <h1 class="jumbotron">Add a point of interest</h1>
        <c:if test = "${param.error == 'invalid_input'}">
            <div class="alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                <span class="sr-only">Error:</span>
                All fields must be filled
            </div>
        </c:if>
        <div class="row">
            <div class="col-md-6 well col-md-offset-3">
                <form action="add_this_poi" method="POST">
                    <div class="form-group">
                        <label for="title">Title</label>
                        <input type="text" class="form-control" id="title" name="title" placeholder="Title" value="${poi_details[0]}">
                    </div>
                    <div class="form-group">
                        <label for="type">Type</label>
                        <select class="form-control" id="type" name="type">
                            <option value="ATM" <c:if test="${poi_details[1] eq 'ATM'}"><c:out value="selected"></c:out></c:if>>ATM</option>
                            <option value="Restroom" <c:if test="${poi_details[1] eq 'Restroom'}"><c:out value="selected"></c:out></c:if>>Restroom</option>
                            <option value="Food_Drink" <c:if test="${poi_details[1] eq 'Food_Drink'}"><c:out value="selected"></c:out></c:if>>Food and Drink</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea class="form-control" placeholder="Description" rows="5" name="desc" id="description">${poi_details[4]}</textarea>
                    </div>
                    <div class="form-group">
                        <label for="longitude">Longitude</label>
                        <input type="text" class="form-control" id="longitude" name="lon" placeholder="Longitude" value="${poi_details[2]}">
                    </div>
                    <div class="form-group">
                        <label for="latitude">Latitude</label>
                        <input type="text" class="form-control" id="latitude" name="lat" placeholder="Latitude" value="${poi_details[3]}">
                    </div>
                    <button type="submit" class="btn btn-primary">Add POI</button>
                </form>
            </div>
        </div>
        <div>
            <a href="main"><button type="button" class="btn btn-default center-block">Search Page</button></a>
        </div>
    </body>
</html>
