package guru.springframework.services.jpaservices;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.domain.security.Role;
import guru.springframework.services.RoleService;

@Service
@Profile("jpadao")
public class RoleServiceJpaImpl extends AbstractJpaDaoService implements RoleService {

	@Override
	public List<?> listAll() {
		EntityManager em = emf.createEntityManager();
		
		return em.createQuery("from Role", Role.class).getResultList();
	}

	@Override
	public Role getById(Integer id) {
		EntityManager em = emf.createEntityManager();
		
		return em.find(Role.class,id);
	}

	@Override
	public Role saveOrUpdate(Role domainObject) {
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		Role saveRole = em.merge(domainObject);
		em.getTransaction().commit();
		
		return saveRole;
	}

	@Override
	public void deleteById(Integer id) {
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction();
		em.remove(em.find(Role.class, id));
		em.getTransaction().commit();
	}

}
