<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <title>Details</title>
    </head>
    <body class="container">
        <h1 class="jumbotron">${type} Details</h1>
        <div class="row">
            <div class="col-md-6">
                <table class="table table-bordered table-striped table-hover">
                    <tr>
                        <td>Type</td>
                        <td>${type}</td>
                    </tr>
                    <tr>
                        <td>Title</td>
                        <td>${title}</td>
                    </tr>
                    <tr>
                        <td>Description</td>
                        <td>${desc}</td>
                    </tr>
                    <tr>
                        <td>Longitude</td>
                        <td>${longitude}</td>
                    </tr>
                    <tr>
                        <td>Latitude</td>
                        <td>${latitude}</td>
                    </tr>
                </table>
                <a href="${main}"><button type="button" class="btn btn-default">Search page</button></a>
            </div>
        </div>
    </body>
</html>
