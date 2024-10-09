package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuConfigController {

    @FXML
    private Button botao_adicionar_usuario;

    @FXML
    private Button botao_adicionar_colaborador;

    @FXML
    private Button botao_relatorio;

    @FXML
    void Adicionar_Colaborador(ActionEvent event) {
    	try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/tela_adicionarColaborador.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Adicionar Usuarios");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception erro_ao_abrir_tela_principal) {
        	erro_ao_abrir_tela_principal.printStackTrace();
        }
    }

    @FXML
    void Abrir_Menu_Adicionar_Usuario(ActionEvent event) {
    	try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/tela_adicionarUsuario.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Adicionar Usuarios");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception erro_ao_abrir_tela_principal) {
        	erro_ao_abrir_tela_principal.printStackTrace();
        }
    }

    @FXML
    void Abrir_Menu_Relatorios(ActionEvent event) {

    }

}
