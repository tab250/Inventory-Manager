/**
 * @author Tyler Brown
 */
package model;

/**
 * This class is a subclass of the Part class.
 * It contains one additional variable, machine Id.
 */
public class InHouse extends Part {

    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @return the Machine Id
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * @param machineId the Machine Id to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
