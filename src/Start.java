/**
 * Agenda em Console
 * @author Gabriel Ripardo
 * @date 08/09/2017
 * @descrition Uma simples agenda que adiciona, lista e remove contatos em um banco de dados SQLite.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;

public class Start {
	public static void main(String[] args) {
		Connection conexao = null;
		PrintContato agenda = null;
		boolean restart = false;
		do {
			System.out.println("============================================");
			System.out.println("============================================");
			System.out.println("============ Agenda de Contatos ============");
			System.out.println("============================================");
			System.out.println("================== Início ==================\n                                      "
					+ "======");
			System.out.println("1. Inserir um contato                 ======");
			System.out.println("2. Buscar todos os contatos           ======");
			System.out.println("3. Buscar contato pelo nome           ======");
			System.out.println("4. Apagar contato                     ======");
			System.out.println("============================================");
			System.out.print("Escolha uma opção: ");
			
			try {
				conexao = new ConnectionAgenda().getConnection();
				agenda = new PrintContato(conexao);
				
				agenda.verificarBanco();
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
					case 3:
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
					case 4:
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
				System.out.println("\nSintaxe Incorreta!\n> Digite uma opção de 1 à 4");
				main(args);
			}
			finally {
				try {
					conexao.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}while(restart);
	}
}
