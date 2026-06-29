package com.backend;



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String AccountNo = req.getParameter("accno");
        String amount = req.getParameter("amount");

        PrintWriter out = resp.getWriter();

        try {
            Connection con = DBConnect.getConnection();

            PreparedStatement psCheck = con.prepareStatement(
                "SELECT * FROM Bank_Account WHERE ACCOUNT_NO = ?");
            psCheck.setString(1, AccountNo);

            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {

                con.setAutoCommit(false);

                PreparedStatement ps1 = con.prepareStatement(
                    "INSERT INTO Transactions VALUES (txn_seq.NEXTVAL, ?, 'CREDIT', ?, SYSDATE)");
                ps1.setString(1, AccountNo);
                ps1.setString(2, amount);
                ps1.executeUpdate();

                PreparedStatement ps2 = con.prepareStatement(
                    "UPDATE Bank_Account SET BALANCE = BALANCE + ? WHERE ACCOUNT_NO = ?");
                ps2.setString(1, amount);
                ps2.setString(2, AccountNo);
                ps2.executeUpdate();

                con.commit();

                out.println("Amount Deposited Successfully...!!!   ::"+amount);

            } else {
                out.println("Invalid Account....");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
