### Throwable
Em Java, Throwable é uma classe base para todas as classes que representam exceções ou erros que podem ocorrer durante a execução de um programa. Throwable é uma superclasse para duas subclasses principais: Exception e Error.

Exception: Essa classe representa exceções que normalmente podem ser tratadas no código, como erros de entrada/saída, problemas de cast de tipos, divisão por zero e assim por diante. Programadores são encorajados a capturar e tratar essas exceções, a fim de evitar que o programa seja encerrado de maneira inesperada.

Error: Esta classe, por outro lado, representa problemas mais sérios que geralmente não devem ser capturados ou tratados. Erros do sistema, erros de memória insuficiente e problemas irrecuperáveis geralmente são representados por subclasses de Error. Quando um erro ocorre, é geralmente sinal de que o programa está em um estado inconsistente e não pode continuar de forma confiável.

A hierarquia de classes de exceção em Java começa com Throwable, que é a superclasse de Exception e Error, e se ramifica ainda mais em várias subclasses específicas para lidar com diferentes tipos de exceções.

Ao lidar com exceções em Java, é importante capturar e tratar exceções apropriadas para garantir que seu programa funcione de maneira mais robusta. Isso envolve a captura de exceções usando blocos try-catch e, em alguns casos, a declaração de exceções que seu código pode lançar usando a cláusula throws.

#
#

Quando usar o IOexeption ou o RuntimeException e qual a diferenca entre eles

Em Java, `IOException` e `RuntimeException` são duas classes de exceção que servem a propósitos diferentes, e a principal diferença entre elas está na forma como são tratadas no código.

### IOException:

IOException é uma classe que estende Exception e `pertence ao grupo de exceções verificadas`. Isso significa que, ao usar IOException, você é obrigado a lidar com a exceção explicitamente em seu código, seja usando uma cláusula try-catch ou declarando a exceção com a cláusula throws.

`IOException é usada para lidar com exceções relacionadas à entrada e saída`, como erros ao ler/escrever arquivos, problemas de conexão de rede, ou qualquer outra operação de entrada/saída que possa falhar.
Exemplos de métodos que lançam IOException incluem FileInputStream.read(), FileOutputStream.write(), Socket.getInputStream(), etc.

### RuntimeException:

RuntimeException é uma classe que estende `Exception mas não é verificada`, `o que significa que você não é obrigado a capturá-la explicitamente ou declará-la com a cláusula throws`. Isso torna as exceções do tipo RuntimeException convenientes para situações em que o tratamento da exceção não é estritamente necessário.

RuntimeException é geralmente usada para representar erros que são causados por problemas na lógica do programa, como divisão por zero, acesso a índices de matriz inválidos, tentativas de acessar um objeto nulo, entre outros.

Essas exceções normalmente indicam problemas que devem ser resolvidos durante o desenvolvimento e teste do software, e não durante a execução normal.
Em resumo, a principal diferença entre IOException e RuntimeException está na verificação em tempo de compilação e na intenção do tratamento da exceção. Use IOException quando estiver lidando com problemas de entrada/saída que podem ocorrer durante a execução, e use RuntimeException para representar erros internos de lógica do programa que não precisam ser tratados explicitamente em todas as partes do código.