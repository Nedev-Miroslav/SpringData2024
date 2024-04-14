import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class P07PrintAllMinionNames {
    public static void main(String[] args) throws SQLException {

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "12345");

        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        PreparedStatement statement = connection.prepareStatement(
                "SELECT `name` FROM `minions`;");

        ResultSet resultSet = statement.executeQuery();

        List<String> minonsList = new ArrayList<>();

        while (resultSet.next()){
            String currentMinion = resultSet.getString("name");

            minonsList.add(currentMinion);

        }

        while (!minonsList.isEmpty()) {
            String firstMinion = minonsList.get(0);
            minonsList.remove(0);

            String lastMinion = minonsList.get(minonsList.size() - 1);
            minonsList.remove(minonsList.size() - 1);

            System.out.println(firstMinion);
            System.out.println(lastMinion);
        }
    }
}
