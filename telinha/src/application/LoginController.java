package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField campo_usuario;

    @FXML
    private Button botao_login;

    @FXML
    private TextField campo_senha;

    @FXML
    void Validar_Login(ActionEvent event) {
    	String usuario = campo_usuario.getText();
    	String senha = campo_senha.getText();
    	
    	if(campo_usuario.getText().isEmpty() || campo_senha.getText().isEmpty()) {
    		if(campo_usuario.getText().isEmpty()) {
    			campo_usuario.setStyle("-fx-border-color: red");
    		}else {
    			campo_usuario.setStyle("");
    		}
    		if(campo_senha.getText().isEmpty()) {
    			campo_senha.setStyle("-fx-border-color: red");
    		}else {
    			campo_senha.setStyle("");
    		}
    	}else {
    		String sql = "SELECT EXISTS (SELECT * FROM registros WHERE usuario = ? AND senha = ?)";
            PreparedStatement ps = null;
            Connection conn = null;
      
            try {
               conn = Conectar_Banco_Dados.getConnection();
               System.out.println("Conexão estabelecida com sucesso: " + (conn != null));
               ps = conn.prepareStatement(sql);
               ps.setString(1, usuario);
               ps.setString(2, senha);
               ResultSet resultado = ps.executeQuery();
	           
	           if(resultado.next()) {
	        	   boolean resultado_pesquisa_usuario_existe = resultado.getBoolean(1);
	        	   if (resultado_pesquisa_usuario_existe == true) {
	        		  System.out.print("Achou");
	        	   }else {
	        		  System.out.print("Não achado");
	        	   }
            }}catch (Exception var13) {
                var13.printStackTrace();
            }finally {
    	           try {
    		              if (ps != null) {
    		                 ps.close();
    		              }
    		  
    		              if (conn != null) {
    		                 Conectar_Banco_Dados.closeConnection();
    		              }
    		           } catch (Exception var12) {
    		              var12.printStackTrace();
    		           }}
    	}  
    }
    @FXML
    void Registrar_Usuario(ActionEvent event) {
    	try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tela_registro.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Registro de Usuário");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
