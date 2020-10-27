/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import sun.java2d.pipe.SpanShapeRenderer;

/**
 *
 * @author István
 */
public class Costumers {
    final private SimpleStringProperty firstName;
    final private SimpleStringProperty surName;
    final private SimpleStringProperty checkInDate;
    final private SimpleStringProperty checkOutDate;
    final private SimpleStringProperty roomNumber;
    final private SimpleStringProperty mobile;
    final private SimpleStringProperty id;

    public Costumers(){
        this.surName = new SimpleStringProperty("");
        this.firstName = new SimpleStringProperty("");
        this.checkInDate = new SimpleStringProperty("");
        this.checkOutDate = new SimpleStringProperty("");
        this.roomNumber = new SimpleStringProperty("");
        this.mobile = new SimpleStringProperty("");
        this.id = new SimpleStringProperty("");
    }
    
    public Costumers(String firstName, String surName, String checkInDate, String checkOutDate, Integer roomNumber, Integer mobileNumber) {
        this.firstName = new SimpleStringProperty(firstName);
        this.surName = new SimpleStringProperty(surName);
        this.checkInDate = new SimpleStringProperty(checkInDate);
        this.checkOutDate = new SimpleStringProperty(checkOutDate);
        this.roomNumber = new SimpleStringProperty(String.valueOf(roomNumber));
        this.mobile = new SimpleStringProperty(String.valueOf(mobileNumber));
        this.id = new SimpleStringProperty("");
    }
    
     public Costumers(Integer id, String firstName, String surName, String checkInDate, String checkOutDate, Integer roomNumber, Integer mobileNumber) {
        this.firstName = new SimpleStringProperty(firstName);
        this.surName = new SimpleStringProperty(surName);
        this.checkInDate = new SimpleStringProperty(checkInDate);
        this.checkOutDate = new SimpleStringProperty(checkOutDate);
        this.roomNumber = new SimpleStringProperty(String.valueOf(roomNumber));
        this.mobile = new SimpleStringProperty(String.valueOf(mobileNumber));
        this.id = new SimpleStringProperty(String.valueOf(id));
    }

    public String getFirstName() {
        return firstName.get();
    }
    
    public void setFirstName(String fName) {
        firstName.set(fName);
    }

    public String getSurName() {
        return surName.get();
    }
    
    public void setSurName(String sName) {
        surName.set(sName);
    }

    public String getCheckInDate() {
        return checkInDate.get();
    }
    
    public void setCheckInDate(String dateIn) {
        checkInDate.set(dateIn);
    }

    public String getCheckOutDate() {
        return checkOutDate.get();
    }
    public void setCheckOutDate(String dateOut) {
        checkOutDate.set(dateOut);
    }

    public String getRoomNumber() {
        return roomNumber.get();
    }
    public void setRoomNumber(String number) {
        roomNumber.set(number);
    }
    
    public String getMobile() {
        return mobile.get();
    }
    public void setMobile(String number) {
        mobile.set(number);
    }
    
    public String getId() {
        return id.get();
    }
    public void setId(String number) {
        id.set(number);
    }
    
    
}
