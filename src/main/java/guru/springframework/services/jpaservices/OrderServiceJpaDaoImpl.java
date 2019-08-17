package guru.springframework.services.jpaservices;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.domain.Order;
import guru.springframework.services.OrderService;

@Service
@Profile("jpadao")
public class OrderServiceJpaDaoImpl extends AbstractJpaDaoService implements OrderService {
	
	@Override
	public List<?> listAll() {
		EntityManager em = emf.createEntityManager();
		return em.createQuery("from Order", Order.class).getResultList();
	}

	@Override
	public Order getById(Integer id) {
		EntityManager em = emf.createEntityManager();
		return em.find(Order.class, id);	
	}

	@Override
	public Order saveOrUpdate(Order domainObject) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Order savedOrder = em.merge(domainObject);
		em.getTransaction().commit();
		
		return savedOrder;
	}

	@Override
	public void deleteById(Integer id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.find(Order.class, id));
		em.getTransaction().commit();
	}

	

}
