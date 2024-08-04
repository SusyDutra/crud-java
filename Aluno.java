import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Aluno {
	
	private int id;
	private String nome;

	//public static void main(String[] args) {
		// TODO Auto-generated method stub

	//}
	
	public Aluno(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	private void recebeNomeInput() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("\nDigite o nome que quer inserir: ");
        String novoAluno = scanner.next();
        this.nome = novoAluno;
        
        scanner.close();
	}
	
	private void recebeIdInput() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("\nDigite o id do aluno que quer ler: ");
		int idAluno = scanner.nextInt();
        this.id = idAluno;
        
        scanner.close();
	}
	
	public void createAluno(Connection conn) {
		recebeNomeInput();
		
    	String sql = "INSERT INTO aluno (nome) VALUES (?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.nome);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Um novo aluno foi inserido com sucesso!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
	
	public void readAluno(Connection conn) {
		recebeIdInput();
		
        String sql = "SELECT nome FROM aluno WHERE id_aluno = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                System.out.printf("ID: %d, nome: %s%n\n", id, nome);
            } else {
                System.out.println("Aluno não encontrado.\n");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
}
