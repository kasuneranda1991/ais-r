/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Controllers.Services.PersistsService;
import DAO.ApplicantDAO;
import DAO.RecruitmentDAO;
import DAO.StaffDAO;
import Models.Applicant;
import Models.Recruitment;
import Models.Staff;
import Models.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kinkinigamage
 */
public class DbConnectionManager {
    
    private static DbConnectionManager connectionManager = null;
    private Connection connection;

    private String dbName = "AIS_R_DB";
    private String user = "root";
    private String password = "root";
    private String url = "jdbc:mysql://localhost:3306?useLegacyDatetimeCode=false&serverTimezone=UTC";
    
    private String STAFF_TABLE = "Staff";
    private String APPLICANTS_TABLE = "Applicants";
    
    public DbConnectionManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);

            String createQuery = "CREATE DATABASE IF NOT EXISTS " + dbName;
            String useQuery = "USE " + dbName;

            Statement statement = connection.createStatement();
            statement.addBatch(createQuery);
            statement.addBatch(useQuery);

            statement.executeBatch();

            System.out.println("Database connection successful");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static DbConnectionManager shared() {
        if (connectionManager == null) {
            connectionManager = new DbConnectionManager();
        }

        return connectionManager;
    }
    
    public Connection getConnection() {
        return this.connection;
    }
    
    public void createTablesAndRestoreDataIfNeeded() {
        createStaffTableIfNeeded();
        createRecruitmentTableIfNeeded();
    }
    
    private void createStaffTableIfNeeded() {
        if (!doesTableExist(STAFF_TABLE)) {
            String sql = "CREATE TABLE " + STAFF_TABLE + " (" +
                "userID INT AUTO_INCREMENT PRIMARY KEY, " +
                "firstName VARCHAR(255) NOT NULL, " +
                "lastName VARCHAR(255) NOT NULL, " +
                "address VARCHAR(255) NOT NULL, " +
                "phone INT NOT NULL, " +
                "email VARCHAR(255) NOT NULL, " +
                "username VARCHAR(50) NOT NULL, " +
                "password VARCHAR(50) NOT NULL, " +
                "role VARCHAR(50) NOT NULL, " +
                "branch VARCHAR(50) NOT NULL " +
                "management_lvl VARCHAR(50) NOT NULL " +
                "employment_type VARCHAR(50) NOT NULL " +
                "type VARCHAR(50) NOT NULL " +
                ")";

            try {
                Statement statement = connection.createStatement();
                statement.execute(sql);
                statement.close();
                restoreStaffDataIfAvailable();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    private void createRecruitmentTableIfNeeded() {
        if (!doesTableExist(APPLICANTS_TABLE)) {
            String sql = "CREATE TABLE " + APPLICANTS_TABLE + " (" +
                "applicantID INT AUTO_INCREMENT PRIMARY KEY, " +
                "firstName VARCHAR(255) NOT NULL, " +
                "lastName VARCHAR(255) NOT NULL, " +
                "address VARCHAR(255) NOT NULL, " +
                "phone INT NOT NULL, " +
                "email VARCHAR(255) NOT NULL, " +
                "username VARCHAR(50) NOT NULL, " +
                "password VARCHAR(50) NOT NULL, " +
                "role VARCHAR(50) NOT NULL, " +
                "branch VARCHAR(50) NOT NULL " +
                "interviewDate VARCHAR(50) NOT NULL " +
                "createdBy VARCHAR(50) NOT NULL " +
                "createdAt VARCHAR(50) NOT NULL " +
                "createdBranch VARCHAR(50) NOT NULL " +
                "status VARCHAR(50) NOT NULL " +
                "edu VARCHAR(50) NOT NULL " +
                "department VARCHAR(50) NOT NULL " +
                ")";

            try {
                Statement statement = connection.createStatement();
                statement.execute(sql);
                statement.close();
                restoreRecruitmentDataIfAvailable();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    private void restoreStaffDataIfAvailable() {
        ArrayList<User> staffRecords = PersistsService.get().staffData();
        StaffDAO staffDAO = new StaffDAO();
        
        if (!staffRecords.isEmpty()) {
            for (User record : staffRecords) {
                staffDAO.insertStaff(record);
            }
        }
    }
    
    private void restoreRecruitmentDataIfAvailable() {
        ArrayList<User> applicantRecords = PersistsService.get().applicantsData();
        ApplicantDAO applicantDAO = new ApplicantDAO();
        
        if (!applicantRecords.isEmpty()) {
            for (User record : applicantRecords) {
                applicantDAO.insertApplicant(record);
            }
        }
    }
    
    private boolean doesTableExist(String tableName) {
        String query = String.format("SHOW TABLES LIKE '%s'", tableName);

        try {
            Statement statement = connection.createStatement();
            statement.execute(query);

            return statement.getResultSet().next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
