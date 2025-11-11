# MotoSecurityX-java

## Integrantes do Grupo

- Carlos Eduardo (RM 555223)
- Antonio Lino (RM 554518)
- Caio Henrique (RM 554600)

## Descrição do Projeto
O JAVA-CHALLENGE é uma aplicação desenvolvida em Java como parte de uma iniciativa colaborativa com a Mottu, empresa referência em soluções de mobilidade urbana. O objetivo central do projeto é oferecer uma plataforma robusta, escalável e de fácil manutenção para o gerenciamento inteligente de motocicletas e funcionários.

## 🚀 Inovação
A inovação do projeto está na correlação inteligente entre veículos e operadores. Desenvolvemos um sistema que não apenas realiza o cadastro de motos e colaboradores, mas que também mapeia dinamicamente qual funcionário está associado a qual moto, permitindo um controle mais eficiente, auditável e estratégico da frota. Além disso, o projeto possue integração com Docker, para a melhora de portabilidade e o uso do banco de dados H2, deixando simples e leve.

Essa funcionalidade, embora simples à primeira vista, é fundamental para operações logísticas modernas — reduzindo erros operacionais, facilitando o rastreio de ativos e contribuindo diretamente para a eficiência operacional e redução de custos.

## Execução com DOCKER

``
cd JAVA-CHALLENGE
``

``
git clone https://github.com/caiohc28/JAVA-CHALLENGE.git
``

``
docker build -t javadocker .
``

``
docker run -d -p 8088:8080 javadocker
``

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

