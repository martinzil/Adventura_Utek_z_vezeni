/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import utils.Observer;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import logika.IHra;
import main.Main;

/**
 * Třída SeznamVychodu vytváří seznam názvů sousedních místností. Aktualizuje se
 * při nové hře nebo přejití do jiného prostoru.
 *
 * @author Martin Žilinský
 * @version ZS 2017
 */
public class VeciKapsa extends ListView implements Observer {

    public IHra hra;
    public TextArea centralText;
    ObservableList<FlowPane> seznamVeciVKapse;

    /**
    *  Konstruktor třídy
    *  
    *  @param hra 
    *  @param centralText
    */ 
    public VeciKapsa(IHra hra, TextArea centralText) {
        this.hra = hra;
        this.centralText = centralText;
        hra.getKapsa().registerObserver(this);
        hra.getHerniPlan().registerObserver(this);
        init();
        update();
    }

    /**
     * Restartování adventury
     *
     * @param hra Nová hra
     */
    public void novaHra(IHra hra) {
        hra.getKapsa().removeObserver(this);
        hra.getHerniPlan().removeObserver(this);
        this.hra = hra;
        hra.getKapsa().registerObserver(this);
        hra.getHerniPlan().registerObserver(this);
        update();
    }

    /**
    *  Update seznamu východů z aktuálního prostoru
    *  
    */
    @Override
    public void update() {
        seznamVeciVKapse.clear();
        for (String vec : hra.getKapsa().getVeciKapsa()) {
            
            FlowPane polozka = new FlowPane();
            
            String nazev = "/zdroje/" + vec + ".jpg";
            
            ImageView imageView = new ImageView(new Image(Main.class.getResourceAsStream(nazev), 50, 50, false, false));
            

            imageView.setOnMouseClicked(event -> {
                
                String prikaz = "poloz " + vec;
                centralText.appendText(prikaz);
                String odpoved = hra.zpracujPrikaz(prikaz);
                centralText.appendText("\n\n" + odpoved + "\n");
            });
            
            polozka.getChildren().add(imageView);
            
            seznamVeciVKapse.add(polozka);
            }
    }
    /**
     *  Úvodní nastavení seznamu východů z prostoru
     */
    private void init() {
        
        seznamVeciVKapse = FXCollections.observableArrayList();
        this.setItems(seznamVeciVKapse);
        this.setPrefWidth(400);
        this.setPrefHeight(300);
    }

}
