package guru.springframework.services.jpaservices;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.domain.User;
import guru.springframework.services.UserService;
import guru.springframework.services.security.EncryptionService;

@Service
@Profile("jpadao")
public class UserServiceJpaDaoImpl extends AbstractJpaDaoService implements UserService {
	private EncryptionService encryptionService;

	@Autowired
	public void setEncryptionService(EncryptionService encryptionService) {
		this.encryptionService = encryptionService;
	}
	
	@Override
	public List<?> listAll(){
		EntityManager em = emf.createEntityManager();
		
		return em.createQuery("from  User", User.class).getResultList();
	}
	
	@Override
	public User saveOrUpdate(User domainObject) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		if(domainObject.getPassword() != null) {
			domainObject.setEncryptedPassword(encryptionService.encryptString(domainObject.getPassword()));
		}
		
		User savedUser = em.merge(domainObject);
		
		em.getTransaction().commit();
		
		return savedUser;
	}
	
	@Override
	public void deleteById(Integer id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.find(User.class, id));
		em.getTransaction().commit();
		System.out.println("Deleted Id : " + id);
	}

	@Override
	public User getById(Integer id) {
		EntityManager em = emf.createEntityManager();
		return em.find(User.class, id);
	}
	
}
