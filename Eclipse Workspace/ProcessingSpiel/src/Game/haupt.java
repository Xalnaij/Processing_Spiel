package Game;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import controlP5.*;	
import ddf.minim.*;
import ddf.minim.Controller;

public class haupt extends PApplet{
// Initialisierung //////////////
	//ControlP5 cp5 = new ControlP5(this);
	public int animationchanger=0;
	public int animationchangerboss1=0;
	public int rightmov = 12;
	public int downmov= 0;
	public int upmov=18;
	public int leftmov=6;
	public int bewegungseitlich = 250;
	public int bewegunghorizontal = 250;
	public int startTime;
	int entercutscene=0;
	int fadeIn=0;
	int schrittZaehler = 0;
	float gainVoulumeMusik = (float) -15.0;
	float gainVoulumeSound = (float) -10.0;
	///////////
	public String textAusgabe ="Hallo Welt";
	///////////
	public PFont font;
	public PImage[] bossimg = new PImage[50];
	public PImage[] pimg = new PImage[50];
	public PImage[] backgroundimg = new PImage[30];
	public PImage[] backgroundcollision = new PImage[30];
	PImage test;
	///////////
	AudioPlayer luca1;
	AudioPlayer luca2;
	AudioPlayer luca3;
	AudioPlayer luca4;
	AudioPlayer luca5;
	AudioPlayer luca6;
	AudioPlayer luca7;
	
	AudioPlayer soren1;
	AudioPlayer soren2;
	AudioPlayer soren3;
	AudioPlayer soren4;
	AudioPlayer soren5;
	AudioPlayer soren6;
	AudioPlayer soren7;
	///////////
	public AudioPlayer song;
	public AudioPlayer levelChangeSound;
	public AudioPlayer bossMusik;
	public gameButtons gbuttons = new gameButtons();
	public hitCollisionUmgebung coll = new hitCollisionUmgebung();
	public ImageLoader imgloader = new ImageLoader();
	public BackagroundLoader backloader;
	public hitCollisionUmgebung collisionUmgebung;
	Minim minim;
	CharMovment charmov = new CharMovment();
	TextBoxTalk tbox = new TextBoxTalk();
	///////////
	boolean erstecutscene = true;
	boolean enterswitch = false;
	boolean laufstop = false;
	boolean animationstop = false;
	boolean switch1 = false;
	boolean animationMovment = false;
	boolean animationMovmentStop = false;
	boolean cutsceneboss1 = false;
///////////////////////////
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("Game.haupt"); // Proccesing main wird aufgerufen
	}


	public void setup(){
		frameRate(15);		 // Framerate wird auf 20 gehalten
		gbuttons.buttonsetup(this);
	}
	
	
	
	public void settings(){
		
		size(800,800); // Unser spiel wird in einem Fenster mit 800x800 geöffnet
		System.out.println(sketchPath()); // Der SketchPath wird ausgegeben, ist unser arbeitspfad für Dateien.
		imgloader.LoadImageCharMov(pimg); // Der Hauptcharackter wird geladen.
		imgloader.LoadImageBoss(bossimg);
		backloader = new BackagroundLoader(backgroundimg); // Alle Hintergründe werden geladen
		collisionUmgebung = new hitCollisionUmgebung();
		collisionUmgebung.backagroundcollision(backgroundcollision);
		//System.out.println(pimg[5]); 
		startTime = millis(); // Millis wird gestartet
		minim = new Minim(this); // Neues minim Objekt erstellen
		//******** Lucas Eingesprochenes *****
		luca1 = minim.loadFile("../Luca/Teil0.mp3");
		luca2 = minim.loadFile("../Luca/Teil1.mp3");
		luca3 = minim.loadFile("../Luca/Teil2.mp3");
		luca4 = minim.loadFile("../Luca/Teil3.mp3");
		luca5 = minim.loadFile("../Luca/Teil4.mp3");
		luca6 = minim.loadFile("../Luca/Teil5.mp3");
		luca7 = minim.loadFile("../Luca/Teil6.mp3");
		
		luca1.setGain(10.0f);
		luca2.setGain(10.0f);
		luca3.setGain(10.0f);
		luca4.setGain(10.0f);
		luca5.setGain(10.0f);
		luca6.setGain(10.0f);
		luca7.setGain(10.0f);
		// ****** ENDE LUCA *****
		// ****** S�ren Eingesprochenes *****
		soren1 = minim.loadFile("../Soren/Teil1.mp3");
		soren2 = minim.loadFile("../Soren/Teil2.mp3");
		soren3 = minim.loadFile("../Soren/Teil3.mp3");
		soren4 = minim.loadFile("../Soren/Teil4.mp3");
		soren5 = minim.loadFile("../Soren/Teil5.mp3");
		soren6 = minim.loadFile("../Soren/Falsch.mp3");
		soren7 = minim.loadFile("../Soren/Richtig.mp3");
		
		soren1.setGain(10.0f);
		soren2.setGain(10.0f);
		soren3.setGain(10.0f);
		soren4.setGain(10.0f);
		soren5.setGain(10.0f);
		soren6.setGain(10.0f);
		soren7.setGain(10.0f);
		// ****** ENDE S�ren *****
		levelChangeSound = minim.loadFile("/Sound/Change.wav"); // Laden der Sound Datei !
		song = minim.loadFile("/Sound/Chellos.wav"); // die Sound Datei wird von minim Geladen und an dem song objekt weiter gegeben
		bossMusik = minim.loadFile("/Sound/guitar.mp3");
		song.play(); // Der sound wird abgespielt
		song.loop(); // Der sound wird Unendlich lange Wiederholt
		song.setGain(gainVoulumeMusik); // Der Sound wird um -15 DB gesunken
		levelChangeSound.setGain(gainVoulumeSound); // Der Sound wird um -10 DB Gesunken
		bossMusik.setGain(gainVoulumeSound); // Bossmusik wird um -10 DB Gesunken
		bossMusik.play();
		bossMusik.loop();
		bossMusik.mute(); 
		//song.mute(); // Musik Muten
		//song.unmute(); // Song wieder Unmuten
	}
	
	
	public void draw(){
		println(entercutscene);
		
		
		if(switch1==true){ // FadeIn Effekt Berechnung f�r den Levelwechsel
			fadeIn = fadeIn +10; // Wert wird mit +10 Addiert!
			tint(255,fadeIn);
			if(fadeIn>255){ // Wenn der FadeIn �ber 255 ist wird der Switch bet�tigt und die Variable wieder auf 0 gesetzt
				switch1=false;
				tint(255,fadeIn);
				fadeIn=0;
			}
		}	
		//println(bewegunghorizontal);
		//System.out.println(frameRate);
		image(backgroundcollision[backloader.backgroundid],0,0);
		 background(0,0,0); // Weiterer Hintergrund der f�r den FadeIn Effekt genutzt wird
		 
		 
		 if(backloader.backgroundid == 5){
			 image(bossimg[animationchangerboss1],0,0);
		 }
		 image(backgroundimg[backloader.backgroundid],0,0); // Hintergrund der geladen wird

		
		 tbox.Textbox(textAusgabe,this,font);
		 image(pimg[animationchanger] ,bewegungseitlich, bewegunghorizontal);// Dieses PImage ist der Hauptcharakter
		 

		 
		if(bewegungseitlich >= 700 && backloader.backgroundid != 4){ // Wenn char auf der x Achse 570 erreicht:
			tint(255,0);	// Die Transperenz wird auf 0 gesetzt
			switch1 = true;	// Der Switch f�r den FadeIn Effekt wird umgelegt
			levelChangeSound.play(); // Beim Wechseln des Levels wird ein Sound Abgespielt
			backloader.backgroundchangerright();// Wird der Hinterdsssssssawdasdgrund gewechselt nach rechts
			bewegungseitlich = 50;
			levelChangeSound.rewind(); // Wichtig damit der Sound wieder am Anfang ist sonst BUG !
			backloader.testswitch = false;
			if(backloader.backgroundid == 4){
				bewegunghorizontal = 380;
			}
		}
		if(bewegungseitlich <= 20){ // Wenn der Char auf der X Achse auf 20 ist:
			tint(255,0);	
			switch1 = true;
			levelChangeSound.play();
			backloader.backgroundchangerleft(); // wird der hintergrund nach links geändert
			levelChangeSound.rewind(); // Wichtig damit der Sound wieder am Anfang ist sonst BUG !
			bewegungseitlich = 650;
			if(backloader.backgroundid == 3){
				bewegunghorizontal = 250;
			}
		}
		if(backloader.backgroundid == 1 && bewegunghorizontal <= 70){ // Levelcahnge nach oben!
			tint(255,0);	
			switch1 = true;
			levelChangeSound.play();
			backloader.backgroundchangerup();
			levelChangeSound.rewind();
			bewegunghorizontal = 270;
		}
		
		if(backloader.backgroundid == 5 && bewegunghorizontal >= 322){
			tint(255,0);	
			switch1 = true;
			levelChangeSound.play();
			backloader.backgroundchangerudown();
			levelChangeSound.rewind();
			bewegunghorizontal = 90;
			bewegungseitlich = 380;
		}
		
		///// ***** Idle Animationen ******
		if(backloader.backgroundid == 5){
		if(millis() > startTime + 200){
		startTime = millis();
			if (animationchangerboss1 == 0){ // einfacher Switch f�r die Idle Animation
				animationchangerboss1 = 1;
				animationchanger = 1;
			}else{
				animationchangerboss1 = 0;
				animationchanger = 0;
			}
		}
		}
		
		
		if(keyPressed == false && backloader.backgroundid != 5){ // Hier befindet sich die Idle Animation			
			if(millis() > startTime + 200){
			startTime = millis();
				if (animationchanger == 0){ // einfacher Switch f�r die Idle Animation
    				animationchanger = 1;
    			}else{
    				animationchanger = 0;
    			}
			}
		}

		
		///// ***** Idle Animationen Ende *****
		//System.out.println(backloader.backgroundid);
		
		// ***** Musik Steuerung **** 
		
		if(backloader.backgroundid == 5 ){
			song.mute();
			bossMusik.unmute();
		}else{
			song.unmute();
			bossMusik.mute();
		}
		
		
		
		// ****** Musik Steuerung Ende ******
		//************************ Hier kommt der Content vom Spiel / Events ****************************
		
		
		
		//
		cutscene(); // Die Anfangs Cutscene wird geladen von der Funktion Cutscene()
		if(backloader.backgroundid == 5){
			cutsceneboss1();
		}
		
		
	}
	
public void controlEvent(ControlEvent theEvent){
	System.out.println(theEvent.getController().getName()); // Event Controller f�r die Button Abfrage
}

public void Fallout(int theValue){ // Button Name Antwort wird hier Aufgerufen und kann manipuliert werden
	System.out.println("test");
	textAusgabe ="Button Pressed";
	animationMovmentStop = false;
	//gbuttons.buttonForAll.hide();
	
}
public void WildLands(int theVaule){ // Button mit dem namen test wird Aufgerufen und kann hier manipuliert werden
	textAusgabe = "Button Test pressed";
	animationMovmentStop = false;
}
	
	public void keyPressed(){ // Alle funktionen für Key presses
		//test platzhalter = new test(this);
		if((key == 'd' || key == 'D' || keyCode == RIGHT)){ // Bei tastentdruck d Passiert:
			//System.out.println("w");
			 pixelcollisionright(bewegungseitlich,bewegunghorizontal);
			 if(animationMovmentStop == false){	
			 if(laufstop == false){
			rightmov = charmov.animationright(rightmov);
			animationchanger = rightmov;// Ruft funktion aus imageLoader aus für animation
				bewegungseitlich = charmov.charbewegungrechts(bewegungseitlich); // der char bewegt sich in folgende richtung	
			 		}	
			 	}
			}
				
		if((key == 'a' || key == 'A' || keyCode == LEFT)){ // Bei Tastendruck a Passiert :
			


			if(backloader.backgroundid == 0){// Wenn der Backgroundid 0 ist also erstes bild:
				

					pixelcollisionleft(bewegungseitlich,bewegunghorizontal);// und der bool wert true ist
					if(animationMovmentStop == false){
					if(laufstop == false){
					leftmov = charmov.animationleft(leftmov);
					animationchanger = leftmov;
					bewegungseitlich = charmov.charbewegunglinks(bewegungseitlich); // Charackter bewegung in die richtung
						}
					}
				
			}else{
				pixelcollisionleft(bewegungseitlich,bewegunghorizontal);// Bei anderen backgrounds darf char sich bewegen
				if(animationMovmentStop == false){
				if(laufstop == false){
				leftmov = charmov.animationleft(leftmov);
				animationchanger = leftmov;
				bewegungseitlich = charmov.charbewegunglinks(bewegungseitlich);	
					}
				}
			}		
		}
		if((key == ENTER || key == RETURN)){ // Bei Tastendruck Enter / Return :
			//textAusgabe = "Enter Wurde gedr�ckt";
			
			if(animationMovmentStop == true){
					entercutscene = entercutscene +1;	
			}
		}
		if((key == 'w' || key == 'W' || keyCode == UP)){ // Bei tastendruck w Passiert:
			//textAusgabe = "das ist die taste w";
			pixelcollisionup(bewegungseitlich,bewegunghorizontal);
			if(animationMovmentStop == false){
			if(laufstop == false){
			upmov = charmov.animationup(upmov);
			animationchanger = upmov;
			bewegunghorizontal = charmov.charbewegunghoch(bewegunghorizontal);// Char bewegt sich in die richtung
				}
			}
		}
		if((key == 's' || key == 'S' || keyCode == DOWN)){ // Bei tastendtuck s Passiert:
			//textAusgabe = "das ist die taste s";

				pixelcollisiondown(bewegungseitlich,bewegunghorizontal);
				if(animationMovmentStop == false){
				if(laufstop == false){
				downmov = charmov.animationdown(downmov);
				animationchanger = downmov;
				bewegunghorizontal = charmov.charbewegungrunter(bewegunghorizontal); // Char bewegt sich in die richtung
				}
			}
		}
	}
	
	public void cutsceneboss1(){
		if(cutsceneboss1 == false){
			animationMovmentStop = true;
			if(entercutscene == 0){
				textAusgabe = "Luca; Was suchst du hier im DreamRealm?";
				luca1.play();
			}
			if(entercutscene == 1){
				textAusgabe = "Held: Das w�rde ich selber gerne wissen";
			}
			if(entercutscene == 2){
				textAusgabe = "Luca: Da du nun hier bist, L�se meine Aufgabe";
				luca2.play();
				
				cutsceneboss1= true;
				gbuttons.buttonForAll.show(); // Buttons anzeigen test
			}
		}
		
	}
	
	public void cutscene(){ // Erste Cutscene
		if(erstecutscene == true){
			animationMovmentStop = true;
			if(entercutscene == 0){
			textAusgabe = "Held: Oh man ... es ist schon recht Sp�t.";
			}
			if(entercutscene == 1){
				textAusgabe = "Held: Ich sollte Vielleicht mal ins Bett.";
					if(schrittZaehler != 10){
				animationMovment = true;
				//System.out.println(schrittZaehler);
				downmov = charmov.animationdown(downmov);
				animationchanger = downmov;
				bewegunghorizontal = charmov.charbewegungrunter(bewegunghorizontal);
				schrittZaehler++;
					}
					if(schrittZaehler == 10){
						animationMovment = false;
					}
				
			}
				if(entercutscene >= 2){
					schrittZaehler=0;
					textAusgabe = "Held: Dann mal los.";
					erstecutscene = false;
					animationMovmentStop = false;
					entercutscene = 0;
					}
		}
		//System.out.println(entercutscene);
	}
	
	
	
	public void pixelcollisionright(int x, int y){
		
		loadPixels();
		backgroundcollision[backloader.backgroundid].loadPixels();
		int pixelcordiante=0;
		pixelcordiante = 26+x+(y+46)*800;
		
		//println(red(backgroundcollision[backloader.backgroundid].pixels[pixelcordiante]));
		//System.out.println(pixelcordiante);
		
		
		for(int i=1;i <= 4; i++){
		if(red(backgroundcollision[backloader.backgroundid].pixels[pixelcordiante]) == 0.0){
			laufstop=true;
			i++;
			pixelcordiante++;
		}
		if(red(backgroundcollision[backloader.backgroundid].pixels[pixelcordiante]) >= 200.0){
			laufstop=false;
			i++;
			pixelcordiante++;
		}
		}
		
	}
	
	public void pixelcollisionleft(int x, int y){
		loadPixels();
		backgroundcollision[backloader.backgroundid].loadPixels();
		int pixelcordiante=0;
		pixelcordiante = x+(y+46)*800;
		
		//println(red(backgroundcollision[backloader.backgroundid].pixels[pixelcordiante]));
		//System.out.println(pixelcordiante);
		
		
		for(int i=1;i <= 4; i++){
		if(red(backgroundcollision[backloader.backgroundid].pixels[pixelcordiante]) == 0.0){
			laufstop=true;
			i++;
			pixelcordiante--;
		}
		if(red(backgroundcollision[backloader.backgroundid].pixels[pixelcordiante]) >= 200.0){
			laufstop=false;
			i++;
			pixelcordiante--;
		}
		}
		
	}
	
	public void pixelcollisionup(int x, int y){
		loadPixels();
		backgroundcollision[backloader.backgroundid].loadPixels();
		int pixelcordiante=0;
		pixelcordiante = x+(y+43)*800;
		//System.out.println(pixelcordiante);
		
		
		for(int i=1;i <= 2; i++){
		if(red(backgroundcollision[backloader.backgroundid].pixels[pixelcordiante]) == 0.0){
			laufstop=true;
			i++;
			pixelcordiante = pixelcordiante -800;
		}
		if(red(backgroundcollision[backloader.backgroundid].pixels[pixelcordiante]) >= 200.0){
			laufstop=false;
			i++;
			pixelcordiante = pixelcordiante -800;
		}
		}
		
	}
	
	public void pixelcollisiondown(int x, int y){
		loadPixels();
		backgroundcollision[backloader.backgroundid].loadPixels();
		int pixelcordiante=0;
		pixelcordiante = x+(y+64)*800;
		//System.out.println(pixelcordiante);
		
		
		for(int i=1;i <= 2; i++){
		if(red(backgroundcollision[backloader.backgroundid].pixels[pixelcordiante]) == 0.0){
			laufstop=true;
			i++;
			pixelcordiante = pixelcordiante +800;
		}
		if(red(backgroundcollision[backloader.backgroundid].pixels[pixelcordiante]) >= 200.0){
			laufstop=false;
			i++;
			pixelcordiante = pixelcordiante +800;
		}
		}
		
	}
	
	
	
	
	
	
	
}