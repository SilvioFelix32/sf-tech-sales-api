### Clean install

mvn clean install

### How to run local

mvn spring-boot:run

### Initial Project Structure

src/main/java/com/sftech/sales/
├── domain/ # Camada mais interna - Regras de negócio puro
│ ├── model/ # Entidades de domínio
│ │ └── Sale.java # Entidade principal
│ ├── repository/ # Interfaces de repositório
│ │ └── SaleRepository.java
│ └── service/ # Serviços de domínio
│ └── SaleService.java
│
├── application/ # Casos de uso e regras de aplicação
│ ├── dto/ # DTOs de entrada/saída
│ │ ├── SaleRequest.java
│ │ └── SaleResponse.java
│ └── service/ # Serviços de aplicação
│ └── SaleApplicationService.java
│
├── infrastructure/ # Implementações técnicas
│ ├── config/ # Configurações
│ │ ├── DatabaseConfig.java
│ │ └── WebConfig.java
│ └── persistence/ # Implementações de persistência
│ ├── entity/ # Entidades JPA
│ │ └── SaleEntity.java
│ ├── mapper/ # Mappers
│ │ └── SaleMapper.java
│ └── repository/ # Implementações de repositório
│ └── SaleRepositoryImpl.java
│
└── presentation/ # Interface com o mundo externo
├── controller/ # Controladores REST
│ └── SaleController.java
├── dto/ # DTOs de apresentação
│ ├── SaleRequestDTO.java
│ └── SaleResponseDTO.java
└── exception/ # Tratamento de exceções
└── GlobalExceptionHandler.java
