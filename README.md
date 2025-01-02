# Simulação da Camada Física e Enlace de Dados com Detecção de Erros - Redes 1

Este repositório é a continuação do projeto que simula a camada física e a camada de enlace de dados. A nova funcionalidade adicionada é a simulação de erros aleatórios nos quadros durante a transmissão, com uma taxa de erro definida pelo usuário. Além disso, foram implementados métodos de detecção de erros para garantir a integridade dos dados transmitidos. Caso seja detectado um erro, o quadro é descartado.

## Objetivo

O objetivo desta fase do trabalho é simular a introdução de erros aleatórios nos quadros transmitidos e implementar diferentes métodos de detecção de erros. A taxa de erro é definida pelo usuário e aplicada de forma aleatória nos quadros. Os métodos de detecção de erros implementados são:

- **Bit de Paridade (Par e Ímpar)**: Um bit adicional é adicionado ao final de cada quadro para verificar se o número total de bits é par ou ímpar.
- **CRC (Cyclic Redundancy Check)**: Um código de verificação é adicionado ao quadro para detectar erros de transmissão.
- **Código de Hamming**: Um código de correção de erros que permite identificar e corrigir erros simples no quadro.

Se um erro for detectado, o quadro será descartado e não será transmitido.

Este trabalho foi desenvolvido como parte da disciplina **Redes 1**, ministrada pelo **Professor Marlos Marques**.

## Funcionalidades

- **Introdução de Erros Aleatórios**: Durante a transmissão, erros aleatórios são introduzidos nos quadros com base na taxa de erro definida pelo usuário.
- **Detecção de Erros**: Implementação de três métodos para detecção de erros:
  - **Bit de Paridade**: Verifica a paridade dos bits no quadro.
  - **CRC**: Verifica a integridade dos dados utilizando o algoritmo de CRC.
  - **Código de Hamming**: Permite detectar e corrigir erros simples.
- **Descartamento de Quadros com Erro**: Caso um erro seja detectado, o quadro é descartado e não é transmitido.

## Tecnologias Utilizadas

- **Java**: Linguagem de programação utilizada para implementar a simulação.
- **Estruturas de Dados**: Manipulação de vetores de bits e codificação/decodificação.
- **JavaFX**: Para a interface gráfica que simula o processo de codificação, enquadramento, erro, e detecção de erro.
- **Algoritmos de Detecção de Erros**: Implementação de bit de paridade, CRC e código de Hamming.

## Como Executar
1. Clone este repositório:
   ```bash
   git clone https://github.com/FrancoBorba/SimulacaoCamadaFisica.git
2. Abra o projeto em sua IDE de preferência.
3. Compile e execute o código principal para iniciar a simulação.


## Aprendizados
- Manipulação de bits
- Codigos de deteccao de erro
- Processo de funcionamento de uma rede com seus protocolos e camadas

## Autor
**Franco Ribeiro Borba**
- **Curso**: Ciência da Computação, 4º semestre
- **Instituição**: UESB (Universidade Estadual do Sudoeste da Bahia)
- **Professor Orientador**: Marlos Marques

## Licença
Este projeto é licenciado sob a Licença MIT. Consulte o arquivo `LICENSE` para mais informações.

## Contato
- [LinkedIn](https://www.linkedin.com/in/franco-borba-37462825b/)
- Email: franco.borba14@gmail.com
