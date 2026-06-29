package com.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/VehicleLoanServlet")
public class VehicleLoanFile extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String Name = req.getParameter("Name");
        String father = req.getParameter("father");
        String mobile = req.getParameter("mobile");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String adhaar = req.getParameter("adhaar");
        String pan = req.getParameter("pan");
        String vehicleType = req.getParameter("vehicleType");
        String model = req.getParameter("model");
        String dealer = req.getParameter("dealer");
        String loanType = req.getParameter("loanType");
        String loanAmount = req.getParameter("loanAmount");
        String downPayment = req.getParameter("downPayment");
        String duration = req.getParameter("duration");

        Connection con = null;

        try {
            con = DBConnect.getConnection();
            con.setAutoCommit(false); 

          
            PreparedStatement pmst = con.prepareStatement(
                "INSERT INTO VehicleLoan VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
            );

            pmst.setString(1, Name);
            pmst.setString(2, father);
            pmst.setString(3, mobile);
            pmst.setString(4, email);
            pmst.setString(5, address);
            pmst.setString(6, adhaar);
            pmst.setString(7, pan);
            pmst.setString(8, vehicleType);
            pmst.setString(9, model);
            pmst.setString(10, dealer);
            pmst.setString(11, loanType);
            pmst.setString(12, loanAmount);
            pmst.setString(13, downPayment);
            pmst.setString(14, duration);

            int i = pmst.executeUpdate();

            
            PreparedStatement pmst2 = con.prepareStatement(
                "UPDATE Bank_Account SET BALANCE = BALANCE - ? WHERE MOBILE = ?"
            );

            pmst2.setDouble(1, Double.parseDouble(loanAmount));
            pmst2.setString(2, mobile);

            int j = pmst2.executeUpdate();

            
            if (i > 0 && j > 0) {
                con.commit();

                out.println("<h2 style='color:green;'>Vehicle Loan Approved Successfully</h2>");
                out.println("<p>Amount Deducted from Bank Balance</p>");
            } else {
                con.rollback();

                out.println("<h2 style='color:red;'>Something went wrong</h2>");
            }

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");

        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}