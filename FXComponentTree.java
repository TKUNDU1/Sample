/** Turjya Kundu 112651178 RO3*/
import java.io.*;
import java.util.Scanner;

public class FXComponentTree {
    private FXTreeNode root = new FXTreeNode("", ComponentType.AnchorPane);
    private FXTreeNode cursor = root;
    public FXComponentTree() {
        this.cursor = cursor;
        this.root = root;
    }

    public void cursorToRoot() {
        cursor = root;
    }

    public FXTreeNode getCursor() {
        return cursor;
    }
    public void setCursor(FXTreeNode x) {
        this.cursor = x;
    }
    public FXTreeNode getRoot() {
        return root;
    }
    int indentcounter = 0;
    public String trueString(FXTreeNode node) {
        if (cursor == node) System.out.println("==>" + String.valueOf(node.getType()) + " : " + node.getText());
        else System.out.println("--+" + String.valueOf(node.getType())  + " : " + node.getText());
        if (node.getChildren() != null) {
            for (int i = 0; node.getChildren()[i] != null; i++) {
                indentcounter++;
                for (int j = 0; j < indentcounter; j++) {
                    System.out.print("    ");
                }
                trueString(node.getChildren()[i]);
                indentcounter--;
                if (i == 9) break; /**to prevent array out of bounds error*/
            }
        }
        return "";
    }

    public void addChild(int index, FXTreeNode node) {
        index = index-1;
        node.setNumber(index);
        FXTreeNode deepclonenode = new FXTreeNode(node.getText(), node.getType());
        deepclonenode.setNumber(node.getNumber());
        if (cursor.getType() == ComponentType.AnchorPane && cursor.getChildren()[0] == null) {
            cursor.getChildren()[0] = deepclonenode;
            cursor.getChildren()[0].setNumber(deepclonenode.getNumber());
            cursor.getChildren()[0].setType(deepclonenode.getType());
            cursor.getChildren()[0].setText(deepclonenode.getText());
            cursor.getChildren()[0].setParent(cursor);
        } else if (cursor.getType() == ComponentType.AnchorPane && cursor.getChildren()[0] != null) {
            System.out.println("Root can only have one child.");
        } else if (cursor.getChildren()[0] == null) {
            cursor.getChildren()[0] = deepclonenode;
            cursor.getChildren()[0].setNumber(deepclonenode.getNumber());
            cursor.getChildren()[0].setType(deepclonenode.getType());
            cursor.getChildren()[0].setText(deepclonenode.getText());
            cursor.getChildren()[0].setParent(cursor);
        } else if (cursor.getChildren()[0] != null && cursor.getChildren()[9] != null) {
            System.out.println("Node children is already full.");
        } else if (cursor.getChildren()[0] != null && cursor.getChildren()[9] == null) {
            int i = 0;
            while (cursor.getChildren()[i] != null) {
                if (deepclonenode.getNumber() > cursor.getChildren()[i].getNumber()) {
                    i = i+1;
                } else if (deepclonenode.getNumber() == cursor.getChildren()[i].getNumber()) {
                    System.out.println("Variable with same number, abandoned.");
                    i = 9; /** breaks the loop instantly */
                } else if (deepclonenode.getNumber() < cursor.getChildren()[i].getNumber()) {
                    FXTreeNode temporary = new FXTreeNode(cursor.getChildren()[i].getText(), cursor.getChildren()[i].getType());
                    temporary.setNumber(cursor.getChildren()[i].getNumber());
                    cursor.getChildren()[i].setNumber(deepclonenode.getNumber());
                    cursor.getChildren()[i].setType(deepclonenode.getType());
                    cursor.getChildren()[i].setText(deepclonenode.getText());
                    cursor.getChildren()[i].setParent(cursor);
                    deepclonenode.setText(temporary.getText());
                    deepclonenode.setType(temporary.getType());
                    deepclonenode.setNumber(temporary.getNumber());
                }
            }
            cursor.getChildren()[i] = deepclonenode;
            cursor.getChildren()[i].setNumber(deepclonenode.getNumber());
            cursor.getChildren()[i].setType(deepclonenode.getType());
            cursor.getChildren()[i].setText(deepclonenode.getText());
            cursor.getChildren()[i].setParent(cursor);
        }
    }
    public void setTextAtCursor(String text) {
        cursor.setText(text);
    }
    public void cursorToChild(int index) {
        index = index - 1;
        if (index > 9) {
            System.out.println("Out of bounds.");
        } else if (cursor.getChildren()[index] == null) {
            System.out.println("Pointing towards empty place.");
        } else {
            cursor = cursor.getChildren()[index];
        }
    }
    public void cursorToParent() {
        if (cursor.getType() == ComponentType.AnchorPane) {
            System.out.println("Root cannot have a parent.");
        } else {
            cursor = cursor.getParent();
        }
    }
    public void deleteChild(int index) {
        index = index - 1;
        int i = 0;
        if (cursor.getChildren()[index] == null) {
            System.out.println("Empty index.");
        }
        for (i = index; cursor.getChildren()[i+1] != null; i++) {
            cursor.getChildren()[i] = cursor.getChildren()[i+1];
        }
        cursor.getChildren()[i] = null;
    }
    public static FXComponentTree readFromFile(String filename) throws FileNotFoundException {
        filename = "src//" + filename;
        FXComponentTree sample = new FXComponentTree();
        FXTreeNode sampler = new FXTreeNode("", ComponentType.Vbox);
        try {
        FileReader fr = new FileReader(filename);
        Scanner inFile = new Scanner(fr);
        String line;
        while (inFile.hasNext()) {
            boolean anchor = false; /**to ignore anchor root */
            line = inFile.nextLine();
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                int x = -1;
                if (c == '0') x = 0;
                if (c == '1') x = 1;
                if (c == '2') x = 2;
                if (c == '3') x = 3;
                if (c == '4') x = 4;
                if (c == '5') x = 5;
                if (c == '6') x = 6;
                if (c == '7') x = 7;
                if (c == '8') x = 8;
                if (c == '9') x = 9;
                if (c != ' ') {
                    if (x == 0 && !anchor) {
                        anchor = true; /** basically skipping over initial anchor */
                    } else {
                        if (x != -1) { /** to account for - */
                            if (sample.getCursor().getChildren()[x] != null) {
                                sample.setCursor(sample.getCursor().getChildren()[x]);
                            } else {
                                i = i + 2;
                                String TextHolder = "";
                                while (c != ' ' && i < line.length()) {
                                    c = line.charAt(i);
                                    TextHolder += c;
                                    i++;
                                }
                                if (TextHolder.toUpperCase().equals("VBOX")) sampler.setType(ComponentType.Vbox);
                                if (TextHolder.toUpperCase().equals("HBOX")) sampler.setType(ComponentType.Hbox);
                                if (TextHolder.toUpperCase().equals("LABEL ")) sampler.setType(ComponentType.Label);
                                if (TextHolder.toUpperCase().equals("BUTTON ")) sampler.setType(ComponentType.Button);
                                if (TextHolder.toUpperCase().equals("TEXTAREA "))
                                    sampler.setType(ComponentType.TextArea);
                                TextHolder = "";
                                while (i < line.length()) {
                                    c = line.charAt(i);
                                    TextHolder += c;
                                    i++;
                                }
                                sampler.setText(TextHolder);
                                sample.addChild(x + 1, sampler);
                            }
                        }
                    }
                }
            }
            sample.cursorToRoot();
        }
        } catch(FileNotFoundException fr) {
            System.out.println(filename.substring(5, filename.length()) + " not found.");
        }
        return sample;
    }
    public static void writeToFile(FXComponentTree tree, String filename) throws IOException {
        filename = "src//" + filename;
        String tracker = "0";
        FileWriter myObj = new FileWriter(filename);
        if (tree.getRoot() != null) {
            myObj.write(tracker + " " + String.valueOf(tree.getRoot().getType()) + " " + tree.getRoot().getText());
            myObj.append('\n');
            if (tree.getRoot().getChildren()[0] != null) {
                tracker = tracker + "-0";
                myObj.write(tracker + " " + String.valueOf(tree.getRoot().getChildren()[0].getType()) + " " + tree.getRoot().getChildren()[0].getText());
                myObj.append('\n');
                if (tree.getRoot().getChildren()[0].getChildren() != null) {
                    Author(tree.getRoot().getChildren()[0], tracker, myObj);
                }
            }
        }
        myObj.close();
    }
    public static void Author(FXTreeNode piece, String tracker, FileWriter myObj) throws IOException{
        if (piece.getChildren() != null) {
            for (int i = 0; piece.getChildren()[i] != null; i++) {
                tracker = tracker + "-" + piece.getChildren()[i].getNumber();
                myObj.write(tracker + " " + String.valueOf(piece.getChildren()[i].getType()) + " " + piece.getChildren()[i].getText());
                myObj.append('\n');
                if (piece.getChildren()[i].getChildren() != null) {
                    Author(piece.getChildren()[i], tracker, myObj);
                }
                tracker = tracker.substring(0, tracker.length() - 2);
            }
        }
    }
}
