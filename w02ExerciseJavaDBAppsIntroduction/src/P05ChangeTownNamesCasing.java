import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class P05ChangeTownNamesCasing {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "12345");

        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        String inputCountry = scanner.nextLine();

        PreparedStatement updateStatement = connection.prepareStatement(
                "UPDATE `towns` " +
                        "SET `name` = UPPER(`name`) " +
                        "WHERE `country` = ?;"
        );

        updateStatement.setString(1, inputCountry);

        int count = updateStatement.executeUpdate();

        if(count == 0) {
            System.out.printf("No town names were affected.");
            return;
        }


        PreparedStatement resultUpdateStatement = connection.prepareStatement(
                "SELECT `name` " +
                        "FROM `towns` " +
                        "WHERE `country` = ?;");


        resultUpdateStatement.setString(1, inputCountry);
        ResultSet resultSet = resultUpdateStatement.executeQuery();

        List<String> upperTowns = new ArrayList<>();

        while (resultSet.next()){
            String currentTown = resultSet.getString("name");
            upperTowns.add(currentTown);

        }

        System.out.printf("%d town names were affected.%n", upperTowns.size());
        System.out.println(upperTowns);

        connection.close();
    }
}
