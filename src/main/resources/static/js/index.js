async function performSearch() {

  const searchTerms = Array.from(document.querySelectorAll(".filtroInput")).map(input => input.value);
  const selectTerm = document.getElementById("filtroTipo").value.toLowerCase();
  console.info("selectTerm ", selectTerm)

  const table = document.getElementById("tabela");
  const rows = table.getElementsByTagName("tr");

  // Crie um objeto de filtro com os critérios de pesquisa
const filtro = {
    nome: searchTerms[0] || null,
    numero: searchTerms[1] || null,
    email: searchTerms[2] || null,
    data: searchTerms[3] || null,
    usuarioEnumTypeEnum: selectTerm
};


// Certifique-se de que os valores não sejam undefined ou null
//Object.keys(filtro).forEach(key => {
//    if (filtro[key] === undefined || filtro[key] === null) {
//        delete filtro[key];
//    }
// });

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
        // Atualize a tabela com os resultados da pesquisa
        // Implemente a lógica para atualizar a tabela aqui

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
       console.info("USER => ", usuariosFiltrados)
    })
    .catch(error => {
       console.error('Erro na chamada AJAX:', error);
    });
}

// Adicione ouvintes de eventos aos campos de filtro e ao campo select
document.querySelectorAll(".filtroInput").forEach(input => {
  input.addEventListener("input", performSearch);
  console.info("input => ", input)
});
document.getElementById("filtroTipo").addEventListener("change", performSearch);



// Função para realizar a chamada AJAX quando os campos de filtro ou o campo select forem alterados
// Funcionalidade pelo Javascript.
// Obtém a referência para os campos de entrada de pesquisa
// const searchInputs = document.querySelectorAll(".filtroInput");

// Obtém a referência para a tabela e suas linhas
//  const table = document.getElementById("tabela");
//  const rows = table.getElementsByTagName("tr");

//  Função para realizar a pesquisa
// function performSearch() {
//   const searchTerms = Array.from(searchInputs).map(input => input.value.toLowerCase());

//  for (let i = 1; i < rows.length; i++) { // Começando em 1 para pular o cabeçalho
//    const row = rows[i];
//    const cells = row.getElementsByTagName("td");
//    let rowContainsSearchTerm = true;
//
//    for (let j = 0; j < cells.length; j++) {
//      const cell = cells[j];
//      const cellText = cell.textContent || cell.innerText;
//      const searchTerm = searchTerms[j];
//
//      if (searchTerm && cellText.toLowerCase().indexOf(searchTerm) === -1) {
//        rowContainsSearchTerm = false;
//        break;
//      }
//    }
//
//    if (rowContainsSearchTerm) {
//      row.style.display = "";
//    } else {
//      row.style.display = "none";
//    }
//  }
//}

// Adiciona um ouvinte de evento de digitação em cada campo de entrada de pesquisa
//searchInputs.forEach(input => {
//  input.addEventListener("input", performSearch);
//});
//selectInput.addEventListener("change", performSearch);




