import java.util.Scanner;
import java.sql.*;

public class Lab4Demo {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner keyboard = new Scanner(System.in);
        String input = "Initialized"; //initialized variable
        Lab4Database databaseDemo = new Lab4Database();
        do { //repeating until program is terminated
            System.out.println("Pomona Transit System Main Menu");
            System.out.println("Enter 'q' to exit the program:");
            System.out.println("1) Display Schedule");
            System.out.println("2) Edit Schedule");
            System.out.println("3) Display Stops");
            System.out.println("4) Display Weekly Schedule of Driver");
            System.out.println("5) Add a Drive");
            System.out.println("6) Add a Bus");
            System.out.println("7) Delete a Bus");
            System.out.println("8) Transfer Data From TripOffering to ActualTripStopInfo\n");
            System.out.print("Enter option: ");
            input = keyboard.nextLine();
            System.out.println();
            if(!input.equals("Initialized") && !input.equals("1")
                && !input.equals("2") && !input.equals("3") 
                && !input.equals("4") && !input.equals("5") 
                && !input.equals("6") && !input.equals("7")
                && !input.equals("8") && !input.equals("q")) {
                System.out.println("Wrong input please try again\n");
            }

            if (input.equals("1")) 
                databaseDemo.displaySchedule();
            else if (input.equals("2")) 
                databaseDemo.editScheduleMenu();
            else if (input.equals("4")) 
                databaseDemo.displayScheduleOfDriver();
            else if (input.equals("8")) 
                databaseDemo.transfer();

            if(input.equals("3"))
            {
                System.out.println("Which Trip?");
                input = keyboard.nextLine();
                displayStopsOfTrip(input);
            }
            if(input.equals("5"))
            {
                System.out.println("Enter Name: ");
                input = keyboard.nextLine();
                System.out.println("Enter Telephone Number: ");
                String input2 = keyboard.nextLine();
                addDriver(input, input2);
            }
            if(input.equals("6"))
            {
                System.out.println("Enter BusID: ");
                input = keyboard.nextLine();
                System.out.println("Enter Model: ");
                String input2 = keyboard.nextLine();
                System.out.println("Enter year: ");
                String input3 = keyboard.nextLine();
                addBus(input, input2, input3);
            }
            if(input.equals("7"))
            {
                System.out.println("Enter BusID you want to delete: ");
                input = keyboard.nextLine();
                deleteBus(input);
            }

        } while(!input.equals("q"));
        keyboard.close();
    }
    private static void displayStopsOfTrip(String input) 
    {
        final String DB_URL = "jdbc:mysql://localhost:3306/lab4";
        final String USERNAME = "root";
        final String PASSWORD = "LaBamba2";

        Connection con = null;
        Statement st = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Database Connected Successfully");
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM trip_stop_info");
        while(rs.next())
        {
            String TripNumber = rs.getString("trip_number");
            String StopNumber = rs.getString("stop_number");
            String SequenceNumber = rs.getString("sequence_number");
            String DrivingTime = rs.getString("driving_time");
            if(input.equals(TripNumber))
            {
             System.out.println(TripNumber + " " + StopNumber + " " + SequenceNumber + " " + DrivingTime);
            }
        }
        st.close();
        con.close();
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }


    private static void addDriver(String input, String input2) 
    {
        final String DB_URL = "jdbc:mysql://localhost:3306/lab4";
        final String USERNAME = "root";
        final String PASSWORD = "LaBamba2";

        Connection con = null;
        Statement st = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Database Connected Successfully");
            st = con.createStatement();
            String q4 = "insert into driver values('" +input+ "', '"+input2+"')";
            int x = st.executeUpdate(q4);
            if(x>0)
            {
                System.out.println("Successfully Inserted");
            }
            else
            {
                System.out.println("Insert Failed");
            }
            ResultSet rs = st.executeQuery("SELECT * FROM driver");
            while(rs.next())
            {
    
            String DriverName = rs.getString("driver_name");
            String DriverPhoneNumber = rs.getString("driver_telephone_number");
    
            System.out.println(DriverName + " " + DriverPhoneNumber + " ");
            }
            st.close();
            con.close();
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }


    private static void addBus(String input, String input2, String input3) 
    {
        final String DB_URL = "jdbc:mysql://localhost:3306/lab4";
        final String USERNAME = "root";
        final String PASSWORD = "LaBamba2";

        Connection con = null;
        Statement st = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Database Connected Successfully");
            st = con.createStatement();
            String q6 = "insert into bus values('" +input+ "', '"+input2+"', '" +input3+ "')";
            int x = st.executeUpdate(q6);
            if(x>0)
            {
                System.out.println("Successfully Inserted");
            }
            else
            {
                System.out.println("Insert Failed");
            }
            ResultSet rs = st.executeQuery("SELECT * FROM bus");
            while(rs.next())
            {
            String BusID = rs.getString("bus_id");
            String BusModel = rs.getString("model");
            String BusYear = rs.getString("year");
    
            System.out.println(BusID + " " + BusModel + " " + BusYear);
            }
            st.close();
            con.close();
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }    
    }

    private static void deleteBus(String input) 
    {
        final String DB_URL = "jdbc:mysql://localhost:3306/lab4";
        final String USERNAME = "root";
        final String PASSWORD = "LaBamba2";

        Connection con = null;
        Statement st = null;
        PreparedStatement preparedStatement = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Database Connected Successfully");
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM bus");

            String query = "DELETE FROM bus WHERE bus_id = ?";
                        
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, input);
            preparedStatement.execute();
             
            while(rs.next())
            {
                String BusID = rs.getString("bus_id");
                String BusModel = rs.getString("model");
                String BusYear = rs.getString("year");

                if(!input.equals(BusID))
                {
                 System.out.println(BusID + " " + BusModel + " " + BusYear + "\n");
                }
            }
    
            st.close();
            con.close();
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }    
    }

    private static void recordTripOffering(String input) 
    {
        final String DB_URL = "jdbc:mysql://localhost:3306/lab4";
        final String USERNAME = "root";
        final String PASSWORD = "LaBamba2";
        String tripNumberRecord = "";

        Connection con = null;
        Statement st = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Database Connected Successfully");
            st = con.createStatement();
        //    String q6 = "insert into actual_trip_stop_info values('" +input+ "')";
          //  int x = st.executeUpdate(q6);

          ResultSet rs1 = st.executeQuery("SELECT * FROM trip_offering");
          while(rs1.next())
          {
          String tripNumber1 = rs1.getString("trip_number");
          tripNumberRecord = tripNumber1;
          String date = rs1.getString("date");
          String scheduledStartTime = rs1.getString("scheduled_start_time");
  
          System.out.println(tripNumber1 + " " + date + " " + scheduledStartTime + "\n");
          }


          ResultSet rs = st.executeQuery("SELECT * FROM actual_trip_stop_info");
          while(rs.next())
          {
            String tripNumber = rs.getString("trip_number");
            if(tripNumberRecord==tripNumber)
            {
                String q6 = "insert into actual_trip_stop_info values('" +input+ "')";   
                int x = st.executeUpdate(q6);
                if(x>0)
                {
                  System.out.println("Successfully Inserted");
                }
                else
                {
                  System.out.println("Insert Failed");
                }
            }
            String date = rs.getString("date");
            String scheduledStartTime = rs.getString("scheduled_start_time");
            String stopNumber = rs.getString("stop_number");
            String scheduledArrivalTime = rs.getString("scheduled_arrival_time");
            String actualStartTime = rs.getString("actual_arrival_time");
            String numberOfPassengersIn = rs.getString("number_of_passenger_in");
            String numberOfPassengersOut = rs.getString("number_of_passenger_out");
    
            System.out.println(tripNumber + " " + date + " " + scheduledStartTime + " " + stopNumber + " " + scheduledArrivalTime + " " + actualStartTime + " " + numberOfPassengersIn + " " + numberOfPassengersOut);
         }


      //      if(x>0)
        //    {
          //      System.out.println("Successfully Inserted");
     //       }
       //     else
         //   {
           //     System.out.println("Insert Failed");
            //}
        
            st.close();
            con.close();
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }    
    }
}
