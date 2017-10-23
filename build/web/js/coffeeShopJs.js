var s = {
    "menu": []
}
var o = {
    "order": []
}
function addItemToMenu(checkbox, categoryName, name, price) {
    if (checkbox.checked == true)
    {
        if (s.menu.length == 0) {
            addCateToMenu(categoryName, s.menu);
            addProductToMenu(name, price, s.menu[0].productList);
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
                        addProductToMenu(name, price, s.menu[i].productList);
                        break;
                    }
                }
            }
            if (existCategory == false) {
                addCateToMenu(categoryName, s.menu);
                addProductToMenu(name, price, s.menu[s.menu.length - 1].productList);
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
function addProductToMenu(name, price, arr) {
    arr.push({
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
    document.getElementById("tableId").value = tableId;
    if (hasClass(el, 'show')) {
        removeClass(el, 'show');
    } else {
        addClass(el, 'show');
    }
}
function onloadFunction() {
    setMenuContent();
}
function ChangeTableItem(name, price, quantity, btn) {
    var orderBlock = document.getElementsByClassName("order-block")[0];
    var tableId = document.getElementById("tableId").value;
    debugger;
    if (o.order.length == 0) {
        if (quantity > 0) {
            addTableToOrder(tableId, price, o.order);
            addProductToOrder(name, price, quantity, o.order[0].productList);
        }
    } else {
        var existTable = false;
        var existProduct = false;
        for (var i = 0; i < o.order.length; i++) {
            if (tableId == o.order[i].tableId) {
                for (var j = 0; j < o.order[i].productList.length; j++) {
                    if (name == o.order[i].productList[j].name) {
                        if (o.order[i].productList[j].quantity > 0) {
                            o.order[i].productList[j].quantity = o.order[i].productList[j].quantity + (quantity);
                            o.order[i].tableTotal = o.order[i].tableTotal + (price);
                            if (o.order[i].productList[j].quantity == 0) {
                                o.order[i].productList.splice(j, 1);
                            }
                        }
                        existProduct = true;
                        break;
                    }
                }
                if (existProduct == true) {
                    existTable = true;
                    break;
                }
                if (existProduct == false) {
                    existTable = true;
                    if (quantity > 0) {
                        addProductToOrder(name, price, quantity, o.order[i].productList);
                        o.order[i].tableTotal = o.order[i].tableTotal + (price);
                    }
                    break;
                }
            }
        }
        if (existTable == false) {
            addTableToOrder(tableId, o.order);
            addProductToOrder(name, price, quantity, o.order[o.order.length - 1].productList);
        }
    }
    document.getElementById("order").innerHTML = JSON.stringify(o);
}
function addTableToOrder(tableId, total, arr) {
    arr.push({
        "tableId": tableId,
        "tableStatus": "Chưa thanh toán",
        "tableTotal": total,
        "productList": []
    })
}
function addProductToOrder(name, price, quantity, arr) {
    arr.push({
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
            var productName = menuContent.menu[i].productList[j].name;
            var productPrice = menuContent.menu[i].productList[j].price;
            var productItemBlock = document.createElement("ul");
            productItemBlock.appendChild(document.createElement("li")).appendChild(document.createTextNode(productName));
            productItemBlock.appendChild(document.createElement("li")).appendChild(document.createTextNode(productPrice));
            var lastChild = document.createElement("li");
            var btnAdd = document.createElement("a");
            btnAdd.className += "btn btn-add";
            btnAdd.setAttribute("onclick", " ChangeTableItem('" + productName + "'," + productPrice + ", 1, this)");
            var btnAddText = document.createTextNode("Cộng");
            btnAdd.appendChild(btnAddText);
            var btnMinus = document.createElement("a");
            btnMinus.className += "btn btn-minus";
            btnMinus.setAttribute("onclick", "ChangeTableItem('" + productName + "'," + (-productPrice) + ", -1, this)");
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
    document.getElementsByClassName("modal-body")[1].appendChild(MenuBlock);
}