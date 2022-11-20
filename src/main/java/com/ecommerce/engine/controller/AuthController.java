package com.ecommerce.engine.controller;

import com.ecommerce.engine.model.entity.User;
import com.ecommerce.engine.model.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("SameReturnValue")
@Controller
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

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

    @GetMapping("register")
    public String getRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("register")
    public String postRegisterPage(@Valid User user, BindingResult result) {
        if (result.hasErrors())
            return "register";

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveAndFlush(user);
        return "redirect:/";
    }


}
