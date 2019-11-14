package entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import service.Calculator;

@Entity
@Table(name="invoice_position")
public class Position {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="nr")
	private int nr;
	@Column(name="quantity")
	private BigDecimal quantity;
	@ManyToOne
	@JoinColumn(name="invoice")
	private Invoice invoice;
	@Column(name="product_name")
	private String name;
	@Column(name="unit")
	private String unit;
	@Column(name="price")
	private BigDecimal price;
	@Column(name="vat")
	private BigDecimal vat;
	
	@Transient
	private Hyperlink linkDelete;
	@Transient
	private BigDecimal nettoSum;
	@Transient
	private BigDecimal bruttoSum;
	@Transient
	private BigDecimal vatSum;
	
	public Position()
	{
		setLinkDelete();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getVat() {
		return vat;
	}
	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}	
	public int getNr() {
		return nr;
	}
	public void setNr(int nr) {
		this.nr = nr;
	}
	
	public BigDecimal getNettoSum() {
		return Calculator.countNettoSumOfPosition(price, quantity);
	}
	public BigDecimal getBruttoSum() {
		return Calculator.countBruttoSumOfPosition(price, quantity, vat);
	}
	public BigDecimal getVatSum() {
		return Calculator.countVatSumOfPosition(price, quantity, vat);
	}
	public Hyperlink getLinkDelete()
	{
		return this.linkDelete;
	}
	public void setLinkDelete()
	{
		this.linkDelete = new Hyperlink("Delete");
		linkDelete.setId(new Integer(this.getNr()).toString());
	}
	public void setLinkDeleteId(String id)
	{	
		linkDelete.setId(id);
	}
	public void setOnLinkDelete(EventHandler<ActionEvent> eh)
	{
		linkDelete.setOnAction(eh);
	}
	
}
