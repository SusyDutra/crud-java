import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Nota {

	private int id;
	private float nota;
	public Scanner scanner;

	//public static void main(String[] args) {
		// TODO Auto-generated method stub

	//}
	
	public Nota(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public void recebeNotaInput(String complemento) {
		System.out.println("\nDigite a nota " + complemento + " (separe as casas decimais com uma vírgula): ");
		float novaNota = scanner.nextFloat();
        this.nota = novaNota;
	}
	
	private void recebeIdInput(String complemento) {
		System.out.println("\nDigite o id da nota " + complemento);
		int idAluno = scanner.nextInt();
        this.id = idAluno;
        
        scanner.nextLine(); // para ler o \n deixado
	}
	
	public void createNota(Connection conn) {
		recebeNotaInput("que que adicionar");

    	String sql = "INSERT INTO nota (nota) VALUES (?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setFloat(1, this.nota);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Uma nova nota foi inserida com sucesso!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    
 
    public void readNota(Connection conn) {
    	recebeIdInput("que quer ver");

        String sql = "SELECT nota FROM nota WHERE id_nota = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
            	this.nota = rs.getFloat("nota");
                System.out.printf("ID: %d, nota: %.2f%n\n", this.id, this.nota);
            } else {
                System.out.println("Nota não encontrada.\n");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
    
    public void updateNota(Connection conn) {
		recebeIdInput("que quer atualizar: ");
		recebeNotaInput("atualizada");
		
        String sql = "UPDATE nota SET nota = ? WHERE id_nota = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setFloat(1, this.nota);
            pstmt.setInt(2, this.id);
            int atualizou = pstmt.executeUpdate();

            if (atualizou == 1) {
                System.out.printf("A nota de id %d agora vale: %f%n\n", this.id, this.nota);
            } else {
                System.out.println("Nota não encontrada.\n");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
    
    public void deleteNota(Connection conn) {
		recebeIdInput("que quer deletar: ");
		
    	String sql = "DELETE FROM nota WHERE id_nota = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.id);
            int deletou = pstmt.executeUpdate();

            if (deletou == 1) {
                System.out.printf("O aluno de id %d foi deletado\n", this.id);
            } else {
                System.out.println("Aluno não encontrado.\n");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
	}
}
