import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.util.ArrayList;

/**Class for running experiment automatically*/
public class PlaceExperiment {
    /**Main method to run experiment*/
    public static void main(String[] args){

        float[] arrayResults = new float[10];
        float[] asisBSTResults = new float[10];
        float[] degenBSTResults = new float[10];
        float[] idealBSTResults = new float[10];

        Integer[] N = {1000,2000,3000,4000,5000,6000,7000,8000,9000,10000};
        String[] arrSearches = loadSearchArray();
        PlaceNameArray PNA = new PlaceNameArray();
        ArrayList<PlaceNameEntry> items = new ArrayList<PlaceNameEntry>();
        Path file = Paths.get("data.txt");

        createSortedFile("SAPlaceNames.csv");
        arrayResults = ArrayResults(N, arrSearches, "SAPlaceNames.csv"); //each of the experiments has corresponding method
        asisBSTResults = BSTResults(N, arrSearches, "SAPlaceNames.csv");
        degenBSTResults = BSTResults(N, arrSearches, "Sorted.csv");
        createIdealFile("SAPlaceNamesOptimal.txt");
        idealBSTResults = BSTResults(N, arrSearches, "Ideal.csv");

        try {
            Files.write(file, new byte[0], StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.writeString(file, String.format("%-8s %-10s %-15s %-15s %-15s%n", "N","Array","BST(as-is)", "BST(sorted)","BST(optimal)"), StandardOpenOption.APPEND); //formatting for header
            for (int n = 0; n < 10; n++) {
                //formatting for data
                Files.writeString(file, String.format("%-8d %-10.1f %-15.1f %-15.1f %-15.1f%n", N[n], arrayResults[n], asisBSTResults[n], degenBSTResults[n], idealBSTResults[n]), StandardOpenOption.APPEND);          }
        }
        catch(IOException e){
                System.err.println("An error occurred while writing to the file: " + e.getMessage());
            }
    }

    private static String[] loadSearchArray(){
        String fileName = "SearchQueries.txt";
        String[] outputQuery = new String[50];
        try {
            // Create a File object
            File file = new File(fileName);
            // Create a Scanner object to read from the file
            Scanner scanner = new Scanner(file);

            // Read line by line
            for (int i = 0; i <50; i++) {
                String data = scanner.nextLine();
                outputQuery[i] = data;
            }
            scanner.close();
            return outputQuery;
            // Close the scanner

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
        return outputQuery;
    }
    private static void createSortedFile(String fileName){
        File cFile = new File(fileName);
        Path filenew = Paths.get("Sorted.csv");
        ArrayList<PlaceNameEntry> arrResults = new ArrayList<>();
        try{
            Scanner scanner = new Scanner(cFile);
            scanner.nextLine();
            while (scanner.hasNextLine()){ //iCount given by user, reads <size> records from csv
                String line = scanner.nextLine();
                String[] values = line.split(",");
                arrResults.add(new PlaceNameEntry(values[0], values[1], values[2], values[3], Integer.valueOf(values[4])));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        arrResults.sort(null); //natural ordering
        try (BufferedWriter writer = Files.newBufferedWriter(filenew,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (PlaceNameEntry entry : arrResults) {
                writer.write(entry.toString());
                writer.newLine();
            }
        } catch(IOException e) {
            System.err.println("Error writing Sorted.csv: " + e.getMessage());
        }
    }
    private static float[] ArrayResults(Integer[] N, String[] arrSearches, String fileName){
        float[] arrResults = new float[10];
        PlaceNameArray PNA = new PlaceNameArray();
        for (int i = 0; i <10; i++){
            int comparisons = 0;
            PNA.loadArray(N[i], fileName);

            for (int n = 0; n < 50; n++){
                PNA.search(arrSearches[n]);
                comparisons += PNA.getComparisons(); //gets number of comparisons
            }
            arrResults[i] = (float) comparisons/50; //getd

        }
        return arrResults;
    }
    private static float[] BSTResults(Integer[] N, String[] arrSearches, String fileName){
        float[] arrResults = new float[10];
        PlaceNameBST PNB = new PlaceNameBST();
        for (int i = 0; i <10; i++){
            int comparisons = 0;
            PNB.loadBST(N[i], fileName);

            for (int n = 0; n<arrSearches.length; n++){
                PNB.searchTree(arrSearches[n]);
                comparisons += PNB.getComparisons(); //get total comparisons
            }
            arrResults[i] = (float) comparisons / 50; //get average
        }
        return  arrResults;
    }
    private static void createIdealFile(String fileName) {
        PlaceNameBST BST = new PlaceNameBST();
        BST.loadBST(12499, "SAPlaceNames.csv");

        File idealtxt = new File(fileName);
        Path idealcsv = Paths.get("Ideal.csv"); //csv of names in ideal ordering

        try (BufferedWriter writer = Files.newBufferedWriter(idealcsv,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {

            // Write the CSV Header
            writer.write("id,placename,municipality,province,population");
            writer.newLine();

            Scanner scanner = new Scanner(idealtxt);
            while (scanner.hasNextLine()) {
                String targetName = scanner.nextLine().trim();
                if (targetName.isEmpty()) continue;

                PlaceNameEntry record = BST.searchTree(targetName);  //get whole record from placename alone

                if (record != null && !record.id().equals("")) {
                    // Construct the line
                    String csvLine = String.format("%s,%s,%s,%s,%d",
                            record.id(),
                            record.placeName(),
                            record.municipality(),
                            record.province(),
                            record.population());

                    // Write data to buffer
                    writer.write(csvLine);
                    writer.newLine();
                }
            }
            scanner.close();

        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage() + Paths.get(fileName));
        }
    }
}
