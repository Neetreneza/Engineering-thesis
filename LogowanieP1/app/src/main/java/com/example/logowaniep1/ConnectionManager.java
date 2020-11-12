package com.example.logowaniep1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionManager {
    private static String url = "jdbc:mysql://192.168.1.100:3306/aplikacja";
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String username = "andro";
    private static String password = "andro";
    private static Connection con;
    private static String urlstring;

    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Błąd\n");
            System.out.println(e);
        }
        return con;
    }
//    public List<String> list() throws SQLException {
//        List<String> bilers = new ArrayList<String>();
//
//        try (
//                Connection connection = database.getConnection();
//                PreparedStatement statement = connection.prepareStatement("SELECT id, name, value FROM Biler");
//                ResultSet resultSet = statement.executeQuery();
//        ) {
//            while (resultSet.next()) {
//                Biler biler = new Biler();
//                biler.setId(resultSet.getLong("id"));
//                biler.setName(resultSet.getString("name"));
//                biler.setValue(resultSet.getInt("value"));
//                bilers.add(biler);
//            }
//        }
//
//        return bilers;
//    }

}