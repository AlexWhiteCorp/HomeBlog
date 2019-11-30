package com.alexcorp.homeblog.controllers;

import com.alexcorp.homeblog.domais.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    /*@Value("${spring.profiles.active}")
    private String profile;*/

    @GetMapping("/")
    String main(@AuthenticationPrincipal User user, HttpServletRequest request, Model model){
        try {
            //SxGeoResult result = SxRestClient.create(null).get("178.213.174.39");
            //System.out.println(result.country.name.en());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("isDevMode", true);
        return "home";
    }

   // @GetMapping("**/**.chunk.js")
   /* String redirectToNode(HttpServletRequest request){
        return "redirect:http://localhost:8000/" +
                request.getRequestURL().toString().split("/")[request.getRequestURL().toString().split("/").length - 1];
    }*/

    //@GetMapping("**/**.bundle.js")
    /*String redirectToNode2(HttpServletRequest request){
        return "redirect:http://localhost:8000/" +
                request.getRequestURL().toString().split("/")[request.getRequestURL().toString().split("/").length - 1];
    }*/
}
