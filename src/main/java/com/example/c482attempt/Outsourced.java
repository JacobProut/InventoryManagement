package com.example.c482attempt;

/**
 * Outsourced is a class that describes an Outsourced Parts object.
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * Outsourced is an Outsourced class for an Object called Outsourced.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * setCompanyName "sets" the company name in "addPartController.saveButton".
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * getCompanyName "gets" the company name in "modifyPartController.partSelection".
     * @return companyName
     */
    public String getCompanyName() {
        return companyName;
    }
}
