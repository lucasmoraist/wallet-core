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