package guru.springframework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import guru.springframework.domain.Customer;
import guru.springframework.services.CustomerService;

@RequestMapping("/customer")
@Controller
public class CustomerController {
	private CustomerService customerService;
	
	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@RequestMapping({"/list","/"})
	public String listCustomer(Model model) {
		model.addAttribute("customers", customerService.listAll());
		
		return "customer/list";
	}
	
	@RequestMapping("/show/{id}")
	public String getCustomer(@PathVariable Integer id, Model model) {
		model.addAttribute("customer", customerService.getById(id));
		return "customer/show";
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		model.addAttribute("customer", customerService.getById(id));
		return "customer/customerForm";
	}
	
	@RequestMapping("/new")
	public String newCustomer(Model model) {
		model.addAttribute("customer", new Customer());
		return "customer/customerForm";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String saveOrUpdateCustomer(Customer customer) {
		Customer savedCustomer = customerService.saveOrUpdate(customer);
		return "redirect:/customer/show/" + savedCustomer.getId();
	}
	
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		customerService.deleteById(id);
		return "redirect:/customer/list";
	}
}
