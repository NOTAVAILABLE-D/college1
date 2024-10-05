import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static String url ="jdbc:mysql://localhost/Record";
    public static String user = "root";
    public static String password ="";
    public static void main(String[] args) {
        listStudent();
    }
    public static void listStudent() {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Student")) {
            int columCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columCount; i++) {
                System.out.print(resultSet.getMetaData().getColumnName(i) + "\t");
            }
            System.out.println();

            while (resultSet.next())
            {
                for (int i = 1; i <= columCount; i++) {
                    System.out.print(resultSet.getString(i)+"\t");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
