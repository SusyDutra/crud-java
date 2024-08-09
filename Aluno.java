import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Aluno {
	private static final int INDEFINIDO = -1;

	public static String recebeNomeInput(Scanner scanner, String complemento) {
		String novoAluno;

		while(true) {
			System.out.println("\nDigite o nome" + complemento);
			
			try {
				novoAluno = scanner.nextLine();
				
				if(novoAluno.length() > 15) {
					System.out.println("O nome deve ter no máximo 15 caracteres.\n");
				}
				else if(novoAluno.matches(".*\\d.*")) {
					System.out.println("O nome não pode ser numérico\n");
				}
				else if(novoAluno.isBlank()) {
					System.out.println("O nome não pode ser vazio\n");
				} else {
					break;
				}
			} catch (Exception e) {
				System.out.println("Input inválido.\n");
				scanner.nextLine();
			}			
		}
		
		return novoAluno;
	}
	
	public static int recebeIdInput(Scanner scanner, String complemento) {
		int idAluno;

		while(true) {
			System.out.println("\nDigite o id do aluno" + complemento);
			
			try {
				idAluno = scanner.nextInt();
				
				if(idAluno <= 0) {
					System.out.println("O id é um número maior que zero\n");
				} else {
					break;					
				}
			} catch (Exception e) {
				System.out.println("Input inválido.\n");
				scanner.nextLine();
			}
		}
        
		scanner.nextLine(); // para ler o \n deixado
		return idAluno;
	}
	
	public static void saveAluno(Connection conexao, Scanner scanner, String nome, int id) {
		String sql = "";

		if(id == INDEFINIDO) { sql = "INSERT INTO aluno (nome) VALUES (?)"; }
		else { sql = "UPDATE aluno SET nome = ? WHERE id_aluno = ?"; }
		
		
		try (PreparedStatement comando = conexao.prepareStatement(sql)) {
			comando.setString(1, nome);
			if(id != INDEFINIDO) { comando.setInt(2, id); }
			
			int acao = comando.executeUpdate();

			if(id == INDEFINIDO) {
				if (acao == 1) {
					System.out.println("Um novo aluno foi inserido com sucesso!\n");
				}
			} else {
				if (acao == 1) {
	                System.out.printf("O aluno de id %d agora tem nome: %s%n\n", id, nome);
	            } else {
	                System.out.println("Aluno não encontrado.\n");
	            }
			}
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}					
    }
	
	public static boolean readAluno(Connection conexao, Scanner scanner, int id) {
        String sql = "SELECT nome, id_aluno FROM aluno";
        
        if(id != INDEFINIDO) { sql += " WHERE id_aluno = ?"; }

        try (PreparedStatement comando = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
        	if(id != INDEFINIDO) { comando.setInt(1, id); }
        	
            ResultSet resultado = comando.executeQuery();
            
            if(resultado.first()) {
            	do{
            		String nomeResultado = resultado.getString("nome");
            		int idResultado = resultado.getInt("id_aluno");
            		System.out.printf("ID: %d, nome: %s%n\n", idResultado, nomeResultado);
            	} while (resultado.next());            	
            } else {
            	System.out.println("Aluno não encontrado.\n");
            	return false;
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            return false;
        }
        
        return true;
    }
	
	
	public static boolean deleteAluno(Connection conexao, Scanner scanner, int id) {		
    	String sql = "DELETE FROM aluno WHERE id_aluno = ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
        	comando.setInt(1, id);
            int deletou = comando.executeUpdate();

            if (deletou == 1) {
                System.out.printf("O aluno de id %d foi deletado\n", id);
                return true;
            } else {
                System.out.println("Aluno não encontrado.\n");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
  
        return false;
	}
}
