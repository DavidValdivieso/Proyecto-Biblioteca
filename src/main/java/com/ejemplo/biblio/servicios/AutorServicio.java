/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejemplo.biblio.servicios;

import com.ejemplo.biblio.entidades.Autor;
import com.ejemplo.biblio.excepciones.MiException;
import com.ejemplo.biblio.repositorio.AutorRepositorio;
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
public class AutorServicio {
     @Autowired // esto seria una instancia unica del repo "AutorRepositorio"
    AutorRepositorio autorRepositorio; 
    
     @Transactional
    public void crearAutor (String nombre) throws MiException {
         validar(nombre);
        Autor autor = new Autor ();
        autor.setNombre(nombre);
        
        autorRepositorio.save(autor);
    
}
    public List<Autor> listarAutores(){
        List<Autor> autores = new ArrayList();
        
        autores = autorRepositorio.findAll();
        return autores;
    }
    
public void modificarAutor (String nombre, String id) throws MiException{
        validar(nombre);
        if (id.isEmpty()) {
            throw new MiException("el id no puede esta vacio");
        }
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            
            Autor autor = respuesta.get();
           
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
            
        }else{
            throw new MiException("Error respuesta no esta presente");
        }
        
        
    }
    
    public Autor getOne(String id){
        return autorRepositorio.getOne(id);
    }
    
    private void   validar(String nombre) throws MiException{
         if (nombre == null || nombre.isEmpty() ) {
             throw new MiException("el nombre no puede estar vacio");
    
         }
    }
  
 
}
/*
thymlef: motor de plantillas, permite añadir atributos dinamicos a etiquetas
*/