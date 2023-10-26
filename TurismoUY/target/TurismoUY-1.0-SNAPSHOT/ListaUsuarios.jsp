<%-- 
    Document   : ListaUsuarios
    Created on : Oct 26, 2023, 2:17:09 AM
    Author     : Facundo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.sc.entidades.usuario"%>
<%@page import="com.sc.datatypes.dataUsuario"%>
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
            <jsp:include page="/Header.jsp" />
            <section>
            <ul id="actv">
                	<% 
	List<dataUsuario> usuarios = (List<dataUsuario>)
	request.getAttribute("usuarios");
	for(List<dataUsuario> usuario: usuarios){
	%>
               <li>
                <h1><%= usuario.getNombre() %></h1>
                <h1><%= usuario.getApellido() %></h1>
                <h2><%= usuario.getDescripcion() %></h2>
                <h2><%= usuario.getEmail() %></h2>
                <h2><%= usuario.getNacimiento() %></h2>
                </li>
        <% } %>
            </ul>
        </section>
        <jsp:include page="/Footer.jsp" />
        <script src="https://unpkg.com/boxicons@2.1.4/dist/boxicons.js"></script>
        </body>
    </html>
</f:view>
