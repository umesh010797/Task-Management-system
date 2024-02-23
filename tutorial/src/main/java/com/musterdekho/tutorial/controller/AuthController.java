package com.musterdekho.tutorial.controller;

import com.musterdekho.tutorial.dto.UserDto;
import com.musterdekho.tutorial.entity.Role;
import com.musterdekho.tutorial.entity.User;
import com.musterdekho.tutorial.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {
    @Autowired

    private UserService userService;



    @GetMapping("index")
    public String home(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get user email from authentication object
        String userEmail = authentication.getName();
        User user = userService.findByEmail(userEmail);
        model.addAttribute("userId", user.getName());
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        System.out.println("inside @GetMapping(\"/login\")");
        return "login";
    }

    // handler method to handle user registration request
    @GetMapping("register")
    public String showRegistrationForm(Model model){

        UserDto user = new UserDto();
        model.addAttribute("user", user);
        System.out.println("inside @GetMapping(\"register\")");
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        System.out.println("inside line no 59");
        return "redirect:/register?success";
    }


    @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        System.out.println("inside line no 67");
        return "users";
    }
    @GetMapping("/profile")
    public String userProfile(Model model) {
        // Get authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get user email from authentication object
        String userEmail = authentication.getName();

        // Get user details from the service layer
        User user = userService.findByEmail(userEmail);
        List<Role> role=  user.getRoles();
        // Add user details to the model
        model.addAttribute("userId", user.getId());
        model.addAttribute("UserId",role.get(0).getUsers());
        model.addAttribute("userRoles", user.getRoles());
        System.out.println(model.getAttribute("userId"));
        return "profile";
    }
}
