import java.util.Arrays;
import java.util.Scanner;
import java.sql.*;


public class Lab4Database {
    public static void main(String[] args) throws SQLException, ClassNotFoundException{
  /*       Scanner keyboard = new Scanner(System.in);
        String input = "Initialized"; //initialized variable
        do { //repeating until program is terminated
            System.out.println("Pomona Transit System Main Menu");
            System.out.println("Enter 'q' to exit the program:");
            System.out.println("1) Display Schedule");
            System.out.println("2) Edit Schedule");
            System.out.println("3) Display Stops");
            System.out.println("4) Add a Drive");
            System.out.println("5) Add a Bus");
            System.out.println("6) Delete a Bus\n");
            input = keyboard.nextLine();
            System.out.println();
            if(!input.equals("Initialized") && !input.equals("1")
                && !input.equals("2") && !input.equals("3") 
                && !input.equals("4") && !input.equals("5") 
                && !input.equals("6") && !input.equals("q")) {
                System.out.println("Wrong input please try again\n");
            }
        } while(!input.equals("q"));
        keyboard.close(); */

        final String DB_URL = "jdbc:mysql://localhost:3306/Lab4";
        final String USERNAME = "root";
        final String PASSWORD = "LaBamba2";

        Connection con = null;
        Statement st = null;


        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        System.out.println("Database Connected Successfully");
        st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM bus");
        //rs.next();

        while(rs.next()) {

        String bus_id = rs.getString("bus_id");
        String model = rs.getString("model");
        String year = rs.getString("year");

        System.out.println(model + " " + bus_id + " " + year);
        }
        
        st.close();
        con.close();
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void displaySchedule() throws SQLException, ClassNotFoundException {
        final String DB_URL = "jdbc:mysql://localhost:3306/Lab4";
        final String USERNAME = "root";
        final String PASSWORD = "LaBamba2";

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter the StartLocationName:");
        String startLocationName = keyboard.nextLine();
        System.out.println("Please enter the DestinationName:");
        String destinationName = keyboard.nextLine();
        System.out.println("Please enter the Date:");
        String date = keyboard.nextLine();
        
        Connection con = null;
        Statement st = null;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT t.start_location_name, t.destination_name, " 
                + "tt.date, tt.scheduled_start_time, tt.scheduled_arrival_time, " + 
                "tt.driver_name, tt.bus_id FROM trip t, trip_offering tt " + 
                "WHERE t.trip_number = tt.trip_number" + 
                " AND t.start_location_name ='" + startLocationName + "' AND " +
                "t.destination_name ='" + destinationName + "' AND " +
                "tt.date = '" + date + "'");
            
            System.out.println("\nSchedule:\n");
            System.out.println("StartLocation Destination Date       ScheduledStartTime ScheduledArrivalTime DriverName   BusID ");
    
            while(rs.next()) {
    
            String scheduled_start_time = rs.getString("tt.scheduled_start_time");
            String scheduled_arrival_time = rs.getString("tt.scheduled_arrival_time");
            String driver_name = rs.getString("tt.driver_name");
            String bus_id = rs.getString("tt.bus_id");
    
            System.out.format("%13s %11s %10s %18s %20s %12s %5s\n", startLocationName, destinationName,
            date, scheduled_start_time, scheduled_arrival_time, driver_name, bus_id);
            }
            
            System.out.println();

            st.close();
            con.close();
            }catch(SQLException e)
            {
                e.printStackTrace();
            }
    }

    public void editScheduleMenu() throws SQLException, ClassNotFoundException {
        Scanner keyboard = new Scanner(System.in);
        String input = "Initialized"; //initialized variable
        do { //repeating until program is terminated
            System.out.println("Edit Schedule Menu");
            System.out.println("Enter 'q' to go back to main menu:");
            System.out.println("1) Delete trip offering");
            System.out.println("2) Add trip offering");
            System.out.println("3) Change Driver for trip offering");
            System.out.println("4) Change Bus for trip offering");
            System.out.print("Enter option: ");
            input = keyboard.nextLine();
            System.out.println();
            if(!input.equals("Initialized") && !input.equals("1")
                && !input.equals("2") && !input.equals("3") 
                && !input.equals("4") && !input.equals("q")) {
                System.out.println("Wrong input please try again\n");
            }

            if (input.equals("1")) 
                deleteTripOffering();
            else if (input.equals("2")) 
                addTripOffering();
            else if (input.equals("3")) 
                changeDriver();
            else if (input.equals("4")) 
                changeBus();
        } while(!input.equals("Initialized") && !input.equals("1")
            && !input.equals("2") && !input.equals("3") 
            && !input.equals("4") && !input.equals("5") 
            && !input.equals("6") && !input.equals("q"));
    }
    public static void deleteTripOffering() throws SQLException, ClassNotFoundException {
        final String DB_URL = "jdbc:mysql://localhost:3306/Lab4";
        final String USERNAME = "root";
        final String PASSWORD = "LaBamba2";

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter the Trip Number:");
        String tripNumber = keyboard.nextLine();
        System.out.println("Please enter the Date:");
        String date = keyboard.nextLine();
        System.out.println("Please enter the Scheduled Start Time:");
        String scheduledStartTime = keyboard.nextLine();
        
        Connection con = null;
        Statement st = null;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            st = con.createStatement();
            st.executeUpdate("DELETE FROM trip_offering " 
                + "WHERE trip_number = '" + tripNumber + "' AND " + 
                "date = '" + date + "' AND scheduled_start_time = '" + scheduledStartTime + "';");

            System.out.println("\nDeleted\n");
            
            st.close();
            con.close();
            }catch(SQLException e)
            {
                e.printStackTrace();
            }
    }

    public static void addTripOffering() throws SQLException, ClassNotFoundException {
        final String DB_URL = "jdbc:mysql://localhost:3306/Lab4";
        final String USERNAME = "root";
        final String PASSWORD = "LaBamba2";
        String redo = "redo";
        
        do {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Please enter the Trip Number:");
            String tripNumber = keyboard.nextLine();
            System.out.println("Please enter the Date:");
            String date = keyboard.nextLine();
            System.out.println("Please enter the Scheduled Start Time:");
            String scheduledStartTime = keyboard.nextLine();
            System.out.println("Please enter the Scheduled Arrival Time:");
            String scheduledArrivalTime = keyboard.nextLine();
            System.out.println("Please enter the Driver Name:");
            String driverName = keyboard.nextLine();
            System.out.println("Please enter the Bus ID:");
            String busID = keyboard.nextLine();
            
            Connection con = null;
            Statement st = null;
            
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                st = con.createStatement();
                st.executeUpdate("INSERT INTO trip_offering " 
                    + "(trip_number, date, scheduled_start_time, scheduled_arrival_time, driver_name, bus_id)" + 
                    " VALUES (" + tripNumber + ", '" + date + "', '" + scheduledStartTime + "', '" + scheduledArrivalTime + "', '" +
                    driverName + "', " + busID + ");");

                System.out.println("\nAdded\n");
                System.out.print("Would you like to add another? If so enter y, else enter another key: ");
                redo = keyboard.nextLine();
                
                System.out.println();
                st.close();
                con.close();
                }catch(SQLException e)
                {
                    e.printStackTrace();
                }
        } while(redo.equals("y"));
    }

    public static void changeDriver() throws SQLException, ClassNotFoundException {
        final String DB_URL = "jdbc:mysql://localhost:3306/Lab4";
        final String USERNAME = "root";
        final String PASSWORD = "LaBamba2";

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter the Trip Number:");
        String tripNumber = keyboard.nextLine();
        System.out.println("Please enter the Date:");
        String date = keyboard.nextLine();
        System.out.println("Please enter the Scheduled Start Time:");
        String scheduledStartTime = keyboard.nextLine();
        System.out.println("Please enter new driver's name:");
        String driverName = keyboard.nextLine();
        
        Connection con = null;
        Statement st = null;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            st = con.createStatement();
            st.executeUpdate("UPDATE trip_offering SET driver_name = '" + driverName + "' " 
                + "WHERE trip_number = '" + tripNumber + "' AND " + 
                "date = '" + date + "' AND scheduled_start_time = '" + scheduledStartTime + "';");

            System.out.println("\nChanged the bus driver for this trip offering\n");
            
            st.close();
            con.close();
            }catch(SQLException e)
            {
                e.printStackTrace();
            }
    }

    public static void changeBus() throws SQLException, ClassNotFoundException {
        final String DB_URL = "jdbc:mysql://localhost:3306/Lab4";
        final String USERNAME = "root";
        final String PASSWORD = "LaBamba2";

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter the Trip Number:");
        String tripNumber = keyboard.nextLine();
        System.out.println("Please enter the Date:");
        String date = keyboard.nextLine();
        System.out.println("Please enter the Scheduled Start Time:");
        String scheduledStartTime = keyboard.nextLine();
        System.out.println("Please enter new Bus ID:");
        String busID = keyboard.nextLine();
        
        Connection con = null;
        Statement st = null;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            st = con.createStatement();
            st.executeUpdate("UPDATE trip_offering SET bus_id = '" + busID + "' " 
                + "WHERE trip_number = '" + tripNumber + "' AND " + 
                "date = '" + date + "' AND scheduled_start_time = '" + scheduledStartTime + "';");

            System.out.println("\nChanged the bus driver for this trip offering\n");
            
            st.close();
            con.close();
            }catch(SQLException e)
            {
                e.printStackTrace();
            }
    }

    public void displayScheduleOfDriver() throws SQLException, ClassNotFoundException {
        final String DB_URL = "jdbc:mysql://localhost:3306/Lab4";
        final String USERNAME = "root";
        final String PASSWORD = "LaBamba2";

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter the Driver's Name:");
        String busDriver = keyboard.nextLine();
        System.out.println("Please enter the Date:");
        String date = keyboard.nextLine();

        Connection con = null;
        Statement st = null;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT t.start_location_name, t.destination_name, tt.date, " 
            +"tt.scheduled_start_time, tt.scheduled_arrival_time, tt.driver_name, tt.bus_id FROM trip t, " 
            + "trip_offering tt WHERE t.trip_number = tt.trip_number AND tt.driver_name = '" + busDriver + "' AND WEEK( tt.date ) IN (SELECT WEEK ('" + date + "') );");

            System.out.println("\nSchedule:\n");
            System.out.println("StartLocation Destination Date       ScheduledStartTime ScheduledArrivalTime DriverName   BusID ");
    
            while(rs.next()) {
    
            String startLocationName = rs.getString("t.start_location_name");
            String destinationName = rs.getString("t.destination_name");
            String scheduled_start_time = rs.getString("tt.scheduled_start_time");
            String scheduled_arrival_time = rs.getString("tt.scheduled_arrival_time");
            String driver_name = rs.getString("tt.driver_name");
            String bus_id = rs.getString("tt.bus_id");
            date = rs.getString("tt.date");
    
            System.out.format("%13s %11s %10s %18s %20s %12s %5s\n", startLocationName, destinationName,
            date, scheduled_start_time, scheduled_arrival_time, driver_name, bus_id);
            }
            
            System.out.println();

            st.close();
            con.close();
            }catch(SQLException e)
            {
                e.printStackTrace();
            }
    }

    public void transfer() throws SQLException, ClassNotFoundException {
        final String DB_URL = "jdbc:mysql://localhost:3306/Lab4";
        final String USERNAME = "root";
        final String PASSWORD = "LaBamba2";

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter the Trip Number:");
        String tripNumber = keyboard.nextLine();
        System.out.println("Please enter the Date:");
        String date = keyboard.nextLine();
        System.out.println("Please enter the Scheduled Start Time:");
        String scheduledStartTime = keyboard.nextLine();

        Connection con = null;
        Statement st = null;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT scheduled_arrival_time, driver_name," + 
            "bus_id FROM trip_offering WHERE trip_number = " + tripNumber + " " +
            "AND date = '" + date + "' " + "AND scheduled_start_time = '" 
            + scheduledStartTime + "';");

    
            String scheduled_arrival_time = "Initialized Variable";

            while(rs.next()) {
    
            scheduled_arrival_time = rs.getString("scheduled_arrival_time");
            String driver_name = rs.getString("driver_name");
            String bus_id = rs.getString("bus_id");
    
            }

            st.executeUpdate("INSERT INTO actual_trip_stop_info (trip_number, date, " 
            + "scheduled_start_time, scheduled_arrival_time) VALUES (" + tripNumber 
            + ", '" + date + "', '" + scheduledStartTime + "', '" + 
            scheduled_arrival_time + "');");
            
            System.out.println("Transferred Data");

            st.close();
            con.close();
            }catch(SQLException e)
            {
                e.printStackTrace();
            }
    }
}