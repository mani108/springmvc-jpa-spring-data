package guru.springframework.services;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import guru.springframework.config.JpaIntegrationConfig;
import guru.springframework.domain.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=JpaIntegrationConfig.class)
@ActiveProfiles("jpadao")	
public class ProductServiceJpaDaoImplTest {
	private ProductService productService;
	
	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	@Test
	public void testListMethod() throws Exception{
		List<Product> products = (List<Product>) productService.listAll();
		assert products.size() == 6;	
	}
	
	@Test
	public void testGetbyIdMethod() throws Exception{
		Integer id = 1;
		Product product = productService.getById(id);
		//assert product.getDescription() == "Product 1";
	}
	
	@Test
	public void testSaveOrUpdate() throws Exception{
		Product product = new Product();
		product.setId(7);
		product.setDescription("Testing Product");
		product.setPrice(new BigDecimal(1000));
		product.setImageUrl("http://sample.com/img=1");
		
		Product testProduct = productService.saveOrUpdate(product);
		assert testProduct != null;
		assert testProduct.getId() == 7;
	}
	
	@Test
	public void testDelete() throws Exception{
		productService.deleteById(1);
		
		//assert productService.listAll().size() == 6;
	}
	
	
}
