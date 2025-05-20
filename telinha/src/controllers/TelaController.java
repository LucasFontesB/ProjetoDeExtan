package controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import application.Buscar_Passeios;

import application.Buscar_Passeios.PasseioSimplificado;
import application.Conectar_Banco_Dados;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import application.Formatar_Datas;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
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
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import application.App;

public class TelaController {
    
    @FXML
    private ImageView add_passeio;

    @FXML
    private ImageView botao_sair;

    @FXML
    private Button botao_adicionar_colaborador;

    @FXML
    private Button botao_adicionar_usuario;

    @FXML
	public TextField barra_busca;

    @FXML
    private Button botao_relatorio;
    
    @FXML
    private TextField comprovante_label;
    
    @FXML
    private Text nome_usuario;
    
    @FXML
    private Button botao_adicionar_tipoPasseios;
    
    public static boolean fazer_busca = true;
    
    public static boolean Get_Busca_Inicial() {
    	return fazer_busca;
    }
    
    public static void Set_Busca_Inicial_False() {
    	fazer_busca = false;
    }
    
    public static void Set_Busca_Inicial_True() {
    	fazer_busca = true;
    }
    
    @FXML
    private TableView<PasseioSimplificado> tabela_passeios_futuros;
    @FXML
    private TableColumn<PasseioSimplificado, Integer> idPasseioColumn;
    @FXML
    private TableColumn<PasseioSimplificado, String> nomeHospedeColumn;
    @FXML
    private TableColumn<PasseioSimplificado, String> dataPasseioColumn;
    @FXML
    private TableColumn<PasseioSimplificado, String> tipoPasseioColumn;
    @FXML
    private TableColumn<PasseioSimplificado, String> statusPasseioColumn;
    
    public TableView<PasseioSimplificado> Get_TabelaPrincipal() {
    	return tabela_passeios_futuros;
    }
    
    private void Abrir_Menu_Reserva(int id_passeio) {
    	MenuPasseioController menuPasseioController = new MenuPasseioController();
        menuPasseioController.abrirTela(id_passeio);
    }
   
    @SuppressWarnings("rawtypes")
	@FXML
    public void initialize() {
    	int id_usuario = LoginController.Get_Id_Usuario_Logado();
    	
        System.out.println("Buscando Nome De Usuario...\n");
		String sql_registrar_horario_registra_turno = "SELECT nome FROM usuarios WHERE id_usuario = ?";
        PreparedStatement ps_registrar_horario_registra_turno = null;
        Connection conn_registrar_horario_registra_turno = null;
        
        try {
      	  conn_registrar_horario_registra_turno = Conectar_Banco_Dados.getConnection();
            System.out.println("Conexão estabelecida com sucesso para Buscar Nome: " + (conn_registrar_horario_registra_turno != null));
            ps_registrar_horario_registra_turno = conn_registrar_horario_registra_turno.prepareStatement(sql_registrar_horario_registra_turno);
            ps_registrar_horario_registra_turno.setInt(1, id_usuario);
            ResultSet result = ps_registrar_horario_registra_turno.executeQuery();
            
            if (result.next()) {
            	String nome = result.getString("nome");
            	nome_usuario.setText("nome");
            }
            
            System.out.println("Horário Resgistrado Com Sucesso\n");
        }catch (Exception erro_ao_definir_usuario_logado) {
      	  erro_ao_definir_usuario_logado.printStackTrace();
        }
    	
    	nome_usuario.setText(null);
    	PauseTransition Timer_Para_Pesquisa = new PauseTransition(Duration.millis(750));
        System.out.println("Tela carregada!");
        verificar_adm(null);  
        
        tabela_passeios_futuros.setOnMousePressed((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                PasseioSimplificado passeio_selecionado = tabela_passeios_futuros.getSelectionModel().getSelectedItem();
                
                if (passeio_selecionado != null) {
                	int id_passeio = passeio_selecionado.getIdPasseioSimplificado();
                	Abrir_Menu_Reserva(id_passeio);
                }
            }
        });
        
        barra_busca.setOnKeyReleased(event -> {
            String item_pesquisa = barra_busca.getText();
            Timer_Para_Pesquisa.setOnFinished(e -> Buscar_Passeios.BuscarSimplificado(this, item_pesquisa));
            Timer_Para_Pesquisa.playFromStart();
        });
        
        idPasseioColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdPasseioSimplificado()));
        nomeHospedeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNomeHospedeSimplificado()));
        dataPasseioColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDataPasseioSimplificado()));
        tipoPasseioColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTipoPasseioSimplificado()));
        statusPasseioColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStatusSimplificado()));
        
        Platform.runLater(() -> {
            Buscar_Passeios.BuscarSimplificado(this, null);
        });}
        
        
 	    
    @FXML
    private void abrir_menu_add_passeio(MouseEvent event){  
    	try {
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/novatelaaddpasseios.fxml"));
    		Parent root = fxmlLoader.load();		                     
    		Stage stage = new Stage();
    		stage.setResizable(false);
    		stage.setTitle("Adicionar Passeios");
    		stage.setScene(new Scene(root));
    		stage.show();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    @FXML
    void Sair_Conta(MouseEvent event) {
    	TelaController.Set_Busca_Inicial_True();
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/novateladelogin.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Gerenciador De Passeios");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception erro_ao_abrir_tela_principal) {
        	erro_ao_abrir_tela_principal.printStackTrace();
        }
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
            		//tab_configuracoes.setDisable(false);
            		System.out.print("O usuario: "+id_usuario+" é ADM");
            	}else {
            		//tab_configuracoes.setDisable(true);
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
            stage.setResizable(false);
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
            stage.setResizable(false);
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
    void Adicionar_Tipo_Passeio(ActionEvent event) {

    }

}