import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

public class P08IncreaseMinionsAge {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "12345");

        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        int[] inputIds = Arrays.stream(scanner.nextLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();


        for (int i = 0; i < inputIds.length; i++) {
            int currentId = inputIds[i];
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE `minions` " +
                            "SET `name` = LOWER(`name`), " +
                            "\t`age` = `age` + 1 " +
                            "WHERE `id` = (?);");


            statement.setInt(1, currentId);
            statement.executeUpdate();

        }


        PreparedStatement resultStatement = connection.prepareStatement(
                "SELECT `name`, `age` FROM `minions`;");

        ResultSet resultSet = resultStatement.executeQuery();

        while (resultSet.next()){
            String minionName = resultSet.getString("name");
            int age = resultSet.getInt("age");
            System.out.printf("%s %d%n", minionName, age);


        }


    }
}
