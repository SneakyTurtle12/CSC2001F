public class SortAux {
    public static void quickSort(PlaceNameEntry[] arr, int low, int high) {
        if (low < high) {
            
            int index = partition(arr, low, high);

            // Recursively sort elements before and after partition
            quickSort(arr, low, index - 1);
            quickSort(arr, index + 1, high);
        }
    }
    private static int partition(PlaceNameEntry[] arr, int low, int high) {
        // Choosing the last element as the pivot
        PlaceNameEntry pivot = arr[high];
        int i = (low - 1); // Index of smaller element

        for (int j = low; j < high; j++) {
            // Using your record's compareTo method
            // If current element is smaller than or equal to pivot
            if (arr[j].compareTo(pivot) <= 0) {
                i++;

                // Swap arr[i] and arr[j]
                PlaceNameEntry temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Swap the pivot element with the element at i + 1
        PlaceNameEntry temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }
}
