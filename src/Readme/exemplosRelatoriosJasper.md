em formato PDF com base em um arquivo de modelo JRXML e dados de uma lista de objetos. No entanto, algumas pequenas correções são necessárias:

É mais apropriado usar o método JasperExportManager.exportReportToPdfFile em vez de JasperExportManager.exportReportToe para exportar o relatório em formato PDF.

Certifique-se de adicionar tratamento de exceção para capturar qualquer exceção que possa ocorrer durante o processo.

Aqui está o código corrigido:

java
Copy code
var findByAllUsers = usuarioFlywayService.findByAll();

String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jrxml";
JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath);

String contextImage = "/home/flexabus/Imagens/Capturas de tela/Captura de tela de 2023-10-09 08-44-11.png";

Map<String, Object> parameters = new HashMap<>();
parameters.put("tituloRelatorio", "Relatório de Produtos");
parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
parameters.put("contextImage", contextImage);

List<Map<String, Object>> lisMapParamiters = new ArrayList<>();
for (UsuarioFlyway usuario : findByAllUsers) {
Map<String, Object> parametersMap = new HashMap<>();
parametersMap.put("nome", usuario.getNome());
parametersMap.put("numero", usuario.getNumero());
parametersMap.put("email", usuario.getEmail());
parametersMap.put("data", usuario.getData());
parametersMap.put("usuarioenumtypeenum", usuario.getUsuarioEnumTypeEnum().name());
lisMapParamiters.add(parametersMap);
}

JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lisMapParamiters);

JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

response.setContentType("application/pdf");
response.setHeader("Content-Disposition", "inline; filename=relatorio.pdf");

OutputStream outStream = response.getOutputStream();
JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

String filePath = "/home/flexabus/Downloads/ExercicoSpring_teste/PDF_Docs/" + UUID.randomUUID().toString() + "_usuarios.pdf";
JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);

outStream.flush();
outStream.close();
Este código irá compilar o relatório a partir do modelo JRXML, preenchê-lo com os dados da lista de objetos UsuarioFlyway e exportá-lo em formato PDF. Certifique-se de ajustar os caminhos dos arquivos e os detalhes do seu relatório conforme necessário. Além disso, esteja ciente de que é importante lidar com exceções apropriadamente em um ambiente de produção.




User
agora para exel
ChatGPT
Para exportar seu relatório em formato Excel, você pode usar a biblioteca JasperReports junto com a biblioteca JRXlsxExporter. Aqui está um exemplo de como exportar seu relatório para o formato Excel:

java
Copy code
var findByAllUsers = usuarioFlywayService.findByAll();

String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jrxml";
JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath);

String contextImage = "/home/flexabus/Imagens/Capturas de tela/Captura de tela de 2023-10-09 08-44-11.png";

Map<String, Object> parameters = new HashMap<>();
parameters.put("tituloRelatorio", "Relatório de Produtos");
parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
parameters.put("contextImage", contextImage);

List<Map<String, Object>> lisMapParamiters = new ArrayList<>();
for (UsuarioFlyway usuario : findByAllUsers) {
Map<String, Object> parametersMap = new HashMap<>();
parametersMap.put("nome", usuario.getNome());
parametersMap.put("numero", usuario.getNumero());
parametersMap.put("email", usuario.getEmail());
parametersMap.put("data", usuario.getData());
parametersMap.put("usuarioenumtypeenum", usuario.getUsuarioEnumTypeEnum().name());
lisMapParamiters.add(parametersMap);
}

JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lisMapParamiters);

JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
response.setHeader("Content-Disposition", "inline; filename=relatorio.xlsx");

OutputStream outStream = response.getOutputStream();
JRXlsxExporter exporter = new JRXlsxExporter();
exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));
exporter.exportReport();

String filePath = "/home/flexabus/Downloads/ExercicoSpring_teste/Excel_Docs/" + UUID.randomUUID().toString() + "_usuarios.xlsx";
JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);

outStream.flush();
outStream.close();
Neste exemplo, estamos utilizando o JRXlsxExporter para exportar o relatório em formato Excel (XLSX). Certifique-se de ajustar os caminhos dos arquivos e outros detalhes conforme necessário para o seu ambiente específico.

Lembre-se de adicionar as bibliotecas JasperReports e JRXlsxExporter ao seu projeto para que esse código funcione corretamente.




User
agora para csv
ChatGPT
Para exportar seu relatório para o formato CSV, você pode utilizar a biblioteca JasperReports em conjunto com o JRCsvExporter. Aqui está um exemplo de como exportar seu relatório para o formato CSV:

java
Copy code
var findByAllUsers = usuarioFlywayService.findByAll();

String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jrxml";
JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath);

String contextImage = "/home/flexabus/Imagens/Capturas de tela/Captura de tela de 2023-10-09 08-44-11.png";

Map<String, Object> parameters = new HashMap<>();
parameters.put("tituloRelatorio", "Relatório de Produtos");
parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
parameters.put("contextImage", contextImage);

List<Map<String, Object>> lisMapParamiters = new ArrayList<>();
for (UsuarioFlyway usuario : findByAllUsers) {
Map<String, Object> parametersMap = new HashMap<>();
parametersMap.put("nome", usuario.getNome());
parametersMap.put("numero", usuario.getNumero());
parametersMap.put("email", usuario.getEmail());
parametersMap.put("data", usuario.getData());
parametersMap.put("usuarioenumtypeenum", usuario.getUsuarioEnumTypeEnum().name());
lisMapParamiters.add(parametersMap);
}

JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lisMapParamiters);

JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

response.setContentType("text/csv");
response.setHeader("Content-Disposition", "inline; filename=relatorio.csv");

OutputStream outStream = response.getOutputStream();
JRCsvExporter exporter = new JRCsvExporter();
exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
exporter.setExporterOutput(new SimpleWriterExporterOutput(outStream));

exporter.exportReport();
outStream.flush();
outStream.close();
Neste exemplo, estamos utilizando o JRCsvExporter para exportar o relatório em formato CSV. Certifique-se de ajustar os caminhos dos arquivos e outros detalhes conforme necessário para o seu ambiente específico.

Lembre-se de adicionar as bibliotecas JasperReports e JRCsvExporter ao seu projeto para que esse código funcione corretamente.




User
agora para html
ChatGPT
Para exportar seu relatório para o formato HTML, você pode utilizar a biblioteca JasperReports junto com o JRHtmlExporter. Aqui está um exemplo de como exportar seu relatório para o formato HTML:

java
Copy code
var findByAllUsers = usuarioFlywayService.findByAll();

String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jrxml";
JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath);

String contextImage = "/home/flexabus/Imagens/Capturas de tela/Captura de tela de 2023-10-09 08-44-11.png";

Map<String, Object> parameters = new HashMap<>();
parameters.put("tituloRelatorio", "Relatório de Produtos");
parameters put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
parameters.put("contextImage", contextImage);

List<Map<String, Object>> lisMapParamiters = new ArrayList<>();
for (UsuarioFlyway usuario : findByAllUsers) {
Map<String, Object> parametersMap = new HashMap<>();
parametersMap.put("nome", usuario.getNome());
parametersMap.put("numero", usuario.getNumero());
parametersMap.put("email", usuario.getEmail());
parametersMap.put("data", usuario.getData());
parametersMap.put("usuarioenumtypeenum", usuario.getUsuarioEnumTypeEnum().name());
lisMapParamiters.add(parametersMap);
}

JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lisMapParamiters);

JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

response.setContentType("text/html");
response.setHeader("Content-Disposition", "inline; filename=relatorio.html");

OutputStream outStream = response.getOutputStream();
JRHtmlExporter exporter = new JRHtmlExporter();
exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
exporter.setExporterOutput(new SimpleHtmlExporterOutput(outStream));

exporter.exportReport();
outStream.flush();
outStream.close();
Neste exemplo, estamos utilizando o JRHtmlExporter para exportar o relatório em formato HTML. Certifique-se de ajustar os caminhos dos arquivos e outros detalhes conforme necessário para o seu ambiente específico.

Lembre-se de adicionar as bibliotecas JasperReports ao seu projeto para que esse código funcione corretamente. Além disso, você pode personalizar o estilo e o layout do relatório HTML, se necessário, de acordo com os requisitos do seu projeto.




User
não encontra o JRHtmlExporter
ChatGPT
Peço desculpas pela confusão anterior. De fato, a biblioteca JasperReports não possui um exportador nativo para HTML. Para exportar um relatório para HTML, geralmente é necessário gerar o relatório em formato PDF e, em seguida, convertê-lo em HTML.

Aqui está um exemplo de como você pode fazer isso usando a biblioteca Flying Saucer (também conhecida como iText Renderer) para converter um relatório PDF em HTML:

Certifique-se de adicionar as bibliotecas Flying Saucer (iText Renderer) ao seu projeto. Você pode baixá-las em https://github.com/flyingsaucerproject/flyingsaucer.

Utilize o código a seguir para gerar o relatório em PDF e convertê-lo em HTML:

java
Copy code
var findByAllUsers = usuarioFlywayService.findByAll();

String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jrxml";
JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath);

String contextImage = "/home/flexabus/Imagens/Capturas de tela/Captura de tela de 2023-10-09 08-44-11.png";

Map<String, Object> parameters = new HashMap<>();
parameters.put("tituloRelatorio", "Relatório de Produtos");
parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
parameters.put("contextImage", contextImage);

List<Map<String, Object>> lisMapParamiters = new ArrayList<>();
for (UsuarioFlyway usuario : findByAllUsers) {
Map<String, Object> parametersMap = new HashMap<>();
parametersMap.put("nome", usuario.getNome());
parametersMap.put("numero", usuario.getNumero());
parametersMap.put("email", usuario.getEmail());
parametersMap.put("data", usuario.getData());
parametersMap.put("usuarioenumtypeenum", usuario.getUsuarioEnumTypeEnum().name());
lisMapParamiters.add(parametersMap);
}

JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lisMapParamiters);

JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

// Exportar o relatório para PDF
byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

// Converter o PDF em HTML usando Flying Saucer (iText Renderer)
ByteArrayOutputStream htmlStream = new ByteArrayOutputStream();
ITextRenderer renderer = new ITextRenderer();
renderer.setDocumentFromString(new String(pdfBytes));
renderer.layout();
renderer.createPDF(htmlStream, true);

// Agora você tem o relatório em HTML no ByteArrayOutputStream "htmlStream"

response.setContentType("text/html");
response.setHeader("Content-Disposition", "inline; filename=relatorio.html");

OutputStream outStream = response.getOutputStream();
outStream.write(htmlStream.toByteArray());
outStream.flush();
outStream.close();
Certifique-se de adicionar as bibliotecas Flying Saucer (iText Renderer) ao seu projeto e ajustar os caminhos dos arquivos conforme necessário. Este código gera o relatório em PDF e, em seguida, o converte em HTML para exibição no navegador.




User
ha outra forma de gerar um relatorio em html como por exemplo usando exportReportToHtmlFile
ChatGPT
O JasperReports fornece a função exportReportToHtmlFile para exportar um relatório diretamente em um arquivo HTML. No entanto, essa função requer que você especifique um caminho de arquivo no sistema de arquivos onde o arquivo HTML será salvo. Se você deseja exibir o relatório HTML diretamente no navegador, pode ser mais conveniente gerar o relatório em PDF e, em seguida, convertê-lo em HTML, como demonstrado no exemplo anterior.

No entanto, se você ainda deseja usar a função exportReportToHtmlFile, aqui está como você pode usá-la:

java
Copy code
var findByAllUsers = usuarioFlywayService.findByAll();

String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jrxml";
JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath);

String contextImage = "/home/flexabus/Imagens/Capturas de tela/Captura de tela de 2023-10-09 08-44-11.png";

Map<String, Object> parameters = new HashMap<>();
parameters.put("tituloRelatorio", "Relatório de Produtos");
parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
parameters.put("contextImage", contextImage);

List<Map<String, Object>> lisMapParamiters = new ArrayList<>();
for (UsuarioFlyway usuario : findByAllUsers) {
Map<String, Object> parametersMap = new HashMap<>();
parametersMap.put("nome", usuario.getNome());
parametersMap.put("numero", usuario.getNumero());
parametersMap.put("email", usuario.getEmail());
parametersMap.put("data", usuario.getData());
parametersMap.put("usuarioenumtypeenum", usuario.getUsuarioEnumTypeEnum().name());
lisMapParamiters.add(parametersMap);
}

JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lisMapParamiters);

JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

// Exportar o relatório para HTML
JasperExportManager.exportReportToHtmlFile(jasperPrint, "caminho/para/salvar/relatorio.html");
Neste exemplo, o relatório será gerado em HTML e salvo no caminho especificado em "caminho/para/salvar/relatorio.html". Você pode, em seguida, servir este arquivo HTML no navegador, se desejar. Certifique-se de ajustar os caminhos de arquivo conforme necessário.




User
agora gere um relatorio em xml
ChatGPT
Para gerar um relatório em XML com o JasperReports, você pode usar a função exportReportToXmlFile. Aqui está um exemplo de como fazê-lo:

java
Copy code
var findByAllUsers = usuarioFlywayService.findByAll();

String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jrxml";
JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath);

String contextImage = "/home/flexabus/Imagens/Capturas de tela/Captura de tela de 2023-10-09 08-44-11.png";

Map<String, Object> parameters = new HashMap<>();
parameters.put("tituloRelatorio", "Relatório de Produtos");
parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
parameters.put("contextImage", contextImage);

List<Map<String, Object>> lisMapParamiters = new ArrayList<>();
for (UsuarioFlyway usuario : findByAllUsers) {
Map<String, Object> parametersMap = new HashMap<>();
parametersMap.put("nome", usuario.getNome());
parametersMap.put("numero", usuario.getNumero());
parametersMap.put("email", usuario.getEmail());
parametersMap.put("data", usuario.getData());
parametersMap.put("usuarioenumtypeenum", usuario.getUsuarioEnumTypeEnum().name());
lisMapParamiters.add(parametersMap);
}

JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lisMapParamiters);

JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

// Exportar o relatório para XML
JasperExportManager.exportReportToXmlFile(jasperPrint, "caminho/para/salvar/relatorio.xml");
Neste exemplo, o relatório será gerado em formato XML e salvo no caminho especificado em "caminho/para/salvar/relatorio.xml". Certifique-se de ajustar os caminhos de arquivo conforme necessário. Note que o formato XML gerado será específico do JasperReports e não um formato XML genérico.




User
seguindo esse exemplo var findByAllUsers = usuarioFlywayService.findByAll();

String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jrxml";
JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath);

String contextImage = "/home/flexabus/Imagens/Capturas de tela/Captura de tela de 2023-10-09 08-44-11.png";

Map<String, Object> parameters = new HashMap<>();
parameters.put("tituloRelatorio", "Relatório de Produtos");
parameters put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
parameters.put("contextImage", contextImage);

List<Map<String, Object>> lisMapParamiters = new ArrayList<>();
for (UsuarioFlyway usuario : findByAllUsers) {
Map<String, Object> parametersMap = new HashMap<>();
parametersMap.put("nome", usuario.getNome());
parametersMap.put("numero", usuario.getNumero());
parametersMap.put("email", usuario.getEmail());
parametersMap.put("data", usuario.getData());
parametersMap.put("usuarioenumtypeenum", usuario.getUsuarioEnumTypeEnum().name());
lisMapParamiters.add(parametersMap);
}

JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lisMapParamiters);

JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

response.setContentType("text/html");
response.setHeader("Content-Disposition", "inline; filename=relatorio.html");

OutputStream outStream = response.getOutputStream();
JRHtmlExporter exporter = new JRHtmlExporter();
exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
exporter.setExporterOutput(new SimpleHtmlExporterOutput(outStream));

exporter.exportReport();
outStream.flush();
outStream.close();
gere um relatorio em xml
ChatGPT
A geração de relatórios em XML com o JasperReports requer algumas alterações em relação ao exemplo anterior. O JasperReports não possui um exportador XML nativo, como tem para PDF, HTML e outros formatos. Portanto, para gerar um relatório em XML, você precisará criar seu próprio formato XML ou exportar os dados do relatório em um formato XML específico.

Aqui está um exemplo de como você pode exportar os dados de um relatório em formato XML usando a biblioteca JDOM:

java
Copy code
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

// Seu código para criar o relatório e o JasperPrint aqui...

// Criar um documento XML para armazenar os dados do relatório
Document xmlDocument = new Document();
Element rootElement = new Element("relatorio");
xmlDocument.setRootElement(rootElement);

for (UsuarioFlyway usuario : findByAllUsers) {
Element usuarioElement = new Element("usuario");
usuarioElement.addContent(new Element("nome").setText(usuario.getNome()));
usuarioElement.addContent(new Element("numero").setText(usuario.getNumero()));
usuarioElement.addContent(new Element("email").setText(usuario.getEmail()));
usuarioElement.addContent(new Element("data").setText(usuario.getData().toString()));
usuarioElement.addContent(new Element("usuarioEnumType").setText(usuario.getUsuarioEnumTypeEnum().name()));
rootElement.addContent(usuarioElement);
}

// Exportar o documento XML para o OutputStream
response.setContentType("application/xml");
response.setHeader("Content-Disposition", "inline; filename=relatorio.xml");

OutputStream outStream = response.getOutputStream();
XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
xmlOutputter.output(xmlDocument, outStream);

outStream.flush();
outStream.close();
Neste exemplo, estamos criando um documento XML personalizado com os dados do relatório e, em seguida, exportando esse documento para o navegador como um arquivo XML. Certifique-se de ajustar os detalhes do XML de acordo com os dados que você deseja incluir.




User
configure esse exemplo var findByAllUsers = usuarioFlywayService.findByAll();

            String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath); // Compila o relátorio .jrxml para um binário .jasper

            String contextImage = "/home/flexabus/Imagens/Capturas de tela/Captura de tela de 2023-10-09 08-44-11.png";

            //  Preencher os parâmetros do relatório, se houver
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tituloRelatorio", "Relatório de Produtos");
            parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
            parameters.put("contextImage", contextImage);

            List<Map<String, Object>> lisMapParamiters = new ArrayList<>();
            for (UsuarioFlyway usuario : findByAllUsers) {
                Map<String, Object> parametersMap = new HashMap<>();

                parametersMap.put("nome", usuario.getNome());
                parametersMap.put("numero", usuario.getNumero());
                parametersMap.put("email", usuario.getEmail());
                parametersMap.put("data", usuario.getData());
                parametersMap.put("usuarioenumtypeenum", usuario.getUsuarioEnumTypeEnum().name());

                lisMapParamiters.add(parametersMap);
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lisMapParamiters);

            // Preencher o relatório com os dados
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            response.setContentType("application/excel");
            response.setHeader("Content-Disposition", "inline; filename=relatorio.ex");

            // Exportar o relatório para um fluxo de saída
            OutputStream outStream = response.getOutputStream();
            // obtém um fluxo de saída a partir do objeto usado para exportar o relatório no formato PDF.
            JRPdfExporter exporter = new JRPdfExporter();
            // Aqui, definimos o relatório a ser exportado como entrada para o exportador.
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            // Configuramos o fluxo de saída para o exportador, que enviará o PDF para o outStream
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));
            // Isso efetivamente exporta o relatório para o fluxo de saída, criando o PDF.
            exporter.exportReport();

            String filePath = "/home/flexabus/Downloads/ExercicoSpring_teste/PDF_Docs/" + UUID.randomUUID().toString() + "_usuarios.pdf";
            JasperExportManager.exportReportToe(jasperPrint, filePath);

            outStream.flush(); // Isso limpa qualquer buffer de saída pendente.
            outStream.close(); //  Isso fecha o fluxo de saída, para gerar um documento xml
ChatGPT
Para gerar um relatório no formato XML usando o JasperReports, você pode seguir um processo semelhante ao exemplo anterior. A principal diferença é que você precisa ajustar o tipo de conteúdo (Content-Type) e o nome do arquivo no cabeçalho da resposta HTTP para indicar que o relatório é um arquivo XML. Aqui está um exemplo de como você pode fazer isso:

java
Copy code
var findByAllUsers = usuarioFlywayService.findByAll();

String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jrxml";
JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath);

String contextImage = "/home/flexabus/Imagens/Capturas de tela/Captura de tela de 2023-10-09 08-44-11.png";

// Preencher os parâmetros do relatório, se houver
Map<String, Object> parameters = new HashMap<>();
parameters.put("tituloRelatorio", "Relatório de Produtos");
parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
parameters.put("contextImage", contextImage);

List<Map<String, Object>> lisMapParamiters = new ArrayList<>();
for (UsuarioFlyway usuario : findByAllUsers) {
Map<String, Object> parametersMap = new HashMap<>();

    parametersMap.put("nome", usuario.getNome());
    parametersMap.put("numero", usuario.getNumero());
    parametersMap.put("email", usuario.getEmail());
    parametersMap.put("data", usuario.getData());
    parametersMap.put("usuarioenumtypeenum", usuario.getUsuarioEnumTypeEnum().name());

    lisMapParamiters.add(parametersMap);
}

JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lisMapParamiters);

// Preencher o relatório com os dados
JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

response.setContentType("application/xml"); // Define o tipo de conteúdo para XML
response.setHeader("Content-Disposition", "inline; filename=relatorio.xml"); // Define o nome do arquivo como relatorio.xml

// Exportar o relatório para um fluxo de saída
OutputStream outStream = response.getOutputStream();

// Crie um exportador para o formato XML (JRXmlExporter)
JRXmlExporter exporter = new JRXmlExporter();

// Configure o exportador
exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
exporter.setExporterOutput(new SimpleXmlExporterOutput(outStream));

// Exporte o relatório
exporter.exportReport();

outStream.flush(); // Isso limpa qualquer buffer de saída pendente.
outStream.close(); // Isso fecha o fluxo de saída.
Neste exemplo, definimos o tipo de conteúdo como "application/xml" e o nome do arquivo como "relatorio.xml" no cabeçalho da resposta HTTP. Em seguida, usamos o JRXmlExporter para exportar o relatório no formato XML. Certifique-se de ajustar os caminhos de arquivo e as configurações conforme necessário.