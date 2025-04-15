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

//Testing Post:
{
"companyId": "b4cce349-7c0b-41c7-9b3e-c21c9f0c2e4c",
"userId": "b4cce349-7c0b-41c7-9b3e-c21c9f0cafafd",
{
"items": [
{
"category_id": "81c96746-8727-49cf-bbc1-acfd35a33017",
"product_id": "45429b59-37ea-4574-9d18-3299033d527f",
"sku": "1a3fd14d-1de6-4c1d-b1b6-43042da5880d",
"title": "Computador",
"subtitle": "Gamer Starter",
"description": "Intel Core i3-10300H, Memória8gb, 120Gb SSD PCIe, NVIDEA GeForce GTX 650 2gb",
"url_banner": "https://i.imgur.com/YvXrCsS.png",
"quantity": 2,
"total_value": 7200
},
{
"category_id": "81c96746-8727-49cf-bbc1-acfd35a33017",
"product_id": "0e27d7bb-ebe9-4c5f-9573-6665112463e8",
"sku": "cec866f7-07f3-4ac7-87c2-83ab964bbea0",
"title": "Computador",
"subtitle": "Gamer Evolution",
"description": "Intel Core i5-10300H, Memória16gb, 240Gb SSD PCIe, NVIDEA GeForce GTX 950 4gb",
"url_banner": "https://i.imgur.com/YvXrCsS.png",
"quantity": 1,
"total_value": 9397
}
],
"total": 23797
}
}
