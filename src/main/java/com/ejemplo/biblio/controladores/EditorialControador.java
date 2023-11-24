
package com.ejemplo.biblio.controladores;

import com.ejemplo.biblio.entidades.Editorial;
import com.ejemplo.biblio.excepciones.MiException;
import com.ejemplo.biblio.servicios.EditorialServicio;
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
@RequestMapping("/editorial")
public class EditorialControador {
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/registrar") // localhost:8080/editorial/registrar
    public String registrar(){
        
        return "editorial_form.html";
    }
    
    @PostMapping("/registro")
    
    public String registro(@RequestParam String nombre, ModelMap modelo) {
        
        
        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("Exito", "La editorial fue creada correctamente");
        } catch (MiException ex) {
            modelo.put("Error", ex.getMessage());
            return "editorial_form.html";
        }  
        return "editorial_form.html";
    }
    
    @GetMapping("/lista") //localhost:8080/editorial/lista
    public String listar(ModelMap modelo){
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        
        
        return "editorial_list.html";
    }   
    
    @GetMapping ("/modificar/{id}")//path variable: indica que  viajó un recurso en el enlace
    public String modificar(@PathVariable String id, ModelMap modelo){
        //vamos a traer la id para buscar la editorial a inyectar
        
        modelo.put("editorial", editorialServicio.getOne(id));//vamos a inyectar en el html mediante la llave "editorial", la editorial a modificar
      
        return "editorial_modificar.html";
    }
    
 @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo){
    
        try {
            editorialServicio.modificarEditorial(id, nombre);
            modelo.put("Exito", "La editorial fue modificada correctamente");
            return "redirect:../lista";
        } catch (MiException ex) {
           modelo.put("error", ex.getMessage());
           return "editorial_modificar.html";
        }
    }
    
    
}
