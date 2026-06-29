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

@WebServlet("/PersonalLoanServlet")
public class PersonalLoanFile extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String Name = req.getParameter("Name").toUpperCase();
        String Adhaar = req.getParameter("Adhaar");
        String Pan = req.getParameter("Pan");
        String number = req.getParameter("number");
        String Income = req.getParameter("Income");
        String Office = req.getParameter("Office").toUpperCase();
        String LoanAmount = req.getParameter("LoanAmount");
        String Duration = req.getParameter("Duration");
        String LoanType = req.getParameter("LoanType");
        String Date = req.getParameter("Date");
        String Address = req.getParameter("Address").toUpperCase();

        Connection con = null;

        try {
            con = DBConnect.getConnection();
            con.setAutoCommit(false); 

            
            PreparedStatement pmst = con.prepareStatement(
                "INSERT INTO PersonalLoan VALUES(?,?,?,?,?,?,?,?,?,?,?)"
            );

            pmst.setString(1, Name);
            pmst.setString(2, Adhaar);
            pmst.setString(3, Pan);
            pmst.setString(4, number);
            pmst.setString(5, Income);
            pmst.setString(6, Office);
            pmst.setString(7, LoanAmount);
            pmst.setString(8, Duration);
            pmst.setString(9, LoanType);
            pmst.setString(10, Date);
            pmst.setString(11, Address);

            int i = pmst.executeUpdate();

           
            PreparedStatement pmst2 = con.prepareStatement(
                "UPDATE Bank_Account SET BALANCE = BALANCE - ? WHERE MOBILE = ?"
            );

            pmst2.setDouble(1, Double.parseDouble(LoanAmount));
            pmst2.setString(2, number);

            int j = pmst2.executeUpdate();

           
            if (i > 0 && j > 0) {
                con.commit();

                out.println("<h2 style='color:green;'>Personal Loan Approved Successfully</h2>");
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