#Atividade Swing

## Estrutura do projeto:
+---src
    +---dal
    |       Mod_conexao.java
    |
    +---icones
    |       KnobCancel.png
    |       KnobValidGreen.png
    |
    \---telas
            TelaCliente.form
            TelaCliente.java
            TelaLogin.form
            TelaLogin.java
            TelaPrincipal.form
            TelaPrincipal.java
            TelaSobre.form
            TelaSobre.java
            telaUsuarios.form
            telaUsuarios.java

## Stack do Projeto
 - Java: Linguagem de programação principal.
 - Java Swing: Framework para criação da interface gráfica (telas).
 - XAMPP (MySQL): Servidor local utilizado para gerenciar o banco de dados.
 - MySQL Connector: Driver para conexão entre Java e MySQL.
 - NetBeans: Ambiente de desenvolvimento e design das telas.

## Funcionalidades:
 - Login simples: Apenas um select no banco, sem criptografia ou.
 - Crud cliente: Crud apenas com verificação simples de campos sendo o mais avançado um regex para cpf e cnpj.
 - Crud usuário: Crud para usuário de login.

## Entidades:
 - USUARIO: id(incremental), nome, email e senha(sem hash).
 - CLIENTE: id(incremental), nome, endereco, cidade, uf, doc, fone, data de nascimento, tipo de personalidade juridica.

## Telas:
 - TelaUsuarios: Login para a aplicação, também mostra status de conexão com o banco.
 - TelaPrincipal: Visão geral da aplicação.
 - TelaSobre: Dá créditos e direito de distribuição.
 - TelaCliente: Parte central, onde contém todas as funções CRUD do cliente.

## Requisitos para rodar:

### SQL:
-- 0. Banco
CREATE DATABASE usuarios; -- Se mudar o nome modifique Mod_conexao

USE usuarios;

-- 1. Tabela de Login (Acesso do Usuário)
CREATE TABLE tb_usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL
);

-- 2. Cria usuário para login
INSERT INTO tb_usuario (nome, email, senha) VALUES ("xiru", "xiru@gmail.com", "123");

-- 3. Tabela com os dados do cliente
CREATE TABLE tb_cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    endereco VARCHAR(150),
    cidade VARCHAR(100),
    uf CHAR(2),
    documento VARCHAR(20) UNIQUE, -- CPF ou CNPJ
    fone VARCHAR(20),
    data_nasc DATE,
    status VARCHAR(2) -- PF ou PJ. Também pode ser facilitado com ENUM
);

### Dependência:
 - MYSQL connector.

### Rodando:
 - Inicie os serviços MYSQL e Apache no XAMPP.

 cd poo2\banco-de-dados-java-main\
 ant clean jar
 ant run

Observação:
    O projeto NÃO segue alguns padrões de desenvolvimento, como convenções de nomenclatura, boas práticas de segurança, separação de responsabilidades, arquitetura baseada em domínio, etc, pois foi desenvolvido a partir de uma estrutura-base fornecida previamente, que já apresentava tais limitações.