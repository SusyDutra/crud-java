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
    	
        int escolhaOperacao1;
		while(true) {
        	displayMenu1();
 
        	try {
        		escolhaOperacao1 = scanner.nextInt();
        		scanner.nextLine();
    			
        		if(List.of(0, 1, 2, 3, 4, 5, 6).contains(escolhaOperacao1)) {
        			if(escolhaOperacao1 == 0) {
        				break;
        			}
        		} else {
    				System.out.println("Opção inválida.\n");
    				continue;
    			}
    				
				if(escolhaOperacao1 == 6){ // selecionou um aluno
					int idAluno = Aluno.recebeIdInput(scanner, " que quer selecionar: ");
  
					int escolhaOperacao2;
					while(true) {
						displayMenu2();
						try {
			        		escolhaOperacao2 = scanner.nextInt();
			        		scanner.nextLine();
			   
			        		if(List.of(0, 1, 2, 3, 4, 5).contains(escolhaOperacao2)) {
			        			if(escolhaOperacao2 == 0) {
			        				break;
			        			} else {
			        				funcoesMenu2(conexao, scanner, escolhaOperacao2, idAluno);
			        			}
			    			} else {
			    				System.out.println("Opção inválida.\n");
			    				continue;
			    			}
			    		} catch (Exception e) {
			    			System.out.println("Opção inválida.\n");
			    			scanner.nextLine();
			    		}
					}
				} else {
					funcoesMenu1(conexao, scanner, escolhaOperacao1);
				}
    		} catch (Exception e) {
    			System.out.println("Opção inválida.\n");
    			scanner.nextLine();
    		}
        }
		
        scanner.close();
    }
    
    private static void displayMenu1() {
    	System.out.println("\nMenu de opções:\n"
				 + "1 - Listar alunos\n"
				 + "2 - Listar notas\n"
				 + "3 - Cadastrar aluno\n"
				 + "4 - Deletar aluno\n"
				 + "5 - Editar aluno\n"
				 + "6 - Selecionar aluno\n\n"
				 + "0 - Sair do programa\n");
    }
    
    private static void displayMenu2() {
    	System.out.println("\nMenu de opções do aluno:\n"
				 + "1 - Editar aluno\n"
				 + "2 - Listar notas\n"
				 + "3 - Adicionar nota aluno\n"
				 + "4 - Editar nota aluno\n"
				 + "5 - Deletar nota aluno\n\n"
				 + "0 - Voltar\\n");
    }
    
    private static void funcoesMenu1(Connection conexao, Scanner scanner, int operacao) {
    	switch (operacao) {
			case 1:
				Aluno.readAluno(conexao, scanner, -1);
				break;
				
			case 2:
				Nota.readNota(conexao, scanner, -1, -1);
				break;				

			case 3:
				String nomeCA = Aluno.recebeNomeInput(scanner, " que quer adicionar: ");
				Aluno.saveAluno(conexao, scanner, nomeCA, -1);
				break;
				
			case 4:
				int idDA = Aluno.recebeIdInput(scanner, " que quer deletar: ");
				Aluno.deleteAluno(conexao, scanner, idDA);
				break;

			case 5:
				int idUA = Aluno.recebeIdInput(scanner, " que quer atualizar o nome: ");
				String nomeUA = Aluno.recebeNomeInput(scanner, " atualizado: ");
				Aluno.saveAluno(conexao, scanner, nomeUA, idUA);
				break;
    	}
    }
    
    private static void funcoesMenu2(Connection conexao, Scanner scanner, int operacao, int idAluno) {
    	switch (operacao) {
			case 1:
				String nomeUA = Aluno.recebeNomeInput(scanner, " atualizado: ");
				Aluno.saveAluno(conexao, scanner, nomeUA, idAluno);
				break;	
		
			case 2:
				Nota.readNota(conexao, scanner, -1, idAluno);
				break;				

			case 3:
				float notaCN = Nota.recebeNotaInput(scanner, " que que adicionar:");
				Nota.saveNota(conexao, scanner, notaCN, idAluno, true);
				break;
				
			case 4:
				int idUN = Nota.recebeIdInput(scanner, " que será atualizada: ");
				float notaUN = Nota.recebeNotaInput(scanner, " atualizada: ");
				Nota.saveNota(conexao, scanner, notaUN, idUN, false); // tem q envolver o id do aluno
				break;

			case 5:
				int idDN = Nota.recebeIdInput(scanner, " que quer deletar: ");
				Nota.deleteNota(conexao, scanner, idDN);   
				break;
    	}
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