<%-- 
    Document   : menu
    Created on : Oct 19, 2017, 4:56:50 PM
    Author     : MYNVTSE61526
--%>

<%@page contentType="text/html; charset=UTF-8" %> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/coffeeShopStyle.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
    </head>
    <body onload="onloadFunction()">
        <c:forEach var="AllMenuItem" items="${ListAllMenuItem}" >
            <h1>${AllMenuItem.categoryName}</h1>
            <div>
                <table>
                    <c:forEach var="products" items="${AllMenuItem.listProduct}" >
                        <tr>
                            <td>${products.name}</td>
                            <c:forEach var="productItem" items="${products.listProductItemDto}" >
                                <td>${productItem.price}</td>
                                <td><input type="radio" name="${products.id}" 
                                           onclick="addItemToMenu(this, '${AllMenuItem.categoryName}', '${products.id}','${products.name}', '${productItem.price}')"></td>
                                </c:forEach>
                        </tr>
                    </c:forEach>

                </table>
            </div>
        </c:forEach>
        <div id="menu">

        </div>
        <a href="#" onclick="saveToLocalStorage(menu)">save to Localstorage</a>
        <a href="#" onclick="openMenuModal('MenuModal')">Open modal</a>
    </body>

</html>
<script src="js/coffeeShopJs.js" type="text/javascript"></script>
<div id="MenuModal" class="modal" >
    <div>
        Menu
    </div>
    <div class="modal-body">

    </div>
</div>