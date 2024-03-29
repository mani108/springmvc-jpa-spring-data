package guru.springframework.controllers;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.*;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;

public class ProductControllerTest {
	@Mock //Mockito Mock Object
	private ProductService productService;
	
	@InjectMocks // setups up controller, and injects mock object in
	private ProductControllers productController;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this); // initializes controller and mocks
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}
	
	@Test
	public void testList() throws Exception {
		List<Product> products= new ArrayList<>();
		products.add(new Product());
		products.add(new Product());
		
		// specific Mockito interaction, tell stub to return list of products
		when(productService.listAll()).thenReturn((List) products); //need to strip generics to keep Mockito happy
		
		mockMvc.perform(get("/product/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("product/list"))
			.andExpect(model().attribute("products", hasSize(2)));
	}
	
	@Test
	public void testShow() throws Exception{
		Integer id = 1;
		
		//Tell Mockito stup to return new product for ID 1
		when(productService.getById(id)).thenReturn(new Product());
		
		mockMvc.perform(get("/product/show/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("product/show"))
			.andExpect(model().attribute("product", instanceOf(Product.class)));
	}
	
	@Test
	public void testEdit() throws Exception{
		Integer id = 1;
		
		// Tell Mockito stub to return new product for ID 1
		when(productService.getById(id)).thenReturn(new Product());
		
		mockMvc.perform(get("/productedit/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("product/productform"))
			.andExpect(model().attribute("product",instanceOf(Product.class)));
	}
	
	@Test
	public void testNewProduct() throws Exception {
		//Integer id = 1;
		
		// should not call servie
		verifyZeroInteractions(productService);
		
		mockMvc.perform(get("/product/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("product/productform"))
			.andExpect(model().attribute("product", instanceOf(Product.class)));
	}
	
	@Test
	public void testSaveOrUpdate() throws Exception {
		Integer id = 1;
		String description = "Test Description";
		BigDecimal price = new BigDecimal("12.00");
		String imageUrl = "example.com";
		
		Product returnProduct = new Product();
		returnProduct.setId(id);
		returnProduct.setDescription(description);
		returnProduct.setPrice(price);
		returnProduct.setImageUrl(imageUrl);
		
		when(productService.saveOrUpdate(Matchers.<Product>any())).thenReturn(returnProduct);
		
		mockMvc.perform(post("/product")
			.param("id", "1")
			.param("description", description)
			.param("price", "12.00")
			.param("imageUrl", "example.com"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/product/show/1"))
				.andExpect(model().attribute("product", instanceOf(Product.class)))
				.andExpect(model().attribute("product", hasProperty("id", is(id))))
				.andExpect(model().attribute("product", hasProperty("description", is(description))))
				.andExpect(model().attribute("product", hasProperty("price", is(price))))
				.andExpect(model().attribute("product", hasProperty("imageUrl", is(imageUrl))));
		
		
		// Verify properties of bound object
		ArgumentCaptor<Product> boundProduct = ArgumentCaptor.forClass(Product.class);
		verify(productService).saveOrUpdate(boundProduct.capture());
		
		assertEquals(id, boundProduct.getValue().getId());
		assertEquals(description, boundProduct.getValue().getDescription());
		assertEquals(price, boundProduct.getValue().getPrice());
		assertEquals(imageUrl, boundProduct.getValue().getImageUrl());
	}
	
	@Test
	public void testDelete() throws Exception{
		Integer id = 1;
		
		mockMvc.perform(get("/product/delete/1"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/product/list"));
		
		verify(productService, times(1)).deleteById(id);
	}
}
