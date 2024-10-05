import java.sql.*;
import java.util.List;


public class MyDataBase {
    public static String url ="jdbc:mysql://localhost/Record";
    public static String user = "root";
    public static String password ="";

    // открыть соединение
    public Connection openConnection() throws SQLException
    {
        return DriverManager.getConnection(url, user, password);
    }

    //закрыть соединение
    public void closeConnection(Connection connection)
    {
        if (connection!=null)
        {
            try
            {
                connection.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void findStudent(String surname)
    {
        Connection connection = null;
        try {
            connection = openConnection();
            String query = "SELECT Student.surname, Student.name, Groups.group_name " +
                    "FROM `Student`, `Groups` " +
                    "WHERE Groups.id_group=Student.id_group AND Student.surname=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,surname);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                String surnam = resultSet.getString("surname");
                String nam = resultSet.getString("name");
                String group_name = resultSet.getString("group_name");
                System.out.println(surnam + " "+nam+" "+group_name);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection);
        }
    }

    public void addStudent(String surname, String name, String lastname, int id_group)
    {
        Connection connection = null;
        try {
            connection = openConnection();
            String query = "INSERT INTO `Student` (`id_student`, `surname`, `name`, `lastname`, `id_group`) " +
                    "VALUES (NULL, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,surname);
            statement.setString(2,name);
            statement.setString(3,lastname);
            statement.setInt(4,id_group);

            int rowsTrue = statement.executeUpdate();
            if (rowsTrue>0) System.out.println("Добавлен");
            else System.out.println("Не хочу работать(((");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection);
        }
    }
    public void selectOneColum(String columName, String tableName, List<Integer> arr)
    {
        arr.clear();
        Connection connection = null;
        try {
            connection = openConnection();
            String query = "SELECT `"+columName+"` FROM `"+tableName+"`;";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                int a = resultSet.getInt(columName);
                arr.add(a);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection);
        }
    }
    public void addVariant()
    {
        RandomVar randomVar = new RandomVar();
        selectOneColum("id_student", "Student", randomVar.idArray);
        randomVar.randVar();
        Connection connection = null;
        try {
            connection = openConnection();
            String query = "SELECT COUNT(*) FROM `Variation`";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
            {
                if (resultSet.getInt(1)!=0)
                {//Если есть записи в таблице, то удалить все
                    query = "DELETE FROM `Variation`";
                    statement.executeUpdate(query);
                }
                for (int i=0; i<randomVar.idArray.size();i++)
                {
                    query = "INSERT INTO `Variation` (`id_var`, `id_student`, `variation`) " +
                            "VALUES (NULL, "+randomVar.idArray.get(i)+" ,"+randomVar.arr[i]+");";
                    statement = connection.prepareStatement(query);
                    statement.executeUpdate();
                }
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection);
        }

    }
}















