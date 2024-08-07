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
        
        int escolhaOperacao;
		while(true) {
        	System.out.println("Menu de opções:\n"
        					 + "1 - Criar um aluno\n"
        					 + "2 - Criar uma nota\n"
        					 + "3 - Buscar um aluno\n"
        					 + "4 - Buscar todos os alunos\n"
        					 + "5 - Buscar uma nota\n"
        					 + "6 - Buscar todas as notas\n"
        					 + "7 - Atualizar um aluno\n"
        					 + "8 - Atualizar uma nota\n"
        					 + "9 - Deletar um aluno\n"
        					 + "10 - Deletar uma nota");
        	try {
        		escolhaOperacao = scanner.nextInt();
    			
        		if(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).contains(escolhaOperacao)) {
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

        switch (escolhaOperacao) {
    		case 1, 7:
	            Aluno.saveAluno(conexao);
	            break;
    		case 2, 8:
    			Nota.saveNota(conexao);
    			break;
    		case 3, 4:
    			Aluno.readAluno(conexao);
    			break;
    		case 5, 6:
    			Nota.readNota(conexao);
    			break;
    		case 9:
    			Aluno.deleteAluno(conexao);
    			break;
    		case 10:
    			Nota.deleteNota(conexao);    			
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