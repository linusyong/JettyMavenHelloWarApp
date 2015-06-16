package org.example;
 
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

public class HelloServlet extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    response.setStatus(HttpServletResponse.SC_OK);
    response.getWriter().println("<h1>Hello Servlet</h1>");
    response.getWriter().println("session=" + request.getSession(true).getId());

    Context initCtx = null;
    DataSource ds = null;
    try {
      initCtx = new InitialContext();
      ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/gbds");
      Connection conn = ds.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT SYSDATE FROM DUAL");

      Date currentDate = rset.getDate(1);
      response.getWriter().println("current date from Oracle: " + currentDate);
  
      rset.close();
      stmt.close();
      conn.close();
    } catch(Exception ex) {
      ex.printStackTrace();
    } 
  }
}
