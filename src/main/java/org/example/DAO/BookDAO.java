package org.example.DAO;
import org.example.Model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    Connection conn;
    public BookDAO(Connection conn){
        this.conn = conn;
    }
    public void insertBook(Book b){
        try{
            PreparedStatement ps = conn.prepareStatement("insert into book" +
                    " (product_id,name, product_price, Seller_id) " +
                    "values (?,?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, b.getId());
            ps.setString(2, b.getName());
            ps.setDouble(3, b.getPrice());
            ps.setInt(4, b.getSellerId());
            ps.executeUpdate();
           // ResultSet rs = ps.getGeneratedKeys();
            //if (rs.next()) { int id = rs.getInt(1);
              //  b.setId(id);}
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public List<Book> getAllBooks(){
        List<Book> resultBook = new ArrayList<>();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from Book");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("product_id");
                String Name = rs.getString("name");
                double Price = rs.getDouble("product_price");
                int SellerId = rs.getInt("Seller_id");
                Book  b = new Book(id, Name, Price, SellerId);
                resultBook.add(b);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return resultBook;
    }
    public Book getBookById(int id){
        try{
            PreparedStatement ps = conn.prepareStatement("select * from Book where product_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int Id = rs.getInt("product_id");
                String Name = rs.getString("name");
                double Price = rs.getDouble("product_price");
                int SellerId = rs.getInt("Seller_id");
                return new Book(id,Name, Price, SellerId);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public void updateBook(int id, Book updatedBook) throws SQLException {
        try{  PreparedStatement ps = conn.prepareStatement
                ("UPDATE BOOK SET name = ?, product_price = ?, Seller_Id = ? WHERE product_id = ?");

            ps.setString(1, updatedBook.getName());
            ps.setDouble(2, updatedBook.getPrice());
            ps.setInt(3, updatedBook.getSellerId());
            ps.setInt(4,id);
            ps.executeUpdate();
        } catch (SQLException e ) { e.printStackTrace();}
    }

    public void deleteBook(int bookId) throws SQLException {
        try{  PreparedStatement ps = conn.prepareStatement("DELETE from BOOK WHERE product_id = ?");
            ps.setInt(1, bookId);
            ps.executeUpdate();
        }catch (SQLException e) { e.printStackTrace();
        }
    }
}

