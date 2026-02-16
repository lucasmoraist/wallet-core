# Wallet Core Service

Microserviço responsável pela gestão de usuários, carteiras digitais e processamento transacional de saldo (ledger) no ecossistema PayFlow.

## 🚀 Visão Geral

O **Wallet Core** atua como a autoridade de saldo do sistema. Ele gerencia o ciclo de vida dos usuários e suas carteiras, além de processar as transações financeiras solicitadas via mensageria.

**Principais Responsabilidades:**
- Cadastro de Usuários (Clientes e Lojistas).
- Criação automática de Carteiras.
- Consulta de Saldo.
- Processamento de Débito e Crédito via RabbitMQ.
- Garantia de consistência transacional (ACID).

## 🛠 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 4**
- **Spring Data JPA** (PostgreSQL)
- **Flyway** (Gerenciamento de Migrações de Banco de Dados)
- **Spring Cloud Stream** (RabbitMQ)
- **Docker**

## 🔌 Endpoints (API REST)

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/v1/users` | Cria um novo usuário e sua respectiva carteira. |
| `GET` | `/api/v1/users/{id}` | Busca detalhes de um usuário. |
| `POST` | `/api/v1/wallets` | Cria uma carteira (caso avulsa). |
| `GET` | `/api/v1/wallets/balance` | Consulta saldo (Requer `userId` ou header). |
| `POST` | `/api/v1/transaction/deposit` | Realiza depósito direto (uso interno/teste). |

## 📨 Eventos (Mensageria)

- **Consome:** `transfer.created` (Exchange: `payflow-exchange`)
    - Ação: Verifica saldo, realiza débito no remetente e crédito no destinatário atomicamente.
- **Produz:** - `transfer.success`: Quando a transferência é efetivada.
    - `transfer.failed`: Quando há saldo insuficiente ou erro de validação.

## 🔮 Melhorias Futuras

- [x] Implementar **Spring Security + JWT** para proteger os endpoints de saldo.
- [x] Adicionar **Redis** para cache de consulta de saldo (Strategy Pattern para invalidar cache na transação).
- [x] Implementar **Optimistic Locking** (`@Version`) na entidade Wallet para alta concorrência.
- [x] Aumentar a cobertura de **Testes de Integração** (TestContainers).

### Resiliência e Tolerância a Falhas
- [ ] Adicionar **Circuit Breaker** (Resilience4j) para lidar com falhas nos Use Cases que interagem com db e mensageria.

### Consistência de Dados e Transações
- [ ] Implementar uma verificação de **idempotência** no `OrchestrationTransfer`. Usando o `transferId` da mensagem como chave 
      no Redis para garantir que a mesma transferência não seja processada duas vezes caso a mensagem seja duplicada na fila.

### Observabilidade
- [ ] Observabilidade e monitoramento: Integrar com **Prometheus & Grafana** para métricas de performance e saúde do serviço.

### Refinamento de Domínio e Código
- [ ] **Value Objects:** Em vez de usar `BigDecimal` solto para saldo e valor, crie um Value Object `Money` que encapsula regras de arredondamento e validações (como não permitir valores negativos), aumentando a expressividade do domínio.
- [ ] **Contratos com Bean Validation:** Melhore as validações no `CreateUserRequest`. Adicione validações específicas de CPF/CNPJ (usando bibliotecas como o Hibernate Validator ou customizadas) para evitar que dados inválidos cheguem à camada de persistência.