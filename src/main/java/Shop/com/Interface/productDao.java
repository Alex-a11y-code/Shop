package Shop.com.Interface;

import Shop.com.Product;
import java.util.ArrayList;

public interface productDao {


    public abstract ArrayList<Product> findAll();


    public abstract Product findById(int id);


    public abstract int insert(Product product);


    public abstract int update(Product product);


    public abstract int delete(int id);
}
