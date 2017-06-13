package Models;

import Views.GameScene;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import Models.TileStump;

import java.rmi.RemoteException;

/**
 * Created by Marti on 11-6-2017.
 */
public class GameClient {

	GameScene view;

	Thread gameThread;

	private boolean enableThread = true;

	private String spelerNaam;
	public String kaartPlaatsId = "";

	String spelerBeurt = "";
 	int beurt = 0;

	RMIInterface RmiStub;

	public GameClient(GameScene view){
		this.view = view;

	}


	/**
	 * Deze functie MOET gerunt worden als de speler de game joint, dit start de thread en set de spelernaam.
 	 * @param spelerNaam
	 */
	public void Join(String spelerNaam) {
	this.spelerNaam = spelerNaam;

		gameThread = new Thread( () -> {
			while(enableThread == true){
				Update();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		gameThread.start();

	}

	/**
	 * Pak een kaart van de stapel, dit kan alleen als de speler aan de beurt is
 	 */
	public void pakKaart() {
		try {
			String id = RmiStub.pakKaart(spelerNaam);
			if(id == null){
				return;
			}
			kaartPlaatsId = id;
			view.showKaart(this);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Plaats de kaart in de view
	 * @param x coordinaat
	 * @param y coordinaat
	 */
	public void plaatsKaart(int x, int y) {
		try {
			if(kaartPlaatsId != "" && RmiStub.plaatsKaart(x,y)) {
				view.plaatsKaart(this, kaartPlaatsId, x, y);
				kaartPlaatsId = "";
			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}

	public void setRmiStub(RMIInterface rmiController) {
		RmiStub = rmiController;
		view.RmiStub = rmiController;
	}


	/**
	 * De client wordt elke x ms geupdate, als de beurt op de server hoger is dan de beurt op de client betekent dat en
	 * speler klaar is met zijn beurt, en het spelbord geupdate moet worden.
	 */
	public void Update() {
		try {
			if (beurt != RmiStub.getBeurt()) {
				view.updateView(this);
				beurt = RmiStub.getBeurt();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public TileStump getTile() throws RemoteException {
		try {
			return RmiStub.getPlacedKaart();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
}


