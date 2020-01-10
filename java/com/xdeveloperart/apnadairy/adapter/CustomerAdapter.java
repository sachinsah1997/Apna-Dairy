package com.xdeveloperart.apnadairy.adapter;

public class CustomerAdapter {

        public String customerName;
        public String customerNumber;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getSalesmanAssociated() {
        return salesmanAssociated;
    }

    public void setSalesmanAssociated(String salesmanAssociated) {
        this.salesmanAssociated = salesmanAssociated;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String salesmanAssociated;
    public String area;
    private static CustomerAdapter instance;

	public CustomerAdapter(){}

    public static synchronized CustomerAdapter getInstance(){
            if(instance==null){
                instance=new CustomerAdapter();
            }
            return instance;
        }

        public static void setInstance(CustomerAdapter instance) {
            CustomerAdapter.instance = instance;
        }

  	public CustomerAdapter(String customerName,String customerNumber,String salesmanAssociated, String area) {
        	this.customerName=customerName;
        	this.customerNumber=customerNumber;
		    this.salesmanAssociated=salesmanAssociated;
        	this.area=area;
	}
}


