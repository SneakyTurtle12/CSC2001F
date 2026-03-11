import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class PlaceNameArray {
    PlaceNameEntry[] arrPlaces;
    private int comparisons;
    private int iCount = 0;
    public void loadArray(int size, String fileName){
        Set<String> seen = new HashSet<>();
        File cFile = new File(fileName);
        arrPlaces = new PlaceNameEntry[size];
        try{
            Scanner scanner = new Scanner(cFile);
            scanner.nextLine();
            while (scanner.hasNextLine() && iCount < size){
                String line = scanner.nextLine();
                String[] values = line.split(",");
                if (seen.add(values[1])) //O(1) complexity on hashset duplicate checks
                {
                    arrPlaces[iCount] = new PlaceNameEntry(values[0], values[1], values[2], values[3], Integer.valueOf(values[4]));
                    iCount +=1;
                }

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public PlaceNameEntry search(String name){
        comparisons = 0;
        for (int i = 0; i < iCount; i++){
            comparisons+=1;
            if (name.compareTo(arrPlaces[i].placeName()) == 0){
                return arrPlaces[i];
            }
        }
        return new PlaceNameEntry("","","","",0);
    }
    public int getComparisons(){return comparisons;}
}
