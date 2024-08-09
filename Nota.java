import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Nota {
	private static final int INDEFINIDO = -1;
	
	public static float recebeNotaInput(Scanner scanner, String complemento) {
		float novaNota;
		
		while(true) {
			System.out.println("\nDigite a nota" + complemento + " (separe as casas decimais com uma vírgula): ");
			
			try {
				novaNota = scanner.nextFloat();
				
				if(novaNota < 0) {
					System.out.println("A nota não pode ser negativa\n");
				}
				else if(novaNota > 10) {
					System.out.println("A nota não pode ser maior que 10\n");
				} else {
					break;					
				}
			} catch (Exception e) {
				System.out.println("Input inválido.\n");
				scanner.nextLine();
			}
		}

		scanner.nextLine(); // para ler o \n deixado
		
		BigDecimal bd = new BigDecimal(Float.toString(novaNota));
        bd = bd.setScale(2, RoundingMode.DOWN);
		
		return bd.floatValue();
	}
	
	public static int recebeIdInput(Scanner scanner, String complemento) {
		int idAluno;
		
		while(true) {
			System.out.println("\nDigite o id da nota" + complemento);
			
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
	
	public static void saveNota(Connection conexao, Scanner scanner, float nota, int id, boolean create) {
		String sql = "";

		if(create) { sql = "INSERT INTO nota (nota, id_aluno) VALUES (?, ?)"; }
		else { sql = "UPDATE nota SET nota = ? WHERE id_nota = ?"; }

		try (PreparedStatement comando = conexao.prepareStatement(sql)) {
			System.out.println(Float.toString(nota));
			BigDecimal notaDecimal = new BigDecimal(Float.toString(nota)).setScale(2, RoundingMode.DOWN);
			comando.setBigDecimal(1, notaDecimal);

			comando.setInt(2, id);
			
			int acao = comando.executeUpdate();
			if (create) {
				if(acao == 1) {
					System.out.println("Uma nova nota foi inserida com sucesso!\n");	
					System.out.print(nota);
				} else {
					System.out.println("A nota não foi criada.\n");					
				}
			} else {
				if(acao == 1) {
					System.out.printf("A nota de id %d agora vale: %.2f%n\n", id, nota);
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
        String sql = "SELECT nota, id_nota, id_aluno FROM nota";
        
        int id = INDEFINIDO;

        if(idNota != INDEFINIDO) { sql += " WHERE id_nota = ?"; id = idNota; }
        
        if(idAluno != INDEFINIDO) { sql += " WHERE id_aluno = ?"; id = idAluno; }

        try (PreparedStatement comando = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
        	if(idNota != INDEFINIDO || idAluno != INDEFINIDO) {
        		comando.setInt(1, id);        		
        	}

            ResultSet resultado = comando.executeQuery();

            if (resultado.first()) {
                do{
                	float notaResultado = resultado.getFloat("nota");
            		int idResultado = resultado.getInt("id_nota");
            		int idAlunoResultado = resultado.getInt("id_aluno");
            		System.out.printf("ID: %d, nota: %.2f, id_aluno: %d\n", idResultado, notaResultado, idAlunoResultado);
            	} while (resultado.next());   
            } else {
                System.out.println("Nota não encontrada.\n");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
   
    
    public static void deleteNota(Connection conexao, Scanner scanner, int idNota,int idAluno) {
	
    	String sql = "DELETE FROM nota WHERE id_nota = ? AND id_aluno = ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
        	comando.setInt(1, idNota);
        	comando.setInt(2, idAluno);
            int deletou = comando.executeUpdate();

            if (deletou == 1) {
                System.out.printf("A nota de id %d do aluno de id %d foi deletada\n", idNota, idAluno);
            } else {
                System.out.println("Nota não encontrada.\n");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
	}
}
