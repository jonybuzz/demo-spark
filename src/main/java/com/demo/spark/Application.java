package com.demo.spark;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

public class Application {

    public static void main(String[] args) {
        // Instantiate your dependencies
//        bookDao = new BookDao();
//        userDao = new UserDao();

        // Configure Spark
        port(4567);
        staticFiles.location("/public");
        staticFiles.expireTime(600L);
        enableDebugScreen();

        // Set up before-filters (called before each get/post)
//        before("*", Filters.addTrailingSlashes);

        // Set up routes
        get("/index", IndexController.serveIndexPage);
        get("/books", BookController.fetchAllBooks);
        get("/login", LoginController.serveLoginPage);
        post("/login", LoginController.handleLoginPost);
        post("/logout", LoginController.handleLogoutPost);
        get("*", ViewUtil.notFound);
    }

}
