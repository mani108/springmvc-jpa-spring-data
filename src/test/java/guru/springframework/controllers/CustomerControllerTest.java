package guru.springframework.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.domain.Address;
import guru.springframework.domain.Customer;
import guru.springframework.services.CustomerService;

public class CustomerControllerTest {
	@Mock
	private CustomerService customerService;
	
	@InjectMocks
	private CustomerController customerController;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}
	
	
	@Test
	public void testList() throws Exception {
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer());
		customers.add(new Customer());
		
		when(customerService.listAll()).thenReturn((List)customers);
		
		mockMvc.perform(get("/customer/list/"))
			.andExpect(status().isOk())
			.andExpect(view().name("customer/list"))
			.andExpect(model().attribute("customers", hasSize(2)));
	}
	
	@Test
	public void testShow() throws Exception{
		Integer id = 1;
		
		//Tell Mockito stub to return new product for ID 1
		when(customerService.getById(id)).thenReturn(new Customer());
		
		mockMvc.perform(get("/customer/show/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("customer/show"))
			.andExpect(model().attribute("customer", instanceOf(Customer.class)));
	}
	
	@Test
	public void testEdit() throws Exception{
		Integer id = 1;
		
		when(customerService.getById(id)).thenReturn(new Customer());
		
		mockMvc.perform(get("/customer/edit/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("customer/customerForm"))
			.andExpect(model().attribute("customer", instanceOf(Customer.class)));
	}
	
	@Test
	public void testNewCustomer() throws Exception {
		//Integer id = 1;
		
		// should not call service
		verifyZeroInteractions(customerService);
		
		mockMvc.perform(get("/customer/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("customer/customerForm"))
			.andExpect(model().attribute("customer", instanceOf(Customer.class)));
	}
	
	
	@Test
	public void testSaveOrUpdate() throws Exception {
		Integer id = 1;
		String firstName = "Kavi";
		String lastName = "Chintamani";
		String phoneNumber = "7987974654";
		String addressLine1 = "Pune 12";
		String addressLine2 = "MH";
		String city = "Pune";
		String state = "MH";
		String country = "IND";
		String zipCode = "452012";
		
		Customer returnCustomer = new Customer();
		returnCustomer.setId(id);
		returnCustomer.setFirstName(firstName);
		returnCustomer.setLastName(lastName);
		returnCustomer.setPhoneNumber(phoneNumber);
		returnCustomer.setBillingAddress(new Address());
		returnCustomer.getBillingAddress().setAddressLine1(addressLine1);
		returnCustomer.getBillingAddress().setAddressLine2(addressLine2);
		returnCustomer.getBillingAddress().setCity(city);
		//returnCustomer.getBillingAddress().setZipCode(zipCode);
		returnCustomer.getBillingAddress().setState(state);
		returnCustomer.getBillingAddress().setCountry(country);
		
		when(customerService.saveOrUpdate(Matchers.<Customer>any())).thenReturn(returnCustomer);
		
		mockMvc.perform(post("/customer")
		.param("id","1")
		.param("firstName","Kavi")
		.param("lastName","Chintamani")
		.param("phoneNumber","7987974654")
		.param("billingAddress.addressLine1","Pune 12")
		.param("billingAddress.addressLine2", "MH")
		.param("billingAddress.city", "Pune")
		.param("billingAddress.state","MH")
		//.param("billingAddress.zipCode","411038")
		.param("billingAddress.country","IND"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/customer/show/1"))
			.andExpect(model().attribute("customer",instanceOf(Customer.class)))
			.andExpect(model().attribute("customer", hasProperty("id", is(id))))
			.andExpect(model().attribute("customer", hasProperty("firstName",is(firstName))))
			.andExpect(model().attribute("customer", hasProperty("lastName",is(lastName))))
			.andExpect(model().attribute("customer", hasProperty("phoneNumber",is(phoneNumber))))
			.andExpect(model().attribute("customer", hasProperty("billingAddress",hasProperty("addressLine1",is(addressLine1)))))
			.andExpect(model().attribute("customer", hasProperty("billingAddress",hasProperty("addressLine2",is(addressLine2)))))
			.andExpect(model().attribute("customer", hasProperty("billingAddress",hasProperty("city",is(city)))))
			.andExpect(model().attribute("customer", hasProperty("billingAddress",hasProperty("state",is(state)))))
			//.andExpect(model().attribute("customer", hasProperty("billingAddress",hasProperty("zipCode",is(zipCode)))))
			.andExpect(model().attribute("customer", hasProperty("billingAddress",hasProperty("country",is(country)))));
			
		//verify properties of bound object
		ArgumentCaptor<Customer> boundCustomer = ArgumentCaptor.forClass(Customer.class);
		verify(customerService).saveOrUpdate(boundCustomer.capture());
		
		assertEquals(id, boundCustomer.getValue().getId());
		assertEquals(firstName, boundCustomer.getValue().getFirstName());
		assertEquals(lastName, boundCustomer.getValue().getLastName());
		assertEquals(phoneNumber, boundCustomer.getValue().getPhoneNumber());
		assertEquals(addressLine1, boundCustomer.getValue().getBillingAddress().getAddressLine1());
		assertEquals(addressLine2, boundCustomer.getValue().getBillingAddress().getAddressLine2());
		assertEquals(city, boundCustomer.getValue().getBillingAddress().getCity());
		assertEquals(state, boundCustomer.getValue().getBillingAddress().getState());
		//assertEquals(zipCode, boundCustomer.getValue().getBillingAddress().getZipCode());
		assertEquals(country, boundCustomer.getValue().getBillingAddress().getCountry());
	}
	
}
