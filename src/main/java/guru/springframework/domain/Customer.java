package guru.springframework.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Customer extends AbstractDomainClass implements DomainObject {
	
	private String firstName;
	private String lastName;
	private String phoneNumber;
	
	@Embedded
	private Address billingAddress;
	
	///@Embedded
	//private Address shippingAddress;

	
	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST},orphanRemoval = true)
	private User user;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public Address getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}
//	public Address getShippingAddress() {
//		return shippingAddress;
//	}
//	public void setShippingAddress(Address shippingAddress) {
//		this.shippingAddress = shippingAddress;
//	}
} 
