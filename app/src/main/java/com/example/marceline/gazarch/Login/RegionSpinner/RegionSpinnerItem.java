package com.example.marceline.gazarch.Login.RegionSpinner;

public class RegionSpinnerItem {

    String text;
    Integer imageId;

    public RegionSpinnerItem(String text, Integer imageId){
        this.text=text;
        this.imageId=imageId;
    }

    public String getText(){
        return text;
    }

    public Integer getImageId(){
        return imageId;
    }
}