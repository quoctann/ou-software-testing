/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.ou_software_testing.ou_software_testing.pojo.Category;
import com.ou_software_testing.ou_software_testing.services.CategoryServices;
import com.ou_software_testing.ou_software_testing.services.JdbcServices;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
/**
 *
 * @author Alive Nguyễn
 */
public class CategoryTester {
    Connection conn = JdbcServices.getConnection();
    
    
    //Số loại sản phẩm không vượt quá 50.
    @Test
    public void testQuantityBiggest() throws SQLException {
       List<Category> cates = new CategoryServices(conn).getCategorys();
        Assertions.assertTrue(cates.size() <= 50);
    }
    
    //Số loại sản phẩm không ít hơn 2 loại.
    @Test
    public void testQuantitySmallest() throws SQLException {
       List<Category> cates = new CategoryServices(conn).getCategorys();
        Assertions.assertTrue(cates.size() >= 2);
    }
    
    //Loại sản phẩm không được trùng
    @Test
    public void testUnique() throws SQLException, Exception {
        List<Category> cates = new CategoryServices(conn).getCategorys();
        
        List<String> names = new ArrayList<>();
        List<String> sexs = new ArrayList<>();
        cates.forEach(c -> {names.add(c.getName());
                            sexs.add(c.getSex());
                        });
        
        Set<Category> uniqueCates = new HashSet<>(cates);
        
        Assertions.assertEquals(cates.size(), uniqueCates.size());
        
    }


}
