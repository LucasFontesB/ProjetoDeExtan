package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class App extends Application{

    public static void main(String[] args) {
       List<Integer> ids_com_turnos_abertos = new ArrayList<>();
       System.out.println("\nIniciando Verificação De Turnos Abertos...\n");
       System.out.println("============== LOG DE TURNOS ABERTOS ==============\n");
	   String sql_verificar_ids_turnos_abertos = "SELECT id_usuario FROM registros_turnos WHERE horario_final IS NULL";
       PreparedStatement ps_verificar_ids_turnos_abertos = null;
       Connection conn_verificar_ids_turnos_abertos = null;
       
       try {
     	   conn_verificar_ids_turnos_abertos = Conectar_Banco_Dados.getConnection();
           System.out.println("Conexão estabelecida com sucesso para Verificar Turnos Abertos: " + (conn_verificar_ids_turnos_abertos != null));
           ps_verificar_ids_turnos_abertos = conn_verificar_ids_turnos_abertos.prepareStatement(sql_verificar_ids_turnos_abertos);
           ResultSet id_usuario_achado_turnos_abertos = ps_verificar_ids_turnos_abertos.executeQuery();
           
           while(id_usuario_achado_turnos_abertos.next()) {
         	  int id_usuario = id_usuario_achado_turnos_abertos.getInt(1);
         	  ids_com_turnos_abertos.add(id_usuario);
           }
           
           if(ids_com_turnos_abertos.isEmpty()) {
        	   System.out.print("Não Há Usuarios Com Turnos Abertos\n");
        	   System.out.println("\n========== FIM DO LOG DE TURNOS ABERTOS ===========\n");
           }else {
        	   for(int id_usuario : ids_com_turnos_abertos) {
            	   System.out.println("Iniciando Fechamento De Turno Do Id: " + id_usuario);
            	   String sql_pegar_horario_inicial = "SELECT horario_inicio FROM registros_turnos WHERE horario_final IS NULL AND id_usuario = ?";
                   PreparedStatement ps_pegar_horario_inicial = null;
                   Connection conn_pegar_horario_inicial = null;
                   
                   try {
                	   conn_pegar_horario_inicial = Conectar_Banco_Dados.getConnection();
                       System.out.println("Conexão estabelecida com sucesso para Pescar Horário De Inicio: " + (conn_pegar_horario_inicial != null));
                       ps_pegar_horario_inicial = conn_pegar_horario_inicial.prepareStatement(sql_pegar_horario_inicial);
                       ps_pegar_horario_inicial.setInt(1, id_usuario);
                       ResultSet horario_inicio = ps_pegar_horario_inicial.executeQuery();
                       
                       if(horario_inicio.next()) {
                    	   LocalDateTime horarioInicio = horario_inicio.getObject("horario_inicio", LocalDateTime.class);
                    	   LocalDateTime data_hora_atual = LocalDateTime.now();
                    	   System.out.println("Horario De Início: "+horarioInicio);
                    	   System.out.println("Horario Atual: "+data_hora_atual);
                    	    
                    	    Duration duracao = Duration.between(horarioInicio, data_hora_atual);
                    	    long diffHoras = duracao.toHours();

                    	    System.out.println("Duração do turno em horas: " + diffHoras + " Horas");
                    	    
                    	    if(diffHoras > 12) {
                    	    	System.out.print("A diferença de horas do primeiro login do usuario com ID: " + id_usuario + " É de: " + diffHoras + " Horas. Seu turno será AUTOMATICAMENTE FECHADO\n");
                    	    	System.out.println("\nRegistrando Horário Final No Registra Turno...\n");
                      		    String sql_registrar_horario_final_registra_turno = "UPDATE registros_turnos SET horario_final = ? WHERE id_usuario = ?";
    		                    PreparedStatement ps_registrar_horario_final_registra_turno = null;
    		                    Connection conn_registrar_horario_final_registra_turno = null;
    		                      
    		                    try {
    		                    	conn_registrar_horario_final_registra_turno = Conectar_Banco_Dados.getConnection();
    			                    System.out.println("Conexão estabelecida com sucesso para Registrar Horário FINAL No Registra Turno: " + (conn_registrar_horario_final_registra_turno != null));
    			                    ps_registrar_horario_final_registra_turno = conn_registrar_horario_final_registra_turno.prepareStatement(sql_registrar_horario_final_registra_turno);
    			                    ps_registrar_horario_final_registra_turno.setObject(1, data_hora_atual);
    			                    ps_registrar_horario_final_registra_turno.setInt(2, id_usuario);
    			                    ps_registrar_horario_final_registra_turno.executeUpdate();
    			                      
    			                    System.out.println("Horário Final Resgistrado Com Sucesso\n");
    			                    System.out.print("Fechamento Automatico Do Turno Do Usuario Com ID: "+id_usuario+" Foi Realizado Com Sucesso!");
    		                    }catch (Exception erro_ao_definir_usuario_logado) {
    		                    	erro_ao_definir_usuario_logado.printStackTrace();
    		                    }	    	
                    	    }else {
                    	    	System.out.print("A diferença de horas do primeiro login do usuario com ID: " + id_usuario + " É de: " + diffHoras + " Horas. Seu turno NÃO será automaticamente fechado\n");
                    	    }
                       }
                       
                   }catch (Exception erro_ao_definir_usuario_logado) {
                  	  erro_ao_definir_usuario_logado.printStackTrace();
                   }
               }
               
               System.out.println("\n========== FIM DO LOG DE TURNOS ABERTOS ===========\n");
           }}catch (Exception erro_ao_buscar_turnos_abertos) {
        	   erro_ao_buscar_turnos_abertos.printStackTrace();
           }
       		launch(args);
       }
        	   
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/tela_login.fxml"));
        Parent root = fxmlLoader.load();
        Scene tela = new Scene(root);
        
        primaryStage.setTitle("Gerenciador De Passeios");
        primaryStage.setScene(tela);
        primaryStage.show();
    }
}
