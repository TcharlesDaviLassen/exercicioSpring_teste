Você pode simplesmente usar o beanDataSource que você já criou para preencher o relatório.

```java
    var findByAllUsers = usuarioFlywayService.findByAll();
    JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(findByAllUsers);
    
    String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jasper";
    
    // Preencher os parâmetros do relatório, se houver
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("tituloRelatorio", "Relatório de Produtos");
    parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
    
    // Criar a fonte de dados JRBeanCollectionDataSource
    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(findByAllUsers);
    
    // Preencher o relatório com os dados
    JasperPrint jasperPrint = JasperFillManager.fillReport(reportTemplatePath, parameters, dataSource);
    
    // Visualizar o relatório
    JasperViewer viewer = new JasperViewer(jasperPrint, false);
    viewer.setVisible(true);

```

#
#

O código que você forneceu parece estar correto, mas eu farei algumas correções e explicarei melhor o que cada parte faz:

```java
@RequestMapping(value = "/jasperPDF", method = RequestMethod.GET)
public void jasperPDF(HttpServletResponse response) {
try {
// Obter os dados a serem incluídos no relatório
var findByAllUsers = usuarioFlywayService.findByAll();

        // Criar uma fonte de dados a partir dos dados dos usuários
        JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(findByAllUsers);

        // Definir parâmetros que podem ser usados no relatório
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tituloRelatorio", "Relatório de Produtos");
        parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");

        // Especificar o caminho do arquivo JRXML do relatório
        String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jrxml";

        // Compilar o relatório a partir do arquivo JRXML
        JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath);

        // Preencher o relatório com dados
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanDataSource);

        // Configurar a resposta HTTP para um PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=relatorio.pdf");

        // Exportar o relatório para um fluxo de saída
        OutputStream outStream = response.getOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));
        exporter.exportReport();

        // Exportar o relatório para um arquivo PDF no servidor
        String filePath = "/home/flexabus/Projects_Test/ExercicoSpring_testePDF_Docs/" + UUID.randomUUID().toString() + "_usuarios.pdf";
        JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);

        outStream.flush();
        outStream.close();

    } catch (CustomException e) {
        System.out.println("ERRO NO JASPERSOFT NA CLASSE DE UsuarioController: " + e.getStatus() + " " + e.getMessage());
    } catch (JRException e) {
        throw new RuntimeException("Erro no JasperReports: " + e.getMessage(), e);
    } catch (IOException e) {
        throw new RuntimeException("Erro de E/S: " + e.getMessage(), e);
    }
}
```
Aqui estão algumas das correções e melhorias feitas no código:

O método jasperPDF não recebe mais o parâmetro Model model, pois não parece ser necessário para essa ação. A resposta HTTP é manipulada diretamente.

Adicionada uma cláusula catch para capturar exceções de CustomException que podem ser lançadas no código. Essa é uma exceção personalizada que você está tratando.

As exceções JRException e IOException agora lançam exceções RuntimeException com mensagens de erro apropriadas.

O relatório é exportado para um arquivo PDF no servidor com um nome exclusivo baseado no UUID.

Comentários adicionados para explicar o que cada parte do código faz.

#
#

O erro java.awt.HeadlessException que você está enfrentando ocorre quando você tenta usar funcionalidades gráficas, como o JasperReports, em um ambiente sem uma interface gráfica disponível, o que é comum em aplicativos web ou servidores. Para corrigir o problema, você deve configurar o JasperReports para funcionar no modo "headless" e remover a parte da visualização, já que não é possível visualizar relatórios em um ambiente sem uma interface gráfica.

Aqui está o código corrigido:

```java
var findByAllUsers = usuarioFlywayService.findByAll();
JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(findByAllUsers);

Map<String, Object> parameters = new HashMap<>();
parameters.put("tituloRelatorio", "Relatório de Produtos");
parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");

String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jasper";

JasperPrint jasperPrint = JasperFillManager.fillReport(reportTemplatePath, parameters, beanDataSource);

// Salvar o relatório em um arquivo PDF no servidor
String filePath = "/home/flexabus/Downloads/ExercicoSpring_teste/PDF_Docs/" + UUID.randomUUID().toString() + "_usuarios.pdf";
JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
```

Neste código, removi a parte de visualização com o JasperViewer, uma vez que isso não é possível em um ambiente "headless". Apenas a parte de geração e exportação do relatório para um arquivo PDF foi mantida. Certifique-se de que o caminho do relatório JasperReports e o caminho de salvamento estejam configurados corretamente.

#
#

var findByAllUsers = usuarioFlywayService.findByAll();: Aqui, você está obtendo dados dos registros de usuários por meio do serviço usuarioFlywayService.

#### JRBeanCollectionDataSource
JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(findByAllUsers);: Você está convertendo a lista de dados dos usuários (findByAllUsers) em um JRBeanCollectionDataSource, que é usado como a fonte de dados para o relatório.

Map<String, Object> parameters = new HashMap<>();: Você está criando um mapa de parâmetros que podem ser usados no relatório. Por exemplo, você definiu "tituloRelatorio" e "subTituloRelatorio" como parâmetros.

String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/Waves.jrxml";: Aqui, você está especificando o caminho para o arquivo JRXML do relatório que será compilado. Certifique-se de que o caminho seja válido.

#### JasperReport 
JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath);: Você está compilando o relatório JRXML especificado no caminho.

#### JasperPrint 
JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanDataSource);: Aqui, você está preenchendo o relatório compilado com dados do beanDataSource e parâmetros. O resultado é um objeto JasperPrint.

Configuração da resposta HTTP para PDF e definição do cabeçalho da resposta para especificar o nome do arquivo PDF a ser exibido no navegador.

Exportação do relatório para um fluxo de saída no formato PDF. O PDF é gerado e enviado para o navegador para exibição.

JasperViewer viewer = new JasperViewer(jasperPrint, false);: Este trecho de código cria uma visualização do relatório no ambiente de desenvolvimento. Pode ser útil ao depurar e testar relatórios, mas não é geralmente usado em um ambiente de produção.

#
#

```java
@Controller
public class JasperController {

    @RequestMapping(value = "/jasperPDF", method = RequestMethod.GET)
    public void jasperPDF(HttpServletResponse response) {
        try {
            // Compilar o relatório JRXML
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    "caminho/para/seu/arquivo.jasper", // Substitua pelo caminho do seu arquivo JRXML compilado
                    new HashMap<>(),
                    new JREmptyDataSource()
            );

            // Configurar a resposta HTTP para um PDF
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=relatorio.pdf");

            // Exportar o relatório para um fluxo de saída
            OutputStream outStream = response.getOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));
            exporter.exportReport();

            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
Aqui estão as explicações linha por linha:

@Controller: Esta anotação marca a classe JasperController como um controlador Spring, indicando que ele irá gerenciar as solicitações HTTP.

@RequestMapping(value = "/jasperPDF", method = RequestMethod.GET): Esta anotação define que o método jasperPDF será acionado quando uma solicitação HTTP GET for feita para o caminho /jasperPDF.

public void jasperPDF(HttpServletResponse response) {: O método jasperPDF recebe um objeto HttpServletResponse como um parâmetro, que é usado para enviar a resposta HTTP.

#### JasperPrint 
JasperPrint jasperPrint = JasperFillManager.fillReport(...): Aqui, o método fillReport da classe JasperFillManager é chamado para compilar e preencher o relatório. Ele recebe três parâmetros:

O primeiro parâmetro é o caminho para o arquivo JasperReports (formato binário) que contém o relatório compilado.

O segundo parâmetro é um Map de parâmetros que podem ser passados para o relatório (neste caso, estamos usando um HashMap vazio).

O terceiro parâmetro é um JREmptyDataSource, que é um fornecedor de dados vazio.
response.setContentType("application/pdf"): Isso define o tipo de conteúdo da resposta HTTP como "application/pdf", indicando que a resposta será um documento PDF.

response.setHeader("Content-Disposition", "inline; filename=relatorio.pdf"): Isso configura o cabeçalho da resposta HTTP para definir o nome do arquivo e como o navegador deve manipulá-lo. Neste caso, "inline" indica que o navegador deve exibir o PDF no navegador, e "filename=relatorio.pdf" define o nome do arquivo.

#### OutputStream
OutputStream outStream = response.getOutputStream(): Isso obtém um fluxo de saída a partir do objeto response, que será usado para enviar o PDF gerado como parte da resposta.

#### JRPdfExporter
JRPdfExporter exporter = new JRPdfExporter(): Isso cria uma instância do JRPdfExporter, que é usado para exportar o relatório no formato PDF.
JRPdfExporter
exporter.setExporterInput(new SimpleExporterInput(jasperPrint)): Aqui, definimos o relatório a ser exportado como entrada para o exportador.

exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream)): Configuramos o fluxo de saída para o exportador, que enviará o PDF para o outStream.

exporter.exportReport(): Isso efetivamente exporta o relatório para o fluxo de saída, criando o PDF.

outStream.flush(): Isso limpa qualquer buffer de saída pendente.

outStream.close(): Isso fecha o fluxo de saída.

catch (Exception e) { e.printStackTrace(); }: Captura exceções que podem ocorrer durante o processo e imprime uma pilha de exceções se ocorrerem problemas.

#
#
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RelatorioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/relatorio")
    public void gerarRelatorio(OutputStream outputStream) throws Exception {
        List<Usuario> usuarios = usuarioService.listarUsuarios();

        // Carregar o arquivo do modelo do relatório (arquivo .jrxml)
        File jasperFile = new ClassPathResource("relatorio_usuarios.jasper").getFile();

        // Compilar o modelo do relatório para um arquivo binário .jasper
        File compiledReportFile = new File(jasperFile.getParentFile(), "relatorio_usuarios_compiled.jasper");
        if (!compiledReportFile.exists()) {
            JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream(jasperFile));
            JRSaver.saveObject(jasperReport, compiledReportFile);
        } else {
            jasperFile = compiledReportFile;
        }

        // Configurar os parâmetros do relatório (se houver)
        Map<String, Object> parametros = new HashMap<>();

        // Configurar o DataSource com a lista de usuários
        JRDataSource dataSource = new JRBeanCollectionDataSource(usuarios);

        // Gerar o relatório
        JasperPrint print = JasperFillManager.fillReport(jasperFile.getAbsolutePath(), parametros, dataSource);

        // Exportar o relatório para o formato desejado (PDF, neste caso)
        JasperExportManager.exportReportToPdfStream(print, outputStream);
    }
}
```

#
#

Aqui está um exemplo completo e funcional de como criar um relatório JasperSoft que recebe uma lista de objetos UsuarioFlyway como parâmetro e exibe os dados no relatório. Você precisará incluir a biblioteca Jackson (JSON) em seu projeto para trabalhar com JSON. Certifique-se de que o arquivo .jrxml está configurado de acordo com a estrutura da entidade UsuarioFlyway.

Primeiro, você precisa configurar a classe JsonDataSource para decodificar o JSON no relatório:

```java
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperReport;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonDataSource implements JRDataSource {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<Map<String, Object>> data;
    private int index = -1;

    public JsonDataSource(String jsonData) throws IOException {
        this.data = objectMapper.readValue(jsonData, List.class);
    }

    @Override
    public boolean next() throws JRException {
        index++;
        return index < data.size();
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        Map<String, Object> map = data.get(index);
        return map.get(jrField.getName());
    }
}
```

Aqui está o código de exemplo para criar o relatório com base nos parâmetros:

```java
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.JasperExportManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;

public class RelatorioUsuarioFlyway {

    public static void main(String[] args) {
        try {
            // Carregue seus dados da entidade UsuarioFlyway em uma lista
            List<UsuarioFlyway> usuarios = carregarDados();

            // Converta a lista de objetos em JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonUsuarios = objectMapper.writeValueAsString(usuarios);

            // Compile o arquivo .jrxml para um arquivo .jasper
            JasperCompileManager.compileReportToFile("caminho/para/seu/relatorio.jrxml");

            // Carregue o relatório compilado
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("caminho/para/seu/relatorio.jasper");

            // Passe o JSON como um parâmetro para o relatório
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JsonDataSource(jsonUsuarios));

            // Exporte o relatório para o formato desejado (PDF, HTML, etc.)
            // Exemplo de exportação para PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, "caminho/para/seu/relatorio.pdf");
        } catch (JRException | IOException e) {
            e.printStackTrace();
        }
    }

    private static List<UsuarioFlyway> carregarDados() {
        // Implemente a lógica para carregar os dados da entidade UsuarioFlyway
        // e retorne uma lista de objetos UsuarioFlyway
        // Exemplo:
        List<UsuarioFlyway> usuarios = new ArrayList<>();
        // Preencha a lista com seus dados
        return usuarios;
    }
}
```

#
#

preencher um relatório JasperReports com dados de uma lista de objetos ProdutoTeste. Para fazer isso, você precisa modificar o código da seguinte maneira:

Certifique-se de ter a lista findByAllUsers que contém objetos ProdutoTeste.

Crie uma lista de Map<String, Object> para armazenar os parâmetros para cada objeto ProdutoTeste. Em seguida, adicione cada mapa a uma lista.

Preencha o relatório com os dados usando a lista de mapas.

Aqui está o código modificado:

```java
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

// ...

// Suponha que você tenha uma lista de objetos ProdutoTeste em findByAllUsers

List<Map<String, Object>> listaParametros = new ArrayList<>();

for (ProdutoTeste produto : findByAllUsers) {
Map<String, Object> parametros = new HashMap<>();
parametros.put("tituloRelatorio", "Relatório de Produtos");
parametros.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
parametros.put("nome", produto.getNome());
parametros.put("preco", produto.getPreco());
parametros.put("tipoProdutoType", produto.getTipoProdutoType().getDescription());

    listaParametros.add(parametros);
}

// Crie um JRBeanCollectionDataSource com a lista de mapas
JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaParametros);

// Preencha o relatório com os dados
JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
```

Certifique-se de que a classe ProdutoTeste tenha um método getDescription() na enumeração TipoProdutoType para obter a descrição apropriada. Você pode adaptar o código de acordo com a estrutura do seu relatório e os dados específicos que deseja incluir.

#
#

Para escrever um arquivo JSON em Java, você pode usar a biblioteca Gson, que é uma biblioteca popular para trabalhar com JSON em Java. Primeiro, certifique-se de que você tenha a biblioteca Gson adicionada ao seu projeto. Você pode fazer isso adicionando a seguinte dependência ao seu arquivo pom.xml (se você estiver usando o Maven):

```xml
<dependency>
<groupId>com.google.code.gson</groupId>
<artifactId>gson</artifactId>
<version>2.8.8</version> <!-- Use a versão mais recente, se disponível -->
</dependency>
```
Aqui está um exemplo de como escrever um arquivo JSON em Java usando a biblioteca Gson:

```java
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class WriteJSONFile {
public static void main(String[] args) {
// Crie um objeto que você deseja serializar em JSON
MyObject myObject = new MyObject("Hello, World!", 42);

        // Crie um objeto Gson
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter("output.json")) {
            // Converte o objeto em JSON e escreve no arquivo
            gson.toJson(myObject, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Defina uma classe simples para representar os dados que deseja escrever em JSON
class MyObject {
private String message;
private int value;

    public MyObject(String message, int value) {
        this.message = message;
        this.value = value;
    }
}
```

Neste exemplo, criamos uma classe chamada MyObject para representar os dados que queremos escrever em JSON. Usamos a biblioteca Gson para serializar o objeto em JSON e, em seguida, escrevemos o JSON no arquivo "output.json".

Lembre-se de ajustar a classe MyObject e os dados conforme necessário para representar os dados que deseja escrever em seu arquivo JSON. Certifique-se de tratar exceções adequadamente, como a exceção IOException ao lidar com arquivos.

#
#

Para gerar um relatório usando o JasperReports no Spring Boot com o código fornecido, siga os passos a seguir:

Certifique-se de que você tem as dependências necessárias no arquivo pom.xml do seu projeto Spring Boot. Adicione as dependências do JasperReports e do JasperReports Spring Integration:

```xml
<dependency>
<groupId>net.sf.jasperreports</groupId>
<artifactId>jasperreports</artifactId>
<version>6.17.0</version> <!-- Use a versão mais recente disponível -->
</dependency>
<dependency>
<groupId>net.sf.jasperreports</groupId>
<artifactId>jasperreports-spring</artifactId>
<version>2.11.0</version> <!-- Use a versão mais recente disponível -->
</dependency>
```

Crie um endpoint no seu controlador Spring Boot para gerar o relatório. Por exemplo:

```java
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperExportManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RelatorioController {

    private final TipoProdutoService tipoProdutoService;

    public RelatorioController(TipoProdutoService tipoProdutoService) {
        this.tipoProdutoService = tipoProdutoService;
    }

    @GetMapping("/gerarRelatorio")
    public void gerarRelatorio() {
        try {
            List<ProdutoTeste> listaDeProdutos = tipoProdutoService.findAllTeste();
            
            String reportTemplatePath = "/home/tcharles/JaspersoftWorkspace/MyReports/tipoProduto.jrxml";
            
            // Carregar e compilar o JRXML
            JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath);
            
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("tituloRelatorio", "Relatório de Produtos");
            parametros.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
            
            // Passe a lista de dados como parâmetro
            parametros.put("dadosProdutos", listaDeProdutos);

            // Crie o relatório
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(listaDeProdutos));

            // Exporte o relatório para o formato desejado (PDF, por exemplo)
            String outputFile = "/caminho/para/salvar/o/relatorio.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);
        } catch (JRException e) {
            // Lide com exceções do JasperReports aqui
            e.printStackTrace();
        }
    }
}
```

Neste exemplo, criamos um endpoint gerarRelatorio que gera o relatório em PDF. Você pode personalizar o caminho do arquivo de saída e o formato de saída conforme necessário.


#
#

A escolha de passar um enum no JasperSoft (JasperReports) por parâmetro ou por função depende das necessidades e da estrutura do seu relatório. Ambas as abordagens podem ser usadas com sucesso, e a escolha depende do contexto e dos requisitos específicos do seu projeto. Aqui estão algumas considerações para ajudá-lo a decidir:

### 1. Passagem por Parâmetro:

#### Vantagens:

Simplicidade: A passagem por parâmetro é direta e fácil de implementar.

Flexibilidade: Você pode criar vários relatórios que usam o mesmo parâmetro de enumeração com valores diferentes.

Reutilização: É fácil de reutilizar em vários relatórios.

#### Desvantagens:

Limitação: A passagem por parâmetro pode ser limitada quando você precisa executar cálculos complexos ou lógica personalizada no relatório com base no enum.

### 2. Passagem por Função:

#### Vantagens:

Maior Flexibilidade: Com funções, você pode realizar cálculos e lógica personalizada no relatório com base no enum.

Controle: As funções permitem maior controle sobre como o enum é processado e exibido no relatório.

#### Desvantagens:

Complexidade: Implementar funções pode ser mais complexo do que passar parâmetros.

Reutilização: Pode ser menos reutilizável em diferentes relatórios sem recriar a mesma lógica de função.

Qual escolher depende do que você precisa fazer com o enum no relatório. Se você simplesmente deseja exibir os 
valores do enum, a passagem por parâmetro é a abordagem mais direta. No entanto, se você precisa executar lógica específica no relatório com base no enum (por exemplo, formatação especial, cálculos ou traduções), usar funções pode ser mais apropriado.

Em resumo, se a necessidade for simples, passe o enum como um parâmetro. Se for complexa e exigir manipulação personalizada dos valores do enum, considere criar funções personalizadas. Em muitos casos, você pode até usar ambas as abordagens, dependendo dos requisitos específicos do relatório.

#
#

exemplo de aplicação Spring Boot que gera um relatório usando o JasperReports com base nos dados do enum UsuarioEnumType a partir de uma lista de valores "N" e "E". Neste exemplo, usaremos uma lista de valores simulados como entrada e mapearemos esses valores para a enumeração UsuarioEnumType, para então incluí-los em um relatório.

1. Defina a classe UsuarioEnumType

```java
public enum UsuarioEnumType {
N("Normal"),
E("Especial");

    private final String descricao;

    UsuarioEnumType(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
```

2. Crie um controlador Spring Boot

```java
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EnumReportController {

    @GetMapping("/generateEnumReport")
    public JasperPrint generateEnumReport() throws JRException {
        // Simulando uma lista de valores "N" e "E"
        List<String> dados = new ArrayList<>();
        dados.add("N");
        dados.add("E");
        dados.add("E");
        dados.add("N");

        // Mapear valores para a enumeração UsuarioEnumType
        List<UsuarioEnumType> enumList = new ArrayList<>();
        for (String dado : dados) {
            if ("N".equals(dado)) {
                enumList.add(UsuarioEnumType.N);
            } else if ("E".equals(dado)) {
                enumList.add(UsuarioEnumType.E);
            }
        }

        // Carregar o arquivo JRXML
        ClassPathResource jrxmlFile = new ClassPathResource("path/to/your/report.jrxml");
        InputStream inputStream = jrxmlFile.getInputStream();

        // Compilar o arquivo JRXML em um JasperReport
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        // Criar um mapa de parâmetros
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tituloRelatorio", "Relatório de Produtos");
        parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho Spring teste");
        parameters.put("contextImage", "path/to/your/image.png");
        parameters.put("usuarioEnumList", enumList); // Passar a lista de enumeração

        // Gerar o relatório
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        return jasperPrint;
    }
}
```

#
#

preencher uma lista de nomes de enumeração a partir de uma lista de dados, onde os valores da lista de dados correspondem ao enum. Nesse caso, você pode fazer uma correspondência direta entre os valores da lista de dados e os valores do enum. Aqui está um exemplo de como fazer isso:

Primeiro, você precisa ter uma lista de dados que corresponda aos valores do enum. Suponhamos que você tenha uma lista de strings onde "n" corresponde a "Normal" e "m" corresponde a "Especial". Então, você pode fazer o seguinte:

```java
List<String> dados = Arrays.asList("n", "m");
List<String> enumNames = new ArrayList<>();

for (String dado : dados) {
    for (UsuarioEnumType enumValue : UsuarioEnumType.values()) {
        if (dado.equalsIgnoreCase(enumValue.name().substring(0, 1))) {
            enumNames.add(enumValue.name());
        }
    }
}
```

Neste exemplo, percorremos a lista de dados e, para cada dado, percorremos os valores do enum UsuarioEnumType para encontrar uma correspondência com base na primeira letra do nome do enum (por exemplo, "n" corresponde a "Normal" e "m" corresponde a "Especial"). Em seguida, adicionamos o nome do enum correspondente à lista enumNames.

Depois, você pode passar a lista enumNames como um parâmetro para o seu relatório JasperReports, conforme mencionado anteriormente.

#
#

```java
List<UsuarioEnumType> usuarioEnumTypeList = findByAllUsers.stream()
.map(UsuarioFlyway::getUsuarioEnumTypeEnum)
.collect(Collectors.toList());

Map<String, Object> parameters = new HashMap<>();
parameters.put("tituloRelatorio", "Relatório de Produtos");
parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
parameters.put("contextImage", contextImage);
parameters.put("usuarioEnumTypeList", usuarioEnumTypeList);
```

No seu relatório Jasper, use a variável $P{usuarioEnumTypeList} onde deseja exibir os valores da lista. Você pode usar uma tabela ou uma lista de elementos de texto para exibir esses valores.


#
#

Para transferir o valor do enum para o relatório do JasperReports, você precisa criar um parâmetro no seu arquivo JRXML e, em seguida, passar o valor do enum para esse parâmetro quando gerar o relatório no controlador Spring Boot. Aqui está um exemplo de como fazer isso:

Primeiro, no seu arquivo JRXML, crie um parâmetro para receber o valor do enum:

```xml
<parameter name="usuarioEnumValue" class="java.lang.String" isForPrompting="false"/>
```

Depois, no controlador Spring Boot, você passa o valor do enum como um parâmetro ao gerar o relatório:

```java
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
public class EnumReportController {

    @GetMapping("/enumReport")
    public JasperPrint generateEnumReport() throws JRException {
        // Carregar o arquivo JRXML
        ClassPathResource jrxmlFile = new ClassPathResource("path/to/your/report.jrxml");
        InputStream inputStream = jrxmlFile.getInputStream();

        // Compilar o arquivo JRXML em um JasperReport
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        // Suponha que você queira obter o valor "Especial" do enum com ID 1
        int idToFind = 1;
        String enumValue = null;
        for (UsuarioEnumType enumItem : UsuarioEnumType.values()) {
            if (enumItem.getId() == idToFind) {
                enumValue = enumItem.getDescricao();
                break;
            }
        }

        // Crie um mapa de parâmetros
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tituloRelatorio", "Relatório de Produtos");
        parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho Spring teste");
        parameters.put("contextImage", "path/to/your/image.png"); // Substitua pelo caminho da sua imagem
        parameters.put("usuarioEnumValue", enumValue); // Passar o valor do enum como parâmetro

        // Gerar o relatório
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        return jasperPrint;
    }
}
```

Neste exemplo, usuarioEnumValue é o parâmetro no arquivo JRXML que recebe o valor do enum. No controlador Spring Boot, você localiza o valor do enum com base no ID desejado e o passa para o parâmetro do relatório. Certifique-se de que o campo correspondente no arquivo JRXML esteja configurado para usar o parâmetro usuarioEnumValue.

#
#

é necessário desse treixo de códogo para ser exibido no navegador o pdf

```java
OutputStream outStream = response.getOutputStream();
JRPdfExporter exporter = new JRPdfExporter();
// Aqui, definimos o relatório a ser exportado como entrada para o exportador.
exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
// Configuramos o fluxo de saída para o exportador, que enviará o PDF para o outStream
exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));
// Isso efetivamente exporta o relatório para o fluxo de saída, criando o PDF.
exporter.exportReport();
```

Sim, o trecho de código que você forneceu é necessário para exibir o PDF gerado no navegador. Ele configura a exportação do relatório JasperReports para um fluxo de saída e, em seguida, envia o PDF resultante para o navegador como uma resposta HTTP. Aqui está um exemplo de como você pode integrar esse trecho de código em um controlador Spring Boot para enviar o PDF gerado como resposta:

```java
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
public class JasperReportController {

    @GetMapping("/generateReport")
    public void generateReport(HttpServletResponse response) throws JRException {
        try {
            // Carregar o arquivo JRXML
            ClassPathResource jrxmlFile = new ClassPathResource("path/to/your/report.jrxml");
            InputStream inputStream = jrxmlFile.getInputStream();

            // Compilar o arquivo JRXML em um JasperReport
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            // Criar um mapa de parâmetros (se necessário)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tituloRelatorio", "Relatório de Usuários");
            parameters.put("subTituloRelatorio", "Relatório de Usuários do Spring Boot");
            parameters.put("contextImage", "path/to/your/image.png");

            // Gerar o relatório
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            // Configurar a resposta HTTP
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=relatorio.pdf");

            // Obter o fluxo de saída da resposta
            OutputStream outStream = response.getOutputStream();

            // Exportar o relatório como PDF
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));

            SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
            reportConfig.setSizePageToContent(true);
            reportConfig.setForceLineBreakPolicy(false);

            SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
            exportConfig.setMetadataAuthor("Seu Nome");
            exportConfig.setEncrypted(false);

            exporter.setConfiguration(reportConfig);
            exporter.setConfiguration(exportConfig);

            exporter.exportReport();

            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

Neste exemplo, a rota /generateReport gera o relatório e o exporta como um documento PDF para o navegador como resposta. Certifique-se de ajustar o caminho para o arquivo JRXML, o layout do relatório e outras configurações conforme necessário.


#
#

//        // Obter os dados a serem incluídos no relatório
//        var findByAllUsers = usuarioFlywayService.findByAll();
//
//        // Criar uma fonte de dados a partir dos dados dos usuários
//        JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(findByAllUsers);
//
//        // Definir parâmetros que podem ser usados no relatório
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("tituloRelatorio", "Relatório de Produtos");
//        parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
//
//        // Especificar o caminho do arquivo JRXML do relatório
//        String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jrxml";
//
//        // Compilar o relatório a partir do arquivo JRXML
//        JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath);
//
//        // Preencher o relatório com dados
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanDataSource);
//
//        // Configurar a resposta HTTP para um PDF
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "inline; filename=relatorio.pdf");
//
//        // Exportar o relatório para um fluxo de saída
//        OutputStream outStream = response.getOutputStream();
//        JRPdfExporter exporter = new JRPdfExporter();
//        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));
//        exporter.exportReport();
//
//        // Exportar o relatório para um arquivo PDF no servidor
//        String filePath = "/home/flexabus/Downloads/ExercicoSpring_teste/PDF_Docs/" + UUID.randomUUID().toString() + "_usuarios.pdf";
//        JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
//
//        outStream.flush();
//        outStream.close();


//            var findByAllUsers = usuarioFlywayService.findByAll();
//            //// Converter a lista de findByAllUsers em um JRBeanCollectionDataSource
//            //// JRBeanCollectionDataSource: Esta é uma classe do JasperReports que permite criar uma fonte de dados a partir de uma coleção de objetos Java (no seu caso, objetos representando registros de usuários).
//            JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(findByAllUsers);
//
//            Map<String, Object> parameters = new HashMap<>();
//            parameters.put("tituloRelatorio", "Relatório de Produtos");
//            parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
//
//            //// para compilar o relatório JasperReports. O método compileReport é usado para compilar o arquivo JRXML no caminho especificado em reportTemplatePath. O resultado compilado é armazenado na variável jasperReport.
//            String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jasper";
////            JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath);
//
//            //// Compilar o relatório JRXML
//            JasperPrint jasperPrint = JasperFillManager.fillReport(reportTemplatePath, parameters, beanDataSource );
//            //   new JREmptyDataSource() //  é um fornecedor de dados vazio.
//
//            // Configurar a resposta HTTP para um PDF
//            response.setContentType("application/pdf");
//            // Isso configura o cabeçalho da resposta HTTP para definir o nome do arquivo e como o navegador deve manipulá-lo.
//            // Neste caso, "inline" indica que o navegador deve exibir o PDF no navegador, e "filename=relatorio.pdf" define o nome do arquivo.
//            response.setHeader("Content-Disposition", "inline; filename=relatorio.pdf");
//
//            // Exportar o relatório para um fluxo de saída
//            // obtém um fluxo de saída a partir do objeto
//            // usado para exportar o relatório no formato PDF.
//            OutputStream outStream = response.getOutputStream();
//            JRPdfExporter exporter = new JRPdfExporter();
//
//            // Aqui, definimos o relatório a ser exportado como entrada para o exportador.
//            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//
//            // Configuramos o fluxo de saída para o exportador, que enviará o PDF para o outStream
//            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));
//
//            // Isso efetivamente exporta o relatório para o fluxo de saída, criando o PDF.
//            exporter.exportReport();
//
//            //// Visualizar o relatório
//            JasperViewer viewer = new JasperViewer(jasperPrint, false);
//            viewer.setVisible(true);
//
//            String filePath = "/home/flexabus/Downloads/ExercicoSpring_teste/PDF_Docs/" + UUID.randomUUID().toString() + "_usuarios.pdf";
//            JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
//
//            outStream.flush(); //// Isso limpa qualquer buffer de saída pendente.
//            outStream.close(); ////  Isso fecha o fluxo de saída.


//            var findByAllUsers = usuarioFlywayService.findByAll();
//            JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(findByAllUsers);

//            Map<String, Object> parameters = new HashMap<>();
//            parameters.put("tituloRelatorio", "Relatório de Produtos");
//            parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
//
//            String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jasper";
//
//            JasperPrint jasperPrint = JasperFillManager.fillReport(reportTemplatePath, parameters, beanDataSource);
//
//            // Salvar o relatório em um arquivo PDF no servidor
//            String filePath = "/home/flexabus/Downloads/ExercicoSpring_teste/PDF_Docs/" + UUID.randomUUID().toString() + "_usuarios.pdf";
//            JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
//
//            // Visualizar o relatório com o JasperViewer
//            JasperViewer viewer = new JasperViewer(jasperPrint, false);
//            // Configure o fechamento da janela para encerrar o aplicativo
//            viewer.setDefaultCloseOperation(JasperViewer.DISPOSE_ON_CLOSE);
//            viewer.setVisible(true);


//    var findByAllUsers = usuarioFlywayService.findByAll();
//    // Criar a fonte de dados JRBeanCollectionDataSource
//    // JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(findByAllUsers);
//
//    UsuarioFlyway userFly = new UsuarioFlyway();
//    //    for (UsuarioFlyway findByAllUser : findByAllUsers) {
//    //        userFly.setUsuarioEnumTypeEnum(findByAllUser.getUsuarioEnumTypeEnum());
//    //        userFly.getUsuarioEnumAsString(userFly.getUsuarioEnumTypeEnum());
//    //
//    //    }
//
//    String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jrxml";
//    JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath);
//
//    String contextImage = "/home/flexabus/Imagens/Capturas de tela/Captura de tela de 2023-10-09 08-44-11.png";
//
//    // Create a list of Enum values
//    //   List<UsuarioEnumType> enumList = Arrays.asList(UsuarioEnumType.values());
//    // Create a list of Strings from the enum values
//    //    List<String> dados = Arrays.asList("N", "E");
//    // List<UsuarioEnumType> enumNames = new ArrayList<>();
//
//    //  String enumNames = " ";
//    //            for (UsuarioEnumType item : UsuarioEnumType.values()) {
//    //                    enumNames.add(item.name());
//    //            }
//
//    //  for (UsuarioEnumType dado : UsuarioEnumType.values()) {
//    //        if ("N".equals(dado)) {
//    //            enumNames.add(UsuarioEnumType.N);
//    //        } else if ("E".equals(dado)) {
//    //            enumNames.add(UsuarioEnumType.E);
//    //        }
//    //    }
//    //  }
//
//    //  Preencher os parâmetros do relatório, se houver
//    Map<String, Object> parameters = new HashMap<>();
//    UsuarioFlyway usuarioFlyway = new UsuarioFlyway();
//
//    //            for (int i = 0; i < findByAllUsers.size(); i++) {
//    //                parameters.put("usuario_enum_type_enum", findByAllUsers.get(i).getUsuarioEnumTypeEnum().name());
//    //            };
//
//    parameters.put("tituloRelatorio", "Relatório de Produtos");
//    parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
//    parameters.put("contextImage", contextImage);
//
//    List<Map<String, Object>> lisMapParamiters = new ArrayList<>();
//    for (UsuarioFlyway usuario : findByAllUsers) {
//        Map<String, Object> parametersMap = new HashMap<>();
//
//        parametersMap.put("nome", usuario.getNome());
//        parametersMap.put("numero", usuario.getNumero());
//        parametersMap.put("email", usuario.getEmail());
//        parametersMap.put("data", usuario.getData());
//
//        if(usuario.getUsuarioEnumTypeEnum() != null) {
//            parametersMap.put("usuarioenumtypeenum", usuario.getUsuarioEnumTypeEnum().name());
//        }
//
//        lisMapParamiters.add(parametersMap);
//    }
//
//    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lisMapParamiters);
//
//    // Adicione a lista de enum ao parâmetro
//    // parameters.put(, usuarioFlyway.getUsuarioEnumTypeEnum().name());
//
//    // Preencher o relatório com os dados
//    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
//
//    // Configurar a rparameters =  size = 12esposta HTTP para um PDF
//    // Isso configura o cabeçalho da resposta HTTP para definir o nome do arquivo e como o navegador deve manipulá-lo.
//    // Neste caso, "inline" indica que o navegador deve exibir o PDF no navegador,
//    // e "filename=relatorio.pdf" define o nome do arquivo.
//    response.setContentType("application/pdf");
//    response.setHeader("Content-Disposition", "inline; filename=relatorio.pdf");
//
//    OutputStream outStream = response.getOutputStream();
//    JRPdfExporter exporter = new JRPdfExporter();
//    // Aqui, definimos o relatório a ser exportado como entrada para o exportador.
//    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//    // Configuramos o fluxo de saída para o exportador, que enviará o PDF para o outStream
//    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));
//    // Isso efetivamente exporta o relatório para o fluxo de saída, criando o PDF.
//    exporter.exportReport();
//
//    String filePath = "/home/flexabus/Downloads/ExercicoSpring_teste/PDF_Docs/" + UUID.randomUUID().toString() + "_usuarios.pdf";
//    JasperExportManager.exportReportToPdfFile(jasperPrint, filePath );
//
//    // Visualizar o relatório
//    //    JasperViewer viewer = new JasperViewer(jasperPrint, false);
//    //    viewer.setVisible(true);

#
#

Para adicionar um cabeçalho e um rodapé em todas as páginas do relatório gerado pelo JasperReports, você pode usar os elementos <pageHeader> e <pageFooter> no arquivo XML do relatório. Aqui está como você pode adicionar um cabeçalho e um rodapé ao seu relatório:

Cabeçalho:

```xml
<pageHeader>
    <band height="50">
    <!-- Adicione os elementos do cabeçalho aqui -->
        <staticText>
            <reportElement x="0" y="0" width="100" height="20" uuid="some-uuid">
            <!-- Defina as propriedades do elemento, como fonte, tamanho, etc. -->
            </reportElement>
            <textElement>
            <!-- Defina a formatação do texto, como alinhamento, cor, etc. -->
            </textElement>
            <text><![CDATA[Seu cabeçalho aqui]]></text>
        </staticText>
    </band>
</pageHeader>
```

Rodapé:

```xml

<pageFooter>
    <band height="50">
    <!-- Adicione os elementos do rodapé aqui -->
        <staticText>
            <reportElement x="0" y="0" width="100" height="20" uuid="another-uuid">
            <!-- Defina as propriedades do elemento, como fonte, tamanho, etc. -->
            </reportElement>
            <textElement>
            <!-- Defina a formatação do texto, como alinhamento, cor, etc. -->
            </textElement>
            <text><![CDATA[Seu rodapé aqui]]></text>
        </staticText>
    </band>
</pageFooter>
```
Lembre-se de personalizar os elementos de cabeçalho e rodapé de acordo com suas necessidades, como definir as fontes, tamanhos, alinhamentos e cores desejados. Certifique-se de adicionar os elementos necessários dentro das tags <pageHeader> e <pageFooter>. Isso garantirá que o cabeçalho e o rodapé sejam exibidos em todas as páginas do relatório gerado.



