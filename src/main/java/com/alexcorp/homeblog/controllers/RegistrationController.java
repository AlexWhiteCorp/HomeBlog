package com.alexcorp.homeblog.controllers;

import com.alexcorp.homeblog.domais.Account;
import com.alexcorp.homeblog.domais.Role;
import com.alexcorp.homeblog.domais.User;
import com.alexcorp.homeblog.repositories.UserRepository;
import com.alexcorp.homeblog.rest.RegistrationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.Set;

@Controller
public class RegistrationController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/registration")
    String registration(){
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String registrationProcessing(RegistrationInfo data, Model model) {
        if(data.getEmail() == "" || data.getPassword() == "" || data.getNickname() == ""){
            model.addAttribute("error", "Confirm All fields, please!");
            return "registration";
        }
        if(userRepository.findByEmail(data.getEmail()) != null){
            model.addAttribute("error", "Account already exists!");
            return "registration";
        }
        if(!data.getPassword().equals(data.getPassword2())){
            model.addAttribute("error", "Passwords don\'t match!");
            return "registration";
        }
        Set<Role> roles = new HashSet<Role>();
        roles.add(Role.ADMIN);
        User user = new User(data.getEmail(), data.getPassword(), "active", roles);
        user.setAccount(new Account(data.getNickname()));
        userRepository.save(user);
        model.addAttribute("email", data.getEmail());
        return "redirect:/login";
    }
}
