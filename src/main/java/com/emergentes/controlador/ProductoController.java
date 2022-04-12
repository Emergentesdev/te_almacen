package com.emergentes.controlador;

import com.emergentes.modelo.Categoria;
import com.emergentes.modelo.Producto;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name="ProductoController", urlPatterns={"/ProductoController"})
public class ProductoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String op = (request.getParameter("op").equals("")) ? "listar" : request.getParameter("op");
        Producto prod = new Producto();
        int id;
        
        HttpSession ses = request.getSession();
        List<Producto> lista =  (List<Producto>) ses.getAttribute("almacen");
        List<Categoria> categorias = (List<Categoria>) ses.getAttribute("cates");
        
        switch(op){
            case "nuevo":
                if (lista.size() == 0){
                    prod.setId(1);
                }
                else{
                    prod.setId(lista.get(lista.size()-1).getId() + 1);
                }
                request.setAttribute("categorias",categorias);
                request.setAttribute("prod", prod);
                request.setAttribute("tipo", "new");
                request.getRequestDispatcher("producto-edit.jsp").forward(request, response);
                break;
            case "editar":
                id = Integer.parseInt(request.getParameter("id"));
                prod = lista.get(posNodoProducto(id,request));
                request.setAttribute("categorias",categorias);
                request.setAttribute("prod", prod);
                request.setAttribute("tipo", "edit");
                request.getRequestDispatcher("producto-edit.jsp").forward(request, response);                
                break;
            case "eliminar":
                id = Integer.parseInt(request.getParameter("id"));
                int pos = posNodoProducto(id,request);
                lista.remove(pos);
                response.sendRedirect("productos.jsp");
                break;
            case "listar":
                request.setAttribute("almacen", lista);
                response.sendRedirect("productos.jsp");
                break;
        }
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String descripcion = request.getParameter("descripcion");
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        float precio = Float.parseFloat(request.getParameter("precio"));
        int idcat = Integer.parseInt(request.getParameter("idcat"));
        Categoria cate = nodoCategoria(idcat, request);
        
        String tipo = request.getParameter("tipo");
        
        Producto p = new Producto();
        p.setId(id);
        p.setDescripcion(descripcion);
        p.setCantidad(cantidad);
        p.setPrecio(precio);
        p.setCate(cate);
        
        HttpSession ses = request.getSession();
        List<Producto> almacen = (List<Producto>)ses.getAttribute("almacen");
        
        if (tipo.equals("new")){
            almacen.add(p);
        }
        else{
            almacen.set(posNodoProducto(id,request), p);
        }
        response.sendRedirect("productos.jsp");
    }
    
    public int posNodoProducto(int id, HttpServletRequest request){
        int index = -1;
        
        HttpSession ses = request.getSession();
        List<Producto> lista =  (List<Producto>) ses.getAttribute("almacen");
        for  (int i = 0; i < lista.size(); i++){
            if (lista.get(i).getId() == id){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public int posNodoCategoria(int id, HttpServletRequest request){
        int index = -1;
        
        HttpSession ses = request.getSession();
        List<Categoria> lista =  (List<Categoria>) ses.getAttribute("cates");
        for  (int i = 0; i < lista.size(); i++){
            if (lista.get(i).getId() == id){
                index = i;
                break;
            }
        }
        return index;
    }   
    
    public Categoria nodoCategoria(int id, HttpServletRequest request){
        Categoria aux = new Categoria();
        HttpSession ses = request.getSession();
        List<Categoria> lista = (List<Categoria>) ses.getAttribute("cates");
        for (Categoria obj : lista){
            if (obj.getId() == id){
                aux = obj;
            }
        }
        return aux;
          
    }
}
