import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Nota {

	private int id;
	private float nota;

	//public static void main(String[] args) {
		// TODO Auto-generated method stub

	//}
	
	public Nota(int id, float nota) {
		this.id = id;
		this.nota = nota;
	}
	
	public void recebeNotaInput() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("\nDigite a nota que quer inserir (separe as casas decimais com uma vírgula): ");
		float novaNota = scanner.nextFloat();
        this.nota = novaNota;
        
        scanner.close();
	}
	
	private void recebeIdInput() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("\nDigite o id da nota que quer ler: ");
		int idNota = scanner.nextInt();
        this.id = idNota;
        
        scanner.close();
	}
	
	public void createNota(Connection conn) {
		recebeNotaInput();

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
    	recebeIdInput();

        String sql = "SELECT nota FROM nota WHERE id_nota = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                float nota = rs.getFloat("nota");
                System.out.printf("ID: %d, nota: %.2f%n\n", id, nota);
            } else {
                System.out.println("Nota não encontrada.\n");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
}
