package org.olmedo.apiservlet.webapp.session.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {

    private static String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=UTC";
    private static String username = "root";
    private static String password = "sasa";

  public static Connection getConnection() throws SQLException {
      return DriverManager.getConnection(url, username, password);
  }
}
