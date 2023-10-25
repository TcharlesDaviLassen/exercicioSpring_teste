// var dropArea = document.getElementById('drop-area');

// // Impedir que o navegador abra o arquivo quando solto
// dropArea.addEventListener('dragover', function (e) {
//     e.preventDefault();
// });

// // Manipular o evento de soltar
// dropArea.addEventListener('drop', function (e) {
//     e.preventDefault();

//     var files = e.dataTransfer.files; // Obter os arquivos que foram soltos

//     // Faça algo com os arquivos, como fazer upload ou processá-los
//     handleDroppedFiles(files);
// });

// function handleDroppedFiles(files) {
//     for (var i = 0; i < files.length; i++) {
//         var file = files[i];
//         console.log('Nome do arquivo: ' + file.name);
//         console.log('Tipo do arquivo: ' + file.type);
//         console.log('Tamanho do arquivo: ' + file.size + ' bytes');

//         // Implemente o código para fazer upload do arquivo ou realizar outra ação
//     }
// }



// var dropArea = document.getElementById('drop-area');
// // Impedir que o navegador abra o arquivo quando solto
// dropArea.addEventListener('dragover', function (e) {
//     e.preventDefault();
// });

// // Manipular o evento de soltar
// dropArea.addEventListener('drop', function (e) {
//     e.preventDefault();

//     var files = e.dataTransfer.files;

//     // Processar os arquivos (enviar para o servidor, mostrar informações, etc.)
//     handleDroppedFiles(files);
// });

// function handleDroppedFiles(files) {
//     for (var i = 0; i < files.length; i++) {
//         var file = files[i];
//         console.log('Nome do arquivo: ' + file.name);
//         console.log('Tipo do arquivo: ' + file.type);
//         console.log('Tamanho do arquivo: ' + file.size + ' bytes');
//         // Aqui você pode implementar a lógica para fazer upload dos arquivos para o servidor ou realizar outras ações com eles.
//     }
// }


var dropArea = document.getElementById('drop-area');
var imagePreview = document.getElementById('image-preview');

// Impedir que o navegador abra o arquivo quando solto
dropArea.addEventListener('dragover', function (e) {
    e.preventDefault();
});

// Manipular o evento de soltar
dropArea.addEventListener('drop', function (e) {
    e.preventDefault();

    var files = e.dataTransfer.files;

    // Processar os arquivos (exibir imagens na tela)
    handleDroppedFiles(files);
});

function handleDroppedFiles(files) {
    for (var i = 0; i < files.length; i++) {
        var file = files[i];
        console.log('Nome do arquivo: ' + file.name);
        console.log('Tipo do arquivo: ' + file.type);
        console.log('Tamanho do arquivo: ' + file.size + ' bytes');

        // Verificar se o arquivo é uma imagem
        if (file.type.startsWith('image/')) {
            var img = new Image();
            img.src = URL.createObjectURL(file); // Cria uma URL temporária para o arquivo

            // Adicionar a imagem à área de visualização
            img.style.maxWidth = '100px';
            img.style.margin = '10px';
            imagePreview.appendChild(img);
        }
    }
}


// Arrasta e solta
// var dragSrcElement = null;

// // Função para iniciar a operação de arrastar
// function handleDragStart(e) {
//     dragSrcElement = this;
//     e.dataTransfer.effectAllowed = 'move';
//     e.dataTransfer.setData('text/html', this.innerHTML);
// }

// // Função para quando um item é solto
// function handleDrop(e) {
//     if (e.stopPropagation) {
//         e.stopPropagation(); // Evita que o navegador abra o link quando um item é solto.
//     }

//     if (dragSrcElement !== this) {
//         var items = document.querySelectorAll('#sortable-list li');
//         var dropIndex = -1;
//         for (var i = 0; i < items.length; i++) {
//             if (items[i] === this) {
//                 dropIndex = i;
//                 break;
//             }
//         }

//         var dragIndex = -1;
//         for (var i = 0; i < items.length; i++) {
//             if (items[i] === dragSrcElement) {
//                 dragIndex = i;
//                 break;
//             }
//         }

//         if (dragIndex !== -1 && dropIndex !== -1) {
//             // Inserir o item arrastado acima ou abaixo do destino
//             if (dragIndex < dropIndex) {
//                 this.parentNode.insertBefore(dragSrcElement, this.nextSibling);
//             } else {
//                 this.parentNode.insertBefore(dragSrcElement, this);
//             }
//         }
//     }

//     return false;
// }

// // Função para evitar o comportamento padrão ao arrastar
// function handleDragOver(e) {
//     if (e.preventDefault) {
//         e.preventDefault();
//     }
//     e.dataTransfer.dropEffect = 'move';
//     return false;
// }

// var items = document.querySelectorAll('#sortable-list li');
// items.forEach(function (item) {
//     item.addEventListener('dragstart', handleDragStart);
//     item.addEventListener('dragover', handleDragOver);
//     item.addEventListener('drop', handleDrop);
// });