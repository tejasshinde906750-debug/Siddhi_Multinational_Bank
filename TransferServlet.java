package com.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/TransferServlet")
public class TransferServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String fromAccStr = req.getParameter("fromAcc");
        String toAccStr   = req.getParameter("toAcc");
        String amtStr     = req.getParameter("amount");

        Connection con = null;

        try {
            // ✅ Validation
            if (fromAccStr == null || toAccStr == null || amtStr == null ||
                fromAccStr.trim().isEmpty() || toAccStr.trim().isEmpty() || amtStr.trim().isEmpty()) {

                out.println("<h3 style='color:red;'>All fields are required</h3>");
                return;
            }

            int fromAcc = Integer.parseInt(fromAccStr.trim());
            int toAcc   = Integer.parseInt(toAccStr.trim());
            double amount = Double.parseDouble(amtStr.trim());

            if (fromAcc == toAcc) {
                out.println("<h3 style='color:red;'>Cannot transfer to same account</h3>");
                return;
            }

            if (amount <= 0) {
                out.println("<h3 style='color:red;'>Invalid Amount</h3>");
                return;
            }

            con = DBConnect.getConnection();
            con.setAutoCommit(false); // 🔥 Start transaction

            // ✅ Check sender balance
            double balance = 0;

            try (PreparedStatement ps1 = con.prepareStatement(
                    "SELECT BALANCE FROM Bank_Account WHERE ACCOUNT_NO=?")) {

                ps1.setInt(1, fromAcc);
                ResultSet rs1 = ps1.executeQuery();

                if (!rs1.next()) {
                    con.rollback();
                    out.println("<h3 style='color:red;'>Sender Account Not Found</h3>");
                    return;
                }

                balance = rs1.getDouble("BALANCE");
            }

            if (balance < amount) {
                con.rollback();
                out.println("<h3 style='color:red;'>Insufficient Balance</h3>");
                return;
            }

            // ✅ Check receiver
            try (PreparedStatement psCheck = con.prepareStatement(
                    "SELECT ACCOUNT_NO FROM Bank_Account WHERE ACCOUNT_NO=?")) {

                psCheck.setInt(1, toAcc);
                ResultSet rs2 = psCheck.executeQuery();

                if (!rs2.next()) {
                    con.rollback();
                    out.println("<h3 style='color:red;'>Receiver Account Not Found</h3>");
                    return;
                }
            }

            // ✅ Debit
            int debit;
            try (PreparedStatement ps2 = con.prepareStatement(
                    "UPDATE Bank_Account SET BALANCE = BALANCE - ? WHERE ACCOUNT_NO=?")) {

                ps2.setDouble(1, amount);
                ps2.setInt(2, fromAcc);
                debit = ps2.executeUpdate();
            }

            // ✅ Credit
            int credit;
            try (PreparedStatement ps3 = con.prepareStatement(
                    "UPDATE Bank_Account SET BALANCE = BALANCE + ? WHERE ACCOUNT_NO=?")) {

                ps3.setDouble(1, amount);
                ps3.setInt(2, toAcc);
                credit = ps3.executeUpdate();
            }

            if (debit == 0 || credit == 0) {
                throw new Exception("Transaction update failed");
            }

            // ✅ Commit
            con.commit();

            out.println("<h3 style='color:green;'>Transfer Successful</h3>");

        } catch (Exception e) {

            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            e.printStackTrace(); // server console only
            out.println("<h3 style='color:red;'>Transaction Failed</h3>");

        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}