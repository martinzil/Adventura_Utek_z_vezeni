/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import logika.Hra;
import logika.IHra;
import main.Main;

/**
 * Třída MenuLista vytváří menu adventury.
 * 
 * @author Martin Žilinský
 * @version 28112017
 */
public class MenuLista extends MenuBar{
    
    private IHra hra;
    private Main main;
    
    /**
    *  Konstruktor třídy 
    *  @param hra 
    *  @param main
    */
    public MenuLista(IHra hra, Main main){
        this.hra = hra;
        this.main = main;
        init();
    }
    
    /**
    *  Nástavení nabídky menu lišty 
    */
    private void init(){
    
        Menu novySoubor = new Menu("Adventura");
    
        MenuItem napoveda = new Menu("Nápověda");
        napoveda.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
    
        MenuItem novaHra = new MenuItem("Nova hra");
        novaHra.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
    
        MenuItem konecHry = new MenuItem("Konec hry");
    
        //, new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/ikona.png")))
    
        novySoubor.getItems().addAll(novaHra, konecHry, napoveda);  
        this.getMenus().addAll(novySoubor);
    
        novaHra.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                hra = new Hra();
                main.setHra(hra);
                main.getMapa().novaHra(hra);
                main.getCentralText().setText(hra.vratUvitani());
                main.getSeznamVychodu().novaHra(hra);
                main.getVeciKapsa().novaHra(hra);
                main.getVeciVProstoru().novaHra(hra);
            }
        });
        
    

        konecHry.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
    
    
    
        napoveda.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                stage.setTitle("Napoveda");
                WebView webview = new WebView();
                
                webview.getEngine().load(Main.class.getResource("/zdroje/napoveda.html").toExternalForm());
                
                stage.setScene(new Scene(webview, 500, 700));
                stage.show();
            }
        });    
   }
}
