package Controllers;

import Entities.Job;
import Main.Main;
import acceso_datos.conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller_Jobs {

    /**
     * Attributes
     */
    private String jobName = "";
    private String jobState = "";
    private String jobInformation = "";
    private final int INVALID_OPTION = -1;
    private final int SEE_JOBS_OPTION = 1;


    // Constructor
    public Controller_Jobs() {}

    /**
     * Get the elements in the view
     */
    @FXML private Button enviar;
    @FXML private TextArea console;
    @FXML private TextField input;

    /**
     * Methods for the elements
     */
    // Handle back button
    public void onClick_Atras(ActionEvent actionEvent) throws IOException {
        // Create instance of main
        Main main = new Main();
        // Launch main activity
        main.changeScene("/views/Pantalla_Inicio.fxml");
    }

    // Handle onClick in 'enviar' button
    public void onClick_Enviar(ActionEvent event) throws SQLException {
        // Get the text of the user input
        String inputText = input.getText();

        // Validate input
        if (inputText.equals("")) return;

        // Update text area with user input
        if (!console.getText().equals(""))
            console.setText(console.getText() + "\n$ " + inputText);
        else console.setText("$ " + inputText);

        // Clear text on input field
        input.clear();

        // Validate user input
        String response = "";
        int validation = validateUserInput(inputText);
        if (validation == INVALID_OPTION) {
            response = "Invalid option, valid options : \n see jobs \n enable job <Job Name> \n disable job <Job Name>";
        }
        // Check if the user input was "see jobs"
        else if (validation == SEE_JOBS_OPTION) {
            response = ""; // Set response string
            printJobs(); // Method that prints all the Jobs name and status
        }
        // Otherwise the user input was to change the state of a job
        else {

            // Validate if the job exists
            if (jobExists(jobName) == false) {
                response = "Job " + jobName + " is not a valid Job Name";
            }
            // If job exists, print the job, the status and the information
            else {
                response = "Job " + jobName + " " + jobState + "d ... \n";
                response += jobInformation;
            }
        }
        // Print response in console
        console.setText(console.getText() + "\n" + response);
    }

    // Method to print all jobs (Name and status)
    private void printJobs() throws SQLException {
        // Define connections parameters
        Connection conex = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        /**
         * Query to get all the jobs name and its status
         */
        try{
            // Make DB connection
            conex = conexion.getConnection();
            // Prepare SQL statement
            ps = conex.prepareStatement("SELECT OWNER, JOB_NAME, JOB_CLASS, COMMENTS, ENABLED, CREDENTIAL_NAME, DESTINATION, PROGRAM_NAME, JOB_TYPE, JOB_ACTION, NUMBER_OF_ARGUMENTS, SCHEDULE_OWNER, SCHEDULE_NAME, SCHEDULE_TYPE, START_DATE, REPEAT_INTERVAL, END_DATE FROM ALL_SCHEDULER_JOBS");
            // Execute query
            rs = ps.executeQuery();
            // Traverse results
            String columns = "\n[ Owner , Job Name , Status ] ... \n";
            console.setText(console.getText() + columns);
            while(rs.next()){
                String job = rs.getString("OWNER") + " , " + rs.getString("JOB_NAME") + " , " + rs.getString("ENABLED") + "\n";
                console.setText(console.getText() + job);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } finally {
            // Close DB connections
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conex != null) conex.close();
        }
    }

    /**
     * Method to check if the given job exists in the DB or not
     * @param jobName
     * @return true if Job exists, false if not
     */
    private boolean jobExists(String jobName) throws SQLException {
        // Define connections parameters
        Connection conex = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        /**
         * Query to get all the Jobs in the DB and compare the User Input job name to see if exists
         */
        try{
            // Make DB connection
            conex = conexion.getConnection();
            // Prepare SQL statement
            ps = conex.prepareStatement("SELECT OWNER, JOB_NAME, JOB_CLASS, COMMENTS, ENABLED, CREDENTIAL_NAME, DESTINATION, PROGRAM_NAME, JOB_TYPE, JOB_ACTION, NUMBER_OF_ARGUMENTS, SCHEDULE_OWNER, SCHEDULE_NAME, SCHEDULE_TYPE, START_DATE, REPEAT_INTERVAL, END_DATE FROM ALL_SCHEDULER_JOBS");
            // Execute query
            rs = ps.executeQuery();
            // Traverse results
            while(rs.next()){
                // Check if the job name that the user input exists
                if (rs.getString("JOB_NAME").equals(jobName)) {
                    String owner = rs.getString("OWNER");
                    String jobToChangeState = owner + "." + jobName;
                    String sqlCommand = "EXECUTE dbms_scheduler." + jobState + "('" + jobToChangeState + "')";
                    // Execute change of state in job
                    ps = conex.prepareStatement(sqlCommand);
                    if (ps.executeUpdate() != 1) System.out.println("Error executing command");
                    // Get all the information fields
                    jobInformation = "------ Job ------ \n";
                    jobInformation += "OWNER : " + owner + "\n";
                    jobInformation += "JOB_NAME : " + rs.getString("JOB_NAME") + "\n";
                    jobInformation += "JOB_CLASS : " + rs.getString("JOB_CLASS") + "\n";
                    jobInformation += "COMMENTS :" + rs.getString("COMMENTS") + "\n";
                    jobInformation += "ENABLED : " + rs.getString("ENABLED") + "\n";
                    jobInformation += "CREDENTIAL_NAME : " + rs.getString("CREDENTIAL_NAME") + "\n";
                    jobInformation += "DESTINATION : " + rs.getString("DESTINATION") + "\n";
                    jobInformation += "PROGRAM_NAME : " + rs.getString("PROGRAM_NAME") + "\n";
                    jobInformation += "JOB_TYPE : " + rs.getString("JOB_TYPE") + "\n";
                    jobInformation += "JOB_ACTION : " + rs.getString("JOB_ACTION") + "\n";
                    jobInformation += "NUMBER_OF_ARGUMENTS : " + rs.getString("NUMBER_OF_ARGUMENTS") + "\n";
                    jobInformation += "SCHEDULE_OWNER : " + rs.getString("SCHEDULE_OWNER") + "\n";
                    jobInformation += "SCHEDULE_NAME : " + rs.getString("SCHEDULE_NAME") + "\n";
                    jobInformation += "SCHEDULE_TYPE : " + rs.getString("SCHEDULE_TYPE") + "\n";
                    jobInformation += "START_DATE : " + rs.getString("START_DATE") + "\n";
                    jobInformation += "REPEAT_INTERVAL : " + rs.getString("REPEAT_INTERVAL") + "\n";
                    jobInformation += "END_DATE : " + rs.getString("END_DATE") + "\n";
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } finally {
            // Close DB connections
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conex != null) conex.close();
        }

        // If this point is reached, return false
        return false;
    }

    /**
     * Method to check user input
     * @param userInput, Not null or empty
     * @return Integer representing the status of the user input (-1 -> Invalid option, 1 -> see jobs, 2 -> enable/disable jobs)
     */
    public int validateUserInput(String userInput) {
        // Check input using RegEx
        String regEx = "((enable|disable) job +([a-zA-Z0-9\\_\\-]+)\\s?)|(see jobs)";
        Pattern pattern = Pattern.compile(regEx); // Make pattern to match
        Matcher matcher = pattern.matcher(userInput); // Instance of matcher Class
        // If user input doesn't match, return false
        if (!matcher.find()) {
            return -1;
        }
        // Check the case that the user want to see the jobs
        else if (userInput.equals("see jobs")) {
            return 1;
        }
        // Otherwise, return true and store the name of the job
        else {
            jobState = matcher.group(2); // The state that the job will be changed to
            jobName = matcher.group(3); // The name of the job is in the group 3 of the regex
            return 2;
        }
    }
}