package Control;

import Model.BinarySearchTree;
import Model.Customer;
import Model.List;
import View.DrawingPanel;
import View.TreeView.TreeNode;
import View.TreeView.TreePath;

/**
 * Created by Jean-Pierre on 12.01.2017.
 */
public class MainController {

    //Attribute
    private boolean surpriseIsSet;


    //Referenzen
    private BinarySearchTree<Customer> customerTree;

    public MainController(){
        surpriseIsSet = false;
        customerTree = new BinarySearchTree<Customer>();
        createCustomerTree();
    }

    /**
     * Füllt Daten in Form von Kunden-Objekten in den Baum.
     */
    private void createCustomerTree(){
        customerTree.insert(new Customer("Ulf",500));
        customerTree.insert(new Customer("Ralle",250));
        customerTree.insert(new Customer("Bernd",750));
    }

    /**
     * Der Baum wird im übergebenem Panel dargestellt.
     * Dazu wird zunächst die alte Zeichnung entfernt.
     * Im Anschluss wird eine eine internete Hilfsmethode aufgerufen.
     * @param panel Das DrawingPanel-Objekt, auf dem gezeichnet wird.
     */
    public void showTree(DrawingPanel panel){
        panel.removeAllObjects();
        //Der Baum wird in der Mitte des Panels beginnend gezeichnet: panel.getWidth()/2
        //Der linke und rechte Knoten in Tiefe 1 sind jeweils ein Viertel der Breite des Panels entfernt.
        showTree(customerTree, panel, panel.getWidth()/2, 50, panel.getWidth()/4);
    }

    /**
     * Hilfsmethode zum Zeichnen des Baums.
     * Für jeden Knoten wird ein neues TreeNode-Objekt dem panel hinzugefügt.
     * Für jede Kante wird ein neues TreePath-Pbjekt dem panel hinzugefügt.
     * @param tree Der zu zeichnende (Teil)Binärbaum.
     * @param panel Das DrawingPanel-Objekt, auf dem gezeichnet wird.
     * @param startX x-Koordinate seiner Wurzel
     * @param startY y-Koordinate seiner Wurzel
     * @param spaceToTheSide Gibt an, wie weit horizontal entfernt die folgenden Bäume gezeichnet werden soll.
     */
    private void showTree(BinarySearchTree tree, DrawingPanel panel, double startX, double startY, double spaceToTheSide) {
        if (!tree.isEmpty()) {
            TreeNode node = new TreeNode(startX, startY, 10, tree.getContent().toString(), false);
            panel.addObject(node);
            if (!tree.getLeftTree().isEmpty()) {
                TreePath path = new TreePath(startX, startY, startX - spaceToTheSide, startY + 50, 10, false);
                panel.addObject(path);
                showTree(tree.getLeftTree(), panel, startX - spaceToTheSide, startY + 50, spaceToTheSide * 0.5);
            }
            if (!tree.getRightTree().isEmpty()) {
                TreePath path = new TreePath(startX, startY, startX + spaceToTheSide, startY + 50, 10, false);
                panel.addObject(path);
                showTree(tree.getRightTree(), panel, startX + spaceToTheSide, startY + 50, spaceToTheSide * 0.5);
            }
        }
    }

    /**
     * Es wird das Ergebnis einer Traversierung bestimmt.
     * Ruft dazu eine interne Hilfsmethode auf.
     * @return Das Ergebnis der Traversierung als Zeichenkette.
     */
    public String traverse(){
        return traverse(customerTree);
    }

    /**
     * Interne Hilfsmethode zur Traversierung.
     * @param tree Der zu traversierende BinarySearchTree.
     * @return Das Ergebnis der Traversierung als Zeichenkette.
     */
    private String traverse(BinarySearchTree tree){
        //TODO 04:  Siehe Rückgabe. You can do it!
        if(tree.isEmpty()) {
            return "";
        }
        return tree.getContent().toString()+" "+traverse(tree.getLeftTree())+" "+traverse(tree.getRightTree());
    }

    /**
     * Es wird nach dem letzten Kunden in der Datenmenge geuscht.
     * Falls dieser existiert, wird ein zwei Felder großes Array mit seinem Namen (Index 0) und seinem Umsatz (Index 1) zurückgegeben.
     * Sonst wird ein ein Feld großes Array mit "no customers in db" zurückgegeben.
     * @return
     */
    public String[] searchLastName(){
        //TODO 05: Umsetzung einer Teilaufgabe einer zurückliegenden Hausaufgabe.
        Customer cstmer = biggest(customerTree);
        if(cstmer != null){
            return new String[]{cstmer.getName(), String.valueOf(cstmer.getSales())};
        }
        return new String[]{"no customer in db"};
    }

    private Customer biggest(BinarySearchTree<Customer> tree){
        if(tree.getRightTree().isEmpty()){
            return tree.getContent();
        }
        return biggest(tree.getRightTree());
    }

    /**
     * Bestimme den gesamten Umsatz aller Kunden, die im Baum gespeichert sind.
     * @return Umsatz-Summe
     */
    public int sumUpSales(){
        return traverseSumUp(customerTree);
    }

    private int traverseSumUp(BinarySearchTree<Customer> tree){
        if(tree.isEmpty()) {
            return 0;
        }
        return tree.getContent().getSales()+traverseSumUp(tree.getLeftTree())+traverseSumUp(tree.getRightTree());
    }

    /**
     * Fügt dem Baum ein neues Kunden-Objekt hinzu, falls dieses noch nicht existiert.
     * @param name Name des Kunden-Objekts.
     * @param sales Umsatz des Kunden-Objekts.
     * @return true, falls ein neuer Kunde hinzugefügt wurde, sonst false.
     */
    public boolean insert(String name, int sales){
        Customer newCus = new Customer(name,sales);
        if(customerTree.search(newCus) == null) {
            customerTree.insert(newCus);
            return true;
        }
        return !true;
    }

    /**
     * Es wird nach einem Kunden gesucht, der den entsprechenden Namen aufweist.
     * Falls einer vorhanden ist, so wird er gelöscht und true zurückgegeben. Sonst wird false zurückgegeben.
     * @param name
     * @return Teilt mit, ob eine Löschung erfolgt ist oder nicht.
     */
    public boolean delete(String name){
        Customer searchKey = new Customer("name");
        if(customerTree.search(searchKey) == null){
            return false;
        }
        customerTree.remove(searchKey);
        return true;
    }

    /**
     * Es wird im Baum nach einem Kunden mit entsprechendem Namen gesucht.
     * Falls dieser existiert, wird ein zwei Felder großes Array mit seinem Namen (Index 0) und seinem Umsatz (Index 1) zurückgegeben.
     * Sonst wird ein ein Feld großes Array mit "name not found" zurückgegeben.
     * @param name
     * @return
     */
    public String[] searchName(String name){
        Customer temp = customerTree.search(new Customer(name));
        if(temp != null) {
            return new String[]{temp.getName(),String.valueOf(temp.getSales())};
        }
        return new String[]{"name not found"};
    }

    /**
     * Es wird im Baum nach einem Kunden gesucht, der den geforderten Umsatz generiert hat.
     * Falls dieser existiert, wird ein zwei Felder großes Array mit seinem Namen (Index 0) und seinem Umsatz (Index 1) zurückgegeben.
     * Sonst wird ein ein Feld großes Array mit "sales not found" zurückgegeben.
     * @param sales
     * @return Informationen zum Ausgang der Suche.
     */
    public String[] searchSales(int sales){
        Customer cTemp = searchSales(customerTree,sales);
        if(cTemp != null){
            return new String[]{cTemp.getName(), String.valueOf(cTemp.getSales())};
        }
        return new String[]{"sales not found"};
    }

    private Customer searchSales(BinarySearchTree<Customer> tree, int sales) {
        Customer temp = tree.getContent();
        if(temp != null){
            if(temp.getSales() == sales) {
                return temp;
            }
            Customer leftCustomer = searchSales(tree.getLeftTree(),sales);
            if(leftCustomer != null) {
                return leftCustomer;
            }
            Customer rightCustomer = searchSales(tree.getRightTree(),sales);
            if(rightCustomer != null) {
                return rightCustomer;
            }
        }
        return null;
    }


    /**
     * Bestimmt eine Liste von Kunden-Objekten, deren Namen lexikographisch später erscheinen als der Name, der als Parameter übergeben wird.
     * Konvertiert anschließend diese Liste in ein zweidimensionales Zeichenketten-Array. Die erste Dimension bestimmt einen Kunden,
     * die zweite Dimension enthält die Daten "Name" und "Umsatz", also z.B.
     * output[0][0] = "Ulf", output[0][1] = "500"; output[1][0] = "Ralle", output[1][1] = "250" etc.
     * @param name Name, der als lexikographisches Minimum gilt.
     * @return Zweidimensionales Zeichenketten-Array.
     */
    public String[][] listUpperNames(String name){
        List<Customer> list = getAllCustomer(customerTree);
        String[][] array = new String[getSize(list)][2];
        list.toFirst();
        for (int i = 0; i < array.length; i++) {
            array[i][0] = list.getContent().getName();
            array[i][1] = String.valueOf(list.getContent().getSales());
            list.next();
        }
        return array;
    }

    private int getSize(List list) {
        int size = 0;
        list.toFirst();
        while(list.hasAccess()){
            size++;
            list.next();
        }
        return size;
    }

    private List getAllCustomer(BinarySearchTree tree){
        List list = new List<>();
        if(!tree.isEmpty()) {
            list.append(tree.getContent());
            list.concat(getAllCustomer(tree.getLeftTree()));
            list.concat(getAllCustomer(tree.getRightTree()));
        }
        return list;
    }

    public void surprise(){
        surpriseIsSet = !surpriseIsSet;
        switchSortKey();
    }

    public void switchSortKey() {
        List<Customer> list = getAllCustomer(customerTree);
        BinarySearchTree<Customer> tree = new BinarySearchTree<>();
        list.toFirst();
        while (list.hasAccess()) {
            list.getContent().switchSearchKey();
            tree.insert(list.getContent());
            list.next();
        }
        customerTree = tree;
    }
}
