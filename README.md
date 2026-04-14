# Bank - Sistema Bancário em Java

Sistema bancário desenvolvido em Java com arquitetura MVC, funcionalidades de criação de contas com senha, autenticação por número de conta e senha, depósito, saque, consulta de saldo e extrato de transações.

---

## Estrutura do Projeto

```
co.Daniel.Bank/
├── App.java                    # Ponto de entrada — instancia controller e inicia menu
├── model/
│   ├── Account.java            # Representa uma conta bancária individual
│   ├── Bank.java               # Gerencia a agência e a lista de contas
│   ├── Log.java                # Utilitário de log para registrar operações
│   └── Transaction.java        # Representa uma transação financeira
├── controller/
│   └── BankController.java     # Intermediário entre view e model
└── view/
    ├── MainMenu.java           # Loop principal de interação com o usuário
    ├── AccountMenu.java        # Loop de operações da conta logada
    ├── MainOption.java         # Enum das opções do menu principal
    └── AccountOption.java      # Enum das opções do menu de operações
```

---

## Arquitetura MVC

O projeto segue o padrão Model-View-Controller:

- **Model** — entidades e regras de negócio (`Account`, `Bank`, `Transaction`, `Log`)
- **Controller** — intermediário que recebe ações da View e executa no Model (`BankController`)
- **View** — interface com o usuário via terminal (`MainMenu`, `AccountMenu`, enums)

A View nunca acessa o Model diretamente — tudo passa pelo Controller.

---

## Descrição das Classes

### `App.java`

Ponto de entrada da aplicação. Instancia o `BankController` e inicia o `MainMenu`.

---

### `model/Bank.java`

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

### `model/Account.java`

Representa uma conta bancária individual.

| Campo / Método | Descrição |
|---|---|
| `name` | Nome do titular (máx. 12 caracteres, apenas letras e espaços) |
| `agency` | Agência da conta |
| `account` | Número da conta |
| `passwordHash` | Hash SHA-256 da senha — nunca armazenada em texto puro |
| `balance` | Saldo atual (`BigDecimal`) |
| `history` | Lista imutável de transações |
| `setName(name)` | Valida e define o nome do titular |
| `setPassword(password)` | Valida os 6 dígitos e armazena o hash SHA-256 |
| `checkPassword(password)` | Compara o hash do input com o hash armazenado |
| `getAccountNumber()` | Retorna o número da conta |
| `getHistory()` | Retorna lista imutável de transações |
| `deposit(value)` | Deposita valor positivo (`BigDecimal`), registra no histórico |
| `withdraw(value)` | Realiza saque se valor válido e saldo suficiente, registra no histórico |
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
- Toda transação é registrada no histórico com tipo, valor, saldo e data/hora

---

### `model/Transaction.java`

Representa uma transação financeira. Implementada como `record` — imutável por natureza.

| Campo / Método | Descrição |
|---|---|
| `type` | Tipo da transação (`DEPOSITO` ou `SAQUE`) |
| `amount` | Valor da transação (`BigDecimal`) |
| `balanceAfter` | Saldo após a transação (`BigDecimal`) |
| `when` | Data e hora da transação (`LocalDateTime`) |
| `formattedWhen()` | Retorna a data/hora formatada como `dd/MM/yyyy - HH:mm:ss` |

---

### `model/Log.java`

Utilitário de log. Registra mensagens no console com prefixo `LOG:` e data/hora das operações.

| Método | Descrição |
|---|---|
| `out(message)` | Imprime mensagem com prefixo `LOG:` |
| `outDateTime()` | Imprime a data e hora no formato `dd/MM/yyyy - HH:mm:ss` |

```
LOG: SAQUE - R$50.00 - Seu saldo atual é de R$150.00
LOG: Saque realizado com sucesso!
Operação realizada em: 07/04/2026 - 14:32:10
```

---

### `controller/BankController.java`

Intermediário entre View e Model. A View nunca acessa `Account` ou `Bank` diretamente.

| Método | Descrição |
|---|---|
| `createAccount(name, password)` | Cria e registra uma nova conta |
| `login(accountNumber, password)` | Autentica e retorna a conta |
| `deposit(account, value)` | Executa depósito na conta |
| `withdraw(account, value)` | Executa saque na conta |
| `getHistory(account)` | Retorna o histórico de transações |

---

### `view/MainMenu.java`

Loop principal do programa. Lê opções via `MainOption` e delega ao `BankController`.

**Menu principal:**

- `C` — Criar nova conta (solicita nome e senha de 6 dígitos)
- `E` — Entrar em conta existente pelo número e senha
- `S` — Sair do sistema

---

### `view/AccountMenu.java`

Loop de operações da conta logada. Lê opções via `AccountOption` e delega ao `BankController`.

**Menu de operações:**

- `D` — Depositar valor
- `S` — Sacar valor
- `V` — Ver saldo atual
- `X` — Ver extrato de transações
- `E` — Sair da conta

---

### `view/MainOption.java` e `view/AccountOption.java`

Enums com método `parse(String)` que converte o input do usuário em opção válida. Retorna `null` se a entrada não corresponder a nenhuma opção — o menu exibe "Operacao invalida" e repete.

---

## Correções e Melhorias Aplicadas

| # | Arquivo | Problema | Solução |
|---|---|---|---|
| 1 | `Bank.java` | Sem método de busca de conta | Adicionado `findAccount(accountNumber, password)` |
| 2 | `Account.java` | `getAccountNumber()` inexistente | Adicionado getter para o campo `account` |
| 3 | `App.java` | Login não funcionava | Implementado fluxo completo via `BankController` |
| 4 | `App.java` | `nextInt()` causava bug de buffer no Scanner | Padronizado para `nextLine()` + `Integer.parseInt()` |
| 5 | `App.java` | `scanner.nextLine()` extra consumia dado real no login | Linha removida |
| 6 | `App.java` | Saque usava `nextDouble()` instável | Padronizado com `nextLine()` + `BigDecimal` |
| 7 | `App.java` | `scanner` era recriado a cada iteração do loop | Removida recriação desnecessária de `Scanner` |
| 8 | `App.java` | Opção Ver Saldo inexistente | Adicionada opção `V` |
| 9 | `Account.java` | Sem autenticação por senha | Adicionados `setPassword()` e `checkPassword()` |
| 10 | `App.java` | `Integer.parseInt()` quebrava com entrada não numérica | Adicionado `replaceAll("[^0-9]", "")` |
| 11 | `Log.java` | Log não registrava data/hora | Adicionado `outDateTime()` |
| 12 | `Account.java` | Depósito e saque não registravam data/hora | Adicionada chamada a `logger.outDateTime()` |
| 13 | `Account.java` | Senha armazenada como `int` em texto puro | Substituído por hash SHA-256 em `passwordHash` |
| 14 | `Account.java` | `getPassword()` expunha a senha | Removido — substituído por `checkPassword()` |
| 15 | `Account.java` | Código morto no `setPassword()` após `throw` | Reestruturado — hash executado após validação |
| 16 | `Account.java` | Saldo como `double` causava erros de precisão monetária | Substituído por `BigDecimal` com `setScale(2, HALF_UP)` |
| 17 | `App.java` | `Integer.parseInt("")` derrubava o programa no login | Login envolvido em `try-catch NumberFormatException` |
| 18 | `Bank.java` | `findAccount()` comparava senha em texto puro | Atualizado para usar `checkPassword()` |
| 19 | `App.java` | `Math.round` como workaround para imprecisão do `double` | Removido — `BigDecimal` resolve na raiz |
| 20 | `App.java` | Parsing de valores usava `Double.parseDouble` | Substituído por `new BigDecimal(...).setScale(2, HALF_UP)` |
| 21 | Projeto | Código sem separação de responsabilidades | Aplicada arquitetura MVC com packages `model`, `controller` e `view` |
| 22 | `App.java` | Loop principal misturava input, lógica e exibição | Separado em `MainMenu` e `AccountMenu` |
| 23 | `App.java` | Comparações de string cruas com `equalsIgnoreCase` | Substituídas por enums `MainOption` e `AccountOption` com switch |
| 24 | `Account.java` | `withDraw()` com nomenclatura incorreta | Renomeado para `withdraw()` seguindo convenção Java |
| 25 | `Account.java` | `deposit()` retornava `void` — inconsistente com `withdraw()` | Alterado para retornar `boolean` |
| 26 | Projeto | Sem histórico de transações | Adicionado `Transaction` record e lista `history` em `Account` |
| 27 | `view/AccountMenu.java` | Sem opção de extrato | Adicionada opção `X` com `showHistory()` |

---

## Como Executar

**Pré-requisitos:** Java JDK 11 ou superior.

```bash
# Compilar
javac co/Daniel/Bank/*.java co/Daniel/Bank/model/*.java co/Daniel/Bank/controller/*.java co/Daniel/Bank/view/*.java

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
- **java.time.LocalDateTime** — registro de data e hora nas transações
- **java.time.format.DateTimeFormatter** — formatação de data e hora
