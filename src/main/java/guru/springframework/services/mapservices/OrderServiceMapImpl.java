package guru.springframework.services.mapservices;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.domain.DomainObject;
import guru.springframework.domain.Order;
import guru.springframework.services.OrderService;

@Service
@Profile("map")
public class OrderServiceMapImpl extends AbstractMapService implements OrderService {
	@Override
	public List<DomainObject> listAll(){
		return super.listAll();
	}
	
	@Override
	public Order getById(Integer id) {
		return (Order) super.getById(id);
	}
	
	@Override
	public Order saveOrUpdate(Order domainObject) {
		return (Order) super.saveOrUpdate(domainObject);
	}
	
	@Override
	public void deleteById(Integer id) {
		super.deleteById(id);
	}
}
