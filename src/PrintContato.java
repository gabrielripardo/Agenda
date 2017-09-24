/**
* @descrition Essa classe realiza a interação com o usuário. Ela aprimora as funcionalidades do banco.
* @date 09-23-2017
* @author Gabriel Ripardo
*/
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PrintContato extends ContatoDAO{
	public Contato conta;
	public Scanner entry;
	public boolean isEmpty; // Atibuto usado por dois métodos para verificar se há conteúdo na tabela
	
	public PrintContato(){
		entry = new Scanner(System.in);
		conta = new Contato();
		isEmpty = true;
	}
	public int verificarBanco() { // Verifica se tem conteúdo no Banco
		int ids = 0;
		List <Contato> listCts = super.listarData();
		for(Contato ct:listCts) {
			if(ct.getId() > 0) {
				ids++;
			}
		}		
		return ids;
	}
	public String concatenarNascimento() {
		while(true) {
			System.out.println("Data de Nascimento: ");
			System.out.print(" |---- Dia: ");
			int dia = entry.nextInt();
			System.out.print(" |---- Mês: ");
			int mes = entry.nextInt();
			System.out.print(" |---- Ano: ");
			int ano = entry.nextInt();
			
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
				System.out.println("Sintaxe Incorreta!");
				System.out.println("Tente novamente!\n");
			}
		}
	}
	
	public boolean inserirContato(){ // Insere um novo contato
		entry.nextLine();
		System.out.print("Nome: ");
		String nmCt = entry.nextLine();
		System.out.print("Telefone: ");
		String telCt = entry.nextLine();
		System.out.print("Email: ");
		String mailCt = entry.next();
		String dtNasCt = this.concatenarNascimento();
		System.out.print("Twitter: ");
		String twitterCt = entry.next();
		entry.nextLine();
		System.out.print("Endereço: ");
		String addressCt = entry.nextLine();

		conta.setNome(nmCt);
		conta.setTelefone(telCt);
		conta.setEmail(mailCt);
		conta.setDtNascimento(dtNasCt);
		conta.setTwitter(twitterCt);
		conta.setAddress(addressCt);
		
		int idsOld = this.verificarBanco(); // Verifica a quantidade de contatos atual
		
		super.adicionarContato(conta); // Adiciona o objeto contato no bd
		
		int idsNew = this.verificarBanco(); // Verifica se o novo contato foi adicionado
		if(idsNew > idsOld) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean buscarTodos(){
		int id = 1;
		List <Contato> listCts = super.listarData();
		boolean hasCt = false;
		for(Contato ct:listCts) {
			
			if(ct.getId() >= 0) {
				System.out.print(id+++" - Nome: "+ct.getNome()+" || ");
				System.out.print("Telefone: "+ct.getTelefone()+" || ");
				System.out.print("E-mail: "+ct.getEmail()+" || ");
				System.out.print("Data de Nascimento: "+ct.getDtNascimento()+" || ");
				System.out.print("Twitter: "+ct.getTwitter()+" || ");
				System.out.println("Endereço: "+ct.getAddress());
				
				hasCt = true;
			}
		}
		return hasCt;
	}
	public List<Contato> buscarPorNome(){
		isEmpty = true;
		List <Contato> listCtsNm = null; 
		if(this.verificarBanco() > 0) {
			System.out.print("Digite o nome: ");
			String nomCt = entry.next();
			
			int id = 1;
			listCtsNm = super.buscarNome(nomCt);
			for(Contato ct:listCtsNm) {
				if(ct.getId() > 0) {
					System.out.print(id+" - Nome: "+ct.getNome()+" || ");
					System.out.print("Telefone: "+ct.getTelefone()+" || ");
					System.out.println("E-mail: "+ct.getEmail());
					
					isEmpty = false;
					id++;
				}
			}
			if(isEmpty) {
				System.out.println("Este nome não existe!");
			}
		}else {
			System.out.println("Não há contatos!");
		}
		return listCtsNm;
	}
	boolean buscarPorAniversario() {
		System.out.print("Digite o mês: ");
		int mes = entry.nextInt();
		
		int id = 1;
		if(mes > 0 && mes <= 12) {
			List<Contato> nives = super.listarAniversariantesdomes(mes);
			for(Contato ct:nives) {
				System.out.print(id+++" - Nome: "+ct.getNome()+" || ");
				System.out.print("Telefone: "+ct.getTelefone()+" || ");
				System.out.print("E-mail: "+ct.getEmail()+" || ");
				System.out.print("Data de Nascimento: "+ct.getDtNascimento()+" || ");
				System.out.print("Twitter: "+ct.getTwitter()+" || ");
				System.out.println("Endereço: "+ct.getAddress());
			}
		}	
		else {
			System.out.println("Sintaxe desconhecida!");
		}
		if(id > 1) {
			return true;
		}
		else {
		return false;
		}
	}
	public boolean alterarContato() {
		boolean changed = false;
		this.buscarTodos();
		int id = 0;
		
		System.out.println("Digite o Id: ");
		id = entry.nextInt();
		
		Contato ctOld = new Contato();
		this.limparTela();
		List<Contato>cts = super.listarData();
		for(Contato conta:cts) {
			if(conta.getId() == id) { 				
				ctOld.setNome(conta.getNome());
				ctOld.setTelefone(conta.getTelefone());
				ctOld.setEmail(conta.getEmail());
				ctOld.setDtNascimento(conta.getDtNascimento());
				ctOld.setTwitter(conta.getTwitter());
				ctOld.setAddress(conta.getAddress());
			}
		}
		System.out.println("Id: "+id);
		System.out.println("Nome: "+ctOld.getNome());
		System.out.println("Telefone: "+ctOld.getTelefone());
		System.out.println("E-mail: "+ctOld.getEmail());
		System.out.println("Data de nascimento: "+ctOld.getDtNascimento());
		System.out.println("Twitter: "+ctOld.getTwitter());
		System.out.println("Endereço: "+ctOld.getAddress());		
		System.out.println("============================================");
		System.out.println("================= Alterar: =================");
		System.out.println("1.Nome                         =============");
		System.out.println("2.Telefone                     =============");
		System.out.println("3.E-mail                       =============");
		System.out.println("4.Data de Nascimento           =============");
		System.out.println("5.Twitter                      =============");
		System.out.println("6.Endereço                     =============");
		System.out.println("============================================");
		System.out.print("Escolha uma opção: ");
		int opt = entry.nextInt();
		
		entry.nextLine();
		switch(opt) {
			case 1: 
				System.out.print("Novo Nome: ");
				String nmCt = entry.nextLine();
				super.atualizarConta(1, nmCt, id);
				break;
			case 2:
				System.out.print("Novo Telefone: ");
				String telCt = entry.nextLine();
				super.atualizarConta(2, telCt, id);
				break;
			case 3:
				System.out.print("Novo E-mail: ");
				String emailCt = entry.nextLine();
				super.atualizarConta(3, emailCt, id);
				break;
			case 4:
				System.out.print("Nova Data de Nascimento: ");
				String dtnasc = entry.nextLine();
				super.atualizarConta(4, dtnasc, id);
				break;
			case 5:
				System.out.print("Novo Twitter: ");
				String twitter = entry.nextLine();
				super.atualizarConta(5, twitter, id);
				break;
			case 6:
				System.out.print("Novo Endereço: ");
				String address = entry.nextLine();
				super.atualizarConta(6, address, id);
				break;
		}
		List<Contato>ctUpdated = super.listarData(); // Cria novo array para verificar se foi alterado
		for(Contato conta:ctUpdated) { // Verifica se aconteceu alguma mudança em alguma coluna
			if(conta.getId() == id) { 
				if(conta.getNome() != ctOld.getNome() 
						|| conta.getTelefone() != ctOld.getTelefone() 
						|| conta.getEmail() != ctOld.getEmail() 
						|| conta.getDtNascimento() != ctOld.getDtNascimento() 
						|| conta.getTwitter() != ctOld.getTwitter() 
						|| conta.getAddress() != ctOld.getAddress()) {
					changed = true;
				}
			}
		}
		return changed;
	}
	public boolean deletar(){
		int idsOld = this.verificarBanco();
		List<Contato> choose = buscarPorNome();
		if(isEmpty == false) {		
			System.out.println("\n>> Escolha um id referente ao contato <<");
				try {	
					System.out.print("Digite o id: ");
					int nmChoose = entry.nextInt();
					int id = choose.get(nmChoose - 1).getId();
					super.deletarContato(id);
				}catch(InputMismatchException ime) {// Warning: Precisa ser tratado.
					System.out.println("Sintaxe desconhecida!\n Aviso: Inicie o programa novamente.");

				}catch(IndexOutOfBoundsException iofbe) {
					System.out.println("O Id não está correto ");	
				}	
		}
		int idsNew = this.verificarBanco();
		if(idsNew < idsOld) {
			return true;
		}else {
			return false;
		}
	}
	public void limparTela() {
		for(int c=0; c<100; c++) {
			System.out.println("");
		}
	}
}
