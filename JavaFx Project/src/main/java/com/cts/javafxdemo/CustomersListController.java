package com.cts.javafxdemo;

public class CustomersListController {
    private Integer customerId;
    private String businessName;
    private String firstname;
    private String lastname;
    private String street;
    private String village;
    private String city;
    private String country;
    private String mobilePhone;
    private String businessPhone;
    private String email;
    private String customerType;
    private String note;
    private Boolean active;

    public CustomersListController(Integer customerId, String businessName, String firstname, String lastname,
                                   String street, String village, String city, String country,
                                   String mobilePhone, String businessPhone, String email, String customerType,
                                   String note, Boolean active) {
        this.customerId = customerId;
        this.businessName = businessName;
        this.firstname = firstname;
        this.lastname = lastname;
        this.street = street;
        this.village = village;
        this.city = city;
        this.country = country;
        this.mobilePhone = mobilePhone;
        this.businessPhone = businessPhone;
        this.email = email;
        this.customerType = customerType;
        this.note = note;
        this.active = active;
    }

    public Integer getCustomerId() { return customerId; }
    public String getBusinessName() { return businessName; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public String getStreet() { return street; }
    public String getVillage() { return village; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public String getMobilePhone() { return mobilePhone; }
    public String getBusinessPhone() { return businessPhone; }
    public String getEmail() { return email; }
    public String getCustomerType() { return customerType; }
    public String getNote() { return note; }
    public Boolean getActive() { return active; }
}
}
