package org.example.Service;
import org.example.DAO.BookDAO;
import org.example.Exceptions.BookAlreadyExistsException;
import org.example.Exceptions.BookNotFoundException;
import org.example.Model.Book;

import java.sql.SQLException;
import java.util.List;

public class BookService {
   BookDAO bookDAO;
   public BookService(BookDAO bookDAO){
       this.bookDAO = bookDAO;
   }
    public void saveBook(Book b) throws BookAlreadyExistsException,BookNotFoundException {
        if (b.getId() <= 0) {
            throw new BookAlreadyExistsException ("Book ID must be greater than 0");
        }

        if (bookDAO.getBookById(b.getId()) != null) {
            throw new BookAlreadyExistsException("Book with ID " + b.getId() + " already exists");
        }

        if (b.getName() == null || b.getName().isEmpty()) {
            throw new BookNotFoundException("Book name cannot be null or empty");
        }

        if (b.getPrice() <= 0) {
            throw new BookNotFoundException("Book price must be greater than 0");
        }

        bookDAO.insertBook(b);
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }
   public Book getBookById(int id) throws BookNotFoundException {
    return bookDAO.getBookById(id);
   }
    public void updateBook(int id, Book updatedBook) throws BookNotFoundException, SQLException {
        if (updatedBook.getName() == null || updatedBook.getName().isEmpty()) {
            throw new BookNotFoundException ("Book name cannot be null or empty");
        }

        if (updatedBook.getPrice() <= 0) {
            throw new BookNotFoundException("Book price must be greater than 0");
        }

        Book existingBook = bookDAO.getBookById(id);
        if (existingBook == null) {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }

        bookDAO.updateBook(id, updatedBook);
    }

    public void deleteBook(int bookId) throws SQLException, BookNotFoundException {
        Book existingBook = bookDAO.getBookById(bookId);
        if (existingBook == null) {
            throw new BookNotFoundException("Book with ID " + bookId + " not found");
        }

        bookDAO.deleteBook(bookId);
    }
}
