package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;

import application.Conectar_Banco_Dados;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AdicionarTipoPasseioController {

    @FXML
    private Button botao_registrar_tipo_passeio;

    @FXML
    private TextField descricao_passeio;

    @FXML
    void Registrar_Tipo_Passeio(ActionEvent event) {
    	String descricao = descricao_passeio.getText();
    	
    	System.out.println("\nRegistrando Tipo De Passeio...\n");
		String sql_registrar_tipo_passeio = "INSERT INTO tipos_passeios (descricao) VALUES (?)";
        PreparedStatement ps_registrar_tipo_passeio = null;
        Connection conn_registrar_tipo_passeio = null;
        
        try {
        	conn_registrar_tipo_passeio = Conectar_Banco_Dados.getConnection();
            System.out.println("Conexão estabelecida com sucesso para Registrar Tipo De Passeio: " + (conn_registrar_tipo_passeio != null));
            ps_registrar_tipo_passeio = conn_registrar_tipo_passeio.prepareStatement(sql_registrar_tipo_passeio);
            ps_registrar_tipo_passeio.setString(1, descricao);
            ps_registrar_tipo_passeio.executeUpdate();
            
            System.out.println("Horário Resgistrado Com Sucesso\n");
        }catch (Exception erro_ao_definir_usuario_logado) {
      	  	erro_ao_definir_usuario_logado.printStackTrace();
        }
    	

    }

}
