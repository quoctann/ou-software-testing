import com.ou_software_testing.ou_software_testing.pojo.ListProduct;
import com.ou_software_testing.ou_software_testing.pojo.Product;
import com.ou_software_testing.ou_software_testing.services.ProductServices;
import com.ou_software_testing.ou_software_testing.services.JdbcServices;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
/**
 *
 * @author Alive Nguyễn
 */
public class ProduceTester {
    Connection conn1;
    Product p;
    
    //Số lượng từng sản phẩm không vượt quá 200.
    @Test
    public void testQuantityBiggest() throws SQLException {
        Connection conn = JdbcServices.getConnection();
        boolean flag = false;
        ListProduct listProds = new ProductServices(conn).getAllProduct();
        
        for(Product p: listProds.getListProduct()){
            if(p.getCount() > 200)  {
                flag = true;
                break;
            }
        }
        Assertions.assertFalse(flag);
    } 
    
    //Số lượng từng sản phẩm không được ít hơn 3 sản phẩm
    @Test
    public void testQuantitySmallest() throws SQLException {
        Connection conn = JdbcServices.getConnection();
        boolean flag = false;
        ListProduct listProds = new ProductServices(conn).getAllProduct();
        for(Product p: listProds.getListProduct()){
            if(p.getCount() < 3)  {
                flag = true;
                break;
            }
        }
        Assertions.assertFalse(flag);
    } 
    //Edit với số lượng < 200 && >3
    @Test 
    public void testEditProductById() {
        Connection conn = JdbcServices.getConnection();
        p = new Product("ao den","USA","XL",10,1,new BigDecimal("5000"));
        p.setId(1);
        Boolean rs = new ProductServices(conn).editProductById(p);
        Assertions.assertTrue(rs);
        resetBack();
    }
    //Edit với số lượng > 200 
    @Test
    public void testEditProductByIdBigQuantity() {
        Connection conn = JdbcServices.getConnection();
        p = new Product("ao den thui","USA","XL",201,1,new BigDecimal("5000"));
        p.setId(1);
        Boolean rs = new ProductServices(conn).editProductById(p);
        Assertions.assertFalse(rs);
    }
    //Edit với số lượng <3
    @Test
    public void testEditProductByIdSmallQuantity() {
        Connection conn = JdbcServices.getConnection();
        p = new Product("ao den thui","USA","XL",2,1,new BigDecimal("5000"));
        p.setId(1);
        Boolean rs = new ProductServices(conn).editProductById(p);
        Assertions.assertFalse(rs);
    }
    //Edit với trùng tên
    @Test
    public void testEditProductByIdSameName() {
        Connection conn = JdbcServices.getConnection();
        p = new Product("ao den2","USA","XL",2,1,new BigDecimal("5000"));
        p.setId(1);
        Boolean rs = new ProductServices(conn).editProductById(p);
        Assertions.assertFalse(rs);
    }
    @Test
    public void testCheckUniqueName() {
        Connection conn = JdbcServices.getConnection();
        p = new Product("ao den thui","USA","XL",2,1,new BigDecimal("5000"));
        Boolean rs = new ProductServices(conn).checkUniqueName(p);
        Assertions.assertTrue(rs);
    }

    private void resetBack() {
        Connection conn = JdbcServices.getConnection();
        p = new Product("ao den1","USA","XL",10,1,new BigDecimal("5000"));
        p.setId(1);
        Boolean rs = new ProductServices(conn).editProductById(p);
    }
}
