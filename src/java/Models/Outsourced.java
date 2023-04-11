package Models;

/**
 * The Outsourced class extends the Part class.
 * It contains an additional attribute, companyName.
 */
public class Outsourced extends Part {

    /**
     * The name of the company that supplies the part.
     */
    private String companyName;

    /**
     * Creates a new Outsourced part with the specified parameters.
     *
     * @param id the ID of the part
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the current inventory level of the part
     * @param min the minimum inventory level of the part
     * @param max the maximum inventory level of the part
     * @param companyName The name of the company that supplies the part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets the name of the company that supplies the part.
     * @param companyName The name of the company that supplies the part.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Returns the name of the company that supplies the part.
     * @return The name of the company that supplies the part.
     */
    public String getCompanyName() {
        return companyName;
    }
}
