package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;

@Entity
@Table(name="user")
public class User {

	@Id
	@Column(name="login")
	private String login;
	@Column(name="password")
	private String password;
	@Column(name="type")
	private String type;
	
	@Transient
	private Hyperlink deleteLink;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setOnActionDelete(EventHandler<ActionEvent> h)
	{
		deleteLink.setOnAction(h);
	}
	public void setDeleteLink() {
		deleteLink = new Hyperlink("Usuń");
		deleteLink.setId("d"+this.getLogin());
	}
	public Hyperlink getDeleteLink() {
		return deleteLink;
	}
	public void setDeleteLink(Hyperlink deleteLink) {
		this.deleteLink = deleteLink;
	}
	
	
}
