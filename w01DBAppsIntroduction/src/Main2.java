import java.sql.*;
import java.util.Properties;

public class Main2 {
    public static void main(String[] args) throws SQLException {
        // Connect to DB

        Properties credentials = new Properties();
        credentials.setProperty("user", "root");
        credentials.setProperty("password", "12345");


        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/soft_uni", credentials);

        // Execute Query
        PreparedStatement updateStatement =
                connection.prepareStatement("UPDATE employees SET first_name = ? WHERE employee_id = ?");

        updateStatement.setString(1,"Changed");
        updateStatement.setLong(2,3);

        int updateResult = updateStatement.executeUpdate();
        System.out.println(updateResult);


        PreparedStatement preparedStatement =
                connection.prepareStatement("SELECT * FROM employees WHERE salary > ? LIMIT 10");

        preparedStatement.setDouble(1, 17000.0);

        ResultSet resultSet = preparedStatement.executeQuery();

        // Print Result

        while (resultSet.next()) {
            long employeeId = resultSet.getLong("employee_id");
            String firstName = resultSet.getString("first_name");
            double salary = resultSet.getDouble("salary");
            Timestamp hireDate = resultSet.getTimestamp("hire_date");

            System.out.printf("%d - %s %.2f %s%n",
                    employeeId, firstName, salary, hireDate);
        }

    }
}
