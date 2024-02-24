package org.example;

import org.example.Controller.Controller;
import org.example.DAO.BookDAO;
import org.example.DAO.SellerDAO;
import org.example.Service.BookService;
import org.example.Service.SellerService;
import org.example.Util.ConnectionSingleton;
import io.javalin.Javalin;

import java.sql.Connection;

    public class Application {
        public static void main(String[] args) {

            Connection conn = ConnectionSingleton.getConnection();
            SellerDAO sellerDAO = new SellerDAO(conn);
            BookDAO bookDAO = new BookDAO(conn);
            BookService bookService = new BookService(bookDAO);
            SellerService sellerService = new SellerService(sellerDAO);
            Controller controller = new Controller(bookService, sellerService);

            Javalin api = controller.getAPI();

            api.start(9004);


        }
    }
