package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Collectors;

import application.Buscar_Passeios.PasseioSimplificado;
import controllers.TelaController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Buscar_Passeios {
	
	public static class PasseioSimplificado {
        private int idPasseio;
        private String nomeHospede;
        private String dataPasseio;
        private String tipoPasseio;
        private int status;

        public PasseioSimplificado(int idPasseio, String nomeHospede, String dataPasseio, String tipoPasseio, int status) {
            this.idPasseio = idPasseio;
            this.nomeHospede = nomeHospede;
            this.dataPasseio = dataPasseio;
            this.tipoPasseio = tipoPasseio;
            this.status = status;
        }
              
        public String toString() {
            return "Passeio{" +
                    "idPasseio=" + idPasseio +
                    ", nomeHospede='" + nomeHospede + '\'' +
                    ", dataPasseio='" + dataPasseio + '\'' +
                    ", tipoPasseio='" + tipoPasseio + '\'' +
                    ", status='" + status + '/' +
                    '}';
        }

        public int getIdPasseioSimplificado() { 
        	return idPasseio; 
        }
        public String getNomeHospedeSimplificado() { 
        	return nomeHospede;
        }
        public String getDataPasseioSimplificado() { 
        	return dataPasseio; 
        }
        public String getTipoPasseioSimplificado() { 
        	return tipoPasseio; 
        }
        public int getStatusSimplificado() { 
        	return status; 
        }
    }
	
	public static class Passeio {
        private int idPasseio;
        private String nomeHospede;
        private String dataPasseio;
        private double valor;
        private String tipoPasseio;
        private String dataRegistro;
        private String responsavel;
        private int status;

        public Passeio(int idPasseio, String nomeHospede, String dataPasseio, double valor, String tipoPasseio, String dataRegistro, String responsavel, int status) {
            this.idPasseio = idPasseio;
            this.nomeHospede = nomeHospede;
            this.dataPasseio = dataPasseio;
            this.valor = valor;
            this.tipoPasseio = tipoPasseio;
            this.dataRegistro = dataRegistro;
            this.responsavel = responsavel;
            this.status = status;
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
                    ", status='" + status + '/' +
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
        public int getStatus() { 
        	return status; 
        }
    }
	
	public static void BuscarSimplificado(TelaController telaController, String item_pesquisa) {
		TableView<PasseioSimplificado> tabela_telaPrincipal = telaController.Get_TabelaPrincipal();
		LocalDate hoje = LocalDate.now();
		System.out.println("Fazer Busca Inicial? "+TelaController.fazer_busca);
		System.out.print("Item Para Busca: "+item_pesquisa);

		if(TelaController.fazer_busca == true || item_pesquisa == null) {
		    System.out.println("\nPreenchendo Tabela Incial...\n");
		    System.out.print("========= LOG DE BUSCA INICIAL =========\n\n");
			String sql_busca = "SELECT id_passeio, nome_do_hospede, data_do_passeio, id_tipo_passeio, status_passeio FROM passeios WHERE data_do_passeio >= ?";
		    PreparedStatement ps_busca = null;
		    Connection conn_busca= null;
		    
		    try {
		    	int count = 1;
		    	conn_busca = Conectar_Banco_Dados.getConnection();
		        System.out.println("\nConexão estabelecida com sucesso para Pesquisa Das Reservas Futuras: " + (conn_busca != null));
		        ps_busca = conn_busca.prepareStatement(sql_busca);
		        ps_busca.setObject(1, hoje);
		        ResultSet resultado_pesquisa = ps_busca.executeQuery();
		        
		        ObservableList<PasseioSimplificado> passeios_achados = FXCollections.observableArrayList();
		        
		        while(resultado_pesquisa.next()) {
		        	System.out.print("\n======== INICIANDO BUSCA DE PASSEIOS: "+count+" ========\n");
		        	int idPasseio = resultado_pesquisa.getInt("id_passeio");
		            String nomeHospede = resultado_pesquisa.getString("nome_do_hospede");
		            String dataPasseio = resultado_pesquisa.getString("data_do_passeio");
		            String dataPasseio_formatada = Formatar_Datas.Formatar_Para_Usuario(dataPasseio);
		            String tipoPasseio = resultado_pesquisa.getString("id_tipo_passeio");
		            String nome_passeio = TelaController.Get_Tipo_Passeio(tipoPasseio);
		            int status = resultado_pesquisa.getInt("status_passeio");

		            PasseioSimplificado passeios_futuros = new PasseioSimplificado(idPasseio, nomeHospede, dataPasseio_formatada, nome_passeio, status);
		            
		            passeios_achados.add(passeios_futuros);
		            System.out.println("======= FIM DA BUSCA DE PASSEIO: "+count+" =======\n");
		        }
		        if (passeios_achados.isEmpty()) {
		        	System.out.print("\n\nNenhum Dado Encontrado");
		            Alert alert = new Alert(Alert.AlertType.INFORMATION);
		            alert.setTitle("Tabela Vazia");
		            alert.setHeaderText(null);
		            alert.setContentText("Não Foi Encontrada Nenhuma Reserva Com Esses Dados Informados!");
		            alert.showAndWait();
		        }   	
		        	tabela_telaPrincipal.setItems(passeios_achados);
		            System.out.println("Tabela Inciada Com Sucesso!");
		        System.out.println("\n===== FIM DO LOG DE BUSCA INICIAL ======\n");
		    }catch (Exception erro_ao_pesquisar) {
		        erro_ao_pesquisar.printStackTrace();
		    }
		    TelaController.Set_Busca_Inicial_False();
		    System.out.print("Final para fazer busca: "+TelaController.fazer_busca);
		}else {		
			System.out.println("\nBuscando Reserva Com Dados: "+item_pesquisa+"\n");
		    System.out.print("========= LOG DE BUSCA COM DADO =========\n\n");
			String sql_busca = "SELECT id_passeio, nome_do_hospede, data_do_passeio, id_tipo_passeio, status_passeio FROM passeios WHERE nome_do_hospede LIKE ? OR data_do_passeio LIKE ? OR id_passeio LIKE ?";
		    PreparedStatement ps_busca = null;
		    Connection conn_busca= null;
		    
		    try {
		    	conn_busca = Conectar_Banco_Dados.getConnection();
		        System.out.println("\nConexão estabelecida com sucesso para Pesquisa Das Reservas Futuras: " + (conn_busca != null));
		        ps_busca = conn_busca.prepareStatement(sql_busca);
		        ps_busca.setString(1, "%"+item_pesquisa+"%");
		        ps_busca.setString(2, "%"+item_pesquisa+"%");
		        ps_busca.setString(3, "%"+item_pesquisa+"%");
		        ResultSet resultado_pesquisa = ps_busca.executeQuery();
		        
		        ObservableList<PasseioSimplificado> passeios_achados = FXCollections.observableArrayList();
		        
		        while(resultado_pesquisa.next()) {
		        	int idPasseio = resultado_pesquisa.getInt("id_passeio");
		            String nomeHospede = resultado_pesquisa.getString("nome_do_hospede");
		            String dataPasseio = resultado_pesquisa.getString("data_do_passeio");
		            String dataPasseio_formatada = Formatar_Datas.Formatar_Para_Usuario(dataPasseio);
		            String tipoPasseio = resultado_pesquisa.getString("id_tipo_passeio");
		            String nome_passeio = TelaController.Get_Tipo_Passeio(tipoPasseio);
		            int status = resultado_pesquisa.getInt("status_passeio");

		            PasseioSimplificado passeios_futuros = new PasseioSimplificado(idPasseio, nomeHospede, dataPasseio_formatada, nome_passeio, status);
		            
		            passeios_achados.add(passeios_futuros);
		        }
		        if (passeios_achados.isEmpty()) {
		        	System.out.print("\n\nNenhum Dado Encontrado");
		            Alert alert = new Alert(Alert.AlertType.INFORMATION);
		            alert.setTitle("Tabela Vazia");
		            alert.setHeaderText(null);
		            alert.setContentText("Não Foi Encontrada Nenhuma Reserva Com Esses Dados Informados!");
		            alert.showAndWait();
		        }
		        	System.out.print("Mostando Todas As Reservas De Passeios de: "+hoje+" em diante\n");
		        	System.out.print("\nDados Encontrados. Mostrando Na Tabela...\n");
		        	
		        	DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		            ObservableList<PasseioSimplificado> PasseiosFuturos = passeios_achados.stream().filter(p -> {LocalDate dataPasseio = LocalDate.parse(p.getDataPasseioSimplificado(), formatador);return dataPasseio.isAfter(hoje) || dataPasseio.isEqual(hoje) || dataPasseio.isBefore(hoje);}).sorted(Comparator.comparing(p -> LocalDate.parse(p.getDataPasseioSimplificado(), formatador))).collect(Collectors.toCollection(FXCollections::observableArrayList));
		        	
		        	tabela_telaPrincipal.setItems(PasseiosFuturos);
		            
		        	TelaController.Set_Busca_Inicial_False();
		            
		            System.out.println("Tabela Inciada Com Sucesso!");
		        System.out.println("\n===== FIM DO LOG DE BUSCA INICIAL ======\n");
		    }catch (Exception erro_ao_pesquisar) {
		        erro_ao_pesquisar.printStackTrace();
		    }
			
		}
	}
	
	
	public static void Buscar(TelaController telacontroller, String item_busca) {
		String item_pesquisa = item_busca;
		TableView<Passeio> tabela_resultado = new TableView<>();
		
		TableColumn<Passeio, Integer> idColumn = new TableColumn<>("ID Passeio");
	    TableColumn<Passeio, String> nomeColumn = new TableColumn<>("Nome do Hóspede");
	    TableColumn<Passeio, String> dataColumn = new TableColumn<>("Data do Passeio");
	    TableColumn<Passeio, Double> valorColumn = new TableColumn<>("Valor");
	    TableColumn<Passeio, String> tipoColumn = new TableColumn<>("Tipo do Passeio");
	    TableColumn<Passeio, String> registroColumn = new TableColumn<>("Data de Registro");
	    TableColumn<Passeio, String> responsavelColumn = new TableColumn<>("Responsável");
	    TableColumn<Passeio, Integer> statusColumn= new TableColumn<>("Status");
	    
	    idColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdPasseio()));
	    nomeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNomeHospede()));
	    dataColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDataPasseio()));
	    valorColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValor()));
	    tipoColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTipoPasseio()));
	    registroColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDataRegistro()));
	    responsavelColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getResponsavel()));
	    statusColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStatus()));
	    
	    tabela_resultado.getColumns().addAll(idColumn, nomeColumn, dataColumn, valorColumn, tipoColumn, registroColumn, responsavelColumn, statusColumn);
		
	    System.out.println("Iniciando Busca De Passeio...\n");
	    System.out.print("========= LOG DE BUSCA =========\n\n");
		String sql_busca = "SELECT id_passeio, nome_do_hospede, data_do_passeio, valor, id_tipo_passeio, data_de_registro_passeio, id_responsavel_registro_passeio, status_passeio FROM passeios WHERE nome_do_hospede LIKE ? OR data_do_passeio LIKE ? OR id_passeio LIKE ?";
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
	            String nome_passeio = TelaController.Get_Tipo_Passeio(tipoPasseio);
	            String dataRegistro = resultado_pesquisa.getString("data_de_registro_passeio");
	            String dataRegistro_formatada = Formatar_Datas.Formatar_Para_Usuario(dataRegistro);
	            String responsavel = resultado_pesquisa.getString("id_responsavel_registro_passeio");
	            int status = resultado_pesquisa.getInt("status_passeio");

	            Passeio passeio = new Passeio(idPasseio, nomeHospede, dataPasseio_formatada, valor, nome_passeio, dataRegistro_formatada, responsavel, status);
	            
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
	        		System.out.print("Mostando Todas As Reservas De Passeios\n");
	        	}else {
	        		System.out.print("\nDados Encontrados Para: "+item_pesquisa+". Iniciando Tabela...\n");
	        	}
	            System.out.println("Tabela Inciada Com Sucesso!");
	        }  
	        System.out.println("\n===== FIM DO LOG DE BUSCA ======\n");
	    }catch (Exception erro_ao_pesquisar) {
	        erro_ao_pesquisar.printStackTrace();
	    }
		
	}
	

}
