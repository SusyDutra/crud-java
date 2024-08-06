import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CRUD {
	
    public static void main(String[] args) {

    	Connection conn = null;
    	try {
			conn = conexao();
		} catch (Exception e) {
			System.out.println("A conexão com o banco de dados foi mal sucedida.");
			System.exit(0);
		}
    	
    	Scanner scanner = new Scanner(System.in);

    	int escolhaTabela;
    	while(true) {
    		System.out.println("Escolha qual tabela deseja operar:\n1 - Tabela Aluno\n2 - Tabela Nota\n");
    		
    		try {
    			escolhaTabela = scanner.nextInt();
    			
    			if(escolhaTabela == 1 || escolhaTabela == 2) {
    				break;
    			} else {
    				System.out.println("Opção inválida.\n");
    			}
    		} catch (Exception e) {
    			System.out.println("Opção inválida.\n");
    			scanner.nextLine();
    		}
    	}
    	scanner.nextLine();
        
        int escolhaOperacao;
        
        while(true) {
        	System.out.println("Agora, a operação:\n1 - CREATE\n2 - READ\n3 - UPDATE\n4 - DELETE\n");
        	
        	try {
        		escolhaOperacao = scanner.nextInt();
    			
        		if(List.of(1, 2, 3, 4).contains(escolhaOperacao)) {
    				break;
    			} else {
    				System.out.println("Opção inválida.\n");
    			}
    		} catch (Exception e) {
    			System.out.println("Opção inválida.\n");
    			scanner.nextLine();
    		}
        }
        scanner.nextLine();

        switch (escolhaTabela) {
	        case 1:
	        	Aluno aluno = new Aluno(scanner);

	        	switch(escolhaOperacao) {
	        		case 1:
			            aluno.createAluno(conn);
			            break;
	        		case 2:
	        			aluno.readAluno(conn);
	        			break;
	        		case 3:
	        			aluno.updateAluno(conn);
	        			break;
	        		case 4:
	        			aluno.deleteAluno(conn);
	        			break;
	        		default:
	    	            System.out.println("\nOpção inválida\n");
	    	            break;
	        	}
        	break;
	            
	        case 2:
	        	Nota nota = new Nota(scanner);
	        	
	        	switch(escolhaOperacao) {
	        		case 1:
	        			nota.createNota(conn);
	        			break;
	        		case 2:
	        			nota.readNota(conn);
	        			break;
	        		case 3:
	        			nota.updateNota(conn);
	        			break;
	        		case 4:
	        			nota.deleteNota(conn);
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

    private static Connection conexao () throws SQLException {
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
    
    

    


}