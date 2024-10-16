package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegistroController {

    @FXML
    private TextField nome_cad;

    @FXML
    private TextField senha_cad;

    @FXML
    private TextField conf_senha_cad;

    @FXML
    private Button botao_finalizar_registro;

    @FXML
    private TextField usuario_cad;

    @FXML
    private TextField sobrenome_cad;

    @FXML
    void Finalizar_Cadastro(ActionEvent event) {
    	String nome = nome_cad.getText();
    	String sobrenome = sobrenome_cad.getText();
    	String senha = senha_cad.getText();
    	String conf_senha = conf_senha_cad.getText();
    	String usuario = usuario_cad.getText();
    	
    	if (nome_cad.getText().isEmpty() || sobrenome_cad.getText().isEmpty() || senha_cad.getText().isEmpty() || conf_senha_cad.getText().isEmpty() || usuario_cad.getText().isEmpty()) {
    		if(nome_cad.getText().isEmpty()) {
    			nome_cad.setStyle("-fx-border-color: red");
    		}else {
    			nome_cad.setStyle("");
    		}
    		if (sobrenome_cad.getText().isEmpty()) {
    			sobrenome_cad.setStyle("-fx-border-color: red");
    		}else {
    			sobrenome_cad.setStyle("");
    		}
    		if (senha_cad.getText().isEmpty()) {
    			senha_cad.setStyle("-fx-border-color: red");
    		}else {
    			senha_cad.setStyle("");
    		}
    		if(conf_senha_cad.getText().isEmpty()) {
    			conf_senha_cad.setStyle("-fx-border-color: red");
    		}else {
    			conf_senha_cad.setStyle("");
    		}
    		if (usuario_cad.getText().isEmpty()) {
    			usuario_cad.setStyle("-fx-border-color: red");
    		}else {
    			usuario_cad.setStyle("");
    		}
    	}else {
    		String sql = "INSERT INTO registros (nome, sobrenome, usuario, senha, data_registro) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = null;
            Connection conn = null;
            try {
            	conn = Conectar_Banco_Dados.getConnection();
                System.out.println("Conexão estabelecida com sucesso: " + (conn != null));
                ps = conn.prepareStatement(sql);
                ps.setString(1, nome);
                ps.setString(2, sobrenome);
                ps.setString(3, usuario);
                ps.setString(4, senha);
        		LocalDateTime data_registro = LocalDateTime.now();
                ps.setObject(5, data_registro);
                ps.executeUpdate();
            }catch (Exception var13) {
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
    		           }
    	           }
    		  
    		   }
    	}
    	
    }
