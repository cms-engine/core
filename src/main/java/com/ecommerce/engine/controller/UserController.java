package com.ecommerce.engine.controller;

import com.ecommerce.engine.exception.UserNotFoundException;
import com.ecommerce.engine.exception.UserPermissionException;
import com.ecommerce.engine.model.entity.Role;
import com.ecommerce.engine.model.entity.User;
import com.ecommerce.engine.model.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("all")
    public String getAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/all";
    }

    private void checkPermission(String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.getAuthorities().contains(Role.ADMIN) && !id.equals(authentication.getName()))
            throw new UserPermissionException();
    }

    @GetMapping("edit/{id}")
    public String getEdit(@PathVariable String id, Model model) {
        checkPermission(id);

        User user = userService.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        model.addAttribute("user", user);

        return "users/edit";
    }

    @PostMapping("edit/{id}")
    public String postEdit(@PathVariable String id, @Valid User user, BindingResult result,
                           @AuthenticationPrincipal org.springframework.security.core.userdetails.User userAuth) {
        checkPermission(id);

        if (result.hasErrors())
            return "users/edit";

        boolean needLogout = false;

        Optional<User> foundInDB = userService.findById(id);
        if (foundInDB.isPresent()) {
            User userInDb = foundInDB.get();
            needLogout = user.getId().equals(userAuth.getUsername()) && (userInDb.getRole() != user.getRole());

            user = userInDb.updateAllowed(user);
        }

        userService.saveAndFlush(user);

        if (needLogout)
            return "redirect:/logout";
        else
            return "redirect:/users/edit/" + id;
    }

    @PostMapping("delete/{id}")
    public String postDelete(@PathVariable String id,
                             @AuthenticationPrincipal org.springframework.security.core.userdetails.User userAuth) {
        checkPermission(id);

        userService.deleteById(id);

        if (id.equals(userAuth.getUsername()))
            return "redirect:/logout";
        else
            return "redirect:/users/all";
    }

}
