MessageFormat é uma classe em Java que faz parte do pacote java.text e é usada para formatar mensagens de texto, substituindo argumentos em uma string de formato. Essa classe é útil para criar mensagens de saída mais complexas e dinâmicas, nas quais você deseja incorporar valores variáveis em locais específicos de uma string.

A classe MessageFormat funciona usando um padrão de formatação que define onde os argumentos devem ser inseridos na string de formato. Os argumentos são passados como um array ou objeto, e a classe se encarrega de substituí-los nos locais apropriados da string de formato.

Aqui está um exemplo simples de uso do MessageFormat:

```java
import java.text.MessageFormat;

public class MessageFormatExample {
public static void main(String[] args) {
String pattern = "Olá, {0}! Hoje é {1}.";
String nome = "João";
String data = "17 de outubro";

        String mensagemFormatada = MessageFormat.format(pattern, nome, data);
        System.out.println(mensagemFormatada);
    }
}
```
Neste exemplo, o {0} e o {1} na string de formato são substituídos pelos valores correspondentes que são passados para o método MessageFormat.format.

