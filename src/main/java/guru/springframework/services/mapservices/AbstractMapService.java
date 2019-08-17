package guru.springframework.services.mapservices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import guru.springframework.domain.DomainObject;

public class AbstractMapService {
	protected Map<Integer, DomainObject> domainMap;
	
	public AbstractMapService() {
		domainMap = new HashMap<>();
	}
	
	public List<DomainObject> listAll(){
		return new ArrayList<>(domainMap.values());
	}
	
	public DomainObject saveOrUpdate(DomainObject domainObject) {
		if(domainObject != null) {
			if(domainObject.getId() == null) {
				domainObject.setId(getNextKey());
			}
			
			domainMap.put(domainObject.getId(), domainObject);
			
			return domainObject;
			
		}else {
			throw new RuntimeException("Object can't be null");
		}
	}
	
	public DomainObject getById(Integer id) {
		return domainMap.get(id);
	}
	
	public void deleteById(Integer id) {
		domainMap.remove(id);
	}
	
	private Integer getNextKey() {
		return Collections.max(domainMap.keySet()) + 1;
	}
}
