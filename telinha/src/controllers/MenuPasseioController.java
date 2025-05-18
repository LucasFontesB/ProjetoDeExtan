package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MenuPasseioController {
	
	private int id_passeio;
	
	@FXML
    private ComboBox<?> tipo_passeio_entry;
	
	@FXML
    private TextField data_passeio_entry;

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
    private Button botao_salvar_alteracoes;
    
    @FXML
    private Button botao_excluir_passeio;
    
    @FXML
    private Button botao_cancelar_alteracao;

	
    public void abrirTela (int id) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MenuPasseioController.class.getResource("/views/tela_menuPasseios.fxml"));
            Parent root = fxmlLoader.load();
            MenuPasseioController controller = fxmlLoader.getController();
            controller.Mostrar_Passeio(id);
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
    	
    	System.out.println("Procurando Passeio Para Menu...\n");
		String sql_buscar_passeio_completo = "SELECT passeios.id_passeio, passeios.nome_do_hospede, passeios.data_do_passeio, passeios.valor, tipos_passeios.descricao, passeios.data_de_registro_passeio, usuarios.nome as 'nome_usuario_registro', colaboradores.nome as 'nome_colaborador', passeios.status_passeio FROM passeios JOIN tipos_passeios ON tipos_passeios.id_tipo_passeio = passeios.tipo_passeio JOIN usuarios ON passeios.id_responsavel_registro_passeio = usuarios.id_usuario JOIN colaboradores ON passeios.id_colaborador_passeio = colaboradores.id_colaborador WHERE passeios.id_passeio = ?";
        PreparedStatement ps_buscar_passeio_completo = null;
        Connection conn_buscar_passeio_completo = null;
        
        try {
        	System.out.print("\nTry\n");
        	conn_buscar_passeio_completo = Conectar_Banco_Dados.getConnection();
            System.out.println("Conexão estabelecida com sucesso para Cancelar Mudanças: " + (conn_buscar_passeio_completo != null));
            ps_buscar_passeio_completo = conn_buscar_passeio_completo.prepareStatement(sql_buscar_passeio_completo);
            ps_buscar_passeio_completo.setInt(1, id_passeio);
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
	            if(status == 1) {
	            	status_convertido = "Agendado";
	            }else {
	            	status_convertido = "Não Agendado";
	            }
	            
	            System.out.print("\n\nNome Do Colaborador: "+nome_colaborador+"\n\n");
	            
	            nome_hospede_entry.setText(nomeHospede);
		        data_passeio_entry.setText(dataPasseio_formatada);
		        data_registro_entry.setText(dataRegistro_formatada);
		        usuario_registro_entry.setText(responsavel);
		        id_passeio_entry.setText(""+idPasseio);
		        status_passeio_entry.setText(status_convertido);
		        valor_passeio_entry.setText("R$ "+valor+"0");
		        
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
		        
		        nome_colaborador_entry.setPromptText(nome_colaborador);
		        System.out.print("Teste");		        
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
		        
		        tipo_passeio_entry.setPromptText(tipoPasseio);
            }
            System.out.println("Edição Cancelada Com Sucesso\n");
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
    	data_registro_entry.setEditable(true);
    	usuario_registro_entry.setEditable(true);
    	id_passeio_entry.setEditable(true);
    	status_passeio_entry.setEditable(true);
    	valor_passeio_entry.setEditable(true);
    	nome_colaborador_entry.setEditable(true);
    	nome_colaborador_entry.setDisable(false);
    	tipo_passeio_entry.setEditable(true);
    	tipo_passeio_entry.setDisable(false);
    	
    	botao_salvar_alteracoes.setVisible(true);
    	botao_editar_passeio.setVisible(false);
    	botao_excluir_passeio.setVisible(false);
    	botao_cancelar_alteracao.setVisible(true);
    }
    
    @FXML
    void Salvar_Alteracoes(ActionEvent event) {
    	

    }
    
    public void Mostrar_Passeio(int id) {
    	id_passeio = id;
    	
    	botao_salvar_alteracoes.setVisible(false);
    	botao_cancelar_alteracao.setVisible(false);
    	
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
		String sql_buscar_passeio_completo = "SELECT passeios.id_passeio, passeios.nome_do_hospede, passeios.data_do_passeio, passeios.valor, tipos_passeios.descricao, passeios.data_de_registro_passeio, usuarios.nome as 'nome_usuario_registro', colaboradores.nome as 'nome_colaborador', passeios.status_passeio FROM passeios JOIN tipos_passeios ON tipos_passeios.id_tipo_passeio = passeios.tipo_passeio JOIN usuarios ON passeios.id_responsavel_registro_passeio = usuarios.id_usuario JOIN colaboradores ON passeios.id_colaborador_passeio = colaboradores.id_colaborador WHERE passeios.id_passeio = ?";
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
	            if(status == 1) {
	            	status_convertido = "Agendado";
	            }else {
	            	status_convertido = "Não Agendado";
	            }
	            
	            nome_hospede_entry.setText(nomeHospede);
	            data_passeio_entry.setText(dataPasseio_formatada);
	            data_registro_entry.setText(dataRegistro_formatada);
	            usuario_registro_entry.setText(responsavel);
	            id_passeio_entry.setText(""+idPasseio);
	            status_passeio_entry.setText(status_convertido);
	            valor_passeio_entry.setText("R$ "+valor+"0");
	            nome_colaborador_entry.setPromptText(nome_colaborador);
	            tipo_passeio_entry.setPromptText(tipoPasseio);

            }

            System.out.println("Horário Resgistrado Com Sucesso\n");
        }catch (Exception erro_ao_definir_usuario_logado) {
      	  erro_ao_definir_usuario_logado.printStackTrace();
        }
    }
}


