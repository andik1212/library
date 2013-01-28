package com.example.andik1212.helper;

/**
 * Created with IntelliJ IDEA.
 * User: Andik
 * Date: 28.01.13
 * Time: 3:52
 * To change this template use File | Settings | File Templates.
 */
public class CustomDate {
    private String input;
    private String output;
    public CustomDate(String input){
        this.input = input;
    }
    public  String convert(){
        String mon = input.substring(5,7);
        if (mon.equals("01")) {
            output = "Jan ";
        } else if (mon.equals("02")) {
            output = "Feb ";
        } else if (mon.equals("03")) {
            output = "Mar ";
        } else if (mon.equals("04")) {
            output = "Apr ";
        } else if (mon.equals("05")) {
            output = "May ";
        } else if (mon.equals("06")) {
            output = "Jun ";
        } else if (mon.equals("07")) {
            output = "Jul ";
        } else if (mon.equals("08")) {
            output = "Aug ";
        } else if (mon.equals("09")) {
            output = "Sep ";
        } else if (mon.equals("10")) {
            output = "Oct ";
        } else if (mon.equals("11")) {
            output = "Nov ";
        } else if (mon.equals("12")) {
            output = "Dec ";
        }

        output += input.substring(8,10)+", ";

        output += input.substring(0,4);

        return output;
    }

}
