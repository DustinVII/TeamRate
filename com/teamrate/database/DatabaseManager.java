package com.teamrate.database;
import com.teamrate.graphics.*;
import java.sql.*;

public class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/teamrate";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";






    // Database verbinding maken method
    public static Connection getConnection() {
        Connection connection = null;


        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            //System.out.println("Connected to database!");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }

        return connection;
    }



    public void fetchUsers() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                String sql = "SELECT playerName FROM players";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String name = resultSet.getString("playerName");
                    System.out.println(name);
                }
            } catch (SQLException e) {
                System.err.println("Geen spelers gevonden: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Fout bij het sluiten van de verbinding met de database: " + e.getMessage());


                }
            }
        }
    }


    public static void insertUser(String name, String password, String birthdate) {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                String sql = "INSERT INTO players (playerName, playerPassword, playerBirthday) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, name);
                statement.setString(2, password);
                statement.setString(3, birthdate);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println(TextColor.WHITE_BOLD_BRIGHT + name + TextColor.WHITE_BRIGHT + " je bent geregistreerd! Druk ENTER om terug te gaan naar het begin." + TextColor.RESET);
                }
            } catch (SQLException e) {
                System.err.println("Fout bij het invoeren van de data: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Fout bij het sluiten van de verbinding met de database: " + e.getMessage());
                }
            }
        }
    }


    public static boolean usernameExists(String username) {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                String sql = "SELECT count(*) FROM players WHERE playerName = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            } catch (SQLException e) {
                System.err.println("Fout bij het natrekken of de user al voorkomt in de database: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Fout bij het sluiten van de verbinding met de database: " + e.getMessage());
                }
            }
        }
        return false;
    }


    public static void changeUsername(String username, int playerid) {
        Connection connection = getConnection();
        if (connection != null) {

            try {
                String sql = "UPDATE players SET playerName = ? WHERE playerID = ? ";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                statement.setInt(2, playerid);
                statement.executeUpdate();

            } catch (SQLException e) {
                System.err.println("Fout bij het invoeren van de data: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Fout bij het sluiten van de verbinding met de database: " + e.getMessage());
                }
            }
        }
    }


    public static void changePassword(String newpass, int playerid) {
        Connection connection = getConnection();
        if (connection != null) {

            try {
                String sql = "UPDATE players SET playerPassword = ? WHERE playerID = ? ";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, newpass);
                statement.setInt(2, playerid);
                statement.executeUpdate();

            } catch (SQLException e) {
                System.err.println("Fout bij het invoeren van de data: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Fout bij het sluiten van de verbinding met de database: " + e.getMessage());
                }
            }
        }
    }


    public static void changeBirthday(String birthday, int playerid) {
        Connection connection = getConnection();
        if (connection != null) {

            try {
                String sql = "UPDATE players SET playerBirthday = ? WHERE playerID = ? ";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, birthday);
                statement.setInt(2, playerid);
                statement.executeUpdate();

            } catch (SQLException e) {
                System.err.println("Fout bij het invoeren van de data: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Fout bij het sluiten van de verbinding met de database: " + e.getMessage());
                }
            }
        }
    }

    public static String getBrithday(String username) {
        Connection connection = getConnection();
        if (connection != null) {
            String sql = "SELECT playerBirthday FROM players WHERE playerName = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("playerBirthday");  //De playerID
                    } else {
                        return "Geen speler gevonden met de username: " + username;
                    }
                }
            } catch (SQLException e) {
                System.err.println("Fout bij het opvragen van de geboortedatum: " + e.getMessage());
                return "Error" + e.getMessage();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Fout bij het sluiten van de verbinding met de database: " + e.getMessage());
                }
            }
        } else {
            System.err.println("Geen speler gevonden met de username: " + username);
            return "Error user " + username + " niet gevonden";
        }

    }


    public static boolean validatePassword(String username, String password) {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                String sql = "SELECT count(*) FROM players WHERE playerName = ? AND playerPassword = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            } catch (SQLException e) {
                System.err.println("Fout bij het inloggen: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Fout bij het sluiten van de verbinding met de database: " + e.getMessage());
                }
            }
        }
        return false;
    }

    public static int getPlayerID(String username) {
        Connection connection = getConnection();
        if (connection != null) {
            String sql = "SELECT playerID FROM players WHERE playerName = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("playerID");  //De playerID
                    } else {
                        System.out.println("Geen speler gevonden met de naam: " + username);
                        return 0;  // Geen speler gevonden
                    }
                }
            } catch (SQLException e) {
                System.err.println("Fout bij het opvragen van de player ID: " + e.getMessage());
                return 0;
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Fout bij het sluiten van de verbinding met de database: " + e.getMessage());
                }
            }
        }
        return 0;
    }


}
