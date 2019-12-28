package com.xdeveloperart.apnadairy.adapter;

public class ProfileApdater {



    //profile

    String pname;
    String paddress;
    String pnumber;
    String pgender;

    String imgurl;


    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
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

    public ProfileApdater(String imgurl,String pname,String paddress,String pnumber,String pgender){

        this.imgurl=imgurl;
        this.pname=pname;
        this.paddress=paddress;
        this.pnumber=pnumber;
        this.pgender =pgender;

    }

}