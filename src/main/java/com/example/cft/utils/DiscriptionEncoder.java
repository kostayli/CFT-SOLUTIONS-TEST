package com.example.cft.utils;

public class DiscriptionEncoder {
    private String text;
    public DiscriptionEncoder(String text){
        this.text = text;
    }
    public String[] splitParam(){
        if(this.text.contains("{") && this.text.contains("}") ) {
            int i = text.indexOf("}"); // 6
            this.text = text.substring(0, i) + this.text.substring(i + 1);
            i = this.text.indexOf("{"); // 6
            this.text = this.text.substring(0, i) + this.text.substring(i + 1);
        }
            String[] pairs = this.text.split(";");

    return pairs;
    }

    public String[] splitParam(String text){
        if(text.contains("{") && text.contains("}") ) {
            int i = text.indexOf("}"); // 6
            text = text.substring(0, i) + text.substring(i + 1);
            i = this.text.indexOf("{"); // 6
            text = text.substring(0, i) + text.substring(i + 1);
        }
        String[] pairs = text.split(";");

        return pairs;
    }

    public String getKeyParam(){
        String[] pairs = this.text.split(":");
        return pairs[0];
    }

    public String getKeyParam(String text){
        String[] pairs = text.split(":");
        return pairs[0];
    }

    public String getValueParam(){
        String[] pairs = this.text.split(":");
        if(pairs[1].contains("[") && pairs[1].contains("]") ) {
            int i = pairs[1].indexOf("]"); // 6
            pairs[1] = pairs[1].substring(0, i) + pairs[1].substring(i + 1);
            i = pairs[1].indexOf("["); // 6
            pairs[1] = pairs[1].substring(0, i) + pairs[1].substring(i + 1);
        }
        return pairs[1];
    }

    public String getValueParam(String text){
        String[] pairs = text.split(":");
        if(pairs[1].contains("[") && pairs[1].contains("]") ) {
            int i = pairs[1].indexOf("]"); // 6
            pairs[1] = pairs[1].substring(0, i) + pairs[1].substring(i + 1);
            i = pairs[1].indexOf("["); // 6
            pairs[1] = pairs[1].substring(0, i) + pairs[1].substring(i + 1);
        }
        return pairs[1];
    }

    public String[] getMassiveParam(){
        String[] pairs = this.text.split(",");
        return pairs;
    }

    public String[] getMassiveParam(String text){
        String[] pairs = text.split(",");
        return pairs;
    }

    public String setMassiveParam(){
        return "{variants:["+this.text+"]}";
    }
    public String setMassiveParam(String text){
        return "{variants:["+text+"]}";
    }

    public String setValueParam(String text){
        return "{type:"+text+"}";
    }
    public String setValueParam(){
        return "{type:"+this.text+"}";
    }


}
