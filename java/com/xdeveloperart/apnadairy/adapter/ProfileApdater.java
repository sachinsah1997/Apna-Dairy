package com.xdeveloperart.apnadairy.adapter;

public class ProfileApdater {

    //profile
    String pname;
    String paddress;
    String pnumber;
    String pgender;
    private static ProfileApdater instance;

    public ProfileApdater(){}

    public static synchronized ProfileApdater getInstance(){
        if(instance==null){
            instance=new ProfileApdater();
        }
        return instance;
    }


    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPaddress() {
        return paddress;
    }

    public void setPaddress(String paddress) {
        this.paddress = paddress;
    }

    public String getPnumber() {
        return pnumber;
    }

    public void setPnumber(String pnumber) {
        this.pnumber = pnumber;
    }

    public String getPgender() {
        return pgender;
    }

    public void setPgender(String pgender) {
        this.pgender = pgender;
    }

    public ProfileApdater(String pname,String paddress,String pnumber,String pgender){

        this.pname=pname;
        this.paddress=paddress;
        this.pnumber=pnumber;
        this.pgender =pgender;
    }
}