package com.example.c482attempt;

/**
 * InHouse is a class that describes an InHouse Parts object.
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * InHouse is a Part class for an Object called InHouse.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * setMachineId "sets" machineId in addPartController.saveButton
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * getMachineId "gets" the machineId in modifyPartFormController.partSelection
     * @return machineId
     */
    public int getMachineId() {
        return machineId;
    }
}