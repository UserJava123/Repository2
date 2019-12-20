package entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class YourCompany {

	private static final YourCompany INSTANCE = new YourCompany();
	
	private YourCompany() {}
	private final String name = "NAZWA TWOJEJ FIRMY";
	private final String id  = "NIP TWOJEJ FIRMY"; 
	private final String street = "UL TWOJEJ FIRMY";
	private final String city = "MIASTO TWOJEJ FIRMY";
	private final String postCode = "KOD POCZTOWY TWOJEJ FIRMY";
	private final String state = "PAŃSTWO TWOJEJ FIRMY";
	private final String type = "TYP TWOJEJ FIRMY";

	public static YourCompany getInstance() {
		return INSTANCE;
	}
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}
	public String getStreet() {
		return street;
	}
	public String getCity() {
		return city;
	}
	public String getPostCode() {
		return postCode;
	}
	public String getState() {
		return state;
	}
	public String getType() {
		return type;
	}
	
	
	
}
