import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**Class for all methods relating to BST operation*/
public class PlaceNameBST {
    private boolean bFail;
    private int comparisons;
    private int iCount;
    public int getComparisons(){
        return comparisons;
    }
    private class Node{
        PlaceNameEntry value;
        Node left,right;
        public Node(PlaceNameEntry item){
            value = item;
            left=right=null;
        }
    }
    private Node root;
    /**Constructor for PlaceNameBST*/
    public PlaceNameBST() {
        root = null;
    }
    private void insert(PlaceNameEntry value) {
        root = recursiveInsert(root, value);
    }
    private boolean dupFlag;
    private Node recursiveInsert(Node root, PlaceNameEntry value) {

        // If the tree is empty, or we reach a leaf node's null child, create a new node
        if (root == null) {
            dupFlag = false;
            return new Node(value);
        }

        // Traverse the tree to place entry
        if (value.placeName().compareTo(root.value.placeName()) < 0) {
            root.left = recursiveInsert(root.left, value);
        } else if (value.placeName().compareTo(root.value.placeName()) > 0) {
            root.right = recursiveInsert(root.right, value);
        }
        else{
            dupFlag = true; //set flag if duplicate is made (for counting entries)
        }

        return root;
    }

    /**Searches tree recursively, best case O(logN) time
     * @param placeName String of name to search for
     * @return Entire record of searched for string
     */
    public PlaceNameEntry searchTree(String placeName){
        comparisons = 0;
        return searchTreeRecursive(root, placeName);
    }
    private PlaceNameEntry searchTreeRecursive(Node root, String placeName){
        if (root == null){
            return new PlaceNameEntry("","","","",0);//item not found, returns empty placeNameEntry
        }
        int comparison = root.value.placeName().compareTo(placeName);
        comparisons++;
        if (comparison == 0){ //item found, returns the PlaceNameENtry value with given placeName
            return root.value;
        }
        if (comparison > 0){ //if value smaller than current root, traverse left node until its found
            return searchTreeRecursive(root.left,placeName);
        } else {
            return searchTreeRecursive(root.right, placeName); //if value greater than current root, traverse right node until its found
        }
    }
    /**Deletes an element from the binary search  tree
     * @param placeName String to search for and delete
     * */
    public void deleteBST(String placeName){
        root = deleteBSTRecursive(root, placeName);
    }

    private PlaceNameEntry findMin(Node root){ //auxilliary method for deletion, follows left node of given root all the way down to find smallest value
        PlaceNameEntry min = root.value;
        while (root.left != null) {
            min = root.left.value;
            root = root.left;
        }
        return min;
    }
    private Node deleteBSTRecursive(Node root, String placeName) {
        if (root == null) return null;

        int cmp = placeName.compareTo(root.value.placeName());

        if (cmp < 0) {
            root.left = deleteBSTRecursive(root.left, placeName);
        } else if (cmp > 0) {
            root.right = deleteBSTRecursive(root.right, placeName);
        } else {

            // Case 1 & 2: One child or Leaf
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // Case 3: Two children
            root.value = findMin(root.right); // Get the inorder successor

            // Delete the inorder successor from the right subtree
            root.right = deleteBSTRecursive(root.right, root.value.placeName());
        }

        return root;
    }
    /** Returns the height of the current tree from root to furthest leaf
     * @return int of tree height
     */
    public int getTreeHeight(){
        return getRecursiveTreeHeight(root);
    }

    private int getRecursiveTreeHeight(Node root){
        if (root == null){
            return -1;
        }
        int lefth = getRecursiveTreeHeight(root.left);
        int righth = getRecursiveTreeHeight(root.right);

        return Math.max(lefth,righth) + 1;//recursively increases height, starts at -1 for null root
    }
    /**Returns the boolean on whether the last deletion failed
     * @return boolean of whether deletion failed or not*/
    public boolean getFail(){
        return bFail;
    }
    /**Loads the BST with given number of files from file specified
     * @param size int of non-duplicate lines to load
     * @param fileName string of file to load, with file extension*/
    public void loadBST(int size, String fileName){
        bFail = false;
        File cFile = new File(fileName);

        try{
            Scanner scanner = new Scanner(cFile);
            scanner.nextLine();
            while (scanner.hasNextLine() && iCount <= size){ //iCount given by user, reads <size> records from csv
                String line = scanner.nextLine();
                String[] values = line.split(",");
                insert(new PlaceNameEntry(values[0], values[1], values[2], values[3], Integer.valueOf(values[4]))); //insert into BST
                if (!dupFlag){
                    iCount+=1; //only increments on nonduplicate
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            bFail = true;
            System.out.println("Could not find file " + fileName);
        }
    }
}


