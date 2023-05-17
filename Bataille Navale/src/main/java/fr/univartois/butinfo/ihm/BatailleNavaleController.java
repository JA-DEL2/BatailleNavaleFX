package fr.univartois.butinfo.ihm;

import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import java.util.Arrays;

public class BatailleNavaleController {
	
	private GrilleJeu grilleJoueur = new GrilleJeu(10,10);
	private GrilleJeu grilleAdverse = new GrilleJeu(10,10);
	private int dePlace,crPlace,toPlace;
	private int coulPaJ, coulCrJ, coulDeJ, coulToJ;
	private int coulPaA, coulCrA, coulDeA, coulToA;
	private Bateau currentBoat;
	private Button currentButton;  
	private boolean signal = false;
	
	enum Orientation {VERTICAL,HORIZONTAL};
	private Orientation orientation = Orientation.HORIZONTAL;
	private static final Random rand = new Random();
	private int batPlace;
	
	@FXML
	private Button[][] playerButtons = new Button[10][10];
	private Button[][] adversaryButtons = new Button[10][10];
	
    @FXML
    private Label AdvCr;

    @FXML
    private	Label AdvPa;

    @FXML
    private	Label AdvDe;

    @FXML
    private Label AdvTo;

    @FXML
    private Label JouCr;

    @FXML
    private Label JouPa;

    @FXML
    private Label JouDe;
   
    @FXML
    private Label JouTo;

    @FXML
    private GridPane grilleAdv;

    @FXML
    private GridPane grilleYou;

    @FXML
    private Label welcomeText;
    
    @FXML
    private Label advertLabel;
    
    @FXML
    private Label logBotLabel;
    
    @FXML
    private Button newPartButton; 
    
    @FXML
    private Button vertButton;
    
    @FXML
    private Button horiButton;

    @FXML
    void newGame(ActionEvent event) {
    	if (signal) {
    		grilleAdverse.delBateaux();
    		grilleJoueur.delBateaux();
    		vertButton.setDisable(false);
			horiButton.setDisable(false);
			newPartButton.setDisable(true);
			for (int i=0; i<10; i++) {
				for (int j=0; j<10; j++) {
					playerButtons[i][j].setDisable(false);
					playerButtons[i][j].setText(null);
					adversaryButtons[i][j].setDisable(true);
					adversaryButtons[i][j].setText(null);
				}
			}
			logBotLabel.setText(null);
			advertLabel.setText(null);
    	}
    	else {
			vertButton.setDisable(true);
			horiButton.setDisable(true);
			newPartButton.setDisable(true);
			for (int i=0; i<10; i++) {
				for (int j=0; j<10; j++) {
					playerButtons[i][j].setDisable(true);
					adversaryButtons[i][j].setDisable(false);
				}
			}
			placeAlea();
    	}
    }
    
    @FXML
    void horizontal(ActionEvent event) {
    	orientation = Orientation.HORIZONTAL;
    }
    
    @FXML
    void vertical(ActionEvent event) {
    	orientation = Orientation.VERTICAL;
    }

    @FXML
    void placeT(ActionEvent event) {
    	currentBoat = new Bateau(4);
    	currentButton = (Button)event.getSource();
    }

    @FXML
    void placePA(ActionEvent event) {
    	currentBoat = new Bateau(5);
    	currentButton = (Button)event.getSource();
    }


    @FXML
    void placeC(ActionEvent event) {
    	currentBoat = new Bateau(3);
    	currentButton = (Button)event.getSource();

    }

    @FXML
    void placeDe(ActionEvent event) {
    	currentBoat = new Bateau(2);
    	currentButton = (Button)event.getSource();
    }
    
    public void placeAlea () {
    	int[][] pos = new int[10][2];
    	for (int i = 0; i<10; i++) {
    		pos[i][0] = rand.nextInt(10);
    		pos[i][1] = rand.nextInt(10);
    	}
    	int[] tailles = {5,4,4,3,3,3,2,2,2,2};
    	boolean dir;
    	for (int j = 0; j<10; j++) {
    		Bateau boat = new Bateau(tailles[j]);
    		dir = rand.nextBoolean();
    		while (! grilleAdverse.placerBateauAlea(boat, pos[j], dir)) {
    			pos[j][0] = rand.nextInt(1,11)-1;
        		pos[j][1] = rand.nextInt(1,11)-1;
    		}
    	}
    	grilleAdverse.createVoidCase();
    }
    
    public void tirAlea() {
    logBotLabel.setText(null);
    int[] pos = new int[2];
    	int x,y;
	    	do {
	    		x = rand.nextInt(10);
				pos[0] = x;
		    	y = rand.nextInt(10);
		    	pos[1]=y;
	    	}
	    	while (grilleJoueur.accesCase(pos).getEstTouchee());
    		
    	if(grilleJoueur.tirer(pos)) {
    		playerButtons[pos[0]][pos[1]].setText("X");
    		logBotLabel.setText("Adversaire : touché");
    	}
    	else {
	    	playerButtons[pos[0]][pos[1]].setText("|_|");
	    	logBotLabel.setText("Adversaire : raté");
    	}
    	if (grilleJoueur.accesCase(pos).verifCoule()) {
			logBotLabel.setText("Adversaire : Touche, Coule, Bravo !");
			int taille = grilleJoueur.accesCase(pos).getBateau().getTaille();
			if (taille == 2) {coulDeJ ++;JouDe.setText(Integer.toString(Integer.parseInt(JouDe.getText())-1));}
			else if (taille == 3) {coulCrJ ++;JouCr.setText(Integer.toString(Integer.parseInt(JouCr.getText())-1));}
			else if (taille == 4) {coulToJ++;JouTo.setText(Integer.toString(Integer.parseInt(JouTo.getText())-1));}
			else {coulPaJ ++;JouPa.setText(Integer.toString(Integer.parseInt(JouPa.getText())-1));}
    	}
    	if (grilleJoueur.verifAllCoule()) {
    		logBotLabel.setText("Vous avez perdu, désolé !");
    		signal = true;
    	}
    	
    }
    
    @FXML
    void putBoat(ActionEvent event) {
        if (currentBoat != null) {
        	boolean alert = false;
        	Button btn = (Button) event.getSource();
        	int row = GridPane.getRowIndex(btn);
        	if (row >0) row--;
        	int column = GridPane.getColumnIndex(btn);
        	if (column >0) column--;
        	int[] pos = {column,row};
        	if (orientation == Orientation.VERTICAL) {
        		if(grilleJoueur.placerVertical(currentBoat,pos)) {
        			alert = true;
	        		for (int i=0; i < currentBoat.getTaille(); i++) {
	        			playerButtons[pos[1]+i][pos[0]].setText("O");
	        			playerButtons[pos[1]+i][pos[0]].setDisable(true);
		        		}
	        		}
        		else advertLabel.setText("Vous ne pouvez pas placer cela ici, réessayez !");
	    		}
        	else {
        		if(grilleJoueur.placerHorizontal(currentBoat, pos)) {
        			alert = true;
	        		for (int i=0; i < currentBoat.getTaille(); i++) {
	        			playerButtons[pos[1]][pos[0]+i].setText("O");
	        			playerButtons[pos[1]][pos[0]+i].setDisable(true);        			
	        			}
	        		}
        		else advertLabel.setText("Vous ne pouvez pas placer cela ici, réessayez !");
        		}
        	if (alert) {
	        	if (currentBoat.getTaille() == 2 && dePlace < 3) dePlace++;
	        	else if (currentBoat.getTaille() == 3 && crPlace < 2) crPlace++;
	        		else if (currentBoat.getTaille() == 4 && toPlace < 1) toPlace++;
	        			else {currentButton.setDisable(true);batPlace++;}
	        	currentBoat = null;
	        	currentButton = null;
	        	advertLabel.setText(null);
        		}
        	else putBoat(event);
        	if (batPlace == 4) {
        		newPartButton.setDisable(false);
        		grilleJoueur.createVoidCase();
        	}
        }
    }
    
    @FXML
    void shoot(ActionEvent event) {
    	advertLabel.setText(null);
    	Button btn = (Button) event.getSource();
    	int row = GridPane.getRowIndex(btn);
    	if (row > 0) row--;
    	int column = GridPane.getColumnIndex(btn);
    	if (column > 0) column--;
    	int[] pos = {row,column};
    	
    	PartieBateau partie = grilleAdverse.accesCase(pos);
    	if (partie.tirer()) {
    		btn.setText("X");
    		btn.setDisable(true);
    		advertLabel.setText("Vous : Touche !");
    		if (partie.verifCoule()) {
    			advertLabel.setText("Vous : Touche, Coule, Bravo !");
    			int taille = partie.getBateau().getTaille();
    			if (taille == 2) {coulDeA ++;AdvDe.setText(Integer.toString(Integer.parseInt(AdvDe.getText())-1));}
    			else if (taille == 3) {coulCrA ++;AdvCr.setText(Integer.toString(Integer.parseInt(AdvCr.getText())-1));}
    			else if (taille == 4) {coulToA++;AdvTo.setText(Integer.toString(Integer.parseInt(AdvTo.getText())-1));}
    			else {coulPaA ++;AdvPa.setText(Integer.toString(Integer.parseInt(AdvPa.getText())-1));}
    		
    		}
    	}else {
    		advertLabel.setText("Vous : Loupe, dommage !");
    		btn.setText("|_|");
    		btn.setDisable(true);
    	}    	
    	tirAlea();
    	if (grilleAdverse.verifAllCoule()) {
    		advertLabel.setText("Vous avez gagné, bravo !");
    		signal = true;
    		newPartButton.setDisable(false);
    	}
    }
    

    @FXML
    void initialize() {
        for (int i = 0; i<10; i++) {
        	for (int j=0; j<10; j++ ) {
	            Button btn = new Button();
	            btn.setPrefWidth(100);
	            btn.setPrefHeight(100);
	            grilleYou.add(btn, j+1, i+1);
	            playerButtons[i][j] = btn;
	            playerButtons[i][j].setOnAction(this::putBoat);
	            
	            Button btna = new Button();
	            btna.setPrefWidth(100);
	            btna.setPrefHeight(100);
	            btna.setDisable(true);
	            grilleAdv.add(btna, j+1, i+1);
	            adversaryButtons[i][j] = btna;
	            adversaryButtons[i][j].setOnAction(this::shoot);
            	}
        }
    }
}