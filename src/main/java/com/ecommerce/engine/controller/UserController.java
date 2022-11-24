package com.ecommerce.engine.controller;

import com.ecommerce.engine.exception.UserNotFoundException;
import com.ecommerce.engine.model.entity.User;
import com.ecommerce.engine.model.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping("all")
    public String getAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/all";
    }

    @PreAuthorize("hasAuthority('USER_CREATE')")
    @GetMapping("create")
    public String getCreate(Model model) {
        model.addAttribute("user", new User());

        return "users/edit";
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @GetMapping("edit/{id}")
    public String getEdit(@PathVariable String id, Model model) {
        User user = userService.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        model.addAttribute("user", user);

        return "users/edit";
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PostMapping("edit/{id}")
    public String postEdit(@PathVariable String id, @Valid User user, BindingResult result) {
        if (result.hasErrors())
            return "users/edit";

        Optional<User> foundInDB = userService.findById(id);
        if (foundInDB.isPresent()) {
            User userInDb = foundInDB.get();
            user = userInDb.updateAllowed(user);
        }

        userService.saveAndFlush(user);

        return "redirect:/users/edit/" + id;
    }

    @PreAuthorize("hasAuthority('USER_DELETE')")
    @PostMapping("delete/{id}")
    public String postDelete(@PathVariable String id,
                             @AuthenticationPrincipal org.springframework.security.core.userdetails.User userAuth) {
        userService.deleteById(id);

        if (id.equals(userAuth.getUsername()))
            return "redirect:/logout";
        else
            return "redirect:/users/all";
    }

}
