package com.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AdminLoginServlet")
public class AdminLogin extends HttpServlet{
protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

    String username = req.getParameter("user_name");
    String password = req.getParameter("password");
    
  

              try {
               Connection con = DBConnect.getConnection();

                PreparedStatement pmst = con.prepareStatement(
                   "SELECT * FROM Bank_Admin  WHERE user_name=? AND password=?"
                     );

                             pmst.setString(1, username);
                                 pmst.setString(2, password);

                                  ResultSet rs = pmst.executeQuery();
        
        
       

                                             if (rs.next()) {
        	                                   PrintWriter out = resp.getWriter();
        	                                     resp.getWriter().print("Successs");
                                                    resp.sendRedirect("BankDashbord.html");
                                                          } else {
        	                                               PrintWriter out = resp.getWriter();

                                                                  resp.getWriter().println("<h1>Invalid Username or Password</h1>");

            
                                                     }

                                                   } catch (Exception e) {
                                                       e.printStackTrace();
                                }
                               }

                      protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	                  PrintWriter out = resp.getWriter();
	                  out.println("<h1>Server working Successfully..!!!</h1>");	
	                  System.out.println("Login Successfully!!!");
	
                  }      


                 }