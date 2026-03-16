import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PlaceSearchArray {
    public static void main(String[] args){
        PlaceNameArray PNA = new PlaceNameArray();
        System.out.println("Welcome to the PlaceSearchArray Terminal Dialogue");
        Scanner scanner = new Scanner(System.in);
        boolean bQuit = false;
        while (!bQuit) {
            System.out.println("What would you like to do? Press (q) to quit the application");
            System.out.println("1. Load data from a file into the array");
            System.out.println("2. Search array for entry matching a place name");
            System.out.println("3. Return number of comparisons for most recent search");
            switch (scanner.next().toLowerCase().charAt(0)){
                case '1':
                    scanner.nextLine(); //gets rid of \n from reading first char, allowing user to say 1 or 1. and q or quit
                    System.out.println("What is the name of the file?");
                    String fileName = scanner.nextLine();
                    System.out.println("How many lines of the array would you like to load?");
                    int N = scanner.nextInt();
                    System.out.println("You are loading " + N + " lines, from the file \"" + fileName + "\". \nConfirm [Y/N]:");
                        if (scanner.next().toLowerCase().charAt(0) == 'y') {
                                PNA.loadArray(N, fileName);
                                System.out.println("Array loaded successfully.");
                    }
                    break;
                case '2':
                    scanner.nextLine();
                    System.out.println("What is the name of the place you are looking for?");
                    String placeName = scanner.nextLine();
                    System.out.println("You are looking for place \"" + placeName + "\". \nConfirm [Y/N]:");
                    if (scanner.next().toLowerCase().charAt(0) == 'y') { //allows user to say Y, Yes, y or yes
                        PlaceNameEntry search = PNA.search(placeName);
                        if (search.placeName().isEmpty()){
                            System.out.println("The place could not be found.");
                        }
                        else {
                            System.out.println("The place " + search.placeName() + " has ID " + search.id() + ". It is in " + search.municipality() + ", " + search.province() + ". It has a population of " + search.population() + ".");
                        }
                    }
                    break;
                case '3':
                    scanner.nextLine(); //no need to confirm here, there is no other input and it merely fetches a variable
                    System.out.println("There were " + PNA.getComparisons() + " comparisons in your most recent search.");
                    break;
                case 'q':
                    System.out.println("Thank you");
                    bQuit = true;
                    break;
            }
        }

    }
}
