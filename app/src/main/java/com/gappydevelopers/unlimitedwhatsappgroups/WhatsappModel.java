package com.gappydevelopers.unlimitedwhatsappgroups;

/**
 * Created by yash on 17/9/17.
 */

public class WhatsappModel {

    String gp_name,gp_link,gp_category,gp_image;

    public WhatsappModel (String gp_name,String gp_link, String gp_category, String gp_image){
        this.gp_name = gp_name;
        this.gp_link = gp_link;
        this.gp_category = gp_category;
        this.gp_image = gp_image;
    }

    public String getGp_name(){
        return gp_name;
    }

    public String getGp_link(){
        return gp_link;
    }

    public String getGp_category() {
        return gp_category;
    }

    public String getGp_image() {
        return gp_image;
    }
}
