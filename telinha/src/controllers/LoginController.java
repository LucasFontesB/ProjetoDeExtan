package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

import application.Conectar_Banco_Dados;

public class LoginController {
	static int id_usuario = 0;

    @FXML
    private Button botao_login;

    @FXML
    private TextField campo_senha;
    
    public static int Get_Id_Usuario_Logado() {
    	return id_usuario;
    }
    
    void Apertar_Botao_Login_AO_Clique() {
        campo_senha.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                botao_login.fire();
            }
        });
    }
    
    void Exibir_Mensagem_Usuario_Senha_Incorretos() {
    	Alert usuario_senha_incorretos = new Alert(AlertType.WARNING);
  	  	usuario_senha_incorretos.setTitle("Erro!");
  	  	usuario_senha_incorretos.setHeaderText(null);
  	  	usuario_senha_incorretos.setContentText("Usuario ou Senha estão incorretos!");
  	  	usuario_senha_incorretos.showAndWait();
    }

    @FXML
    void Validar_Login(ActionEvent event) {
    	System.out.println("\n\nBotão_Login_Pressionado\n");
    	if(TelaPrincipalController.Get_saida() == false){
    		System.out.println("true or false: " + TelaPrincipalController.Get_saida());
    		id_usuario = id_usuario;
    		System.out.print("Seu id de usuario (in false): " + id_usuario);
    	}else {
    		id_usuario = 0;
    		System.out.print("Seu id de usuario (in true): " + id_usuario);
    	}
    	String senha = campo_senha.getText();
    	String usuario = null;
    	
    	if(campo_senha.getText().isEmpty()) {
    		campo_senha.setStyle("-fx-border-color: red");
    	}else {
    		System.out.print("\n\n============= LOG DE LOGIN ==============\n\n");
    		String sql = "SELECT EXISTS (SELECT * FROM usuarios WHERE senha = ?)";
            PreparedStatement ps = null;
            Connection conn = null;
      
            try {
               conn = Conectar_Banco_Dados.getConnection();
               System.out.println("Conexão estabelecida com sucesso para verificar se o usuario existe: " + (conn != null));
               ps = conn.prepareStatement(sql);
               ps.setString(1, senha);
               ResultSet resultado = ps.executeQuery();
	           
	           if(resultado.next()) {
	        	   boolean resultado_pesquisa_usuario_existe = resultado.getBoolean(1);
	        	   if (resultado_pesquisa_usuario_existe == true) {
	        		  campo_senha.setStyle("");
	        		  System.out.println("Usuario Encontrado\n");
	        		  
	        		  String sql_busca_usuario = "SELECT nome FROM usuarios WHERE senha = ?";
	                  PreparedStatement ps_busca_usuario = null;
	                  Connection conn_busca_usuario = null;
	        		  try {
	        			  conn_busca_usuario = Conectar_Banco_Dados.getConnection();
	                      System.out.println("Conexão estabelecida com sucesso para pescar o nome do usuario a partir da senha: " + (conn_busca_usuario != null));
	                      ps_busca_usuario = conn_busca_usuario.prepareStatement(sql_busca_usuario);
	                      ps_busca_usuario.setString(1, senha);
	                      ResultSet retorno_usuario = ps_busca_usuario.executeQuery();
	                      
	                      if(retorno_usuario.next()) {
	                    	  usuario = retorno_usuario.getString("nome");
		                      System.out.println("Seu usuario: " + usuario);
		                      Stage tela_login = (Stage) botao_login.getScene().getWindow();
		                      tela_login.close();
		                      
		                      String consulta_id_usuario = "SELECT id_usuario FROM usuarios WHERE nome = ?";
		                      PreparedStatement ps_id_usuario = null;
		                      Connection conn_id_usuario = null;
		                      
		                      try {
		                    	  conn_id_usuario = Conectar_Banco_Dados.getConnection();
			                      System.out.println("\nConexão estabelecida com sucesso para buscar o id: " + (conn_id_usuario != null));
			                      ps_id_usuario = conn_id_usuario.prepareStatement(consulta_id_usuario);
			                      ps_id_usuario.setString(1, usuario);
			                      ResultSet id_usuario_achado = ps_id_usuario.executeQuery();
			                      
			                      if(id_usuario_achado.next()) {
			                    	  id_usuario = id_usuario_achado.getInt(1);
			                    	  System.out.println("Seu id: " + id_usuario + "\n");
			                      }else {
			                    	  System.out.println("Falha Ao Obter ID a partir do nome de usuario\n");
			                      }
			                      
		                      }catch (Exception erro_ao_buscar_id) {
		                    	  erro_ao_buscar_id.printStackTrace();
		                      }finally {
		           	           try {
		     		              if (ps_id_usuario != null) {
		     		                 ps_id_usuario.close();
		     		              }else {
		     		            	 System.out.print("Erro Ao Tentar Fechar PreparedStatement");
		     		             }
		     		  
		     		              if (conn_id_usuario != null) {
		     		                 Conectar_Banco_Dados.closeConnection();
		     		              }else {
		     		            	 System.out.print("Erro Ao Tentar Fechar Conexão Com Banco De Dados");
		     		             }
		     		           } catch (Exception var12) {
		     		              var12.printStackTrace();
		     		           }}
		                      
		                      String consulta_esta_logado = "SELECT logado FROM usuarios WHERE id_usuario = ?";
		                      PreparedStatement ps_esta_logado = null;
		                      Connection conn_esta_logado = null;

		                      try {
		                    	  conn_esta_logado = Conectar_Banco_Dados.getConnection();
			                      System.out.println("\nConexão estabelecida com sucesso para buscar se usuario está logado: " + (conn_esta_logado != null));
			                      ps_esta_logado = conn_esta_logado.prepareStatement(consulta_esta_logado);
			                      ps_esta_logado.setInt(1, id_usuario);
			                      ResultSet resultado_esta_logado = ps_esta_logado.executeQuery();
			                      
			                      if(resultado_esta_logado.next()) {
			                    	  int esta_logado = resultado_esta_logado.getInt(1);
			                    	  if(esta_logado == 1) {
			                    		  System.out.println("Usuario Logado\n");
			                    	  }else {
			                    		  System.out.println("Usuario Não logado\n");
			                    		  String definir_usuario_logado = "UPDATE usuarios SET logado = 1 WHERE id_usuario = ?";
					                      PreparedStatement ps_definir_usuario_logado = null;
					                      Connection conn_definir_usuario_logado = null;
					                      
					                      try {
					                    	  conn_definir_usuario_logado = Conectar_Banco_Dados.getConnection();
						                      System.out.println("Conexão estabelecida com sucesso para definir o usuario como logado: " + (conn_definir_usuario_logado != null));
						                      ps_definir_usuario_logado = conn_definir_usuario_logado.prepareStatement(definir_usuario_logado);
						                      ps_definir_usuario_logado.setInt(1, id_usuario);
						                      ps_definir_usuario_logado.executeUpdate();
					                      }catch (Exception erro_ao_definir_usuario_logado) {
					                    	  erro_ao_definir_usuario_logado.printStackTrace();
					                      }finally {
					           	           try {
					     		              if (ps_definir_usuario_logado != null) {
					     		                 ps_definir_usuario_logado.close();
					     		              }else {
					     		            	 System.out.print("Erro Ao Tentar Fechar PreparedStatement");
					     		             }
					     		  
					     		              if (conn_definir_usuario_logado != null) {
					     		                 Conectar_Banco_Dados.closeConnection();
					     		              }else {
					     		            	 System.out.print("Erro Ao Tentar Fechar Conexão Com Banco De Dados");
					     		             }
					     		              
					     		              LocalDateTime data_hora_atual = LocalDateTime.now();
					     		              System.out.println("\nRegistrando Horário Inicial No Registra Turno...\n");
				                    		  String sql_registrar_horario_registra_turno = "INSERT INTO registros_turnos (horario_inicio, id_usuario) VALUES (?, ?)";
						                      PreparedStatement ps_registrar_horario_registra_turno = null;
						                      Connection conn_registrar_horario_registra_turno = null;
						                      
						                      try {
						                    	  conn_registrar_horario_registra_turno = Conectar_Banco_Dados.getConnection();
							                      System.out.println("\nConexão estabelecida com sucesso para Registrar Horário No Registra Turno: " + (conn_registrar_horario_registra_turno != null));
							                      ps_registrar_horario_registra_turno = conn_registrar_horario_registra_turno.prepareStatement(sql_registrar_horario_registra_turno);
							                      ps_registrar_horario_registra_turno.setObject(1, data_hora_atual);
							                      ps_registrar_horario_registra_turno.setInt(2, id_usuario);
							                      ps_registrar_horario_registra_turno.executeUpdate();
							                      
							                      System.out.println("Horário Resgistrado Com Sucesso\n");
						                      }catch (Exception erro_ao_definir_usuario_logado) {
						                    	  erro_ao_definir_usuario_logado.printStackTrace();
						                      }
					     		              
					     		              System.out.println("Usuario Logado Com Sucesso\n");
					     		              System.out.println("========== FIM DO LOG DE LOGIN ==========\n");
					     		           } catch (Exception var12) {
					     		              var12.printStackTrace();
					     		           }}
			                    	  }
			                      }else {
			                    	  System.out.println("Nenhum Resultado Obtido Ao Tentar Buscar Se Usuario Está Logado\\n");
			                      }
			                      
		                      }catch (Exception erro_ao_consultar_se_usuario_esta_logado) {
		                    	  erro_ao_consultar_se_usuario_esta_logado.printStackTrace();
		                      }finally {
		           	           try {
		     		              if (ps_esta_logado != null) {
		     		                 ps_esta_logado.close();
		     		              }else {
		     		            	 System.out.print("Erro Ao Tentar Fechar PreparedStatement");
		     		             }
		     		  
		     		              if (conn_esta_logado != null) {
		     		                 Conectar_Banco_Dados.closeConnection();
		     		              }else {
		     		            	 System.out.print("Erro Ao Tentar Fechar Conexão Com Banco De Dados");
		     		             }
		     		           } catch (Exception var12) {
		     		              var12.printStackTrace();
		     		           }}
		                      
		                      try {
		                          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/tela_principal.fxml"));
		                          Parent root = fxmlLoader.load();
		                          TelaPrincipalController controlador = fxmlLoader.getController();
		                          controlador.setNomeUsuario(usuario);
		                          Stage stage = new Stage();
		                          stage.setTitle("Passeios");
		                          stage.setScene(new Scene(root));
		                          stage.setOnCloseRequest(e -> {
		                              e.consume();
		                              System.out.print("Olá");});
		                          
		                          stage.show();
		                      } catch (Exception e) {
		                          e.printStackTrace();
		                      }
	                      }else {
	                    	  System.out.println("Nenhum Resultado Obtido Ao Tentar Puxar O Nome De Usuario A Partir Da Senha Fornecida\n");
	                      }
	        		  }catch (Exception erro_ao_puxar_nome_usuario_apartir_senha) {
	        			  erro_ao_puxar_nome_usuario_apartir_senha.printStackTrace();
	                  }finally {
	          	           try {
	          		              if (ps_busca_usuario != null) {
	          		            	ps_busca_usuario.close();
	          		              }else {
	          		            	 System.out.print("Erro Ao Tentar Fechar PreparedStatement");
	          		             }
	          		  
	          		              if (conn_busca_usuario != null) {
	          		                 Conectar_Banco_Dados.closeConnection();
	          		              }else {
	          		            	 System.out.print("Erro Ao Tentar Fechar Conexão Com Banco De Dados");
	          		             }
	          		           } catch (Exception erro_ao_fechar_conexoes) {
	          		        	 erro_ao_fechar_conexoes.printStackTrace();
	          		           }}
	        	   }else {
	        		  campo_senha.setStyle("-fx-border-color: red");
	        		  Exibir_Mensagem_Usuario_Senha_Incorretos();
	        		  System.out.println("Senha Fornecida Não Existe No Banco De Dados!\n");
	        	   }
            }else {
            	System.out.println("Nenhum Resultado Retornado Para Busca De Usuario A Partir Da Senha Fornecida\n");
            }
	           }catch (Exception erro_ao_achar_usuario) {
            	erro_ao_achar_usuario.printStackTrace();
            }finally {
    	           try {
    		              if (ps != null) {
    		                 ps.close();
    		              }else {
    		             	 System.out.print("Erro Ao Tentar Fechar PreparedStatement");
    		              }
    		  
    		              if (conn != null) {
    		                 Conectar_Banco_Dados.closeConnection();
    		              }else {
    		             	 System.out.print("Erro Ao Tentar Fechar Conexão Com Banco De Dados");
    		              }
    		           } catch (Exception erro_ao_fechar_conexoes) {
    		        	   erro_ao_fechar_conexoes.printStackTrace();
    		           }
    	           }
    	}  
    }
    }