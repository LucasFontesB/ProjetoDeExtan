package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

import application.Conectar_Banco_Dados;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import controllers.LoginController;

public class TelaPrincipalController {
	static boolean saida = false;

    @FXML
    private Button botao_configuracao;

    @FXML
    private TextField barra_busca;

    @FXML
    private Button botao_pesquisar;

    @FXML
    private Button botao_sair;

    @FXML
    private Button botao_novo_passeio;
    
    @FXML
    private Label label_usuario_conectado;
    
    void Exibir_Mensagem_Usuario_Senha_Incorretos() {
    	Alert usuario_senha_incorretos = new Alert(AlertType.CONFIRMATION);
  	  	usuario_senha_incorretos.setTitle("Aviso!");
  	  	usuario_senha_incorretos.setHeaderText(null);
  	  	usuario_senha_incorretos.setContentText("Deseja Finaliza Seu Turno?");
  	  	usuario_senha_incorretos.showAndWait();
    }
    
    public static boolean Get_saida() {
    	return saida;
    }
    
    public void setNomeUsuario(String nomeUsuario) {
        label_usuario_conectado.setText(nomeUsuario);
    }

    @FXML
    void Adicionar_Novo_Passeio(ActionEvent event) {

    }

    @FXML
    void Abrir_Menu_Configuracao(ActionEvent event) {
    	int id_usuario = LoginController.Get_Id_Usuario_Logado();
    	
    	System.out.println("Verificando Se Usuario É ADM...");
    	System.out.print("\n============== LOG DE VERIFICAÇÃO DE ADM ==============\n\n");
		String sql_verificar_adm = "SELECT adm FROM usuarios WHERE id_usuario = ?";
        PreparedStatement ps_verificar_adm = null;
        Connection conn_verificar_adm = null;
        
        try {
        	conn_verificar_adm = Conectar_Banco_Dados.getConnection();
            System.out.println("\nConexão estabelecida com sucesso para Verificar Se Usuario é ADM: " + (conn_verificar_adm != null));
            ps_verificar_adm = conn_verificar_adm.prepareStatement(sql_verificar_adm);
            ps_verificar_adm.setObject(1, id_usuario);
            ResultSet resultado_consulta_adm = ps_verificar_adm.executeQuery();
            
            if(resultado_consulta_adm.next()) {
            	int isADM = resultado_consulta_adm.getInt(1);
            	if (isADM == 1) {
            		try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/tela_configs.fxml"));
                        Parent root = fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Gerenciador De Passeios");
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (Exception erro_ao_abrir_tela_principal) {
                    	erro_ao_abrir_tela_principal.printStackTrace();
                    }
            		System.out.print("O usuario: "+id_usuario+" é ADM");
            	}else {
            		Alert usuario_nao_adm = new Alert(AlertType.WARNING);
            		usuario_nao_adm.setTitle("Aviso!");
            		usuario_nao_adm.setHeaderText(null);
            		usuario_nao_adm.setContentText("O usuario não tem as permissões para acessar esta opção!");
            		usuario_nao_adm.showAndWait();
            		System.out.print("O usuario: "+id_usuario+" NÃO é ADM");
            	}
            }
           
        }catch (Exception erro_ao_definir_usuario_logado) {
      	  erro_ao_definir_usuario_logado.printStackTrace();
        }
        
        System.out.print("\n\n========== FIM DO LOG DE VERIFICAÇÃO DE ADM ===========\n\n");
    }

    @FXML
    void Sair_Conta(ActionEvent event) {
    	int id_usuario = LoginController.Get_Id_Usuario_Logado();
    	
    	LocalDateTime data_hora_atual = LocalDateTime.now();
        System.out.println("Registrando Horário De Saída No Registra Turno...\n");
		String sql_registrar_horario_registra_turno = "UPDATE registros_turnos SET horario_final = ? WHERE id_usuario = ?";
        PreparedStatement ps_registrar_horario_registra_turno = null;
        Connection conn_registrar_horario_registra_turno = null;
        
        try {
      	  conn_registrar_horario_registra_turno = Conectar_Banco_Dados.getConnection();
            System.out.println("Conexão estabelecida com sucesso para Registrar Horário No Registra Turno: " + (conn_registrar_horario_registra_turno != null));
            ps_registrar_horario_registra_turno = conn_registrar_horario_registra_turno.prepareStatement(sql_registrar_horario_registra_turno);
            ps_registrar_horario_registra_turno.setObject(1, data_hora_atual);
            ps_registrar_horario_registra_turno.setInt(2, id_usuario);
            ps_registrar_horario_registra_turno.executeUpdate();
            
            System.out.println("Horário Resgistrado Com Sucesso\n");
        }catch (Exception erro_ao_definir_usuario_logado) {
      	  erro_ao_definir_usuario_logado.printStackTrace();
        }
    	
    	System.out.println("\n\n========== LOG DE LOGOUT ==========\n");
    	
    	System.out.println("Setando usuario como 0...\n");
		String saida_usuario = "UPDATE usuarios SET logado = 0 WHERE id_usuario = ?";
        PreparedStatement ps_deslogar_usuario = null;
        Connection conn_deslogar_usuario= null;
        
        try {
      	  	conn_deslogar_usuario = Conectar_Banco_Dados.getConnection();
            System.out.println("\nConexão estabelecida com sucesso para definir o usuario como DESLOGADO (0): " + (conn_deslogar_usuario != null));
            ps_deslogar_usuario = conn_deslogar_usuario.prepareStatement(saida_usuario);
            ps_deslogar_usuario.setInt(1, id_usuario);
            ps_deslogar_usuario.executeUpdate();
        }catch (Exception erro_ao_definir_usuario_deslogado) {
            erro_ao_definir_usuario_deslogado.printStackTrace();
        }finally {
	           try {
             if (ps_deslogar_usuario != null) {
            	 ps_deslogar_usuario.close();
             }else {
            	 System.out.print("Erro Ao Tentar Fechar PreparedStatement");
             }
 
             if (conn_deslogar_usuario != null) {
                Conectar_Banco_Dados.closeConnection();
             }else {
            	 System.out.print("Erro Ao Tentar Fechar Conexão Com Banco De Dados");
             }
             
             System.out.println("Usuario Deslogado Com Sucesso\n");
             System.out.print("======= FIM DO LOG DE LOGOUT =======");
          } catch (Exception var12) {
             var12.printStackTrace();
          }}
    	
    	Stage tela_principal = (Stage) botao_sair.getScene().getWindow();
        tela_principal.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/tela_login.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Gerenciador De Passeios");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception erro_ao_abrir_tela_principal) {
        	erro_ao_abrir_tela_principal.printStackTrace();
        }
        
        
        saida = true;

    }

    @FXML
    void Pesquisar(ActionEvent event) {

    }

}
