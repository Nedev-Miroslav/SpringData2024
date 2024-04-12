import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class P03GetMinionNames {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        int inputId = Integer.parseInt(scanner.nextLine());

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "12345");

        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);


        PreparedStatement statement = connection.prepareStatement(
                "SELECT `name` FROM `villains` WHERE `id` = ?"
                );
        statement.setInt(1, inputId);

        ResultSet villainSet = statement.executeQuery();

        if(!villainSet.next()) {
            System.out.printf("No villain with ID %d exists in the database.", inputId);
            return;
        }

        String villainName = villainSet.getString("name");
        System.out.printf("Villain: %s%n", villainName);

        PreparedStatement minionStatement =
                connection.prepareStatement(
                        "SELECT `m`.`name` , `m`.`age` " +
                                "FROM `minions` AS `m` " +
                                "JOIN `minions_villains` AS `mv` ON `m`.`id` = `mv`.`minion_id` " +
                                "WHERE `villain_id` = ?;"
                );

        minionStatement.setInt(1, inputId);

        ResultSet minionSet = minionStatement.executeQuery();
        int position = 1;
        while (minionSet.next()){
            String name = minionSet.getString("name");
            int age = minionSet.getInt("age");

            System.out.printf("%d. %s %d%n", position, name, age);
            position++;
        }

        connection.close();
    }
}
