Para criar um mecanismo de busca em JavaScript para a tabela fornecida, você pode usar a seguinte abordagem. Neste exemplo, usaremos JavaScript puro sem bibliotecas adicionais.

Primeiro, você precisará de um campo de entrada onde os usuários possam inserir os termos de pesquisa e, em seguida, você precisará de código JavaScript para filtrar a tabela com base nos termos de pesquisa. Vamos supor que você tenha um campo de entrada com o ID "searchInput" para a pesquisa:

```html
<input type="text" id="searchInput" placeholder="Pesquisar...">
```

Aqui está o código JavaScript para implementar a pesquisa:

```javascript
// Obtém a referência para o campo de entrada de pesquisa
const searchInput = document.getElementById("searchInput");

// Obtém a referência para a tabela e suas linhas
const table = document.getElementById("tabela");
const rows = table.getElementsByTagName("tr");

// Função para realizar a pesquisa
function performSearch() {
const searchTerm = searchInput.value.toLowerCase();

    for (let i = 1; i < rows.length; i++) { // Começando em 1 para pular o cabeçalho
    const row = rows[i];
    const cells = row.getElementsByTagName("td");
    let rowContainsSearchTerm = false;

        for (let j = 0; j < cells.length; j++) {
        const cell = cells[j];
        const cellText = cell.textContent || cell.innerText;

        if (cellText.toLowerCase().indexOf(searchTerm) > -1) {
            rowContainsSearchTerm = true;
            break;
        }
        }

        if (rowContainsSearchTerm) {
        row.style.display = "";
        } else {
        row.style.display = "none";
        }
    }
}

// Adiciona um ouvinte de evento de digitação no campo de entrada de pesquisa
searchInput.addEventListener("keyup", performSearch);
```

Neste código, primeiro obtemos referências para o campo de entrada de pesquisa e para a tabela e suas linhas. Em seguida, adicionamos um ouvinte de evento de digitação ao campo de entrada para que a função performSearch seja chamada sempre que o usuário digitar algo na caixa de pesquisa.

A função performSearch percorre todas as linhas da tabela (começando da segunda linha para ignorar o cabeçalho) e verifica se alguma célula contém o termo de pesquisa. Se uma linha contiver o termo de pesquisa, ela é exibida; caso contrário, é ocultada.

Dessa forma, a tabela será filtrada dinamicamente com base no que o usuário digitar na caixa de pesquisa. Certifique-se de que o código JavaScript seja carregado após a criação da tabela no HTML para que ele funcione corretamente.

#
#

Atualize em relação a seuinte abordagem    

```html
<thead>
<tr>
<th class="thInput"><input type="text" class="filtroInput" id="filtroNome" placeholder="Filtrar por nome"></th>
<th class="thInput"><input type="text" class="filtroInput" id="filtroNumero" placeholder="Filtrar por número"></th>
<th class="thInput"><input type="text" class="filtroInput" id="filtroEmail" placeholder="Filtrar por email"></th>
<th class="thInput"><input type="text" class="filtroInput" id="filtroData" placeholder="Filtrar por data"></th>
<th class="thInput"><input type="text" class="filtroInput" id="filtroTipo" placeholder="Filtrar por tipo"></th>
<th class="thInput"><input type="text" class="filtroInput" id="filtroAcoes" style="width: 8rem;"  disabled></th>
</tr>
</thead>
```

Ajustar o código JavaScript para que ele funcione com a estrutura da tabela que você forneceu, onde os campos de pesquisa estão dentro do cabeçalho da tabela. Aqui está o código JavaScript atualizado para essa abordagem:

```javascript
// Obtém a referência para os campos de entrada de pesquisa
const searchInputs = document.querySelectorAll(".filtroInput");

// Obtém a referência para a tabela e suas linhas
const table = document.getElementById("tabela");
const rows = table.getElementsByTagName("tr");

// Função para realizar a pesquisa
function performSearch() {
const searchTerms = Array.from(searchInputs).map(input => input.value.toLowerCase());

for (let i = 1; i < rows.length; i++) { // Começando em 1 para pular o cabeçalho
const row = rows[i];
const cells = row.getElementsByTagName("td");
let rowContainsSearchTerm = true;

    for (let j = 0; j < cells.length; j++) {
      const cell = cells[j];
      const cellText = cell.textContent || cell.innerText;
      const searchTerm = searchTerms[j];

      if (searchTerm && cellText.toLowerCase().indexOf(searchTerm) === -1) {
        rowContainsSearchTerm = false;
        break;
      }
    }

    if (rowContainsSearchTerm) {
      row.style.display = "";
    } else {
      row.style.display = "none";
    }
}
}

// Adiciona um ouvinte de evento de digitação em cada campo de entrada de pesquisa
searchInputs.forEach(input => {
input.addEventListener("input", performSearch);
});
selectInput.addEventListener("change", performSearch);
```

Neste código atualizado, usamos document.querySelectorAll para obter todos os campos de entrada de pesquisa com a classe .filtroInput. Também atualizamos a função performSearch para coletar os termos de pesquisa de todos os campos de entrada.

Agora, o código se adapta automaticamente ao número de campos de pesquisa e filtra a tabela com base em todos eles. Cada campo de entrada de pesquisa tem um ouvinte de evento de entrada que aciona a função performSearch sempre que o usuário digita algo em qualquer campo.

Certifique-se de que o código JavaScript seja carregado após a criação da tabela no HTML para que ele funcione corretamente.

Neste código, adicionamos um ouvinte de evento change ao campo select com o ID "filtroTipo". Quando o valor do campo select é alterado, a função performSearch é chamada para realizar a pesquisa com base no valor selecionado.

#
#

Para criar inputs em uma tabela (HTML <table>) para fazer um filtro de busca com base em cada <thead>, você pode seguir os seguintes passos:

Estruture sua tabela HTML:

```html
<table id="tabela">
    <thead>
        <tr>
            <th>Nome</th>
            <th>Idade</th>
            <!-- Adicione mais colunas conforme necessário -->
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>João</td>
            <td>30</td>
            <!-- Preencha com os dados da tabela -->
        </tr>
        <!-- Adicione mais linhas conforme necessário -->
    </tbody>
</table>
```

Adicione inputs de filtro para cada coluna do `<thead>`:

```html
<thead>
    <tr>
        <th><input type="text" id="filtroNome" placeholder="Filtrar por nome"></th>
        <th><input type="text" id="filtroIdade" placeholder="Filtrar por idade"></th>
        <!-- Adicione inputs para cada coluna -->
    </tr>
</thead>
```

Crie JavaScript para adicionar funcionalidade de filtro:

```html
<script>
    // Função para realizar o filtro
    function filtrarTabela(coluna) {
        var input = document.getElementById('filtro' + coluna);
        var tabela = document.getElementById('tabela');
        var linhas = tabela.getElementsByTagName('tr');

        for (var i = 1; i < linhas.length; i++) {
            var celula = linhas[i].getElementsByTagName('td')[coluna];
            if (celula) {
                var textoCelula = celula.textContent || celula.innerText;
                if (textoCelula.toLowerCase().indexOf(input.value.toLowerCase()) > -1) {
                    linhas[i].style.display = '';
                } else {
                    linhas[i].style.display = 'none';
                }
            }
        }
    }

    // Adicione listeners para os inputs de filtro
    document.getElementById('filtroNome').addEventListener('keyup', function() {
        filtrarTabela(0); // 0 corresponde à coluna "Nome"
    });

    document.getElementById('filtroIdade').addEventListener('keyup', function() {
        filtrarTabela(1); // 1 corresponde à coluna "Idade"
    });

    // Adicione mais listeners para as outras colunas conforme necessário
</script>
```
Neste exemplo, criamos inputs de filtro para cada coluna do `<thead>`, dando a cada input um ID único (por exemplo, filtroNome, filtroIdade). Em seguida, escrevemos uma função JavaScript filtrarTabela que é chamada quando os inputs são alterados. Essa função itera pelas linhas da tabela e verifica se o valor no input de filtro corresponde ao conteúdo da célula da coluna correspondente. Se houver correspondência, a linha é exibida; caso contrário, a linha é ocultada.

Você pode adicionar mais colunas à tabela e inputs de filtro correspondentes conforme necessário. Certifique-se de atualizar os índices na função filtrarTabela para corresponder às colunas específicas. Este é um exemplo básico de como implementar um filtro de busca em uma tabela HTML, e você pode personalizá-lo de acordo com suas necessidades.


corrigir referente a esse exemplo 

```html
<table  id="tabela" class="table table-striped table-bordered custom-table">
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

                <thead>
                    <tr>
                        <th class="thInput"><input type="text" id="filtroNome" placeholder="Filtrar por nome"></th>
                        <th class="thInput"><input type="text" id="filtroNumero" placeholder="Filtrar por número"></th>
                        <th class="thInput"><input type="text" id="filtroEmail" placeholder="Filtrar por email"></th>
                        <th class="thInput"><input type="text" id="filtroData" placeholder="Filtrar por data"></th>
                        <th class="thInput"><input type="text" id="filtroTipo" placeholder="Filtrar por tipo"></th>
                        <th class="thInput"><input type="text" id="filtroAcoes" style="width: 8rem;"  disabled></th>
                    </tr>
                </thead>

                <tbody>
                    <tr class="custom-row" th:each="func : ${usuarioFlyway}">
                        <td th:text="${func.nome}"></td>
                        <td th:text="${func.numero}"></td>
                        <td th:text="${func.email}"></td>
                        <td th:text="${func.data}"></td>
                        <td th:text="${func.usuarioEnumTypeEnum}"></td>
                        <td class="buttons">
                            <a th:href="@{/usuario/edit/{id}(id=${func.id})}"
                               method="get"
                               class="btn btn-warning">
                                <i class="fa fa-pencil"></i>
                            </a>
                            <a th:href="@{/usuario/delete/{id}(id=${func.id})}"
                                th:method="get"
                                class="btn btn-danger">
                                <i class="fa fa-trash"></i>
                            </a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
```
ChatGPT
O exemplo que você forneceu parece estar funcionando corretamente para realizar filtros na tabela. No entanto, vou fornecer algumas dicas para melhorar a clareza e a manutenção do código:

Adicione classes CSS às células de cabeçalho para estilização:
```html
<thead>
    <tr>
        <th class="thInput">Nome</th>
        <th class="thInput">Número</th>
        <th class="thInput">Email</th>
        <th class="thInput">Data</th>
        <th class="thInput">Tipo</th>
        <th class="thInput" style="width: 8rem;" disabled>Ações</th>
    </tr>
</thead>
```

Para os inputs de filtro, você pode adicionar eventos JavaScript de forma mais limpa usando um identificador de classe em vez de IDs individuais:

```html
<thead>
    <tr>
        <th class="thInput"><input type="text" class="filtroInput" placeholder="Filtrar por nome"></th>
        <th class="thInput"><input type="text" class="filtroInput" placeholder="Filtrar por número"></th>
        <th class="thInput"><input type="text" class="filtroInput" placeholder="Filtrar por email"></th>
        <th class="thInput"><input type="text" class="filtroInput" placeholder="Filtrar por data"></th>
        <th class="thInput"><input type="text" class="filtroInput" placeholder="Filtrar por tipo"></th>
        <th class="thInput" style="width: 8rem;" disabled></th>
    </tr>
</thead>
```
Use JavaScript para adicionar a lógica de filtro:

```javascript
// Função para realizar o filtro
function filtrarTabela(coluna) {
    var input = document.querySelectorAll('.filtroInput')[coluna];
    var tabela = document.getElementById('tabela');
    var linhas = tabela.getElementsByTagName('tr');

    for (var i = 1; i < linhas.length; i++) {
        var celula = linhas[i].getElementsByTagName('td')[coluna];
        if (celula) {
            var textoCelula = celula.textContent || celula.innerText;
            if (textoCelula.toLowerCase().indexOf(input.value.toLowerCase()) > -1) {
                linhas[i].style.display = '';
            } else {
                linhas[i].style.display = 'none';
            }
        }
    }
}

// Adicione listeners para os inputs de filtro
var inputsFiltro = document.querySelectorAll('.filtroInput');
for (var i = 0; i < inputsFiltro.length; i++) {
    inputsFiltro[i].addEventListener('keyup', function () {
        var coluna = Array.prototype.indexOf.call(this.parentElement.parentElement.children, this.parentElement) - 1;
        filtrarTabela(coluna);
    });
}

```
Neste código JavaScript, usamos classes em vez de IDs para os inputs de filtro, o que simplifica a lógica de adicionar os listeners para todos os inputs. Além disso, o código identifica automaticamente a coluna à qual o input de filtro está associado, para que você não precise ajustar manualmente os índices das colunas.

Certifique-se de adicionar esse código JavaScript após o fechamento da `</table>` para garantir que o DOM esteja carregado antes de adicionar os listeners. Com essas melhorias, você deve ter um código mais organizado e fácil de manter para filtrar a tabela com base nas colunas.

#
#

como pegar o valor de um input em js

Para pegar o valor de um elemento `<input>` em JavaScript, você pode usar a propriedade value do elemento. Aqui está um exemplo de como fazer isso:

```javascript
// Suponha que você tenha um elemento input com o id "meuInput"
var meuInput = document.getElementById("meuInput");

// Pegue o valor do input usando a propriedade value
var valorDoInput = meuInput.value;

// Agora, 'valorDoInput' contém o valor do input
console.log(valorDoInput);
```

Neste exemplo:

Usamos document.getElementById("meuInput") para obter uma referência ao elemento input com o ID "meuInput".

Em seguida, usamos meuInput.value para acessar o valor atual do input e armazená-lo na variável valorDoInput.

Finalmente, usamos console.log(valorDoInput) para exibir o valor no console, mas você pode usá-lo da maneira que desejar em seu código JavaScript.

#
#


e se for dessa forma var inputsFiltro = document.querySelectorAll('.filtroInput');

Se você está usando document.querySelectorAll('.filtroInput') para obter uma coleção de elementos com a classe "filtroInput", você pode percorrer essa coleção para acessar os valores de cada input. Aqui está como você pode fazer isso:

```javascript
// Suponha que você tenha vários elementos input com a classe "filtroInput"
var inputsFiltro = document.querySelectorAll('.filtroInput');

// Itere sobre a coleção de inputs
inputsFiltro.forEach(function (input) {
// Use input.value para pegar o valor de cada input
var valorDoInput = input.value;

    // Faça algo com o valor, por exemplo, exiba no console
    console.log(valorDoInput);
});
```

Neste exemplo, document.querySelectorAll('.filtroInput') seleciona todos os elementos com a classe "filtroInput" e retorna uma coleção de elementos. Em seguida, usamos .forEach para iterar sobre essa coleção e, para cada elemento, usamos input.value para obter o valor do input.

Isso permite que você acesse os valores de todos os inputs com a classe "filtroInput" e faça o que quiser com esses valores em seu código JavaScript.