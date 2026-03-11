import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PlaceNameBST {
    private int comparisons;
    private int iCount;
    public int getiCount(){
        return iCount;
    }
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
    public PlaceNameBST() { //constructor for BST
        root = null;
    }
    // Public method to insert
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
    public PlaceNameEntry searchTree(String placeName){
        return searchTreeRecursive(root, placeName);
    }
    private PlaceNameEntry searchTreeRecursive(Node root, String placeName){
        if (root == null){
            return new PlaceNameEntry("","","","",0);//item not found, returns empty placeNameEntry
        }
        if (root.value.placeName().compareTo(placeName) == 0){ //item found, returns the PlaceNameENtry value with given placeName
            comparisons+=1;
            return root.value;
        }
        if (root.value.placeName().compareTo(placeName) < 0){ //if value smaller than current root, traverse left node until its found
            comparisons+=1;
            return searchTreeRecursive(root.left,placeName);
        } else {
            comparisons+=1;
            return searchTreeRecursive(root.right, placeName); //if value greater than current root, traverse right node until its found
        }
    }
    public void deleteBST(String placeName){
        root = deleteBSTRecursive(root, placeName);
    }

    private PlaceNameEntry findMin(Node root){ //auxilliary method for deletion, follows left node all the way down to find smallest value
        PlaceNameEntry min = root.value;
        while (root.left != null) {
            min = root.left.value;
            root = root.left;
        }
        return min;
    }
    private Node deleteBSTRecursive(Node root, String placeName){
        if (root == null){
            return null;
        }

        // Traverse to find the node
        if (placeName.compareTo(root.value.placeName()) < 0 ){
            root.left = deleteBSTRecursive(root.left, placeName);} //traversing tree to find the node, "searching" for node to delete
        else if (placeName.compareTo(root.value.placeName()) > 0 ){
            root.left = deleteBSTRecursive(root.left, placeName);}

        // Case 1: Node with only one child, or no children
        if (root.left == null){
            return root.right;}
        else if (root.right == null){
            return root.left;}

        // Case 3: Node with two children, get the smallest in right subtree using findmin function and replace current node's value with it
        root.value = findMin(root.right);

        // Deletes the now duplicated smallest value in right subtree
        root.right = deleteBSTRecursive(root.right, root.value.placeName());

        return root;

    }
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

    public void loadBST(int size, String fileName){
        File cFile = new File(fileName);

        try{
            Scanner scanner = new Scanner(cFile);
            scanner.nextLine();
            while (scanner.hasNextLine() && iCount <= size){ //iCount given by user, reads <size> records from csv
                String line = scanner.nextLine();
                String[] values = line.split(",");
                insert(new PlaceNameEntry(values[0], values[1], values[2], values[3], Integer.valueOf(values[4]))); //insert into BST
                if (!dupFlag){
                    iCount+=1;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}


