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

@WebServlet("/BussinessLoanServlet")
public class BussinessLoanFile extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String BusinessName = req.getParameter("BussinessName");
        String Owner = req.getParameter("Owner");
        String Type = req.getParameter("Type");
        String RegNo = req.getParameter("RegNo");
        String LoanType = req.getParameter("LoanType");
        String MonthlyIncome = req.getParameter("MonthlyIncome");
        String LoanAmount = req.getParameter("LoanAmount");
        String Purpose = req.getParameter("Purpose");
        String Experience = req.getParameter("Exceperience");
        String PAN = req.getParameter("PAN");
        String Name = req.getParameter("Name");
        String Mobile = req.getParameter("Mobile");

        Connection con = null;

        try {
            con = DBConnect.getConnection();
            con.setAutoCommit(false); 

           
            PreparedStatement pmst = con.prepareStatement(
                "INSERT INTO BussinessLoan VALUES(?,?,?,?,?,?,?,?,?,?,?,?)"
            );

            pmst.setString(1, BusinessName);
            pmst.setString(2, Owner);
            pmst.setString(3, Type);
            pmst.setString(4, RegNo);
            pmst.setString(5, LoanType);
            pmst.setString(6, MonthlyIncome);
            pmst.setString(7, LoanAmount);
            pmst.setString(8, Purpose);
            pmst.setString(9, Experience);
            pmst.setString(10, PAN);
            pmst.setString(11, Name);
            pmst.setString(12, Mobile);

            int i = pmst.executeUpdate();

            
            PreparedStatement pmst2 = con.prepareStatement(
                "UPDATE Bank_Account SET BALANCE = BALANCE - ? WHERE MOBILE = ?"
            );

            pmst2.setDouble(1, Double.parseDouble(LoanAmount));
            pmst2.setString(2, Mobile);

            int j = pmst2.executeUpdate();

            
            if (i > 0 && j > 0) {
                con.commit();

                out.println("<h2 style='color:green;'>Business Loan Approved Successfully</h2>");
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