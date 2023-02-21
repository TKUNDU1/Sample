/** Turjya Kundu 112651178 RO3*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FXGuiMaker {
    public static void main(String[] args) throws IOException {
        String option = ""; int number = 0; FXTreeNode node = new FXTreeNode("", ComponentType.Vbox);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        FXComponentTree alpha = new FXComponentTree();
        System.out.println("Welcome to counterfeit SceneBuilder.");
        System.out.println("\n" +
                "Menu:\n" +
                "    L) Load from file\n" +
                "    P) Print tree\n" +
                "    C) Move cursor to a child node\n" +
                "    R) Move cursor to root\n" +
                "    A) Add a child\n" +
                "    U) Cursor up (to parent)\n" +
                "    E) Edit text of cursor\n" +
                "    D) Delete child\n" +
                "    S) Save to file\n" +
                "    X) Export FXML \n" + //extra credit, if you don't do this delete from this
                "    Q) Quit");
        try {
            while (!option.equals("Q")) {
                System.out.print("Please select an option: ");
                option = br.readLine();
                option = option.toUpperCase();
                switch (option) {
                    case ("L"):
                        System.out.print("Please enter filename: ");
                        option = br.readLine();
                        alpha = FXComponentTree.readFromFile(option);
                        break;
                    case ("P"):
                        alpha.trueString(alpha.getRoot());
                        break;
                    case ("C"):
                        System.out.print("Please enter number of child (starting with 1): ");
                        number = Integer.valueOf(br.readLine());
                        alpha.cursorToChild(number);
                        if (alpha.getCursor().getText() != "")
                            System.out.println("Cursor Moved to " + alpha.getCursor().getType() + " : " + alpha.getCursor().getText());
                        else
                            System.out.println("Cursor Moved to " + alpha.getCursor().getType());
                        break;
                    case ("R"):
                        alpha.cursorToRoot();
                        System.out.println("Cursor is at root");
                        break;
                    case ("A"):
                        System.out.print("Select component type (H - HBox, V - VBox, T - TextArea, B - Button, L - Label): ");
                        option = br.readLine();
                        option = option.toUpperCase();
                        switch (option) {
                            case ("H"):
                                node.setType(ComponentType.Hbox);
                                break;
                            case ("V"):
                                node.setType(ComponentType.Vbox);
                                break;
                            case ("T"):
                                node.setType(ComponentType.TextArea);
                                break;
                            case ("B"):
                                node.setType(ComponentType.Button);
                                break;
                            case ("L"):
                                node.setType(ComponentType.Label);
                                break;
                        }
                        System.out.print("Please enter text: ");
                        option = br.readLine();
                        option = option.toUpperCase();
                        node.setText(option);
                        System.out.print("Please enter an index: ");
                        number = Integer.valueOf(br.readLine());
                        alpha.addChild(number, node);
                        break;
                    case ("U"):
                        alpha.cursorToParent();
                        if (alpha.getCursor().getText() != "")
                            System.out.println("Cursor moved to " + alpha.getCursor().getType() + " : " + alpha.getCursor().getText());
                        else
                            System.out.println("Cursor moved to " + alpha.getCursor().getType());
                        break;
                    case ("E"):
                        System.out.print("Please enter new text: ");
                        option = br.readLine();
                        alpha.setTextAtCursor(option);
                        System.out.println("Text Edited.");
                        break;
                    case ("D"):
                        System.out.print("Please enter number of child(starting with 1): ");
                        number = Integer.valueOf(br.readLine());
                        if (alpha.getCursor().getChildren()[number - 1].getText() != "")
                            System.out.println(alpha.getCursor().getChildren()[number - 1].getType() + " : " + alpha.getCursor().getChildren()[number - 1].getText() + " removed");
                        else
                            System.out.println(alpha.getCursor().getChildren()[number - 1].getType() + " removed");
                        alpha.deleteChild(number);
                        break;
                    case ("S"):
                        System.out.print("Please enter a filename:");
                        option = br.readLine();
                        FXComponentTree.writeToFile(alpha, option);
                        break;
                }
            } System.out.println("Make like a tree and leave!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

