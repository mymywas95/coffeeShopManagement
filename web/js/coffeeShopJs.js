var s = {
    "menu": []
};
var o = {};
function addItemToMenu(checkbox, categoryName, id, name, price) {
    if (checkbox.checked == true)
    {
        if (s.menu.length == 0) {
            addCateToMenu(categoryName, s.menu);
            addProductToMenu(id, name, price, s.menu[0].productList);
        } else {
            var existCategory = false;
            var existProduct = false;
            for (var i = 0; i < s.menu.length; i++) {
                if (categoryName == s.menu[i].CategoryName) {
                    for (var j = 0; j < s.menu[i].productList.length; j++) {
                        if (name == s.menu[i].productList[j].name) {
                            s.menu[i].productList[j].price = price;
                            existProduct = true;
                            break;
                        }
                    }
                    if (existProduct == true) {
                        existCategory = true;
                        break;
                    }
                    if (existProduct == false) {
                        existCategory = true;
                        addProductToMenu(id, name, price, s.menu[i].productList);
                        break;
                    }
                }
            }
            if (existCategory == false) {
                addCateToMenu(categoryName, s.menu);
                addProductToMenu(id, name, price, s.menu[s.menu.length - 1].productList);
            }
        }
    }
    document.getElementById("menu").innerHTML = JSON.stringify(s);
}
function addCateToMenu(CateTitle, arr) {
    arr.push({
        "CategoryName": CateTitle,
        "productList": []
    })
}
function addProductToMenu(id, name, price, arr) {
    arr.push({
        "id": id,
        "name": name,
        "price": price
    })
}
function saveToLocalStorage(selectedMenuContentId) {
    localStorage.setItem("myMenu", JSON.stringify(s));
    console.log(localStorage.getItem("myMenu"));
}
function hasClass(el, className) {
    if (el.classList)
        return el.classList.contains(className)
    else
        return !!el.className.match(new RegExp('(\\s|^)' + className + '(\\s|$)'))
}
function addClass(el, className) {
    if (el.classList)
        el.classList.add(className)
    else if (!hasClass(el, className))
        el.className += " " + className
}
function removeClass(el, className) {
    if (el.classList)
        el.classList.remove(className)
    else if (hasClass(el, className)) {
        var reg = new RegExp('(\\s|^)' + className + '(\\s|$)')
        el.className = el.className.replace(reg, ' ')
    }
}
function openMenuModal(id, tableId) {
    var el = document.getElementById(id);
    o = {};
    document.getElementById("tableId").value = tableId;
    var tableExist = false;
    if (hasClass(el, 'show')) {
        removeClass(el, 'show');
    } else {
        var orderStored = localStorage.getItem("order");
        if (orderStored !== null && typeof (orderStored) !== "undefined") {
            var orderStoredParsed = JSON.parse(orderStored);
            var currentTable;
            for (var i = 0; i < orderStoredParsed.order.length; i++) {
                if (orderStoredParsed.order[i].tableId == tableId) {
                    currentTable = orderStoredParsed.order[i];
                    setOrderContent(currentTable, 1);
                    o = currentTable;
                    tableExist = true;
                    break;
                }
            }
            if (tableExist == false) {
                var myNode = document.getElementsByClassName("order-item-block");
                var myNodeTotal = document.getElementsByClassName("order-total");
                var myNodeSave = document.getElementsByClassName("btn-save-order");
                var myNodePay = document.getElementsByClassName("btn-pay-order");
                if (typeof (myNode[0]) !== "undefined") {
                    while (myNode[0]) {
                        myNode[0].parentNode.removeChild(myNode[0]);
                    }
                }
                if (typeof (myNodeTotal[0]) !== "undefined") {
                    while (myNodeTotal[0]) {
                        myNodeTotal[0].parentNode.removeChild(myNodeTotal[0]);
                    }
                }
                if (typeof (myNodeSave[0]) !== "undefined") {
                    while (myNodeSave[0]) {
                        myNodeSave[0].parentNode.removeChild(myNodeSave[0]);
                    }
                }
                if (typeof (myNodePay[0]) !== "undefined") {
                    while (myNodePay[0]) {
                        myNodePay[0].parentNode.removeChild(myNodePay[0]);
                    }
                }

            }
        }
        addClass(el, 'show');
    }
}
function onloadFunction() {
    setMenuContent();
}
function ChangeTableItem(id, name, price, quantity, btn) {
    debugger;
    var orderBlock = document.getElementsByClassName("order-block")[0];
    var tableId = document.getElementById("tableId").value;

    if (typeof (o.productList) == "undefined") {
        addTableToOrder(tableId, price, o);
        addProductToOrder(id, name, price, quantity, o.productList);
    } else {
        var existTable = false;
        var existProduct = false;
        for (var j = 0; j < o.productList.length; j++) {
            if (name == o.productList[j].name) {
                if (o.productList[j].quantity > 0) {
                    o.productList[j].quantity = o.productList[j].quantity + (quantity);
                    o.tableTotal = o.tableTotal + (price);
                    if (o.productList[j].quantity == 0) {
                        o.productList.splice(j, 1);
                    }
                }
                existProduct = true;
                break;
            }
        }
        if (existProduct == false) {
            existTable = true;
            if (quantity > 0) {
                addProductToOrder(id, name, price, quantity, o.productList);
                o.tableTotal = o.tableTotal + (price);
            }
        }
    }
    setOrderContent(o, 0);
    document.getElementById("order").innerHTML = JSON.stringify(o);
}
function addTableToOrder(tableId, total, arr) {
    arr.tableId = tableId;
    arr.tableStatus = "Chưa thanh toán";
    arr.tableTotal = total;
    arr.productList = [];
}
function addProductToOrder(id, name, price, quantity, arr) {
    arr.push({
        "id": id,
        "name": name,
        "price": price,
        "quantity": quantity
    })
}
function setMenuContent() {
    var menuContentStored = localStorage.getItem("myMenu");
    var menuContent = JSON.parse(menuContentStored);
    var MenuBlock = document.getElementsByClassName("menu-block")[0];
    var tableBlock = document.createElement("input");
    tableBlock.setAttribute('value', '1');
    tableBlock.setAttribute('id', 'tableId');
    MenuBlock.appendChild(tableBlock);
    for (var i = 0; i < menuContent.menu.length; i++) {
        var categoryBlock = document.createElement("div");
        categoryBlock.className += "category-block";
        var category = document.createElement("div");
        category.className += "category-title";
        var categoryContent = category.appendChild(document.createTextNode(menuContent.menu[i].CategoryName));
        var productList = document.createElement("div");
        productList.className += "product-list-block";
        for (var j = 0; j < menuContent.menu[i].productList.length; j++) {
            var productId = menuContent.menu[i].productList[j].id;
            var productName = menuContent.menu[i].productList[j].name;
            var productPrice = menuContent.menu[i].productList[j].price;
            var productItemBlock = document.createElement("ul");
            productItemBlock.appendChild(document.createElement("li")).appendChild(document.createTextNode(productName));
            productItemBlock.appendChild(document.createElement("li")).appendChild(document.createTextNode(productPrice));
            var lastChild = document.createElement("li");
            var btnAdd = document.createElement("a");
            btnAdd.className += "btn btn-add";
            btnAdd.setAttribute("onclick", "ChangeTableItem(" + productId + ",'" + productName + "'," + productPrice + ", 1, this)");
            var btnAddText = document.createTextNode("Cộng");
            btnAdd.appendChild(btnAddText);
            var btnMinus = document.createElement("a");
            btnMinus.className += "btn btn-minus";
            btnMinus.setAttribute("onclick", "ChangeTableItem(" + productId + ",'" + productName + "'," + (-productPrice) + ", -1, this)");
            var btnMinusText = document.createTextNode("Trừ");
            btnMinus.appendChild(btnMinusText);
            lastChild.appendChild(btnAdd);
            lastChild.appendChild(btnMinus);
            productItemBlock.appendChild(lastChild);
            productList.appendChild(productItemBlock);
        }
        categoryBlock.appendChild(category);
        categoryBlock.appendChild(productList);
        MenuBlock.appendChild(categoryBlock);
    }
    document.getElementsByClassName("modal-body")[0].appendChild(MenuBlock);
}
//link = 1 is show pay, 0 is not
function setOrderContent(orderContent, link) {
    debugger;
    var myNode = document.getElementsByClassName("order-item-block");
    var myNodeTotal = document.getElementsByClassName("order-total");
    var myNodeSave = document.getElementsByClassName("btn-save-order");
    var myNodePay = document.getElementsByClassName("btn-pay-order");
    if (typeof (myNode[0]) !== "undefined") {
        while (myNode[0]) {
            myNode[0].parentNode.removeChild(myNode[0]);
        }
    }
    if (typeof (myNodeTotal[0]) !== "undefined") {
        while (myNodeTotal[0]) {
            myNodeTotal[0].parentNode.removeChild(myNodeTotal[0]);
        }
    }
    if (typeof (myNodeSave[0]) !== "undefined") {
        while (myNodeSave[0]) {
            myNodeSave[0].parentNode.removeChild(myNodeSave[0]);
        }
    }
    if (typeof (myNodePay[0]) !== "undefined") {
        while (myNodePay[0]) {
            myNodePay[0].parentNode.removeChild(myNodePay[0]);
        }
    }
    var orderBlock = document.createElement("div");
    orderBlock.className += "order-item-block";
    for (var j = 0; j < orderContent.productList.length; j++) {
        var productName = orderContent.productList[j].name;
        var productPrice = orderContent.productList[j].price;
        var productQuabtity = orderContent.productList[j].quantity;
        var productItemBlock = document.createElement("ul");
        productItemBlock.appendChild(document.createElement("li")).appendChild(document.createTextNode(productName));
        productItemBlock.appendChild(document.createElement("li")).appendChild(document.createTextNode(productPrice));
        productItemBlock.appendChild(document.createElement("li")).appendChild(document.createTextNode(productQuabtity));
        orderBlock.appendChild(productItemBlock);
    }
    var orderToalBlock = document.createElement("div");
    orderToalBlock.appendChild(document.createTextNode(orderContent.tableTotal));
    orderToalBlock.className += "order-total";
    var orderSaveBlock = document.createElement("div");
    orderSaveBlock.className += "btn-save-block";
    var btnSaveOrder = document.createElement("a");
    btnSaveOrder.className += "btn btn-save-order";
    btnSaveOrder.setAttribute("onclick", "saveOrderToLocalStorage()");
    orderSaveBlock.appendChild(btnSaveOrder);
    btnSaveOrder.appendChild(document.createTextNode("Xác nhận"));

    document.getElementsByClassName("order-block")[0].appendChild(orderBlock);
    if (link == 1) {
        var btnPayOrder = document.createElement("a");
        btnPayOrder.className += "btn btn-pay-order";
        btnPayOrder.setAttribute("onclick", "solveOrder('" + orderContent.tableId + "')");
        btnPayOrder.appendChild(document.createTextNode("Thanh toán"));
        orderSaveBlock.appendChild(btnPayOrder)
    }
    if (orderContent.productList.length > 0) {
        document.getElementsByClassName("order-block")[0].appendChild(orderToalBlock);
        document.getElementsByClassName("order-block")[0].appendChild(orderSaveBlock);
    }


}
function saveOrderToLocalStorage() {
    var existTable = false;
    var orderStored = localStorage.getItem("order");
    setOrderContent(o, 1);
    if (typeof (orderStored) == "undefined" || orderStored == null) {
        orderStored = {"order": []};
        orderStored.order.push(o);
        var orderStoredParsed = JSON.stringify(orderStored);
        localStorage.setItem("order", orderStoredParsed);
    } else {
        var orderStoredParsed = JSON.parse(orderStored);
        for (var i = 0; i < orderStoredParsed.order.length; i++) {
            if (orderStoredParsed.order[i].tableId == o.tableId) {
                orderStoredParsed.order[i].tableStatus = o.tableStatus;
                orderStoredParsed.order[i].tableTotal = o.tableTotal;
                orderStoredParsed.order[i].productList = o.productList;
                existTable = true;
                localStorage.setItem("order", JSON.stringify(orderStoredParsed));
                break;

            }
        }
        if (existTable == false) {
            orderStoredParsed.order.push(o);
            localStorage.setItem("order", JSON.stringify(orderStoredParsed));
        }
    }
    o = {};
}
function solveOrder(tableId) {
    var orderStored = localStorage.getItem("order");
    var orderStoredParsed = JSON.parse(orderStored);
    var tableInformation;
    for (var i = 0; i < orderStoredParsed.order.length; i++) {
        if (orderStoredParsed.order[i].tableId == tableId) {
            tableInformation = orderStoredParsed.order[i];
//           orderStoredParsed.order[i].tableStatus = "Đã Thanh toán";
//           orderStoredParsed.order[i].tableTotal = "0";
//           orderStoredParsed.order[i].productList = [];
            break;
        }
    }
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            if (this.responseText == "success") {
                for (var i = 0; i < orderStoredParsed.order.length; i++) {
                    if (orderStoredParsed.order[i].tableId == tableInformation.tableId) {
                        orderStoredParsed.order[i].tableStatus = "Đã Thanh toán";
                        orderStoredParsed.order[i].tableTotal = 0;
                        orderStoredParsed.order[i].productList = [];
                         localStorage.setItem("order", JSON.stringify(orderStoredParsed));
                         setOrderContent(orderStoredParsed.order[i], 1);
                        break;
                    }
                }
            }else{
                 document.getElementById("demo").innerHTML = this.responseText;
            }
           
        }
    };
    xhttp.open("POST", "/coffeeShopManagement/solveOrderServlet");
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xhttp.send("order=" + JSON.stringify(tableInformation));

}