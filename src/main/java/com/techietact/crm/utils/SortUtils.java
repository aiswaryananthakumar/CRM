package com.techietact.crm.utils;

/**
 * Created by CryptoSingh1337 on 6/3/2021
 */

public enum SortUtils {
    SortByFirstName(0), SortByLastName(1), SortByEmail(2), SortByName(1), 
    SortByStartDate(2), 
    SortByEndDate(3), 
    SortByStatus(4);;
    private final int value;

    SortUtils(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    /**
     * Gets the sort field name based on the enum constant.
     * @return Field name for sorting
     */
    public String getFieldName() {
        switch (this) {
            case SortByFirstName:
                return "firstName";
            case SortByLastName:
                return "lastName";
            case SortByEmail:
                return "email";
            case SortByName:
                return "name";
            case SortByStartDate:
                return "startDate";
            case SortByEndDate:
                return "endDate";
            case SortByStatus:
                return "status";
            default:
                throw new IllegalArgumentException("Invalid sort option: " + this);
        }
    }
    
}
