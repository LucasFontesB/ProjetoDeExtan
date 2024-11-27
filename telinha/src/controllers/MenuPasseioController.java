package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import application.Conectar_Banco_Dados;
import application.Formatar_Datas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MenuPasseioController {
	
    @FXML
    private Label nome_hospede_label;
	
    public void abrirTela(int id) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MenuPasseioController.class.getResource("/views/tela_menuPasseios.fxml"));
            Parent root = fxmlLoader.load();
            MenuPasseioController controller = fxmlLoader.getController();
            controller.Mostrar_Passeio(id);
            Stage stage = new Stage();
            stage.setTitle("Tour Manager - Gerenciador De Passeios");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception erro_ao_abrir_tela_principal) {
            erro_ao_abrir_tela_principal.printStackTrace();
        }
    }
    
    public void Mostrar_Passeio(int id) {
    	System.out.println("Procurando Passeio Para Menu...\n");
		String sql_buscar_passeio_completo = "SELECT passeios.id_passeio, passeios.nome_do_hospede, passeios.data_do_passeio, passeios.valor, tipos_passeios.descricao, passeios.data_de_registro_passeio, usuarios.nome as 'nome_usuario_registro', colaboradores.nome as 'nome_colaborador', passeios.status_passeio, passeios.id_pagamento, transacoes.comissao, transacoes.comprovante, transacoes.data_pagamento FROM passeiosJOIN transacoes ON passeios.id_pagamento = transacoes.id_transacao JOIN tipos_passeios ON tipos_passeios.id_tipo_passeio = passeios.tipo_passeio JOIN usuarios ON passeios.id_responsavel_registro_passeio = usuarios.id_usuario JOIN colaboradores ON passeios.id_colaborador_passeio = colaboradores.id_colaborador WHERE passeios.id_passeio = ?";
        PreparedStatement ps_buscar_passeio_completo = null;
        Connection conn_buscar_passeio_completo = null;
        
        try {
        	conn_buscar_passeio_completo = Conectar_Banco_Dados.getConnection();
            System.out.println("Conexão estabelecida com sucesso para Registrar Horário No Registra Turno: " + (conn_buscar_passeio_completo != null));
            ps_buscar_passeio_completo = conn_buscar_passeio_completo.prepareStatement(sql_buscar_passeio_completo);
            ps_buscar_passeio_completo.setInt(1, id);
            ResultSet resultado_passeio = ps_buscar_passeio_completo.executeQuery();
            
            if (resultado_passeio.next()) {
            	String status_convertido;
	        	int idPasseio = resultado_passeio.getInt("id_passeio");
	            String nomeHospede = resultado_passeio.getString("nome_do_hospede");
	            String dataPasseio = resultado_passeio.getString("data_do_passeio");
	            String dataPasseio_formatada = Formatar_Datas.Formatar_Para_Usuario(dataPasseio);
	            double valor = resultado_passeio.getDouble("valor");
	            String tipoPasseio = resultado_passeio.getString("descricao");
	            String dataRegistro = resultado_passeio.getString("data_de_registro_passeio");
	            String dataRegistro_formatada = Formatar_Datas.Formatar_Para_Usuario(dataRegistro);
	            String responsavel = resultado_passeio.getString("nome_usuario_registro");
	            String nome_colaborador = resultado_passeio.getString("nome_colaborador");
	            int status = resultado_passeio.getInt("status_passeio");
	            int id_pagamento = resultado_passeio.getInt("id_pagamento");
	            double comissao = resultado_passeio.getDouble("comissao");
	            String comprovante = resultado_passeio.getString("comprovante");
	            String data_pagamento = resultado_passeio.getString("data_pagamento");
	            String data_pagamento_formatada = Formatar_Datas.Formatar_Para_Usuario(data_pagamento);
	            if(status == 1) {
	            	status_convertido = "Agendado";
	            }else {
	            	status_convertido = "Não Agendado";
	            }
            }
            
            System.out.println("Horário Resgistrado Com Sucesso\n");
        }catch (Exception erro_ao_definir_usuario_logado) {
      	  erro_ao_definir_usuario_logado.printStackTrace();
        }
    }
}


