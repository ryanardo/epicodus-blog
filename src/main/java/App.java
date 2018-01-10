import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import com.sun.org.apache.xpath.internal.operations.Mod;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

import models.Post;
public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");

        get("/posts/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "newpost-form.hbs");
        }, new HandlebarsTemplateEngine());

        get("/posts/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Post.clearAllPosts();
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        get("/posts/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfPostToFind = Integer.parseInt(req.params("id")); //pull id - must match route segment
            Post foundPost = Post.findById(idOfPostToFind); //use it to find post
            model.put("post", foundPost); //add it to model for template to display
            return new ModelAndView(model, "post-detail.hbs"); //individual post page.
        }, new HandlebarsTemplateEngine());


        post("/posts/new", (request, response) -> { //URL to make new post on POST route
            Map<String, Object> model = new HashMap<>();

            String content = request.queryParams("content");
            Post newPost = new Post(content);
            model.put("post", newPost);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            ArrayList<Post> posts = Post.getAll();
            model.put("posts", posts);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/posts/:id/update", (req, res) -> {
           Map<String, Object> model = new HashMap<>();
           int idOfPostToEdit = Integer.parseInt(req.params("id"));
           Post editPost = Post.findById(idOfPostToEdit);
           model.put("editPost", editPost);
           return new ModelAndView(model, "newpost-form.hbs");
        }, new HandlebarsTemplateEngine());

        get("/post/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfPostToDelete = Integer.parseInt(req.params("id"));
            Post deletePost = Post.findById(idOfPostToDelete);
            deletePost.deletePost();
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());


    }
}