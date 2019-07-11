package com.fluentWait.test.bin;

public class CustomerBin {

    // Fields
    private String id;
    private String firstName;
    private String lastName;
    private String phone;
    private String city;

    // Getters
    public String getCustomerId(){
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPhone() {
        return phone;
    }
    public String getCity() {
        return city;
    }
    // Setters
    public void setId(String id) {
        this.id = id;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setCity(String city) {
        this.city = city;
    }
}
