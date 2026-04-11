# Bank - Sistema Bancário em Java

Sistema bancário simples desenvolvido em Java, com funcionalidades de criação de contas com senha, autenticação por número de conta e senha, depósito, saque e consulta de saldo.

---

## Estrutura do Projeto

```
co.Daniel.Bank/
├── App.java        # Ponto de entrada, interface com o usuário via terminal
├── Bank.java       # Gerencia a agência e a lista de contas
├── Account.java    # Representa uma conta bancária individual
└── Log.java        # Utilitário de log para registrar operações
```

---

## Descrição das Classes

### `App.java`

Ponto de entrada da aplicação. Responsável pelo loop principal de interação com o usuário via terminal. Toda leitura de entrada usa `nextLine()` com conversão explícita, evitando problemas de buffer com `nextInt()` e `nextDouble()`.

**Menu principal:**

- `C` — Criar nova conta (solicita nome e senha de 6 dígitos)
- `E` — Entrar em conta existente pelo número da conta e senha
- `S` — Sair do sistema

**Menu de operações:**

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
| `findAccount(accountNumber, password)` | Busca conta validando senha via `checkPassword()` |

---

### `Account.java`

Representa uma conta bancária individual.

| Campo / Método | Descrição |
|---|---|
| `name` | Nome do titular (máx. 12 caracteres, apenas letras e espaços) |
| `agency` | Agência da conta |
| `account` | Número da conta |
| `passwordHash` | Hash SHA-256 da senha — nunca armazenada em texto puro |
| `balance` | Saldo atual (`BigDecimal`) |
| `setName(name)` | Valida e define o nome do titular |
| `setPassword(password)` | Valida os 6 dígitos e armazena o hash SHA-256 |
| `checkPassword(password)` | Compara o hash do input com o hash armazenado |
| `getAccountNumber()` | Retorna o número da conta |
| `deposit(value)` | Deposita valor positivo (`BigDecimal`) |
| `withDraw(value)` | Realiza saque se o valor for válido e houver saldo suficiente |
| `hash(password)` | Gera hash SHA-256 — método privado, uso interno |
| `toString()` | Exibe as informações da conta formatadas |

**Regras de negócio:**

- Nome aceita apenas letras e espaços (`[\p{L} ]+`)
- Nomes com mais de 12 caracteres são truncados automaticamente
- Senha deve ter exatamente 6 dígitos numéricos (100000–999999)
- Senha nunca é armazenada — apenas seu hash SHA-256
- Autenticação compara hashes, nunca valores em texto puro
- Saldo armazenado como `BigDecimal` com escala de 2 casas decimais
- Arredondamento via `RoundingMode.HALF_UP`
- Depósitos e saques com valor `<= 0` são rejeitados
- Saque com saldo insuficiente é bloqueado e logado
- Depósitos e saques bem-sucedidos registram data e hora via `logger.outDateTime()`

---

### `Log.java`

Utilitário de log. Registra mensagens no console com prefixo `LOG:` e data/hora das operações.

| Método | Descrição |
|---|---|
| `out(message)` | Imprime uma mensagem de log com prefixo `LOG:` |
| `outDateTime()` | Imprime a data e hora no formato `dd/MM/yyyy - HH:mm:ss` |

```
LOG: SAQUE - R$50.00 - Seu saldo atual é de R$150.00
LOG: Saque realizado com sucesso!
Operação realizada em: 07/04/2026 - 14:32:10
```

---

## Correções e Melhorias Aplicadas

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
| 9 | `Account.java` | Sem autenticação por senha | Adicionados campo `password`, `setPassword()` com validação |
| 10 | `App.java` | `Integer.parseInt()` quebrava ao receber caracteres não numéricos na senha | Adicionado `replaceAll("[^0-9]", "")` para sanitizar a entrada antes do parse |
| 11 | `Log.java` | Log não registrava data/hora das operações | Adicionado `outDateTime()` com `OffsetDateTime` e `DateTimeFormatter` |
| 12 | `Account.java` | Depósito e saque não informavam quando foram realizados | Adicionada chamada a `logger.outDateTime()` após operações bem-sucedidas |
| 13 | `Account.java` | Senha armazenada como `int` em texto puro | Substituído por hash SHA-256 em `String passwordHash` |
| 14 | `Account.java` | `getPassword()` expunha a senha | Removido — substituído por `checkPassword(int password)` |
| 15 | `Account.java` | Código morto no `setPassword()` após `throw` | Reestruturado — hash executado após validação |
| 16 | `Account.java` | Saldo como `double` causava erros de precisão monetária | Substituído por `BigDecimal` com `setScale(2, HALF_UP)` |
| 17 | `App.java` | `Integer.parseInt("")` derrubava o programa no login com entrada vazia | Login envolvido em `try-catch NumberFormatException` |
| 18 | `Bank.java` | `findAccount()` comparava senha em texto puro | Atualizado para usar `account.checkPassword(password)` |
| 19 | `App.java` | `Math.round` como workaround para imprecisão do `double` | Removido — `BigDecimal` resolve na raiz |
| 20 | `App.java` | Parsing de valores monetários usava `Double.parseDouble` | Substituído por `new BigDecimal(...).setScale(2, HALF_UP)` |

---

## Como Executar

**Pré-requisitos:** Java JDK 11 ou superior.

```bash
# Compilar
javac co/Daniel/Bank/*.java

# Executar
java co.Daniel.Bank.App
```

---

## Tecnologias

- **Java** — linguagem principal
- **java.security.MessageDigest** — hash SHA-256 para senhas
- **java.math.BigDecimal** — precisão monetária
- **java.math.RoundingMode** — arredondamento monetário
- **java.util.Scanner** — leitura de entrada do usuário
- **java.util.ArrayList** — armazenamento das contas
- **java.time.OffsetDateTime** — registro de data e hora nas operações
