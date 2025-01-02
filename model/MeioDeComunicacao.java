/* ***************************************************************
* Autor: Franco Ribeiro Borba
* Matricula........: 202310445
* Inicio...........: 28/08/2024
* Ultima alteracao.: 28/08/2024
* Nome.............: MeioDeComunicacao
* Funcao...........: Enviar o sinal de onda relativo ao escolhido na GUI 
*************************************************************** */
package model;

import java.util.Random;
import controller.ControllerAplicacao;


public class MeioDeComunicacao {

   public static void meioDeComunicacao(int fluxoBrutoDeBits[]) {
    int fluxoBrutoDeBitsPontoA[], fluxoBrutoDeBitsPontoB[];
    int taxaDeErro = ControllerAplicacao.getValorDoErro();
    Random random = new Random();

    fluxoBrutoDeBitsPontoA = fluxoBrutoDeBits;
    fluxoBrutoDeBitsPontoB = new int[fluxoBrutoDeBitsPontoA.length];

    int totalErros = 0; // Contador de erros
    System.out.println("Taxa de erros: " + taxaDeErro);

    for (int i = 0; i < fluxoBrutoDeBitsPontoA.length; i++) {
        int quadro = fluxoBrutoDeBitsPontoA[i];

        // Verifica se o quadro deve ter erro introduzido
        if (random.nextInt(100) < taxaDeErro) {
            System.out.println("Erro introduzido no quadro " + i);
            totalErros++; // Incrementa o contador de erros

            // Gera uma posicao de bit aleatoria dentro do quadro de 32 bits
            int posicaoBitNoQuadro = random.nextInt(32);

            // Inverte o bit na posicao calculada
            quadro ^= (1 << posicaoBitNoQuadro);
        }

        fluxoBrutoDeBitsPontoB[i] = quadro; // Armazena o quadro (com erro) no fluxo de saida
    }

    System.out.println("Total de erros introduzidos: " + totalErros);
    CamadaFisicaReceptora.camadaFisicaReceptora(fluxoBrutoDeBitsPontoB);
}
}


