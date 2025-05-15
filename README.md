# cp2-java

## Integrantes do Grupo

- Carlos Eduardo (RM 555223)
- Antonio Lino (RM 554518)
- Caio Henrique (RM 554600)

## Exemplos de Comandos INSERT 
INSERT INTO moto (placa, modelo, situacao) VALUES ('ABC1A23', 'CG 160', 'Dentro');

INSERT INTO moto (placa, modelo, situacao) VALUES ('DEF2B34', 'Fazer 250', 'Fora');

INSERT INTO moto (placa, modelo, situacao) VALUES ('GHI3C45', 'Titan 150', 'Dentro');

INSERT INTO moto (placa, modelo, situacao) VALUES ('JKL4D56', 'NXR 160', 'Dentro');

INSERT INTO moto (placa, modelo, situacao) VALUES ('MNO5E67', 'PCX 150', 'Fora');

INSERT INTO funcionario (moto_id_moto, nome, cpf, telefone, tipo_funcionario) VALUES (1, 'Carlos Silva', '123.456.789-00', '11999990000', 'Motoqueiro');

INSERT INTO funcionario (moto_id_moto, nome, cpf, telefone, tipo_funcionario) VALUES (2, 'Fernanda Souza', '987.654.321-00', '11888880000', 'Motoqueiro');

INSERT INTO funcionario (moto_id_moto, nome, cpf, telefone, tipo_funcionario) VALUES (3, 'Ana Lima', '456.789.123-00', '11666660000', 'Motoqueiro');

INSERT INTO funcionario (moto_id_moto, nome, cpf, telefone, tipo_funcionario) VALUES (4, 'Ricardo Alves', '789.123.456-00', '11555550000', 'Motoqueiro');

INSERT INTO funcionario (moto_id_moto, nome, cpf, telefone, tipo_funcionario) VALUES (5, 'Marcos Oliveira', '111.111.111-11', '11988887777', 'Motoqueiro');


## Links

- API Base URL: http://localhost:8080
- Endpoints Principais:
  - Motos: http://localhost:8080/api/motos
  - Funcionários: http://localhost:8080/api/funcionarios
- Swagger UI: http://localhost:8080/swagger-ui.html
- Console do Banco de Dados H2: http://localhost:8080/h2-console
  - JDBC URL: jdbc:h2:mem:testdb
  - User Name: sa
  - Password: (deixe em branco)

