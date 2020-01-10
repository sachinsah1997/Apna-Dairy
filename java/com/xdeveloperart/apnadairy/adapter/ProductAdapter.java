package com.xdeveloperart.apnadairy.adapter;

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

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public  String stock;
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

  	public ProductAdapter(String productName,String piecesPerCarat,String costPrice,String salesPrice,String stock) {
        	this.productName=productName;
        	this.piecesPerCarat=piecesPerCarat;
        	this.costPrice=costPrice;
		    this.salesPrice=salesPrice;
		    this.stock=stock;
	}
}


