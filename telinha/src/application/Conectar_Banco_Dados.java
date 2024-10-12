package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

public class Conectar_Banco_Dados {
   private static Connection conn;
   static boolean erro_na_conexao;
   
   public boolean Get_Erro_Conexao() {
		return erro_na_conexao;
   }
   
   public static Connection Get_Connection_Status() {
	   return conn;
   }

   static {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
      } catch (ClassNotFoundException var1) {
         var1.printStackTrace();
         throw new RuntimeException("MySQL JDBC Driver not found", var1);
      }
   }

   public Conectar_Banco_Dados() {
   }

   public static Connection getConnection() {
      if (conn == null) {
         try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/meu_banco", "root", "");
            System.out.println("----Conectar_Banco_Dados - Conexão sucesso---\n");
         } catch (CommunicationsException commEx) {
        	 conn = null;
             System.err.println("Erro: Não foi possível se conectar ao banco de dados. Verifique se o XAMPP está iniciado e o MySQL está rodando.");
             commEx.printStackTrace();
             erro_na_conexao = true;
         }catch (SQLException sqlEx) {
             sqlEx.printStackTrace();
             throw new RuntimeException("Erro na conexão com o banco de dados - ", sqlEx);
          }
      }

      return conn;
   }

   public static void closeConnection() {
      if (conn != null) {
         try {
            conn.close();
         } catch (SQLException var4) {
            var4.printStackTrace();
         } finally {
            conn = null;
         }
      }

   }
}