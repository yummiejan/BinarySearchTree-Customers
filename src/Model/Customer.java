package Model;

/** Klasse für ein Kunden-Objekt.
 * Created by Jean-Pierre on 26.03.2017.
 */
public class Customer implements ComparableContent<Customer> {

    private String name;
    private int sales; //Umsatz
    private boolean searchKeyName;

    /**
     * Ein Kunden-Objekt besteht aus einem Namen und einem getätigten Umsatz.
     * @param name
     */
    public Customer(String name){
        this.name = name;
        this.sales = 0;
        searchKeyName = true;
    }

    public Customer(String name, int sales){
        this.name = name;
        this.sales = sales;
        searchKeyName = true;
    }

    /**
     *
     * @return Name des Kunden-Objekts
     */
    public String getName(){
        return name;
    }

    /**
     *
     * @return Umsatz des Kunden-Objekts
     */
    public int getSales(){
        return sales;
    }

    /**
     *
     * @param newSales Neuer Umsatz
     */
    public void setSales(int newSales){
        this.sales = newSales;
    }

    public void switchSearchKey(){
        searchKeyName = !searchKeyName;
    }

    public boolean isSearchKeyName(){
        return searchKeyName;
    }

    @Override
    public boolean isGreater(Customer pContent) {
        if(pContent != null) {
            if (searchKeyName) {
                return this.getName().compareTo(pContent.getName()) > 0;
            }
            return this.getSales() > pContent.getSales();
        }
        return false;
    }

    @Override
    public boolean isEqual(Customer pContent) {
        if(pContent != null) {
            if (searchKeyName) {
                return this.getName().compareTo(pContent.getName()) == 0;
            }
            return this.getSales() == pContent.getSales();
        }
        return false;
    }

    @Override
    public boolean isLess(Customer pContent) {
        if(pContent != null) {
            if (searchKeyName) {
                return this.getName().compareTo(pContent.getName()) < 0;
            }
            return this.getSales() < pContent.getSales();
        }
        return false;
    }

    @Override
    public String toString(){
        return name+" "+sales;
    }
}
