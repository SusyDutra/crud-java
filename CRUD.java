import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CRUD {
	
    public static void main(String[] args) {

    	Connection conexao = null;
    	try {
			conexao = conectar();
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
			            aluno.createAluno(conexao);
			            break;
	        		case 2:
	        			aluno.readAluno(conexao);
	        			break;
	        		case 3:
	        			aluno.updateAluno(conexao);
	        			break;
	        		case 4:
	        			aluno.deleteAluno(conexao);
	        			break;
	        	}
        	break;
	            
	        case 2:
	        	Nota nota = new Nota(scanner);
	        	
	        	switch(escolhaOperacao) {
	        		case 1:
	        			nota.createNota(conexao);
	        			break;
	        		case 2:
	        			nota.readNota(conexao);
	        			break;
	        		case 3:
	        			nota.updateNota(conexao);
	        			break;
	        		case 4:
	        			nota.deleteNota(conexao);
	        	}	
        	break;
        }     

        scanner.close();
    }

    private static Connection conectar () throws SQLException {
    	Connection conexao = null;
 
        try {
        	conexao = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/exati", "guia", "asdumsiyn");
        } catch (Exception e) {
            System.out.println("Falha na conexão com o banco");
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
        	System.out.println("Falha na conexão com o driver");
        }
    
        if (conexao == null) {
            throw new SQLException();
        }

        return conexao;
    }
    
    

    


}