# Projeto MC322 - Roguelike Deckbuilder

Este projeto foi desenvolvido como parte dos laboratórios da disciplina **MC322 - Programação Orientada a Objetos**.

O objetivo é implementar um jogo inspirado em **Slay the Spire**, no qual o jogador utiliza um **baralho de cartas** para derrotar inimigos em batalhas por turno.

O projeto foi desenvolvido em **Java** e executado via terminal.

# Estrutura do Projeto

O projeto segue a estrutura padrão criada pelo VSCode para projetos Java:

```text
.
├─ src/
│  ├─ App.java
│  ├─ Heroi.java
│  ├─ Inimigo.java
│  ├─ CartaDano.java
│  ├─ CartaEscudo.java
│  └─ ...
├─ lib/
├─ bin/
└─ README.md
```

Onde:

- **src** — contém todos os arquivos `.java` do projeto  
- **lib** — pasta reservada para dependências externas (não utilizada neste projeto)  
- **bin** — arquivos `.class` gerados após a compilação  

# Como Compilar o Projeto

No diretório raiz do projeto, execute:

```bash
javac -d bin $(find src -name "*.java")
```

Esse comando compila todos os arquivos `.java` dentro da pasta `src` e coloca os arquivos compilados (`.class`) na pasta `bin`.

# Como Executar o Projeto

Após compilar, execute:

```bash
java -cp bin App
```

Isso iniciará o programa e o sistema de combate será executado no terminal.

# Como Jogar

Durante o combate:

- O jogador possui um **baralho de cartas** (Agora introduzido como ações que podem ser escolhidas ao digitar seu respectivo número)
- No início de cada turno, cartas são compradas para a **mão**
- Cada carta possui um **custo de energia**
- O jogador pode usar cartas enquanto tiver energia disponível
- Ao final do turno do jogador, os **inimigos realizam suas ações**

O combate termina quando:

- o **herói é derrotado** (Vida chega à zero), ou
- todos os **inimigos são derrotados** (O inimigo tem sua vida zerada)


# Tecnologias Utilizadas

- Java 25
- Visual Studio Code
- Git e GitHub
- Gradle

# Autores

Projeto desenvolvido por:

- Danilo Henrique Brondi Karpiuck, RA 223386

- Vítor Guimarães Duarte, RA 268526
