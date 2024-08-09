## CRUD em java

Este projeto consiste em um CRUD feito em java, que acessa uma base PostgreSQL por meio de um driver JDBC

A base possui duas tabelas:

Tabela aluno
| nome da coluna | tipo de dado |
| -------------  | ------------ |
| id_aluno       | SERIAL       |
| nome           | VARCHAR(15)  |

Tabela nota
| nome da coluna               | tipo de dado  |
| ---------------------------  | ------------- |
| id_nota                      | SERIAL        |
| nota                         | DECIMAL(4, 2) |
| id_aluno FK(aluno(id_aluno)) | INT           |
