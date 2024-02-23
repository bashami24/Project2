package org.example.DAO;
import org.example.Model.Seller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SellerDAO {
    Connection conn;
    public SellerDAO(Connection conn){
        this.conn = conn;
    }
    public void insertSeller(Seller s){
        try{
            PreparedStatement ps = conn.prepareStatement("insert into " +
                    "(seller_id, Seller_name) values (?, ?)");
            ps.setInt(1, s.getId());
            ps.setString(2, s.getName());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public List<Seller> getAllSellers(){
        List<Seller> SellerResults = new ArrayList<>();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from Seller");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("Seller_id");
                String name = rs.getString("seller_name");
                Seller s = new Seller(id,name);
                SellerResults.add(s);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return SellerResults;
    }


    public Seller getSellerById(int id){
        try{
            PreparedStatement ps = conn.prepareStatement(
                    "select * from seller where seller_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int Id = rs.getInt("seller_id");
                String name = rs.getString("name");
                Seller s = new Seller(id, name);
                return s;
            }else{
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}