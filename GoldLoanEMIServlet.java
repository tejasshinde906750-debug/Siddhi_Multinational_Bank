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

@WebServlet("/GoldLoanEMIServlet")
public class GoldLoanEMIServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String emiStr = req.getParameter("GoldLoanEMI");
        String mobile = req.getParameter("Mobile");

        try {
            int emi = Integer.parseInt(emiStr);

            Connection con = DBConnect.getConnection();

            String sql =
                "UPDATE GoldLoan " +
                "SET PAIDAMOUNT = PAIDAMOUNT + ?, " +
                "OUTSTANDING = LOANAMOUNT - (PAIDAMOUNT + ?) " +
                "WHERE MOBILE = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, emi);
            ps.setInt(2, emi);
            ps.setString(3, mobile);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                out.println("EMI Paid Successfully");
            } else {
                out.println("No record found for this Mobile Number");
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error : " + e.getMessage());
        }
    }
}