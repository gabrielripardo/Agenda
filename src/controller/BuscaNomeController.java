package controller;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.sqlite.Contato;
import model.sqlite.ContatoDAO;

public class BuscaNomeController {
	protected ContatoDAO agendaDb; 
	
	@FXML
	protected ListView<Contato> lvContatos;
	
	@FXML
	protected TextField txNome;
	
	@FXML
	protected void initialize() {
		agendaDb = new ContatoDAO();	
	}

	private void updateList() {
		lvContatos.getItems().clear();
		List<Contato> cts = agendaDb.buscarNome(txNome.getText());
		
		for(Contato l:cts) {
			lvContatos.getItems().add(l);
		}	
	}
	@FXML
	protected void btnBuscarAction(ActionEvent e) {
		System.out.println("txNome=> "+txNome.getText());
		updateList();
	}
	@FXML
	protected void btnOkAction(ActionEvent e) {
		Main.changeScreen("main");
	}
	@FXML
	protected void btnCancelarAction(ActionEvent e) {
		Main.changeScreen("main");
	}
}

