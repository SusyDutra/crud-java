import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
 
        System.out.println("Escolha qual tabela deseja operar:\n1 - Tabela Aluno\n2 - Tabela Nota\n");

        createAluno(conn, "Aluno");
     
        createNota(conn, (float) 10.0);
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

}