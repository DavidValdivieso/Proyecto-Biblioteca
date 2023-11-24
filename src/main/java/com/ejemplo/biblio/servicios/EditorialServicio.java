/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejemplo.biblio.servicios;

import com.ejemplo.biblio.entidades.Editorial;
import com.ejemplo.biblio.excepciones.MiException;
import com.ejemplo.biblio.repositorio.EditorialRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author david
 */
@Service
public class EditorialServicio {
    @Autowired  
    EditorialRepositorio editorialRepositorio;
    
    
    @Transactional 
    public void crearEditorial(String nombre) throws MiException{
         validar(nombre);
         Editorial editorial = new Editorial();
         editorial.setNombre(nombre);
         
         editorialRepositorio.save(editorial); 
    
    }
     public List<Editorial> listarEditoriales(){
        List<Editorial> editoriales = new ArrayList();
        
        editoriales = editorialRepositorio.findAll();
        return editoriales;
    }
     
     public void modificarEditorial (String id,String nombre) throws MiException{
          validar(nombre);
        if (id.isEmpty()) {
            throw new MiException("el id no puede esta vacio");
        }
            Optional <Editorial> respuesta = editorialRepositorio.findById(id);
            
            
            if (respuesta.isPresent()) {
                
                Editorial editorial = respuesta.get();
                editorial.setNombre(nombre);
                
                editorialRepositorio.save(editorial);
             
         }
     }
     
       public Editorial getOne(String id){
        return editorialRepositorio.getOne(id);
    }
    
     
     private void validar(String nombre) throws MiException{
         
          if (nombre == null || nombre.isEmpty() ) {
             throw new MiException("el nombre no puede estar vacio");    
         }
     }
    
}
