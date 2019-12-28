package com.xdeveloperart.apnadairy.adapter;

public class SalesmanAdapter {

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getSalesmanNumber() {
        return salesmanNumber;
    }

    public void setSalesmanNumber(String salesmanNumber) {
        this.salesmanNumber = salesmanNumber;
    }

    public String salesmanName;
        public String salesmanNumber;
    	private static SalesmanAdapter instance;

	public SalesmanAdapter(){}

        public static synchronized SalesmanAdapter getInstance(){
            if(instance==null){
                instance=new SalesmanAdapter();
            }
            return instance;
        }

        public static void setInstance(SalesmanAdapter instance) {
            SalesmanAdapter.instance = instance;
        }

  	public SalesmanAdapter(String salesmanName,String salesmanNumber) {
        	this.salesmanName=salesmanName;
        	this.salesmanNumber=salesmanNumber;
	}
}


