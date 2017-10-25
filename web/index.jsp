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
        <link href="css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/coffeeShopStyle.css" rel="stylesheet" type="text/css"/>
    </head>
    <body onload="onloadFunction()">
        <div class="header-lock">
            <div class="header-item">
                <div class="logo-block">
                    <div class="logo-img">
                        <img src="img/phecapheLogo.png" alt=""/>
                    </div>
                </div>
                <div class="user-information-block">
                    <div class="information-block">
                        <div class="user-infor-item user-img">
                            <img src="img/User.png" alt=""/>
                        </div>
                        <div class="user-infor-item user-action"> 
                            <span class="user-name">Mynvt</span>
                            <span class="user-action-item"><i class="fa fa-caret-down"></i></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="content-block">
            <div class="group-action-btn">
                <div class="action-btn-block">
                    <div class="action-btn-item">
                        <a class="btn btn-normal">Cập nhập menu</a>
                    </div>
                    <div class="action-btn-item">
                        <a class="btn btn-normal">Thêm bàn</a>
                    </div>
                </div>
            </div>
            <div class="content-management-block">
                <div class="table-management-block">
                    <ul>
                        <li>
                            <a>
                                <div>
                                    <div>bàn 1</div>
                                    <div><span>Tổng: </span> <span>125k</span></div>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div>
                                    <div>bàn 1</div>
                                    <div><span>Tổng: </span> <span>125k</span></div>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div>
                                    <div>bàn 1</div>
                                    <div><span>Tổng: </span> <span>125k</span></div>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div>
                                    <div>bàn 1</div>
                                    <div><span>Tổng: </span> <span>125k</span></div>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div>
                                    <div>bàn 1</div>
                                    <div><span>Tổng: </span> <span>125k</span></div>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div>
                                    <div>bàn 1</div>
                                    <div><span>Tổng: </span> <span>125k</span></div>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div>
                                    <div>bàn 1</div>
                                    <div><span>Tổng: </span> <span>125k</span></div>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div>
                                    <div>bàn 1</div>
                                    <div><span>Tổng: </span> <span>125k</span></div>
                                </div>
                            </a>
                        </li>
                    </ul>
                </div>       
            </div>
        </div>
        <!--        <form action="ProcessServlet" method="POST">
                    <input type="submit" value="get menu" name="btnAction">
                </form>-->
        <div id="order"></div>
        <div class="header-page">

        </div>
        <div>
            <div class="table-block"  onclick="openMenuModal('MenuModal', 1)">table 1</div>
            <div class="table-block"  onclick="openMenuModal('MenuModal', 2)">table 2</div>
        </div>
        <div id="demo">

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