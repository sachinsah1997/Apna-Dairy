package com.xdeveloperart.apnadairy.adapter;

public class TransactionAdapter {

	public String transcationDate;
	public String customerName;
	public String salesmanName;
	public float previousBalance;
	public float currentAmount;
	public float totalAmount;
	public float creditAmount;
	public float balance;
	private static TransactionAdapter instance;

	public String getTranscationDate() {
		return transcationDate;
	}

	public void setTranscationDate(String transcationDate) {
		this.transcationDate = transcationDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSalesmanName() {
		return salesmanName;
	}

	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}

	public float getPreviousBalance() {
		return previousBalance;
	}

	public void setPreviousBalance(float previousBalance) {
		this.previousBalance = previousBalance;
	}

	public float getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(float currentAmount) {
		this.currentAmount = currentAmount;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public float getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(float creditAmount) {
		this.creditAmount = creditAmount;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

		public TransactionAdapter(){}

        public static synchronized TransactionAdapter getInstance(){
            if(instance==null){
                instance=new TransactionAdapter();
            }
            return instance;
        }

        public static void setInstance(TransactionAdapter instance) {
            TransactionAdapter.instance = instance;
        }

  		public TransactionAdapter(String transcationDate, String customerName,float previousBalance, float currentAmount, float totalAmount, float creditAmount, float balance) {
        	this.transcationDate=transcationDate;
        	this.customerName=customerName;
			this.previousBalance=previousBalance;
			this.currentAmount=currentAmount;
			this.totalAmount=totalAmount;
			this.creditAmount=creditAmount;
			this.balance=balance;
	}
}


//https://stackoverflow.com/questions/42173438/getting-the-child-list-of-a-node-then-iterate-for-similar-values-in-another-node
//https://stackoverflow.com/questions/18207470/adding-table-rows-dynamically-in-android

