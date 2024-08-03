import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CRUD {
	
    public static void main(String[] args) {

    	Connection conn = null;
    	try {
			conn = setConnection();
		} catch (Exception e) {
			System.out.println("A conexão com o banco de dados foi mal sucedida.");
			System.exit(0);
		}
    	
    	Scanner scanner = new Scanner(System.in);
 
        System.out.println("Escolha qual tabela deseja operar:\n1 - Tabela Aluno\n2 - Tabela Nota\n");
        int escolhaTabela = scanner.nextInt();
        
        System.out.println("A operação:\n1 - CREATE\n2 - READ\n");
        int escolhaOperacao = scanner.nextInt();
        
        switch (escolhaTabela) {
	        case 1:
	        	switch(escolhaOperacao) {
	        		case 1:
			        	System.out.println("\nDigite o nome que quer inserir: ");
			            String novoAluno = scanner.next();
			            createAluno(conn, novoAluno);
			            break;
	        		case 2:
	        			System.out.println("\nDigite o id do aluno que quer ler: ");
	        			int idAluno = scanner.nextInt();
	        			readAluno(conn, idAluno);
	        			break;
	        		default:
	    	            System.out.println("\nOpção inválida\n");
	    	            break;
	        	}
        	break;
	            
	        case 2:
	        	switch(escolhaOperacao) {
	        		case 1:
	        			System.out.println("\nDigite a nota que quer inserir (separe as casas decimais com uma vírgula): ");
	        			float novaNota = scanner.nextFloat();
	        			createNota(conn, novaNota);
	        			break;
	        		case 2:
	        			System.out.println("\nDigite o id da nota que quer ler: ");
	        			int idNota = scanner.nextInt();
	        			readNota(conn, idNota);
	        			break;
	        		default:
	    	            System.out.println("\nOpção inválida\n");
	    	            break;
	        	}	
        	break;
 
	        default:
	            System.out.println("\nOpção inválida\n");
	            break;
        }     

        scanner.close();
    }

    private static Connection setConnection () throws SQLException {
    	Connection conn = null;
 
        try {
        	conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/exati", "guia", "asdumsiyn");
        } catch (Exception e) {
            System.out.println("Falha na conexão com o banco");
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
        	System.out.println("Falha na conexão com o driver");
        }
    
        if (conn == null) {
            throw new SQLException();
        }

        return conn;
    }
    
    public static void createAluno(Connection conn, String nome) {
    	String sql = "INSERT INTO aluno (nome) VALUES (?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Um novo aluno foi inserido com sucesso!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public static void createNota(Connection conn, float nota) {
    	String sql = "INSERT INTO nota (nota) VALUES (?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setFloat(1, nota);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Uma nova nota foi inserida com sucesso!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    private static void readAluno(Connection conn, int id) {
        String sql = "SELECT nome FROM aluno WHERE id_aluno = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
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
 
    private static void readNota(Connection conn, int id) {
        String sql = "SELECT nota FROM nota WHERE id_nota = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
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