/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejemplo.biblio.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author david
 * Aca en vez de usar el @Preauthorize(roles)
 * lo que tendriamos que hacer es pre autorizar toda la clase.
 * para eso vamos a ir a SeguridadWeb y agregar un "antMatcher("/admin/*").hasRole("ADMIN")"
 * 
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {
    
    @GetMapping("/dashboard")
    public String panelAdministrativo(){
        
        return "panel.html";
        
    }
}
