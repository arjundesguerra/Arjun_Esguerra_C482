package Models;
/**
 * The InHouse class extends the Part class.
 * It contains an additional attribute, machineId.
 */
public class InHouse extends Part {
    /**
     * The ID of the machine.
     */
    private int machineId;

    /**
     * Constructor for the InHouse class.
     *
     * @param id the ID of the part
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the current inventory level of the part
     * @param min the minimum inventory level of the part
     * @param max the maximum inventory level of the part
     * @param machineId the machine ID of the part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Sets the machineID
     *
     * @param machineId the ID of the machine.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Gets the machineID
     * @return the ID of the machine used to manufacture the part
     */
    public int getMachineId() {
        return machineId;
    }

}
