/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejemplo.biblio.servicios;

import com.ejemplo.biblio.entidades.Usuario;
import com.ejemplo.biblio.enumeraciones.Rol;
import com.ejemplo.biblio.excepciones.MiException;
import com.ejemplo.biblio.repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

/**
 *
 * @author david
 */
@Service
public class UsuarioServicio implements UserDetailsService {
    //implementamos el userDetail para validar los usuarios
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio ;
    
    @Transactional
    public void registrar(String nombre, String email, String password, String password2) throws MiException{
        validar(nombre, email, password, password2);
        
        Usuario usuario = new Usuario();
        
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        
        usuario.setRol(Rol.USER);
        
        usuarioRepositorio.save(usuario);
        
        
    }
    
    
    private void validar(String nombre, String email, String password, String password2) throws MiException{
        
         if (nombre == null || nombre.isEmpty()) 
             throw new MiException("el nombre no puede estar vacio"); 
         
          if (email == null || email.isEmpty()) 
             throw new MiException("el email no puede estar vacio");
          
           if (password == null || password.isEmpty() || password.length()<=5) 
             throw new MiException("la clave no puede estar vacia, y ser mayor de 5 digitos  ");
            if (!password.equals(password2)) 
             throw new MiException("la clave debe ser igual a la ingresada anteriormente");
        
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        //vamos  a buscar un usuario de nustro dominio
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        
        if (usuario != null) {
            //luego lo vamos a transformar en un usuario de dominio de Springsecurity.
            
            List<GrantedAuthority> permisos = new ArrayList();
            
            GrantedAuthority p =  new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());// "ROLE_USER"
            permisos.add(p);
            
            //ahora vamos a guardar en la sesion,el usuario logueado
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            
            //una vez que traemos la solicitud se guarda en un objeto de la interface "HttpSesion"
            HttpSession session = attr.getRequest().getSession(true);
            //y en los datos de esta session vamos a setear lo siguiente:
            session.setAttribute("usuariosession", usuario);
            
                    
            
           
            return  new User(usuario.getEmail(), usuario.getPassword(), permisos);
        }else{
            return null; 
        }
            
       
    }
    
}
