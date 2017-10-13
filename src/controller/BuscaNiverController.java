package controller;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.sqlite.Contato;
import model.sqlite.ContatoDAO;

public class BuscaNiverController {

	protected ContatoDAO agendaDb; 
	
	@FXML
	protected ListView<Contato> lvContatos;
	
	@FXML
	protected TextField txMes;
	
	@FXML
	protected void initialize() {
		agendaDb = new ContatoDAO();
		
	}
	
	private void updateList() {
		try {
			
		List<Contato> cts = agendaDb.listarAniversariantesdomes(Integer.parseInt(txMes.getText()));

		for(Contato l:cts) {
			System.out.print(">>>" + l );
			lvContatos.getItems().add(l);
		}	
		}catch(NumberFormatException nfe) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro ao buscar contato");
			alert.setContentText("Digite um valor númerico");
			alert.showAndWait();
		}
	}
	public int converterMes() {
		int mes = Integer.parseInt(txMes.getText());
		if(mes > 1 && mes <= 12) {
			return mes;
		}
		return 0;
	}
	@FXML
	protected void btnBuscarAction(ActionEvent e) {
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



