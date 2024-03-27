import java.sql.*;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

public class Diablo {
    public static void main(String[] args) throws SQLException {

        // Connect to DB

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "12345");

        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/diablo", properties);


        // Execute Query
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Username: ");
        String username = scanner.nextLine();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT `u`.`id`, `u`.`first_name`, `u`.`last_name`, COUNT(`ug`.`game_id`) " +
                        "FROM `users` AS `u` " +
                        "JOIN `users_games` AS `ug` ON `ug`.`user_id` = `u`.`id` " +
                        "WHERE `u`.`user_name` = ?;");

        preparedStatement.setString(1, username);
        ResultSet result = preparedStatement.executeQuery();

        // Print Result
        result.next();
        Object userId = result.getObject(1);
        if(userId != null) {
            System.out.printf("User: %s%n%s %s has played %d games",
                    username,
                    result.getString(2),
                    result.getString(3),
                    result.getInt(4));
        }else {
            System.out.println("No such user exists");
        }

    }
}
