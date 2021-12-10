import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SQLTest {


    @Test
    void testInsertRead(){
        SQL sqlTest = new SQL();
        sqlTest.create("Product");
        Product prod1 = sqlTest.insert(new Product("buckwheat1", 14.5, 4.0));
        Product prod2 =   sqlTest.insert(new Product("buckwheat2", 25.0, 6.0));

        List<Product> products = sqlTest.read();
        Assertions.assertTrue(products.contains(prod1));
        Assertions.assertTrue(products.contains(prod2));
        sqlTest.deleteAll();
    }

    @Test
    void testCreteria(){
        SQL sqlTest = new SQL();
        sqlTest.create("Product");
        Product prod1 = sqlTest.insert(new Product("buckwheat1", 14.5, 4.0));
        Product prod2 =   sqlTest.insert(new Product("buckwheat2", 25.0, 6.0));
        Product prod3 =   sqlTest.insert(new Product("tiger", 35.0, 8.0));

        ProductFilter filter = new ProductFilter();
        filter.setName("tig");
        Assertions.assertTrue( sqlTest.listByCriteria(filter).contains(prod3));

         filter = new ProductFilter();
        filter.setPriceFrom(20.0);
        Assertions.assertTrue( sqlTest.listByCriteria(filter).contains(prod2));
        Assertions.assertTrue( sqlTest.listByCriteria(filter).contains(prod3));

         filter = new ProductFilter();
        filter.setPriceTo(20.0);
        Assertions.assertTrue( sqlTest.listByCriteria(filter).contains(prod1));

         filter = new ProductFilter();
        filter.setAmountFrom(5.0);
        Assertions.assertTrue( sqlTest.listByCriteria(filter).contains(prod2));
        Assertions.assertTrue( sqlTest.listByCriteria(filter).contains(prod3));

         filter = new ProductFilter();
        filter.setAmountTo(5.0);
        Assertions.assertTrue( sqlTest.listByCriteria(filter).contains(prod1));

        filter = new ProductFilter();
        filter.setAmountTo(7.0);
        filter.setPriceFrom(20.0);
        System.out.println(sqlTest.listByCriteria(filter));
        Assertions.assertTrue( sqlTest.listByCriteria(filter).contains(prod2));
        sqlTest.deleteAll();
    }

    @Test
    void testUpdate() {
        SQL sqlTest = new SQL();
        sqlTest.create("Product");
        Product prod1 = sqlTest.insert(new Product("buckwheat1", 14.5, 4.0));
        Product prod2 =   sqlTest.insert(new Product("buckwheat2", 25.0, 6.0));
        Product prod3 =   sqlTest.insert(new Product("tiger", 35.0, 8.0));

        ProductFilter filter = new ProductFilter();
        filter.setName("buckwheat1");
       sqlTest.update("lemon", 100.6, 5, filter);
       Product expected = new Product("lemon", 100.6, 5);
       System.out.println( sqlTest.read());

       Assertions.assertEquals(sqlTest.read().get(0).getName(),"lemon");
        Assertions.assertEquals(sqlTest.read().get(0).getAmount(),5);
        Assertions.assertEquals(sqlTest.read().get(0).getPrice(),100.6);
       Assertions.assertFalse(sqlTest.read().contains(prod1));
    }
}
