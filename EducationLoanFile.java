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

@WebServlet("/EducationLoanServlet")
public class EducationLoanFile extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String Name = req.getParameter("Name").toUpperCase();
        String Father = req.getParameter("Father").toUpperCase();
        String Mobile = req.getParameter("Mobile");
        String LoanType = req.getParameter("LoanType");
        String Collage = req.getParameter("Collage").toUpperCase();
        String Course = req.getParameter("Course");
        String LoanAmount = req.getParameter("LoanAmount");
        String Duration = req.getParameter("Duration");

        Connection con = null;

        try {
            con = DBConnect.getConnection();
            con.setAutoCommit(false); 

            // 1️⃣ Insert Education Loan
            PreparedStatement pmst = con.prepareStatement(
                "INSERT INTO EducationLoan VALUES(?,?,?,?,?,?,?,?)"
            );

            pmst.setString(1, Name);
            pmst.setString(2, Father);
            pmst.setString(3, Mobile);
            pmst.setString(4, LoanType);
            pmst.setString(5, Collage);
            pmst.setString(6, Course);
            pmst.setString(7, LoanAmount);
            pmst.setString(8, Duration);

            int i = pmst.executeUpdate();

            
            PreparedStatement pmst2 = con.prepareStatement(
                "UPDATE Bank_Account SET BALANCE = BALANCE - ? WHERE MOBILE = ?"
            );

            pmst2.setDouble(1, Double.parseDouble(LoanAmount));
            pmst2.setString(2, Mobile);

            int j = pmst2.executeUpdate();

            
            if (i > 0 && j > 0) {
                con.commit();

                out.println("<h2 style='color:green;'>Education Loan Approved Successfully</h2>");
                out.print("Passed Loan Amount"+LoanAmount);
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

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
    
    
}