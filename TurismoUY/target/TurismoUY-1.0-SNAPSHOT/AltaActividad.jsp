<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.sc.entidades.actividad"%>
<%@page import="com.sc.datatypes.dataActividad"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
    <meta charset="UTF-8">
    <title>Formulario de Creación de Actividad</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            text-align: center;
        }

        #container {
            width: 300px;
            margin: 0 auto;
            background: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 5px 0 rgba(0, 0, 0, 0.2);
        }

        input {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        #submit-btn {
            background-color: #007bff;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        #submit-btn:hover {
            background-color: #0056b3;
        }
    </style>
    
</head>
<body>
<jsp:include page="/Header.jsp" />
    <div id="container">
        <h2>Formulario de Creación de Actividad</h2>
        <form id="user-form" action="/ActividadControllerServlet" method="post">
    <input type="hidden" name="action" value="create">
    
    <label for="nombreDepto">Nombre del Departamento:</label>
    <input type="text" id="nombreDepto" name="nombreDepto" required>
    
    <label for="nombreProv">Nombre de la Provincia:</label>
    <input type="text" id="nombreProv" name="nombreProv" required>
    
    <label for="nombre">Nombre de la Actividad:</label>
    <input type="text" id="nombre" name="nombre" required>
    
    <label for="desc">Descripción:</label>
    <textarea id="desc" name="desc" required></textarea>
    
    <label for="duracion">Duración (en días):</label>
    <input type="number" id="duracion" name="duracion" required>
    
    <label for="costoUni">Costo por Persona:</label>
    <input type="number" id="costoUni" name="costoUni" required>
    
    <label for="ciudad">Ciudad:</label>
    <input type="text" id="ciudad" name="ciudad" required>
    
    <label for="fechaCrea">Fecha de Creación:</label>
    <input type="date" id="fechaCrea" name="fechaCrea" required>
    
    <button type="submit" id="submit-btn">Crear Actividad</button>
</form>
    </div>
<jsp:include page="/Footer.jsp" />
        </body>
    </html>
</f:view>
