<%-- 
    Document   : Header
    Created on : Oct 25, 2023, 8:32:53 PM
    Author     : Facundo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="styles.css" rel="stylesheet" type="text/css">
            <title>JSP Page</title>
        </head>
        <body>
            <header>
        <nav>
            <h1>Turismo.UY</h1>
            <div id="busqueda">
                <i class='bx bx-search'></i>
                <input type="text" id="search" placeholder="Actividades Turísticas, Paquetes">
                <button type="submit" id="search-btn">Buscar</button>
            </div>
            <ul>
                <li><a href="./login-form-v1/Login_v1/index.html">Iniciar Sesión</a></li>
                <li><a href="./colorlib-regform-4/index.html">Alta Usuario</a></li>
            </ul>
        </nav>
    </header>
        </body>
    </html>
    <script src="https://unpkg.com/boxicons@2.1.4/dist/boxicons.js"></script>

</f:view>
