package com.example.esieben_sizebook;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.text.NumberFormat;

/**
 * Created by Erick on 2/2/2017.
 *
 * Contains entered information about a user, including name and several measurements used for
 * determining clothes sizes, as well as date of validity of measurements, and a comment.
 *
 * only the name parameter is required, all other fields can be left blank
 *
 * measurements are taken as floats, but stored as strings containing only one decimal place
 *
 * date parameter is currently stored as a string only, due to difficulty encountered in converting
 * back from a string object to a date object
 */

public class Profile implements Serializable {
    private String name;
    private String date;
    private String neck;
    private String bust;
    private String chest;
    private String waist;
    private String hip;
    private String inseam;
    private String comment;

    /**
     * Constructor called when only a name is given
     * @param name holds the users name, all other values are initialized to zero
     *              to prevent difficulties in passing information between activities
     */
    public Profile(String name){
        this.name = name;
        this.setNeck(0);
        this.setBust(0);
        this.setChest(0);
        this.setWaist(0);
        this.setHip(0);
        this.setInseam(0);
    }

    /**
     * Constructor called when a name and any other fields are given
     * fields are given as floats and stored as strings with one decimal place
     * Any ignored fields must be set to zero
     *
     * currently unused, but available for further development purposes
     *
     * @param name user associated with profile information
     * @param neck measurement of neck circumference (inches)
     * @param bust measurement of bust circumference (inches)
     * @param chest measurement of chest circumference (inches)
     * @param waist measurement of waist circumference (inches)
     * @param hip measurement of hip circumference (inches)
     * @param inseam measurement of inseam length (inches)
     * @param comment optional additional information regarding profile
     */
    public Profile(String name, String date, float neck, float bust, float chest, float waist, float hip, float inseam, String comment){
        this.name = name;
        this.date = date;
        this.neck = TrimDecimal(neck);
        this.bust = TrimDecimal(bust);
        this.chest = TrimDecimal(chest);
        this.waist = TrimDecimal(waist);
        this.hip = TrimDecimal(hip);
        this.inseam = TrimDecimal(inseam);
        this.comment = comment;
    }

    /**
     * method used to trim decimal places from float inputs: inputs are loosely controlled by
     * maxLength constraints, but can still be bypassed with unrealistic measurements
     *
     * <bold>example:</bold> user enters a neck measurement with only a single digit before the
     * decimal place
     *
     * @param num is the number to be trimmed
     * @return a string version of the float with only one decimal place
     */
    private String TrimDecimal(float num){
        //decimal place formatting solution adapted from http://kodejava.org/how-do-i-format-a-number/
        //viewed on 02.02.17
        NumberFormat formatter = new DecimalFormat("#0.0");
        return formatter.format(num);
    }

    /**
     * method used to access the 'name' value of the profile object
     * @return name of profile's associated user
     */
    public String getName(){
        return this.name;
    }

    /**
     * method used to access the 'neck' value of the profile object
     * @return neck circumference (inches) as a string
     */
    public String getNeck() { return this.neck; }

    /**
     * method used to access the 'bust' value of the profile object
     * @return bust circumference (inches) as a string
     */
    public String getBust() { return this.bust; }

    /**
     * method used to access the 'chest' value of the profile object
     * @return chest circumference (inches) as a string
     */
    public String getChest() { return this.chest; }

    /**
     * method used to access the 'waist' value of the profile object
     * @return waist circumference (inches) as a string
     */
    public String getWaist() { return this.waist; }

    /**
     * method used to access the 'hip' value of the profile object
     * @return hip circumference (inches) as a string
     */
    public String getHip() { return this.hip; }

    /**
     * method used to access the 'inseam' value of the profile object
     * @return inseam length (inches) as a string
     */
    public String getInseam() { return this.inseam; }

    /**
     * method used to access the 'date' value of the profile object
     * @return date of validity of measurements as a string
     */
    public String getDate() { return this.date;}

    /**
     * method used to access the 'comment' string of the profile object
     * @return any entered comment string
     */
    public String getComment() { return this.comment;}

    /**
     * method used to set the 'name' String of the profile object
     * @param name name of profile's associated user
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * method used to set the 'neck' value of the profile object
     * @param neck neck measurement input
     */
    public void setNeck(float neck) { this.neck = TrimDecimal(neck); }

    /**
     * method used to set the 'bust' value of the profile object
     * @param bust bust measurement input
     */
    public void setBust(float bust) { this.bust = TrimDecimal(bust); }

    /**
     * method used to set the 'chest' value of the profile object
     * @param chest chest measurement input
     */
    public void setChest(float chest) { this.chest = TrimDecimal(chest); }

    /**
     * method used to set the 'waist' value of the profile object
     * @param waist waist measurement input
     */
    public void setWaist(float waist) { this.waist = TrimDecimal(waist); }

    /**
     * method used to set the 'hip' value of the profile object
     * @param hip measurement input
     */
    public void setHip(float hip) { this.hip = TrimDecimal(hip); }

    /**
     * method used to set the 'inseam' value of the profile object
     * @param inseam inseam measurement input
     */
    public void setInseam(float inseam) { this.inseam = TrimDecimal(inseam); }

    /**
     * method used to set the 'date' String of the profile object
     * @param date date measurement input
     */
    public void setDate(String date) { this.date = date;}

    /**
     * method used to set the 'comment' String of the profile object
     * @param comment additional comment string specified by user
     */
    public void setComment(String comment) { this.comment = comment;}

    /**
     * method used to determine how the profile object is displayed in the app
     * Each profile object contained in the ListView in MainActivity will be shown in this format
     * @return a string containing the necessary data and format to display this object
     */
    public String toString() {
        return name + "\nNeck: " + neck + " | Bust: " + bust + " | Chest: " + chest + "\nWaist: " + waist + " | Hip: " + hip + " | Inseam: " + inseam;
    }
}
