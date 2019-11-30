package com.alexcorp.homeblog.controllers;

import com.alexcorp.homeblog.domais.User;
import com.alexcorp.homeblog.rest.UserInfo;
import com.alexcorp.homeblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String loginProcessing(UserInfo userInfo, HttpSession session, Model model) {
        String email = userInfo.getEmail();
        String password = userInfo.getPassword();
        UserDetails user = userService.loadUserByUsername(email);
        if(user == null){
            model.addAttribute("error", "Account doesn't exist!");
            return "/login";
        }
        if(!user.getPassword().equals(password)){
            model.addAttribute("error", "Incorrect password!");
            return "/login";
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        session.setAttribute("auth", true);
        session.setAttribute("nickname", ((User)user).getAccount().getNickname());
        session.setAttribute("usericon", ((User)user).getAccount().getIcon());
        return "redirect:/";
    }
}
