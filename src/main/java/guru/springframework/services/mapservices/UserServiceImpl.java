package guru.springframework.services.mapservices;

import java.util.List;

import guru.springframework.domain.DomainObject;
import guru.springframework.domain.User;
import guru.springframework.services.UserService;

public class UserServiceImpl extends AbstractMapService implements UserService {

	@Override
	public List<DomainObject> listAll(){
		return super.listAll();
	}
	
	@Override
	public User getById(Integer id) {
		return (User) super.getById(id);
	}

	@Override
	public User saveOrUpdate(User domainObject) {
		return (User) super.saveOrUpdate(domainObject);
	}
		
	@Override
	public void deleteById(Integer id) {
		super.deleteById(id);
	}
	
}
