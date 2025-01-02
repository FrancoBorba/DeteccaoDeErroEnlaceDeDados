/* ***************************************************************
* Autor: Franco Ribeiro Borba
* Matricula........: 202310445
* Inicio...........: 23/08/2024
* Ultima alteracao.: 11/10/2024
* Nome.............: Principal.java
* Funcao...........: Classe para o controle das acoes na tela inicial
*************************************************************** */
package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import util.DataSingleton;

public class ControllerCapa {

   private Parent root;
  private Scene scene;
  private Stage stage;

  @FXML
  private Rectangle codificacaoBinaria , codificacaoManchester , codificacaoManchesterDiferencial; // quadrados opacos para receber a informacao do clique

    


  DataSingleton data = DataSingleton.getInstance(); // concecta as telas

  
        /*
   * ***************************************************************
   * Metodo: codificacaoBinaria
   * Funcao: informar um valor para o dataSingleton
   * Parametros: event
   * Retorno: void
   */
    @FXML
    void codificacaoBinaria(MouseEvent event) throws IOException {
      data.setOpcao(0);
      trocarTela(event);
    }

        /*
   * ***************************************************************
   * Metodo: codificacaoBinaria
   * Funcao: informar um valor para o dataSingleton
   * Parametros: event
   * Retorno: void
   */
    @FXML
    void codificacaoManchester(MouseEvent event) throws IOException {
      data.setOpcao(1);
      trocarTela(event);
    }

        /*
   * ***************************************************************
   * Metodo: codificacaoBinaria
   * Funcao: informar um valor para o dataSingleton
   * Parametros: event
   * Retorno: void
   */
    @FXML
    void codificacaoManchesterDiferencial(MouseEvent event) throws IOException {
        data.setOpcao(2);
        trocarTela(event);
    }


  

  

      /*
   * ***************************************************************
   * Metodo: trocarTela
   * Funcao: trocar a tela do menu para aplicacao
   * Parametros: event
   * Retorno: void
    * ***************************************************************
   */
    public void trocarTela(MouseEvent event) throws IOException{
    root = FXMLLoader.load(getClass().getResource("/view/telaPrincipal.fxml")); // Carrega o FXML para a aplicacao
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root); // Passa a scene para o root
    stage.setScene(scene); // passa a scene para o stage
    stage.show(); // exibe o stage

    }
  
}
