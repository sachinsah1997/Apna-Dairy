package com.xdeveloperart.apnadairy;

public class ProductAdapter {

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPiecesPerCarat() {
        return piecesPerCarat;
    }

    public void setPiecesPerCarat(String piecesPerCarat) {
        this.piecesPerCarat = piecesPerCarat;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(String salesPrice) {
        this.salesPrice = salesPrice;
    }

    public String productName;
        public String piecesPerCarat;
        public String costPrice;
	public String salesPrice;
    	private static ProductAdapter instance;

	public ProductAdapter(){}

        public static synchronized ProductAdapter getInstance(){
            if(instance==null){
                instance=new ProductAdapter();
            }
            return instance;
        }

        public static void setInstance(ProductAdapter instance) {
            ProductAdapter.instance = instance;
        }

  	public ProductAdapter(String productName,String piecesPerCarat,String costPrice,String salesPrice) {
        	this.productName=productName;
        	this.piecesPerCarat=piecesPerCarat;
        	this.costPrice=costPrice;
		this.salesPrice=salesPrice;
	}
}


