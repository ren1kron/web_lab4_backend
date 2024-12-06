package ren1kron;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseTest {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/web_lab3";
        String username = "ren1kron";
        String password = "ren1kron";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
            if (conn != null) {
                System.out.println("Успешное подключение к базе данных!");
            } else {
                System.out.println("Не удалось подключиться к базе данных.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
