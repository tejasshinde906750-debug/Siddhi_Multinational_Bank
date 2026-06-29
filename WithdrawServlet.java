package com.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/WithdrawServlet")
public class WithdrawServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int accNo = Integer.parseInt(req.getParameter("accno"));
        int amount = Integer.parseInt(req.getParameter("amount"));

        PrintWriter out = resp.getWriter();

        try {
            Connection con = DBConnect.getConnection();

            PreparedStatement psCheck = con.prepareStatement(
                "SELECT BALANCE FROM Bank_Account WHERE ACCOUNT_NO = ?");
            psCheck.setInt(1, accNo);

            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {

                double balance = rs.getDouble("BALANCE");

                if (balance >= amount) {

                    con.setAutoCommit(false);

                    PreparedStatement ps1 = con.prepareStatement(
                        "INSERT INTO Transactions VALUES (txn_seq.NEXTVAL, ?, 'DEBIT', ?, SYSDATE)");
                    ps1.setInt(1, accNo);
                    ps1.setInt(2, amount);
                    ps1.executeUpdate();

                   
                    PreparedStatement ps2 = con.prepareStatement(
                        "UPDATE Bank_Account SET BALANCE = BALANCE - ? WHERE ACCOUNT_NO = ?");
                    ps2.setInt(1, amount);
                    ps2.setInt(2, accNo);
                    ps2.executeUpdate();

                    con.commit();

                    out.println("  Withdraw Successful ");
                   
                    out.println("Your Account No:"+accNo+ "   from withdraw  Rs:"+amount);

                } else {
                    out.println("<h2 style='color:red;'>Insufficient Balance </h2>");
                }

            } else {
                out.println("<h2 style='color:red;'>Invalid Account </h2>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}