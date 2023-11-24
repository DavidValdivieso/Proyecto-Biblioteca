/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejemplo.biblio.controladores;

import com.ejemplo.biblio.entidades.Autor;
import com.ejemplo.biblio.entidades.Editorial;
import com.ejemplo.biblio.entidades.Libro;
import com.ejemplo.biblio.excepciones.MiException;
import com.ejemplo.biblio.servicios.AutorServicio;
import com.ejemplo.biblio.servicios.EditorialServicio;
import com.ejemplo.biblio.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author david
 */
@Controller
@RequestMapping("/libro")
public class LibroControlador {

    //------- Instancias unicas -------------//
    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;

    //------------------------------------//
    //Aca cuando entramos al "/registrar hacemos que se ingrese a este metodo:
    //Tambien inyectamos en "libro_form.html" mediante "ModelMap", la lista de los autores y Editoriales.
    @GetMapping("/registrar") //localhost:8080/libro/regitrar
    public String registrar(ModelMap modelo) {

        //Vamos a generar una lista para almacenar los autores y las editoriales:
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();

        //Con el modelmap "modelo" vamos a mostar las editoriales y los autores
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_form.html";
    }

    @PostMapping("/registro") //localhost:8080/libro/registro
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo, @RequestParam(required = false)
            Integer ejemplares, @RequestParam String idAutor, @RequestParam String idEditorial, ModelMap modelo) {

        //*"(required = false)": para que no tire error en la ejecucion y lo tome el catch.
        //* "ModelMap": los modelos sirven para insertar toda la info por pantalla, en este caso 
        //vamos a mostrar el error
        try {

            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial); // si todo sale bien se persiste
            modelo.put("Exito", "El libro fue cargado correctamente"); // llave, valor

        } catch (MiException ex) {
            //en el caso de que ocurra un error vamos a copiar lo que inyectamos en el metodo "registrar".
            //Vamos a generar una lista para almacenar los autores y las editoriales:
            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditoriales();

            //Con el modelmap "modelo" vamos a mostar/inyectar las editoriales y los autores
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);

            modelo.put("Error", ex.getMessage());

            return "libro_form.html";
        }

        return "index.html";
    }
    
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        
        List<Libro> libros = libroServicio.listarLibros();
        modelo.addAttribute("libros", libros);
    
        return "libro_list.html";
    }
            
            
            

}
