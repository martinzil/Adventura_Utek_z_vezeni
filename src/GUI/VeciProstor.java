/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import utils.Observer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import logika.IHra;
import main.Main;

/**
 * Třída VeciVProstoru vytváří seznam obrázků vecí, které jsou v inventáři.
 * Aktualizuje se při nové hře, přejití do jiného prostoru a sebrání/položení
 * věci.
 *
 * @author Michal Šráček
 * @version ZS 2017
 */
public class VeciProstor extends ListView implements Observer {

    public IHra hra;
    public TextArea centralText;
    ObservableList<FlowPane> seznamVeciVProstoru;

    /**
    *  Konstruktor třídy
    *  
    *  @param hra 
    *  @param centralText
    */ 
    public VeciProstor(IHra hra, TextArea centralText) {
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
     * Update seznamu věcí v aktuálním prostoru
     */
    @Override
    public void update() {
        seznamVeciVProstoru.clear();
       
        for (String nazevVeci : hra.getHerniPlan().getAktualniProstor().getVeci()) {
            
            FlowPane polozka = new FlowPane();

            String nazev = "/zdroje/" + nazevVeci + ".jpg";
            
            ImageView imageView = new ImageView(new Image(Main.class.getResourceAsStream(nazev), 50, 50, false, false));
            

            imageView.setOnMouseClicked(event -> {
                
                String prikaz = "vezmi " + nazevVeci;
                centralText.appendText(prikaz);
                String odpoved = hra.zpracujPrikaz(prikaz);
                centralText.appendText("\n\n" + odpoved + "\n");
            });

            polozka.getChildren().add(imageView);
            

            seznamVeciVProstoru.add(polozka);
            
        }
    }

    /**
     * Úvodní nastavení seznamu věcí v prostoru
     */
    private void init() {
        seznamVeciVProstoru = FXCollections.observableArrayList();
        this.setItems(seznamVeciVProstoru);
        this.setPrefWidth(400);
        this.setPrefHeight(300);
    }
}
