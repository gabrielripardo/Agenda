/**
* @descrition Classe Dao que realiza toda a manipulação pura com o banco de dados.
* @date 09-23-2017
* @author Gabriel Ripardo
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {
private Connection conexao;
private boolean isEmpty;

	ContatoDAO(){
		conexao = null;
		isEmpty = true;
	}
	
	public void abrirConexao() {
		if(this.conexao == null){
			this.conexao = new ConnectionAgenda().getConnection();
		}
	}
	void criarBanco() {
		try {                                //id integer primary key autoincrement not null
			this.abrirConexao();
			String sql = "create table contatos(id integer primary key autoincrement not null, nome varchar(20), telefone varchar(15), email varchar(30), dtNascimento varchar(9), twitter varchar(15), address varchar(30))";
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			isEmpty = false;
		}catch(SQLException e) {
			System.out.print("Não foi possível criar campos! "+e);
		}finally {
			this.fecharConexao();
		}
	}
	public void adicionarContato(Contato conta) {
		try{
			this.abrirConexao();
			
			String sql = "insert into contatos(nome, telefone, email, dtNascimento, twitter, address) values(?,?,?,?,?,?)";
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			
			stmt.setString(1, conta.getNome());
			stmt.setString(2, conta.getTelefone());
			stmt.setString(3, conta.getEmail());
			stmt.setString(4, conta.getDtNascimento());
			stmt.setString(5, conta.getTwitter());
			stmt.setString(6, conta.getAddress());
			
			stmt.execute();
			stmt.close();
			
			this.realocarContatos();
		}
		catch(SQLException e){
			if(isEmpty) {
				this.criarBanco();
				this.adicionarContato(conta);
			}
		}finally {
			this.fecharConexao();
		}
	}
	public boolean atualizarConta(int typeEdit, String valueUpdated, int contaId) {
		try {
			this.abrirConexao();
			String sql = null;
			
			switch(typeEdit) {
			case 1: sql = "update contatos set nome=? where id=?"; 
			break;
			case 2: sql = "update contatos set telefone=? where id=?"; 
			break;
			case 3: sql = "update contatos set email=? where id=?"; 
			break;
			case 4: sql = "update contatos set dtnascimento=? where id=?"; 
			break;
			case 5: sql = "update contatos set twitter=? where id=?"; 
			break;
			case 6: sql = "update contatos set address=? where id=?"; 
			break;
			}
			
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, valueUpdated);
			stmt.setInt(2, contaId);
			
			stmt.execute();
			stmt.close();
		}catch(SQLException e) {
			System.out.print("Erro ao alterar banco: "+e);
		}finally{
			this.fecharConexao();
		}
		return true;
	}
	public List<Contato> listarAniversariantesdomes(int mesNiver){
		List<Contato> contatos = new ArrayList<Contato>(); 
		try {
			this.abrirConexao();
			String sql = "select * from contatos";
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Contato c = new Contato();
				
				c.setNome(rs.getString("NOME")); // Inseri o valor de Nome do DB em um objeto Contato
				c.setTelefone(rs.getString("TELEFONE"));
				c.setEmail(rs.getString("EMAIL"));
				c.setDtNascimento(rs.getString("DTNASCIMENTO"));
				c.setTwitter(rs.getString("TWITTER"));
				c.setAddress(rs.getString("ADDRESS"));
				c.setId(rs.getInt("ID"));
				
				// Filtro para armazenamento no array
				int mes = Integer.parseInt(String.valueOf(rs.getString("dtNascimento").charAt(3))+String.valueOf(rs.getString("dtNascimento").charAt(4)));
				if(mes == mesNiver)
					contatos.add(c);
			}
			rs.close();
			stmt.close();
			
		}catch(SQLException e) {
			System.out.println("Erro ao listar aniversariantes: "+e);
		}finally {
			this.fecharConexao();
		}
		return contatos; 
	}
	
	public List<Contato> listarData(){
		List<Contato> contas = new ArrayList<Contato>();
		
		try {
			abrirConexao();
			String sqlSelect = "select * from contatos";
			PreparedStatement stmt = this.conexao.prepareStatement(sqlSelect);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Contato c = new Contato(); // Criar novo objeto Contato para guardar os dados que ele irá capturar do select
				
				c.setNome(rs.getString("NOME")); // Inseri o valor de Nome do DB em um objeto Contato
				c.setTelefone(rs.getString("TELEFONE"));
				c.setEmail(rs.getString("EMAIL"));
				c.setDtNascimento(rs.getString("DTNASCIMENTO"));
				c.setTwitter(rs.getString("TWITTER"));
				c.setAddress(rs.getString("ADDRESS"));
				c.setId(rs.getInt("ID"));
				
				contas.add(c);
			}
			rs.close();
			stmt.close();
		}catch(SQLException e) {
			if(isEmpty) {
				this.criarBanco();
				this.listarData();
			}
		}
		finally {
			this.fecharConexao();
		}
		return contas;
	}
	public List<Contato> buscarNome(String inicial) {
		List<Contato> contas = this.listarData();
		List<Contato> cs = new ArrayList<Contato>();
		
		for(Contato conta: contas) {
			String nmBd = String.valueOf(conta.getNome());
			String paramChar = null;
			String nmBdChar = null;
			int nComp = inicial.length();
			
			// Max nComparações
			if(nmBd.length()<inicial.length()) {
				nComp = nmBd.length();
			}
			int cont = 0;
			if(conta.getNome() != null && nmBd.length() >= inicial.length()) {
				for(int i=0; i < nComp; i++) {
					paramChar = String.valueOf(inicial.charAt(i)); 
					nmBdChar = String.valueOf(nmBd.charAt(i));
	
					if(paramChar.toLowerCase().equals(nmBdChar.toLowerCase())) {
						cont++;
					}
				}
				if(cont == nComp) {
					Contato c = new Contato();
		
					c.setNome(conta.getNome());
					c.setTelefone(conta.getTelefone());
					c.setEmail(conta.getEmail());
					c.setId(conta.getId());
					cs.add(c);
				}
			}
		}
		return cs;
	}
	public void fecharConexao() {
		try{
			if(this.conexao != null){
				this.conexao.close();
				this.conexao = null;
			}
		}catch(SQLException e){
			if(isEmpty) {
				this.criarBanco();
				this.fecharConexao();
			}
		}
	}
	public void realocarContatos() {
		int setId = 0;
		List<Contato> contatos = this.listarData();
		Contato conta = new Contato();
		for(Contato c:contatos) {
			conta.setId(c.getId());
			setId++;
		}
		try{
			this.abrirConexao();
				String sql = "update contatos set id=? where id=?";
				PreparedStatement stmt = this.conexao.prepareStatement(sql);
				stmt.setInt(1, setId);
				stmt.setInt(2, conta.getId());
				
				stmt.execute();
				stmt.close();
		}catch(SQLException e){
			System.out.println("Erro ao realocar id "+e);
		}finally {
			this.fecharConexao();
		}
	}
	public void realocarContatos(int idDeleted) { // Método auxiliar para evitar que os campos fiquem sem sequência  
		List<Contato> contatos = this.listarData(); 
		int totalContacts = 0;
		for (int i = 0; i < contatos.size(); i++) {
			totalContacts++;
		}
		int setId = idDeleted;
		int whereDel = setId + 1;
		try{
			this.abrirConexao();
			for(int i=0; i<=totalContacts-idDeleted; i++) {

				String sql = "update contatos set id=? where id=?";
				PreparedStatement stmt = this.conexao.prepareStatement(sql);
				stmt.setInt(1, setId);
				stmt.setInt(2, whereDel);
				
				stmt.execute();
				stmt.close();
				
				setId++;
				whereDel++;
			}
		}
		catch(SQLException e){
			System.out.println("Erro ao realocar id "+e);
		}finally {
			this.fecharConexao();
		}
	}
	public void deletarContato(int ctId) {
		try {
			this.abrirConexao();
			String sqlDel = "delete from contatos where id=?";
			PreparedStatement stmt = this.conexao.prepareStatement(sqlDel);
			stmt.setInt(1, ctId);	
			stmt.execute();
			stmt.close();
			this.realocarContatos(ctId);
			
		}catch(SQLException e){
			System.out.println("Error ao deletar no Banco de dados: "+e);
		}finally {
			this.fecharConexao();
		}
	}
}
