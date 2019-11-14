package entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import service.InvoiceService;

@Entity
@Table(name="invoice")
public class Invoice {

	@Id
	private BigDecimal number;
	
	@Column(name="placeofinvoice")
	private String placeOfInvoice;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="buyer")
	private Buyer buyer;
	
	@Transient
	private Seller seller;
	
	@OneToMany(mappedBy="invoice")
	private List<Position> positions;
	
	@Transient
	private BigDecimal nettoSum;
	
	@Transient
	private BigDecimal bruttoSum;
	
	@Transient
	private BigDecimal vatSum;
	
	@Column(name="dateOfPayment")
	private LocalDate dateOfPayment;
	
	@Column(name="dateOfSale")
	private LocalDate dateOfSale;
	
	@Column(name="dateOfInvoice")
	private LocalDate dateOfInvoice;
	
	@Column(name="comments")
	private String comments;
	
	@Column(name="paid")
	private boolean paid;
	
	@Column(name="alreadyPaid")
	private BigDecimal alreadyPaid;
	
	/*
	 * Fields filling the table 
	 */
	@Transient 
	Hyperlink invoiceLink;
	
	@Transient
	Hyperlink delete;
	
	@Transient
	String buyerName;
	
	@Transient
	Integer buyerNip;
	
	@Transient
	String buyerType;
	
	public BigDecimal getNumber() {
		return number;
	}
	public void setNumber(BigDecimal number) {
		this.number = number;
	}
	public String getPlaceOfInvoice() {
		return placeOfInvoice;
	}
	public void setPlaceOfInvoice(String placeOfInvoice) {
		this.placeOfInvoice = placeOfInvoice;
	}
	public Buyer getBuyer() {
		return buyer;
	}
	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	public List<Position> getPositions() {
		return positions;
	}
	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}
	public BigDecimal getNettoSum() {
		BigDecimal netto=new BigDecimal(0);
		for(Position p: this.getPositions())
		{
			netto = netto.add(p.getNettoSum());
		}
		return netto;
	}
	public void setNettoSum(BigDecimal nettoSum) {
		this.nettoSum = nettoSum;
	}
	public BigDecimal getBruttoSum() {
		BigDecimal brutto=new BigDecimal(0);
		for(Position p: this.getPositions())
		{
			brutto = brutto.add(p.getBruttoSum());
		}
		return brutto;
	}
	public void setBruttoSum(BigDecimal bruttoSum) {
		this.bruttoSum = bruttoSum;
	}
	public BigDecimal getVatSum() {
		BigDecimal vat=new BigDecimal(0);
		for(Position p: this.getPositions())
		{
			vat = vat.add(p.getVatSum());
		}
		return vat;
	}
	public void setVatSum(BigDecimal vatSum) {
		this.vatSum = vatSum;
	}
	public LocalDate getDateOfPayment() {
		return dateOfPayment;
	}
	public void setDateOfPayment(LocalDate dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}
	public LocalDate getDateOfSale() {
		return dateOfSale;
	}
	public void setDateOfSale(LocalDate dateOfSale) {
		this.dateOfSale = dateOfSale;
	}
	public LocalDate getDateOfInvoice() {
		return dateOfInvoice;
	}
	public void setDateOfInvoice(LocalDate dateOfInvoice) {
		this.dateOfInvoice = dateOfInvoice;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public BigDecimal getAlreadyPaid() {
		return alreadyPaid;
	}
	public void setAlreadyPaid(BigDecimal alreadyPaid) {
		this.alreadyPaid = alreadyPaid;
	}
	public Hyperlink getInvoiceLink() {
		return invoiceLink;
	}
	public void setInvoiceLink() {
		invoiceLink = new Hyperlink(InvoiceService.formatExistingInvoiceNumber(number));
		invoiceLink.setId(this.getNumber().toString());
	}
	public String getInvoiceLinkId()
	{
		return invoiceLink.getId();
	}

	public Hyperlink getDelete() {
		return delete;
	}
	public void setDelete() {
		delete = new Hyperlink("Usuń");
		delete.setId("d"+this.getNumber().toString());
	}
	public String getDeleteId()
	{
		return delete.getId();
	}
	public String getBuyerName() {
		return buyer.getName();
	}
	public String getBuyerNip() {
		return buyer.getId();
	}
	public String getBuyerType() {
		return buyer.getType();
	}
	public void setOnActionLink(EventHandler<ActionEvent> h)
	{
		invoiceLink.setOnAction(h);
	}
	public void setOnActionDelete(EventHandler<ActionEvent> h)
	{
		delete.setOnAction(h);
	}
}
