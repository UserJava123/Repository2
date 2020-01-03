package mycompany.invoice;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.javafx.logging.Logger;

import DAO.JpaInitializer;
import DAO.SellerDAO;
import entity.Seller;
import entity.User;
import entity.YourCompany;
import javafx.event.ActionEvent;

public class MainViewController implements Initializable{
	
	private YourCompany seller;
	@FXML
	private BorderPane bpIndex;
	@FXML
	private Button btnAddInvoice;
	@FXML
	private MenuItem miSeller;
	@FXML
	private Label lName;
	@FXML
	private Label lNip;
	@FXML
	private Label lUser;
	@FXML
	private Label lAdres;
	private User user;

	// Event Listener on Button[#btnAddInvoice].onAction
	@FXML
	public void addInvoice(ActionEvent event) throws IOException {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("newInvoice.fxml"));

			VBox box = loader.load(); 
			
			AddInvoiceController aic = loader.<AddInvoiceController>getController();
			aic.initData(null);
		
			Scene scene = new Scene(box);
		
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch ( javax.persistence.RollbackException e)
		{
			Alert alert = new Alert(AlertType.ERROR, "Brak uprawnień do wykonania akcji", ButtonType.CANCEL);
			alert.showAndWait();
		}
	}
	
	public void setSeller(MouseEvent e)
	{
		lName.setText(seller.getName());
		lNip.setText("NIP: " + seller.getId());
		lAdres.setText(seller.getCity() +",\n" + seller.getStreet() +",\n" +seller.getPostCode());
	}

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		seller = YourCompany.getInstance();
		setSeller(null);
	}
	
	public void setUser(User user)
	{
		this.user = user;
		lUser.setText("User:" + user.getLogin());
	}
	
	public void addUser(ActionEvent e) throws IOException
	{
		if(user.getType().equals("boss")) {
		
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("addUser.fxml"));

			AnchorPane box = loader.load(); 
		
			Scene scene = new Scene(box);
			Stage primarystage = new Stage();
			primarystage.setScene(scene);
			primarystage.setTitle("Rejestracja");
			primarystage.show();
		}
		else {
			Alert alert = new Alert(AlertType.ERROR, "Brak uprawnień do wykonania akcji", ButtonType.CANCEL);
			alert.showAndWait();
		}
	}
	
	public void logOut(ActionEvent e) throws IOException
	{

		Stage stage = (Stage) bpIndex.getScene().getWindow();
		stage.close();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("log.fxml"));

		AnchorPane box = loader.load(); 

		Scene scene = new Scene(box);
		Stage primarystage = new Stage();
		primarystage.setScene(scene);
		primarystage.setTitle("Faktury");
		primarystage.show();
	}
	
	public void showUsers(ActionEvent e) throws IOException
	{
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("showUser.fxml"));

		AnchorPane box = loader.load(); 

		Scene scene = new Scene(box);
		Stage primarystage = new Stage();
		primarystage.setScene(scene);
		primarystage.setTitle("Faktury");
		primarystage.show();
	}
	
	public void save(ActionEvent e) throws InterruptedException
	{
		if(!user.getType().equals("boss")) return;
		FileChooser fileChooser = new FileChooser();
		final File file = fileChooser.showSaveDialog((Stage) bpIndex.getScene().getWindow());
        if (file != null) {
        	try {
        		System.out.println(file.getPath());
        		String[] command = new String[] {"cmd.exe", "/c", 
        				"\"C:/Program Files/MySQL/MySQL Server 8.0/bin/mysqldump.exe\" "
        				+ "--user=\"root\" "
        				+ "--password=\"root\" tpLab2 "};
        		String cmd = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\"
        				+ "mysqldump.exe -u root -proot tpLab2 > " 
        				+ file.getPath();
                final Process p = Runtime.getRuntime().exec(command);
                
                if(p!=null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                try(BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(p.getInputStream()))); 
                                    BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                                    String line;
                                    while((line=reader.readLine())!=null)
                                    { 
                                        writer.write(line);
                                        writer.newLine();
                                    }
                                }
                            } catch(Exception ex){
                                // handle or log exception ...
                            }
                        }
                    }).start();
                }
                if(p!=null && p.waitFor()==0) {
                    System.out.println("Sukces");
                } else {
                	System.out.println("Porażka");
                }
        	} catch (IOException ex) {
                ex.printStackTrace();
            }
        }
	}
	
	public void restore(ActionEvent e)
	{
		if(!user.getType().equals("boss")) return;
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog((Stage) bpIndex.getScene().getWindow());
        if (file != null) {
        	try {
                Desktop.getDesktop().open(file);
                if(file.getPath().endsWith(".sql"))
                Runtime.getRuntime().exec("mysql -u root -proot tpLab2 < " + file.getPath());
            } catch (IOException ex) {
                
                    
            }
        }
	}
}
