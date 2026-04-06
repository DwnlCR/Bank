# Bank - Sistema Bancário em Java

Sistema bancário simples desenvolvido em Java, com funcionalidades de criação de contas, depósito e saque.

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
- `S` — Entrar em conta existente *(incompleto)*
- `E` — Sair do sistema

**Funcionalidades no menu de operações:**
- `D` — Depositar valor
- `S` — Sacar valor
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
| `deposit(value)` | Deposita valor positivo na conta |
| `withDraw(value)` | Realiza saque se houver saldo suficiente |
| `toString()` | Exibe as informações da conta formatadas |

**Regras de negócio:**
- Nome aceita apenas letras e espaços (`[\p{L} ]+`)
- Nomes com mais de 12 caracteres são truncados automaticamente
- Depósitos e saques com valor `<= 0` são rejeitados
- Saque com saldo insuficiente é bloqueado e logado

---

### `Log.java`
Utilitário simples de log. Registra mensagens de operações no console com o prefixo `LOG:`.

```
LOG: SAQUE - R$50.0 - Seu saldo atual é de R$150.0
LOG: Saque realizado com sucesso!
```

---

## ⚠️ Limitações Conhecidas

- **Login (opção S) incompleto:** o fluxo de entrar em uma conta existente lê o nome do usuário mas não busca nem retorna a conta correspondente.
- **`Bank` sem busca de conta:** não existe método para localizar uma conta pelo número, impossibilitando o login.
- **`withDraw` retorna `true` em valor inválido:** quando `value <= 0`, a variável `result` permanece `true` e é retornada incorretamente.
- **Leitura do saque inconsistente:** o saque usa `nextDouble()`, enquanto o depósito usa `nextLine()` + `Double.parseDouble()`, o que pode causar bugs de buffer no `Scanner`.
- **Precisão numérica:** o uso de `double` para representar valores monetários pode causar erros de arredondamento (ex: `0.1 + 0.2 = 0.30000000000000004`).

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
