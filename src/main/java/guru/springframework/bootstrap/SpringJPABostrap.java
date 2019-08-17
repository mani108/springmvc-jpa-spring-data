package guru.springframework.bootstrap;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import guru.springframework.domain.Address;
import guru.springframework.domain.Cart;
import guru.springframework.domain.CartDetail;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Order;
import guru.springframework.domain.OrderDetail;
import guru.springframework.domain.Product;
import guru.springframework.domain.User;
import guru.springframework.domain.security.Role;
import guru.springframework.enums.OrderStatus;
import guru.springframework.services.ProductService;
import guru.springframework.services.RoleService;
import guru.springframework.services.UserService;

@Component
public class SpringJPABostrap implements ApplicationListener<ContextRefreshedEvent>{
	private ProductService productService;
	private UserService userService;
	private RoleService roleService;
	

	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		loadProducts();
		loadUsersAndCustomers();
		loadCarts();
		loadOrderHistory();
		loadRoles();
		assignRolesToDefaultRole();
	}
	
	private void assignRolesToDefaultRole() {
		List<Role> roles = (List<Role>) roleService.listAll();
		List<User> users = (List<User>) userService.listAll();
		
		roles.forEach(role -> {
			if(role.getRole().equalsIgnoreCase("CUSTOMER")) {
				users.forEach(user -> {
					user.addRole(role);
					userService.saveOrUpdate(user);
				});
			}
		});
	} 
	
	private void loadRoles() {
		Role role = new Role();
		role.setRole("CUSTOMER");
		roleService.saveOrUpdate(role);
	}
	public void loadProducts() {
	
		Product product1 = new Product();
		product1.setId(1);
		product1.setDescription("Product 1");
		product1.setPrice(new BigDecimal("12.99"));
		product1.setImageUrl("http://example.com/product1");
		productService.saveOrUpdate(product1);
		
		Product product2 = new Product();
		product2.setId(2);
		product2.setDescription("Product 2");
		product2.setPrice(new BigDecimal("14.99"));
		product2.setImageUrl("http://example.com/product2");
		productService.saveOrUpdate(product2);
		
		Product product3 = new Product();
		product3.setId(3);
		product3.setDescription("Product 3");
		product3.setPrice(new BigDecimal("16.99"));
		product3.setImageUrl("http://example.com/product3");
		productService.saveOrUpdate(product3);
		
		
		Product product4 = new Product();
		product4.setId(4);
		product4.setDescription("Product 4");
		product4.setPrice(new BigDecimal("18.99"));
		product4.setImageUrl("http://example.com/product4");
		productService.saveOrUpdate(product4);
		
		Product product5 = new Product();
		product5.setId(5);
		product5.setDescription("Product 5");
		product5.setPrice(new BigDecimal("20.99"));
		product5.setImageUrl("http://example.com/product5");
		productService.saveOrUpdate(product5);
		
		Product product6 = new Product();
		product6.setId(6);
		product6.setDescription("Product 6");
		product6.setPrice(new BigDecimal("16.99"));
		product6.setImageUrl("http://example.com/product3");
		productService.saveOrUpdate(product6);

	}

	public void loadUsersAndCustomers() {
		User user1 = new User();
		user1.setUsername("mani108");
		user1.setPassword("jamfal121");
		
		
		Customer customer = new Customer();
		customer.setId(1);
		customer.setFirstName("Chintamani");
		customer.setLastName("Puntambekar");
		customer.setPhoneNumber("98965245");
		customer.setBillingAddress(new Address());
		customer.getBillingAddress().setAddressLine1("AGC");
		customer.getBillingAddress().setAddressLine2("Warje");
		customer.getBillingAddress().setCity("Pune");
		customer.getBillingAddress().setState("MH");
		customer.getBillingAddress().setCountry("India");
		customer.getBillingAddress().setZipCode("411058");
		
		user1.setCustomer(customer);
		userService.saveOrUpdate(user1);
		
		//customerService.saveOrUpdate(customer);
		
		User user2 = new User();
		user2.setUsername("mani109");
		user2.setPassword("jamfal121");
		
		
		Customer customer1 = new Customer();
		customer1.setId(2);
		customer1.setFirstName("Keshav");
		customer1.setLastName("Sanse");
		customer1.setPhoneNumber("98965245");
		customer1.setBillingAddress(new Address());
		customer1.getBillingAddress().setAddressLine1("AGC");
		customer1.getBillingAddress().setAddressLine2("Warje");
		customer1.getBillingAddress().setCity("Pune");
		customer1.getBillingAddress().setState("MH");
		customer1.getBillingAddress().setCountry("India");
		customer1.getBillingAddress().setZipCode("411058");
		
		user2.setCustomer(customer1);
		userService.saveOrUpdate(user2);
		//customerService.saveOrUpdate(customer1);
	}

	public void loadCarts() {
		List<User> users = (List<User>) userService.listAll();
		List<Product> products = (List<Product>) productService.listAll();
		
		users.forEach(user -> {
			user.setCart(new Cart());
			CartDetail cartDetail = new CartDetail();
			cartDetail.setProduct(products.get(0));
			cartDetail.setQuantity(2);
			
			user.getCart().addCartDetails(cartDetail);
			
			userService.saveOrUpdate(user);
		});
	}


	public void loadOrderHistory() {
		List<User> users = (List<User>) userService.listAll();
		List<Product> products = (List<Product>) productService.listAll();
		
		users.forEach(user -> {
			Order order = new Order();
			order.setCustomer(user.getCustomer());
			order.setOrderStatus(OrderStatus.SHIPPED);
			
			products.forEach(product -> {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setProduct(product);
				orderDetail.setQuantity(1);
				order.addToOrderDetails(orderDetail);
			});
		});
	}
}
