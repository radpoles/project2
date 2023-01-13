package model;

public class InHouse extends Part {
    private int machineId;

        public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
           super(id, name, price, stock, min, max);
           this.machineId = machineID;
        }
    /**
     * returns the machine ID
     * @return the machine ID
     */
    public int getMachineId() {
            return machineId;
    }

    /**
     * sets the machine ID
     * @param machineId the machine ID to set
     */
    public void setMachineId(int machineId) {
            this.machineId = machineId;
    }
}


