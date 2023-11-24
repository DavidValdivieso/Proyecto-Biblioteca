/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejemplo.biblio.servicios;

import com.ejemplo.biblio.entidades.Autor;
import com.ejemplo.biblio.entidades.Editorial;
import com.ejemplo.biblio.entidades.Libro;
import com.ejemplo.biblio.excepciones.MiException;
import com.ejemplo.biblio.repositorio.AutorRepositorio;
import com.ejemplo.biblio.repositorio.EditorialRepositorio;
import com.ejemplo.biblio.repositorio.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {

    @Autowired // indica una instancia unica y global 

    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    /* Esto indica que si el metodo se ejecuta sin errores se realiza un 
                    "commit" para aplicar los cambios.*/

    public void crearLibro(Long isbn, String titulo, Integer Ejemplares, String idAutor, String idEditorial) throws MiException {

        validar(isbn, titulo, Ejemplares, idAutor, idEditorial);
        Autor autor = autorRepositorio.findById(idAutor).get();

        Editorial editorial = editorialRepositorio.findById(idEditorial).get();

        Libro libro = new Libro();

        libro.setIsbn(isbn);

        libro.setTitulo(titulo);
        libro.setEjemplares(Ejemplares);
        libro.setAlta(new Date());

        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);

    }

    //Listar los libros
    public List<Libro> listarLibros() {

        List<Libro> libros = new ArrayList();

        libros = libroRepositorio.findAll();

        return libros;
    }

    public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MiException {
        //Hay que validar que todos los parametros sean correctos con un metodo.

        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        //El optional es un contenedor que puede o no contener un valor NO nulo
        Optional<Libro> respuesta = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        if (respuestaAutor.isPresent()) {

            autor = respuestaAutor.get();

        }

        if (respuestaEditorial.isPresent()) {
            editorial = respuestaEditorial.get();

        }

        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(ejemplares);
            libroRepositorio.save(libro);
        }

    }
    
      public Libro getOne(long isbn){
          
        return libroRepositorio.getOne(isbn);
    }
    

    private void validar(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {

        if (isbn == null) {
            throw new MiException("el isbn no puede ser nulo");
        }

        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("el titulo no puede estar vacio o ser nulo");
        }

        if (ejemplares == null) {
            throw new MiException("ejemplares no puede ser nulo");
        }

        if (idAutor.isEmpty() || idAutor == null) {
            throw new MiException("el id del autor no puede estar vacio o ser nulo");
        }
        if (idEditorial.isEmpty() || idEditorial == null) {
            throw new MiException("el id de la editorial no puede estar vacio o ser nulo");
        }

    }

}
