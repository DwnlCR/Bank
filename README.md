# Bank - Sistema Bancário em Java

Sistema bancário simples desenvolvido em Java, com funcionalidades de criação de contas, login, depósito e saque.

---

## 📁 Estrutura do Projeto

```
co.Daniel.Bank/
├── App.java        # Ponto de entrada, interface com o usuário via terminal
├── Bank.java       # Gerencia a agência e a lista de contas
├── Account.java    # Representa uma conta bancária individual
└── Log.java        # Utilitário de log para registrar operações
```

---

## 🗂️ Descrição das Classes

### `App.java`
Ponto de entrada da aplicação. Responsável pelo loop principal de interação com o usuário via terminal (`Scanner`).

**Funcionalidades disponíveis no menu principal:**
- `C` — Criar nova conta
- `E` — Entrar em conta existente pelo número da conta
- `S` — Sair do sistema

**Funcionalidades no menu de operações:**
- `D` — Depositar valor
- `S` — Sacar valor
- `V` — Ver saldo atual
- `E` — Sair da conta

---

### `Bank.java`
Representa a agência bancária. Armazena e gerencia todas as contas criadas.

| Campo / Método | Descrição |
|---|---|
| `agency` | Código da agência |
| `accounts` | Lista de contas cadastradas |
| `lastAccount` | Contador para geração de número de conta |
| `generateAccount(name)` | Cria uma nova conta com número sequencial |
| `insertAccount(account)` | Insere a conta na lista da agência |
| `getAccounts()` | Retorna a lista de contas |
| `findAccount(accountNumber)` | Busca e retorna uma conta pelo número, ou `null` se não encontrada |

---

### `Account.java`
Representa uma conta bancária individual.

| Campo / Método | Descrição |
|---|---|
| `name` | Nome do titular (máx. 12 caracteres, apenas letras e espaços) |
| `agency` | Agência da conta |
| `account` | Número da conta |
| `balance` | Saldo atual (`double`) |
| `setName(name)` | Valida e define o nome do titular |
| `getAccountNumber()` | Retorna o número da conta |
| `deposit(value)` | Deposita valor positivo na conta |
| `withDraw(value)` | Realiza saque se o valor for válido e houver saldo suficiente |
| `toString()` | Exibe as informações da conta formatadas |

**Regras de negócio:**
- Nome aceita apenas letras e espaços (`[\p{L} ]+`)
- Nomes com mais de 12 caracteres são truncados automaticamente
- Depósitos e saques com valor `<= 0` são rejeitados e retornam `false`
- Saque com saldo insuficiente é bloqueado e logado
- Toda operação usa `if/else` encadeado com variável `result`, sem early returns

---

### `Log.java`
Utilitário simples de log. Registra mensagens de operações no console com o prefixo `LOG:`.

```
LOG: SAQUE - R$50.0 - Seu saldo atual é de R$150.0
LOG: Saque realizado com sucesso!
```

---

## ✅ Correções Aplicadas

| # | Arquivo | Problema | Solução |
|---|---|---|---|
| 1 | `Bank.java` | Sem método de busca de conta | Adicionado `findAccount(accountNumber)` |
| 2 | `Account.java` | `getAccountNumber()` inexistente | Adicionado getter para o campo `account` |
| 3 | `App.java` | Login (opção E) não funcionava | Implementado fluxo completo: lê número, busca conta, entra ou exibe erro |
| 4 | `Account.java` | `withDraw` retornava `true` em valor inválido | Corrigida lógica com `if/else` encadeado — valor inválido retorna `false` |
| 5 | `App.java` | Saque usava `nextDouble()` instável | Padronizado com `nextLine()` + `Double.parseDouble()`, igual ao depósito |
| 6 | `App.java` | Opção Ver Saldo inexistente | Adicionada opção `V` que exibe `account.toString()` |

---

## 🚀 Como Executar

**Pré-requisitos:** Java JDK 8 ou superior.

```bash
# Compilar
javac co/Daniel/Bank/*.java

# Executar
java co.Daniel.Bank.App
```

---

## 🔧 Tecnologias

- **Java** — linguagem principal
- **java.util.Scanner** — leitura de entrada do usuário
- **java.util.ArrayList** — armazenamento das contas
