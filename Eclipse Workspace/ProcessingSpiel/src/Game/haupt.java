package Game;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import controlP5.*;	
import ddf.minim.*;

public class haupt extends PApplet{
// Initialisierung //////////////
	public int firstCutsceneFadeOut = 255;
	public int bossKillCount =0;
	public int animationchanger=0;
	public int animationchangerboss1=0;
	public int rightmov = 12;
	public int downmov= 0;
	public int upmov=18;
	public int leftmov=6;
	public int bewegungseitlich = 480;
	public int bewegunghorizontal = 200;
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
	//////////
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
	boolean switch2 = false;
	boolean animationMovment = false;
	boolean animationMovmentStop = false;
	boolean cutsceneboss1 = false;
	boolean cutsceneboss2 = false;
	boolean cutsceneboss3 = false;
	boolean cutsceneboss4 = false;
	boolean switch3 = false;
	boolean switch4 = true;
	boolean switch5=true;
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
	}
	public void draw(){
		//println(bewegunghorizontal);
		//println(bewegungseitlich);
		//println(bossKillCount);
		
		
		if(bossKillCount == 4){
			textAusgabe = "DU HAST DAS SPIEL BENDET ! VIELEN DANK F�RS SPIELEN";
		}

		if(switch2==true){ // FadeIn f�r die Cutscene !
			firstCutsceneFadeOut = firstCutsceneFadeOut -13;
			tint(255,firstCutsceneFadeOut);	
			if(firstCutsceneFadeOut <=0){
				switch2 = false;
			}
		}
		
		if(switch3 == false){
			if(erstecutscene == true){
				backloader.backgroundid = 6;
			}else{
				backloader.backgroundid = 0;
				switch3 = true;
			}
		}
		
		if(switch1==true){ // FadeIn Effekt Berechnung f�r den Levelwechsel
			fadeIn = fadeIn +15; // Wert wird mit +10 Addiert!
			tint(255,fadeIn);
			if(fadeIn>255){ // Wenn der FadeIn �ber 255 ist wird der Switch bet�tigt und die Variable wieder auf 0 gesetzt
				switch1=false;
				tint(255,fadeIn);
				fadeIn=0;
			}
		}	
		//System.out.println(frameRate);
		image(backgroundcollision[backloader.backgroundid],0,0);
		 background(0,0,0); // Weiterer Hintergrund der f�r den FadeIn Effekt genutzt wird
		
		 cutscene(); // Die Anfangs Cutscene wird geladen von der Funktion Cutscene()
		 
		 
		 if(backloader.backgroundid == 5 || backloader.backgroundid == 7 || backloader.backgroundid == 8){ // Boss nur laden wenn bossarena betreten wird ! 
			 image(bossimg[animationchangerboss1],0,0);
		 }
		 image(backgroundimg[backloader.backgroundid],0,0); // Hintergrund der geladen wird		
		 tbox.Textbox(textAusgabe,this);
		 if(backloader.backgroundid == 9){
			 image(bossimg[animationchangerboss1],0,0);
		 }
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
			textAusgabe ="";
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
			textAusgabe ="";
		}
		if(backloader.backgroundid == 1 && bewegunghorizontal <= 70){ // Levelcahnge nach oben!
			animationchangerboss1 =0;
			tint(255,0);	
			switch1 = true;
			levelChangeSound.play();
			backloader.backgroundchangerup();
			levelChangeSound.rewind();
			bewegunghorizontal = 270;
			textAusgabe ="";
		}
		if(backloader.backgroundid == 2 && bewegunghorizontal <= 200){ // Levelcahnge nach oben!
			animationchangerboss1 = 2;
			tint(255,0);	
			switch1 = true;
			levelChangeSound.play();
			backloader.backgroundchangerup();
			levelChangeSound.rewind();
			bewegunghorizontal = 300;
			textAusgabe ="";
		}
		if(backloader.backgroundid == 3 && bewegunghorizontal <= 165){ // Levelcahnge nach oben!
			animationchangerboss1 = 5;
			tint(255,0);	
			switch1 = true;
			levelChangeSound.play();
			backloader.backgroundchangerup();
			levelChangeSound.rewind();
			bewegunghorizontal = 370;
			bewegungseitlich = 380;
			textAusgabe ="";
		}		
			if(backloader.backgroundid == 4 && bewegunghorizontal <= 275){ // Levelcahnge nach oben!
			animationchangerboss1 = 9;
			tint(255,0);	
			switch1 = true;
			levelChangeSound.play();
			backloader.backgroundchangerup();
			levelChangeSound.rewind();
			bewegunghorizontal = 470;
			bewegungseitlich = 383;
			textAusgabe ="";
		}
		
		
		if(backloader.backgroundid == 5 && bewegunghorizontal >= 318){
			tint(255,0);	
			switch1 = true;
			levelChangeSound.play();
			backloader.backgroundchangerudown();
			levelChangeSound.rewind();
			bewegunghorizontal = 90;
			bewegungseitlich = 380;
			textAusgabe ="";
		}
		if(backloader.backgroundid == 7 && bewegunghorizontal >= 400){
			tint(255,0);	
			switch1 = true;
			levelChangeSound.play();
			backloader.backgroundchangerudown();
			levelChangeSound.rewind();
			bewegunghorizontal = 230;
			bewegungseitlich = 380;
			textAusgabe ="";
		}
		if(backloader.backgroundid == 8 && bewegunghorizontal >= 450){
			tint(255,0);	
			switch1 = true;
			levelChangeSound.play();
			backloader.backgroundchangerudown();
			levelChangeSound.rewind();
			bewegunghorizontal = 200;
			bewegungseitlich = 383;
			textAusgabe ="";
		}
		if(backloader.backgroundid == 9 && bewegunghorizontal >= 530){
			tint(255,0);	
			switch1 = true;
			levelChangeSound.play();
			backloader.backgroundchangerudown();
			levelChangeSound.rewind();
			bewegunghorizontal = 300;
			bewegungseitlich = 383;
			textAusgabe ="";
		}
		
		
		///// ***** Idle Animationen ******
		if(backloader.backgroundid == 9){
			if(millis() > startTime + 200){
				startTime = millis();
				if(animationchangerboss1 == 11){
					animationchangerboss1 = 12;
				}
				if(animationchangerboss1 == 10){
					animationchangerboss1 = 11;
				}
				if(animationchangerboss1 == 9){
					animationchangerboss1 = 10;
				}
				if(switch5==false){
				if(animationchangerboss1 == 12){
					animationchangerboss1 = 9;
					switch5=true;
				}}
				
				if(animationchangerboss1 == 12){
					switch5=false;
				}
				
				if (animationchanger == 0){ // einfacher Switch f�r die Idle Animation
						animationchanger = 1;
					}else{
						animationchanger = 0;
					}
				}
		}
		
		
		
		if(backloader.backgroundid == 8){
			if(millis() > startTime + 200){
				startTime = millis();
				if(animationchangerboss1 == 7){
					animationchangerboss1 = 8;
				}
				if(animationchangerboss1 == 6){
					animationchangerboss1 = 7;
				}
				if(animationchangerboss1 == 5){
					animationchangerboss1 = 6;
				}
				if(switch5==false){
				if(animationchangerboss1 == 8){
					animationchangerboss1 = 5;
					switch5=true;
				}}
				
				if(animationchangerboss1 == 8){
					switch5=false;
				}
				
				if (animationchanger == 0){ // einfacher Switch f�r die Idle Animation
						animationchanger = 1;
					}else{
						animationchanger = 0;
					}
				}
			
		}
		if(backloader.backgroundid == 7){
			if(millis() > startTime + 200){
				startTime = millis();
				if (animationchanger == 0){ 
    				animationchanger = 1;
    			}else{
    				animationchanger = 0;
    			}
				if(animationchangerboss1 == 3){
					animationchangerboss1 = 4;
				}
					
				if(animationchangerboss1 == 2){
					animationchangerboss1 = 3;
					
				}
				if(switch4 == true){
				if(animationchangerboss1 == 4){
						animationchangerboss1 = 2;
						switch4 = false;
						}
					}
				if(animationchangerboss1 == 4){
					switch4 = true;
				}
				}
		}
		
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
		
		// ***** Musik Steuerung **** 
		if(backloader.backgroundid == 5 || backloader.backgroundid == 7 || backloader.backgroundid == 8 || backloader.backgroundid == 9){
			song.mute();
			bossMusik.unmute();
		}else{
			song.unmute();
			bossMusik.mute();
		}
		// ****** Musik Steuerung Ende ******
		//************************ Hier kommt der Content vom Spiel / Events ****************************
		if(backloader.backgroundid == 5){
			cutsceneboss1();
		}
		if(backloader.backgroundid == 7){
			cutsceneboss2();
		}
		if(backloader.backgroundid == 8){
			cutsceneboss3();
		}
		if(backloader.backgroundid == 9){
			cutsceneboss4();
		}
		///// ***** Ende Content *****
	}

	//// **** Movment,Events,Cutscenes,ETC *****
public void controlEvent(ControlEvent theEvent){
	System.out.println(theEvent.getController().getName()); // Event Controller f�r die Button Abfrage
}

public void Fallout(int theValue){ // Button Name Antwort wird hier Aufgerufen und kann manipuliert werden
	animationMovmentStop = false;
	gbuttons.buttonBoss1.hide();
	if(entercutscene >= 7){
		bossKillCount++;
		entercutscene = 0;
		luca6.play();
				}
			textAusgabe = "Luca: Genau, somit hast du die erste Pr�fung gemeistert";
		}

public void WildLands(int theVaule){ // Button mit dem namen test wird Aufgerufen und kann hier manipuliert werden
	if(entercutscene > 6){
	luca7.play();
	luca7.rewind();
			}
		textAusgabe = "Luca: Denk beim n�chsten Versuch besser nach.";
	}

public void MadMax(int theVaule){ // Button mit dem namen test wird Aufgerufen und kann hier manipuliert werden
	if(entercutscene > 6){
	luca7.play();
	luca7.rewind();
			}
		textAusgabe = "Luca: Denk beim n�chsten Versuch besser nach.";
	}

public void DarkSouls(int theVaule){ // Button mit dem namen test wird Aufgerufen und kann hier manipuliert werden
	if(entercutscene > 6){
	luca7.play();
	luca7.rewind();
			}
		textAusgabe = "Luca: Denk beim n�chsten Versuch besser nach.";
	}

///// ***** Boss2 *****

public void DOOM(int theValue){
	if(entercutscene > 4){
		textAusgabe = "Bruno: Fast Richtig, versuch es noch einmal!";
	}
}
public void TombRaider(int theValue){
	if(entercutscene > 4){
		textAusgabe = "Bruno: Fast Richtig, versuch es noch einmal!";
	}
}
public void FinalFantasy(int theValue){
	if(entercutscene > 4){
		animationMovmentStop = false;
		textAusgabe = "Bruno: Genau, somit hast du auch die zweite Pr�fung bestanden.";
		bossKillCount++;
		entercutscene = 0;
		gbuttons.buttonBoss2.hide();
	}
}
public void ChronoTrigger(int theValue){
	if(entercutscene > 4){
		textAusgabe = "Bruno: Fast Richtig, versuch es noch einmal!";
	}
}




///// ***** Boss3 *****
public void megami(int theValue){
	if(entercutscene > 4){
		animationMovmentStop = false;
		textAusgabe = "Adrian: Gut gemacht Mensch, geh nun weiter";
		bossKillCount++;
		entercutscene = 0;
		gbuttons.buttonBoss3.hide();
	}
}
public void persona(int theValue){
	if(entercutscene > 4){
		textAusgabe = "Adrian: Jack ist noch nicht hier ... noch nicht";
	}
}
public void lostbible(int theValue){
	if(entercutscene > 4){
		textAusgabe = "Adrian: Jack ist noch nicht hier ... noch nicht";
	}
}
public void soulhackers(int theValue){
	if(entercutscene > 4){
		textAusgabe = "Adrian: Jack ist noch nicht hier ... noch nicht";
	}
}

///// ***** Boss4 ****
public void jesus(int theValue){
	if(entercutscene > 6){
		textAusgabe = "S�ren: Du willst mir w�rdig sein? Denk nochmal scharf nach!";
	}
}
public void cuthulu(int theValue){
	if(entercutscene > 6){
		animationMovmentStop = false;
		textAusgabe = "S�ren: Das ist richtig, deine Weisheit hat mich �berzeugt";
		bossKillCount++;
		entercutscene = 0;
		gbuttons.buttonBoss4.hide();
	}
}
public void Diablo(int theValue){
	if(entercutscene > 6){
		textAusgabe = "S�ren: Du willst mir w�rdig sein? Denk nochmal scharf nach!";
	}
}
public void anubis(int theValue){
	if(entercutscene > 6){
		textAusgabe = "S�ren: Du willst mir w�rdig sein? Denk nochmal scharf nach!";
	}
}

///// ***** Boss Buttons Ende *****
	
	public void keyPressed(){ // Alle funktionen für Key presses
		if((key == 'd' || key == 'D' || keyCode == RIGHT)){ // Bei tastentdruck d Passiert:
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
			if(animationMovment==false){
			if(animationMovmentStop == true){
					entercutscene = entercutscene +1;	
				}
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
	///// ****** Cutscenes ! *****
	public void cutsceneboss4(){
		if(cutsceneboss4 == false){
			animationMovmentStop = true;
			if(entercutscene == 0){
				textAusgabe = "S�ren: Wer bist du denn, Menschling ?";
			}
			if(entercutscene == 1){
				textAusgabe = "Held: Lass mich raten dies ist nun die Letzte Pr�fung";
			}
			if(entercutscene == 2){
				textAusgabe = "S�ren: Wagst du es Wirklich, dich mir so gegen�ber zu �u�ern?";
			}
			if(entercutscene == 3){
				textAusgabe = "Held: Ich muss ja wohl oder �bel alle Pr�fungen bestehen oder ?";
			}
			if(entercutscene == 4){
				textAusgabe = "S�ren: Aber ja du hast recht, dies ist die letzte deiner Aufgaben";
			}
			if(entercutscene == 5){
				textAusgabe = "Held: Ich habe mich schon darauf vorbereitet!";
			}
			if(entercutscene == 6){
				textAusgabe = "Beantworte mir folgende frage und beweise mir das du w�rdig bist.";
			}
			if(entercutscene == 7){
				textAusgabe = "Was ist nicht tot was ewig liegt und wartet darauf das die Zeit den Tod besiegt?";
				cutsceneboss4= true;
				gbuttons.buttonBoss4.show();
			}
		}
		
	}
	
	public void cutsceneboss3(){
		if(cutsceneboss3 == false){
			animationMovmentStop = true;
			if(entercutscene == 0){
				textAusgabe = "Adrian: Du Wagst es in das Himmelsreich einzudrigen?";
			}
			if(entercutscene == 1){
				textAusgabe = "Held: Ja hier sollte ja die dritte Pr�fung sein oder?";
			}
			if(entercutscene == 2){
				textAusgabe = "Adrian: Das stimmt zwar aber dennoch, es wird nicht Leichter werden";
			}
			if(entercutscene == 3){
				textAusgabe = "Held: Das ist mir ja nichts neues ...";
			}
			if(entercutscene == 4){
				textAusgabe = "Adrian: Nun denn, hier kommt mein R�tsel";
			}
			if(entercutscene == 5){
				textAusgabe = "Adrian: Aus welcher Spieleserie Stammt mein guter Freund: Jack Frost";
				cutsceneboss3= true;
				gbuttons.buttonBoss3.show();
			}
		}
	}
	
	public void cutsceneboss2(){
		if(cutsceneboss2 == false){
			animationMovmentStop = true;
			if(entercutscene == 0){
				textAusgabe = "Bruno: Wilkommen Held, im Reich des Feuers";
			}
			if(entercutscene == 1){
				textAusgabe = "Held: Lass mich raten du hast auch eine Pr�fung die ich l�sen muss";
			}
			if(entercutscene == 2){
				textAusgabe = "Bruno: Das ist Richtig";
			}
			if(entercutscene == 3){
				textAusgabe = "Held: Dann mal los ich bin Bereit!";
			}
			if(entercutscene == 4){
				textAusgabe = "Bruno: Gut wie du willst";
			}
			if(entercutscene == 5){
				textAusgabe = "Bruno: Seit mehr als 30 Jahren gibt es mich, doch werde ich immer erneuert, wer bin ich";
				cutsceneboss2= true;
				gbuttons.buttonBoss2.show(); // Buttons anzeigen test
			}
		}
	}
	
	public void cutsceneboss1(){
		if(cutsceneboss1 == false){
			animationMovmentStop = true;
			if(entercutscene == 0){
				textAusgabe = "Luca: Was suchst du hier im DreamRealm?";
				luca1.play();
			}
			if(entercutscene == 1){
				textAusgabe = "Held: Wenn ich das selber w�sste, dann w�rde ich hier nicht Stehen";
			}
			if(entercutscene == 2){
				textAusgabe = "Luca: Da du nun hier bist, L�se meine Aufgabe";
				luca2.play();
			}
			if(entercutscene == 3){
				textAusgabe = "Held: und wenn ich keine Lust dazu habe";
			}
			if(entercutscene == 4){
				textAusgabe = "Luca: Dann wirst du hier dein Ende finden";
				luca3.play();
			}
			if(entercutscene == 5){
				textAusgabe = "Held: Alles klar hab�s Verstanden";
			}
			if(entercutscene == 6){
				textAusgabe = "Luca: Nun sag mir, in welcher postapokalyptische Welt";
				luca4.play();
			}
			if(entercutscene == 7){
				textAusgabe = "Luca: f�hlte ich mich im Jahre 2015 am wohlsten.";
				luca5.play();
				cutsceneboss1= true;
				gbuttons.buttonBoss1.show(); // Buttons anzeigen test
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
					if(schrittZaehler != 50){
				animationMovment = true;
				leftmov = charmov.animationleft(leftmov);
				animationchanger = leftmov;
				bewegungseitlich = charmov.charbewegunglinks(bewegungseitlich);
				schrittZaehler++;
					}				
					if(schrittZaehler == 50){
						animationMovment = false;
						schrittZaehler = 0;
						entercutscene = 2;
						}				
					}
			
				if(entercutscene == 2){
					textAusgabe = "Held: Dann mal los.";
					if(schrittZaehler != 25){
						animationMovment = true;
						upmov = charmov.animationup(upmov);
						animationchanger = upmov;
						bewegunghorizontal = charmov.charbewegunghoch(bewegunghorizontal);
						schrittZaehler++;
					}
					if(schrittZaehler >= 24){
						animationMovment = false;
						schrittZaehler = 0;
						entercutscene = 3;
						}
					}
				
				if(entercutscene == 3){
					switch2 = true;
					textAusgabe = "";
					if(schrittZaehler != 25){
						animationMovment = true;
						leftmov = charmov.animationleft(leftmov);
						animationchanger = leftmov;
						bewegungseitlich = charmov.charbewegunglinks(bewegungseitlich);
						schrittZaehler++;
					}
					if(schrittZaehler >= 24){
						animationMovment = false;
						schrittZaehler = 0;
						entercutscene = 4;
						}
					}
				if(entercutscene >= 4){
					switch1 = true;
					erstecutscene = false;
					animationMovmentStop = false;
					entercutscene = 0;
					bewegungseitlich = 185;
					bewegunghorizontal = 280;
					}
				}
			}
	
	public void pixelcollisionright(int x, int y){
		
		loadPixels();
		backgroundcollision[backloader.backgroundid].loadPixels();
		int pixelcordiante=0;
		pixelcordiante = 26+x+(y+46)*800;
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
	///// ***** ENDE Events,Cutscenes,ECT ******

}