/*
 * Copyright 2012 Richard Beech (digitale001@gmail.com)
 * Core Stardust Class, manages game data and screen navigation
 */
package com.digitale.mygdxgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.digitale.connex.Actor;
import com.digitale.connex.AssetDef;
import com.digitale.connex.Avatar;
import com.digitale.connex.AvatarDef;
import com.digitale.connex.Channeldef;
import com.digitale.connex.Chatline;
import com.digitale.connex.Fitting;
import com.digitale.connex.GroundItem;
import com.digitale.connex.Inventory;
import com.digitale.connex.Location;
import com.digitale.connex.MissionDef;
import com.digitale.connex.MissionLog;
import com.digitale.connex.NPC;
import com.digitale.connex.News;
import com.digitale.connex.ShipDef;
import com.digitale.connex.celestial;
import com.digitale.screens.CharCreator;
import com.digitale.screens.CharPicker;
import com.digitale.screens.GameLoop;
import com.digitale.screens.Help;
import com.digitale.screens.Login;
import com.digitale.screens.NewAccount;
import com.digitale.screens.Options;
import com.digitale.screens.Solar;
import com.digitale.screens.Splash;
import com.digitale.screens.StardustScreen;
import com.digitale.sim.Simulation;
import com.digitale.utils.Checker;
import com.digitale.utils.DataOp;
import com.digitale.utils.DataOpImpl;
import com.digitale.utils.NativeFunctions;

public class BattleBeasties3d extends Game {

	public static AssetManager manager = new AssetManager();
	// debug for spammy outputs
	public static final boolean DEEPDEBUG = false;
	// system debug flag
	public static final boolean DEBUG = true;
	public static String version="0.2.21.10.19.00";
	public static NativeFunctions mNativeFunctions;
	public static boolean selectScreen;
	static String helptext = "\nVersion:"+version+"\n\n"
			+ "In flight controls:-\n"
			+ "PC keyboard controls:-\n R=accelerate, F=deccelerate‎, Cursor Keys or WASD steer ship, Space bar to shoot.\n"
			+ " Q=fire missiles, E=repair ship\n\n Touch screen control & mouse:-\n"
			+ "Click arrow in top left to pull-out menu bar.\n"
			+ "Thumb control (bottom left, when in station) moves camera.\n"
			+ "Click and hold on the top and bottom of speed meter (middle of left side of screen) to accelerate/deccelerate‎.\n"
			+ "Shoot gun by touching bottom right button.\n"
			+ "When close enough to station, a docking button will appear, top right of screen, click it to dock. Use the same button to undock your ship.\n"+
			"\nCredits:\n Code and Graphics:Richard Beech\nMusic:Kevin MacLeod http://incompetech.com/\n"+
			"Sound Effects:Stefan Persson  http://soundtrack.imphenzia.com\n\n"+
			"Special thanks to Testers: Vernon bamboozle, Aubrey Beech, Un Ethical, Dave M., Rob Gloria.";
	public static boolean tinyfont;
	public static String mchatlines = "unintialised";
	public static String mshortChatLines = "unintialised";

	public BattleBeasties3d(NativeFunctions nativeFunctions) {
		mNativeFunctions = nativeFunctions;
	}

	/** flag indicating whether we were initialised already **/
	private static boolean isInitialized = false;

	public static String currencharacteruid = "0";
	public static int muser;
	/** the current screen **/
	public static int gameMode = 1;
	public static DataOp MyDataOp = new DataOpImpl();
	public static Avatar myCharacter = new Avatar();
	public static Fitting myFitting = new Fitting();
	public static long mnetworkPing;
	public static long mParsePing;
	public static NPC[] npcList = new NPC[210];
	public static celestial[] solarSystem = new celestial[50];
	public static String mserverTime;
	public static GroundItem LandTypes[] = new GroundItem[20];
	public static Location[][] LandScape = new Location[64][64];
	public static String muserExists;
	public static News news[] = new News[5];
	// character list vars
	public static Avatar[] charList = new Avatar[10];
	public static Avatar[] tempchar;
	// ship graphic definitions
	public static List<ShipDef> shipdefs = new ArrayList<ShipDef>();
	public static int numberOfCharacters;
	private static String mnewslines;
	public static boolean mremeberAccountname;
	public static boolean mloginOK;
	public static float sfxVolume = 0.5f;
	public static float musicVolume = 0.5f;
	// error flag and constants
	public static final int ALL_OK = 0;
	public static int stationScreen = 0;
	public static final int NETWORK_DOWN = 1;
	public static final int SERVER_DOWN = 2;
	public static final int TEST_ERROR = 3;
	public static final int REPAIR_ERROR = 4;
	public static final int MISSILE_ERROR = 5;
	public static final int WIP_ERROR = 6;
	public static final int FULLHP_ERROR = 7;
	public static final int MISSILE_LOCK_ERROR = 8;
	public static final int RANGE_ERROR = 9;
	public static final int MISSION_SCREEN = 1;
	public static final int NO_SCREEN = 0;
	public static int Error = ALL_OK;
	public static List<AvatarDef> avatarList = new ArrayList<AvatarDef>();
	public static List<AssetDef> assetList = new ArrayList<AssetDef>();
	public static List<Chatline> chatList = new ArrayList<Chatline>();
	public static List<Chatline> serverChatList = new ArrayList<Chatline>();
	public static List<Channeldef> channelList = new ArrayList<Channeldef>();
	public static List<Actor> actorsList = new ArrayList<Actor>();
	public static List<Inventory> myInventory = new ArrayList<Inventory>();
	public static List<Inventory> vendorInventory = new ArrayList<Inventory>();
	public static List<MissionDef> missionDefs = new ArrayList<MissionDef>();
	public static List<MissionLog> myMissions = new ArrayList<MissionLog>();
	public static List<Vector3> battlePositions = new ArrayList<Vector3>();
	public static String generatedFirstName = "";
	public static String generatedSurName = "";
	public static boolean fontsize;
	public static int chatChannel = 4;
	public static String chatChannelName = "";
	public static int currentScreen;
	public static boolean chatListReset;
	public static float accelXsensitivity = 1;
	public static float accelYsensitivity = 1;
	public static float deadzoneX = .5f;
	public static float deadzoneY = .5f;
	public static float dustsize = 50;

	@Override
	public void dispose() {
		SoundManager.dispose();
		Simulation.timer.cancel();
		MyDataOp.setRunning(false);
		manager.dispose();
	}

	@Override
	public void render() {

		StardustScreen currentScreen = getScreen();
		// update the screen
		currentScreen.update(Gdx.graphics.getDeltaTime());
		// render the screen
		currentScreen.render(Gdx.graphics.getDeltaTime());
		// when the screen is done we change to the
		// next screen
		if (selectScreen) {
			// coming from a screen we dont want to dispose ie main sim
			switch (gameMode) {

			case 4:
				if (BattleBeasties3d.DEBUG)
					System.out.println("run game");
				setScreen(new GameLoop());
				break;
			case 8:
				if (BattleBeasties3d.DEBUG)
					System.out.println("run solar sys info");
				setScreen(new Solar());
				break;
			case 9:
				if (BattleBeasties3d.DEBUG)
					System.out.println("run help");
				setScreen(new Help(helptext));
				break;
			case 13:
				if (BattleBeasties3d.DEBUG)
					System.out.println("run market info dep");
				// setScreen(new Market());
				break;

			}
			// reset selectscreen so we only call once
			selectScreen = false;
		}

		if (currentScreen.isDone()) {
			// dispose the current screen
			// may be some screens we dont want to dispose main sim
			currentScreen.dispose();
			// switch to requested screen
			// the game loop
			if (BattleBeasties3d.DEBUG)
				System.out.println("running mode" + gameMode);
			switch (gameMode) {
			case 2:
				if (BattleBeasties3d.DEBUG)
					System.out.println("run login");
				setScreen(new Login(mnewslines));
				break;
			case 3:
				if (BattleBeasties3d.DEBUG)
					System.out.println("run charpicker");
				setScreen(new CharPicker());
				break;
			// run game
			case 4:
				if (BattleBeasties3d.DEBUG)
					System.out.println("run game");
				setScreen(new GameLoop());
				break;
			case 6:
				if (BattleBeasties3d.DEBUG)
					System.out.println("run options");
				setScreen(new Options());
				break;

			case 9:
				if (BattleBeasties3d.DEBUG)
					System.out.println("run help");
				setScreen(new Help(helptext));
				break;

			case 14:
				if (BattleBeasties3d.DEBUG)
					System.out.println("run new account");
				setScreen(new NewAccount());
				break;
			case 15:
				if (BattleBeasties3d.DEBUG)
					System.out.println("run new character");
				setScreen(new CharCreator(muser));
				break;

			}
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void create() {

		if (!isInitialized) {
			MyDataOp.getAssetDefs();
			/*
			 * try { Checker.CheckEncryption(); } catch (Exception e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */
			loadPrefs();
			populateBattlePositions();
			setScreen(new Splash());
			enqueueAssets();
			isInitialized = true;

		}

	}

	private void populateBattlePositions() {
		battlePositions.add(new Vector3(-20,0,10));
		battlePositions.add(new Vector3(-15,0,0));
		battlePositions.add(new Vector3(-20,0,-10));
		
		battlePositions.add(new Vector3(20,0,10));
		battlePositions.add(new Vector3(15,0,0));
		battlePositions.add(new Vector3(20,0,-10));
		
	}

	private void enqueueAssets() {
		String fileName;
		TextureParameter param = new TextureParameter();
		param.minFilter = TextureFilter.MipMap;
		param.magFilter = TextureFilter.MipMapNearestLinear;
		param.genMipMaps = true;
		for (int i = 0; i < assetList.size(); i++) {
			fileName = (assetList.get(i).getAssetName() + "." + assetList
					.get(i).getExt());
			if (BattleBeasties3d.DEBUG)
				System.out.println("enqueue " + fileName);
			manager.load("data/" + "lvlup.jpg", Texture.class);
			manager.load("sound/" + "schmetterling.mp3", Sound.class);
			manager.load("data/" + "explodewave.jpg", Texture.class);
			if ((assetList.get(i).getType()).equals("font")) {
				manager.load("data/" + fileName, BitmapFont.class);
			} else if ((assetList.get(i).getType()).equals("3dtex")
					|| (assetList.get(i).getType()).equals("2dtex")) {
				manager.load("data/" + fileName, Texture.class);

			} else if ((assetList.get(i).getType()).equals("sound")) {
				manager.load("sound/" + fileName, Sound.class);
			} else if ((assetList.get(i).getType()).equals("music")) {
				manager.load("sound/" + fileName, Music.class);
			} else if ((assetList.get(i).getType()).equals("itemicon")) {
				manager.load("data/" + fileName, Pixmap.class);
				
			}
		}

	}

	/**
	 * 
	 */
	private void loadPrefs() {
		// get prefs, if no prefs (-1) use factory setting
		Preferences prefs = Gdx.app.getPreferences("stardustpreferences");
		float tempFloat;
		int tempInt = -1;
		boolean tempBoolean;
		tempBoolean = prefs.getBoolean("fontsize", false);
		tinyfont = tempBoolean;
		tempFloat = prefs.getFloat("sfxvolume", -1);
		if (tempFloat != -1)
			sfxVolume = tempFloat;
		// get music volume from prefs
		tempFloat = prefs.getFloat("musicvolume", -1);
		if (tempFloat != -1)
			musicVolume = tempFloat;
		// get chat channel from prefs
		tempInt = prefs.getInteger("selectedchatchannel", -1);
		if (tempInt != -1) {
			if (tempInt < -1) {
				tempInt = 8;
			}
			chatChannel = tempInt;
		}
		// get dust size from prefs
		tempFloat = prefs.getFloat("dustsize", 50);
		if (tempFloat != 50)
			dustsize = tempFloat;
		// get horiz dead zone from prefs
		tempFloat = prefs.getFloat("dzx", 0.5f);
		if (tempFloat != .5)
			deadzoneX = tempFloat;
		// get vert dead zone from prefs
		tempFloat = prefs.getFloat("dzy", 0.5f);
		if (tempFloat != .5)
			deadzoneY = tempFloat;
		// get horiz sensitivity from prefs
		tempFloat = prefs.getFloat("sx", 1f);
		if (tempFloat != 1)
			accelXsensitivity = tempFloat;
		// get very dead zone from prefs
		tempFloat = prefs.getFloat("sy", 1f);
		if (tempFloat != 1)
			accelYsensitivity = tempFloat;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		if (BattleBeasties3d.DEBUG)
			System.out.println("stardust pause");
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		if (BattleBeasties3d.DEBUG)
			System.out.println("resume");
	}

	public static void showmap() {
		mNativeFunctions.openURL("http://www.example.com");
		Gdx.app.exit();
	}

	/**
	 * For this game each of our screens is an instance of StardustScreen.
	 * 
	 * @return the currently active {@link StardustScreen}.
	 */
	public StardustScreen getScreen() {
		return (StardustScreen) super.getScreen();
	}

	// refresh actors data
	public static void refreshActors() {

		/*BattleBeasties3d.MyDataOp.getLocalPlayers(BattleBeasties3d.myCharacter.getX(),
				BattleBeasties3d.myCharacter.getY(), BattleBeasties3d.myCharacter.getZ(),
				false);*/

	}

	/**
	 * Get latest chat data
	 * 
	 * @param selectedChannel
	 */
	public static void refreshChat(int selectedChannel, boolean serverMessages) {

		if (chatListReset) {
			chatList.clear();
			if (selectedChannel == 8) {
				MyDataOp.getchat(-BattleBeasties3d.myCharacter.getUid(), true,
						serverMessages);
			} else {
				MyDataOp.getchat(selectedChannel, true, serverMessages);
			}
			chatListReset = false;
		} else {
			if (selectedChannel == 8) {
				MyDataOp.getchat(-BattleBeasties3d.myCharacter.getUid(), false,
						serverMessages);
			} else {
				MyDataOp.getchat(selectedChannel, false, serverMessages);
			}
		}
		if (!serverMessages) {
			String tempChatlines = " \n";
			String shortChatlines = BattleBeasties3d.chatChannelName + "\n";
			try {
				for (int i = 0; i < chatList.size(); i++) {
					// check to see that there is news to add
					if (chatList.get(i).getMessage() != null && i<=500) {
						// Create chat string nicely formatted
						String timeConvert = chatList.get(i).getTimestamp()
								.toString();
						tempChatlines = tempChatlines
								+ timeConvert.substring(11,
										timeConvert.length() - 2);
						// if all channels are selected, add channelname
						if (selectedChannel == 10) {
							tempChatlines = tempChatlines + " ["
									+ chatList.get(i).getChannelname() + "]";
						}

						tempChatlines = tempChatlines + " "
								+ chatList.get(i).getName() + ": "
								+ chatList.get(i).getMessage() + " \n";

						// set minichat to first 6 lines of chat
						if (i < 6) {
							shortChatlines = shortChatlines
									+ timeConvert.substring(11,
											timeConvert.length() - 2);
							// if all channels are selected, add channelname
							if (selectedChannel == 10) {
								shortChatlines = shortChatlines + " ["
										+ chatList.get(i).getChannelname()
										+ "]";
							}

							shortChatlines = shortChatlines + " "
									+ chatList.get(i).getName() + ": "
									+ chatList.get(i).getMessage() + " \n";
						}
					}
					if (i>500){
						chatList.remove(i);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (mchatlines.length() != tempChatlines.length())
				mchatlines = tempChatlines;
			// if (mshortChatLines.length() != shortChatlines.length())
			mshortChatLines = shortChatlines;
			// if (Renderer.minichatFontCache !=null){

			// }

		}
	}

	/**
 * 
 */
	public static void populateCharacterlist(int user) {
		MyDataOp.getChars(user);
		numberOfCharacters = 0;
		for (int i = 0; i < charList.length; i++) {
			if (charList[i].getFirstname() != null) {

				numberOfCharacters++;
			}
		}
	}

	public static void loadstage01() {

	}

	public static void loadstage02() {
		//MyDataOp.getAvatarDefs();
		if (BattleBeasties3d.DEBUG) {
			for (int i = 0; i < avatarList.size(); i++) {
				System.out.println("avatardef " + i + " "
						+ avatarList.get(i).getDescription());
			}
		}
		for (int i = 0; i < charList.length; i++) {
			charList[i] = new Avatar();
		}

	}

	public static void loadstage03() {
		// copy to new array without nulls
		tempchar = new Avatar[numberOfCharacters];
		for (int i = 0; i < tempchar.length; i++) {
			tempchar[i] = charList[i];
		}
		// convert array to list
		List<Avatar> charRealList = Arrays.asList(tempchar);

		for (int i = 0; i < npcList.length; i++) {
			npcList[i] = new NPC();
		}

		for (int i = 0; i < LandTypes.length; i++) {
			LandTypes[i] = new GroundItem();
		}
		for (int x = 0; x < 64; x++) {
			for (int y = 0; y < 64; y++) {
				LandScape[x][y] = new Location();
			}
		}

	}

	public static void loadstage04() {
		//MyDataOp.getShipDefs();
		if (BattleBeasties3d.DEBUG) {
			for (int i = 0; i < shipdefs.size(); i++) {
				System.out.println("shipdef " + i + " "
						+ shipdefs.get(i).getModel());
			}
		}
		for (int i = 0; i < solarSystem.length; i++) {
			solarSystem[i] = new celestial();
		}
		String returnString = "test";
		// MyDataOp.getLandTypeData(returnString);
		// MyDataOp.getLandscapeData(returnString);

	}

	public static void loadstage05() {
		String returnString = "test";
		// MyDataOp.getNPCData(returnString);
		// MyDataOp.getServerTime(returnString);

	}

	public static void loadstage06() {

		MyDataOp.getInventory(Integer.valueOf(currencharacteruid),true);
		for (int i = 0; i < news.length; i++) {
			news[i] = new News();
		}

	}

	public static void loadstage07() {
		//MyDataOp.getNews();

		mnewslines = " \n";
		// insert news text into newsTextView
		for (int i = 0; i < news.length; i++) {
			// check to see that there is news to add
			if (news[i].get_title() != null) {
				// Create news string nicely formatted

				mnewslines = mnewslines + news[i].get_title() + "\n"
						+ news[i].get_date() + "\n" + news[i].get_text()
						+ " \n";
				mnewslines = mnewslines
						+ "________________________________________\n";
			}
		}

	}

	public static void loadstage08() {
		//MyDataOp.getChatDefs();
		for (int i = 0; i < channelList.size(); i++) {
			if (BattleBeasties3d.DEBUG)
				System.out.println("channeldef " + i + " "
						+ channelList.get(i).getChannelname());
		}

		for (int i = 0; i < charList.length; i++) {
			charList[i] = new Avatar();
		}
	}

	public static void loadstage09() {
		// TODO Auto-generated method stub
		chatChannelName = "bob";// channelList.get(chatChannel).getChannelname();
		//chatListReset = true;
		//refreshChat(chatChannel, false);
		//chatListReset = true;
		//refreshChat(1, true);
	}

	public static void loadstage10() {
		// TODO Auto-generated method stub

	}

}
