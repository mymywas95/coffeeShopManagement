<%-- 
    Document   : index
    Created on : Oct 23, 2017, 2:20:31 PM
    Author     : MYNVTSE61526
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>mymy</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/coffeeShopStyle.css" rel="stylesheet" type="text/css"/>
    </head>
    <body onload="onloadFunction()">
        <form action="ProcessServlet" method="POST">
            <input type="submit" value="get menu" name="btnAction">
        </form>
        <div id="order"></div>
        <div>
            <div class="table-block"  onclick="openMenuModal('MenuModal', 1)">table 1</div>
            <div class="table-block"  onclick="openMenuModal('MenuModal',2)">table 2</div>
        </div>
    </body>
</html>
<script src="js/coffeeShopJs.js" type="text/javascript"></script>
<div id="MenuModal" class="modal" >
    <div>
        Menu
    </div>
    <div class="modal-body">
        <div class="menu-block">
            
        </div>
        <div class="order-block">
            
        </div>
    </div>
</div>