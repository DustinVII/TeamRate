package com.teamrate;

import com.teamrate.database.DatabaseManager;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {


        Connection connection = DatabaseManager.getConnection();
        if (connection == null) {
            System.err.println("Connection could not be established.");
        } else {
            System.out.println("");
        }


    }

}