# Sobre o projeto SfTech Sales API

Este é um projeto pessoal de e-commerce, onde utilizei Clean Architecture, Clean Code e as melhores práticas para desenvolvimento backend. O projeto foi construído com Spring Boot, JPA/Hibernate e JUnit, garantindo uma estrutura robusta, escalável e completamente testada.

## Demonstração

Este backend faz parte do projeto portfólio Sf-tech, veja mais em https://sf-tech-front.vercel.app/

## 🛠 Tecnologias utilizadas

O backend foi desenvolvido com as seguintes tecnologias principais:

- **Spring Boot 3.2.3** – Framework para aplicações Java escaláveis e modulares
- **Java 21** – Linguagem de programação
- **JPA/Hibernate** – ORM para manipulação eficiente do banco de dados
- **PostgreSQL** – Banco de dados relacional robusto e confiável
- **MapStruct** – Mapeamento entre entidades e DTOs
- **Lombok** – Redução de boilerplate code
- **Maven 3.9.9** – Gerenciamento de dependências e build
- **Swagger/OpenAPI** – Documentação interativa da API
- **Spring Security** – Autenticação e autorização
- **Flyway** – Migrações de banco de dados

## ▶️ Rodando o projeto

### 📌 Pré-requisitos

- Docker e Docker Compose (opção 1)
- OU Java 21 e Maven (opção 2)
- PostgreSQL (banco de dados)

### 🐳 Opção 1: Usando Docker (Recomendado)

#### Build e execução com Docker Compose:

```bash
# Build e iniciar o container
docker compose up -d

# Ver logs em tempo real
docker compose logs -f

# Parar o container
docker compose down
```

#### Build e execução manual:

```bash
# Build da imagem
docker build -t sf-tech-sales-api:latest .

# Executar o container
docker run -d \
  --name sf-tech-sales-api \
  -p 8080:8080 \
  -e DATABASE_URL="jdbc:postgresql://seu-host:5432/seu-db" \
  -e DATABASE_USERNAME="seu-usuario" \
  -e DATABASE_PASSWORD="sua-senha" \
  sf-tech-sales-api:latest

# Ver logs
docker logs -f sf-tech-sales-api

# Parar o container
docker stop sf-tech-sales-api
docker rm sf-tech-sales-api
```

**Variáveis de ambiente necessárias:**
- `DATABASE_URL`: URL completa do banco PostgreSQL
- `DATABASE_USERNAME`: Usuário do banco
- `DATABASE_PASSWORD`: Senha do banco
- `SPRING_PROFILES_ACTIVE`: Perfil Spring (opcional, padrão: `prod`)

### ☕ Opção 2: Usando Maven

#### Pré-requisitos adicionais:
- Java 21 instalado
- Maven 3.9+ instalado
- PostgreSQL rodando localmente (ou configurar variáveis de ambiente)

#### Configuração:

1. Configure as variáveis de ambiente ou edite o arquivo `env_file`:
```bash
export DATABASE_URL="jdbc:postgresql://seu-host:5432/seu-db"
export DATABASE_USERNAME="seu-usuario"
export DATABASE_PASSWORD="sua-senha"
```

2. Instale as dependências:
```bash
mvn clean install
```

3. Execute a aplicação:
```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

### 🔍 Modo Debugger (VS Code)

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

## 📡 Endpoints da API

Base URL: `http://localhost:8080/api/sales`

### Criar Venda
```http
POST /api/sales
Headers:
  company_id: string (obrigatório)
  user_id: string (obrigatório)
Body: {
  "total": number,
  "items": [
    {
      "category_id": string,
      "product_id": string,
      "sku": string,
      "title": string,
      "subtitle": string,
      "description": string,
      "url_banner": string,
      "quantity": number,
      "total_value": number
    }
  ]
}
```

### Listar Vendas
```http
GET /api/sales
Headers:
  company_id: string (obrigatório)
  user_id: string (opcional - se fornecido, retorna vendas do usuário)
```

### Buscar Venda por ID
```http
GET /api/sales/{saleId}
Headers:
  company_id: string (obrigatório)
```

### Deletar Venda
```http
DELETE /api/sales/{saleId}
Headers:
  company_id: string (obrigatório)
```

## 📚 Documentação da API

Após iniciar o servidor, acesse a documentação Swagger em:

```
http://localhost:8080/swagger-ui.html
```

Documentação OpenAPI JSON:
```
http://localhost:8080/api-docs
```

## 🏗️ Estrutura do Projeto (Clean Architecture)

O projeto segue os princípios de **Clean Architecture**, garantindo separação de responsabilidades e independência entre camadas:

```
src/main/java/com/sftech/sales/
├── application/                    # Camada de Aplicação (Use Cases)
│   ├── dto/                       # Data Transfer Objects
│   ├── service/                   # Casos de Uso da Aplicação
│   │   └── SaleService.java
│   └── port/
│       └── out/                   # Portas de Saída (Interfaces)
│           └── SaleMapperPort.java
│
├── domain/                        # Camada de Domínio (Core Business)
│   ├── entity/                    # Entidades de Negócio
│   │   ├── Sale.java
│   │   └── SaleItem.java
│   ├── exception/                 # Exceções de Domínio
│   │   ├── BusinessException.java
│   │   ├── BadRequestException.java
│   │   ├── SaleNotFoundException.java
│   │   └── ...
│   └── port/
│       └── out/                   # Portas de Saída (Interfaces)
│           └── SaleRepositoryPort.java
│
└── infrastructure/                # Camada de Infraestrutura
    ├── config/                    # Configurações
    │   ├── OpenApiConfig.java
    │   └── SecurityConfig.java
    ├── exception/                 # Tratamento de Exceções HTTP
    │   ├── GlobalExceptionHandler.java
    │   └── ErrorResponse.java
    ├── http/                      # Controllers REST
    │   └── controller/
    │       └── SaleController.java
    └── persistence/               # Persistência de Dados
        ├── adapter/               # Adapters (Implementações das Portas)
        │   ├── SaleRepositoryAdapter.java
        │   └── SaleMapperAdapter.java
        ├── mapper/                # Mappers (MapStruct)
        │   ├── SaleMapper.java
        │   └── SaleItemMapper.java
        └── repository/            # Repositórios JPA
            └── SaleRepository.java
```

### 📐 Princípios da Arquitetura

- **Domain**: Contém as regras de negócio puras, sem dependências externas
- **Application**: Implementa os casos de uso, orquestra operações entre domínio e infraestrutura
- **Infrastructure**: Implementa detalhes técnicos (banco de dados, HTTP, frameworks)
- **Dependências**: Domain ← Application ← Infrastructure (regra de dependência unidirecional)

## 🚀 Deploy

### Render.com

1. Conecte seu repositório GitHub ao Render
2. Configure as variáveis de ambiente:
   - `DATABASE_URL`: URL completa do PostgreSQL
   - `DATABASE_USERNAME`: Usuário do banco
   - `DATABASE_PASSWORD`: Senha do banco
   - `SPRING_PROFILES_ACTIVE=prod`
3. Render detectará automaticamente o Dockerfile
4. Porta: 8080
5. Health Check: `/actuator/health`

## 📜 Licença

Este projeto está sob a licença MIT.
