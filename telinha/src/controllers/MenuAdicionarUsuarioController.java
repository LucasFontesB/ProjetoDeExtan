package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

import application.Conectar_Banco_Dados;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MenuAdicionarUsuarioController {

    @FXML
    private TextField nome_label;

    @FXML
    private TextField conf_senha_label;

    @FXML
    private TextField senha_label;

    @FXML
    private CheckBox checkbox_administrador;

    @FXML
    private Button botao_registrar;
    
    void Exibir_Mensagem_Nome_Existente() {
    	Alert senha_confsenha_incorreto = new Alert(AlertType.WARNING);
    	senha_confsenha_incorreto.setTitle("Erro!");
    	senha_confsenha_incorreto.setHeaderText(null);
    	senha_confsenha_incorreto.setContentText("Usuário Já Registrado");
    	senha_confsenha_incorreto.showAndWait();
    }
    
    void Exibir_Mensagem_Sucesso_Ao_Registrar() {
    	Alert senha_confsenha_incorreto = new Alert(AlertType.INFORMATION);
    	senha_confsenha_incorreto.setTitle("Sucesso");
    	senha_confsenha_incorreto.setHeaderText(null);
    	senha_confsenha_incorreto.setContentText("Usuário Registrado Com Sucesso!");
    	senha_confsenha_incorreto.showAndWait();
    }
    
    void Exibir_Mensagem_Senha_Incorreto() {
    	Alert senha_confsenha_incorreto = new Alert(AlertType.WARNING);
    	senha_confsenha_incorreto.setTitle("Erro!");
    	senha_confsenha_incorreto.setHeaderText(null);
    	senha_confsenha_incorreto.setContentText("As senhas digitadas não conferem");
    	senha_confsenha_incorreto.showAndWait();
    }
    
    void Exibir_Mensagem_Senha_Existente() {
    	Alert senha_existe = new Alert(AlertType.WARNING);
    	senha_existe.setTitle("Erro!");
  	  	senha_existe.setHeaderText(null);
  	    senha_existe.setContentText("A senha inserida já está registrada");
  	    senha_existe.showAndWait();
    }

    @FXML
    void Realizar_Registro(ActionEvent event) {
    	String usuario_logado = null;
    	String nome = nome_label.getText();
    	String senha = senha_label.getText();
    	String conf_senha = conf_senha_label.getText();
    	int adm;
    	
    	if(checkbox_administrador.isSelected()) {
    		System.out.println("CheckBox Selecionado");
    		adm = 1;
    	}else {
    		System.out.println("CheckBox Não Selecionado");
    		adm = 0;
    	}
    	
    	if (nome.isEmpty() || senha.isEmpty() || conf_senha.isEmpty()) {
    		if(nome.isEmpty()) {
    			nome_label.setStyle("-fx-border-color: red");
    		}else {
    			nome_label.setStyle("");
    		}if(senha.isEmpty()) {
    			senha_label.setStyle("-fx-border-color: red");
    		}else {
    			senha_label.setStyle("");
    		}if(conf_senha.isEmpty()) {
    			conf_senha_label.setStyle("-fx-border-color: red");
    		}else {
    			conf_senha_label.setStyle("");
    		}
    		
    	}else {
    		if(senha.equals(conf_senha)) {
    			
    			System.out.println("\n\n============ LOG DE CADASTRO DE USUARIO ============\n");
    			
    			senha_label.setStyle("");
    			conf_senha_label.setStyle("");
    			
    			String sql_verificar_senha_existente = "SELECT EXISTS (SELECT * FROM usuarios WHERE senha = ?)";
                PreparedStatement ps_verificar_senha_existente = null;
                Connection conn_verificar_senha_existente = null;
                
                try {
                	conn_verificar_senha_existente = Conectar_Banco_Dados.getConnection();
                    System.out.println("\nConexão estabelecida com sucesso para verificar se a senha existe: " + (conn_verificar_senha_existente != null));
                    ps_verificar_senha_existente = conn_verificar_senha_existente.prepareStatement(sql_verificar_senha_existente);
                    ps_verificar_senha_existente.setString(1, senha);
                    ResultSet consulta_senha_existe = ps_verificar_senha_existente.executeQuery();
                    
                    if(consulta_senha_existe.next()) {
                    	int resultado = consulta_senha_existe.getInt(1);
                    	
                    	if(resultado == 1) {
                    		System.out.println("Senha Existe\n");
                    		Exibir_Mensagem_Senha_Existente();
                    	}else {
                    		System.out.println("Senha Não Existe :)\n");
                    		
                    		String sql_verificar_nome_existente = "SELECT EXISTS (SELECT * FROM usuarios WHERE nome = ?)";
                            PreparedStatement ps_verificar_nome_existente = null;
                            Connection conn_verificar_nome_existente = null;
                            
                            try {
                            	conn_verificar_nome_existente = Conectar_Banco_Dados.getConnection();
                                System.out.println("Conexão estabelecida com sucesso para verificar se o nome existe: " + (conn_verificar_nome_existente != null));
                                ps_verificar_nome_existente = conn_verificar_nome_existente.prepareStatement(sql_verificar_nome_existente);
                                ps_verificar_nome_existente.setString(1, nome);
                                ResultSet consulta_nome_existe = ps_verificar_nome_existente.executeQuery();
                                
                                if (consulta_nome_existe.next()) {
                                	int resultado_nome = consulta_nome_existe.getInt(1);
                                	
                                	if(resultado_nome == 1) {
                                		Exibir_Mensagem_Nome_Existente();
                                		System.out.print("Usuario Já Cadastrado!");
                                	}else {
                                		System.out.println("Usuario Não Existe :)\n");
                                		LocalDateTime data_hora_atual = LocalDateTime.now();
                                		int id_usuario = LoginController.Get_Id_Usuario_Logado();
                                		
                                		System.out.println("Buscando nome de usuario pelo ID...\n");
                                		String sql_buscar_usuario = "SELECT nome FROM usuarios WHERE id_usuario = ?";
                                        PreparedStatement ps_buscar_usuario = null;
                                        Connection conn_buscar_usuario = null;
                                        
                                        try {
                                        	conn_buscar_usuario = Conectar_Banco_Dados.getConnection();
                                            System.out.println("Conexão estabelecida com sucesso para registrar usuario no banco: " + (conn_buscar_usuario != null));
                                            ps_buscar_usuario = conn_buscar_usuario.prepareStatement(sql_buscar_usuario);
                                            ps_buscar_usuario.setInt(1, id_usuario);
                                            ResultSet retorno_usuario = ps_buscar_usuario.executeQuery();
                                            
                                            if (retorno_usuario.next()) {
                                            	usuario_logado = retorno_usuario.getString(1);
                                            }
                                            
                                           
                                        }catch (Exception erro_ao_definir_usuario_logado) {
                                        	  erro_ao_definir_usuario_logado.printStackTrace();
                                        }
                                        
                                		System.out.println("Iniciando Registro No Banco...\n");
                                		String sql_registrar_banco = "INSERT INTO usuarios (nome, senha, data_de_registro, adm, usuario_registro) VALUES (?, ?, ?, ?, ?)";
                                        PreparedStatement ps_registrar_banco = null;
                                        Connection conn_registrar_banco = null;
                                        
                                        try {
                                        	conn_registrar_banco = Conectar_Banco_Dados.getConnection();
                                            System.out.println("Conexão estabelecida com sucesso para registrar usuario no banco: " + (conn_registrar_banco != null));
                                            ps_registrar_banco = conn_registrar_banco.prepareStatement(sql_registrar_banco);
                                            ps_registrar_banco.setString(1, nome);
                                            ps_registrar_banco.setString(2, senha);
                                            ps_registrar_banco.setObject(3, data_hora_atual);
                                            ps_registrar_banco.setInt(4, adm);
                                            ps_registrar_banco.setString(5, usuario_logado);
                                            ps_registrar_banco.executeUpdate();
                                            
                                            System.out.println("Usuario Registrado No Banco Com Sucesso!\n");
                                            System.out.print("======== FIM DO LOG DE CADASTRO DE USUARIO =========");
                                            Exibir_Mensagem_Sucesso_Ao_Registrar();
                               	          	Stage tela_principal = (Stage) botao_registrar.getScene().getWindow();
                                            tela_principal.close();
                                        }catch (Exception erro_ao_definir_usuario_logado) {
                                        	  erro_ao_definir_usuario_logado.printStackTrace();
                                        }finally {
                             	           try {
                        		              if (ps_registrar_banco != null) {
                        		            	  ps_registrar_banco.close();
                        		              }else {
                        		            	 System.out.print("Erro Ao Tentar Fechar PreparedStatement");
                        		             }
                        		  
                        		              if (conn_registrar_banco != null) {
                        		                 Conectar_Banco_Dados.closeConnection();
                        		              }else {
                        		            	 System.out.print("Erro Ao Tentar Fechar Conexão Com Banco De Dados");
                        		             }
                             	           }catch (Exception erro_ao_registrar_banco) {
                                           	  erro_ao_registrar_banco.printStackTrace();
                             	           }
                            		}
                                	}
                                }
                            }catch (Exception e) {
                            	e.printStackTrace();
                            }
                    		
                    	}
                    }
                }catch (Exception erro_ao_definir_usuario_logado) {
              	  erro_ao_definir_usuario_logado.printStackTrace();
                }finally {
     	           try {
		              if (ps_verificar_senha_existente != null) {
		            	  ps_verificar_senha_existente.close();
		              }else {
		            	 System.out.print("Erro Ao Tentar Fechar PreparedStatement");
		             }
		  
		              if (conn_verificar_senha_existente != null) {
		                 Conectar_Banco_Dados.closeConnection();
		              }else {
		            	 System.out.print("Erro Ao Tentar Fechar Conexão Com Banco De Dados");
		             }
     	           }catch (Exception erro_ao_definir_usuario_logado) {
                   	  erro_ao_definir_usuario_logado.printStackTrace();
     	           }
    		}
    		}else {
    			Exibir_Mensagem_Senha_Incorreto();
    			senha_label.setStyle("-fx-border-color: red");
    			conf_senha_label.setStyle("-fx-border-color: red");
    		}
    	}
}}
