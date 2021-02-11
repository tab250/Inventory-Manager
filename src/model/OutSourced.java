/**
 * @author Tyler Brown
 */
package model;

/**
 * This class is a subclass of the Part class.
 * It contains one additional variable, company name.
 */
public class OutSourced extends Part{

    private String companyName;

    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return the Company Name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the Company Name to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
