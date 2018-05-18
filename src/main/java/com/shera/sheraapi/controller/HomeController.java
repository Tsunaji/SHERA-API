/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shera.sheraapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jirasak_ka
 */
@RestController
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "Welcome to Shera API";
    }
}
