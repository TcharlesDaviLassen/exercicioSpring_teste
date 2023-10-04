document.addEventListener("DOMContentLoaded", () => {

  const tableRender = document.getElementById("tableRender");
  const table = document.getElementById("tabela");
  const rows = table.getElementsByTagName("tr");

  // Adicione ouvintes de eventos aos campos de filtro e ao campo select
  document.querySelectorAll(".filtroInput").forEach(input => {
    input.addEventListener("input", performSearch);
  });
  document.getElementById("filtroTipo").addEventListener("change", performSearch);

  function performSearch() {
    const searchTerms = Array.from(document.querySelectorAll(".filtroInput")).map(input => input.value.toLowerCase());
    // const selectTerm = document.getElementById("filtroTipo").value.toUpperCase();

    // Filtre a tabela principal com base nos critérios de pesquisa
    for (let i = 1; i < rows.length; i++) { // Começando em 1 para pular o cabeçalho
      const row = rows[i];
      // console.info("row => ", row)
      const cells = row.getElementsByTagName("td");
      // console.info("cells => ", cells)

      let rowContainsSearchTerm = true;

      for (let j = 0; j < cells.length; j++) {
        const cell = cells[j];
        const cellText = cell.textContent || cell.innerText;
        const searchTerm = searchTerms[j];

        // Verifica se o termo de pesquisa não está vazio e se não está presente no texto da célula.
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

    // Contar o número de linhas visíveis na tabela principal
    let visibleRowCount = 0;
    for (let i = 1; i < rows.length; i++) {
      if (rows[i].style.display !== "none") {
        visibleRowCount++;
      }
    }

    // Atualizar a exibição da tabela secundária e ocultar o thead conforme necessário
    // Atualize a exibição da tabela secundária
    if (visibleRowCount > 1) {
      updateSecondaryTable(searchTerms, visibleRowCount);
      tableRender.querySelector("thead").style.display = "";
    } else {
      updateSecondaryTable(searchTerms, 0); // Não há resultados, passe 0 para updateSecondaryTable
      tableRender.querySelector("thead").style.display = "none";
    }
  }

  function updateSecondaryTable(searchTerms, conn) {
    const nomeIsEmpty = searchTerms[0] === "" || searchTerms[0] === null;
    const emailIsEmpty = searchTerms[2] === "" || searchTerms[2] === null;

    if (nomeIsEmpty && emailIsEmpty) {
      tableRender.style.display = "none";
    } else {
      const tableSecundaria = document.createElement("table");
      tableSecundaria.classList.add("table", "table-striped", "table-bordered", "custom-table");

      // Limpar a tabela secundária
      tableRender.innerHTML = "";

      // Crie o cabeçalho da nova tabela
      const thead = document.createElement("thead");
      thead.classList.add("thead-dark", "custom-thead")

      const trRow = document.createElement("tr");
      if (conn != 0) {
        trRow.innerHTML = `
        <th>Nome</th>
        <th>Email</th>
        <th>Ações</th>
      `
      } else {
        tableRender.innerHTML = `<div><h1>Nenhum resultado encontrado</h1></div>`
      }

      thead.appendChild(trRow);

      // Adicione o cabeçalho à tabela secundária
      tableSecundaria.appendChild(thead)
      tableRender.appendChild(tableSecundaria);

      // Substitua "tabela" pelo ID da sua tabela principal
      const tabela = document.getElementById("tabela");

      // Iterar sobre as linhas da tabela principal
      const rows = tabela.querySelectorAll("tbody tr");
      rows.forEach(row => {

        const cells = row.querySelectorAll("td");
        const nome = cells[0].textContent || cells[0].innerText;
        const email = cells[2].textContent || cells[2].innerText;

        const searchTermNome = searchTerms[0].toLowerCase();
        const searchTermEmail = searchTerms[2].toLowerCase();

        if (
          (!searchTermNome || nome.toLowerCase().includes(searchTermNome)) &&
          (!searchTermEmail || email.toLowerCase().includes(searchTermEmail))
        ) {

          // Crie uma nova linha na tabela secundária
          const newRow = document.createElement("tr");

          // Crie as células para o nome e o email
          const nomeCell = document.createElement("td");
          nomeCell.textContent = nome;

          const emailCell = document.createElement("td");
          emailCell.textContent = email;

          // Adicione as células à nova linha
          newRow.appendChild(nomeCell);
          newRow.appendChild(emailCell);


          // Clone e adicione os botões à célula de ações
          const buttonsCell = row.querySelector("td.buttons");
          // const editButton = buttonsCell.querySelector(".btn-warning").cloneNode(true);

          // Criando o clone dos botões
          // Sem usar o cloneNode()
          const editButton = buttonsCell.querySelector(".btn-warning")
          // Crie um novo elemento com a mesma tag name
          var cloneDoElemento = document.createElement(editButton.tagName);
          // Copie os atributos do elemento original para o clone
          for (var i = 0; i < editButton.attributes.length; i++) {
            var atributo = editButton.attributes[i];
            cloneDoElemento.setAttribute(atributo.name, atributo.value);
          }
          // Copie o conteúdo do elemento original para o clone
          cloneDoElemento.innerHTML = editButton.innerHTML;
          // Agora você tem um clone do elemento na variável cloneDoElemento
          // Você pode fazer o que quiser com o clone, como adicioná-lo a algum lugar no documento

          const deleteButton = buttonsCell.querySelector(".btn-danger").cloneNode(true);

          // Adicione os botões à nova linha
          const actionsCell = document.createElement("td");
          actionsCell.classList.add("buttons", "secondTable");
          actionsCell.appendChild(cloneDoElemento);
          actionsCell.appendChild(deleteButton);

          newRow.appendChild(actionsCell);

          // Adicione a nova linha à tabela secundária
          tableSecundaria.appendChild(newRow)
          tableRender.appendChild(tableSecundaria);
        }
      });

      // Exiba a tabela secundária
      tableRender.style.display = "table";
    }
  }


  // function updateSecondaryTable(searchTerms) {
  //   const nomeIsEmpty = searchTerms[0] === "" || searchTerms[0] === null;
  //   const emailIsEmpty = searchTerms[2] === "" || searchTerms[2] === null;

  //   if (nomeIsEmpty && emailIsEmpty) {
  //     tableRender.style.display = "none";
  //   } else {
  //     const baseEditUrl = '/usuario/edit/';
  //     const baseDeleteUrl = '/usuario/delete/';

  //     tableRender.style.display = "";

  //     // Limpar a tabela secundária
  //     tableRender.innerHTML = "";

  //     // Clonar a tabela principal
  //     const clonedTable = tabela.cloneNode(true);

  //     tableRender.style.display = "flex";
  //     tableRender.style.alignItems = "center";
  //     tableRender.style.marginLeft = "19rem"

  //     // Iterar sobre as linhas clonadas e remover as que não correspondem aos critérios de pesquisa
  //     // Crie uma nova tabela
  //     const newTable = document.createElement("table");
  //     newTable.style.width = "1rem"
  //     newTable.classList.add("table", "table-striped", "table-bordered", "custom-table", "cunstom-render");

  //     // Crie o cabeçalho da nova tabela
  //     const thead = document.createElement("thead");
  //     thead.classList.add("thead-dark", "custom-thead");
  //     const trRow = document.createElement("tr");
  //     trRow.innerHTML =
  //       `
  //         <th>Nome</th>
  //         <th>Email</th>
  //         <th>Ações</th>
  //       `;
  //     thead.appendChild(trRow);
  //     newTable.appendChild(thead);

  //     const clonedRows = clonedTable.querySelectorAll("tbody tr");
  //     clonedRows.forEach(clonedRow => {
  //       const cells = clonedRow.querySelectorAll("td");
  //       const nome = cells[0].textContent || cells[0].innerText;
  //       const email = cells[2].textContent || cells[2].innerText;
  //       const searchTermNome = searchTerms[0];
  //       const searchTermEmail = searchTerms[2];

  //       // Clonar os botões dentro da célula "td.buttons"
  //       const buttonsCell = clonedRow.querySelector("td.buttons");
  //       const editButton = buttonsCell.querySelector(".btn-warning");
  //       const deleteButton = buttonsCell.querySelector(".btn-danger");

  //       // // Clonar os botões
  //       const clonedEditButton = editButton.cloneNode(true);
  //       const clonedDeleteButton = deleteButton.cloneNode(true);

  //       // Modificar os IDs dos botões clonados conforme necessário
  //       // const newIndex = 999; // Novo índice desejado
  //       // clonedEditButton.id = `idUserEdit${newIndex}`;
  //       // clonedDeleteButton.id = `idUserDelete${newIndex}`;newIndex

  //       // Adicionar os botões clonados de volta à célula "td.buttons"
  //       // buttonsCell.appendChild(clonedEditButton);
  //       // buttonsCell.appendChild(clonedDeleteButton);

  //       // const editLink = clonedRow.querySelector('.btn-warning');
  //       // const deleteLink = clonedRow.querySelector('.btn-danger');

  //       if (
  //         (!searchTermNome || nome.toLowerCase().includes(searchTermNome)) &&
  //         (!searchTermEmail || email.toLowerCase().includes(searchTermEmail))
  //       ) {

  //         // Clonar a linha da tabela principal
  //         const newRow = document.createElement("tr");

  //         // Criar as células para o nome e o email
  //         const nomeCell = document.createElement("td");
  //         nomeCell.textContent = nome;

  //         const emailCell = document.createElement("td");
  //         emailCell.textContent = email;

  //         // Adicionar as células de nome e email à nova linha
  //         newRow.appendChild(nomeCell);
  //         newRow.appendChild(emailCell);

  //         // Criar a primeira âncora (editar)
  //         var editarLink = document.createElement("a");
  //         editarLink.id = "idUserEdit";
  //         editarLink.setAttribute("method", "get");
  //         editarLink.className = "idUserEditClass btn btn-warning";

  //         var editarIcon = document.createElement("i");
  //         editarIcon.className = "fa fa-pencil";
  //         editarLink.appendChild(editarIcon);

  //         // Criar a segunda âncora (excluir)
  //         var excluirLink = document.createElement("a");
  //         excluirLink.id = "idUserDelete";
  //         excluirLink.setAttribute("th:method", "get");
  //         excluirLink.className = "idUserDeleteClass btn btn-danger";

  //         var excluirIcon = document.createElement("i");
  //         excluirIcon.className = "fa fa-trash";
  //         excluirLink.appendChild(excluirIcon);

  //         // Obter a célula da tabela onde os elementos serão adicionados
  //         var cell = document.querySelector(".buttons");

  //         // Adicionar as âncoras à célula
  //         cell.appendChild(editarLink);
  //         cell.appendChild(excluirLink);

  //         // // Adicionar os botões de "editar" e "excluir" à nova linha
  //         // const acoesCell = document.createElement("td");
  //         // acoesCell.classList.add("buttons");

  //         // // const elementIdUserEdit = document.getElementById('idUserEdit');
  //         // // const idUserEdit = elementIdUserEdit.getAttribute('data-idUserEdit');
  //         // // const element = document.getElementById('idUserDelete');
  //         // // const idUserDelete = element.getAttribute('data-idUserDelete');

  //         // // const cloneEdit = clonedEditButton.getAttribute("href");

  //         // const editarButton = document.createElement("td");
  //         // const editarTagA = document.createElement("a");
  //         // editarTagA.setAttribute("href", `${clonedEditButton}`);
  //         // editarButton.appendChild(editarTagA);
  //         // // const elementIdUserEdit = document.getElementById('idUserEdit');
  //         // // const idUserEdit = elementIdUserEdit.getAttribute("data-idUserEdit");
  //         // // editarButton.href = `${baseEditUrl}${idUserEdit}`;
  //         // // editarButton.href = `${clonedEditButton}`;
  //         // // editarButton.setAttribute("data-idUserEdit", idUserEdit); // Adicione o ID como atributo de dados
  //         // editarButton.classList.add("btn", "btn-warning");
  //         // // editarButton.setAttribute("class", "btn btn-warning");
  //         // // editarButton.innerHTML =
  //         // //   `
  //         // //     <i class="fa fa-pencil"></i>
  //         // // `;

  //         // // <a th: href="@{/usuario/edit/{id}(id=${idUserEdit})}"
  //         // //   method="get"
  //         // //   class="btn btn-warning">
  //         // //   <i class="fa fa-pencil"></i>
  //         // // </a>

  //         // const excluirButton = document.querySelector("td a.idUserDeleteClass");
  //         // // const element = document.getElementById('idUserDelete');
  //         // // const idUserDelete = element.getAttribute("data-idUserDelete");
  //         // // excluirButton.href = `${clonedDeleteButton.getAttribute("href")}`;
  //         // // excluirButton.setAttribute("data-idUserDelete", idUserDelete); // Adicione o ID como atributo de dados
  //         // // excluirButton.classList.add("btn", "btn-danger");
  //         // excluirButton.innerHTML =
  //         //   `
  //         //     <a href="${clonedDeleteButton}"
  //         //         th:method="get"
  //         //         class="btn btn-danger">
  //         //         <i class="fa fa-trash"></i>
  //         //     </a>
  //         // `;

  //         // // <a th:href="@{/usuario/delete/{id}(id=${idUserDelete})}"
  //         // //       th:method="get"
  //         // //       class="btn btn-danger">
  //         // //       <i class="fa fa-trash"></i>
  //         // //   </a>

  //         // // Ouvinte de eventos para o botão "editar"
  //         // editarButton.addEventListener("click", function (event) {
  //         //   event.preventDefault(); // Evite que o link seja seguido
  //         //   const idUserEdit = editarButton.getAttribute("data-idUserEdit");
  //         //   // Agora, você pode usar idUserEdit como o ID correto para a edição
  //         //   console.log("ID de edição: " + idUserEdit);
  //         // });

  //         // // Ouvinte de eventos para o botão "excluir"
  //         // excluirButton.addEventListener("click", function (event) {
  //         //   event.preventDefault(); // Evite que o link seja seguido
  //         //   const idUserDelete = this.getAttribute("data-idUserDelete");
  //         //   // Agora, você pode usar idUserDelete como o ID correto para a exclusão
  //         //   console.log("ID de exclusão: " + idUserDelete);
  //         // });

  //         // acoesCell.appendChild(cell);
  //         // acoesCell.appendChild(excluirButton);
  //         newRow.appendChild(cell);

  //         // Adicione a linha clonada com botões à tabela secundária
  //         newTable.appendChild(newRow)
  //         tableRender.appendChild(newTable);
  //       }
  //     });
  //   }
  // }

  // function updateSecondaryTable(searchTerms) {
  //   const nomeIsEmpty = !searchTerms[0].trim();
  //   const emailIsEmpty = !searchTerms[2].trim();

  //   // Defina as variáveis globalmente
  //   const baseEditUrl = '/usuario/edit/';
  //   const baseDeleteUrl = '/usuario/delete/';

  //   if (nomeIsEmpty && emailIsEmpty) {
  //     tableRender.style.display = "none";
  //   } else {
  //     tableRender.style.display = "block"; // Ou qualquer outro estilo desejado

  //     // Limpar a tabela secundária
  //     tableRender.innerHTML = "";

  //     // Clonar a tabela principal
  //     const clonedTable = tabela.cloneNode(true);

  //     // Iterar sobre as linhas clonadas e remover as que não correspondem aos critérios de pesquisa
  //     const newTable = document.createElement("table");
  //     newTable.classList.add("table", "table-striped", "table-bordered", "custom-table", "cunstom-render");

  //     // Crie o cabeçalho da nova tabela
  //     const thead = document.createElement("thead");
  //     thead.classList.add("thead-dark", "custom-thead");
  //     thead.innerHTML =
  //       `
  //         <th>Nome</th>
  //         <th>Email</th>
  //         <th>Ações</th>
  //       `;
  //     newTable.appendChild(thead);

  //     const clonedRows = clonedTable.querySelectorAll("tbody tr");
  //     clonedRows.forEach(clonedRow => {
  //       const cells = clonedRow.querySelectorAll("td");
  //       const nome = cells[0].textContent || cells[0].innerText;
  //       const email = cells[1].textContent || cells[1].innerText; // Corrigido para usar cells[1]

  //       const searchTermNome = searchTerms[0].toLowerCase();
  //       const searchTermEmail = searchTerms[2].toLowerCase();

  //       if (
  //         (!searchTermNome || nome.toLowerCase().includes(searchTermNome)) &&
  //         (!searchTermEmail || email.toLowerCase().includes(searchTermEmail))
  //       ) {
  //         const elementIdUserEdit = document.getElementById('idUserEdit');
  //         const idUserEdit = elementIdUserEdit.getAttribute('data-idUserEdit');

  //         const element = document.getElementById('idUserDelete');
  //         const idUserDelete = element.getAttribute('data-idUserDelete');

  //         const newRow = createTableRow(nome, email, baseEditUrl + `${idUserEdit}`, baseDeleteUrl + `${idUserDelete}`);
  //         newTable.appendChild(newRow);
  //       }
  //     });

  //     // Adicione a nova tabela à tabela de renderização
  //     tableRender.appendChild(newTable);
  //   }
  // }

  // function createTableRow(nome, email, baseEditUrl, baseDeleteUrl) {
  //   const newRow = document.createElement("tr");

  //   // Criar as células para o nome e o email
  //   const nomeCell = document.createElement("td");
  //   nomeCell.textContent = nome;

  //   const emailCell = document.createElement("td");
  //   emailCell.textContent = email;

  //   // Adicionar as células de nome e email à nova linha
  //   newRow.appendChild(nomeCell);
  //   newRow.appendChild(emailCell);

  //   // Adicionar os botões de "editar" e "excluir" à nova linha
  //   const acoesCell = document.createElement("td");
  //   acoesCell.classList.add("buttons");

  //   const editarButton = document.createElement("a");
  //   editarButton.href = `${baseEditUrl}`;
  //   editarButton.innerHTML = '<i class="fa fa-pencil"></i>';

  //   const excluirButton = document.createElement("a");
  //   excluirButton.href = `${baseDeleteUrl}`;
  //   excluirButton.innerHTML = '<i class="fa fa-trash"></i>';

  //   acoesCell.appendChild(editarButton);
  //   acoesCell.appendChild(excluirButton);
  //   newRow.appendChild(acoesCell);

  //   return newRow;
  // }

  // Atualize a exibição da tabela secundária quando a página é carregada
  const initialSearchTerms = Array.from(document.querySelectorAll(".filtroInput")).map(input => input.value.toLowerCase());
  updateSecondaryTable(initialSearchTerms);
});








// // Funciona se descometar
// document.addEventListener("DOMContentLoaded", () => {

//   const tableRender = document.getElementById("tableRender");
//   const table = document.getElementById("tabela");
//   const rows = table.getElementsByTagName("tr");
//   const bodyTable = document.getElementById("bodyTable");

//   // Adicione ouvintes de eventos aos campos de filtro e ao campo select
//   document.querySelectorAll(".filtroInput").forEach(input => {
//     input.addEventListener("input", performSearch);
//   });
//   document.getElementById("filtroTipo").addEventListener("change", performSearch);


//   function performSearch() {
//     const searchTerms = Array.from(document.querySelectorAll(".filtroInput")).map(input => input.value.toLowerCase());
//     const selectTerm = document.getElementById("filtroTipo").value.toUpperCase();

//     // Filtre a tabela principal com base nos critérios de pesquisa
//     for (let i = 1; i < rows.length; i++) { // Começando em 1 para pular o cabeçalho
//       const row = rows[i];
//       const cells = row.getElementsByTagName("td");
//       let rowContainsSearchTerm = true;

//       for (let j = 0; j < cells.length; j++) {
//         const cell = cells[j];
//         const cellText = cell.textContent || cell.innerText;
//         const searchTerm = searchTerms[j];

//         if (searchTerm && cellText.toLowerCase().indexOf(searchTerm) === -1) {
//           rowContainsSearchTerm = false;
//           break;
//         }
//       }

//       if (rowContainsSearchTerm) {
//         row.style.display = "";
//       } else {
//         row.style.display = "none";
//       }
//     }

//     // Atualize a exibição da tabela secundária
//     updateSecondaryTable(searchTerms);
//   }

//   // function updateSecondaryTable(searchTerms) {

//   //   // Verifique se o campo de pesquisa de nome ou email está vazio
//   //   const nomeIsEmpty = searchTerms[0] === "" || searchTerms[0] === null;
//   //   const emailIsEmpty = searchTerms[2] === "" || searchTerms[2] === null;

//   //   // Desabilite a tabela secundária se nenhum nome ou email estiver sendo pesquisado
//   //   if (nomeIsEmpty && emailIsEmpty) {
//   //     tableRender.style.display = "none";
//   //   } else {
//   //     tableRender.style.display = ""; // Reabilite a tabela secundária

//   //     // Preencha a tabela secundária com base nos critérios de pesquisa
//   //     const filteredRows = Array.from(bodyTable.getElementsByTagName("tr")).filter(row => {
//   //       console.info("row => ", row)

//   //       const cells = row.getElementsByTagName("td");
//   //       const nome = cells[0].textContent || cells[0].innerText;
//   //       const email = cells[2].textContent || cells[2].innerText;

//   //       const searchTermNome = searchTerms[0];
//   //       const searchTermEmail = searchTerms[2];

//   //       if ((!searchTermNome || nome.toLowerCase().includes(searchTermNome)) &&
//   //         (!searchTermEmail || email.toLowerCase().includes(searchTermEmail))) {
//   //         return true;
//   //       }
//   //       return false;
//   //     });

//   //     console.info("filteredRows => ", filteredRows)

//   //     // Limpe a tabela secundária e crie uma tabela Bootstrap
//   //     tableRender.innerHTML =
//   //       `
//   //         <table class="table table-striped table-bordered custom-table">
//   //             <thead class="thead-dark custom-thead">
//   //                 <tr class="custom-thead">
//   //                     <th>Nome</th>
//   //                     <th>Email</th>
//   //                     <th>Ações</th>
//   //                 </tr>
//   //             </thead>
//   //             <tbody>
//   //             </tbody>
//   //         </table>
//   //     `;

//   //     const tbody = tableRender.querySelector("tbody");

//   //     // Adicione as linhas filtradas à tabela secundária
//   //     // filteredRows.forEach(row => {
//   //     //   console.info("row filteredRows => ", row)
//   //     //   const cells = row.getElementsByTagName("td");
//   //     //   const nome = cells[0].textContent || cells[0].innerText;
//   //     //   const email = cells[2].textContent || cells[2].innerText;
//   //     //   // Extrair os botões de "editar" e "excluir"
//   //     //   // const buttons = cells[5].getElementsByTagName("a");
//   //     //   // const editarButton = buttons[0];
//   //     //   // const excluirButton = buttons[1];

//   //     //   const newRow = document.createElement("tr");
//   //     //   newRow.innerHTML =
//   //     //   `
//   //     //     <td>${nome}</td>
//   //     //     <td>${email}</td>
//   //     //   `;
//   //     //   tbody.appendChild(newRow);
//   //     // });


//   //     filteredRows.forEach(row => {
//   //       const cells = row.getElementsByTagName("td");
//   //       const nome = cells[0].textContent || cells[0].innerText;
//   //       const email = cells[2].textContent || cells[2].innerText;

//   //       // Crie uma nova linha para cada registro
//   //       const newRow = document.createElement("tr");

//   //       // Crie as células para o nome e o email
//   //       const nomeCell = document.createElement("td");
//   //       nomeCell.textContent = nome;
//   //       const emailCell = document.createElement("td");
//   //       emailCell.textContent = email;

//   //       // Crie a célula de ação com os botões
//   //       const acoesCell = document.createElement("td");

//   //       // Crie o botão de "editar"
//   //       const editarButton = document.createElement("button");
//   //       editarButton.classList.add("btn", "btn-warning");
//   //       editarButton.innerHTML = '<i class="fa fa-pencil"></i>';
//   //       // Adicione um ouvinte de evento ou link para a ação de edição, se necessário

//   //       // Crie o botão de "excluir"
//   //       const excluirButton = document.createElement("button");
//   //       excluirButton.classList.add("btn", "btn-danger");
//   //       excluirButton.innerHTML = '<i class="fa fa-trash"></i>';
//   //       // Adicione um ouvinte de evento ou link para a ação de exclusão, se necessário

//   //       // Adicione os botões à célula de ação
//   //       acoesCell.appendChild(editarButton);
//   //       acoesCell.appendChild(excluirButton);

//   //       // Adicione as células à nova linha
//   //       newRow.appendChild(nomeCell);
//   //       newRow.appendChild(emailCell);
//   //       newRow.appendChild(acoesCell);

//   //       // Adicione a nova linha à tabela secundária
//   //       tbody.appendChild(newRow);
//   //     });

//   //   }
//   // }


//   function updateSecondaryTable(searchTerms) {
//     const nomeIsEmpty = searchTerms[0] === "" || searchTerms[0] === null;
//     const emailIsEmpty = searchTerms[2] === "" || searchTerms[2] === null;

//     if (nomeIsEmpty && emailIsEmpty) {
//       tableRender.style.display = "none";
//     } else {
//       tableRender.style.display = "";

//       // Limpar a tabela secundária
//       tableRender.innerHTML = "";

//       // Clonar a tabela principal
//       const clonedTable = tabela.cloneNode(true);

//       // Iterar sobre as linhas clonadas e remover as que não correspondem aos critérios de pesquisa
//       // Crie uma nova tabela
//       const newTable = document.createElement("table");
//       newTable.style.width = "1rem"
//       newTable.classList.add("table", "table-striped", "table-bordered", "custom-table", "cunstom-render");

//       // Crie o cabeçalho da nova tabela
//       const thead = document.createElement("thead");
//       thead.classList.add("thead-dark", "custom-thead");
//       const headerRow = document.createElement("tr");
//       headerRow.innerHTML =
//         `
//           <th>Nome</th>
//           <th>Email</th>
//           <th>Ações</th>
//         `;
//       thead.appendChild(headerRow);
//       newTable.appendChild(thead);

//       const clonedRows = clonedTable.querySelectorAll("tbody tr");
//       clonedRows.forEach(clonedRow => {
//         const cells = clonedRow.querySelectorAll("td");
//         const nome = cells[0].textContent || cells[0].innerText;
//         const email = cells[2].textContent || cells[2].innerText;
//         const searchTermNome = searchTerms[0];
//         const searchTermEmail = searchTerms[2];

//         if (
//           (!searchTermNome || nome.toLowerCase().includes(searchTermNome)) &&
//           (!searchTermEmail || email.toLowerCase().includes(searchTermEmail))
//         ) {

//           // Clonar a linha da tabela principal
//           const newRow = document.createElement("tr");

//           // Criar as células para o nome e o email
//           const nomeCell = document.createElement("td");
//           nomeCell.textContent = nome;

//           const emailCell = document.createElement("td");
//           emailCell.textContent = email;

//           // Adicionar as células de nome e email à nova linha
//           newRow.appendChild(nomeCell);
//           newRow.appendChild(emailCell);

//           // Clonar a linha da tabela principal
//           // const newRow = clonedRow.cloneNode(true);
//           // const name = newRow.querySelectorAll('td')[0];
//           // const email = newRow.querySelectorAll('td')[3];
//           // Adicionar os botões "editar" e "excluir" à célula de ações
//           // const acoesCell = newRow.querySelector("td:last-child");

//           // Adicionar os botões de "editar" e "excluir" à nova linha
//           const acoesCell = document.createElement("td");
//           acoesCell.classList.add("buttons");

//           const editarButton =  document.createElement("a");
//           // editarButton.classList.add("btn", "btn-warning");
//           editarButton.innerHTML =
//             `
//               <a th:href="@{#}"
//                   method="get"
//                   class="btn btn-warning">
//                   <i class="fa fa-pencil"></i>
//               </a>
//             `;
//           // Adicione um ouvinte de evento ou link para a ação de edição, se necessário

//           const excluirButton =  document.createElement("a");
//           // excluirButton.classList.add("btn", "btn-danger");
//           excluirButton.innerHTML =
//             `
//               <a th:href="@{#}"
//                   th:method="get"
//                   class="btn btn-danger">
//                   <i class="fa fa-trash"></i>
//               </a>
//             `;
//           // Adicione um ouvinte de evento ou link para a ação de exclusão, se necessário

//           acoesCell.appendChild(editarButton);
//           acoesCell.appendChild(excluirButton);
//           newRow.appendChild(acoesCell);

//           // Adicione a linha clonada com botões à tabela secundária
//           newTable.appendChild(newRow)
//           tableRender.appendChild(newTable);
//           //tableRender.appendChild(name);
//           //tableRender.appendChild(email);
//         }
//       });
//     }
//   }

//   // Atualize a exibição da tabela secundária quando a página é carregada
//   const initialSearchTerms = Array.from(document.querySelectorAll(".filtroInput")).map(input => input.value.toLowerCase());
//   updateSecondaryTable(initialSearchTerms);
// });





// document.addEventListener("DOMContentLoaded", () => {

//   const tableRender = document.getElementById("tableRender");
//   const table = document.getElementById("tabela");
//   const rows = table.getElementsByTagName("tr");
//   const bodyTable = document.getElementById("bodyTable");

//   // Adicione ouvintes de eventos aos campos de filtro e ao campo select
//   document.querySelectorAll(".filtroInput").forEach(input => {
//     input.addEventListener("input", performSearch);
//   });
//   document.getElementById("filtroTipo").addEventListener("change", performSearch);

//   function performSearch() {
//     const searchTerms = Array.from(document.querySelectorAll(".filtroInput")).map(input => input.value.toLowerCase());
//     const selectTerm = document.getElementById("filtroTipo").value.toUpperCase();

//     // Filtre a tabela principal com base nos critérios de pesquisa
//     for (let i = 1; i < rows.length; i++) { // Começando em 1 para pular o cabeçalho
//       const row = rows[i];
//       const cells = row.getElementsByTagName("td");
//       let rowContainsSearchTerm = true;

//       for (let j = 0; j < cells.length; j++) {
//         const cell = cells[j];
//         const cellText = cell.textContent || cell.innerText;
//         const searchTerm = searchTerms[j];

//         if (searchTerm && cellText.toLowerCase().indexOf(searchTerm) === -1) {
//           rowContainsSearchTerm = false;
//           break;
//         }
//       }

//       if (rowContainsSearchTerm) {
//         row.style.display = "";
//       } else {
//         row.style.display = "none";
//       }
//     }

//     // Atualize a exibição da tabela secundária
//     updateSecondaryTable(searchTerms);
//   }

//   function updateSecondaryTable(searchTerms) {
//     // Verifique se o campo de pesquisa de nome ou email está vazio
//     const nomeIsEmpty = searchTerms[0] === "" || searchTerms[0] === null;
//     const emailIsEmpty = searchTerms[2] === "" || searchTerms[2] === null;

//     // Desabilite a tabela secundária se nenhum nome ou email estiver sendo pesquisado
//     if (nomeIsEmpty && emailIsEmpty) {
//       tableRender.style.display = "none";
//     } else {

//       tableRender.style.display = ""; // Reabilite a tabela secundária


//       // Preencha a tabela de renderização com os resultados
//       const newTable = document.createElement("table");
//       newTable.classList.add("table", "table-striped", "table-bordered", "custom-table", "cunstom-render");

//       // Crie o cabeçalho da nova tabela
//       const thead = document.createElement("thead");
//       thead.classList.add("thead-dark", "custom-thead");
//       const headerRow = document.createElement("tr");
//       headerRow.innerHTML =
//         `
//           <th>Nome</th>
//           <th>Email</th>
//         `;
//       thead.appendChild(headerRow);
//       newTable.appendChild(thead);

//       // // Crie o corpo da nova tabela e preencha com os resultados
//       // const tbody = document.createElement("tbody");
//       // for (const usuario of dadosDeRetornoCallback) {
//       //   const row = document.createElement("tr");
//       //   const cell1 = document.createElement("td");
//       //   cell1.textContent = usuario.nome;
//       //   const cell2 = document.createElement("td");
//       //   cell2.textContent = usuario.email;
//       //   row.appendChild(cell1);
//       //   row.appendChild(cell2);
//       //   tbody.appendChild(row);
//       // }
//       // newTable.appendChild(tbody);
//       // // Limpe a tabela de renderização
//       // tableRender.innerHTML = "";
//       // // Adicione a nova tabela à área de renderização
//       // tableRender.appendChild(newTable);


//       // Preencha a tabela secundária com base nos critérios de pesquisa
//       const filteredRows = Array.from(bodyTable.getElementsByTagName("tr")).filter(row => {
//         const cells = row.getElementsByTagName("td");

//         for (let j = 0; j < cells.length; j++) {
//           const cell = cells[j];
//           const cellText = cell.textContent || cell.innerText;
//           const searchTerm = searchTerms[j];

//           if (searchTerm && cellText.toLowerCase().indexOf(searchTerm) === -1) {
//             return false;
//           }
//         }
//         return true;
//       });

//       // Limpe a tabela secundária
//       tableRender.innerHTML = "";

//       // Adicione as linhas filtradas à tabela secundária
//       filteredRows.forEach(row => {
//         const clonedRow = row.cloneNode(true);
//         tableRender.appendChild(clonedRow);
//       });
//     }
//   }

//   // Atualize a exibição da tabela secundária quando a página é carregada
//   const initialSearchTerms = Array.from(document.querySelectorAll(".filtroInput")).map(input => input.value.toLowerCase());
//   updateSecondaryTable(initialSearchTerms);
// });




// document.addEventListener("DOMContentLoaded", () => {

//   const tableRender = document.getElementById("tableRender");
//   const table = document.getElementById("tabela");
//   const rows = table.getElementsByTagName("tr");

//   // Adicione ouvintes de eventos aos campos de filtro e ao campo select
//   document.querySelectorAll(".filtroInput").forEach(input => {
//     input.addEventListener("input", performSearch);
//   });
//   document.getElementById("filtroTipo").addEventListener("change", performSearch);

//   function performSearch() {
//     const searchTerms = Array.from(document.querySelectorAll(".filtroInput")).map(input => input.value.toLowerCase());
//     const selectTerm = document.getElementById("filtroTipo").value.toUpperCase();

//     // Filtre a tabela principal com base nos critérios de pesquisa
//     for (let i = 1; i < rows.length; i++) { // Começando em 1 para pular o cabeçalho
//       const row = rows[i];
//       const cells = row.getElementsByTagName("td");
//       let rowContainsSearchTerm = true;

//       for (let j = 0; j < cells.length; j++) {
//         const cell = cells[j];
//         const cellText = cell.textContent || cell.innerText;
//         const searchTerm = searchTerms[j];

//         if (searchTerm && cellText.toLowerCase().indexOf(searchTerm) === -1) {
//           rowContainsSearchTerm = false;
//           break;
//         }
//       }

//       if (rowContainsSearchTerm) {
//         row.style.display = "";
//       } else {
//         row.style.display = "none";
//       }
//     }

//     // Atualize a exibição da tabela secundária
//     updateSecondaryTable(searchTerms);
//   }

//   function updateSecondaryTable(searchTerms) {
//     // Verifique se o campo de pesquisa de nome ou email está vazio
//     const nomeIsEmpty = searchTerms[0] === "" || searchTerms[0] === null;
//     const emailIsEmpty = searchTerms[2] === "" || searchTerms[2] === null;

//     // Desabilite a tabela secundária se nenhum nome ou email estiver sendo pesquisado
//     if (nomeIsEmpty && emailIsEmpty) {
//       tableRender.style.display = "none";
//     } else {
//       tableRender.style.display = ""; // Reabilite a tabela secundária
//     }
//   }

//   // Atualize a exibição da tabela secundária quando a página é carregada
//   const initialSearchTerms = Array.from(document.querySelectorAll(".filtroInput")).map(input => input.value.toLowerCase());
//   updateSecondaryTable(initialSearchTerms);
// });




// document.addEventListener("DOMContentLoaded", () => {
//   const tableRender = document.getElementById("tableRender");
//   const table = document.getElementById("tabela");
//   const rows = table.getElementsByTagName("tr");

//   // Adicione ouvintes de eventos aos campos de filtro e ao campo select
//   document.querySelectorAll(".filtroInput").forEach(input => {
//     input.addEventListener("input", performSearch);
//   });
//   document.getElementById("filtroTipo").addEventListener("change", performSearch);

//   function performSearch() {
//     const searchTerms = Array.from(document.querySelectorAll(".filtroInput")).map(input => input.value.toLowerCase());
//     const selectTerm = document.getElementById("filtroTipo").value.toUpperCase();

//     // Crie um objeto de filtro com os critérios de pesquisa
//     const filtro = {
//       nome: searchTerms[0] || null,
//       numero: searchTerms[1] || null,
//       email: searchTerms[2] || null,
//       data: searchTerms[3] || null,
//       usuarioEnumTypeEnum: selectTerm || null
//     };

//     // Certifique-se de que os valores não sejam undefined ou null
//     Object.keys(filtro).forEach(key => {
//       if (filtro[key] === undefined || filtro[key] === null) {
//         delete filtro[key];
//       }
//     });

//     // Realize a chamada AJAX com Fetch
//     fetch('/usuario/filtrar', {
//       method: 'POST',
//       headers: {
//         'Content-Type': 'application/json'
//       },
//       body: JSON.stringify(filtro)
//     })
//       .then(response => {
//         if (!response.ok) {
//           throw new Error('Erro na resposta da chamada AJAX');
//         }
//         return response.json();
//       })
//       .then(dadosDeRetornoCallback => {
//         const rowses = dadosDeRetornoCallback.length;

//         if (rowses === 0) {
//           tableRender.innerHTML = "<p>Nenhum resultado encontrado.</p>";
//         } else {
//           // Preencha a tabela de renderização com os resultados
//           const newTable = document.createElement("table");
//           newTable.classList.add("table", "table-striped", "table-bordered", "custom-table", "cunstom-render");

//           // Crie o cabeçalho da nova tabela
//           const thead = document.createElement("thead");
//           thead.classList.add("thead-dark", "custom-thead");
//           const headerRow = document.createElement("tr");
//           headerRow.innerHTML =
//             `
//                   <th>Nome</th>
//                   <th>Email</th>
//               `;
//           thead.appendChild(headerRow);
//           newTable.appendChild(thead);

//           // Crie o corpo da nova tabela e preencha com os resultados
//           const tbody = document.createElement("tbody");
//           for (const usuario of dadosDeRetornoCallback) {
//             const row = document.createElement("tr");
//             const cell1 = document.createElement("td");
//             cell1.textContent = usuario.nome;
//             const cell2 = document.createElement("td");
//             cell2.textContent = usuario.email;
//             row.appendChild(cell1);
//             row.appendChild(cell2);
//             tbody.appendChild(row);
//           }
//           newTable.appendChild(tbody);
//           // Limpe a tabela de renderização
//           tableRender.innerHTML = "";
//           // Adicione a nova tabela à área de renderização
//           tableRender.appendChild(newTable);
//         }

//         // Atualize a exibição da tabela secundária
//         updateSecondaryTable(searchTerms);
//       })
//       .catch(error => {
//         console.error('Erro na chamada AJAX:', error);
//       });
//   }

//   function updateSecondaryTable(searchTerms) {
//     // Verifique se o campo de pesquisa de nome ou email está vazio
//     const nomeIsEmpty = searchTerms[0] === "" || searchTerms[0] === null;
//     const emailIsEmpty = searchTerms[2] === "" || searchTerms[2] === null;

//     // Desabilite a tabela secundária se nenhum nome ou email estiver sendo pesquisado
//     if (nomeIsEmpty && emailIsEmpty) {
//       tableRender.style.display = "none";
//     } else {
//       tableRender.style.display = ""; // Reabilite a tabela secundária
//     }
//   }

//   // Atualize a exibição da tabela secundária quando a página é carregada
//   const initialSearchTerms = Array.from(document.querySelectorAll(".filtroInput")).map(input => input.value.toLowerCase());
//   updateSecondaryTable(initialSearchTerms);
// });





// document.addEventListener("DOMContentLoaded", () => {
//   // Função para realizar a chamada AJAX quando os campos de filtro ou o campo select forem alterados
//   async function performSearch() {

//     const searchTerms = Array.from(document.querySelectorAll(".filtroInput")).map(input => input.value.toLowerCase());
//     const selectTerm = document.getElementById("filtroTipo").value.toUpperCase();

//     const table = document.getElementById("tabela");
//     const rows = table.getElementsByTagName("tr");

//     // Crie um objeto de filtro com os critérios de pesquisa
//     const filtro = {
//       nome: searchTerms[0] || null,
//       numero: searchTerms[1] || null,
//       email: searchTerms[2] || null,
//       data: searchTerms[3] || null,
//       usuarioEnumTypeEnum: selectTerm || null
//     };

//     if (selectTerm === null || selectTerm === "") {
//       // tableRender.innerHTML = "<p>Nenhum resultado encontrado.</p>";
//       if (filtro.nome === "" || filtro.nome === null) {
//         const tableRender = document.getElementById("tableRender");
//         tableRender.style.display = "none";
//       }
//     }
//     // Certifique-se de que os valores não sejam undefined ou null
//     Object.keys(filtro).forEach(key => {
//       if (filtro[key] === undefined || filtro[key] === null) {
//         delete filtro[key];
//       }
//     });

//     // Realize a chamada AJAX com Fetch
//     fetch('/usuario/filtrar', {
//       method: 'POST',
//       headers: {
//         'Content-Type': 'application/json'
//       },
//       body: JSON.stringify(filtro)
//     })
//       .then(response => {
//         if (!response.ok) {
//           throw new Error('Erro na resposta da chamada AJAX');
//         }
//         return response.json();
//       })
//       .then(dadosDeRetornoCallback => {
//         console.info("call ", dadosDeRetornoCallback)

//         const tableRender = document.getElementById("tableRender");
//         const rowses = dadosDeRetornoCallback.length;
//         // Limpe a tabela de renderização e adicione a nova tabela
//         tableRender.innerHTML = "";

//         if (rowses === 0) {
//           tableRender.innerHTML = "<p>Nenhum resultado encontrado.</p>";
//           // const tableRenders = document.getElementById("tableRender");
//           // tableRenders.style.display = "none";
//         } else {

//           // Preencha a tabela de renderização com os resultados
//           // dadosDeRetornoCallback.forEach(usuario => {
//           //   const row = document.createElement("tr");
//           //   const cell1 = document.createElement("td");
//           //   const cell2 = document.createElement("td");
//           //   cell1.textContent = usuario.nome;
//           //   cell2.textContent = usuario.email;
//           //   row.appendChild(cell1);
//           //   row.appendChild(cell2);
//           //   tableRender.appendChild(row);
//           // });



//           // Limpe a tabela de renderização
//           const tableRender = document.getElementById("tableRender");
//           tableRender.innerHTML = "";

//           // Crie uma nova tabela
//           const newTable = document.createElement("table");
//           newTable.classList.add("table", "table-striped", "table-bordered", "custom-table", "cunstom-render");

//           // Crie o cabeçalho da nova tabela
//           const thead = document.createElement("thead");
//           thead.classList.add("thead-dark", "custom-thead");
//           const headerRow = document.createElement("tr");
//           headerRow.innerHTML =
//             `
//             <th>Nome</th>
//             <th>Email</th>
//           `;
//           thead.appendChild(headerRow);
//           newTable.appendChild(thead);

//           // Crie o corpo da nova tabela e preencha com os resultados
//           const tbody = document.createElement("tbody");
//           for (const usuario of dadosDeRetornoCallback) {
//             const row = document.createElement("tr");
//             const cell1 = document.createElement("td");
//             cell1.textContent = usuario.nome;
//             const cell2 = document.createElement("td");
//             cell2.textContent = usuario.email;
//             row.appendChild(cell1);
//             row.appendChild(cell2);
//             tbody.appendChild(row);
//           }
//           newTable.appendChild(tbody);
//           // Adicione a nova tabela à área de renderização
//           tableRender.appendChild(newTable);

//           // Oculte a tabela original
//           // const originalTable = document.getElementById("bodyTable");
//           // originalTable.style.display = "none";
//         }

//         for (let i = 1; i < rows.length; i++) { // Começando em 1 para pular o cabeçalho
//           const row = rows[i];
//           const cells = row.getElementsByTagName("td");
//           let rowContainsSearchTerm = true;

//           for (let j = 0; j < cells.length; j++) {
//             const cell = cells[j];
//             const cellText = cell.textContent || cell.innerText;
//             const searchTerm = searchTerms[j];

//             if (searchTerm && cellText.toLowerCase().indexOf(searchTerm) === -1) {
//               rowContainsSearchTerm = false;
//               break;
//             }
//           }

//           if (rowContainsSearchTerm) {
//             row.style.display = "";
//           } else {
//             row.style.display = "none";
//           }

//           // if ( selectTerm === "") {
//           //   const tableRenders = document.querySelector(".cunstom-render");
//           //   tableRenders.style.display = "none";
//           // }

//           //        if (rowContainsSearchTerm || selectTerm === "" || row.querySelector(".tipo").textContent === selectTerm) {
//           //        }
//         }
//       })
//       .catch(error => {
//         console.error('Erro na chamada AJAX:', error);
//       });
//   }

//   // Adicione ouvintes de eventos aos campos de filtro e ao campo select
//   document.querySelectorAll(".filtroInput").forEach(input => {
//     input.addEventListener("input", performSearch);
//     // console.info("input => ", input)
//   });
//   document.getElementById("filtroTipo").addEventListener("change", performSearch);

// });


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




