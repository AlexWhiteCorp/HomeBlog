package com.alexcorp.homeblog.controllers;

import com.alexcorp.homeblog.repositories.CommentRepository;
import com.alexcorp.homeblog.rest.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
//@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    CommentRepository commentRepository;

    @Value("${posts.path}")
    private String postsPath;

    private static Map<String, Integer> topics;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");


    public AdminController(){
        topics = new HashMap<String, Integer>();
        topics.put("plants", 1);
        topics.put("school", 2);
    }

    @GetMapping("/adminbench")
    public String admin(){
        return "admin";
    }

    @RequestMapping(value = "/adminbench/posts/add", method=RequestMethod.GET)
    public @ResponseBody
    Message addPost(@RequestParam("id") String id,
                    @RequestParam("topic") String topic,
                    @RequestParam("title") String title,
                    @RequestParam("tags") String tags,
                    @RequestParam("preview") String preview,
                    @RequestParam("content") String content) {
        try {
            File json = new File(postsPath + topic + "/" + id + ".json");
            if(json.exists()){
                return new Message(0, "Post with ID " + id + " already exist");
            }
            saveJSONFile(json, createJsonString(new String[]{
                    "id", id,
                    "topic", topic,
                    "date", dateFormat.format(new Date()),
                    "title", title,
                    "tags", tags,
                    "preview", preview,
                    "content", content}));
            saveJSONFile(new File(postsPath + topic + "/previews" + "/" + id + ".json"), createJsonString(new String[]{
                    "id", id,
                    "topic", topic,
                    "date", dateFormat.format(new Date()),
                    "title", title,
                    "tags", tags,
                    "preview", preview}));

            File listJson = new File(postsPath + topic + "/list.json");
            String posts = readJSONFile(listJson);
            posts = posts.replace("\n}", ",\n  \"" + id + "\" : \"" + title + "\"\n}").replace("{,", "{");
            saveJSONFile(listJson, posts);

            File stateJson = new File(postsPath + "state.json");
            String state = readJSONFile(stateJson);
            state = state.replace(topic + "\" : \"" + (Integer.parseInt(id) - 1), topic + "\" : \"" + id);
            saveJSONFile(stateJson, state);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Message(1, "Post saved success");
    }

    @RequestMapping(value = "/adminbench/posts/change", method=RequestMethod.GET)
    public @ResponseBody
    Message changePost(@RequestParam("id") String id,
                       @RequestParam("topic") String topic,
                       @RequestParam("title") String title,
                       @RequestParam("tags") String tags,
                       @RequestParam("preview") String preview,
                       @RequestParam("content") String content) {
        try {
            File json = new File(postsPath + topic + "/" + id + ".json");
            saveJSONFile(json, createJsonString(new String[]{
                    "id", id,
                    "topic", topic,
                    "date", dateFormat.format(new Date()),
                    "title", title,
                    "tags", tags,
                    "preview", preview,
                    "content", content}));
            saveJSONFile(new File(postsPath + topic + "/previews" + "/" + id + ".json"), createJsonString(new String[]{
                    "id", id,
                    "topic", topic,
                    "date", dateFormat.format(new Date()),
                    "title", title,
                    "tags", tags,
                    "preview", preview}));

            File listJson = new File(postsPath + topic + "/list.json");
            String posts = readJSONFile(listJson);
            int postId = Integer.parseInt(id);
            if(posts.indexOf("\",\n  \"" + (postId + 1)) > 0){
                posts = posts.replace(posts.substring(posts.indexOf("\"" + id + "\"") - 3 , posts.indexOf("\",\n  \"" + (postId + 1)) + 2), "");
            }
            else{
                posts = posts.replace(posts.substring(posts.indexOf("\"" + id + "\"") - 3, posts.indexOf("\"\n}") + 1), "");
            }
            posts = posts.replace("\n}", ",\n  \"" + id + "\" : \"" + title + "\"\n}").replace("{,", "{");
            saveJSONFile(listJson, posts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Message(1, "Post saved success");
    }

    @Transactional
    @RequestMapping(value = "/adminbench/posts/delete", method=RequestMethod.GET)
    public @ResponseBody
    Message deletePost(@RequestParam("id") String id,
                       @RequestParam("topic") String topic) {
        try {
            int postId = Integer.parseInt(id);
            new File(postsPath + topic + "/" + id + ".json").delete();
            new File(postsPath + topic + "/previews" + "/" + id + ".json").delete();

            File listJson = new File(postsPath + topic + "/list.json");
            String posts = readJSONFile(listJson);
            if(posts.indexOf("\",\n  \"" + (postId + 1)) > 0){
                posts = posts.replace(posts.substring(posts.indexOf("\"" + id + "\"") - 3 , posts.indexOf("\",\n  \"" + (postId + 1)) + 2), "");
            }
            else{
                posts = posts.replace(posts.substring(posts.indexOf("\"" + id + "\"") - 4, posts.indexOf("\"\n}") + 1), "");
            }
            if(posts.indexOf("{") < 0) posts = "{" + posts;
            saveJSONFile(listJson, posts);

            File stateJson = new File(postsPath + "state.json");
            String state = readJSONFile(stateJson);
            state = state.replace(topic + "\" : \"" + id, topic + "\" : \"" + (postId - 1));
            saveJSONFile(stateJson, state);

            commentRepository.deleteAllByTopicAndArticle(topics.get(topic), postId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Message(1, "Post deleted success");
    }

    private String createJsonString(String [] args){
        String json = "{\n";
        for(int i = 0; i < args.length; i++){
            json += "  \"" + args[i] + "\" : \"" + args[i + 1];
            i++;
            if(i != args.length - 1) json += "\",\n";
            else json += "\"\n";
        }
        json += "}";
        return json;
    }

    private void saveJSONFile(File json, String text) throws IOException {
        FileWriter fileWriter = new FileWriter(json);
        fileWriter.write(text);
        fileWriter.flush();
        fileWriter.close();
    }

    private String readJSONFile(File json) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(json));
        String text = "";
        while(reader.ready()){
            text += reader.readLine() + "\n";
        }
        return text;
    }
}

/*
 return new ClassPathResource("my-json-file.json");
*/
