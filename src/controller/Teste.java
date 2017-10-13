package controller;

import java.util.List;

import model.sqlite.Contato;
import model.sqlite.ContatoDAO;

public class Teste {

	public static void main(String[] args) {
		BuscaNiverController bnc = new BuscaNiverController();
		//bnc.buscarNome();
		Contato c = new Contato();
		
		ContatoDAO cd = new ContatoDAO();
		
		List<Contato> cts = cd.listarData();
		for(Contato cdd:cts) {
			System.out.println(cdd.toString());
		}
	}
}	


