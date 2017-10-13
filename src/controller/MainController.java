package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import model.sqlite.Contato;
import model.sqlite.ContatoDAO;

public class MainController {
	protected ContatoDAO agendaDb; 
	
	@FXML
	protected ListView<Contato> lvContatos;
	
	@FXML
	protected void initialize() {
		agendaDb = new ContatoDAO();
		Main.addOnChangeScreenListener(new Main.OnChangeScreen() {
			
			@Override
			public void onScreenChanged(String newScreen, Object userData) {
				if(newScreen.equals("main")) {
					System.out.println("Entrou no onScreenChanged");
					updateList();
				}
			}
		});
		updateList();	
	}

	private void updateList() {
		lvContatos.getItems().clear();
		List<Contato> cts = agendaDb.listarData();
		
		for(Contato l:cts) {
			lvContatos.getItems().add(l);
			System.out.print("Entrou no initialize");
		}	
	}
	@FXML
	protected void btnNovo(ActionEvent e) {
		Main.changeScreen("inserir");
	}
	@FXML
	protected void btEditar(ActionEvent e) {
		ObservableList<Contato> ol = lvContatos.getSelectionModel().getSelectedItems();
		
		if(!ol.isEmpty()) {
			Contato c = ol.get(0);
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Deseja alterar o contato?");
			alert.setContentText(c.toString());
			
			Optional<ButtonType> result = alert.showAndWait(); //Caixa de diálogo de pergunta
			
			if(result.get() == ButtonType.OK) {
				agendaDb.atualizarConta(c);
				Main.changeScreen("inserir");
			}
		}
	}
	@FXML
	protected void btnBuscarNome(ActionEvent e) {
		Main.changeScreen("buscaNome");
	}
	@FXML
	protected void btnBuscarNiver(ActionEvent e) {
		Main.changeScreen("buscaNiver");
	}
	
	@FXML
	protected void btnDeletar(ActionEvent e) {
		
		ObservableList<Contato> ol = lvContatos.getSelectionModel().getSelectedItems();
		
		
		if(!ol.isEmpty()) {
			Contato c = ol.get(0);
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Deseja realmente excluir o contato?");
			alert.setContentText(c.toString());
			
			Optional<ButtonType> result = alert.showAndWait(); //Caixa de diálogo de pergunta
			
			if(result.get() == ButtonType.OK) {
				agendaDb.deletarContato(c.getId());
				updateList();
			}
		}else {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Erro");
			alert.setHeaderText("Nenhum contato selecionado");
			alert.setContentText("Selecione um contato");
			alert.showAndWait();
		}
	
	}
}
