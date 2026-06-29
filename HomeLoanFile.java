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

@WebServlet("/HomeLoanServlet")
public class HomeLoanFile extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String FullName = req.getParameter("FullName");
        String Husband = req.getParameter("Husband");
        String Mobile = req.getParameter("Mobile");
        String Adhaar = req.getParameter("Adhaar");
        String LoanType = req.getParameter("LoanType");
        String LoanAmount = req.getParameter("LoanAmount");
        String Date = req.getParameter("Date");
        String Address = req.getParameter("Address");

        Connection con = null;

        try {
            con = DBConnect.getConnection();
            con.setAutoCommit(false); 

           
            PreparedStatement pmst = con.prepareStatement(
                "INSERT INTO HomeLoan VALUES(?,?,?,?,?,?,?,?)"
            );

            pmst.setString(1, FullName);
            pmst.setString(2, Husband);
            pmst.setString(3, Mobile);
            pmst.setString(4, Adhaar);
            pmst.setString(5, LoanType);
            pmst.setString(6, LoanAmount);
            pmst.setString(7, Date);
            pmst.setString(8, Address);

            int i = pmst.executeUpdate();

            
            PreparedStatement pmst2 = con.prepareStatement(
                "UPDATE Bank_Account SET BALANCE = BALANCE - ? WHERE MOBILE = ?"
            );

            pmst2.setDouble(1, Double.parseDouble(LoanAmount));
            pmst2.setString(2, Mobile);

            int j = pmst2.executeUpdate();

            
            if (i > 0 && j > 0) {
                con.commit();

                out.println("<h2 style='color:green;'>Home Loan Approved Successfully</h2>");
                out.println("<p>Loan Amount Deducted from Bank Balance</p>");
            } else {
                con.rollback();

                out.println("<h2 style='color:red;'>Something went wrong, try again</h2>");
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