<%-- 
    Document   : index
    Created on : 19/03/2020, 11:24:39
    Author     : mega_
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>JSP Page</title>
    </head>
    <body>
        <form id="form_search" method="POST" onsubmit="evSearch()">
            <input id="item_id" name="item" type="text" required autofocus>
            <input type="submit" value="Buscar"/>
        </form>
        <div id="result"></div>
        
        <script src="js/jquery-3.4.1.js" type="text/javascript"></script>
        <script src="js/script.js" type="text/javascript"></script>
    </body>
</html>
