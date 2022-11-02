package edu.escuelaing.arep.microservices;

import java.util.ArrayList;
import static spark.Spark.*;
public class MicroServices {

    public static void main(String... args){
        staticFiles.location("/webapp");
        port(getPort());
        
        post("/tweet", (req,res) -> {
            String post = req.queryParams("post");
            return addPost(post);
        });
        
        get("/stream", (req,res) -> {
            return "holsa get sirve";
        });
    }
    
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
    
    private static ArrayList addPost(String post){
        ArrayList<String> failLarge = new ArrayList<>();
        failLarge.add("El post debe tener máximo 140 caracteres");
        if(post.length()<= 140){
            return connectMongo.insertar(post);
        }
        else{
            return failLarge;
        }
    }
}
