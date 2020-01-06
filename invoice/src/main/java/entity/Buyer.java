package entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import service.InvoiceService;

//TODO
//zrobic kolumny tel, email w tabeli buyer
//dodac dodawanie klientow
//dodac wyszukiwanie klientow z usuwaniem, jesli nie ma faktur na tego klienta
//sprobowac edytowanie zrobic(najlepiej poprzez usuniecie i dodanie)

@Entity
@Table(name="buyer")
public class Buyer {

	@Column(name="buyer_name")
	private String name;
	@Id()
	private String id; //w mysql jest to integer więc uwaga
	@Column(name="street")
	private String street;
	@Column(name="city")
	private String city;
	@Column(name="postCode")
	private String postCode;
	@Column(name="state")
	private String state;
	@Column(name="typ")
	private String type;
	@Column(name="nr_tel")
	private String nr_tel;
	@Column(name="email")
	private String email;
	@OneToMany(mappedBy="buyer")
	private List<Invoice> invoices;
	
	@Transient 
	Hyperlink buyerLink;
	
	@Transient
	Hyperlink delete;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Invoice> getInvoices() {
		return invoices;
	}
	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}
	public String getNr_tel() {
		return nr_tel;
	}
	public void setNr_tel(String nr_tel) {
		this.nr_tel = nr_tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Hyperlink getBuyerLink() {
		return buyerLink;
	}
	public void setBuyerLink() {
		buyerLink = new Hyperlink(id);
		buyerLink.setId(this.getId().toString());
	}
	public Hyperlink getDelete() {
		return delete;
	}
	public void setDelete() {
		delete = new Hyperlink("Usuń");
		delete.setId("d"+this.getId().toString());
	}
	public void setOnActionLink(EventHandler<ActionEvent> h)
	{
		buyerLink.setOnAction(h);
	}
	public void setOnActionDelete(EventHandler<ActionEvent> h)
	{
		delete.setOnAction(h);
	}
	
}
