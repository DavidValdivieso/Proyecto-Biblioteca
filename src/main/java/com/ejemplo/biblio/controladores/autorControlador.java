/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejemplo.biblio.controladores;

import com.ejemplo.biblio.entidades.Autor;
import com.ejemplo.biblio.excepciones.MiException;
import com.ejemplo.biblio.servicios.AutorServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author david
 */
@Controller
@RequestMapping("/autor") //localhost:8080/autor
public class autorControlador {

    @Autowired
    private AutorServicio autorServicio;
    
    @GetMapping("/registrar") //localhost:8080/autor/registrar, 
    //"@GetMapping" capta este enlance para ingresar al metodo
    public String registrar(){
        return "autor_form.html"; // y reenderizar "autor_form"
    }
    
    @PostMapping("/registro") //localhost:8080/autor/registro
    //luego de apretar "subir" este metodo entra en accion
    public String registro(@RequestParam  String nombre, ModelMap modelo){ 
        // este atributo se tiene que llamar igual que el atrubuto "name" del input del html
        // este es un atributo requerido
        
         //* "ModelMap": los modelos sirven para insertar toda la info por pantalla, en este caso 
        //vamos a mostrar el error *
         try {
        autorServicio.crearAutor(nombre);
            modelo.put("Exito", "El autor fue cargado corectamente");
        
        } catch (MiException ex) {
           modelo.put("Error", ex.getMessage()); // si male sal se muestra el error 
            return "autor_form.html";
        }
               
        return "autor_form.html";
    }
    
    @GetMapping("/lista") //localhost8080/autor/lista
    public String listar(ModelMap modelo){
        List<Autor> autores = autorServicio.listarAutores();
        modelo.addAttribute("autores",autores); //vamos a inyectar una lista de autores en el html "autor_list.html"
        
        return "autor_list.html";       
    }
    
    
    
    
    @GetMapping ("/modificar/{id}") //path variable: indica que VIAJÓ un recurso en el enlace
    
        public String modificar (@PathVariable String id, ModelMap modelo){
            modelo.put("autor", autorServicio.getOne(id));//vamos a inyectar en el html mediante la llave "autor", el autor a modificar
            return "autor_modificar.html";
        }
        
        
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo){
    
        try {
            autorServicio.modificarAutor(nombre, id);
            modelo.put("Exito", "El autor fue Modificado corectamente");
            
            return "redirect:../lista";
        } catch (MiException ex) {
           modelo.put("Error", ex.getMessage());
           return "autor_modificar.html";
        }
    }

}




/*
copiado de zoom

@Controller
@RequestMapping("/autor") //localhost:8080/autor
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;
    
    @GetMapping("/registrar") //localhost:8080/autor/registrar
    public String registrar(){
        return "autor_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam  String nombre){
        
        try {
            autorServicio.crearAutor(nombre);
        } catch (MiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
             return "autor_form.html";
        }
        
        return "autor_form.html";
    }
}
*/