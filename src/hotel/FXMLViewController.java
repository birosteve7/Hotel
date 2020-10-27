/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

import java.net.URL;
import java.nio.file.attribute.AclEntryType;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 *
 * @author István
 */
public class FXMLViewController implements Initializable {
    
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TabPane tabPane;
    @FXML
    private Button exit;
    @FXML
    private TableView table;
    @FXML
    private TextField firstName;
    @FXML
    private TextField surName;
    @FXML
    private TextField roomNumber;
    @FXML
    private TextField checkIn;
    @FXML
    private TextField checkOut;
    @FXML
    private TextField mobile;
    @FXML
    private TextField inputroom;
    @FXML
    private TextField outputFName;
    @FXML
    private TextField outputSName;
    @FXML
    private TextField outputCheckIn;
    @FXML
    private TextField outputCheckOut;
    @FXML
    private TextField outputMobile;
    @FXML
    private Button find;
    @FXML
    private Button show;
    @FXML
    private ListView outputList;
    
    
    DB db = new DB();
    private final ObservableList<Costumers> data = FXCollections.observableArrayList();
    HashMap<Integer,Boolean> roomRes = new HashMap<Integer,Boolean>();
    
    public void updateRoomRes() {
        for (int i=0; i<15; i++ ){
            Costumers guest = db.findGuestFromRoomNumber(String.valueOf(i));
            roomRes.put(i, (guest == null)? Boolean.FALSE:Boolean.TRUE );
        }
    }
    @FXML
    public void clearFindTabFields(){
        outputList.getItems().clear();
        outputFName.clear();
        outputSName.clear();
        outputCheckIn.clear();
        outputCheckOut.clear();
        outputMobile.clear();
        inputroom.clear();
    }
    @FXML
    public void showAvailableRooms() {
        outputList.getItems().clear();
        for (int i=1; i<15; i++ ){
           if (!roomRes.get(i))
               outputList.getItems().add(i);
        }
    }
    
    public static boolean isvalidDate(String strDate) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern ( "dd.MM.uuuu" );
        try {
            LocalDate ld = LocalDate.parse ( strDate , f );
            System.out.println ( "ld: " + ld );
        } catch ( Exception e ) {
            System.out.println ( "ERROR: " + e );
            return false;
        }
        return true;
    }    
    @FXML
    private void addGuest(ActionEvent event) {
        String checkInDate = checkIn.getText();
        String checkOutDate = checkOut.getText();
        String tmpRNumber = roomNumber.getText();
        String tmpMNumber = mobile.getText();
        Integer rNumber;
        Integer mNumber;
        if (!tmpRNumber.equals("")) {
            rNumber = Integer.valueOf(tmpRNumber);
        }else{
            alert("Please give a room number");
            return;
        }
        if (!tmpMNumber.equals("")) {
            mNumber = Integer.valueOf(tmpMNumber);
        }else{
            alert("Please give a mobile number");
            return;
        }
        if ( !(isvalidDate(checkInDate) &&
             isvalidDate(checkOutDate)) ) { 
            alert("Invalid date format");
            return;
        }
            Costumers newGuest = new Costumers(firstName.getText(), surName.getText(), checkInDate, checkOutDate, rNumber ,mNumber);
            data.add(newGuest);
            db.addContact(newGuest);
            roomRes.put(rNumber, Boolean.TRUE);
            firstName.clear();
            surName.clear();
            roomNumber.clear();
            checkIn.clear();
            checkOut.clear();
            mobile.clear();
    }
    
    @FXML
    private void findGuestfromRoom(ActionEvent event) {
        String key = inputroom.getText();
        System.out.println("key"+key);
        if (key.equals("")){
           alert("Please give a room number");
           return; 
        }
        Costumers guest = db.findGuestFromRoomNumber(key);
        if (guest == null ){
            alert("The room is free");
            return;
        }
        outputFName.setText(guest.getFirstName());
        outputSName.setText(guest.getSurName());
        outputCheckIn.setText(guest.getCheckInDate());
        outputCheckOut.setText(guest.getCheckOutDate());
        outputMobile.setText(guest.getMobile());
    }
    
    public void setTableData() {
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(130);
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Costumers, String>("firstName"));

        firstNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Costumers, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Costumers, String> t) {
                Costumers actualPerson = (Costumers) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setFirstName(t.getNewValue());
                db.updateContact(actualPerson);
            }
        }
        );

        TableColumn surNameCol = new TableColumn("Sur Name");
        surNameCol.setMinWidth(130);
        surNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        surNameCol.setCellValueFactory(new PropertyValueFactory<Costumers, String>("surName"));

        surNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Costumers, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Costumers, String> t) {
                Costumers actualPerson = (Costumers) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setSurName(t.getNewValue());
                db.updateContact(actualPerson);
            }
        }
        );

        TableColumn checkInCol = new TableColumn("Checkin Date");
        checkInCol.setMinWidth(130);
        checkInCol.setCellValueFactory(new PropertyValueFactory<Costumers, String>("checkInDate"));
        checkInCol.setCellFactory(TextFieldTableCell.forTableColumn());

        checkInCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Costumers, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Costumers, String> t) {
                Costumers actualPerson = (Costumers) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setCheckInDate(t.getNewValue());
                db.updateContact(actualPerson);
            }
        }
        );
        
        TableColumn checkOutCol = new TableColumn("Checkout Date");
        checkOutCol.setMinWidth(130);
        checkOutCol.setCellValueFactory(new PropertyValueFactory<Costumers, String>("checkOutDate"));
        checkOutCol.setCellFactory(TextFieldTableCell.forTableColumn());

        checkOutCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Costumers, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Costumers, String> t) {
                Costumers actualPerson = (Costumers) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setCheckOutDate(t.getNewValue());
                db.updateContact(actualPerson);
            }
        }
        );
        
        TableColumn roomNumberCol = new TableColumn("Room");
        roomNumberCol.setMinWidth(50);
        roomNumberCol.setCellValueFactory(new PropertyValueFactory<Costumers, String>("roomNumber"));
        roomNumberCol.setCellFactory(TextFieldTableCell.forTableColumn());

        roomNumberCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Costumers, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Costumers, String> t) {
                System.out.println("Változott: "+Integer.valueOf(t.getOldValue()));
                roomRes.put(Integer.valueOf(t.getOldValue()), Boolean.FALSE);
                Costumers actualPerson = (Costumers) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setRoomNumber(t.getNewValue());
                db.updateContact(actualPerson);
                roomRes.put(Integer.valueOf(t.getNewValue()), Boolean.TRUE);
                System.out.println("Változott: "+Integer.valueOf(t.getNewValue()));
            }
        }
        );
        
        TableColumn mobileCol = new TableColumn("Mobile");
        mobileCol.setMinWidth(100);
        mobileCol.setCellValueFactory(new PropertyValueFactory<Costumers, String>("mobile"));
        mobileCol.setCellFactory(TextFieldTableCell.forTableColumn());

        mobileCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Costumers, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Costumers, String> t) {
                Costumers actualPerson = (Costumers) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setMobile(t.getNewValue());
                db.updateContact(actualPerson);
            }
        }
        );
        

        TableColumn removeCol = new TableColumn( "Törlés" );
        removeCol.setMinWidth(100);

        Callback<TableColumn<Costumers, String>, TableCell<Costumers, String>> cellFactory = 
                new Callback<TableColumn<Costumers, String>, TableCell<Costumers, String>>()
                {
                    @Override
                    public TableCell call( final TableColumn<Costumers, String> param )
                    {
                        final TableCell<Costumers, String> cell = new TableCell<Costumers, String>()
                        {   
                            final Button btn = new Button( "Törlés" );

                            @Override
                            public void updateItem( String item, boolean empty )
                            {
                                super.updateItem( item, empty );
                                if ( empty )
                                {
                                    setGraphic( null );
                                    setText( null );
                                }
                                else
                                {
                                    btn.setOnAction( ( ActionEvent event ) ->
                                            {
                                                Costumers person = getTableView().getItems().get( getIndex() );
                                                data.remove(person);
                                                db.removeContact(person);
                                                roomRes.put(Integer.valueOf(person.getRoomNumber()), Boolean.FALSE);
                                                System.out.println("Változott: "+roomRes.get(Integer.valueOf(person.getRoomNumber())));
                                       } );
                                    setGraphic( btn );
                                    setText( null );
                                }
                            }
                        };
                        return cell;
                    }
                };

        removeCol.setCellFactory( cellFactory );
        
        table.getColumns().addAll(firstNameCol, surNameCol, checkInCol, checkOutCol, roomNumberCol, mobileCol, removeCol);

        data.addAll(db.getAllContacts());

        table.setItems(data);
    }
     private void alert(String text) {
        tabPane.setDisable(true);
        tabPane.setOpacity(0.4);
        
        Label label = new Label(text);
        Button alertButton = new Button("OK");
        VBox vbox = new VBox(label, alertButton);
        vbox.setAlignment(Pos.CENTER);
        
        alertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                tabPane.setDisable(false);
                tabPane.setOpacity(1);
                vbox.setVisible(false);
            }
        });
        
        anchorPane.getChildren().add(vbox);
        anchorPane.setTopAnchor(vbox, 300.0);
        anchorPane.setLeftAnchor(vbox, 300.0);
    }
     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTableData();
        updateRoomRes();
    }    
    
}
