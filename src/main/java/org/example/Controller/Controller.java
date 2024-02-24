package org.example.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import org.example.Exceptions.BookNotFoundException;
import org.example.Exceptions.SellerNotFoundException;
import org.example.Model.Book;
import org.example.Model.Seller;
import org.example.Service.BookService;
import org.example.Service.SellerService;

import java.sql.SQLException;
import java.util.List;

public class Controller {

    BookService bookService;
    SellerService sellerService;

    public Controller(BookService bookService, SellerService sellerService) {
        this.bookService = bookService;
        this.sellerService = sellerService;
    }

    public Javalin getAPI() {
        Javalin api = Javalin.create();

        api.get("health", context -> {
            context.result("Server is UP");
        });

        // Books Endpoints
        api.get("books", context -> {
            List<Book> bookList = bookService.getAllBooks();
            context.json(bookList);
        });

        api.get("books/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            try{
                Book b = bookService.getBookById(id);
                context.json(b);
            }catch (BookNotFoundException e){
                context.status(404);
            }
        });
        api.post("books", context -> {
            try {
                ObjectMapper om = new ObjectMapper();
                Book b = om.readValue(context.body(), Book.class);
                bookService.saveBook(b);
                context.status(201);
            } catch (BookNotFoundException e) {
                context.result(e.getMessage());
                context.status(400);
            }
        });

        api.put("books/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            try {
                ObjectMapper om = new ObjectMapper();
                Book updatedBook = om.readValue(context.body(), Book.class);
                bookService.updateBook(id, updatedBook);
                context.status(200);
            } catch (SQLException e) {
                context.status(500);
            } catch (BookNotFoundException e) {
                context.status(404);
            }
        });

        api.delete("books/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            try {
                bookService.deleteBook(id);
                context.status(200);
            } catch (SQLException e) {
                context.status(500);
            } catch (BookNotFoundException e) {
                context.status(404);
            }
        });

        // Sellers Endpoints
        api.get("sellers/{sellerId}", context -> {
            int id = Integer.parseInt(context.pathParam("sellerId"));
            Seller seller = sellerService.getSellerById(id);
            if (seller == null){
                context.status(404);
            } else {context.json(seller);}
        });

        api.post("sellers", context -> {
            try {
                ObjectMapper om = new ObjectMapper();
                Seller s = om.readValue(context.body(), Seller.class);
                sellerService.saveSeller(s);
                context.status(201);
            } catch (SellerNotFoundException e) {
                context.result(e.getMessage());
                context.status(400);
            }
        });

        return api;
    }
}
