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
- VS Code (para debug)

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

### 🔍 Modo Debugger

1. Abra o projeto no VS Code
2. Instale as extensões necessárias:
   - Java Extension Pack
   - Spring Boot Extension Pack
3. Vá para a aba "Run and Debug" (Ctrl+Shift+D)
4. Selecione "Debug Spring Boot"
5. Clique no botão de play verde ou pressione F5

O debugger permitirá:

- Definir breakpoints (clique na margem esquerda do código)
- Inspecionar variáveis (painel VARIABLES)
- Ver a pilha de chamadas (painel CALL STACK)
- Executar código passo a passo (F10 para step over, F11 para step into)
- Avaliar expressões (painel WATCH)

### 📚 Documentação da API

Após iniciar o servidor, acesse a documentação Swagger em:

```
http://localhost:8080/swagger-ui.html
```

## 📜 Licença

Este projeto está sob a licença MIT.
