Vou criar um exemplo completo que inclui tanto o frontend (HTML, JavaScript) quanto o backend (Spring Boot) para demonstrar como realizar uma pesquisa com filtro, incluindo a filtragem por um campo enum.

Passo 1: Configurar o projeto Spring Boot

Crie um projeto Spring Boot com o Spring Initializr ou usando a ferramenta que preferir. Certifique-se de incluir as dependências necessárias, como "Spring Web" e "Spring Data JPA".

Passo 2: Definir a entidade UsuarioFlyway

Crie uma entidade chamada UsuarioFlyway com um campo usuarioEnumTypeEnum do tipo enum. Aqui está um exemplo:

```java
@Entity
public class UsuarioFlyway {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    
    @Enumerated(EnumType.STRING)
    private UsuarioEnumTypeEnum usuarioEnumTypeEnum;

    // Getters e setters
}
```

Passo 3: Criar o enum UsuarioEnumTypeEnum

Defina o enum UsuarioEnumTypeEnum que representa o campo enum:

```java
public enum UsuarioEnumTypeEnum {
    E("Elegível"),
    N("Não Elegível");

    private final String descricao;

    UsuarioEnumTypeEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
```

Passo 4: Configurar o Repositório

Crie um repositório JPA para a entidade UsuarioFlyway. Por exemplo:

```java
@Repository
public interface UsuarioFlywayRepository extends JpaRepository<UsuarioFlyway, Long> {
    List<UsuarioFlyway> findByUsuarioEnumTypeEnum(UsuarioEnumTypeEnum usuarioEnumTypeEnum);
}
```

Passo 5: Configurar o Controller

Crie um controlador para manipular as solicitações de pesquisa. Aqui está um exemplo:

```java
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioFlywayRepository usuarioRepository;

    @PostMapping("/filtrar")
    public List<UsuarioFlyway> filtrarUsuarios(@RequestBody UsuarioFilter filtro) {
        if (filtro.getUsuarioEnumTypeEnum() != null) {
            return usuarioRepository.findByUsuarioEnumTypeEnum(filtro.getUsuarioEnumTypeEnum());
        } else {
            return usuarioRepository.findAll();
        }
    }
}
```

Passo 6: Configurar a classe de filtro

Crie uma classe de filtro para representar os critérios de pesquisa. Certifique-se de incluir um campo usuarioEnumTypeEnum correspondente ao campo enum:

```java
public class UsuarioFilter {
    private UsuarioEnumTypeEnum usuarioEnumTypeEnum;

    // Getters e setters
}
```

Passo 7: Configurar o Frontend (HTML e JavaScript)

Aqui está um exemplo simples de uma página HTML que permite ao usuário inserir critérios de pesquisa, incluindo um campo para selecionar o enum:

```html
<!DOCTYPE html>
<html>
<head>
    <title>Pesquisa de Usuários</title>
</head>
<body>
    <h1>Pesquisa de Usuários</h1>

    <label for="filtroNome">Nome:</label>
    <input type="text" id="filtroNome" class="filtroInput"><br>

    <label for="filtroEmail">Email:</label>
    <input type="text" id="filtroEmail" class="filtroInput"><br>

    <label for="filtroTipo">Tipo:</label>
    <select id="filtroTipo" class="filtroInput">
        <option value="">-- Selecione --</option>
        <option value="E">Elegível</option>
        <option value="N">Não Elegível</option>
    </select><br>

    <button id="btnPesquisar">Pesquisar</button>

    <table id="tabela">
        <!-- Tabela de resultados será preenchida aqui -->
    </table>

    <script>
        document.getElementById("btnPesquisar").addEventListener("click", performSearch);

        function performSearch() {
            const searchTerms = Array.from(document.querySelectorAll(".filtroInput")).map(input => input.value);
            const selectTerm = document.getElementById("filtroTipo").value.toUpperCase();

            const filtro = {
                nome: searchTerms[0] || null,
                email: searchTerms[1] || null,
                usuarioEnumTypeEnum: selectTerm || null
            };

            fetch('/usuarios/filtrar', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(filtro)
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro na resposta da chamada AJAX');
                }
                return response.json();
            })
            .then(usuariosFiltrados => {
                // Preencha a tabela com os resultados da pesquisa
                const tabela = document.getElementById("tabela");
                tabela.innerHTML = "<tr><th>ID</th><th>Nome</th><th>Tipo</th></tr>";

                for (const usuario of usuariosFiltrados) {
                    const row = tabela.insertRow();
                    const cell1 = row.insertCell(0);
                    const cell2 = row.insertCell(1);
                    const cell3 = row.insertCell(2);

                    cell1.textContent = usuario.id;
                    cell2.textContent = usuario.nome;
                    cell3.textContent = usuario.usuarioEnumTypeEnum;
                }
            })
            .catch(error => {
                console.error('Erro na chamada AJAX:', error);
            });
        }
    </script>
</body>
</html>
```

Este exemplo de página HTML permite que o usuário insira critérios de pesquisa, incluindo um campo para selecionar o enum UsuarioEnumTypeEnum. Quando o botão "Pesquisar" é clicado, ele realiza uma chamada AJAX para o endpoint /usuarios/filtrar no servidor Spring Boot com os critérios de pesquisa e exibe os resultados na tabela.

A tabela de resultados é preenchida dinamicamente com os resultados da pesquisa. Certifique-se de adaptar o código JavaScript e HTML de acordo com suas necessidades específicas.

Com esse exemplo completo, você deve conseguir criar uma funcionalidade de pesquisa com filtro, incluindo a filtragem por um campo enum, em seu aplicativo Spring Boot. Certifique-se de que seu servidor Spring Boot esteja configurado corretamente e que as dependências necessárias estejam no seu projeto

#
#

Entendi que você deseja receber apenas os valores dos usuariosFiltrados sem construir uma tabela. Nesse caso, você pode simplificar o JavaScript da seguinte maneira:

```html
<!DOCTYPE html>
<html>
<head>
    <title>Pesquisa de Usuários</title>
</head>
<body>
    <h1>Pesquisa de Usuários</h1>

    <label for="filtroNome">Nome:</label>
    <input type="text" id="filtroNome" class="filtroInput"><br>

    <label for="filtroEmail">Email:</label>
    <input type="text" id="filtroEmail" class="filtroInput"><br>

    <label for="filtroTipo">Tipo:</label>
    <select id="filtroTipo" class="filtroInput">
        <option value="">-- Selecione --</option>
        <option value="E">Elegível</option>
        <option value="N">Não Elegível</option>
    </select><br>

    <button id="btnPesquisar">Pesquisar</button>

    <div id="resultados">
        <!-- Os resultados da pesquisa serão exibidos aqui -->
    </div>

    <script>
        document.getElementById("btnPesquisar").addEventListener("click", performSearch);

        function performSearch() {
            const searchTerms = Array.from(document.querySelectorAll(".filtroInput")).map(input => input.value);
            const selectTerm = document.getElementById("filtroTipo").value.toUpperCase();

            const filtro = {
                nome: searchTerms[0] || null,
                email: searchTerms[1] || null,
                usuarioEnumTypeEnum: selectTerm || null
            };

            fetch('/usuarios/filtrar', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(filtro)
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro na resposta da chamada AJAX');
                }
                return response.json();
            })
            .then(usuariosFiltrados => {
                // Exiba os resultados da pesquisa
                const resultadosDiv = document.getElementById("resultados");
                resultadosDiv.innerHTML = "";

                for (const usuario of usuariosFiltrados) {
                    const usuarioDiv = document.createElement("div");
                    usuarioDiv.textContent = `ID: ${usuario.id}, Nome: ${usuario.nome}, Tipo: ${usuario.usuarioEnumTypeEnum}`;
                    resultadosDiv.appendChild(usuarioDiv);
                }
            })
            .catch(error => {
                console.error('Erro na chamada AJAX:', error);
            });
        }
    </script>
</body>
</html>
```

Neste exemplo, os resultados da pesquisa são exibidos dentro da <div id="resultados">. Cada usuário filtrado é representado como um <div> dentro dessa <div>.

Certifique-se de ajustar o layout e o estilo conforme necessário para atender aos requisitos de exibição da sua aplicação. Os resultados agora são mostrados de forma mais simples, sem a construção de uma tabela.

#
#

Se você deseja aplicar os filtros aos resultados da tabela HTML que você forneceu, você pode adaptar o exemplo anterior da seguinte forma. No exemplo abaixo, vamos adicionar um evento de ouvinte de evento input a cada campo de filtro e, quando os filtros forem alterados, atualizaremos a exibição da tabela de acordo com os critérios de filtro.

Aqui está o HTML e o JavaScript atualizados:

```html
<!DOCTYPE html>
<html>
<head>
    <title>Pesquisa de Usuários</title>
</head>
<body>
    <h1>Pesquisa de Usuários</h1>

    <table id="tabela" class="table table-striped table-bordered custom-table">
        <!-- Cabeçalho da tabela -->
        <thead class="thead-dark custom-thead">
            <tr class="custom-thead">
                <th>Nome</th>
                <th>Número</th>
                <th>Email</th>
                <th>Data de criação do registro</th>
                <th>Type enum</th>
                <th>Ações</th>
            </tr>
        </thead>

        <!-- Filtros -->
        <thead>
            <tr>
                <th class="thInput"><input type="text" class="filtroInput" id="filtroNome" placeholder="Filtrar por nome"></th>
                <th class="thInput"><input type="text" class="filtroInput" id="filtroNumero" placeholder="Filtrar por número"></th>
                <th class="thInput"><input type="text" class="filtroInput" id="filtroEmail" placeholder="Filtrar por email"></th>
                <th class="thInput"><input type="text" class="filtroInput" id="filtroData" placeholder="Filtrar por data"></th>
                <th class="thInput">
                    <select class="filtroInput" id="filtroTipo">
                        <option value=""></option>
                        <option value="E">Elegível</option>
                        <option value="N">Não Elegível</option>
                    </select>
                </th>
                <th class="thInput"><input type="text" class="filtroInput" id="filtroAcoes" style="width: 8rem;" disabled></th>
            </tr>
        </thead>

        <!-- Corpo da tabela (será preenchido com JavaScript) -->
        <tbody id="tabelaCorpo">
        </tbody>
    </table>

    <script>
        // Referências aos elementos da tabela e campos de filtro
        const tabelaCorpo = document.getElementById("tabelaCorpo");
        const filtroInputs = document.querySelectorAll(".filtroInput");

        // Função para atualizar a exibição da tabela com base nos filtros
        function atualizarTabela() {
            // Obtenha os valores dos campos de filtro
            const filtroNome = document.getElementById("filtroNome").value.toLowerCase();
            const filtroNumero = document.getElementById("filtroNumero").value.toLowerCase();
            const filtroEmail = document.getElementById("filtroEmail").value.toLowerCase();
            const filtroData = document.getElementById("filtroData").value.toLowerCase();
            const filtroTipo = document.getElementById("filtroTipo").value.toUpperCase();

            // Limpe a tabela
            tabelaCorpo.innerHTML = "";

            // Itere sobre os resultados originais e aplique os filtros
            // Substitua isso pelos seus dados reais
            const resultadosOriginais = [
                { nome: "João", numero: "123", email: "joao@example.com", data: "2023-09-28", usuarioEnumTypeEnum: "E" },
                { nome: "Maria", numero: "456", email: "maria@example.com", data: "2023-09-29", usuarioEnumTypeEnum: "N" },
                // Adicione mais resultados aqui
            ];

            for (const usuario of resultadosOriginais) {
                const nome = usuario.nome.toLowerCase();
                const numero = usuario.numero.toLowerCase();
                const email = usuario.email.toLowerCase();
                const data = usuario.data.toLowerCase();
                const tipo = usuario.usuarioEnumTypeEnum.toUpperCase();

                if (
                    nome.includes(filtroNome) &&
                    numero.includes(filtroNumero) &&
                    email.includes(filtroEmail) &&
                    data.includes(filtroData) &&
                    (filtroTipo === "" || tipo === filtroTipo)
                ) {
                    const row = tabelaCorpo.insertRow();
                    const cell1 = row.insertCell(0);
                    const cell2 = row.insertCell(1);
                    const cell3 = row.insertCell(2);
                    const cell4 = row.insertCell(3);
                    const cell5 = row.insertCell(4);
                    const cell6 = row.insertCell(5);

                    cell1.textContent = usuario.nome;
                    cell2.textContent = usuario.numero;
                    cell3.textContent = usuario.email;
                    cell4.textContent = usuario.data;
                    cell5.textContent = usuario.usuarioEnumTypeEnum;
                    cell6.innerHTML = `<a href="#">Editar</a> | <a href="#">Excluir</a>`;
                }
            }
        }

        // Adicione ouvintes de evento de input para os campos de filtro
        filtroInputs.forEach(input => {
            input.addEventListener("input", atualizarTabela);
        });

        // Inicialmente, carregue a tabela com os valores padrão
        atualizarTabela();
    </script>
</body>
</html>
```

Neste exemplo, a tabela é atualizada dinamicamente com base nos critérios de filtro à medida que os campos de filtro são preenchidos ou alterados. Os resultados da pesquisa são filtrados com base nos critérios fornecidos e exibidos na tabela. Certifique-se de adaptar isso às suas necessidades e integrá-lo com sua aplicação Spring Boot para obter os resultados reais do banco de dados.

#
#


Peço desculpas por qualquer confusão anterior. Vou corrigir e simplificar o exemplo da pesquisa com filtros no HTML e JavaScript.

HTML

```html
<!DOCTYPE html>
<html>
<head>
    <title>Pesquisa de Usuários</title>
</head>
<body>
    <h1>Pesquisa de Usuários</h1>

    <label for="filtroNome">Nome:</label>
    <input type="text" id="filtroNome" class="filtroInput"><br>

    <label for="filtroEmail">Email:</label>
    <input type="text" id="filtroEmail" class="filtroInput"><br>

    <label for="filtroTipo">Tipo:</label>
    <select id="filtroTipo" class="filtroInput">
        <option value="">-- Selecione --</option>
        <option value="E">Elegível</option>
        <option value="N">Não Elegível</option>
    </select><br>

    <button id="btnPesquisar">Pesquisar</button>

    <div id="resultados">
        <!-- Os resultados da pesquisa serão exibidos aqui -->
    </div>

    <script>
        document.getElementById("btnPesquisar").addEventListener("click", performSearch);

        function performSearch() {
            const nome = document.getElementById("filtroNome").value;
            const email = document.getElementById("filtroEmail").value;
            const tipo = document.getElementById("filtroTipo").value;

            fetch(`/usuarios/filtrar?nome=${nome}&email=${email}&tipo=${tipo}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro na resposta da chamada AJAX');
                }
                return response.json();
            })
            .then(usuariosFiltrados => {
                // Exiba os resultados da pesquisa
                const resultadosDiv = document.getElementById("resultados");
                resultadosDiv.innerHTML = "";

                for (const usuario of usuariosFiltrados) {
                    const usuarioDiv = document.createElement("div");
                    usuarioDiv.textContent = `Nome: ${usuario.nome}, Email: ${usuario.email}, Tipo: ${usuario.usuarioEnumTypeEnum}`;
                    resultadosDiv.appendChild(usuarioDiv);
                }
            })
            .catch(error => {
                console.error('Erro na chamada AJAX:', error);
            });
        }
    </script>
</body>
</html>
```

Neste exemplo, os filtros de pesquisa consistem em nome, email e tipo (enum). Quando o botão "Pesquisar" é clicado, os valores dos campos de filtro são lidos e passados como parâmetros na URL da solicitação AJAX.

Você precisará ajustar a URL da solicitação fetch para corresponder à sua configuração do servidor Spring Boot. Certifique-se de que a lógica no servidor esteja configurada para receber esses parâmetros e realizar a filtragem corretamente.

Espero que este exemplo atenda às suas necessidades.

#
#


```javascript
// Função para realizar a chamada AJAX quando os campos de filtro ou o campo select forem alterados
async function performSearch() {
  const searchTerms = Array.from(document.querySelectorAll(".filtroInput")).map(input => input.value);
  const selectTerm = document.getElementById("filtroTipo").value.toUpperCase();

  // Crie um objeto de filtro com os critérios de pesquisa
  const filtro = {
    nome: searchTerms[0] || null,
    numero: searchTerms[1] || null,
    email: searchTerms[2] || null,
    data: searchTerms[3] || null,
    usuarioEnumTypeEnum: selectTerm || null
  };

  // Certifique-se de que os valores não sejam undefined ou null
  Object.keys(filtro).forEach(key => {
    if (filtro[key] === undefined || filtro[key] === null) {
      delete filtro[key];
    }
  });

  // Realize a chamada AJAX com Fetch
  fetch('/usuario/filtrar', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(filtro)
  })
    .then(response => {
      if (!response.ok) {
        throw new Error('Erro na resposta da chamada AJAX');
      }
      return response.json();
    })
    .then(usuariosFiltrados => {
      const table = document.getElementById("tabela");

      if (usuariosFiltrados.length > 0) {
        // Exiba a tabela
        table.style.display = "table";

        // Preencha a tabela com os resultados da pesquisa
        const tbody = table.querySelector("tbody");
        tbody.innerHTML = "";

        for (const usuario of usuariosFiltrados) {
          const row = tbody.insertRow();
          const cell1 = row.insertCell(0);
          const cell2 = row.insertCell(1);
          const cell3 = row.insertCell(2);
          const cell4 = row.insertCell(3);
          const cell5 = row.insertCell(4);

          cell1.textContent = usuario.nome;
          cell2.textContent = usuario.numero;
          cell3.textContent = usuario.email;
          cell4.textContent = usuario.data;
          cell5.textContent = usuario.usuarioEnumTypeEnum;
        }
      } else {
        // Oculte a tabela se não houver resultados
        table.style.display = "none";
      }

      console.info("USER => ", usuariosFiltrados)
    })
    .catch(error => {
      console.error('Erro na chamada AJAX:', error);
    });
}

// Adicione ouvintes de eventos aos campos de filtro e ao campo select
document.querySelectorAll(".filtroInput").forEach(input => {
  input.addEventListener("input", performSearch);
});
```
document.getElementById("filtroTipo").addEventListener("change", performSearch);
Neste exemplo, a tabela é exibida ou ocultada com base nos resultados da pesquisa. Se houver resultados, a tabela será exibida e preenchida com os resultados; caso contrário, a tabela será ocultada. Certifique-se de que o ID da tabela (tabela) corresponda ao ID da tabela em seu HTML.

