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

@WebServlet("/GoldLoanServlet")
public class GoldLoanFile extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String FullName = req.getParameter("FullName").toUpperCase();
        String Husband = req.getParameter("Husband").toUpperCase();
        String Mobile = req.getParameter("Mobile");
        String Address = req.getParameter("Address").toUpperCase();
        String Adhaar = req.getParameter("Adhaar");
        String Pan = req.getParameter("Pan");
        String DOB = req.getParameter("DOB");
        String Income = req.getParameter("Income");
        String Things = req.getParameter("Things");
        String Weight = req.getParameter("Weight");
        String Purity = req.getParameter("Purity");
        String LoanAmount = req.getParameter("LoanAmount");
        String Duration = req.getParameter("Duration");
        String LoanType = req.getParameter("LoanType");

        Connection con = null;

        try {
            con = DBConnect.getConnection();
            con.setAutoCommit(false);  

           
            PreparedStatement pmst = con.prepareStatement(
                "INSERT INTO GoldLoan VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
            );

            pmst.setString(1, FullName);
            pmst.setString(2, Husband);
            pmst.setString(3, Mobile);
            pmst.setString(4, Address);
            pmst.setString(5, Adhaar);
            pmst.setString(6, Pan);
            pmst.setString(7, DOB);
            pmst.setString(8, Income);
            pmst.setString(9, Things);
            pmst.setString(10, Weight);
            pmst.setString(11, Purity);
            pmst.setString(12, LoanAmount);
            pmst.setString(13, Duration);
            pmst.setString(14, LoanType);

            int i = pmst.executeUpdate();

           
            PreparedStatement pmst2 = con.prepareStatement(
                "UPDATE Bank_Account SET BALANCE = BALANCE - ? WHERE MOBILE = ?"
            );

            pmst2.setDouble(1, Double.parseDouble(LoanAmount));
            pmst2.setString(2, Mobile);

            int j = pmst2.executeUpdate();

            
            if (i > 0 && j > 0) {
                con.commit();

                out.println("<h2 style='color:green;'>Gold Loan Approved Successfully</h2>");
                out.println("<p>Loan Amount Deducted from Bank Balance</p>");
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