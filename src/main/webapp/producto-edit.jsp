<%@page import="java.util.List"%>
<%@page import="com.emergentes.modelo.Categoria"%>
<%@page import="com.emergentes.modelo.Producto"%>
<%
    Producto prod = (Producto) request.getAttribute("prod");
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
    String tipo = (String) request.getAttribute("tipo");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1><%= (tipo == "new") ? "Nuevo ":"Editar " %>Producto</h1>
        <form action="ProductoController" method="post">
            <input type="hidden" name="tipo" value="<%= tipo %>">
            <table>
                <tr>
                    <td>Id</td>
                    <td><input type="text" name="id" value="<%= prod.getId() %>" readonly ></td>
                </tr>
                <tr>
                    <td>Producto</td>
                    <td><input type="text" name="descripcion" value="<%= prod.getDescripcion() %>" ></td>
                </tr>
                <tr>
                    <td>Cantidad</td>
                    <td><input type="text" name="cantidad" value="<%= prod.getCantidad() %>" ></td>
                </tr>
                <tr>
                    <td>Precio</td>
                    <td><input type="text" name="precio" value="<%= prod.getPrecio() %>" ></td>
                </tr>
                <tr>
                    <td>Categoria</td>
                    <td><select name="idcat">
                    <%
                        for (Categoria c : categorias){
                    %>
                    <option value="<%= c.getId() %>" <%= (c.getId() == prod.getCate().getId()) ? "selected" : "" %>>
                        <%= c.getDescripcion() %>
                    </option>
                    <%
                        }
                    %>
                </select></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Procesar"></td>
                </tr>            
            </table>
        </form>     
    </body>
</html>
