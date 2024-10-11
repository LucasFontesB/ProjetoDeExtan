package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import application.Conectar_Banco_Dados;

import controllers.LoginController;

import java.time.LocalDateTime;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


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
    	try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/tela_adicionarPasseio.fxml"));
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
    
    public class Passeio {
        private static int idPasseio;
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
        System.out.print("========= LOG DE BUSCA =========\n");
		String sql_busca = "SELECT id_passeio, nome_do_hospede, data_do_passeio, valor, id_tipo_passeio, data_de_registro_passeio, id_responsavel_registro_passeio FROM passeios WHERE nome_do_hospede LIKE ? OR data_do_passeio LIKE ?";
        PreparedStatement ps_busca = null;
        Connection conn_busca= null;
        
        try {
        	conn_busca = Conectar_Banco_Dados.getConnection();
            System.out.println("\nConexão estabelecida com sucesso para Pesquisa: " + (conn_busca != null));
            ps_busca = conn_busca.prepareStatement(sql_busca);
            ps_busca.setString(1, "%"+item_pesquisa+"%");
            ps_busca.setString(2, "%"+item_pesquisa+"%");
            ResultSet resultado_pesquisa = ps_busca.executeQuery();
            System.out.print("Iniciando Busca para: "+item_pesquisa);
            
            ObservableList<Passeio> passeios = FXCollections.observableArrayList();
            
            while(resultado_pesquisa.next()) {
            	int idPasseio = resultado_pesquisa.getInt("id_passeio");
                String nomeHospede = resultado_pesquisa.getString("nome_do_hospede");
                String dataPasseio = resultado_pesquisa.getString("data_do_passeio");
                String dataPasseio_formatada = Formatar_Datas.Formatar_Para_Usuario(dataPasseio);
                double valor = resultado_pesquisa.getDouble("valor");
                String tipoPasseio = resultado_pesquisa.getString("id_tipo_passeio");
                String dataRegistro = resultado_pesquisa.getString("data_de_registro_passeio");
                String dataRegistro_formatada = Formatar_Datas.Formatar_Para_Usuario(dataRegistro);
                String responsavel = resultado_pesquisa.getString("id_responsavel_registro_passeio");

                Passeio passeio = new Passeio(idPasseio, nomeHospede, dataPasseio_formatada, valor, tipoPasseio, dataRegistro_formatada, responsavel);
                
                passeios.add(passeio);
            }
            if (passeios.isEmpty()) {
            	System.out.print("\n\nNenhum Dado Encontrado Para: "+item_pesquisa+"\n");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Tabela Vazia");
                alert.setHeaderText(null);
                alert.setContentText("A tabela não contém dados.");
                alert.showAndWait();
            }else {
            	System.out.print("\n\nDados Encontrados Para: "+item_pesquisa+". Iniciando Tabela...\n");
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

}
