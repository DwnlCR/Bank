# Bank - Sistema Bancário em Java

Sistema bancário simples desenvolvido em Java, com funcionalidades de criação de contas com senha, autenticação por número de conta e senha, depósito, saque e consulta de saldo.

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

Ponto de entrada da aplicação. Responsável pelo loop principal de interação com o usuário via terminal (`Scanner`). Toda leitura de entrada usa `nextLine()` com conversão explícita, evitando problemas de buffer com `nextInt()` e `nextDouble()`.

**Funcionalidades disponíveis no menu principal:**

- `C` — Criar nova conta (solicita nome e senha de 6 dígitos)
- `E` — Entrar em conta existente pelo número da conta e senha
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
| `generateAccount(name, password)` | Cria uma nova conta com número sequencial e senha |
| `insertAccount(account)` | Insere a conta na lista da agência |
| `getAccounts()` | Retorna a lista de contas |
| `findAccount(accountNumber, password)` | Busca conta por número e senha; retorna `null` se não encontrada |

---

### `Account.java`

Representa uma conta bancária individual.

| Campo / Método | Descrição |
|---|---|
| `name` | Nome do titular (máx. 12 caracteres, apenas letras e espaços) |
| `agency` | Agência da conta |
| `account` | Número da conta |
| `password` | Senha numérica de 6 dígitos |
| `balance` | Saldo atual (`double`) |
| `setName(name)` | Valida e define o nome do titular |
| `setPassword(password)` | Valida e define a senha (100000–999999) |
| `getAccountNumber()` | Retorna o número da conta |
| `getPassword()` | Retorna a senha da conta |
| `deposit(value)` | Deposita valor positivo na conta |
| `withDraw(value)` | Realiza saque se o valor for válido e houver saldo suficiente |
| `toString()` | Exibe as informações da conta formatadas |

**Regras de negócio:**

- Nome aceita apenas letras e espaços (`[\p{L} ]+`)
- Nomes com mais de 12 caracteres são truncados automaticamente
- Senha deve conter exatamente 6 dígitos numéricos (100000–999999)
- Depósitos e saques com valor `<= 0` são rejeitados
- Saque com saldo insuficiente é bloqueado e logado
- Entrada de valores aceita vírgula ou ponto como separador decimal (ex: `1,50` → `1.50`)

---

### `Log.java`

Utilitário simples de log. Registra mensagens de operações no console com o prefixo `LOG:`.

```
LOG: SAQUE - R$50.0 - Seu saldo atual é de R$150.0
LOG: Saque realizado com sucesso!
```

---

## ✅ Correções e Melhorias Aplicadas

| # | Arquivo | Problema | Solução |
|---|---|---|---|
| 1 | `Bank.java` | Sem método de busca de conta | Adicionado `findAccount(accountNumber, password)` |
| 2 | `Account.java` | `getAccountNumber()` inexistente | Adicionado getter para o campo `account` |
| 3 | `App.java` | Login (opção E) não funcionava | Implementado fluxo completo: lê número e senha, busca conta, entra ou exibe erro |
| 4 | `App.java` | `nextInt()` causava bug de buffer no Scanner | Padronizado para `nextLine()` + `Integer.parseInt()` em toda a aplicação |
| 5 | `App.java` | `scanner.nextLine()` extra consumia dado real no login | Linha removida |
| 6 | `App.java` | Saque usava `nextDouble()` instável | Padronizado com `nextLine()` + `Double.parseDouble()`, igual ao depósito |
| 7 | `App.java` | `scanner` era recriado a cada iteração do loop | Removida recriação desnecessária de `Scanner` |
| 8 | `App.java` | Opção Ver Saldo inexistente | Adicionada opção `V` que exibe `account.toString()` |
| 9 | `Account.java` | Sem autenticação por senha | Adicionados campo `password`, `setPassword()` com validação e `getPassword()` |
| 10 | `App.java` | `Integer.parseInt()` quebrava ao receber caracteres não numéricos na senha | Adicionado `replaceAll("[^0-9]", "")` para sanitizar a entrada antes do parse |

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
