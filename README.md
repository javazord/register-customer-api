# 🏆 Sistema de Controle de Clientes - Backend

Este é o backend do sistema de gerenciamento de clientes e endereços, desenvolvido com **Java 17**, **Spring Boot** e **MySQL**.  
Ele fornece uma API RESTful para cadastro, listagem, atualização e exclusão de clientes, incluindo seus endereços.

## 🚀 Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **MySQL**
- **MapStruct** 
- **Lombok**
- **Maven**

## 📦 Como Rodar o Projeto
1 - git clone https://github.com/javazord/register-customer-api.git

2 - Configurar o Banco de Dados
 - Crie um banco no MySQL (ex.: customers)
 - Atualize o arquivo application.properties com as credenciais do seu banco:

application.properties:
	##spring.datasource.url=jdbc:mysql://localhost:3306/customers
	##spring.datasource.username=root
	##spring.datasource.password=senha
	##spring.jpa.hibernate.ddl-auto=update
	##spring.jpa.show-sql=true

3 - Instalar Dependências
 - mvn clean install (ou IDE -> build)


 - Estrutura do Projeto
src/
 ├── main/java/br/com/studiyng/crud/jz
 │    ├── controller    # Endpoints REST
 │    ├── service       # Regras de negócio
 │    ├── repository    # Acesso ao banco de dados
 │    ├── model         # Entidades e DTOs
 │    └── mapper        # MapStruct mappers
 └── main/resources
      └── application.properties
      
