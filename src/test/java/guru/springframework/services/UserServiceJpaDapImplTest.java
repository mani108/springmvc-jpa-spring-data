package guru.springframework.services;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import guru.springframework.config.JpaIntegrationConfig;
import guru.springframework.domain.Cart;
import guru.springframework.domain.CartDetail;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Product;
import guru.springframework.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=JpaIntegrationConfig.class)
@ActiveProfiles("jpadao")
public class UserServiceJpaDapImplTest {
	private UserService userService;
	private ProductService productService;
	
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@Test
	public void testSaveofUser() throws Exception {
		User user = new User();
		
		user.setUsername("mani108");
		user.setPassword("jamfal121");
		
		User savedUser = userService.saveOrUpdate(user);
		
		assert savedUser.getId() != null;
		assert savedUser.getEncryptedPassword() != null;
		
		System.out.println("Encrypted Password");
		System.out.println(savedUser.getEncryptedPassword());
	}

	@Test
	public void testSaveOfUserWithCustomer() throws Exception{
		User user = new User();
		
		user.setUsername("mani108");
		user.setPassword("jamfal121");
		
		Customer customer = new Customer();
		customer.setFirstName("Mani");
		customer.setLastName("108");
		
		user.setCustomer(customer);
		
		User savedUser = userService.saveOrUpdate(user);
		
		assert savedUser.getId() != null;
		assert savedUser.getVersion() != null;
		assert savedUser.getCustomer() != null;
		assert savedUser.getCustomer().getId() != null;
	}

	
	@Test
	public void testAddCartToUser() throws Exception {
		User user = new User();
		
		user.setUsername("Chintmani");
		user.setPassword("jamfal121");
		
		user.setCart(new Cart());
		
		User savedUser = userService.saveOrUpdate(user);
		
		assert savedUser.getId() != null;
		assert savedUser.getVersion() != null;
		assert savedUser.getCart() != null;
		System.out.println("ID: "+ savedUser.getCart().getId());
		assert savedUser.getCart().getId() != null;
	}
	
	@Test
	public void testAddCartTUserWithCartDetails() throws Exception {
		User user = new User();
		
		user.setUsername("mani");
		user.setPassword("jamfal121");
		
		user.setCart(new Cart());
		
		List<Product> storedProducts = (List<Product>) productService.listAll();
		
		CartDetail cartItemOne = new CartDetail();
		cartItemOne.setProduct(storedProducts.get(0));
		user.getCart().addCartDetails(cartItemOne);
		
		
		CartDetail cartItemTwo = new CartDetail();
		cartItemTwo.setProduct(storedProducts.get(0));
		user.getCart().addCartDetails(cartItemTwo);
		
		User savedUser = userService.saveOrUpdate(user);
		
		assert savedUser.getId() != null;
		assert savedUser.getVersion() != null;
		assert savedUser.getCart() != null;
		//System.out.println("ID: "+ savedUser.getCart().getId());
		assert savedUser.getCart().getId() != null;
		assert savedUser.getCart().getCartDetails().size() == 2;
		
	}
	
	
	@Test
	public void testAddAndRemoveCartToUserWithCartDetails() throws Exception{
		User user = new User();
		
		user.setUsername("mani1");
		user.setPassword("jamfal121");
		
		user.setCart(new Cart());
		
		List<Product> storedProducts = (List<Product>) productService.listAll();
		
		CartDetail cartItemOne = new CartDetail();
		cartItemOne.setProduct(storedProducts.get(0));
		user.getCart().addCartDetails(cartItemOne);
		
		
		CartDetail cartItemTwo = new CartDetail();
		cartItemTwo.setProduct(storedProducts.get(0));
		user.getCart().addCartDetails(cartItemTwo);
		
		User savedUser = userService.saveOrUpdate(user);
		
		assert savedUser.getCart().getCartDetails().size() == 2;
		
		savedUser.getCart().removeCartDetails(savedUser.getCart().getCartDetails().get(0));
		
		userService.saveOrUpdate(savedUser);
		
		assert savedUser .getCart().getCartDetails().size() == 1;
	}
	
}
