/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author István
 */
public class DB {
    final String URL = "jdbc:derby:sampleDB;create=true";
    final String USERNAME = "";
    final String PASSWORD = "";
    
    //Létrehozzuk a kapcsolatot (hidat)
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;
    
    
    public DB() {
        //Megpróbáljuk életre kelteni
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A híd létrejött");
        } catch (SQLException ex) {
            System.out.println("Valami baj van a connection (híd) létrehozásakor.");
            System.out.println(""+ex);
        }
        
        //Ha életre kelt, csinálunk egy megpakolható teherautót
        if (conn != null){
            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("Valami baj van van a createStatament (teherautó) létrehozásakor.");
                System.out.println(""+ex);
            }
        }
        
        //Megnézzük, hogy üres-e az adatbázis? Megnézzük, létezik-e az adott adattábla.
        try {           
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a DatabaseMetaData (adatbázis leírása) létrehozásakor..");
            System.out.println(""+ex);
        }
        
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "GUESTS", null);
            if(!rs.next())
            { 
             createStatement.execute("create table guests(id INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), firstname varchar(20), surname varchar(20), roomnumber int, checkindate date, checkoutdate date, mobile int)");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adattáblák létrehozásakor.");
            System.out.println(""+ex);
        }       
    }
    
    public ArrayList<Costumers> getAllContacts(){
        String sql = "select * from guests";
        ArrayList<Costumers> users = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            users = new ArrayList<>();
            
            while (rs.next()){
                Costumers actualCostumer = new Costumers(rs.getInt("id"),rs.getString("firstname"),rs.getString("surname"),rs.getString("checkindate"), 
                                                         rs.getString("checkoutdate"), rs.getInt("roomnumber"), rs.getInt("mobile"));
                users.add(actualCostumer);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a userek kiolvasásakor");
            System.out.println(""+ex);
        }
      return users;
    }
    
    public void addContact(Costumers costumer){
      try {
        String sql = "insert into guests (firstname, surname, checkindate ,checkoutdate, roomnumber, mobile) values (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, costumer.getFirstName());
        preparedStatement.setString(2, costumer.getSurName());
        preparedStatement.setString(3, costumer.getCheckInDate());
        preparedStatement.setString(4, costumer.getCheckOutDate());
        preparedStatement.setString(5, costumer.getRoomNumber());
        preparedStatement.setString(6, costumer.getMobile());
        preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact hozzáadásakor");
            System.out.println(""+ex);
        }
    }
    
    public void updateContact(Costumers costumer){
      try {
            String sql = "update guests set firstname = ?, surname = ?, checkindate = ?, checkoutdate = ?, roomnumber = ?, mobile=? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, costumer.getFirstName());
            preparedStatement.setString(2, costumer.getSurName());
            preparedStatement.setDate(3, Date.valueOf(costumer.getCheckInDate()));
            preparedStatement.setDate(4, Date.valueOf(costumer.getCheckOutDate()));
            preparedStatement.setString(5, costumer.getRoomNumber());
            preparedStatement.setString(6, costumer.getMobile());
            preparedStatement.setInt(7, Integer.parseInt(costumer.getId()));
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact hozzáadásakor");
            System.out.println(""+ex);
        }
    }
    
     public void removeContact(Costumers costumer){
      try {
            String sql = "delete from guests where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(costumer.getId()));
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact törlésekor");
            System.out.println(""+ex);
        }
    }
     
     public Costumers findGuestFromRoomNumber(String number){ 
        String sql = "select * from guests where roomnumber = "+Integer.valueOf(number)+"";
        Costumers guest = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            if (rs.next()) {
                guest = new Costumers(rs.getInt("id"),rs.getString("firstname"),rs.getString("surname"),rs.getString("checkindate"), 
                                                         rs.getString("checkoutdate"), rs.getInt("roomnumber"), rs.getInt("mobile"));
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a userek kiolvasásakor");
            System.out.println(""+ex);
        }
      return guest;
     }  
}
