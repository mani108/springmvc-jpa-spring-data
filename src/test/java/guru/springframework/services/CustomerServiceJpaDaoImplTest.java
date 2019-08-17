package guru.springframework.services;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import guru.springframework.config.JpaIntegrationConfig;
import guru.springframework.domain.Customer;
import guru.springframework.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=JpaIntegrationConfig.class)
@ActiveProfiles("jpadao")
public class CustomerServiceJpaDaoImplTest {
	private CustomerService customerService;

	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@Test
	public void testList() throws Exception {
		List<Customer> customer =  (List<Customer>) customerService.listAll();
		
		assert customer.size() == 2;
	}
	
	@Test
	public void testSaveWithUser() {
		Customer customer = new Customer();
		User user = new User();
		user.setUsername("mani108");
		user.setPassword("jamfal121");
		customer.setUser(user);
		
		Customer savedCustomer = customerService.saveOrUpdate(customer);
		
		assert savedCustomer.getUser().getId() != null;
	}
	
}
