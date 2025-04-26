
# 🍺 Projeto Reactive Beer API

Este projeto demonstra uma arquitetura **reativa** usando **Spring WebFlux**, **Kafka** e um backend simulando operações de CRUD de cervejas.

## 🚀 Tecnologias Utilizadas

- Java 21+
- Spring Boot 3.4.4
- Spring WebFlux (WebClient)
- Reactor Kafka
- Apache Kafka
- MongoDB (docker-compose)
- Lombok
- JUnit 5

## 📦 Endpoints

### API Principal (Beer REST API)
Base URL:
```bash
http://localhost:8086/api/v1/beer
```

| Método | Rota                | Ação                 |
|--------|---------------------|----------------------|
| GET    | `/api/v1/beer`        | Listar todas as cervejas |
| GET    | `/api/v1/beer/{id}`   | Buscar cerveja por ID |
| POST   | `/api/v1/beer`        | Criar nova cerveja    |
| PUT    | `/api/v1/beer/{id}`   | Atualizar cerveja     |
| PATCH  | `/api/v1/beer/{id}`   | Atualizar parcial     |
| DELETE | `/api/v1/beer/{id}`   | Deletar cerveja       |

### API Kafka Sender
Base URL:
```bash
http://localhost:8085/api/v1/kafka/beer
```

| Método | Rota                 | Ação via Kafka        |
|--------|----------------------|-----------------------|
| POST   | `/api/v1/kafka/beer`   | Envia criação para Kafka |
| PUT    | `/api/v1/kafka/beer/{id}` | Atualiza via Kafka  |
| PATCH  | `/api/v1/kafka/beer/{id}` | Atualiza parcial via Kafka |
| DELETE | `/api/v1/kafka/beer/{id}` | Deleta via Kafka     |

### WebClient
- `GET /call` ➡️ Faz um GET reativo para listar cervejas.
- `POST /post` ➡️ Faz um POST reativo para criar uma nova cerveja.

## 🧱 Modelo Beer

```java
@Builder
@Data
public class Beer {
    private String httpMethod;
    private UUID id;
    private Integer version;
    private String beerName;
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
```

Enum BeerStyle:
- LAGER, PILSNER, STOUT, GOSE, PORTER, ALE, WHEAT, IPA, PALE_ALE, SAISON

## ⚙️ docker-compose

```yaml
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.5.1
    ports:
      - "2181:2181"

  kafka:
    image: confluentinc/cp-kafka:7.5.1
    ports:
      - "9092:9092"
      - "29092:29092"
    environment:
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181

  kafka-ui:
    image: provectuslabs/kafka-ui
    ports:
      - "9080:8080"

  mongo:
    image: mongo:7.0
    ports:
      - "27017:27017"

  mongo-express:
    image: mongo-express:1.0.2
    ports:
      - "9081:8081"
```

## 🛠️ Como rodar o projeto

1. Suba o ambiente:
```bash
docker-compose up -d
```

2. Rode as aplicações:
```bash
./mvnw spring-boot:run
```

- Porta 8085: WebClient + Kafka Producer
- Porta 8086: API REST + Kafka Consumer

## ✅ Testes

Para rodar os testes:
```bash
./mvnw test
```

## 💡 Extras

- API sem persistência em banco de dados (armazenamento em memória com Map<UUID, Beer>).
- Pode ser facilmente estendida para uso com banco de dados (JPA/Hibernate).
- Estrutura modular e de fácil entendimento.

## 📬 Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir PRs ou issues com melhorias ou sugestões.

## 🧑‍💻 Autor

Desenvolvido por Thone
📧 thonecardoso@gmail.com 
🐙 GitHub: [thonecardoso](https://github.com/thonecardoso)

## 📝 Licença

Este projeto está sob a licença MIT.
