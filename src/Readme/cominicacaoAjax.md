Para realizar chamadas AJAX em JavaScript, você pode usar a biblioteca XMLHttpRequest ou a API Fetch, que é mais moderna e flexível. Abaixo, vou criar um exemplo de chamada AJAX usando a API Fetch para enviar uma solicitação POST para o servidor quando os campos de filtro são preenchidos ou o valor do campo select é alterado. Lembre-se de que você deve adaptar este exemplo de acordo com a estrutura do seu projeto Java Spring Boot e os URLs e endpoints corretos.

javascript
Copy code
// Função para realizar a chamada AJAX quando os campos de filtro ou o campo select forem alterados
function performSearch() {
const searchTerms = Array.from(document.querySelectorAll(".filtroInput")).map(input => input.value.toLowerCase());
const selectTerm = document.getElementById("filtroTipo").value.toLowerCase();

// Crie um objeto de filtro com os critérios de pesquisa
const filtro = {
nome: searchTerms[0],
numero: searchTerms[1],
email: searchTerms[2],
data: searchTerms[3],
usuarioEnumTypeEnum: selectTerm
};

// Realize a chamada AJAX
fetch('/usuarios/filtrar', {
method: 'POST',
headers: {
'Content-Type': 'application/json'
},
body: JSON.stringify(filtro)
})
.then(response => response.json())
.then(usuariosFiltrados => {
// Atualize a tabela com os resultados da pesquisa
// Implemente a lógica para atualizar a tabela aqui
})
.catch(error => {
console.error('Erro na chamada AJAX:', error);
});
}

// Adicione ouvintes de eventos aos campos de filtro e ao campo select
document.querySelectorAll(".filtroInput").forEach(input => {
input.addEventListener("input", performSearch);
});
document.getElementById("filtroTipo").addEventListener("change", performSearch);
Neste exemplo, estamos usando a função fetch para enviar uma solicitação POST para o endpoint /usuarios/filtrar com o objeto filtro como corpo da solicitação. A resposta da chamada AJAX contém os resultados da pesquisa, que podem ser processados para atualizar a tabela na página.

Lembre-se de adaptar o código acima para o seu projeto específico, incluindo os URLs e endpoints corretos do seu backend Spring Boot, bem como a lógica para atualizar a tabela com os resultados da pesquisa.




User
crie com axios ou fetch
ChatGPT
Claro, vou criar um exemplo usando a biblioteca Axios para realizar a chamada AJAX para o servidor quando os campos de filtro ou o campo select forem alterados. Primeiro, você precisará garantir que a biblioteca Axios esteja incluída em seu projeto. Você pode adicioná-la via CDN ou instalá-la via npm, dependendo do seu ambiente. Aqui está o exemplo usando Axios:

javascript
Copy code
// Importe a biblioteca Axios (certifique-se de incluir a biblioteca no seu projeto)
import axios from 'axios';

// Função para realizar a chamada AJAX quando os campos de filtro ou o campo select forem alterados
function performSearch() {
const searchTerms = Array.from(document.querySelectorAll(".filtroInput")).map(input => input.value.toLowerCase());
const selectTerm = document.getElementById("filtroTipo").value.toLowerCase();

// Crie um objeto de filtro com os critérios de pesquisa
const filtro = {
nome: searchTerms[0],
numero: searchTerms[1],
email: searchTerms[2],
data: searchTerms[3],
usuarioEnumTypeEnum: selectTerm
};

// Realize a chamada AJAX com Axios
axios.post('/usuarios/filtrar', filtro)
.then(response => {
const usuariosFiltrados = response.data;
// Atualize a tabela com os resultados da pesquisa
// Implemente a lógica para atualizar a tabela aqui
})
.catch(error => {
console.error('Erro na chamada AJAX:', error);
});
}

// Adicione ouvintes de eventos aos campos de filtro e ao campo select
document.querySelectorAll(".filtroInput").forEach(input => {
input.addEventListener("input", performSearch);
});
document.getElementById("filtroTipo").addEventListener("change", performSearch);
Neste exemplo, importamos a biblioteca Axios (certifique-se de que ela esteja configurada corretamente em seu projeto) e usamos o método axios.post para enviar uma solicitação POST para o endpoint /usuarios/filtrar com o objeto filtro como corpo da solicitação. A resposta da chamada AJAX contém os resultados da pesquisa, que podem ser processados para atualizar a tabela na página.

Lembre-se de adaptar o código acima para o seu projeto específico, incluindo os URLs e endpoints corretos do seu backend Spring Boot, bem como a lógica para atualizar a tabela com os resultados da pesquisa. Certifique-se também de que a biblioteca Axios esteja configurada e incluída em seu projeto.




User
agora com fetch
ChatGPT
Claro, aqui está um exemplo de como realizar uma chamada AJAX com a API Fetch:

javascript
Copy code
// Função para realizar a chamada AJAX quando os campos de filtro ou o campo select forem alterados
function performSearch() {
const searchTerms = Array.from(document.querySelectorAll(".filtroInput")).map(input => input.value.toLowerCase());
const selectTerm = document.getElementById("filtroTipo").value.toLowerCase();

// Crie um objeto de filtro com os critérios de pesquisa
const filtro = {
nome: searchTerms[0],
numero: searchTerms[1],
email: searchTerms[2],
data: searchTerms[3],
usuarioEnumTypeEnum: selectTerm
};

// Realize a chamada AJAX com Fetch
fetch('/usuarios/filtrar', {
method: 'POST',
headers: {
'Content-Type': 'application/json'
},
body: JSON.stringify(filtro)
})
.then(response => response.json())
.then(usuariosFiltrados => {
// Atualize a tabela com os resultados da pesquisa
// Implemente a lógica para atualizar a tabela aqui
})
.catch(error => {
console.error('Erro na chamada AJAX:', error);
});
}

// Adicione ouvintes de eventos aos campos de filtro e ao campo select
document.querySelectorAll(".filtroInput").forEach(input => {
input.addEventListener("input", performSearch);
});
document.getElementById("filtroTipo").addEventListener("change", performSearch);
Neste exemplo, estamos usando a função fetch para enviar uma solicitação POST para o endpoint /usuarios/filtrar com o objeto filtro como corpo da solicitação. A resposta da chamada AJAX é processada usando os métodos response.json() e os resultados da pesquisa podem ser utilizados para atualizar a tabela na página.

Lembre-se de adaptar o código acima para o seu projeto específico, incluindo os URLs e endpoints corretos do seu backend Spring Boot, bem como a lógica para atualizar a tabela com os resultados da pesquisa. Certifique-se também de que o seu ambiente de execução suporte a API Fetch, que é amplamente suportada em navegadores modernos.



JavaScript com Fetch:
Aqui está o código JavaScript que faz uma chamada Fetch para o endpoint /usuario/filtrar:

javascript
Copy code
const searchButton = document.getElementById('searchButton');

searchButton.addEventListener('click', () => {
const nome = document.getElementById('nome').value;
const numero = document.getElementById('numero').value;
const email = document.getElementById('email').value;
const data = document.getElementById('data').value;
const usuarioEnumTypeEnum = document.getElementById('usuarioEnumTypeEnum').value;

    const filtro = {
        nome,
        numero,
        email,
        data,
        usuarioEnumTypeEnum
    };

    fetch('/usuario/filtrar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams(filtro),
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro na resposta da chamada AJAX');
        }
        return response.text(); // A resposta é uma página HTML
    })
    .then(html => {
        // Substitua o conteúdo da página atual com a nova página HTML
        document.body.innerHTML = html;
    })
    .catch(error => {
        console.error('Erro na chamada AJAX:', error);
    });
});
Este código JavaScript captura os valores dos campos de filtro, cria um objeto filtro e faz uma chamada Fetch POST com os valores como parâmetros de consulta. A resposta é uma página HTML que substitui o conteúdo da página atual.

Lembre-se de que este é um exemplo simples e pode ser necessário ajustar o código para se adequar aos detalhes da sua aplicação. Certifique-se de que os campos de filtro em sua página HTML tenham os IDs correspondentes (por exemplo, id="nome", id="numero", etc.).


