/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import logika.IHra;
import logika.Prostor;

/**
 * Třída SeznamVychodu vytváří seznam názvů sousedních místností. Aktualizuje se
 * při nové hře nebo přejití do jiného prostoru.
 *
 * @author Martin Žilinský
 * @version ZS 2017
 */
public class SeznamVychodu extends ListView implements Observer {

    public IHra hra;
    public TextArea centralText;
    ObservableList<FlowPane> dataVychodu;

    /**
    *  Konstruktor třídy
    *  
    *  @param hra 
    *  @param centralText
    */ 
    public SeznamVychodu(IHra hra, TextArea centralText) {
        this.hra = hra;
        this.centralText = centralText;
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
        hra.getHerniPlan().removeObserver(this);
        this.hra = hra;
        hra.getHerniPlan().registerObserver(this);
        update();
    }

    /**
    *  Update seznamu východů z aktuálního prostoru
    *  
    */
    @Override
    public void update() {
        dataVychodu.clear();

        for (Prostor prostor : hra.getHerniPlan().getAktualniProstor().getVychody()) {
            FlowPane polozka = new FlowPane();
                
            
                String text = prostor.getNazev();
                Button button = new Button(text);
                button.setPrefWidth(300);
                button.setOnAction((ActionEvent event) -> {
                    String prikaz = "jdi " + prostor.getNazev();
                    centralText.appendText("\n" + prikaz);
                    String odpoved = hra.zpracujPrikaz(prikaz);
                    centralText.appendText("\n\n" + odpoved + "\n");
                });
                polozka.getChildren().add(button);
                                  
                              
                dataVychodu.add(polozka);              
            }
    }
    /**
     *  Úvodní nastavení seznamu východů z prostoru
     */
    private void init() {

        dataVychodu = FXCollections.observableArrayList();
        this.setItems(dataVychodu);
        this.setPrefWidth(200);
        this.setPrefHeight(200);
    }
}