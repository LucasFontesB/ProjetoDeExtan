package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import application.Conectar_Banco_Dados;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddPasseiosController {
	
	@FXML
    private TextField hora_passeio;
	
	@FXML
    public ComboBox<?> menu_responsavel;

    @FXML
    private TextField nome_hospede_label;
    
    @FXML
    private Button registrar_passeio_botao;
    
    @FXML
    private DatePicker data_passeio_label;
    
    @FXML
    private DatePicker data_pagamento;
    
    @FXML
    private TextField valor_passeio_label;

    @FXML
    public ComboBox<?> menu_tipo_passeio;

    @FXML
    private TextField valor_pago_label;
    
    @FXML
    private CheckBox esta_agendado;
    
    public void initialize() {
    	Set<String> tipos_passeios = new HashSet();
		ObservableList passeiosList = FXCollections.observableArrayList();
		menu_tipo_passeio.getItems().clear();
		if(menu_tipo_passeio == null) {
			System.out.print("\nComboBox Vazia\n");
		}else {
			menu_tipo_passeio.getItems().clear();
			System.out.print("\nComboBox NÃO Vazia\n");
		}
		System.out.println("\n\nBuscando por tipo de passeios registrados...\n");
		String sql_buscar_tipos_responsaveis_passeios = "SELECT tipos_passeios.descricao FROM tipos_passeios";
	    PreparedStatement ps_buscar_tipos_responsaveis_passeiosos = null;
	    Connection conn_buscar_tipos_responsaveis_passeiosos = null;
	    
	    try {
	    	conn_buscar_tipos_responsaveis_passeiosos = Conectar_Banco_Dados.getConnection();
	        System.out.println("Conexão estabelecida com sucesso para Buscar tipo de passeios registrados: " + (conn_buscar_tipos_responsaveis_passeiosos != null));
	        ps_buscar_tipos_responsaveis_passeiosos = conn_buscar_tipos_responsaveis_passeiosos.prepareStatement(sql_buscar_tipos_responsaveis_passeios);
	        ResultSet tipos_passeios_achados = ps_buscar_tipos_responsaveis_passeiosos.executeQuery();
	        
	        while(tipos_passeios_achados.next()) {
	        	String tipos_achados = tipos_passeios_achados.getString("descricao");
	        	System.out.print("\nTipos Achados: "+tipos_achados+"\n");
	            tipos_passeios.add(tipos_achados);
	        }
	        passeiosList.addAll(tipos_passeios);
	        menu_tipo_passeio.setItems(passeiosList);
	        
	        System.out.println("Tipos achados!\n");
	    }catch (Exception erro_ao_definir_usuario_logado) {
	  	  erro_ao_definir_usuario_logado.printStackTrace();
	    }finally {
	    	try {
				if (ps_buscar_tipos_responsaveis_passeiosos != null) {
	            	ps_buscar_tipos_responsaveis_passeiosos.close();
	            }else {
	           	 System.out.print("Erro Ao Tentar Fechar PreparedStatement");
	            }
	
	            if (conn_buscar_tipos_responsaveis_passeiosos != null) {
	               Conectar_Banco_Dados.closeConnection();
	            }else {
	           	 System.out.print("Erro Ao Tentar Fechar Conexão Com Banco De Dados");
	            }
	         } catch (Exception var12) {
	            var12.printStackTrace();
	         }
	    }
    
    
	    Set<String> responsaveis_passeios_list = new HashSet();
		ArrayList responsaveis_passeios = new ArrayList();
		ObservableList responsaveisList = FXCollections.observableArrayList();
		menu_responsavel.getItems().clear();
		if(menu_responsavel == null) {
			System.out.print("\nComboBox Vazia\n");
		}else {
			menu_responsavel.getItems().clear();
			System.out.print("\nComboBox NÃO Vazia\n");
		}
		System.out.println("\n\nBuscando por tipo de passeios registrados...\n");
		String sql_buscar_responsaveis_passeios = "SELECT colaboradores.nome FROM colaboradores";
	    PreparedStatement ps_buscar_responsaveis_passeiosos = null;
	    Connection conn_buscar_responsaveis_passeiosos = null;
	    
	    try {
	    	conn_buscar_tipos_responsaveis_passeiosos = Conectar_Banco_Dados.getConnection();
	        System.out.println("Conexão estabelecida com sucesso para Registrar Horário No Registra Turno: " + (conn_buscar_responsaveis_passeiosos != null));
	        ps_buscar_responsaveis_passeiosos = conn_buscar_tipos_responsaveis_passeiosos.prepareStatement(sql_buscar_responsaveis_passeios);
	        ResultSet responsaveis_achados = ps_buscar_responsaveis_passeiosos.executeQuery();
	        
	        while(responsaveis_achados.next()) {
	        	String string_responsaveis_achados = responsaveis_achados.getString("nome");
	        	System.out.print("\nTipos Achados: "+string_responsaveis_achados+"\n");
	            responsaveis_passeios.add(string_responsaveis_achados);
	        }
	        responsaveisList.addAll(responsaveis_passeios);
	        menu_responsavel.setItems(responsaveisList);
	        
	        System.out.println("Horário Resgistrado Com Sucesso\n");
	    }catch (Exception erro_ao_definir_usuario_logado) {
	  	  erro_ao_definir_usuario_logado.printStackTrace();
	    }finally {
	    	try {
				if (ps_buscar_responsaveis_passeiosos != null) {
					ps_buscar_responsaveis_passeiosos.close();
	            }else {
	           	 System.out.print("Erro Ao Tentar Fechar PreparedStatement");
	            }
	
	            if (conn_buscar_tipos_responsaveis_passeiosos != null) {
	               Conectar_Banco_Dados.closeConnection();
	            }else {
	           	 System.out.print("Erro Ao Tentar Fechar Conexão Com Banco De Dados");
	            }
	         } catch (Exception var12) {
	            var12.printStackTrace();
	         }}
	    }
    
    private void Mostrar_Alerta(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    private void Mostrar_Alerta_Sucesso(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public void Cadastrar_Passeio(ActionEvent event) {
    	float valor_total = 0;
    	float valor_pago = 0;
    	String valor_pago_formatado = null;
    	String valor_pagamento_string = null;
    	String tipo_passeio_string = null;
    	LocalDateTime data_hora_passeio = null;
    	LocalDateTime data_hora_pagamento = null;
    	LocalDate data_pagamento_valor = null;
    	String nome_hospede_passeio = null;
    	String hora_passeio_string = null;
    	String valor_total_string = null;
    	String valor_total_formatado = null;
    	String responsavel_string = null;
    	int esta_agendado_formatado = 0;
    	int id_tipo_passeio = 0;
    	int id_responsavel_passeio = 0;
    	
    	try {
    	    nome_hospede_passeio = nome_hospede_label.getText();
    	    if (nome_hospede_passeio == null || nome_hospede_passeio.trim().isEmpty()) {
    	        Mostrar_Alerta("Erro De Preenchimento", "O campo 'Nome do Hóspede' deve ser preenchido!");
    	        nome_hospede_label.setStyle("-fx-border-color: red");
    	        return;
    	    } else {
    	        nome_hospede_label.setStyle("");
    	    }

    	    try {
    	        valor_total_string = valor_passeio_label.getText();
    	        if (valor_total_string == null || valor_total_string.trim().isEmpty()) {
    	            Mostrar_Alerta("Erro De Preenchimento", "O campo 'Valor Total' deve ser preenchido!");
    	            valor_passeio_label.setStyle("-fx-border-color: red");
    	            return;
    	        }

    	        valor_passeio_label.setStyle("");

    	        valor_total_formatado = valor_total_string.replace("R$", "").trim();
    	        valor_total = Float.parseFloat(valor_total_formatado);
    	        
    	        valor_passeio_label.setText(String.format("R$ "+valor_total+"0"));
    	        
    	    } catch (NumberFormatException e) {
    	        Mostrar_Alerta("Erro De Preenchimento", "No campo 'Valor Total', deve ser escrito apenas números!");
    	        valor_passeio_label.setStyle("-fx-border-color: red");
    	        return;
    	    }

    	    Object tipo_passeio_obj = menu_tipo_passeio.getValue();
    	    if (tipo_passeio_obj == null) {
    	        Mostrar_Alerta("Erro De Preenchimento", "Selecione um tipo de passeio!");
    	        menu_tipo_passeio.setStyle("-fx-border-color: red");
    	        return;
    	    } else {
    	        menu_tipo_passeio.setStyle("");
    	        tipo_passeio_string = tipo_passeio_obj.toString();
    	    }

    	    try {
    	    	hora_passeio.setStyle("");
    	        hora_passeio_string = hora_passeio.getText();
    	        if (hora_passeio_string == null || !hora_passeio_string.matches("\\d{2}:\\d{2}")) {
    	            throw new DateTimeParseException("Formato inválido", hora_passeio_string, 0);
    	        }

    	        if (hora_passeio_string.matches("\\d{2}:\\d{2}")) {
    	            hora_passeio_string += ":00";
    	        }

    	        LocalTime hora_passeio_formatada = LocalTime.parse(hora_passeio_string, DateTimeFormatter.ofPattern("HH:mm:ss"));

    	        LocalDate data_passeio = data_passeio_label.getValue();
    	        if (data_passeio == null) {
    	            Mostrar_Alerta("Erro De Preenchimento", "Por favor, selecione uma data!");
    	            data_passeio_label.setStyle("-fx-border-color: red");
    	            return;
    	        } else {
    	            data_hora_passeio = LocalDateTime.of(data_passeio, hora_passeio_formatada);
    	            data_passeio_label.setStyle("");
    	        }
    	    } catch (DateTimeParseException e) {
    	        Mostrar_Alerta("Erro De Preenchimento", "A hora deve estar no formato HH:mm ou HH:mm:ss.");
    	        hora_passeio.setStyle("-fx-border-color: red");
    	        return;
    	    } catch (Exception e) {
    	        Mostrar_Alerta("Erro Inesperado", "Ocorreu um erro ao processar os dados: " + e.getMessage());
    	        return;
    	    }
    	    
    	    Object responsavel_passeio_obj = menu_responsavel.getValue();
    	    if (responsavel_passeio_obj == null) {
    	        Mostrar_Alerta("Erro De Preenchimento", "Selecione um responsavel para o passeio!");
    	        menu_responsavel.setStyle("-fx-border-color: red");
    	        return;
    	    } else {
    	        menu_responsavel.setStyle("");
    	        responsavel_string = responsavel_passeio_obj.toString();
    	    }
    	    
    	    if(esta_agendado.isSelected()) {
    	    	esta_agendado_formatado = 1;
    	    }else {
    	    	esta_agendado_formatado = 0;
    	    }
  
    	    try {
    	        valor_pagamento_string = valor_pago_label.getText();
    	        if (valor_pagamento_string == null || valor_pagamento_string.trim().isEmpty()) {
    	        	valor_pago_label.setText(String.format("R$ 0.00"));
    	        }
    	        valor_pago_label.setStyle("");

    	        valor_pago_formatado = valor_pagamento_string.replace("R$", "").trim();
    	        valor_pago = Float.parseFloat(valor_pago_formatado);
    	        
    	        valor_pago_label.setText(String.format("R$ "+valor_pago+"0"));
    	        
    	    } catch (NumberFormatException e) {
    	        Mostrar_Alerta("Erro De Preenchimento", "No campo 'Valor Pago', deve ser escrito apenas números!");
    	        valor_pago_label.setStyle("-fx-border-color: red");
    	        return;
    	    }
    	    
    	    data_pagamento_valor = data_pagamento.getValue();
	        if (data_pagamento_valor == null) {
	            Mostrar_Alerta("Erro De Preenchimento", "Por favor, selecione uma data!");
	            data_pagamento.setStyle("-fx-border-color: red");
	            return;
	        } else {
	        	LocalTime hora = LocalTime.parse("00:00:00");
	        	data_hora_pagamento = LocalDateTime.of(data_pagamento_valor, hora);;
	            data_passeio_label.setStyle("");
	        }
	        
	        
    	} catch (Exception e) {
    	    Mostrar_Alerta("Erro Inesperado", "Um erro ocorreu: " + e.getMessage());
    	}
    	
    	System.out.print("Buscando por id do tipo de passeio...\n");
    	
    	String sql_buscar_tipo_id = "SELECT id_tipo_passeio FROM tipos_passeios WHERE descricao = ?";
        PreparedStatement ps_buscar_tipo_id = null;
        Connection conn_buscar_tipo_id = null;
    		
        try {
        	conn_buscar_tipo_id = Conectar_Banco_Dados.getConnection();
            System.out.println("Conexão estabelecida com sucesso para Buscar Id Do Passeio: " + (conn_buscar_tipo_id != null));
            ps_buscar_tipo_id = conn_buscar_tipo_id.prepareStatement(sql_buscar_tipo_id);
            ps_buscar_tipo_id.setString(1, tipo_passeio_string);
            ResultSet id_tipo_passeio_result = ps_buscar_tipo_id.executeQuery();
            
            if(id_tipo_passeio_result.next()) {
            	id_tipo_passeio = id_tipo_passeio_result.getInt("id_tipo_passeio");
            }
        }catch (Exception erro_ao_definir_usuario_logado) {
        	  erro_ao_definir_usuario_logado.printStackTrace();
          }finally {
        	
          }
        
        System.out.print("Buscando por id do responsavel pelo passeio...\n");
    	
    	String sql_buscar_responsavel_id = "SELECT id_colaborador FROM colaboradores WHERE nome = ?";
        PreparedStatement ps_buscar_responsavel_id = null;
        Connection conn_buscar_responsavel_id = null;
    		
        try {
        	conn_buscar_responsavel_id = Conectar_Banco_Dados.getConnection();
            System.out.println("Conexão estabelecida com sucesso para Buscar Id Do Colaborador: " + (conn_buscar_responsavel_id != null));
            ps_buscar_responsavel_id = conn_buscar_responsavel_id.prepareStatement(sql_buscar_responsavel_id);
            ps_buscar_responsavel_id.setString(1, responsavel_string);
            ResultSet id_responsavel_passeio_result = ps_buscar_responsavel_id.executeQuery();
            
            if(id_responsavel_passeio_result.next()) {
            	id_responsavel_passeio = id_responsavel_passeio_result.getInt("id_colaborador");
            }
        }catch (Exception erro_ao_definir_usuario_logado) {
        	  erro_ao_definir_usuario_logado.printStackTrace();
          }finally {
        	
          }
        
        System.out.println("Registrando Passeio...\n");

        System.out.println("Registrando Passeio...\n");
		String sql_registrar_passeio = "INSERT INTO passeios (nome_do_hospede, valor_pago, data_do_passeio, valor, tipo_passeio, data_de_registro_passeio, id_responsavel_registro_passeio, id_colaborador_passeio, status_passeio, data_pagamento)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps_registrar_passeio = null;
        Connection conn_registrar_passeio = null;
        
        try {
        	int id_usuario = LoginController.Get_Id_Usuario_Logado();
        	LocalDateTime data_hora_atual = LocalDateTime.now();
        	conn_registrar_passeio = Conectar_Banco_Dados.getConnection();
            System.out.println("Conexão estabelecida com sucesso para Registrar Passeio: " + (conn_registrar_passeio != null));
            ps_registrar_passeio = conn_registrar_passeio.prepareStatement(sql_registrar_passeio);
            ps_registrar_passeio.setString(1,nome_hospede_passeio);
            ps_registrar_passeio.setObject(2, valor_pago);
            ps_registrar_passeio.setObject(3, data_hora_passeio);
            ps_registrar_passeio.setObject(4, valor_total);
            ps_registrar_passeio.setObject(5, id_tipo_passeio);
            ps_registrar_passeio.setObject(6, data_hora_atual);
            ps_registrar_passeio.setObject(7, id_usuario);
            ps_registrar_passeio.setObject(8, id_responsavel_passeio);
            ps_registrar_passeio.setObject(9, esta_agendado_formatado);
            ps_registrar_passeio.setObject(10, data_hora_pagamento);
            
            ps_registrar_passeio.executeUpdate();
            
            System.out.println("Passeio Resgistrado Com Sucesso\n");
            Mostrar_Alerta_Sucesso("Sucesso!", "Passeio Registrado Com Sucesso!");
            
        }catch (Exception erro_registrar_passeio) {
        	erro_registrar_passeio.printStackTrace();
        }
    }}


