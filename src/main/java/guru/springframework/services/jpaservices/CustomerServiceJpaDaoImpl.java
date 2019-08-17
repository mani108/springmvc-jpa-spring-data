package guru.springframework.services.jpaservices;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.domain.Customer;
import guru.springframework.services.CustomerService;
import guru.springframework.services.security.EncryptionService;

@Service
@Profile("jpadao")
public class CustomerServiceJpaDaoImpl extends AbstractJpaDaoService implements CustomerService {

	@Autowired
	private EncryptionService encryptionService;

	@Override
	public List<Customer> listAll() {
		EntityManager em = emf.createEntityManager();
		
		return em.createQuery("from Customer", Customer.class).getResultList();
	}

	@Override
	public Customer getById(Integer id) {
		EntityManager em = emf.createEntityManager();
		
		return em.find(Customer.class, id);
	}

	@Override
	public Customer saveOrUpdate(Customer domainObject) {
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		if(domainObject.getUser() != null && domainObject.getUser().getPassword() != null) {
			domainObject.getUser().setEncryptedPassword(encryptionService.encryptString(domainObject.getUser().getPassword()));
		}
		
		Customer savedCustomer = em.merge(domainObject);
		em.getTransaction().commit();
		
		return savedCustomer;
	}

	@Override
	public void deleteById(Integer id) {
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		em.remove(em.find(Customer.class,id));
		em.getTransaction().commit();
	}

}
