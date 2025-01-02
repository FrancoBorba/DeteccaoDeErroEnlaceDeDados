/* ***************************************************************
* Autor: Franco Ribeiro Borba
* Matricula........: 202310445
* Inicio...........: 19/09/2024
* Ultima alteracao.: 11/10/2024
* Nome.............: CamadaEnlaceDadosReceptora
* Funcao...........: Organiza o fluxo bruto de bits em quadros para em caso de erro ter-se menos prejuizo
*************************************************************** */
package model;


import util.DataSingleton;

public class CamadaEnlaceDadosReceptora {

  private static DataSingleton data = DataSingleton.getInstance(); // transmite informacao entre as telas

  public static void camadaEnlaceDadosReceptora(int quadro[]){
    int quadroDesenquadrado[];
     quadroDesenquadrado = camadaEnlaceDadosReceptoraControleDeErro(quadro);
    quadroDesenquadrado = camadaEnlaceDadosReceptoraEnquadramento(quadroDesenquadrado);
  
    CamadaAplicacaoReceptora.camadaDeAplicacaoReceptora(quadroDesenquadrado);

  }
  public static int[] camadaEnlaceDadosReceptoraEnquadramento(int quadro[]){
     int quadroEnquadrado[];
   switch (data.getEnquadramento()) {
    case 0:{
      quadroEnquadrado = camadaEnlaceDadosReceptoraEnquadramentoContagemDeCaracteres(quadro);
     // CamadaAplicacaoReceptora.camadaDeAplicacaoReceptora(quadroEnquadrado);
     return quadroEnquadrado;
   
    }
    case 1:{
      System.out.println("Desenquadramento por Bytes");
      quadroEnquadrado = camadaEnlaceDadosReceptoraEnquadramentoInsercaoDeBytes(quadro);
      System.out.println("-----------------------------------");
      //CamadaAplicacaoReceptora.camadaDeAplicacaoReceptora(quadroEnquadrado);
      return quadroEnquadrado;
      
    }
    case 2:{

      quadroEnquadrado = camadaEnlaceDadosReceptoraEnquadramentoInsercaoDeBits(quadro);
      //CamadaAplicacaoReceptora.camadaDeAplicacaoReceptora(quadroEnquadrado);
      return quadroEnquadrado;
    }

    case 3:{
      quadroEnquadrado = camadaEnlaceDadosReceptoraEnquadramentoViolacaoDaCamadaFisica(quadro);
        return quadroEnquadrado;
      //CamadaAplicacaoReceptora.camadaDeAplicacaoReceptora(quadroEnquadrado);
    }
      
     
   
    default:
      break;
   }
   return null;
  }
  public static int[] camadaEnlaceDadosReceptoraControleDeErro(int quadro[]){
       int quadroComControleDeErro[];

    switch (data.getErro()) {
      case 0:{
        System.out.println("Erro por paridade par");
        quadroComControleDeErro = camadaEnlaceDadosReceptoraControleDeErroBitDeParidadePar(quadro);
        return quadroComControleDeErro;
      }
        
      case 1:{
          System.out.println("Erro por paridade impar");
        quadroComControleDeErro = camadaEnlaceDadosReceptoraControleDeErroBitDeParidadeImpar(quadro);
        return quadroComControleDeErro;
      }

      case 2:{
          System.out.println("Erro por CRC");
        quadroComControleDeErro = camadaEnlaceDadosReceptoraControleDeErroCRC(quadro);
        return quadroComControleDeErro;
      }

      case 3:{
        System.out.println("Erro por codigo de Hamming");
        quadroComControleDeErro = camadaEnlaceDadosReceptoraControleDeErroCodigoDeHamming(quadro);
      return quadroComControleDeErro;
      }

       default:
      break;
    
    }
    return null;


  }

  public static void camadaEnlaceDadosReceptoraControleDeFluxo(int quadro[]){

  }
  
 
  public static int[] camadaEnlaceDadosReceptoraEnquadramentoContagemDeCaracteres(int quadro[]){

  int novoQuadro[] = new int[quadro.length]; // quadro para simular o desenquadramento
  int informacao = 0;
  int mascara = 1 << 31;
  int bit = 0;
  int contador = 0;
  int indiceNvQ = 0;

  for(int i = 0 ; i < quadro.length ; i++){
    informacao = 0;
    for(int j= 0 ; j < 8 ; j++){ // captura o primeiro byte para identificar quantos caracteres serao lidos
     bit = (mascara & quadro[i]) == 0 ? 0 : 1;
     informacao <<=1;
      informacao = informacao | bit;
      quadro[i] <<= 1;
 
    }
    System.out.println("O valor da informação eh: " + informacao); // valor do enquadramento
    
  

    switch (informacao){ // de acordo com o valor faz a insercao correta em um novo quadro
      
      case 49:{
        for(int k = 0 ; k < 8 ; k++){ // se o valor for 1 insere 8 vezes
          bit = (quadro[i] & mascara) == 0 ? 0 : 1; // pega o bit mais significativo
          novoQuadro[indiceNvQ] = novoQuadro[indiceNvQ] << 1 ; // abre espaco para entrar o bit
          novoQuadro[indiceNvQ] = novoQuadro[indiceNvQ] | bit; // adiciona o bit
          quadro[i] = quadro[i] << 1; // analisa o proximo bit mais significativo
          contador++;
       if(contador == 32 ){
        indiceNvQ++;
        contador = 0;
        
    }
        }
        break;
      }
      case 50:{
         for(int k = 0 ; k < 16 ; k++){
          bit = (quadro[i] & mascara) == 0 ? 0 : 1; // pega o bit mais significativo
          novoQuadro[indiceNvQ] = novoQuadro[indiceNvQ] << 1; // abre espaco para entrar o bit
          novoQuadro[indiceNvQ] = novoQuadro[indiceNvQ] | bit; // adiciona o bit
          quadro[i] = quadro[i] << 1; // analisa o proximo bit mais significativo
          contador++;
      if(contador == 32){
        indiceNvQ++;
        contador = 0;
      
    }
        }
        break;
      }
      case 51:{
      
           for(int k = 0 ; k < 24 ; k++){
          bit = (quadro[i] & mascara) == 0 ? 0 : 1; // pega o bit mais significativo
          novoQuadro[indiceNvQ] = novoQuadro[indiceNvQ] << 1; // abre espaco para entrar o bit
          novoQuadro[indiceNvQ] = novoQuadro[indiceNvQ] | bit; // adiciona o bit
          quadro[i] = quadro[i] << 1; // analisa o proximo bit mais significativo
          contador++;
          if(contador == 32){
        indiceNvQ++;
        contador = 0;
    }
   
        }
        
        break;
      }
      default:{
    
       System.out.println("ERRO NA FLAG , PERDA DO QUADRO");
    }
      
    }
    
  }

   return novoQuadro;
  }
public static int[] camadaEnlaceDadosReceptoraEnquadramentoInsercaoDeBytes(int quadro[]) {
    int novoQuadro[] = new int[quadro.length]; // Quadro para armazenar a carga util sem as flags
    int informacao = 0;
    int mascara = 1 << 31; 
    int bit = 0;
    int indiceNvQ = 0;
    int cont = 0;
    int bitsProcessados = 0;
    int flags = 0;

    boolean escape = false; // Variavel para identificar quando o asterisco (*) aparece

    for (int i = 0; i < quadro.length; i++) {

        int inteiro = quadro[i];

        // Processando os bits no quadro atual (de 32 bits)
        while (cont < 32) {

            for (int j = 0; j < 8; j++) { // Armazena os caracteres para procurar flags e escape
                bit = (inteiro & mascara) == 0 ? 0 : 1;
                informacao <<= 1;
                informacao = informacao | bit;
                inteiro <<= 1; // Desloca o quadro para a esquerda para processar o proximo bit
                cont++;
            }

            // Verifica se encontrou o escape (*)
            if (informacao == 42) {
              System.out.println("Achou o escape");
              escape = true; // Proximo 'E' sera considerado parte da carga util
            } 
            // Verifica se encontrou a flag (E), mas so se nao estiver em modo escape
            else if (informacao == 69) {
             
                if (escape) {
                    // O 'E' eh parte da carga util entao tratamos ele como dado
                    escape = false; // Desativa o modo escape
                    for (int j = 0; j < 8; j++) {
                       informacao = moverBitsEsquerda(informacao);
                        bit = (informacao & mascara) == 0 ? 0 : 1;
                        novoQuadro[indiceNvQ] <<= 1;
                        novoQuadro[indiceNvQ] = novoQuadro[indiceNvQ] | bit;
                        informacao <<= 1; // Desloca o byte para processar o proximo bit
                    }
                    bitsProcessados += 8;
                } else {
                  System.out.println("Encontrou flag");
                    flags++; // Flag 'E' encontrada
                }
            } 
            // Se for outro byte, trata como parte da carga util
            else {
                 informacao = moverBitsEsquerda(informacao);
                if (flags >= 1 && flags < 2) { // Copia da carga util entre flags
                    for (int j = 0; j < 8; j++) {
                        bit = (informacao & mascara) == 0 ? 0 : 1;
                        novoQuadro[indiceNvQ] <<= 1;
                        novoQuadro[indiceNvQ] = novoQuadro[indiceNvQ] | bit;
                        informacao <<= 1; // Desloca o byte para processar o proximo bit
                    }
                    bitsProcessados += 8;
                }
            }

            // Avanca o indice no novo quadro a cada 32 bits processados
            if (bitsProcessados == 32) {
                indiceNvQ++;
                bitsProcessados = 0; // Reinicia a contagem de bits processados
            }

            // Reseta a variavel de informacao para o prximo byte
            informacao = 0;
        }
        flags = 0;
        cont = 0; // Reseta o contador de bits por quadro
    }

    return novoQuadro; // Retorna o quadro desenquadrado, sem as flags
}



public static int[] camadaEnlaceDadosReceptoraEnquadramentoInsercaoDeBits(int[] quadro) {
    int mascara = 1 << 31; // Mascara para identificar o bit mais significativo 
    int[] novoQuadro = new int[quadro.length]; // Inicializa um novo quadro para o resultado
    int indiceNvQ = 0; // indice do novo quadro
    int bitsProcessados = 0; // Contador de bits processados
    int inteiro = 0;
    int bit = 0;
    int cont = 0;
    int informacao = 0;

    for (int i = 0; i < quadro.length; i++) {
      
        inteiro = quadro[i];
    while (cont < 32) {

      for (int j = 0; j < 8; j++) { // Armazena os caracteres para procurar a flag
                bit = (inteiro & mascara) == 0 ? 0 : 1;
                informacao <<= 1;
                informacao = informacao | bit;
                inteiro <<= 1; // Desloca o quadro para a esquerda para processar o proximo bit
                cont++;
            }

        

          if(informacao == 126){ // encontrou a flag 
          // Reseta a variavel de informacao para o proximo byte
            informacao = 0;
            // nao insere
          } else{
            int verificaCincoUm = 0;
            informacao = moverBitsEsquerda(informacao);

            for(int j = 0 ; j < 8 ; j++){
            bit = (informacao & mascara) == 0 ? 0 : 1;
            if(bit  == 1){
              verificaCincoUm++;
            }else{
              verificaCincoUm = 0; // reseta o contador se encontrar um bit 0
            }

             if(verificaCincoUm == 5){
              informacao<<=1; // descarta o bit que seria lido pois eh inutil
              cont++;
              verificaCincoUm = 0;
              
            }
                novoQuadro[indiceNvQ] <<=1;
                novoQuadro[indiceNvQ] = novoQuadro[indiceNvQ] | bit;
                informacao <<= 1; // Desloca o quadro para a esquerda para processar o proximo bit
                bitsProcessados++;

            }

            // Avanca o indice no novo quadro a cada 32 bits processados
            if (bitsProcessados == 32) {
                indiceNvQ++;
                bitsProcessados = 0; // Reinicia a contagem de bits processados
            }

            // Reseta a variavel de informacao para o proximo byte
            informacao = 0;
          }
          
    }
            cont =0;

        
    }

    // Retorna o novo quadro desenquadrado
  
    return novoQuadro;
}


  public static int[] camadaEnlaceDadosReceptoraEnquadramentoViolacaoDaCamadaFisica(int quadro []){
    int[] novoQuadro = new int [quadro.length];
    int contador = 0;
    int bit = 0;
    int mask = 1 << 31;

    for(int j = 0; j < quadro.length; j++){
      contador = 0;
      quadro[j] <<= 8;
      while(contador < 8){
        bit = (quadro[j] & mask) == 0 ? 0 : 1;
        novoQuadro[j] <<= 1;
        novoQuadro[j] = novoQuadro[j] | bit;
        quadro[j] <<= 2;
        contador++;
      }
    }
    for(int i =0; i < quadro.length; i++){
      novoQuadro[i] = moverBitsEsquerda(novoQuadro[i]);
    }
    return novoQuadro;
  }

  public static int[] camadaEnlaceDadosReceptoraControleDeErroBitDeParidadePar(int quadro []){
    int novoQuadro[] = new int[quadro.length];
    int bit = 0;
    int mascara = 1 << 31;
    int cont = 0;
    int contadorDeUm = 0;
    int byteAtual = 0;
    int indiceNvQ = 0;

    for(int i = 0 ; i < quadro.length ; i++){

      cont = 0;
      
      while (cont < 32){

          for(int j = 0 ; j < 8 ; j++){
          bit = (quadro[i] & mascara) == 0 ? 0 : 1; // Verifica se o MSB e 0 ou 1
          byteAtual <<=1;
          byteAtual = byteAtual | bit;
          if(bit == 1){
            contadorDeUm++;
          }
          quadro[i] <<=1;

          cont++;
        }

         System.out.println("Contador de um: " + contadorDeUm);
        System.out.println("Valor do byteAtual: " + byteAtual);
  

        if(contadorDeUm % 2 == 0){ // so pega o byte se nao houve erro

        novoQuadro[indiceNvQ] <<=1; 
          
        for (int j = 0; j < 7; j++) {
        bit = (byteAtual & (1 << (7 - j))) == 0 ? 0 : 1; 
        novoQuadro[indiceNvQ] = (novoQuadro[indiceNvQ] << 1) | bit;
        }
                
        }
       

      byteAtual = 0;
      contadorDeUm = 0;
       
        }
      
         
      indiceNvQ++;
      }
     
   return novoQuadro;
    }

  public static int[] camadaEnlaceDadosReceptoraControleDeErroBitDeParidadeImpar(int quadro []){
    int novoQuadro[] = new int[quadro.length];
    int bit = 0;
    int mascara = 1 << 31;
    int cont = 0;
    int contadorDeUm = 0;
    int byteAtual = 0;
    int indiceNvQ = 0;

    for(int i = 0 ; i < quadro.length ; i++){

      cont = 0;
      
      while (cont < 32){

          for(int j = 0 ; j < 8 ; j++){
          bit = (quadro[i] & mascara) == 0 ? 0 : 1; // Verifica se o MSB e 0 ou 1
          byteAtual <<=1;
          byteAtual = byteAtual | bit;
          if(bit == 1){
            contadorDeUm++;
          }
          quadro[i] <<=1;

          cont++;
        }

        System.out.println("Contador de um: " + contadorDeUm);
        System.out.println("Valor do byteAtual: " + byteAtual);
  

        if(contadorDeUm % 2 != 0){ // so pega o byte se nao houve erro

        novoQuadro[indiceNvQ] <<=1; 
          
        for (int j = 0; j < 7; j++) {
        bit = (byteAtual & (1 << (7 - j))) == 0 ? 0 : 1; 
        novoQuadro[indiceNvQ] = (novoQuadro[indiceNvQ] << 1) | bit;
        }
                
        }
       

      byteAtual = 0;
      contadorDeUm = 0;
       
        }
      
         
      indiceNvQ++;
      }
     
   return novoQuadro;
  }

 public static int[] camadaEnlaceDadosReceptoraControleDeErroCRC(int[] quadroComCRC) {
    int[] novoQuadro = new int[quadroComCRC.length / 2];
    
    for (int i = 0; i < quadroComCRC.length / 2; i++) {
        int quadro = quadroComCRC[i * 2];      // Quadro original
        int crcRecebido = quadroComCRC[i * 2 + 1];  // CRC recebido com o quadro

        int crcCalculado = calcularCRC32(quadro); // Recalcula o CRC do quadro

        if (crcCalculado == crcRecebido) {
      
            novoQuadro[i] = quadro;  // Armazena o quadro original se o CRC estiver correto
        }else{
                // Erro detectado , nao adiciona o quadro
            System.out.println("Erro de CRC detectado no quadro " + i + ". Quadro descartado.");
        }

      
    }

    // Retorna o quadro original se todos os CRCs estiverem corretos
    return novoQuadro;
}

private static int calcularCRC32(int quadro) {
    int[] CRC_TABLE = new int[256];
    int POLINOMIO = 0xEDB88320; // valor em hexidaceimal do crc32(gpt)

    // Inicializa a tabela de CRC32
    for (int i = 0; i < 256; i++) {
        int crc = i;
        for (int j = 0; j < 8; j++) {
            if ((crc & 1) != 0) {
                crc = (crc >>> 1) ^ POLINOMIO;
            } else {
                crc >>>= 1;
            }
        }
        CRC_TABLE[i] = crc;
    }
    

    int crc = 0xFFFFFFFF;  // Valor inicial do CRC (todos os bits em 1)

    // Divida o valor de 32 bits em 4 bytes
    for (int i = 0; i < 4; i++) {
        int byteAtual = (quadro >>> (8 * (3 - i))) & 0xFF;  // Extrai o byte

        int index = (crc ^ byteAtual) & 0xFF;  // XOR do byte atual com os 8 bits menos significativos do CRC
        crc = (crc >>> 8) ^ CRC_TABLE[index];  // Atualiza o CRC usando a tabela
    }


    return crc ^ 0xFFFFFFFF;  // Inverte o CRC no final
}

public static int[] camadaEnlaceDadosReceptoraControleDeErroCodigoDeHamming(int[] quadro) {
    int tamanhoTotal = quadro.length;
    int nParidade = 0;

    // Calcular o numero de bits de paridade
    while (Math.pow(2, nParidade) < tamanhoTotal + 1) {
        nParidade++;
    }

    int[] quadroOriginal = new int[tamanhoTotal - nParidade];
    // Verificar os bits de paridade
    int erro = 0;

    for (int i = 0; i < nParidade; i++) {
        int posicaoParidade = (int) Math.pow(2, i);
        int paridade = 0;

        // Verificar os bits que influenciam a posição de paridade
        for (int m = 1; m <= tamanhoTotal; m++) {
            if (((m >> i) & 1) == 1) {
                paridade ^= quadro[m - 1];
            }
        }

        // Se a paridade nao for zero, erro encontrado
        if (paridade != 0) {
            erro += posicaoParidade; // Soma as posicoes para identificar o erro
        }
    }

    // Se um erro for detectado, descartar o quadro
    if (erro != 0) {
        System.out.println("Erro detectado no quadro. Descartando...");
        // simplesmente nao adiciona
    } else {
        System.out.println("Quadro recebido sem erros.");

        // Extrair os bits de dados, ja que o quadro eh valido
        int j = 0;
        for (int i = 1; i <= tamanhoTotal; i++) {
            if ((i & (i - 1)) != 0) { //nao eh posicao de paridade
                quadroOriginal[j++] = quadro[i - 1];
            }
        }
    }

    
    return quadroOriginal; // Retorna o quadro valido sem erros
}

  
    public static int retornaBitsSignificativos(int numero) {
    int numeroDeBits = Integer.toBinaryString(numero).length();
    if (numeroDeBits <= 8) {
      numeroDeBits = 8;
    } else if (numeroDeBits <= 16) {
      numeroDeBits = 16;
    } else if (numeroDeBits <= 24) {
      numeroDeBits = 24;
    } else if (numeroDeBits <= 32) {
      numeroDeBits = 32;
    }
    return numeroDeBits;
  }

  public static int moverBitsEsquerda(int numero) {
    numero <<= (32 - retornaBitsSignificativos(numero));
    return numero;
  }





}