# Sobre o projeto SfTech Sales API

Este é um projeto pessoal de e-commerce, onde utilizei Clean Architecture, Clean Code e as melhores práticas para desenvolvimento backend. O projeto foi construído com Spring Boot, JPA/Hibernate e JUnit, garantindo uma estrutura robusta, escalável e completamente testada.

## Demonstração

Este backend faz parte do projeto portfólio Sf-tech, veja mais em https://sf-tech-front.vercel.app/

## 🛠 Tecnologias utilizadas

O backend foi desenvolvido com as seguintes tecnologias principais:

- **Spring Boot** – Framework para aplicações Java escaláveis e modulares
- **JPA/Hibernate** – ORM para manipulação eficiente do banco de dados
- **JUnit** – Framework de testes para garantir alta cobertura de código
- **PostgreSQL** – Banco de dados relacional robusto e confiável
- **Swagger/OpenAPI** – Documentação interativa da API
- **Lombok** – Redução de boilerplate code
- **Maven** – Gerenciamento de dependências e build

Outras bibliotecas importantes incluem:

- Spring Data JPA (persistência)
- Spring Web (API REST)
- Spring Security (autenticação e autorização)
- Spring Validation (validações)
- SLF4J (logging)

## ▶️ Rodando o projeto localmente

### 📌 Pré-requisitos

- Java 21
- Maven
- PostgreSQL

### 🔥 Configuração do Banco de Dados

```bash
# Criar o banco de dados
sudo -u postgres psql -c "CREATE DATABASE sf_tech_sales;"

# Configurar usuário
sudo -u postgres psql -c "ALTER USER postgres WITH PASSWORD 'postgres';"
```

### 📦 Instalação das dependências

```bash
mvn clean install
```

### 🏃 Iniciando o servidor

```bash
mvn spring-boot:run
```

### 📚 Documentação da API

Após iniciar o servidor, acesse a documentação Swagger em:

```
http://localhost:8080/swagger-ui.html
```

## 📜 Licença

Este projeto está sob a licença MIT.
