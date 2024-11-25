 package com.carvia.models.conexion;
 
 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.SQLException;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 
 public class conexion {
     private static final Logger logger = Logger.getLogger(conexion.class.getName());
 
     private String dataBaseURL;
     private String driverName;
     private String user;
     private String password;
     private Connection conexion;
 
     public String getDataBaseURL() {
         return dataBaseURL;
     }
 
     public void setDataBaseURL(String dataBaseURL) {
         this.dataBaseURL = dataBaseURL;
     }
 
     public String getDriverName() {
         return driverName;
     }
 
     public void setDriverName(String driverName) {
         this.driverName = driverName;
     }
 
     public String getUser() {
         return user;
     }
 
     public void setUser(String user) {
         this.user = user;
     }
 
     public String getPassword() {
         return password;
     }
 
     public void setPassword(String password) {
         this.password = password;
     }
 
     public conexion() {
         this.dataBaseURL = "jdbc:mysql://localhost:3310/instituto?autoReconnect=true&useSSL=true";
         this.driverName = "com.mysql.cj.jdbc.Driver"; 
         this.user = "carvia";
         this.password = "";
     }
 
     public void abrirConexion() throws Exception {
         if (dataBaseURL.isEmpty() || user.isEmpty() || password.isEmpty() || driverName.isEmpty()) {
             logger.log(Level.SEVERE, "Error al crear la conexión (¿están inicializados?) con estos valores:");
             this.mostrarValoresConexion();
             throw new Exception("Valores de conexión incompletos");
         }
 
         try {
             Class.forName(this.driverName);
             this.conexion = DriverManager.getConnection(this.dataBaseURL, this.user, this.password);
             logger.log(Level.INFO, "Conexión establecida con la base de datos");
         } catch (ClassNotFoundException e) {
             throw new Exception("Driver no encontrado: " + e.getMessage());
         } catch (SQLException e) {
             throw new Exception("Error al abrir la base de datos: " + e.getMessage());
         }
     }
 
     public void cerrarConexion() throws Exception {
         if (this.conexion != null) {
             try {
                 this.conexion.close();
                 logger.log(Level.INFO, "Cierre correcto de la conexión con la base de datos");
             } catch (SQLException e) {
                 throw new Exception("Error al cerrar la conexión de la base de datos: " + e.getMessage());
             }
         } else {
             logger.log(Level.WARNING, "Intento de cerrar una conexión que ya está cerrada o no inicializada");
         }
     }
 
     private void mostrarValoresConexion() {
         logger.log(Level.INFO, "URL: {0}", this.dataBaseURL);
         logger.log(Level.INFO, "Driver: {0}", this.driverName);
         logger.log(Level.INFO, "Usuario: {0}", this.user);
     }
 }
 