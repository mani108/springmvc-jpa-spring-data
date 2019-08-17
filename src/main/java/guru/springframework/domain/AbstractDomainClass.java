package guru.springframework.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

@MappedSuperclass
public class AbstractDomainClass implements DomainObject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private  Integer id;
	
	@Version
	private Integer version; 
	
	private Date dateCreated;
	private Date dateUpdated;
	
	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;

	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	@PreUpdate
	@PrePersist
	public void updatetimeStamp() {
		dateUpdated = new Date();
		if(dateCreated == null)
			dateCreated = new Date();
	}
}
