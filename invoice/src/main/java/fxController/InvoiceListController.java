package fxController;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import DAO.InvoiceDAO;
import DAO.PositionDAO;
import entity.Invoice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.InvoiceService;
import javafx.scene.control.TableView;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;

public class InvoiceListController implements Initializable
{
	
	private ObservableList<entity.Invoice> invoices =
	        FXCollections.observableArrayList();
	
	private ObservableList<String> filters =
	        FXCollections.observableArrayList(); 
	
	private InvoiceDAO invoiceDAO;
	private PositionDAO positionDAO;
	
	@FXML
	private TableView<Invoice> tabInvoice;
	@FXML
	private TableColumn<Invoice,Hyperlink> colNr;
	@FXML
	private TableColumn<Invoice,String> colBuyerName;
	@FXML
	private TableColumn<Invoice,String> colBuyerType;
	@FXML
	private TableColumn<Invoice,Integer> colNip;
	@FXML
	private TableColumn<Invoice,BigDecimal> colNetto;
	@FXML
	private TableColumn<Invoice,BigDecimal> colVat;
	@FXML
	private TableColumn<Invoice,BigDecimal> colBrutto;
	@FXML
	private TableColumn<Invoice,Boolean> colPaid;
	@FXML
	private TableColumn<Invoice,LocalDate> colDate;
	@FXML
	private TableColumn<Invoice,Hyperlink> colDelete;
	@FXML
	private Button btnDeleteFiltr;
	@FXML
	private Button btnFiltr;
	@FXML
	private ChoiceBox<String> choiceFiltr;
	@FXML
	private TextField tfFiltr;

	//zamienic Nip na 9 cyfrowy
	//zrobic zmienna globalna twoja firma z mozliwoscia zmiany danych
	public void initialize(URL location, ResourceBundle resources)
	{	
		colNr.setCellValueFactory(
                new PropertyValueFactory<Invoice, Hyperlink>("invoiceLink"));
		
		colBuyerName.setCellValueFactory(
                new PropertyValueFactory<Invoice, String>("buyerName"));
		
		colBuyerType.setCellValueFactory(
                new PropertyValueFactory<Invoice, String>("buyerType"));
		
		colNip.setCellValueFactory(
                new PropertyValueFactory<Invoice, Integer>("buyerNip"));
		
		colNetto.setCellValueFactory(
                new PropertyValueFactory<Invoice, BigDecimal>("nettoSum"));
		
		colVat.setCellValueFactory(
                new PropertyValueFactory<Invoice, BigDecimal>("vatSum"));
		
		colBrutto.setCellValueFactory(
                new PropertyValueFactory<Invoice, BigDecimal>("bruttoSum"));
		
		colPaid.setCellValueFactory(
                new PropertyValueFactory<Invoice, Boolean>("paid"));
		
		colDate.setCellValueFactory(
                new PropertyValueFactory<Invoice, LocalDate>("dateOfInvoice"));
		
		colDelete.setCellValueFactory(
                new PropertyValueFactory<Invoice, Hyperlink>("delete"));
		
		invoiceDAO = new InvoiceDAO();
		positionDAO = new PositionDAO();
		setInvoices(invoiceDAO.getInvoices());
		tabInvoice.setItems(invoices);
		
		filters.add("Nazwa Nabywcy");
		filters.add("Nr Faktury");
		filters.add("Nip/Pesel");
		filters.add("Data");
		
		choiceFiltr.setItems(filters);
	}
	
	public void setInvoices(List<Invoice> ar)
	{
		if(!invoices.isEmpty())
			invoices.clear();
		if(!ar.isEmpty()) {
			
			for(Invoice i: ar)
			{
				i.setInvoiceLink();
				i.setOnActionLink(edit);
				i.setDelete();
				i.setOnActionDelete(delete);
				invoices.add(i);
			}
		}
	}
	
	@FXML
	public void deleteFiltr(ActionEvent e)
	{
		setInvoices(invoiceDAO.getInvoices());
	}
	
	@FXML
	public void filtr(ActionEvent e)
	{
		if(choiceFiltr.getValue() == "Nr Faktury")
		{
			setInvoices(invoiceDAO.getInvoicesById(InvoiceService.getOriginalInvoiceNumber(tfFiltr.getText())));
			tabInvoice.setItems(invoices);
		}
		else if(choiceFiltr.getValue() == "Nazwa Nabywcy")
		{
			setInvoices(invoiceDAO.getInvoicesByBuyerName(tfFiltr.getText()));
			tabInvoice.setItems(invoices);
		}
		else if(choiceFiltr.getValue() == "Nip/Pesel")
		{
			setInvoices(invoiceDAO.getInvoicesByBuyerId(tfFiltr.getText()));
			tabInvoice.setItems(invoices);
		}	
		else if(choiceFiltr.getValue() == "Data")
		{
			setInvoices(invoiceDAO.getInvoicesByDate(tfFiltr.getText()));
			tabInvoice.setItems(invoices);
		}
	}
	
	public void openEdit(Invoice i) throws IOException
	{		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("newInvoice.fxml"));

		VBox box = loader.load(); 

		AddInvoiceController aic = loader.<AddInvoiceController>getController();
		aic.initData(i);
			
		Scene scene = new Scene(box);			
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
	
	EventHandler<ActionEvent> edit = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {
			Hyperlink hl = (Hyperlink) event.getSource();
			Invoice i = invoiceDAO.getInvoiceById(hl.getId());
			try {
				openEdit(i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	EventHandler<ActionEvent> delete = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {
			Hyperlink hl = (Hyperlink) event.getSource();
			String id = hl.getId().substring(1);
			Invoice i = invoiceDAO.getInvoiceById(id);
			positionDAO.deleteByInvoice(i);
			invoiceDAO.deleteInvoice(i);
			setInvoices(invoiceDAO.getInvoices());
		}
	};
}
	
