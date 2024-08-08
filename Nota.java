import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Nota {
	
	public static float recebeNotaInput(Scanner scanner, String complemento) {
		float novaNota;
		
		while(true) {
			System.out.println("\nDigite a nota" + complemento + " (separe as casas decimais com uma vírgula): ");
			
			try {
				novaNota = scanner.nextFloat();
				
				break;
			} catch (Exception e) {
				System.out.println("Input inválido.\n");
				scanner.nextLine();
			}
		}

		scanner.nextLine(); // para ler o \n deixado
		
		return novaNota;
	}
	
	public static int recebeIdInput(Scanner scanner, String complemento) {
		int idAluno;
		
		while(true) {
			System.out.println("\nDigite o id da nota" + complemento);
			
			try {
				idAluno = scanner.nextInt();
				
				break;
			} catch (Exception e) {
				System.out.println("Input inválido.\n");
				scanner.nextLine();
			}
		}
        
		scanner.nextLine(); // para ler o \n deixado
		return idAluno;
	}
	
	public static void saveNota(Connection conexao, Scanner scanner, float nota, int id, boolean create) {
		String sql = "";

		if(create) { sql = "INSERT INTO nota (nota, id_aluno) VALUES (?, ?)"; }
		else { sql = "UPDATE nota SET nota = ? WHERE id_nota = ?"; }

		try (PreparedStatement comando = conexao.prepareStatement(sql)) {
			comando.setFloat(1, nota);
			comando.setInt(2, id);
			
			int acao = comando.executeUpdate();
			if (create) {
				if(acao == 1) {
					System.out.println("Uma nova nota foi inserida com sucesso!\n");					
				} else {
					System.out.println("A nota não foi criada.\n");					
				}
			} else {
				if(acao == 1) {
					System.out.printf("A nota de id %d agora vale: %.1f%n\n", id, nota);
				} else {
					System.out.println("Nota não encontrada.\n");
				}
			}
		} catch (SQLException e) {
			if("23503".equals(e.getSQLState())){ // violates foreign key constraint
				System.out.println("Aluno não encontrado. Inserção abortada\n");
			}
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
    }    
 
    public static void readNota(Connection conexao, Scanner scanner, int idNota, int idAluno) {
        String sql = "SELECT nota, id_nota FROM nota";
        
        int id = -1;

        if(idNota != -1) { sql += " WHERE id_nota = ?"; id = idNota; }
        
        if(idAluno != -1) { sql += " WHERE id_aluno = ?"; id = idAluno; }

        try (PreparedStatement comando = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
        	if(idNota != -1 || idAluno != -1) {
        		comando.setInt(1, id);        		
        	}

            ResultSet resultado = comando.executeQuery();

            if (resultado.first()) {
                do{
                	float notaResultado = resultado.getFloat("nota");
            		int idResultado = resultado.getInt("id_nota");
            		System.out.printf("ID: %d, nota: %.1f%n\n", idResultado, notaResultado);
            	} while (resultado.next());   
            } else {
                System.out.println("Nota não encontrada.\n");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
   
    
    public static void deleteNota(Connection conexao, Scanner scanner, int id) {
	
    	String sql = "DELETE FROM nota WHERE id_nota = ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
        	comando.setInt(1, id);
            int deletou = comando.executeUpdate();

            if (deletou == 1) {
                System.out.printf("A nota de id %d foi deletada\n", id);
            } else {
                System.out.println("Nota não encontrada.\n");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
	}
}
