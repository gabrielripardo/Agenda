

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbContato {
private Connection conexao;
private boolean isEmpty;

	DbContato(){
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
			String sql = "create table contatos(id integer primary key autoincrement not null, nome varchar(20), telefone varchar(15), email varchar(30))";
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
			
			String sql = "insert into contatos(nome, telefone, email) values(?,?,?)";
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			
			stmt.setString(1, conta.getNome());
			stmt.setString(2, conta.getTelefone());
			stmt.setString(3, conta.getEmail());
			
			stmt.execute();
			stmt.close();
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
	public void deletarContato(int ctId) {
		try {
			this.abrirConexao();
			String sqlDel = "delete from contatos where id=?";
			PreparedStatement stmt = this.conexao.prepareStatement(sqlDel);
			stmt.setInt(1, ctId);	
			stmt.execute();
			stmt.close();
		}catch(SQLException e){
			if(isEmpty) {
				this.criarBanco();
				this.deletarContato(ctId);
			}
		}finally {
			this.fecharConexao();
		}
	}
}
