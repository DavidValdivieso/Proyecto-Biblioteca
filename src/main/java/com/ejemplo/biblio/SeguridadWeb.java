    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejemplo.biblio;

import com.ejemplo.biblio.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author david
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {
    
    @Autowired
     public UsuarioServicio usuarioServicio;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
    //con el parametro , vamos a configurar el manejador de segurirad,
    // y se le va a indicar cual es el servicio para autenticar el usuario.
    
    auth.userDetailsService(usuarioServicio) // ojo que aca no cerramos la linea todavia
    
    //-------- Ahora vamos a codificar la contraseña --------------//
    .passwordEncoder(new BCryptPasswordEncoder());
    //vamos a codificar la contraseña en el servicio (UsuarioServicio) antes de persistirlo tamben.
        
    
    
}
    
    @Override
    protected void configure (HttpSecurity http) throws Exception{
        
        
        
        http
                .authorizeRequests()
                    .antMatchers("/admin/*").hasRole("ADMIN")   
                    .antMatchers("/css/*", "/js/*", "/img/*", "/**")
                    .permitAll()
        //Aca estamos autorizando determinados parametros, para acceder basicamente a todos
                .and().formLogin()
                    .loginPage("/login") // aca indicamos el formulario de login
                    .loginProcessingUrl("/logincheck") //este tiene que coincidir con el action del form.
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/inicio")
                    .permitAll()
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .permitAll()
                .and().csrf()
                     .disable();
                
             
                
    }
    
}
