
import java.sql.Connection;

public class Teste {
	public static void main(String[] args) {
		Connection conexao = new ConnectionAgenda().getConnection();
		ContatoDAO cd = new ContatoDAO(conexao);
		PrintContato pc = new PrintContato(conexao);
		
		Contato ct = new Contato();
		ct.setNome("Gabriel");
		ct.setEmail("gts.senna@gmail.com");
		ct.setTelefone("23423423");
		
		pc.verificarBanco();
		pc.adicionarContato(ct);
		//pc.deletarContato(3);
		pc.buscarTodos();
		
		//cd.criarBanco();
		//cd.adicionarContato(ct);
	//	cd.listarData();
		//cd.deletarContato(1);
		//cd.buscarNome("g");
	}
}

