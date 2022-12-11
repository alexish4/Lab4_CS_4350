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
        } while(!input.equals("q"));
        keyboard.close();
    }
}
