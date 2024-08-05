import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Aluno {
	
	private int id;
	private String nome;
	public Scanner scanner;
	
	public Aluno(Scanner scanner) {
		this.scanner = scanner;
	}
	
	private void recebeNomeInput(String complemento) {
		System.out.println("\nDigite o nome " + complemento);
        String novoAluno = scanner.nextLine();
        this.nome = novoAluno;
	}
	
	private void recebeIdInput(String complemento) {
		System.out.println("\nDigite o id do aluno " + complemento);
		int idAluno = scanner.nextInt();
        this.id = idAluno;
        
        scanner.nextLine(); // para ler o \n deixado
	}
	
	public void createAluno(Connection conn) {
		recebeNomeInput("que quer inserir: ");
		
    	String sql = "INSERT INTO aluno (nome) VALUES (?)";

        try (PreparedStatement comando = conn.prepareStatement(sql)) {
        	comando.setString(1, this.nome);

            int inserido = comando.executeUpdate();
            if (inserido == 1) {
                System.out.println("Um novo aluno foi inserido com sucesso!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
	
	public void readAluno(Connection conn) {
		recebeIdInput("que quer ler: ");
		
        String sql = "SELECT nome FROM aluno WHERE id_aluno = ?";

        try (PreparedStatement comando = conn.prepareStatement(sql)) {
        	comando.setInt(1, this.id);
            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
            	this.nome = resultado.getString("nome");
                System.out.printf("ID: %d, nome: %s%n\n", this.id, this.nome);
            } else {
                System.out.println("Aluno n�o encontrado.\n");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
	
	public void updateAluno(Connection conn) {
		recebeIdInput("que quer atualizar o nome: ");
		recebeNomeInput("atualizado: ");
		
        String sql = "UPDATE aluno SET nome = ? WHERE id_aluno = ?";

        try (PreparedStatement comando = conn.prepareStatement(sql)) {
        	comando.setString(1, this.nome);
        	comando.setInt(2, this.id);
            int atualizou = comando.executeUpdate();

            if (atualizou == 1) {
                System.out.printf("O aluno de id %d agora tem nome: %s%n\n", this.id, this.nome);
            } else {
                System.out.println("Aluno n�o encontrado.\n");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
	
	public void deleteAluno(Connection conn) {
		recebeIdInput("que quer deletar: ");
		
    	String sql = "DELETE FROM aluno WHERE id_aluno = ?";

        try (PreparedStatement comando = conn.prepareStatement(sql)) {
        	comando.setInt(1, this.id);
            int deletou = comando.executeUpdate();

            if (deletou == 1) {
                System.out.printf("O aluno de id %d foi deletado\n", this.id);
            } else {
                System.out.println("Aluno n�o encontrado.\n");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
	}
}
