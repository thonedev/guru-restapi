# 🍺 Beer REST API

Esta é uma API REST desenvolvida com Spring Boot para o gerenciamento de cervejas. O projeto permite operações CRUD completas em uma estrutura simples e funcional.

## 🚀 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Lombok
- RESTful APIs
- JUnit 5

## 📦 Endpoints

### Base URL
```bash
http://localhost:8080/api/v1/beer
```

### 📘 Criar uma nova cerveja
```bash
POST /api/v1/beer
```

Body JSON:
```json
{
  "beerName": "Galaxy Cat",
  "beerStyle": "PALE_ALE",
  "upc": "12356",
  "price": 12.99,
  "quantityOnHand": 100,
  "version": 1
}
```

### 📗 Listar todas as cervejas
```bash
GET /api/v1/beer
```

### 📙 Buscar cerveja por ID
```bash
GET /api/v1/beer/{id}
```

### 📕 Atualizar uma cerveja (total)
```bash
PUT /api/v1/beer/{id}
```
Body JSON: mesmo formato do POST.

### 📝 Atualizar uma cerveja (parcial)
```bash
PATCH /api/v1/beer/{id}
```
Body JSON: qualquer campo pode ser enviado de forma parcial.

### ❌ Deletar uma cerveja
```bash
DELETE /api/v1/beer/{id}
```
## 🧱 Modelo de Dados

```java
public class Beer {
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
- LAGER
- PILSNER
- STOUT
- GOSE
- PORTER
- ALE
- WHEAT
- IPA
- PALE_ALE
- SAISON

## 🛠️ Como rodar o projeto

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/beer-rest-api.git
```

3. Importe o projeto em sua IDE (IntelliJ, Eclipse, etc).

4. Rode a aplicação:
```bash
./mvnw spring-boot:run
```

6. Acesse os endpoints via Postman, Insomnia, ou cURL.

---

## ✅ Testes

Execute os testes com:
./mvnw test

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
