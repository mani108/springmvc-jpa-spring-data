package guru.springframework.services.mapservices;


import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.domain.Customer;
import guru.springframework.domain.DomainObject;
import guru.springframework.services.CustomerService;

@Service
@Profile("map")
public class CustomerServiceImpl extends AbstractMapService implements CustomerService {
	
	@Override
	public List<DomainObject> listAll() {
		return super.listAll();
	}
	
	@Override
	public Customer getById(Integer id) {
		return (Customer) super.getById(id);
	}

	@Override
	public Customer saveOrUpdate(Customer domainObject) {
		return (Customer) super.saveOrUpdate(domainObject);
	}

	
	@Override
	public void deleteById(Integer id) {
		super.deleteById(id);
	}
	
	/*
	 * @Override protected void loadDomainObjects() { domainMap = new HashMap<>();
	 * //products = new HashMap<>();
	 * 
	 * Customer customer = new Customer(); customer.setId(1);
	 * customer.setFirstName("Chintamani"); customer.setLastName("Puntambekar");
	 * customer.setPhoneNumber("98965245"); customer.setAddressLine1("AGC");
	 * customer.setAddressLine2("Warje"); customer.setCity("Pune");
	 * customer.setState("MH"); customer.setCountry("India");
	 * customer.setZipCode("411058");
	 * 
	 * domainMap.put(1,customer);
	 * 
	 * 
	 * Customer customer1 = new Customer(); customer1.setId(2);
	 * customer1.setFirstName("Chintamani"); customer1.setLastName("Puntambekar");
	 * customer1.setPhoneNumber("98965245"); customer1.setAddressLine1("AGC");
	 * customer1.setAddressLine2("Warje"); customer1.setCity("Pune");
	 * customer1.setState("MH"); customer1.setCountry("India");
	 * customer1.setZipCode("411058");
	 * 
	 * domainMap.put(2,customer1); }
	 * 
	 */
}
