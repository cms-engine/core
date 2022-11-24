package com.ecommerce.engine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@SuppressWarnings("SameReturnValue")
@Controller
public class AuthController {

    @GetMapping(value = "/")
    public String welcome() {
        return "redirect:/users/all";
    }

    @GetMapping("login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("login-error")
    public String getLoginErrorPage(Model model) {
        model.addAttribute("errorList", List.of("Username or password is wrong!"));
        return "login";
    }

}
