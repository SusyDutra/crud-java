import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Nota {

	private int id;
	private float nota;
	private Scanner scanner;
	
	public Nota(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public void recebeNotaInput(String complemento) {
		while(true) {
			System.out.println("\nDigite a nota " + complemento + " (separe as casas decimais com uma vírgula): ");
			
			float novaNota;
			try {
				novaNota = this.scanner.nextFloat();
				this.nota = novaNota;
				
				break;
			} catch (Exception e) {
				System.out.println("Input inválido.\n");
				this.scanner.nextLine();
			}
		}

		this.scanner.nextLine(); // para ler o \n deixado
	}
	
	private void recebeIdInput(String complemento) {
		while(true) {
			System.out.println("\nDigite o id da nota " + complemento);
			
			int idAluno;
			try {
				idAluno = this.scanner.nextInt();
				this.id = idAluno;
				
				break;
			} catch (Exception e) {
				System.out.println("Input inválido.\n");
				this.scanner.nextLine();
			}
		}
        
		this.scanner.nextLine(); // para ler o \n deixado
	}
	
	public void createNota(Connection conexao) {
		recebeNotaInput("que que adicionar");

    	String sql = "INSERT INTO nota (nota) VALUES (?)";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
        	comando.setFloat(1, this.nota);

            int inserido = comando.executeUpdate();
            if (inserido == 1) {
                System.out.println("Uma nova nota foi inserida com sucesso!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }    
 
    public void readNota(Connection conexao) {
    	recebeIdInput("que quer ver");

        String sql = "SELECT nota FROM nota WHERE id_nota = ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
        	comando.setInt(1, this.id);
            ResultSet inserido = comando.executeQuery();

            if (inserido.next()) {
            	this.nota = inserido.getFloat("nota");
                System.out.printf("ID: %d, nota: %.2f%n\n", this.id, this.nota);
            } else {
                System.out.println("Nota não encontrada.\n");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
    
    public void updateNota(Connection conexao) {
		recebeIdInput("que quer atualizar: ");
		recebeNotaInput("atualizada");
		
        String sql = "UPDATE nota SET nota = ? WHERE id_nota = ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
        	comando.setFloat(1, this.nota);
        	comando.setInt(2, this.id);
            int atualizou = comando.executeUpdate();

            if (atualizou == 1) {
                System.out.printf("A nota de id %d agora vale: %f%n\n", this.id, this.nota);
            } else {
                System.out.println("Nota não encontrada.\n");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
    
    public void deleteNota(Connection conexao) {
		recebeIdInput("que quer deletar: ");
		
    	String sql = "DELETE FROM nota WHERE id_nota = ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
        	comando.setInt(1, this.id);
            int deletou = comando.executeUpdate();

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
