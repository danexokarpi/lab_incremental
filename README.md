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

# Como rodar os testes do projeto
```bash
./gradlew test
```

Após isso basta entrar nos arquivos locais e abrir o index.html que será criado dentro da pasta JaCoCo!

# Como Jogar

No mapa:

- Escolha, com as setas de para cima e para baixo, qual fase você quer entrar
- Ao entrar em uma fase, um combate irá iniciar
- Se você vencer esse combate você entrará no mapa novamente para escolher a próxima fase

Durante o combate:

- O jogador possui um **baralho de cartas** (Agora introduzido como ações que podem ser escolhidas pelas setinhas do MOUSE (QUE INCRÍVEL!!!!!!))
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

# Mecânica de salvamento

O jogo é salvo logo quando uma nova fase é iniciada, conservando a vida do jogador, sua energia máxima, suas cartas e sua posição no mapa.
Caso você perca, seu progresso é perdido, obrigando-o a iniciar o jogo do início.

P.S.: Um sistema de ganho de novas cartas durante o jogo AINDA não foi implementado, deixando o jogador limitado a uma pequena quantidade de cartas durante o jogo.
 
# Mecânica de Eventos
-Todos os eventos menos a batalha final são completamente aleatórios, assim temos o evento de escolha Ambulancia Abandonada, que faz com que você escolha um status para aumentar do seu heroi. Ademais, os eventos para aumentar a variedade do jogo foram a Loja e a Fogueira, onde ambos usam o desing command para o seu funcionamento, ou seja, ao invés de usar vários métodos, apenas criamos as classes dos comandos escolhidos e usamos o método executar delas.
-Assim, na loja você pode comprar cartas novas ou remover cartas que você possui, sempre gastando ouro (VEJA OQUE O VENDEDOR FALA QUANDO VOCÊ ESTÁ SEM OURO)
-Já a fogueira faz com que você possa ganhar uma carta boa, ou que você descanse, oque regenera vida.
# Quanto aos Testes
-Nós decidimos usar a biblioteca Mockito pois ela permite a rápida e fácil execução de comandos que outrora seriam impossíveis de se realizar com bibliotecas padrões de java. Isso fez com que a implementação de testes mais importantes e precisos fosse possível, assegurando a qualidade do código!

# Diagrama UML

<img width="300" height="104" alt="diagram" src="https://github.com/user-attachments/assets/1084539d-5a23-477e-8c0c-4861a549fc1b" />

# Tecnologias Utilizadas
- Java 25
- Visual Studio Code
- Neovim
- JaCoCo
- Mockito
- Git e GitHub
- Gradle e Gradlew
- ChatGPT e Deepseek (para documentações e diagrama UML)

# Vem já já

-Iremos refazer o iconico gif do jogo rodando assim que possível!

# Autores

Projeto desenvolvido por:

- Danilo Henrique Brondi Karpiuck (RA 223386)
- Vítor Guimarães Duarte (RA 268526)
