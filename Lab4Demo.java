import java.util.Scanner;

public class Lab4Demo {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        String input = "Initialized"; //initialized variable
        do {
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
        keyboard.close();
    }
}