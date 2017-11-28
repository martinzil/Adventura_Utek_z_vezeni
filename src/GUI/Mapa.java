/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import logika.IHra;
import main.Main;
import utils.Observer;

/**
 * Třída Mapa vytváří vizuální zobrazení mapy adventury.
 * Aktualizuje se při spuštění nové hry a při zviditelnění místnosti tunel.
 * 
 * @author Martin Žilinský
 * @version 28112017
 */
public class Mapa extends AnchorPane implements Observer {
    
    private IHra hra;
    private Circle tecka;
    private ImageView obrazekImageView;
    
    /**
    *  Konstruktor třídy
    *  @param hra 
    */
    public Mapa(IHra hra){
        this.hra = hra;
        hra.getHerniPlan().registerObserver(this);
        init();
    }
    
    /**
    *  Úvodní nastavení mapy hry
    */ 
    private void init(){
              
        obrazekImageView = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/mapa.png"),468,459,false,true));
        
        tecka = new Circle(10, Paint.valueOf("black"));
        
        // this.setTopAnchor(tecka, 0.0);
        // this.setLeftAnchor(tecka, 0.0);
            
        this.getChildren().addAll(obrazekImageView, tecka);
        update();
    }
    
    /**
     * Restartování adventury
     * @param hra Nová hra
     */
    public void novaHra (IHra novaHra){
        hra.getHerniPlan().removeObserver(this);
        hra = novaHra;
        hra.getHerniPlan().registerObserver(this);
        update();
    }
    
    /**
    *  Update mapy hry a pohybu po mapě
    */
    @Override
    public void update(){
        
        if(hra.getHerniPlan().vyberProstor("tunel").isVidet()){
            
            this.getChildren().clear();
            
            obrazekImageView = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/mapa2.png"),468,459,false,true));
            this.getChildren().addAll(obrazekImageView, tecka);
        }
        
        this.setTopAnchor(tecka, hra.getHerniPlan().getAktualniProstor().getPosTop());
        this.setLeftAnchor(tecka, hra.getHerniPlan().getAktualniProstor().getPosLeft());
    }
}
