package org.example.Service;
import org.example.DAO.SellerDAO;
import org.example.Exceptions.SellerAlreadyExistsException;
import org.example.Exceptions.SellerNotFoundException;
import org.example.Model.Seller;
import java.util.List;

public class SellerService {
    SellerDAO sellerDAO;

    public SellerService(SellerDAO sellerDAO) {
        this.sellerDAO = sellerDAO;
    }

    public void saveSeller(Seller s) throws SellerAlreadyExistsException, SellerNotFoundException {
        if (s.getId() <= 0) {
            throw new SellerNotFoundException ("Seller ID must be greater than 0");
        }

        if (sellerDAO.getSellerById(s.getId()) != null) {
            throw new SellerAlreadyExistsException("Seller with ID " + s.getId() + " already exists");
        }

        if (s.getName() == null || s.getName().isEmpty()) {
            throw new SellerNotFoundException("Seller name cannot be null or empty");
        }

        sellerDAO.insertSeller(s);
    }

    public List<Seller> getAllSellers() {
        return sellerDAO.getAllSellers();
    }

    public Seller getSellerById(int id) throws SellerNotFoundException {
        Seller seller = sellerDAO.getSellerById(id);
        if (seller == null) {
            throw new SellerNotFoundException("Seller with ID " + id + " not found");
        }
        return seller;
    }
}
