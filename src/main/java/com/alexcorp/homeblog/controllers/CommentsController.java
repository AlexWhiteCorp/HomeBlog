package com.alexcorp.homeblog.controllers;

import com.alexcorp.homeblog.domais.Comment;
import com.alexcorp.homeblog.domais.User;
import com.alexcorp.homeblog.repositories.CommentRepository;
import com.alexcorp.homeblog.rest.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class CommentsController {

    private static Map<String, Integer> topics;

    public CommentsController(){
        topics = new HashMap<String, Integer>();
        topics.put("plants", 1);
        topics.put("school", 2);
    }

    @Autowired
    CommentRepository commentRepository;

    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");


    @RequestMapping("/comments/add")
    public @ResponseBody Message addComment(@AuthenticationPrincipal User user,
                                            @RequestParam("topic") String topic,
                                            @RequestParam("article") String article,
                                            @RequestParam("text") String text){
        if(user != null){
            String date = dateFormat.format(new Date());
            Comment comment = new Comment(topics.get(topic), Integer.parseInt(article), date, text, user.getAccount());
            commentRepository.save(comment);
            return new Message(1, date);
        }
        else{
            return new Message(0, "User is not authorized");
        }
    }

    @RequestMapping("/comments/load")
    public @ResponseBody
    List<Map<String, String>> loadComments(@RequestParam("topic") String topic,
                                           @RequestParam("article") String article,
                                           @RequestParam("page") String page){
        List<Map<String, String>> result = new ArrayList<>();

        Set<Comment> comments = commentRepository.findAllByTopicAndArticle(topics.get(topic), Integer.parseInt(article));
        for(Comment comment : comments){
            result.add(new HashMap<String, String>(){{put("nickname", comment.getAccount().getNickname());
                                                      put("icon", comment.getAccount().getIcon());
                                                      put("date", comment.getDate());
                                                      put("text", comment.getText());
            }});
        }
        return result;
    }
}
