<%-- 
    Document   : ListaActividades
    Created on : Oct 26, 2023, 1:51:50 AM
    Author     : Facundo
--%>
<%@page import="com.sc.entidades.actividad"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="styles.css" rel="stylesheet" type="text/css">
            <title>JSP Page</title>
        </head>
        <body>
            <jsp:include page="/Header.jsp" />
            <main>
        <div>
            <section>
                <h1>DEPARTAMENTOS</h1>
                <ul>
                    <li>Colonia</li>
                    <li>Maldonado</li>
                    <li>Montevideo</li>
                    <li>Rocha</li>
                </ul>
            </section>
            <section>
                <h1>CATEGORÍAS</h1>
                <ul>
                    <li>Aventura y Deporte</li>
                    <li>Campo y Naturaleza</li>
                    <li>Cultura y Patrimonio</li>
                    <li>Gastronomía</li>
                    <li>Turismo Playas</li>
                </ul>
            </section>
        </div>
        <section>

            <ul id="actv">
                	<% 
	List<dataActividad> actividades = (List<dataActividad>)
	request.getAttribute("actividades");
	for(dataActividad actividad: actividades){
	%>
               <li>
                <h1><%= actividad.getNombre() %></h1>
                <h1><%= actividad.getDescripcion() %></h1>
                </li>
        <% } %>
            </ul>
        </section>
    </main>
        <jsp:include page="/Footer.jsp" />
        <script src="https://unpkg.com/boxicons@2.1.4/dist/boxicons.js"></script>
        </body>
    </html>
</f:view>
