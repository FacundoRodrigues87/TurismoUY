<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.sc.entidades.salida"%>
<%@page import="com.sc.datatypes.dataSalida"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
    <meta charset="UTF-8">
    <title>Formulario de Creación de Salida</title>
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
        <h2>Formulario de Creación de Salida</h2>
        <form id="salida-form" action="/salida" method="post">
    <input type="hidden" name="action" value="crear">
    
    <label for="nombre">Nombre de la Salida:</label>
    <input type="text" id="nombre" name="nombre" required>
    
    <label for="capacidad">Capacidad:</label>
    <input type="number" id="capacidad" name="capacidad" required>
    
    <label for="fechaAlta">Fecha de Alta:</label>
    <input type="date" id="fechaAlta" name="fechaAlta" required>
    
    <label for="fechaSalida">Fecha de Salida:</label>
    <input type="date" id="fechaSalida" name="fechaSalida" required>
    
    <label for="lugarSalida">Lugar de Salida:</label>
    <input type="text" id="lugarSalida" name="lugarSalida" required>
    
    <label for="nombreActividad">Nombre de la Actividad:</label>
    <input type="text" id="nombreActividad" name="nombreActividad" required>
    
    <button type="submit" id="submit-btn">Crear Salida</button>
</form>

    </div>
<jsp:include page="/Footer.jsp" />
        </body>
    </html>
</f:view>

