import java.sql.*;
import java.util.Properties;

public class P02GetVillainsNames {
    public static void main(String[] args) throws SQLException {

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "12345");

        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

//        Решение на задачата createStatement
        ResultSet result = connection.createStatement().executeQuery(  "SELECT `name`, COUNT(DISTINCT(`mv`.`minion_id`)) AS `count` " +
                "FROM `villains` AS `v` " +
                "JOIN `minions_villains` AS `mv` ON `v`.`id` = `mv`.`villain_id` " +
                "GROUP BY `name` " +
                "HAVING `count` > 15 " +
                "ORDER BY `count` DESC;");


//        Решение на задачата с prepareStatement
//        PreparedStatement statement = connection.prepareStatement(
//                "SELECT `name`, COUNT(DISTINCT(`mv`.`minion_id`)) AS `count` " +
//                        "FROM `villains` AS `v` " +
//                        "JOIN `minions_villains` AS `mv` ON `v`.`id` = `mv`.`villain_id` " +
//                        "GROUP BY `name` " +
//                        "HAVING `count` > ? " +
//                        "ORDER BY `count` DESC;");
//
//        statement.setInt(1, 15);
//
//        ResultSet result = statement.executeQuery();

        while (result.next()) {
            String villainName = result.getString("name");
            int count = result.getInt("count");
            System.out.printf("%s %d%n", villainName, count);
        }

        connection.close();
    }
}