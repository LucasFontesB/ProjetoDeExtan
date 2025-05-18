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
import application.Formatar_Datas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MenuPasseioController {
	
	private int id_passeio;
	
	@FXML
    private ComboBox<?> tipo_passeio_entry;
	
	@FXML
	private TextField valor_pago_entry;
	
	@FXML
    private DatePicker data_passeio_entry;

    @FXML
    private TextField usuario_registro_entry;

    @FXML
    private TextField id_passeio_entry;

    @FXML
    private TextField status_passeio_entry;

    @FXML
    private TextField valor_passeio_entry;

    @FXML
    private ComboBox<?> nome_colaborador_entry;

    @FXML
    private TextField data_registro_entry;
    
    @FXML
    private Button botao_editar_passeio;
    
    @FXML
    private TextField nome_hospede_entry;
    
    @FXML
    private TextField data_pagamento_entry;
    
    @FXML
    private Button botao_salvar_alteracoes;
    
    @FXML
    private Button botao_excluir_passeio;
    
    @FXML
    private TextField hora_passeio_entry;
    
    @FXML
    private Button botao_cancelar_alteracao;
    
    static int id_passeio_atual;
	
    public void abrirTela (int id) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MenuPasseioController.class.getResource("/views/tela_menuPasseios.fxml"));
            Parent root = fxmlLoader.load();
            MenuPasseioController controller = fxmlLoader.getController();
            controller.Mostrar_Passeio(id);
            id_passeio_atual = id;
            Stage telapasseio = new Stage();
            telapasseio.setTitle("Gerenciador De Passeios");
            telapasseio.setScene(new Scene(root));
            telapasseio.show();
            
        } catch (Exception erro_ao_abrir_tela_principal) {
            erro_ao_abrir_tela_principal.printStackTrace();
        }
    }
    
    @FXML
    void Cancelar_Alteracoes(ActionEvent event) {
    	botao_salvar_alteracoes.setVisible(false);
    	botao_editar_passeio.setVisible(true);
    	botao_excluir_passeio.setVisible(true);
    	botao_cancelar_alteracao.setVisible(false);
    	
    	Set<String> tipos_passeios = new HashSet();
    	ObservableList passeiosList = FXCollections.observableArrayList();
    	tipo_passeio_entry.getItems().clear();
    	if(tipo_passeio_entry == null) {
    	}else {
    		tipo_passeio_entry.getItems().clear();
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
            tipo_passeio_entry.setItems(passeiosList);
            
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
    	nome_colaborador_entry.getItems().clear();
    	if(nome_colaborador_entry == null) {
    	}else {
    		nome_colaborador_entry.getItems().clear();
    	}
    	System.out.println("\n\nBuscando por colaboradores registrados...\n");
		String sql_buscar_responsaveis_passeios = "SELECT colaboradores.nome FROM colaboradores";
        PreparedStatement ps_buscar_responsaveis_passeiosos = null;
        Connection conn_buscar_responsaveis_passeiosos = null;
        
        try {
        	conn_buscar_responsaveis_passeiosos = Conectar_Banco_Dados.getConnection();
            System.out.println("Conexão estabelecida com sucesso para buscar colaboradores registrados: " + (conn_buscar_responsaveis_passeiosos != null));
            ps_buscar_responsaveis_passeiosos = conn_buscar_responsaveis_passeiosos.prepareStatement(sql_buscar_responsaveis_passeios);
            ResultSet responsaveis_achados = ps_buscar_responsaveis_passeiosos.executeQuery();
            
            while(responsaveis_achados.next()) {
            	String string_responsaveis_achados = responsaveis_achados.getString("nome");
            	System.out.print("\nTipos Achados: "+string_responsaveis_achados+"\n");
                responsaveis_passeios.add(string_responsaveis_achados);
            }
            responsaveisList.addAll(responsaveis_passeios);
            nome_colaborador_entry.setItems(responsaveisList);
            
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
    
                if (conn_buscar_responsaveis_passeiosos != null) {
                   Conectar_Banco_Dados.closeConnection();
                }else {
               	 System.out.print("Erro Ao Tentar Fechar Conexão Com Banco De Dados");
                }
             } catch (Exception var12) {
                var12.printStackTrace();
             }
        }
    	
    	System.out.println("Procurando Passeio Para Menu...\n");
		String sql_buscar_passeio_completo = "SELECT passeios.id_passeio, passeios.valor_pago, passeios.data_pagamento, passeios.nome_do_hospede, passeios.data_do_passeio, passeios.valor, tipos_passeios.descricao, passeios.data_de_registro_passeio, usuarios.nome as 'nome_usuario_registro', colaboradores.nome as 'nome_colaborador', passeios.status_passeio FROM passeios JOIN tipos_passeios ON tipos_passeios.id_tipo_passeio = passeios.tipo_passeio JOIN usuarios ON passeios.id_responsavel_registro_passeio = usuarios.id_usuario JOIN colaboradores ON passeios.id_colaborador_passeio = colaboradores.id_colaborador WHERE passeios.id_passeio = ?";
        PreparedStatement ps_buscar_passeio_completo = null;
        Connection conn_buscar_passeio_completo = null;
        
        try {
        	conn_buscar_passeio_completo = Conectar_Banco_Dados.getConnection();
            System.out.println("Conexão estabelecida com sucesso para Registrar Horário No Registra Turno: " + (conn_buscar_passeio_completo != null));
            ps_buscar_passeio_completo = conn_buscar_passeio_completo.prepareStatement(sql_buscar_passeio_completo);
            ps_buscar_passeio_completo.setInt(1, id_passeio_atual);
            ResultSet resultado_passeio = ps_buscar_passeio_completo.executeQuery();
            
            if (resultado_passeio.next()) {
            	String status_convertido;
	        	int idPasseio = resultado_passeio.getInt("id_passeio");
	        	double valorPago = resultado_passeio.getDouble("valor_pago");
	            String nomeHospede = resultado_passeio.getString("nome_do_hospede");
	            String dataPasseio = resultado_passeio.getString("data_do_passeio");
	            //String dataPasseio_formatada = Formatar_Datas.Formatar_Para_Usuario(dataPasseio);
	            double valor = resultado_passeio.getDouble("valor");
	            String tipoPasseio = resultado_passeio.getString("descricao");
	            String dataRegistro = resultado_passeio.getString("data_de_registro_passeio");
	            String dataRegistro_formatada = Formatar_Datas.Formatar_Para_Usuario(dataRegistro);
	            String responsavel = resultado_passeio.getString("nome_usuario_registro");
	            String nome_colaborador = resultado_passeio.getString("nome_colaborador");
	            String data_pagamento = resultado_passeio.getString("data_pagamento");
	            String data_pagamentoFormatada = Formatar_Datas.Formatar_Para_Usuario(data_pagamento);
	            int status = resultado_passeio.getInt("status_passeio");
	            if(status == 1) {
	            	status_convertido = "Agendado";
	            }else {
	            	status_convertido = "Não Agendado";
	            }       
	            String[] partes = dataPasseio.split(" ");
	            String data_separada = partes[0];
	            String hora_separada = partes[1];
	            
	            LocalDate data = LocalDate.parse(data_separada);
	            
	            nome_hospede_entry.setText(nomeHospede);
	            data_passeio_entry.setValue(data);
	            data_registro_entry.setText(dataRegistro_formatada);
	            usuario_registro_entry.setText(responsavel);
	            id_passeio_entry.setText(""+idPasseio);
	            status_passeio_entry.setText(status_convertido);
	            valor_passeio_entry.setText("R$ "+valor+"0");
	            nome_colaborador_entry.setPromptText(nome_colaborador);
	            tipo_passeio_entry.setPromptText(tipoPasseio);
	            valor_pago_entry.setText("R$ "+valorPago+"0");
	            hora_passeio_entry.setText(hora_separada);
	            data_pagamento_entry.setText(data_pagamentoFormatada);

            }

            System.out.println("Horário Resgistrado Com Sucesso\n");
        }catch (Exception erro_ao_definir_usuario_logado) {
      	  erro_ao_definir_usuario_logado.printStackTrace();
        }
        
        nome_hospede_entry.requestFocus();
        
        nome_hospede_entry.setEditable(false);
    	data_passeio_entry.setEditable(false);
    	data_registro_entry.setEditable(false);
    	usuario_registro_entry.setEditable(false);
    	id_passeio_entry.setEditable(false);
    	status_passeio_entry.setEditable(false);
    	valor_passeio_entry.setEditable(false);
    	nome_colaborador_entry.setEditable(false);
    	nome_colaborador_entry.setDisable(true);
    	tipo_passeio_entry.setEditable(false);
    	tipo_passeio_entry.setDisable(true);
    	valor_pago_entry.setEditable(false);
    	data_pagamento_entry.setEditable(false);
    }

    @FXML
    void Excluir_Passeio(ActionEvent event) {	
    	System.out.print("\nid para apagar: "+id_passeio);
    	System.out.println("\nExcluindo Passeio...\n");
		String sql_excluir_passeio = "DELETE FROM passeios WHERE id_passeio = ?";
        PreparedStatement ps_excluir_passeio = null;
        Connection conn_excluir_passeio = null;
        
        try {
        	conn_excluir_passeio = Conectar_Banco_Dados.getConnection();
            System.out.println("Conexão estabelecida com sucesso para Apagar Passeio: " + (conn_excluir_passeio != null));
            ps_excluir_passeio = conn_excluir_passeio.prepareStatement(sql_excluir_passeio);
            ps_excluir_passeio.setInt(1, id_passeio);
            ps_excluir_passeio.executeUpdate();
            
            System.out.println("Passeio Excluido Com Sucesso\n");
        }catch (Exception erro_ao_definir_usuario_logado) {
      	  	erro_ao_definir_usuario_logado.printStackTrace();
        }
    	
    	Alert passeio_excluido = new Alert(AlertType.INFORMATION);
    	passeio_excluido.setTitle("Sucesso!");
    	passeio_excluido.setHeaderText(null);
    	passeio_excluido.setContentText("Passeio Excluido Com Sucesso");
    	passeio_excluido.showAndWait();
    	
    	Stage tela_principal = (Stage) botao_excluir_passeio.getScene().getWindow();
  	  	tela_principal.close();
    }
    
    @FXML
    void Editar_Passeio(ActionEvent event) {
    	nome_hospede_entry.setEditable(true);
    	data_passeio_entry.setEditable(true);
    	data_passeio_entry.setDisable(false);
    	data_registro_entry.setEditable(false);
    	usuario_registro_entry.setEditable(false);
    	id_passeio_entry.setEditable(false);
    	status_passeio_entry.setEditable(true);
    	valor_passeio_entry.setEditable(true);
    	nome_colaborador_entry.setEditable(true);
    	nome_colaborador_entry.setDisable(false);
    	tipo_passeio_entry.setEditable(true);
    	tipo_passeio_entry.setDisable(false);
    	valor_pago_entry.setEditable(true);
    	data_pagamento_entry.setEditable(true);
    	data_pagamento_entry.setDisable(false);
    	hora_passeio_entry.setEditable(true);

    	botao_salvar_alteracoes.setVisible(true);
    	botao_editar_passeio.setVisible(false);
    	botao_excluir_passeio.setVisible(false);
    	botao_cancelar_alteracao.setVisible(true);
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
    
    @FXML
    void Salvar_Alteracoes(ActionEvent event) {
    	float valor_total = 0;
    	float valor_pago = 0;
    	String valor_pago_formatado = null;
    	String valor_pagamento_string = null;
    	String tipo_passeio_string = null;
    	LocalDateTime data_hora_passeio = null;
    	LocalDateTime data_hora_pagamento = null;
    	String nome_hospede_passeio = null;
    	String hora_passeio_string = null;
    	String valor_total_string = null;
    	String valor_total_formatado = null;
    	String responsavel_string = null;
    	int esta_agendado_formatado = 0;
    	int id_tipo_passeio = 0;
    	int id_colaborador = 0;
    	
    	try {
    	    nome_hospede_passeio = nome_hospede_entry.getText();
    	    if (nome_hospede_passeio == null || nome_hospede_passeio.trim().isEmpty()) {
    	        Mostrar_Alerta("Erro De Preenchimento", "O campo 'Nome do Hóspede' deve ser preenchido!");
    	        nome_hospede_entry.setStyle("-fx-border-color: red");
    	        return;
    	    } else {
    	        nome_hospede_entry.setStyle("");
    	    }

    	    try {
    	        valor_total_string = valor_passeio_entry.getText();
    	        if (valor_total_string == null || valor_total_string.trim().isEmpty()) {
    	            Mostrar_Alerta("Erro De Preenchimento", "O campo 'Valor Total' deve ser preenchido!");
    	            valor_passeio_entry.setStyle("-fx-border-color: red");
    	            return;
    	        }

    	        valor_passeio_entry.setStyle("");

    	        valor_total_formatado = valor_total_string.replace("R$", "").trim();
    	        valor_total = Float.parseFloat(valor_total_formatado);
    	        
    	        valor_passeio_entry.setText(String.format("R$ "+valor_total+"0"));
    	        
    	    } catch (NumberFormatException e) {
    	        Mostrar_Alerta("Erro De Preenchimento", "No campo 'Valor Total', deve ser escrito apenas números!");
    	        valor_passeio_entry.setStyle("-fx-border-color: red");
    	        return;
    	    }

    	    Object tipo_passeio_obj = tipo_passeio_entry.getValue();
    	    if (tipo_passeio_obj == null) {
    	        Mostrar_Alerta("Erro De Preenchimento", "Selecione um tipo de passeio!");
    	        tipo_passeio_entry.setStyle("-fx-border-color: red");
    	        return;
    	    } else {
    	    	tipo_passeio_entry.setStyle("");
    	        tipo_passeio_string = tipo_passeio_obj.toString();
    	    }

    	    try {
    	    	hora_passeio_entry.setStyle("");
    	        hora_passeio_string = hora_passeio_entry.getText();
    	        if (hora_passeio_string == null || !hora_passeio_string.matches("\\d{2}:\\d{2}:\\d{2}")) {
    	            throw new DateTimeParseException("Formato inválido", hora_passeio_string, 0);
    	        }

    	        LocalTime hora_passeio_formatada = LocalTime.parse(hora_passeio_string, DateTimeFormatter.ofPattern("HH:mm:ss"));

    	        LocalDate data_passeio = data_passeio_entry.getValue();
    	        if (data_passeio == null) {
    	            Mostrar_Alerta("Erro De Preenchimento", "Por favor, selecione uma data!");
    	            data_passeio_entry.setStyle("-fx-border-color: red");
    	            return;
    	        }
    	        data_hora_passeio = LocalDateTime.of(data_passeio, hora_passeio_formatada);
    	        data_passeio_entry.setStyle("");
    	        hora_passeio_entry.setStyle("");

    	    } catch (DateTimeParseException e) {
    	        Mostrar_Alerta("Erro De Preenchimento", "A hora deve estar no formato HH:mm:ss.");
    	        hora_passeio_entry.setStyle("-fx-border-color: red");
    	        return;
    	    } catch (Exception e) {
    	        Mostrar_Alerta("Erro Inesperado", "Ocorreu um erro ao processar os dados: " + e.getMessage());
    	        return;
    	    }
    	    
    	    Object responsavel_passeio_obj = nome_colaborador_entry.getValue();
    	    System.out.print("Responsavel: "+responsavel_passeio_obj);
    	    if (responsavel_passeio_obj == null) {
    	        Mostrar_Alerta("Erro De Preenchimento", "Selecione um responsavel para o passeio!");
    	        nome_colaborador_entry.setStyle("-fx-border-color: red");
    	        return;
    	    } else {
    	    	nome_colaborador_entry.setStyle("");
    	        responsavel_string = responsavel_passeio_obj.toString();
    	    }
    	    
    	    if(status_passeio_entry.getText().equals("Agendado") || status_passeio_entry.getText().equals("agendado") || status_passeio_entry.getText().equals("Sim") || status_passeio_entry.getText().equals("sim")) {
    	    	esta_agendado_formatado = 1;
    	    }else {
    	    	esta_agendado_formatado = 0;
    	    }
    	    
    	    try {
    	        valor_pagamento_string = valor_pago_entry.getText();
    	        if (valor_pagamento_string == null || valor_pagamento_string.trim().isEmpty()) {
    	        	valor_pago_entry.setText(String.format("R$ 0.00"));
    	        }
    	        valor_pago_entry.setStyle("");

    	        valor_pago_formatado = valor_pagamento_string.replace("R$", "").trim();
    	        valor_pago = Float.parseFloat(valor_pago_formatado);
    	        
    	        valor_pago_entry.setText(String.format("R$ "+valor_pago+"0"));
    	        
    	    } catch (NumberFormatException e) {
    	        Mostrar_Alerta("Erro De Preenchimento", "No campo 'Valor Pago', deve ser escrito apenas números!");
    	        valor_pago_entry.setStyle("-fx-border-color: red");
    	        return;
    	    }
    	    
    	    String data_pagamento_valor = data_pagamento_entry.getText();
    	    LocalDate data_formatada = LocalDate.parse(data_pagamento_valor, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	        if (data_pagamento_valor == null) {
	            Mostrar_Alerta("Erro De Preenchimento", "Por favor, selecione uma data!");
	            data_pagamento_entry.setStyle("-fx-border-color: red");
	            return;
	        } else {
	        	LocalTime hora = LocalTime.parse("00:00:00");
	        	data_hora_pagamento = LocalDateTime.of(data_formatada, hora);;
	            data_passeio_entry.setStyle("");
	        }
	        
	        
    	} catch (Exception e) {
    	    Mostrar_Alerta("Erro Inesperado", "Um erro ocorreu: " + e.getMessage());
    	}
    	
    	System.out.println("Voltando Responsável...\n");
		String sql_buscar_tipo_passeio = "SELECT id_tipo_passeio FROM tipos_passeios WHERE descricao = ?";
        PreparedStatement ps_buscar_tipo_passeio = null;
        Connection conn_buscar_tipo_passeio = null;
        
        try {
        	conn_buscar_tipo_passeio = Conectar_Banco_Dados.getConnection();
            System.out.println("Conexão estabelecida com sucesso para Registrar Passeio: " + (conn_buscar_tipo_passeio != null));
            ps_buscar_tipo_passeio = conn_buscar_tipo_passeio.prepareStatement(sql_buscar_tipo_passeio);
            ps_buscar_tipo_passeio.setString(1, tipo_passeio_string);
            
            ResultSet resultado_passeio = ps_buscar_tipo_passeio.executeQuery();
            
            if(resultado_passeio.next()) {
            	id_tipo_passeio = resultado_passeio.getInt("id_tipo_passeio");
            }
            
        }catch (Exception erro_registrar_passeio) {
        	erro_registrar_passeio.printStackTrace();
        }
        
        System.out.println("Passeio: "+id_colaborador);
        
        System.out.println("Voltando Responsável...\n");
		String sql_buscar_colaborador = "SELECT id_colaborador FROM colaboradores WHERE nome = ?";
        PreparedStatement ps_buscar_colaborador = null;
        Connection conn_buscar_colaborador = null;
        
        try {
        	conn_buscar_colaborador = Conectar_Banco_Dados.getConnection();
            System.out.println("Conexão estabelecida com sucesso para Registrar Passeio: " + (conn_buscar_colaborador != null));
            ps_buscar_colaborador = conn_buscar_colaborador.prepareStatement(sql_buscar_colaborador);
            ps_buscar_colaborador.setString(1, responsavel_string);
            
            ResultSet resultado_colaborador = ps_buscar_colaborador.executeQuery();
            
            if(resultado_colaborador.next()) {
            	id_colaborador = resultado_colaborador.getInt("id_colaborador");
            }
            
        }catch (Exception erro_registrar_passeio) {
        	erro_registrar_passeio.printStackTrace();
        }
        
        System.out.println("Colaborador: "+id_colaborador);
    	
    	System.out.println("Registrando Passeio...\n");
		String sql_registrar_passeio = "UPDATE passeios SET nome_do_hospede = ?, data_do_passeio = ?, status_passeio = ?, id_colaborador_passeio = ?, tipo_passeio = ?, valor = ?, valor_pago = ?, data_pagamento = ? WHERE id_passeio = ?";
        PreparedStatement ps_registrar_passeio = null;
        Connection conn_registrar_passeio = null;
        
        try {
        	conn_registrar_passeio = Conectar_Banco_Dados.getConnection();
            System.out.println("Conexão estabelecida com sucesso para Registrar Passeio: " + (conn_registrar_passeio != null));
            ps_registrar_passeio = conn_registrar_passeio.prepareStatement(sql_registrar_passeio);
            ps_registrar_passeio.setString(1, nome_hospede_passeio);
            ps_registrar_passeio.setObject(2, data_hora_passeio);
            ps_registrar_passeio.setObject(3, esta_agendado_formatado);
            ps_registrar_passeio.setInt(4, id_colaborador);
            ps_registrar_passeio.setInt(5, id_tipo_passeio);
            ps_registrar_passeio.setObject(6, valor_total);
            ps_registrar_passeio.setObject(7, valor_pago);
            ps_registrar_passeio.setObject(8, data_hora_pagamento);
            ps_registrar_passeio.setObject(9, id_passeio_atual);
            
            ps_registrar_passeio.executeUpdate();
            
            System.out.println("Passeio Resgistrado Com Sucesso\n");
            Mostrar_Alerta_Sucesso("Sucesso!", "Passeio Alterado Com Sucesso!");
            
        }catch (Exception erro_registrar_passeio) {
        	erro_registrar_passeio.printStackTrace();
        }

    }
    
    public void Mostrar_Passeio(int id) {
    	id_passeio = id;
    	
    	botao_salvar_alteracoes.setVisible(false);
    	botao_cancelar_alteracao.setVisible(false);
    	
    	nome_hospede_entry.setEditable(false);
    	data_passeio_entry.setEditable(false);
    	data_passeio_entry.setDisable(true);
    	data_registro_entry.setEditable(false);
    	data_registro_entry.setDisable(true);
    	usuario_registro_entry.setDisable(true);
    	id_passeio_entry.setEditable(false);
    	status_passeio_entry.setEditable(false);
    	valor_passeio_entry.setEditable(false);
    	nome_colaborador_entry.setEditable(false);
    	nome_colaborador_entry.setDisable(true);
    	tipo_passeio_entry.setEditable(false);
    	tipo_passeio_entry.setDisable(true);
    	valor_pago_entry.setEditable(false);
    	data_pagamento_entry.setEditable(false);
    	hora_passeio_entry.setEditable(false);
    	

    	
    	Set<String> tipos_passeios = new HashSet();
    	ObservableList passeiosList = FXCollections.observableArrayList();
    	tipo_passeio_entry.getItems().clear();
    	if(tipo_passeio_entry == null) {
    	}else {
    		tipo_passeio_entry.getItems().clear();
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
            tipo_passeio_entry.setItems(passeiosList);
            
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
    	nome_colaborador_entry.getItems().clear();
    	if(nome_colaborador_entry == null) {
    	}else {
    		nome_colaborador_entry.getItems().clear();
    	}
    	System.out.println("\n\nBuscando por colaboradores registrados...\n");
		String sql_buscar_responsaveis_passeios = "SELECT colaboradores.nome FROM colaboradores";
        PreparedStatement ps_buscar_responsaveis_passeiosos = null;
        Connection conn_buscar_responsaveis_passeiosos = null;
        
        try {
        	conn_buscar_responsaveis_passeiosos = Conectar_Banco_Dados.getConnection();
            System.out.println("Conexão estabelecida com sucesso para buscar colaboradores registrados: " + (conn_buscar_responsaveis_passeiosos != null));
            ps_buscar_responsaveis_passeiosos = conn_buscar_responsaveis_passeiosos.prepareStatement(sql_buscar_responsaveis_passeios);
            ResultSet responsaveis_achados = ps_buscar_responsaveis_passeiosos.executeQuery();
            
            while(responsaveis_achados.next()) {
            	String string_responsaveis_achados = responsaveis_achados.getString("nome");
            	System.out.print("\nTipos Achados: "+string_responsaveis_achados+"\n");
                responsaveis_passeios.add(string_responsaveis_achados);
            }
            responsaveisList.addAll(responsaveis_passeios);
            nome_colaborador_entry.setItems(responsaveisList);
            
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
    
                if (conn_buscar_responsaveis_passeiosos != null) {
                   Conectar_Banco_Dados.closeConnection();
                }else {
               	 System.out.print("Erro Ao Tentar Fechar Conexão Com Banco De Dados");
                }
             } catch (Exception var12) {
                var12.printStackTrace();
             }
        }
    	
    	System.out.println("Procurando Passeio Para Menu...\n");
		String sql_buscar_passeio_completo = "SELECT passeios.id_passeio, passeios.valor_pago, passeios.data_pagamento, passeios.nome_do_hospede, passeios.data_do_passeio, passeios.valor, tipos_passeios.descricao, passeios.data_de_registro_passeio, usuarios.nome as 'nome_usuario_registro', colaboradores.nome as 'nome_colaborador', passeios.status_passeio FROM passeios JOIN tipos_passeios ON tipos_passeios.id_tipo_passeio = passeios.tipo_passeio JOIN usuarios ON passeios.id_responsavel_registro_passeio = usuarios.id_usuario JOIN colaboradores ON passeios.id_colaborador_passeio = colaboradores.id_colaborador WHERE passeios.id_passeio = ?";
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
	        	double valorPago = resultado_passeio.getDouble("valor_pago");
	            String nomeHospede = resultado_passeio.getString("nome_do_hospede");
	            String dataPasseio = resultado_passeio.getString("data_do_passeio");
	            //String dataPasseio_formatada = Formatar_Datas.Formatar_Para_Usuario(dataPasseio);
	            double valor = resultado_passeio.getDouble("valor");
	            String tipoPasseio = resultado_passeio.getString("descricao");
	            String dataRegistro = resultado_passeio.getString("data_de_registro_passeio");
	            String dataRegistro_formatada = Formatar_Datas.Formatar_Para_Usuario(dataRegistro);
	            String responsavel = resultado_passeio.getString("nome_usuario_registro");
	            String nome_colaborador = resultado_passeio.getString("nome_colaborador");
	            String data_pagamento = resultado_passeio.getString("data_pagamento");
	            String data_pagamentoFormatada = Formatar_Datas.Formatar_Para_Usuario(data_pagamento);
	            int status = resultado_passeio.getInt("status_passeio");
	            if(status == 1) {
	            	status_convertido = "Agendado";
	            }else {
	            	status_convertido = "Não Agendado";
	            }       
	            String[] partes = dataPasseio.split(" ");
	            String data_separada = partes[0];
	            String hora_separada = partes[1];
	            
	            LocalDate data = LocalDate.parse(data_separada);
	            
	            nome_hospede_entry.setText(nomeHospede);
	            data_passeio_entry.setValue(data);
	            data_registro_entry.setText(dataRegistro_formatada);
	            usuario_registro_entry.setText(responsavel);
	            id_passeio_entry.setText(""+idPasseio);
	            status_passeio_entry.setText(status_convertido);
	            valor_passeio_entry.setText("R$ "+valor+"0");
	            nome_colaborador_entry.setPromptText(nome_colaborador);
	            tipo_passeio_entry.setPromptText(tipoPasseio);
	            valor_pago_entry.setText("R$ "+valorPago+"0");
	            hora_passeio_entry.setText(hora_separada);
	            data_pagamento_entry.setText(data_pagamentoFormatada);

            }

            System.out.println("Horário Resgistrado Com Sucesso\n");
        }catch (Exception erro_ao_definir_usuario_logado) {
      	  erro_ao_definir_usuario_logado.printStackTrace();
        }
    }
}


