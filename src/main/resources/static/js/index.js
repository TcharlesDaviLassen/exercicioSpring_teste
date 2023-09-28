// Função para realizar a chamada AJAX quando os campos de filtro ou o campo select forem alterados
async function performSearch() {

  const searchTerms = Array.from(document.querySelectorAll(".filtroInput")).map(input => input.value.toLowerCase());
  const selectTerm = document.getElementById("filtroTipo").value.toUpperCase();

  const table = document.getElementById("tabela");
  const rows = table.getElementsByTagName("tr");

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
    .then(dadosDeRetornoCallback => {

      // const tableRender = document.getElementById("tableRender");
      const rowses = dadosDeRetornoCallback.length;

      if (rowses === 0) {
        tableRender.innerHTML = "<p>Nenhum resultado encontrado.</p>";
        // const tableRenders = document.getElementById("tableRender");
        // tableRenders.style.display = "none";
        if (filtro.nome === null || filtro.nome === "") {
          const tableRender = document.getElementById("tableRender");
          tableRender.style.display = "none";
        }


      } else {

        // Limpe a tabela de renderização
        const tableRender = document.getElementById("tableRender");
        tableRender.innerHTML = "";

        // Crie uma nova tabela
        const newTable = document.createElement("table");
        newTable.classList.add("table", "table-striped", "table-bordered", "custom-table", "cunstom-render");

        // Crie o cabeçalho da nova tabela
        const thead = document.createElement("thead");
        thead.classList.add("thead-dark", "custom-thead");
        const headerRow = document.createElement("tr");
        headerRow.innerHTML =
          `
          <th>Nome</th>
          <th>Email</th>
        `;
        thead.appendChild(headerRow);
        newTable.appendChild(thead);

        // Crie o corpo da nova tabela e preencha com os resultados
        const tbody = document.createElement("tbody");
        for (const usuario of dadosDeRetornoCallback) {
          const row = document.createElement("tr");
          const cell1 = document.createElement("td");
          cell1.textContent = usuario.nome;
          const cell2 = document.createElement("td");
          cell2.textContent = usuario.email;
          row.appendChild(cell1);
          row.appendChild(cell2);
          tbody.appendChild(row);
        }
        newTable.appendChild(tbody);

        // Limpe a tabela de renderização e adicione a nova tabela
        tableRender.innerHTML = "";
        // Adicione a nova tabela à área de renderização
        tableRender.appendChild(newTable);

        // Oculte a tabela original
        // const originalTable = document.getElementById("bodyTable");
        // originalTable.style.display = "none";


      }

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

        // if ( selectTerm === "") {
        //   const tableRenders = document.querySelector(".cunstom-render");
        //   tableRenders.style.display = "none";
        // }

        //        if (rowContainsSearchTerm || selectTerm === "" || row.querySelector(".tipo").textContent === selectTerm) {
        //        }
      }
    })
    .catch(error => {
      console.error('Erro na chamada AJAX:', error);
    });
}

// Adicione ouvintes de eventos aos campos de filtro e ao campo select
document.querySelectorAll(".filtroInput").forEach(input => {
  input.addEventListener("input", performSearch);
  // console.info("input => ", input)
});
document.getElementById("filtroTipo").addEventListener("change", performSearch);



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




