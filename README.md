# Projeto MC322 - Roguelike Deckbuilder

Este projeto foi desenvolvido como parte dos laboratórios da disciplina **MC322 - Programação Orientada a Objetos**.

O objetivo é implementar um jogo inspirado em **Slay the Spire**, no qual o jogador utiliza um **baralho de cartas** para derrotar inimigos em batalhas por turno.

O projeto foi desenvolvido em **Java** e executado via terminal.

# Estrutura do Projeto

O projeto segue a estrutura padrão de Java criada pelo gradle:

# Como Compilar o Projeto

No diretório raiz do projeto, execute:

```bash
javac -d bin $(find src -name "*.java")
```
Esse comando compila todos os arquivos `.java` dentro da pasta `src` e coloca os arquivos compilados (`.class`) na pasta `bin`.

Como alternativa, você pode utilizar também os comandos padrão do gradlew, como:

```bash
./gradlew build
```

# Como Executar o Projeto

Após compilar, execute:

```bash
java -cp bin App
```
ou, usando o gradlew:

```bash
./gradlew run
```
(o comando acima também compila o código automaticamente)

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

#Mecânica de Efeitos:
-Certos inimigos e ações podem causar efeitos, até agora os seguintes efeitos e suas respectivas propriedades forem implementadas:
  -Veneno - Veneno da dano baseado na quantidade de stacks de veneno que se possuí, caso uma entidade tenha 3 stacks ele da 3 de dano ao dono todo fim de turno, de mesma forma, no fim do turno uma stack do veneno é     perdida
  -Regeneração - Regeneração cura baseado na quantidade de stacks de regeneração que se possui, caso uma entidade tenha 3 stacks ela cura 3 de vida para o dono todo fim de turno, de mesma forma, no fim do turno, uma stack é perdida.

  -Ademais, utilizamos um sistema de subscriber e publisher para ativar os efeitos, tanto as entidades quanto os efeitos que elas possuem agem como subscribers, e a classe de tabuleiro age como publisher, assim que um evento ocorre o tabuleiro reporta para as entidades que então reportam para seus efeitos, consideramos isso mais adequado pois nesse caso quando um inimigo morre, todos os seus efeitos são denconsiderados e apagados.


# Tecnologias Utilizadas

- Java 25
- Visual Studio Code
- Neovim
- Git e GitHub
- Gradle e Gradlew

# Autores

Projeto desenvolvido por:

- Danilo Henrique Brondi Karpiuck (RA 223386)
- Vítor Guimarães Duarte (RA 268526)
