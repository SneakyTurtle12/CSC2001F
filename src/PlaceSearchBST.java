import java.util.Scanner;

/**Class containing main method for interacting with BST*/
public class PlaceSearchBST {
    /**main method for UI of BST interaction*/
    public static void main(String[] args) {
        PlaceNameBST PNB = new PlaceNameBST();
        System.out.println("Welcome to the PlaceSearchBST Terminal Dialogue");
        Scanner scanner = new Scanner(System.in);
        boolean bQuit = false;
        while (!bQuit) {
            System.out.println("What would you like to do? Press (q) to quit the application.");
            System.out.println("1. Load data from a file into the BST.");
            System.out.println("2. Search BST for entry matching a place name.");
            System.out.println("3. Delete from BST.");
            System.out.println("4. Return number of comparisons for most recent search.");
            System.out.println("5. Tree height of BST.");
            switch (scanner.next().toLowerCase().charAt(0)) {
                case '1':
                    scanner.nextLine(); //gets rid of \n from reading first char, allowing user to say 1 or 1. and q or quit
                    System.out.println("What is the name of the file?");
                    String fileName = scanner.nextLine();
                    System.out.println("How many lines of the array would you like to load?");
                    try{
                    int N = scanner.nextInt();
                    System.out.println("You are loading " + N + " lines, from the file \"" + fileName + "\". \nConfirm [Y/N]:");
                    if (scanner.next().toLowerCase().charAt(0) == 'y') {
                        PNB.loadBST(N, fileName);
                        if (!PNB.getFail()){
                        System.out.println("BST loaded successfully.");}
                    }
                    break;}
                    catch (RuntimeException e){System.out.println("Please insert a number for the line count"); break;}
                case '2':
                    scanner.nextLine();
                    System.out.println("What is the name of the place you are looking for?");
                    String placeName = scanner.nextLine();
                    if (placeName.isEmpty()){break;}
                    System.out.println("You are looking for place \"" + placeName + "\". \nConfirm [Y/N]:");
                    if (scanner.next().toLowerCase().charAt(0) == 'y') { //allows user to say Y, Yes, y or yes
                        PlaceNameEntry search = PNB.searchTree(placeName);
                        if (search.placeName().isEmpty()){
                            System.out.println("The place could not be found.");
                        }
                        else {
                            System.out.println("The place " + search.placeName() + " has ID " + search.id() + ". It is in " + search.municipality() + ", " + search.province() + ". It has a population of " + search.population() + ".");
                        }
                    }
                    break;
                case '3':
                    scanner.nextLine();
                    System.out.println("What is the name of the place corresponding to the desired deletion?");
                    String deletion = scanner.nextLine();
                    if (deletion.isEmpty()){break;}
                    System.out.println("You are about to delete the record with place name " + deletion + ". Confirm [Y/N]:");
                    if (scanner.next().toLowerCase().charAt(0) == 'y') {//allows user to say Y, Yes, y or yes
                        if (PNB.searchTree(deletion).id().isEmpty()){
                            System.out.println("The record to delete could not be found");
                            break;
                        }
                        PNB.deleteBST(deletion);
                        System.out.println("The record has been deleted.");
                        }
                    break;
                case '4':
                    scanner.nextLine();
                    System.out.println("There were " + PNB.getComparisons() + " comparisons in your most recent search.");
                    break;
                case '5':
                    scanner.nextLine();
                    System.out.println("The tree has a height (longest path from first root to last leaf) of " + PNB.getTreeHeight() + ".");
                    break;
                case 'q':
                    System.out.println("Thank you");
                    bQuit = true;
                    break;
            }
           }

    }
}
