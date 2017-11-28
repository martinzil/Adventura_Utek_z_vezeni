/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import GUI.Mapa;
import GUI.MenuLista;
import GUI.SeznamVychodu;
import GUI.VeciKapsa;
import GUI.VeciProstor;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logika.*;
import uiText.TextoveRozhrani;

/**
 * @author Martin Žilinský
 * @version 28112017
 */
public class Main extends Application {
    
    private TextArea centralText = new TextArea();
    private TextField zadejPrikazTextField = new TextField();
    private IHra hra;
    private Mapa mapa;
    private MenuLista menuLista;
    private Stage primaryStage;
    private SeznamVychodu seznamVychodu;
    private VeciKapsa veciKapsa;
    private VeciProstor veciProstor;
    
    /**
     * Metoda slouží ke spuštění grafického rozhraní hry
     * a nastavení základního layoutu
     */
    @Override
    public void start(Stage primaryStage) {
        
        this.primaryStage = primaryStage;
        hra = new Hra();
        mapa = new Mapa(hra);
        menuLista = new MenuLista(hra, this);
        seznamVychodu = new SeznamVychodu(hra, centralText);
        veciKapsa = new VeciKapsa(hra, centralText);
        veciProstor = new VeciProstor(hra, centralText);
        
        BorderPane borderPane = new BorderPane();
         
        // Text s prubehem hry    
        centralText.setText(hra.vratUvitani());
        centralText.setEditable(false);
        
        //label s textem zadej prikaz
        Label zadejPrikazLabel = new Label("Zadej prikaz: ");
        zadejPrikazLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        // text area do ktere piseme prikazy
        zadejPrikazTextField.setOnAction(new EventHandler<ActionEvent>() {
                    
            @Override
            public void handle(ActionEvent event) {
                               
                String vstupniPrikaz = zadejPrikazTextField.getText();
                String odpovedHry = hra.zpracujPrikaz(vstupniPrikaz);
            
                centralText.appendText("\n" + vstupniPrikaz + "\n");
                centralText.appendText("\n" + odpovedHry + "\n");
            
                zadejPrikazTextField.setText("");
            
                if(hra.konecHry()){
                    zadejPrikazTextField.setEditable(false);
                    centralText.appendText(hra.vratEpilog());
                }
            }            
        });
        
        //dolni lista s elementy
        FlowPane dolniLista = new FlowPane();
        dolniLista.setAlignment(Pos.CENTER);
        dolniLista.getChildren().addAll(zadejPrikazLabel,zadejPrikazTextField);
        
        borderPane.setCenter(centralText);
        borderPane.setBottom(dolniLista);
        borderPane.setTop(menuLista);
        borderPane.setLeft(levyPanel());
        borderPane.setRight(pravyPanel());
        
        Scene scene = new Scene(borderPane, 1350, 800);
        primaryStage.setTitle("Útěk z vězení");
        
        primaryStage.setScene(scene);
        primaryStage.show();
        zadejPrikazTextField.requestFocus();
    }   
    
    /**
     * Metoda slouží k nastavení levého panelu hry
     */
    private BorderPane levyPanel(){
             
        BorderPane vychody = new BorderPane();
        Label textSeznamVychodu = new Label("\nSeznam východů:");
        textSeznamVychodu.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vychody.setTop(textSeznamVychodu);
        vychody.setCenter(seznamVychodu);
       
        BorderPane levyPanel = new BorderPane();
        levyPanel.setTop(mapa);
        levyPanel.setCenter(vychody);
        
        return levyPanel;
    }
    
    /**
     * Metoda slouží k nastavení pravého panelu hry
     */
    private BorderPane pravyPanel(){
        
        BorderPane veci = new BorderPane();
        veci.setPrefWidth(300);
        
            veci.setCenter(veciProstor);
             
            BorderPane veciVKapse = new BorderPane();
            Label textVeciVKapse = new Label("\nVěci v kapse:");
            textVeciVKapse.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            veciVKapse.setTop(textVeciVKapse);
            veciVKapse.setCenter(veciKapsa);
            
        veci.setCenter(veciVKapse);
        
        BorderPane veciVProstoru = new BorderPane();
        Label textVeciVProstoru = new Label("\nVěci v prostoru:");
        textVeciVProstoru.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        veciVProstoru.setTop(textVeciVProstoru);
        veciVProstoru.setCenter(veciProstor);
        
        BorderPane pravyPanel = new BorderPane();
        pravyPanel.setTop(veci);
        pravyPanel.setCenter(veciVProstoru);
        return pravyPanel;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            launch(args);
        }
        else{
            if (args[0].equals("-txt")) {
                IHra hra = new Hra();
                TextoveRozhrani textHra = new TextoveRozhrani(hra);
                textHra.hraj();
            }
            else{
                System.out.println("Neplatny parametr");
                System.exit(1);
            }
        }
    } 
    
     /**
     * Vrací odkaz na objekt se seznamem východů
     * @return seznam východů
     */
    public SeznamVychodu getSeznamVychodu() {
        return seznamVychodu;
    }
    
    /**
     * Vrací odkaz na objekt se seznamem věcí v kapse
     * @return věcí v batohu
     */
    public VeciKapsa getVeciKapsa() {
        return veciKapsa;
    }
    
    /**
     * Vrací odkaz na objekt se seznamem věcí v prostoru
     * @return věci v prostoru
     */
    public VeciProstor getVeciVProstoru() {
        return veciProstor;
    }
    
    /**
     * Vrací odkaz na pole s textem o průběhu hry
     * @return pole s průběhem hry
     */
    public TextArea getCentralText() {
        return centralText;
    }
        
     /**
     * Metoda nastavuje hru
     * @param hra
     */
    public void setHra(IHra hra) {
        this.hra = hra;
    }  
     
    /**
     * Vrací odkaz na mapu prostorů
     * @return mapa
     */
    public Mapa getMapa() {
        return mapa;
    }
         
    /**
     * Vrací odkaz na primární stage
     * @return primaryStage
     */
    public Stage getStage() {
        return primaryStage;
    }
    
    
}