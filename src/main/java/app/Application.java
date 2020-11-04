package app;

import app.controller.BookController;
import app.controller.IndexController;
import app.controller.LoginController;
import app.persistencia.BookDao;
import app.persistencia.UserDao;
import app.util.Filters;
import app.util.Path;
import app.util.ViewUtil;
import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static spark.debug.DebugScreen.enableDebugScreen;

public class Application {

    // Declare dependencies
    public static BookDao bookDao;
    public static UserDao userDao;

    public static void main(String[] args) {

        // Dependencias del modelo o dominio
        bookDao = new BookDao();
        userDao = new UserDao();

        // Configuracion Spark
        port(4567);
        staticFiles.location("/public");
        staticFiles.expireTime(600L);
        enableDebugScreen(); //ver una pantalla con detalle en caso de error

        // Filtro aplicado antes de get y post
        before("*",             Filters.handleLocaleChange);

        // Rutas (path, controller)
        get(Path.Web.INDEX,     IndexController.serveIndexPage);
        get(Path.Web.BOOKS,     BookController.fetchAllBooks);
        get(Path.Web.ONE_BOOK,  BookController.fetchOneBook);
        get(Path.Web.LOGIN,     LoginController.serveLoginPage);
        post(Path.Web.LOGIN,    LoginController.handleLoginPost);
        post(Path.Web.LOGOUT,   LoginController.handleLogoutPost);
        get("*",                ViewUtil.notFound);

        // Filtro aplicado despues de get y post
        after("*",              Filters.addGzipHeader);

    }

}
