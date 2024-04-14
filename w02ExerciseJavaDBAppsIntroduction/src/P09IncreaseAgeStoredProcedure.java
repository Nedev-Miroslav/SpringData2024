import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class P09IncreaseAgeStoredProcedure {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "12345");

        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        int minionId = Integer.parseInt(scanner.nextLine());

        CallableStatement callableStatement = connection.prepareCall(
                "CALL usp_get_older(?)");

        callableStatement.setInt(1, minionId);
        ResultSet resultSet = callableStatement.executeQuery();
        resultSet.next();
        String name = resultSet.getString("name");
        int age = resultSet.getInt("age");

        System.out.println(name + " " + age);
    }
}
