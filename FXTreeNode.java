/** Turjya Kundu 112651178 RO3*/
public class FXTreeNode {
    private String text;
    private ComponentType type;
    private FXTreeNode parent;
    private final int maxChildren = 10;
    private FXTreeNode[] children;
    private int number; //for children

    public FXTreeNode(String text, ComponentType type) {
        this.text = text;
        this.type = type;
        this.parent = null;
        this.children = new FXTreeNode[maxChildren];
        this.number = number;
    }

    public String getText() {
        return text;
    }
    public ComponentType getType() {
        return type;
    }
    public FXTreeNode getParent() {
        return parent;
    }
    public FXTreeNode[] getChildren() {
        return children;
    }
    public int getNumber() {
        return number;
    }

    public void setText(String x) {
        this.text = x;
    }
    public void setType(ComponentType x) {
        this.type = x;
    }
    public void setParent(FXTreeNode x) { //need to rework to make parent recognize this as child
        this.parent = x;
    }
    public void setChildren(FXTreeNode[] x) {
        for (int z = 0; z < x.length; z++) {
            x[z].setParent(this);
        }
        this.children = x;
    }
    public void setNumber(int x) {
        this.number = x;
    }

}
