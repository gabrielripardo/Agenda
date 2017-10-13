package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.sqlite.Contato;
import model.sqlite.ContatoDAO;

public class InserirController {
	
	@FXML
	protected TextField tfNome;
	
	@FXML
	protected TextField tfTelefone;
	
	@FXML
	protected TextField tfEmail;
	
	@FXML
	protected TextField tfTwitter;
	
	@FXML
	protected TextField tfAdress;
	
	@FXML
	protected TextField tfdia;
	
	@FXML
	protected TextField tfmes;
	
	@FXML
	protected TextField tfano;
	
	@FXML
	protected void btnCancelAction(ActionEvent e) {
		Main.changeScreen("main");
	}
	@FXML
	protected void btnOkAction(ActionEvent e) {
		System.out.print("Clicou");
		try {
			if(tfNome.getText().isEmpty()) {
				throw new RuntimeException("O campo nome não pode ser vazio");
			}
			if(tfTelefone.getText().isEmpty()) {
				throw new RuntimeException("O campo telefone não pode ser vazio");
			}
			ContatoDAO cd = new ContatoDAO();
			Contato ct = new Contato();
			ct.setNome(tfNome.getText());
			ct.setTelefone(tfTelefone.getText());
			ct.setEmail(tfEmail.getText());
			ct.setTwitter(tfTelefone.getText());
			ct.setAddress(tfAdress.getText());
			ct.setDia(Integer.parseInt(tfdia.getText()));
			ct.setMes(Integer.parseInt(tfmes.getText()));
			ct.setAno(Integer.parseInt(tfano.getText()));
			
			cd.adicionarContato(ct);
		}catch(RuntimeException re) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Error ao criar contato");
			alert.setContentText(re.getMessage());
			alert.showAndWait();
		}
	}
	public String concatenarNascimento() {
		while(true) {
			int dia = Integer.parseInt(tfdia.getText());
			int mes = Integer.parseInt(tfmes.getText());
			int ano = Integer.parseInt(tfano.getText());
			
			String dt = dia+"/"+mes+"/"+ano;
			
			if(dia > 0 && dia <= 31 && mes > 0 && mes <= 12 && ano >= 1900 && ano < 5000) { // Verifica se a entrada é uma data.
				if(dia < 10 && mes < 10) {
					dt = "0"+dia+"/"+"0"+mes+"/"+ano;
				}if(dia < 10 && mes > 10) {
					dt = "0"+dia+"/"+mes+"/"+ano;
				}if(dia > 10 && mes < 10) {
					dt = dia+"/"+"0"+mes+"/"+ano;
				}
				return dt;
			}else {	
				return "Sintaxe Incorreta!";
			}
		}
	}
}
