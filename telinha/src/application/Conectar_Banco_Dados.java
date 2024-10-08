package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conectar_Banco_Dados {
   private static Connection conn;

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
            System.out.println("----Conectar_Banco_Dados - Conexão sucesso---");
         } catch (SQLException var1) {
            var1.printStackTrace();
            throw new RuntimeException("Failed to connect to the database", var1);
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