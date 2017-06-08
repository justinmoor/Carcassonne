package Controllers;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Views.CreditsScene;
import Views.EndGameScene;
import Views.GameScene;
import Views.LobbyScene;
import Views.MenuViewScene;
import Views.PreLobbyScene;
import Views.SettingsScene;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import Models.RMIInterface;

public class MenuController {

	Stage gameStage;

	private EndGameScene endGameScene;
	private GameScene gameScene;
	private LobbyScene lobbyScene;
	private MenuViewScene menuViewScene;
	private PreLobbyScene preLobbyScene;
	private SettingsScene settingsScene;
	private CreditsScene creditsScene;

	private String spelernaam;

	public MenuController(Stage gameStage) {

		LobbyController lobbyController = new LobbyController();


		this.gameStage = gameStage;
		endGameScene = new EndGameScene();
		gameScene = new GameScene(this);
		lobbyScene = new LobbyScene(this, lobbyController);
		menuViewScene = new MenuViewScene(this);
		preLobbyScene = new PreLobbyScene(this, lobbyController);
		settingsScene = new SettingsScene(this);
		creditsScene = new CreditsScene(this);

		setMenuViewScene();
		//setGameScene();
		gameStage.show();
		gameStage.setOnCloseRequest(e -> {
			System.exit(0);
		});
	}


	/**
	 * Button action method om de speler terug in het hoofd menu te krijgen
	 */
	public void backToMainMenu(){
		getGameStage().setScene(getMenuViewScene());
	}

	/**
	 * Buttion action method om de speler naar het spel einde scherm te laten stellen
	 */
	public void setEndGameScene(){
		gameStage.setScene(endGameScene);
	}
	public void setGameScene(){
		gameStage.setScene(gameScene);
		gameScene.Join(spelernaam);
	//	gameStage.setFullScreen(SettingsScene.fullScreen);
	}

	public void setLobbyScene(){
		gameStage.setScene(lobbyScene);
		getLobbyScene().Join();
	}

	public void setMenuViewScene(){
		gameStage.setScene(menuViewScene);
	}

	public void setPreLobbyScene(){
		gameStage.setScene(preLobbyScene);

	}
	public void setSettingsScene(){

		gameStage.setScene(settingsScene);
	}
	
	public void setCreditsScene(){

		gameStage.setScene(creditsScene);
	}




	public Stage getGameStage() {
		return gameStage;
	}

	public EndGameScene getEndGameScene() {
		return endGameScene;
	}

	public GameScene getGameScene() {
		return gameScene;
	}

	public LobbyScene getLobbyScene() {
		return lobbyScene;
	}

	public MenuViewScene getMenuViewScene() {
		return menuViewScene;
	}

	public PreLobbyScene getPreLobbyScene() {
		return preLobbyScene;
	}

	public SettingsScene getSettingsScene() {
		return settingsScene;
	}
	
	public CreditsScene getCreditsScene() {
		return creditsScene;
	}


	public void updatePlayerList() {
		// TODO Auto-generated method stub

	}


	public String getSpelernaam() {
		return spelernaam;
	}


	public void setSpelernaam(String spelernaam) {
		this.spelernaam = spelernaam;
	}






}
