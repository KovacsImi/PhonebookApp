package com.example.demo8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final String URL = "jddc:derby:sampleDB;create=true";
    final String USERNAME = "";
    final String PASSWORD = "";

    //Initialize bridge
    Connection connection = null;
    Statement createSttement = null;
    DatabaseMetaData databaseMetaData = null;

    public DataBase() {
        // try to make connection
        try {
            connection = DriverManager.getConnection(URL);
            System.out.println("The bridge has been initialized!");

        } catch (SQLException e) {
            System.out.println(e);
        }

        if (connection != null) {
            try {
                createSttement = connection.createStatement(); // teherautó
            } catch (SQLException e) {
                System.out.println("Valami baj van a createStatement (a teherautó) létrehozásakor.");
            }


            try {
                databaseMetaData = connection.getMetaData();
            } catch (SQLException e) {
                System.out.println("Valami baj van az adatbázis leírása (DatabaseMetaData) létrehozásakor.");
                System.out.println(e.getMessage());
            }


            try {
                ResultSet resultSet = databaseMetaData.getTables(null, "APP", "CONTACTS", null);
                if (!resultSet.next()) {
                    createSttement.execute("CREATE TABLE CONTACTS(firstname varchar(20), lastname varchar(20), email varchar(30))"); //SQL code
                }
            } catch (SQLException e) {
                System.out.println("Valami baj van az adattáblák létrehozásakor.");
                System.out.println(e.getMessage());
            }


        }

    }

    public List<Human> getAllContacts() {
        String sql = "selec * from contacts";
        List<Human> listOfUsers = null;
        try {
            ResultSet resultSet = createSttement.executeQuery(sql);
            listOfUsers = new ArrayList<>();

            while (resultSet.next()) {
                Human human = new Human(resultSet.getString("firstname"), resultSet.getString("lastname"), resultSet.getString("email"));
                listOfUsers.add(human);
            }

        } catch (SQLException e) {
            System.out.println("Valami baj van a user adatok kiolvasásakor.");
            System.out.println(e.getMessage());
        }

        return listOfUsers;
    }

    public void addContact(Human human) {
        String sql = "insert into contacts values (?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, human.getLastName());
            preparedStatement.setString(2, human.getFirstName());
            preparedStatement.setString(3, human.getEmail());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Valami baj van a kontakt hozzáadásakor.");
            System.out.println(e.getMessage());
        }



    }

    public void updateContact(Human human) {
        String sql = "update contacts set lastname = ?, firstname = ?, email = ? where ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, human.getLastName());
            preparedStatement.setString(2, human.getFirstName());
            preparedStatement.setString(3, human.getEmail());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Valami baj van a kontakt hozzáadásakor.");
            System.out.println(e.getMessage());
        }



    }


}
