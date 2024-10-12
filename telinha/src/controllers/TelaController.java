package controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import application.Conectar_Banco_Dados;


import java.time.LocalDateTime;
import java.util.regex.Pattern;

import application.Formatar_Datas;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TelaController {

    @FXML
    private Tab tab_adicionarPasseios;

    @FXML
    private Tab tab_home;

    @FXML
    private ComboBox<?> menu_responsavel;

    @FXML
    private TextField nome_hospede_label;

    @FXML
    private Tab tab_configuracoes;

    @FXML
    private Button botao_sair;

    @FXML
    private Button registrar_passeio_botao;

    @FXML
    private Button botao_adicionar_colaborador;

    @FXML
    private DatePicker data_passeio_label;

    @FXML
    private Button botao_adicionar_usuario;

    @FXML
    private TextField barra_busca;

    @FXML
    private TextField valor_passeio_label;

    @FXML
    private ComboBox<?> menu_tipo_passeio;

    @FXML
    private Button botao_pesquisar;

    @FXML
    private TextField valor_pago_label;

    @FXML
    private Button botao_relatorio;

    @FXML
    private TextField comprovante_label;
    
    @FXML
    private Button botao_adicionar_tipoPasseios;
    
    public class Passeio {
        private int idPasseio;
        private String nomeHospede;
        private String dataPasseio;
        private double valor;
        private String tipoPasseio;
        private String dataRegistro;
        private String responsavel;

        public Passeio(int idPasseio, String nomeHospede, String dataPasseio, double valor, String tipoPasseio, String dataRegistro, String responsavel) {
            this.idPasseio = idPasseio;
            this.nomeHospede = nomeHospede;
            this.dataPasseio = dataPasseio;
            this.valor = valor;
            this.tipoPasseio = tipoPasseio;
            this.dataRegistro = dataRegistro;
            this.responsavel = responsavel;
        }
        
        public String toString() {
            return "Passeio{" +
                    "idPasseio=" + idPasseio +
                    ", nomeHospede='" + nomeHospede + '\'' +
                    ", dataPasseio='" + dataPasseio + '\'' +
                    ", valor=" + valor +
                    ", tipoPasseio='" + tipoPasseio + '\'' +
                    ", dataRegistro='" + dataRegistro + '\'' +
                    ", responsavel='" + responsavel + '\'' +
                    '}';
        }

        public int getIdPasseio() { 
        	return idPasseio; 
        }
        public String getNomeHospede() { 
        	return nomeHospede;
        }
        public String getDataPasseio() { 
        	return dataPasseio; 
        }
        public double getValor() { 
        	return valor; 
        }
        public String getTipoPasseio() { 
        	return tipoPasseio; 
        }
        public String getDataRegistro() { 
        	return dataRegistro; 
        }
        public String getResponsavel() { 
        	return responsavel; 
        }
    }
    
    @FXML
    public void initialize() {
        System.out.println("Tela carregada!");
        verificar_adm(null);
    }
    
    String Get_Tipo_Passeio(String id_tipo_passeio) {
    	String tipo_passeio = null;
    	
    	System.out.println("Buscando Tipo De Passeio...\n");
		String sql_buscar_tipo_passeio = "SELECT tipos_passeios.descricao FROM meu_banco.tipos_passeios WHERE id_tipo_passeio = ?";
        PreparedStatement ps_buscar_tipo_passeio = null;
        Connection conn_buscar_tipo_passeio = null;
        
        try {
        	conn_buscar_tipo_passeio = Conectar_Banco_Dados.getConnection();
            System.out.println("Conexão estabelecida com sucesso para buscar tipo de passeio: " + (conn_buscar_tipo_passeio != null));
            ps_buscar_tipo_passeio = conn_buscar_tipo_passeio.prepareStatement(sql_buscar_tipo_passeio);
            ps_buscar_tipo_passeio.setString(1, id_tipo_passeio);
            ResultSet busca_tipo_passeio = ps_buscar_tipo_passeio.executeQuery();
            
            if(busca_tipo_passeio.next()) {
            	tipo_passeio = busca_tipo_passeio.getString(1);
            }
            
            System.out.println("Busca De Tipo De Passeio Realizada Com Sucesso!");
        }catch (Exception erro_ao_definir_usuario_logado) {
        	  erro_ao_definir_usuario_logado.printStackTrace();
        }
    	return tipo_passeio;
    }

    @FXML
    void Pesquisar(ActionEvent event) {
    	String item_pesquisa = barra_busca.getText();
    	TableView<Passeio> tabela_resultado = new TableView<>();
    	
    	TableColumn<Passeio, Integer> idColumn = new TableColumn<>("ID Passeio");
        TableColumn<Passeio, String> nomeColumn = new TableColumn<>("Nome do Hóspede");
        TableColumn<Passeio, String> dataColumn = new TableColumn<>("Data do Passeio");
        TableColumn<Passeio, Double> valorColumn = new TableColumn<>("Valor");
        TableColumn<Passeio, String> tipoColumn = new TableColumn<>("Tipo do Passeio");
        TableColumn<Passeio, String> registroColumn = new TableColumn<>("Data de Registro");
        TableColumn<Passeio, String> responsavelColumn = new TableColumn<>("Responsável");
        
        idColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdPasseio()));
        nomeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNomeHospede()));
        dataColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDataPasseio()));
        valorColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValor()));
        tipoColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTipoPasseio()));
        registroColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDataRegistro()));
        responsavelColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getResponsavel()));
        
        tabela_resultado.getColumns().addAll(idColumn, nomeColumn, dataColumn, valorColumn, tipoColumn, registroColumn, responsavelColumn);
    	
        System.out.println("Iniciando Busca De Passeio...\n");
        System.out.print("========= LOG DE BUSCA =========\n\n");
		String sql_busca = "SELECT id_passeio, nome_do_hospede, data_do_passeio, valor, id_tipo_passeio, data_de_registro_passeio, id_responsavel_registro_passeio FROM passeios WHERE nome_do_hospede LIKE ? OR data_do_passeio LIKE ? OR id_passeio LIKE ?";
        PreparedStatement ps_busca = null;
        Connection conn_busca= null;
        
        try {
        	conn_busca = Conectar_Banco_Dados.getConnection();
            System.out.println("\nConexão estabelecida com sucesso para Pesquisa: " + (conn_busca != null));
            ps_busca = conn_busca.prepareStatement(sql_busca);
            ps_busca.setString(1, "%"+item_pesquisa+"%");
            ps_busca.setString(2, "%"+item_pesquisa+"%");
            ps_busca.setString(3, "%"+item_pesquisa+"%");
            ResultSet resultado_pesquisa = ps_busca.executeQuery();
            if(item_pesquisa.isEmpty()) {
            	System.out.println("Iniciando Busca Para Todos Os Passeios");
            }else {
                System.out.print("Iniciando Busca para: "+item_pesquisa);
            }
            ObservableList<Passeio> passeios = FXCollections.observableArrayList();
            
            while(resultado_pesquisa.next()) {
            	int idPasseio = resultado_pesquisa.getInt("id_passeio");
                String nomeHospede = resultado_pesquisa.getString("nome_do_hospede");
                String dataPasseio = resultado_pesquisa.getString("data_do_passeio");
                String dataPasseio_formatada = Formatar_Datas.Formatar_Para_Usuario(dataPasseio);
                double valor = resultado_pesquisa.getDouble("valor");
                String tipoPasseio = resultado_pesquisa.getString("id_tipo_passeio");
                String nome_passeio = Get_Tipo_Passeio(tipoPasseio);
                String dataRegistro = resultado_pesquisa.getString("data_de_registro_passeio");
                String dataRegistro_formatada = Formatar_Datas.Formatar_Para_Usuario(dataRegistro);
                String responsavel = resultado_pesquisa.getString("id_responsavel_registro_passeio");

                Passeio passeio = new Passeio(idPasseio, nomeHospede, dataPasseio_formatada, valor, nome_passeio, dataRegistro_formatada, responsavel);
                
                passeios.add(passeio);
            }
            if (passeios.isEmpty()) {
            	System.out.print("\n\nNenhum Dado Encontrado Para: "+item_pesquisa+"\n");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Tabela Vazia");
                alert.setHeaderText(null);
                alert.setContentText("Não Foi Encontrada Nenhuma Reserva Com Esses Dados Informados!");
                alert.showAndWait();
            }else {
            	if(item_pesquisa.isEmpty()) {
            		System.out.print("Mostando Todas As Reservas De Passeios");
            	}else {
            		System.out.print("\nDados Encontrados Para: "+item_pesquisa+". Iniciando Tabela...\n");
            	}
            	tabela_resultado.setItems(passeios);
                
                VBox layout = new VBox(10, tabela_resultado);
                Scene scene = new Scene(layout, 800, 400);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Exibição de Passeios");
                stage.show();
                System.out.println("Tabela Inciada Com Sucesso!");
            }  
            System.out.println("\n===== FIM DO LOG DE BUSCA ======\n");
        }catch (Exception erro_ao_pesquisar) {
            erro_ao_pesquisar.printStackTrace();
        }
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
    }

    @FXML
    void Cadastrar_Passeio(ActionEvent event) {
    	
    }

    @FXML
    void Copiar_Caminho(ActionEvent event) {

    }

    @FXML
    void verificar_adm(Event event) {
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
            		tab_configuracoes.setDisable(false);
            		System.out.print("O usuario: "+id_usuario+" é ADM");
            	}else {
            		tab_configuracoes.setDisable(true);
            		System.out.print("O usuario: "+id_usuario+" NÃO é ADM");
            	}
            }
           
        }catch (Exception erro_ao_definir_usuario_logado) {
      	  erro_ao_definir_usuario_logado.printStackTrace();
        }
        
        System.out.print("\n\n========== FIM DO LOG DE VERIFICAÇÃO DE ADM ===========\n\n");
    }
    
    @FXML
    void Adicionar_Colaborador(ActionEvent event) {
    	try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/tela_adicionarColaborador.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Adicionar Usuarios");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception erro_ao_abrir_tela_principal) {
        	erro_ao_abrir_tela_principal.printStackTrace();
        }
    }

    @FXML
    void Abrir_Menu_Adicionar_Usuario(ActionEvent event) {
    	try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/tela_adicionarUsuario.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Adicionar Usuarios");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception erro_ao_abrir_tela_principal) {
        	erro_ao_abrir_tela_principal.printStackTrace();
        }
    }

    @FXML
    void Abrir_Menu_Relatorios(ActionEvent event) {
    	
    }
    
    @FXML
    void Adicionar_Passeio(ActionEvent event) {

    }

}
