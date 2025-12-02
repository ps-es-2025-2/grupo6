package model;

import java.sql.SQLException;
import java.util.Objects;

import com.j256.ormlite.jdbc.JdbcConnectionSource;

public class Database {

    private final String jdbcUrl;
    private final String username;
    private final String password;
    private JdbcConnectionSource connection;

    public Database(String jdbcUrl) {
        this(jdbcUrl, null, null);
    }

    public Database(String jdbcUrl, String username, String password) {
        this.jdbcUrl = Objects.requireNonNull(jdbcUrl, "jdbcUrl não pode ser nulo");
        this.username = username;
        this.password = password;
    }

    public synchronized JdbcConnectionSource getConnection() throws SQLException {
        if (connection == null) {
            connection = new JdbcConnectionSource(jdbcUrl, username, password);
        }
        return connection;
    }

    public synchronized void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println("Falha ao fechar conexão: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
}