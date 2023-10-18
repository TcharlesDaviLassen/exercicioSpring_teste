// // Criando uma matriz bidimensional 3x3
// var matriz = [
//     [1, 2, 3],
//     [4, 5, 6],
//     [7, 8, 9]
// ];

// let dados = [];

// // Percorrendo a matriz usando loops for
// for (var i = 0; i < matriz.length; i++) {
//     for (var j = 0; j < matriz[i].length; j++) {
//         dados.push(matriz[i][j])

//     }
// }

// console.log(dados);

// // Acessando elementos da matriz
// // console.log(matriz[0][0]); // Saída: 1
// // console.log(matriz[1][2]); // Saída: 6



function formatarMatriz(matriz, linhas, colunas) {
    for (var i = 0; i < linhas; i++) {
        var linha = '';
        for (var j = 0; j < colunas; j++) {
            linha += matriz[i][j] + '\t'; // Use '\t' para criar uma tabulação entre os elementos
        }
        console.log(linha);
    }
}

var matriz3x3 = [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
];

var matriz6x6 = [
    [1, 2, 3, 4, 5, 6],
    [7, 8, 9, 10, 11, 12],
    [13, 14, 15, 16, 17, 18],
    [19, 20, 21, 22, 23, 24],
    [25, 26, 27, 28, 29, 30],
    [31, 32, 33, 34, 35, 36]
];

console.log('Matriz 3x3:');
formatarMatriz(matriz3x3, 3, 3);

console.log('Matriz 6x6:');
formatarMatriz(matriz6x6, 6, 6);