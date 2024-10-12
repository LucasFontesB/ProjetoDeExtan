package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import application.Conectar_Banco_Dados;

public class MenuAdicionarColaborador {
	int id_usuario = LoginController.Get_Id_Usuario_Logado();
	private static final String REGEX_TELEFONE = "^\\+55\\s?\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$";
	private static final Pattern PADRAO_TELEFONE = Pattern.compile(REGEX_TELEFONE);

	public static boolean Validar_Telefone(String telefone) {
		return PADRAO_TELEFONE.matcher(telefone).matches();
	}

    @FXML
    private Button botao_registrar_colaborador;

    @FXML
    private TextField nome_colaborador_label;

    @FXML
    private TextField telefone_colaborador_label;

    @FXML
    void Registrar_Colaborador(ActionEvent event) {
    	String nome_colaborador = nome_colaborador_label.getText();
    	String telefone_colaborador = telefone_colaborador_label.getText();
    	
    	if(nome_colaborador_label.getText().isEmpty() || telefone_colaborador_label.getText().isEmpty()) {
    		if(nome_colaborador_label.getText().isEmpty()) {
    			nome_colaborador_label.setStyle("-fx-border-color: red");
    		}else {
    			nome_colaborador_label.setStyle("");
    		}
    		if(telefone_colaborador_label.getText().isEmpty()) {
    			telefone_colaborador_label.setStyle("-fx-border-color: red");
    		}else {
    			telefone_colaborador_label.setStyle("");
    		}
    	}else {
    		if(Validar_Telefone(telefone_colaborador) == true) {
    			System.out.print("\n============ LOG DE CADASTRO DE COLABORADOR ============\n");
    			
    			System.out.println("\nVerificando Se Há Colaborador registrado...\n");
      		    String sql_verificar_colaborador = "SELECT EXISTS (SELECT * FROM colaboradores WHERE nome = ?)";
                PreparedStatement ps_verificar_colaborador = null;
                Connection conn_verificar_colaborador = null;
                
                try {
                	conn_verificar_colaborador = Conectar_Banco_Dados.getConnection();
                    System.out.println("\nConexão estabelecida com sucesso para Registrar Colaborador: " + (conn_verificar_colaborador != null));
                    ps_verificar_colaborador = conn_verificar_colaborador.prepareStatement(sql_verificar_colaborador);
                    ps_verificar_colaborador.setString(1, nome_colaborador);
                    ResultSet verificacao_colaborador = ps_verificar_colaborador.executeQuery();
                    
                    if(verificacao_colaborador.next()) {
                    	int colaborador_achado = verificacao_colaborador.getInt(1);
                    	
                    	if(colaborador_achado == 1) {
                    		Alert colaborador_existente = new Alert(AlertType.WARNING);
                    		colaborador_existente.setTitle("Erro!");
                    		colaborador_existente.setHeaderText(null);
                    		colaborador_existente.setContentText("Colaborador Já Registrado");
                    		colaborador_existente.showAndWait();
                    	}else {
                    		LocalDateTime data_hora_atual = LocalDateTime.now();
            	            System.out.println("\nRegistrando Colaborador No banco De Dados...\n");
                  		    String sql_registrar_colaborador = "INSERT INTO colaboradores (nome, numero_para_contato, data_de_registro, id_responsavel_registro) VALUES (?, ?, ?, ?)";
                            PreparedStatement ps_registrar_colaborador = null;
                            Connection conn_registrar_colaborador = null;
                    		
                    		try {
                          	    conn_registrar_colaborador = Conectar_Banco_Dados.getConnection();
                                System.out.println("\nConexão estabelecida com sucesso para Registrar Colaborador: " + (conn_registrar_colaborador != null));
                                ps_registrar_colaborador = conn_registrar_colaborador.prepareStatement(sql_registrar_colaborador);
                                ps_registrar_colaborador.setString(1, nome_colaborador);
                                ps_registrar_colaborador.setString(2, telefone_colaborador);
                                ps_registrar_colaborador.setObject(3, data_hora_atual);
                                ps_registrar_colaborador.setInt(4, id_usuario);
                                ps_registrar_colaborador.executeUpdate();
                                
                                System.out.println("Colaborador Resgistrado Com Sucesso\n");
                                System.out.print("========== FIM DO LOG DE CADASTRO DE COLABORADOR ===========");
                                Alert usuario_senha_incorretos = new Alert(AlertType.INFORMATION);
                          	  	usuario_senha_incorretos.setTitle("Sucesso!");
                          	  	usuario_senha_incorretos.setHeaderText(null);
                          	  	usuario_senha_incorretos.setContentText("Colaborador Registrado Com Sucesso!");
                          	  	usuario_senha_incorretos.showAndWait();
                          	  	Stage tela_principal = (Stage) botao_registrar_colaborador.getScene().getWindow();
                          	  	tela_principal.close();
                            }catch (Exception erro_ao_definir_usuario_logado) {
                          	  erro_ao_definir_usuario_logado.printStackTrace();
                            }
                    		
                    	}
                    }
                }catch (Exception erro_ao_definir_usuario_logado) {
              	  erro_ao_definir_usuario_logado.printStackTrace();
                }
    			
    			
                
                
    		}else {
    			telefone_colaborador_label.setStyle("-fx-border-color: red");
    			System.out.print("Não aceito");
    		}
    	}
    }

}
