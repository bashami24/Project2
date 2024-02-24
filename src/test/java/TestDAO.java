import org.example.DAO.BookDAO;
import org.example.DAO.SellerDAO;
import org.example.Model.Book;
import org.example.Model.Seller;
import org.example.Service.BookService;
import org.example.Service.SellerService;
import org.example.Util.ConnectionSingleton;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.example.Util.ConnectionSingleton.connection;
import static org.junit.Assert.*;

public class TestDAO {

    Connection conn = ConnectionSingleton.getConnection();
     BookDAO bookDAO;
    SellerDAO sellerDAO;
   //ResetTestdatabase();
    BookService bookService;
    SellerService sellerService;

    @Before
    public void setUp() throws SQLException {
        dropTable();
        createTables();
        sellerDAO = new SellerDAO(conn);
        bookDAO = new BookDAO(conn);

    }

    @After
    public void tearDown() throws SQLException {
        dropTable();
        //conn.close();
    }
private void dropTable() throws SQLException{
        PreparedStatement ps = conn.prepareStatement("DROP TABLE  IF EXISTS BOOK");
        ps.executeUpdate();
        ps.close();
        ps = conn.prepareStatement("DROP TABLE IF EXISTS Seller");
        ps.executeUpdate();
        ps.close();
}
    private void createTables() throws SQLException {
        if (conn.isClosed()){conn = ConnectionSingleton.getConnection();}
        PreparedStatement p = conn.prepareStatement(
                "CREATE TABLE if not exists Seller (seller_id INT PRIMARY KEY, seller_name VARCHAR(255))"
        );
        p.executeUpdate();
        p.close();
        PreparedStatement ps = conn.prepareStatement(
                "CREATE TABLE if not exists Book (product_id INT PRIMARY KEY, name VARCHAR(255), product_price Decimal(10,2), Seller_id int references Seller(Seller_id))"

        );
        ps.executeUpdate();
        ps.close();
    }

    @Test
    public void testSellerCRUD() throws SQLException {
        if (conn.isClosed()){conn = ConnectionSingleton.getConnection();}
        // Insert
        Seller seller = new Seller(1,"Seller1");
        sellerDAO.insertSeller(seller);

        // Get
        Seller retrievedSeller = sellerDAO.getSellerById(seller.getId());
        assertNotNull(retrievedSeller);
        assertEquals(seller.getName(), retrievedSeller.getName());

    }
    @Test
    public void testBookCRUD() throws SQLException {
        if (conn.isClosed()){conn = ConnectionSingleton.getConnection();}
        // Insert Seller
        Seller seller = new Seller(1,"seller1");
        sellerDAO.insertSeller(seller);
        //Insert Book
        Book book = new Book(6,"Book6", 10.0, 1);
        bookDAO.insertBook(book);

        // Get
        Book retrievedBook = bookDAO.getBookById(book.getId());
        assertNotNull(retrievedBook);
        assertEquals(book.getName(), retrievedBook.getName());
        assertEquals(book.getPrice(), retrievedBook.getPrice(), 0.01);
        assertEquals(book.getSellerId(), retrievedBook.getSellerId());

        // Update
        book.setName("UpdatedBook");
        bookDAO.updateBook(book.getId(), book);
        retrievedBook = bookDAO.getBookById(book.getId());
        assertEquals(book.getName(), retrievedBook.getName());

        // Delete
        bookDAO.deleteBook(book.getId());
        retrievedBook = bookDAO.getBookById(book.getId());
        assertNull(retrievedBook);
    }



    @Test
    public void testGetAllBooksAndSellers() throws SQLException {
        // Insert some books and sellers

        Seller seller1 = new Seller(4,"Seller4");
        Seller seller2 = new Seller(5,"Seller5");
        sellerDAO.insertSeller(seller1);
        sellerDAO.insertSeller(seller2);

        Book book1 = new Book(4,"Book4", 10.0, 4);
        Book book2 = new Book(5,"Book5", 20.0, 5);
        bookDAO.insertBook(book1);
        bookDAO.insertBook(book2);

        // Get all books and sellers
        List<Book> books = bookDAO.getAllBooks();
        List<Seller> sellers = sellerDAO.getAllSellers();

        assertEquals(2, books.size());
        assertEquals(2, sellers.size());
    }
}
