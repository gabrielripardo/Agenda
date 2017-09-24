/**
 * Agenda em Console
 * @author Gabriel Ripardo
 * @date 09-23-2017
 * @descrition Uma simples agenda que adiciona, lista, altera e remove contatos em um banco de dados SQLite.
 */


import java.util.InputMismatchException;

public class Start {
	public static void main(String[] args) {
		PrintContato agenda = new PrintContato();
		boolean restart = false;
		do {
			System.out.println("============================================");
			System.out.println("============================================");
			System.out.println("============ Agenda de Contatos ============");
			System.out.println("============================================");
			System.out.println("================== Início ==================\n                                      "
					+ "======");
			System.out.println("1.Inserir um contato                 =======");
			System.out.println("2.Alterar um contato                 =======");
			System.out.println("3.Buscar todos os contatos           =======");
			System.out.println("4.Buscar pelo nome                   =======");
			System.out.println("5.Buscar pelo mês de aniverário      =======");
			System.out.println("6.Apagar contato                     =======");
			System.out.println("============================================");
			System.out.print("Escolha uma opção: ");
			
			try {
				int opcao = agenda.entry.nextInt();
				
				String choosed = null;
				restart = false;
				
					switch(opcao) {
					case 1:
						while(true) {
							agenda.limparTela();
							System.out.println(">>> Novo contato <<<");
							boolean ok = agenda.inserirContato();
							if(ok == true) {
								System.out.println("Contato adicionado com Sucesso!\n");
							}
							else {
								System.out.print("Erro ao adicionar contato");
							}
							System.out.println("\nDeseja continuar? s/n: ");
							choosed = agenda.entry.next();
							if(choosed.toLowerCase().equals("s")) {
								continue;
							}else {
								agenda.limparTela();
								restart = true;
								break;
							}
						}
						break;
					case 2:
						while(true) {
							agenda.limparTela();
							System.out.println("*********** Alterar Contato ***********");
							boolean ok = agenda.alterarContato();
							if(ok == true) {
								System.out.println("Contato alterado com sucesso!\n");
							}
							else {
								System.out.print("Erro ao alterar contato");
							}
							System.out.println("\nDeseja continuar? s/n: ");
							choosed = agenda.entry.next();
							if(choosed.toLowerCase().equals("s")) {
								continue;
							}else {
								agenda.limparTela();
								restart = true;
								break;
							}
						}
						break;
					case 3:
						while(true) {
							agenda.limparTela();
							System.out.println("\n@@@@@@@@@@@@@@@@@@@@ Todos os contatos @@@@@@@@@@@@@@@@@@" );						
							boolean ok = agenda.buscarTodos();
							if(ok == false) {
								System.out.println("Nenhum contato foi adicionado!");
							}
							System.out.println("\nDeseja continuar? s/n: ");
							choosed = agenda.entry.next();
							if(choosed.toLowerCase().equals("s")) {
								continue;
							}else {
								agenda.limparTela();
								restart = true;
								break;
							}
						}
						break;
					case 4:
						while(true) {
							agenda.limparTela();
							System.out.println("&&&&&&&&&&&&&&&&&&&&&&&& Busca por nome &&&&&&&&&&&&&&&&&&&&&&&&&&&");
							agenda.buscarPorNome();
							System.out.println("\nDeseja continuar? s/n: ");
							choosed = agenda.entry.next();
							if(choosed.toLowerCase().equals("s")) {
								continue;
							}else {
								agenda.limparTela();
								restart = true;
								break;
							}
						}
						break;
					case 5:
						while(true) {
							agenda.limparTela();
							System.out.println(":::::::::::::::::::::: Busca por mês de aniversário ::::::::::::::::::::::");
							boolean ok = agenda.buscarPorAniversario();
							if(ok == false) {
								System.out.println("Nenhum contato encontrado");
							}
							System.out.println("\nDeseja continuar? s/n: ");
							choosed = agenda.entry.next();
							if(choosed.toLowerCase().equals("s")) {
								continue;
							}else {
								agenda.limparTela();
								restart = true;
								break;
							}
						}
						break;
					case 6:
						while(true) {
							agenda.limparTela();
							boolean ok = agenda.deletar();
							if(ok) {
								System.out.println("Contato deletado com sucesso!\n");
							}else {
								System.out.println("Erro ao deletar contato!");
							}
							System.out.println("\nDeseja continuar? s/n: ");
							choosed = agenda.entry.next();
							if(choosed.toLowerCase().equals("s")) {
								continue;
							}else {
								agenda.limparTela();
								restart = true;
								break;
							}
						}
						break;
					default:
						System.out.println("Opção desconhecida!\nTente novamente:");
						restart = true;
				}
			}catch(InputMismatchException e) {
				agenda.limparTela();
				System.out.println("\nSintaxe Incorreta!\n> Digite uma opção de 1 à 6");
				main(args);
			}
		}while(restart);
	}
}
