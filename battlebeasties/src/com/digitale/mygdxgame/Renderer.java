/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.digitale.mygdxgame;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.loaders.obj.ObjLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.digitale.connex.Actor;
import com.digitale.connex.ActorNameCache;
import com.digitale.screens.Char;
import com.digitale.screens.Chat;
import com.digitale.screens.FactoryScreen;
import com.digitale.screens.GameLoop;
import com.digitale.screens.InGameHelp;
import com.digitale.screens.InGameOptions;
import com.digitale.screens.MapScreen;
import com.digitale.screens.Market;
import com.digitale.screens.MissionLog;
import com.digitale.screens.MissionScreen;
import com.digitale.screens.ShipScreen;
import com.digitale.screens.SpaceInventory;
import com.digitale.screens.StationInventory;
import com.digitale.screens.Vendor;
import com.digitale.sim.Drone;
import com.digitale.sim.Dust;
import com.digitale.sim.Explosion;
import com.digitale.sim.Missile;
import com.digitale.sim.Ship;
import com.digitale.sim.Shot;
import com.digitale.sim.Simulation;
import com.digitale.sim.Station;
import com.digitale.sim.Trail;
import com.digitale.utils.Actors;
import com.digitale.utils.Util;

/**
 * The renderer receives a simulation and renders it.
 * 
 * @author rbeech
 */
public class Renderer {

	/** set scale factor for world **/
	private static final float worldscale = .001f;
	/** sprite batch to draw text **/
	private SpriteBatch spriteBatch;
	/** the squirrel mesh **/
	private static Mesh squirrelMesh;
	/** the sun mesh **/
	private static Mesh sunMesh;
	/** the shield disrupter mesh **/
	private static Mesh shieldDisruptorMesh;
	/** the shield disrupter texture **/
	private Texture shieldDisruptorTexture;
	/** the cone mesh **/
	private static Mesh coneMesh;
	/** the swirlyglow texture **/
	private Texture swirlyTexture;
	/** the final explosion texture **/
	private Texture swansongTexture;
	/** the station mesh **/
	private static Mesh stationMesh;
	/** the ship mesh **/
	private static Mesh shipMesh01;
	/** the ship texture **/
	private Texture shipTexture01;
	/** the ship mesh **/
	private static Mesh shipMesh02;
	/** the ship texture **/
	private Texture shipTexture02;
	/** the ship mesh **/
	private static Mesh shipMesh03;
	/** the ship texture **/
	private Texture blackpixelTexture;
	private Texture shipTexture03;
	/** the ship texture **/
	private Texture squirrelTexture;
	/** the planet mesh **/
	private static Mesh planetMesh02;
	/** the planet mesh **/
	private static Mesh planetMesh;
	/** the planet texture **/
	private Texture planetTexture;
	/** the planet texture **/
	private Texture planetTexture02;
	/** the jet mesh **/
	private static Mesh jetMesh;
	/** the jet texture **/
	private Texture jetTexture;
	/** the moon mesh **/
	private static Mesh moonMesh;
	/** the moon texture **/
	private Texture moonTexture;
	/** the ship mesh **/
	private static Mesh skyMesh;
	/** the ship texture **/
	private Texture skyTexture;
	/** the asteroid mesh **/
	private static Mesh roidMesh;
	/** the dust texture **/
	private Texture dustTexture;
	/** the asteroid texture **/
	private Texture roidTexture;
	/** the station texture **/
	private Texture stationTexture;
	/** the forcefield mesh **/
	private static Mesh forcefieldMesh;
	/** the block mesh **/
	private static Mesh blockMesh;
	/** the hangar mesh **/
	private static Mesh hangarMesh;
	/** the hangar texture **/
	private Texture hangarTexture;
	/** the grid texture **/
	private Texture gridTexture;
	/** the shot mesh **/
	private static Mesh shotMesh;
	/** the explosion mesh **/
	private Mesh explosionMesh;
	/** the explosion texture **/
	private Texture explosionTexture;
	/** the lvlup texture **/
	private Texture lvlupTexture;
	public static BitmapFont fontnormal;
	public static BitmapFont fontsmall;
	public BitmapFont fonttiny;
	public static BitmapFontCache minichatFontCache;
	public static BitmapFontCache statusFontCache;
	public static List<ActorNameCache> actorNames = new ArrayList<ActorNameCache>();
	// hud icons
	/** red vingette overlay when player takes damage **/
	private Texture hurtflash;
	/** the reticule texture **/
	private Texture reticuleTexture;
	/** the help menu icon texture **/
	private Texture helpTexture;
	/** the chat menu icon texture **/
	private Texture chatTexture;
	/** the character menu icon texture **/
	private Texture characterTexture;
	/** the ship icon texture **/
	private Texture shipiconTexture;
	/** the inventory menu icon texture **/
	private Texture inventoryTexture;
	/** the cash icon texture **/
	private Texture cashTexture;
	/** the swap beastie icon texture **/
	private Texture swapTexture;
	/** the options menu icon texture **/
	private Texture optionsTexture;
	/** the pullout menu icon texture **/
	private Texture pulloutTexture;
	/** the close icon texture **/
	private Texture mapTexture;
	/** the thumb control texture **/
	private Texture thumbControlTexture;
	/** the missile icon texture **/
	private Texture missileTexture;
	/** the surrender icon texture **/
	private Texture surrenderTexture;
	/** the shooticon texture **/
	private Texture shootTexture;
	/** the dock icon texture **/
	private Texture dockTexture;
	/** the repair icon texture **/
	private Texture repairTexture;
	/** the mission icon texture **/
	private Texture missionTexture;
	/** the mission log texture **/
	private Texture logTexture;
	/** the vendor icon texture **/
	private Texture vendorTexture;
	/** the crafting icon texture **/
	private Texture buildTexture;
	/** the exit icon texture **/
	private Texture exitTexture;
	/** the speedo texture **/
	private Texture speedoTexture;
	/** the rotation angle of all invaders around y **/
	public static float invaderAngle = 0;
	/** the rotation angle of all stations around z **/
	public static float stationAngle = 0;
	/** status string **/
	public static String status = "";
	static Skin skin;
	public static Stage stage;
	/** perspective camera **/
	public static PerspectiveCamera camera;
	private Vendor vendorScreen;
	private Chat chatScreen;
	private Char charScreen;
	private InGameHelp helpScreen;
	private ShipScreen shipScreen;
	private FactoryScreen factoryScreen;
	private StationInventory stationInventoryScreen;
	private InGameOptions optionsScreen;
	private SpaceInventory spaceInventoryScreen;
	private Market marketScreen;
	private MapScreen mapScreen;
	private MissionLog logScreen;
	private MissionScreen missionScreen;
	private int width;
	private int height;
	TextureRegion gaugetop;
	TextureRegion gaugemiddle;
	TextureRegion gaugebottom;

	public void addActor(com.badlogic.gdx.scenes.scene2d.Actor stageActor) {
		stage.addActor(stageActor);
	}

	public static boolean toastShown;

	public Renderer(Application app) {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		Gdx.input.setInputProcessor(null);
		spriteBatch = new SpriteBatch();
		try {
			// bindMeshes();
			try {
				bindTextures();
			} catch (GdxRuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			explosionMesh = new Mesh(true, 4 * 16, 0, new VertexAttribute(
					Usage.Position, 3, "a_position"), new VertexAttribute(
					Usage.TextureCoordinates, 2, "a_texCoord"));
			// explosion mesh

			float[] vertices = new float[4 * 16 * (3 + 2)];
			int idx = 0;
			for (int row = 0; row < 4; row++) {
				for (int column = 0; column < 4; column++) {
					vertices[idx++] = 1;
					vertices[idx++] = 1;
					vertices[idx++] = 0;
					vertices[idx++] = 0.25f + column * 0.25f;
					vertices[idx++] = 0 + row * 0.25f;

					vertices[idx++] = -1;
					vertices[idx++] = 1;
					vertices[idx++] = 0;
					vertices[idx++] = 0 + column * 0.25f;
					vertices[idx++] = 0 + row * 0.25f;

					vertices[idx++] = -1;
					vertices[idx++] = -1;
					vertices[idx++] = 0;
					vertices[idx++] = 0f + column * 0.25f;
					vertices[idx++] = 0.25f + row * 0.25f;

					vertices[idx++] = 1;
					vertices[idx++] = -1;
					vertices[idx++] = 0;
					vertices[idx++] = 0.25f + column * 0.25f;
					vertices[idx++] = 0.25f + row * 0.25f;
				}
			}

			explosionMesh.setVertices(vertices);
			BattleBeasties3d.manager.get("data/default.fnt", BitmapFont.class);
			fontsmall = BattleBeasties3d.manager.get("data/small.fnt",
					BitmapFont.class);
			minichatFontCache = new BitmapFontCache(fontsmall);
			statusFontCache = new BitmapFontCache(fontsmall);

			fonttiny = BattleBeasties3d.manager.get("data/smallest.fnt",
					BitmapFont.class);
			fontnormal = BattleBeasties3d.manager.get("data/default.fnt",
					BitmapFont.class);
			// camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(),
			// Gdx.graphics.getHeight());
			camera = new PerspectiveCamera(67, 800, 480);
			skin = new Skin(Gdx.files.internal("data/uiskin.json"),
					Gdx.files.internal("data/uiskin.png"));
			stage = new Stage(Gdx.graphics.getWidth(),
					Gdx.graphics.getHeight(), false);
			Gdx.input.setInputProcessor(stage);
			new TextButton("Quick Help", skin.getStyle(TextButtonStyle.class),
					"buttonHelp");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @return
	 * @throws IOException
	 */
	public static InputStream bindMeshes(int chunk) throws IOException {
		InputStream in;
		switch (chunk) {
		case 0:
			in = Gdx.files.internal("data/jet.obj").read();
			jetMesh = ObjLoader.loadObj(in, true);
			in.close();

			in = Gdx.files.internal("data/ship02.obj").read();
			shipMesh01 = ObjLoader.loadObj(in, true);
			in.close();
			in = Gdx.files.internal("data/squirrel.obj").read();
			squirrelMesh = ObjLoader.loadObj(in, true);
			in.close();
			in = Gdx.files.internal("data/cone.obj").read();
			coneMesh = ObjLoader.loadObj(in, true);
			in.close();
			break;
		case 1:
			in = Gdx.files.internal("data/shielddisruptor.obj").read();
			shieldDisruptorMesh = ObjLoader.loadObj(in, true);
			in.close();
			in = Gdx.files.internal("data/sun.obj").read();
			sunMesh = ObjLoader.loadObj(in, true);
			in.close();
			in = Gdx.files.internal("data/ship03.obj").read();
			shipMesh02 = ObjLoader.loadObj(in, true);
			in.close();

			in = Gdx.files.internal("data/ship.obj").read();
			shipMesh03 = ObjLoader.loadObj(in, true);
			in.close();
			break;
		case 2:
			in = Gdx.files.internal("data/moon.obj").read();
			moonMesh = ObjLoader.loadObj(in, true);
			in.close();

			in = Gdx.files.internal("data/station.obj").read();
			stationMesh = ObjLoader.loadObj(in, true);
			in.close();

			in = Gdx.files.internal("data/planet.obj").read();
			planetMesh = ObjLoader.loadObj(in, true);
			in.close();

			in = Gdx.files.internal("data/planet02.obj").read();
			planetMesh02 = ObjLoader.loadObj(in, true);
			in.close();
			break;
		case 3:
			in = Gdx.files.internal("data/block.obj").read();
			blockMesh = ObjLoader.loadObj(in, false);
			in.close();

			in = Gdx.files.internal("data/hangar01.obj").read();
			hangarMesh = ObjLoader.loadObj(in, true);
			in.close();

			in = Gdx.files.internal("data/roid01.obj").read();
			roidMesh = ObjLoader.loadObj(in, false);
			in.close();

			in = Gdx.files.internal("data/shot.obj").read();
			shotMesh = ObjLoader.loadObj(in, false);
			in.close();
			break;
		case 4:
			// note use of tru to flip texture V co-ords for max OBJ files
			in = Gdx.files.internal("data/sky.obj").read();
			skyMesh = ObjLoader.loadObj(in, false);
			in.close();
			// note use of tru to flip texture V co-ords for max OBJ files
			in = Gdx.files.internal("data/forcefield.obj").read();
			forcefieldMesh = ObjLoader.loadObj(in, true);
			in.close();
			break;
		default:
			in = null;

		}

		return in;
	}

	/*
	 * private ArrayList<DecalSprite> getDusts() { for( int i=0; i<10;i++){
	 * DecalSprite dust=new DecalSprite().build("data/dust.png");
	 * dust.sprite.setDimensions(10,10); dust.sprite.setPosition(5,5,5);
	 * dust.faceCamera(camera); dusts.add(dust); } return dusts; }
	 */

	public void render(Application app, Simulation simulation) {
		if (Ship.STATUS == 1) {
			renderSolarSystem(app, simulation);
		} else {
			renderInsideStation(app, simulation);
		}
	}

	private void renderSolarSystem(Application app, Simulation simulation) {
		GL10 gl = app.getGraphics().getGL10();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glViewport(0, 0, app.getGraphics().getWidth(), app.getGraphics()
				.getHeight());

		// renderBackground(gl);

		gl.glDisable(GL10.GL_DITHER);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_CULL_FACE);

		setProjectionAndCamera(app.getGraphics(), Simulation.ship, app, gl);
		setLighting(gl);

		gl.glEnable(GL10.GL_TEXTURE_2D);
		// renderRoids(gl);
		// camera.unproject(GameLoop.touchPoint.set(Gdx.input.getX(),
		// Gdx.input.getY(), 0));

		// render earth
		// renderPlanet(gl, "earth", true, 63780f, 200000, 0, 50000);
		// render mars
		renderPlanet(gl, "sun", true, 33970, 0, 0, 50000);
		// render sun
		renderSun(gl, 69550f, 0, 0, -500000, app);
		// render jupiter
		// renderPlanet(gl, "earth", false, 714920f, -4000000, 0, 50000);
		// render moon
		// renderMoon(gl, 17370f, 100000, 0, -50000);

		// renderDrones(gl, simulation.drones, app, Simulation.ship);
		renderSky(gl, Simulation.ship, app);
		renderActors(gl);

		// do alpha models after this

		// render station
		renderStation(gl, "station01", true, 1000f, 0, 0, 0);
		// render myship
		renderShip(gl, Simulation.ship, BattleBeasties3d.myCharacter.getShipname());
		renderDusts(gl, Simulation.dusts);
		renderShots(gl, simulation.shots);
		renderMissiles(gl, simulation.missiles);
		// renderTrails(gl, simulation.trails);
		gl.glDisable(GL10.GL_TEXTURE_2D);
		// renderBlocks(gl, simulation.blocks);

		// gl.glDisable(GL10.GL_LIGHTING);

		gl.glEnable(GL10.GL_TEXTURE_2D);
		renderExplosions(gl, Simulation.explosions);

		renderHud(simulation, gl);
		stationAngle += app.getGraphics().getDeltaTime() * 1;
		if (stationAngle > 360)
			stationAngle -= 360;

		invaderAngle += app.getGraphics().getDeltaTime() * 10;
		if (invaderAngle > 360)
			invaderAngle -= 360;
	}

	/**
	 * @param simulation
	 * @param gl
	 */
	private void renderHud(Simulation simulation, GL10 gl) {
		float textlength=0;
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glDisable(GL10.GL_DEPTH_TEST);
		spriteBatch.enableBlending();
		spriteBatch.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		spriteBatch.begin();
		//draw turn indicator
		if (Simulation.turn==0){
		 textlength = fontnormal.getBounds("Your Turn"
				).width;
		fontnormal.draw(spriteBatch, "Your Turn"
				, (width / 2)
				- (textlength / 2), ((height / 4)*3) + 34);
		}else{
			 textlength = fontnormal.getBounds("Opponent Turn"
						).width;
				fontnormal.draw(spriteBatch, "Opponent Turn"
						, (width / 2)
						- (textlength / 2), ((height / 4)*3) + 34);
			
		}
		//draw round indicator
		textlength = fontnormal.getBounds("Round "
				+ (Simulation.round)).width;
		fontnormal.draw(spriteBatch, "Round "
				+ (Simulation.round), (width / 2)
				- (textlength / 2), ((height / 4)*3) + 64);
		
		fontnormal.draw(spriteBatch, Simulation.currentBeastieName
				, (width / 4)
				, ((height / 5)*4) + 64);
		fontnormal.draw(spriteBatch, Simulation.opponentCurrentBeastieName
				, (width / 4)*3
				, ((height / 5)*4) + 64);
		
		if (Ship.STATUS == 1) {
			hurtflash(gl);
			renderReticules(simulation);
			if (Ship.docking) {
				 textlength = fontnormal.getBounds("Docking in "
						+ (5 - (Simulation.docktick / 10))).width;
				fontnormal.draw(spriteBatch, "Docking in "
						+ (5 - (Simulation.docktick / 10)), (width / 2)
						- (textlength / 2), (height / 2) + 64);
			}

		} else {
			if (Ship.undocking) {
				 textlength = fontnormal.getBounds("Undocking in "
						+ (4 - (Simulation.docktick / 10))).width;
				fontnormal.draw(spriteBatch, "Undocking in "
						+ (4 - (Simulation.docktick / 10)), (width / 2)
						- (textlength / 2), (height / 2) + 64);
			}

		}

		// GameLoop.touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);

		spriteBatch.enableBlending();
		spriteBatch.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		if (GameLoop.androidVersion != 0) {
			renderButtonsAndroid(simulation);
		} else {
			renderButtonsPC(simulation);
		}

		renderChat();

		renderScreens();
		spriteBatch.end();

		spriteBatch.begin();
		spriteBatch.enableBlending();
		spriteBatch.setBlendFunction(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
		spriteBatch.end();
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		if (BattleBeasties3d.DEBUG)
			Table.drawDebug(stage);
	}

	/**
	 * @param simulation
	 */
	private void renderButtonsPC(Simulation simulation) {
		if(Simulation.turn ==0){
			spriteBatch.setColor(Color.GREEN);
		}else{
			spriteBatch.setColor(Color.RED);	
		}
		spriteBatch.draw(mapTexture, 800 - 64, 480 - 64, 64, 64);
		
			spriteBatch.draw(dockTexture, 800 - 64, 480 - 64 - 64, 64, 64);
		
		// slidemenu
		spriteBatch
				.draw(pulloutTexture, simulation.slideOut, 256 + 128, 64, 64);
		// if(simulation.pullout=true){
		spriteBatch.draw(logTexture, simulation.slideOut - 64, 256 + 128, 64,
				64);
		spriteBatch.draw(exitTexture, simulation.slideOut
				- (simulation.iconWidth * 2), 256 + 128, 64, 64);
		spriteBatch.draw(optionsTexture, simulation.slideOut
				- (simulation.iconWidth * 3), 256 + 128, 64, 64);

		spriteBatch.draw(helpTexture, simulation.slideOut
				- (simulation.iconWidth * 4), 256 + 128, 64, 64);
		spriteBatch.draw(cashTexture, simulation.slideOut
				- (simulation.iconWidth * 5), 256 + 128, 64, 64);
		spriteBatch.draw(characterTexture, simulation.slideOut
				- (simulation.iconWidth * 6), 256 + 128, 64, 64);
		spriteBatch.draw(shipiconTexture, simulation.slideOut
				- (simulation.iconWidth * 7), 256 + 128, 64, 64);
		spriteBatch.draw(inventoryTexture, simulation.slideOut
				- (simulation.iconWidth * 8), 256 + 128, 64, 64);
		spriteBatch.draw(chatTexture, simulation.slideOut
				- (simulation.iconWidth * 9), 256 + 128, 64, 64);
		// }

		if (Ship.STATUS == 1) {
			spriteBatch.draw(swapTexture, 0, 0, 64 * GameLoop.screenScaleX,
					64 * GameLoop.screenScaleY);
			spriteBatch.draw(surrenderTexture, 0, 64, 64 * GameLoop.screenScaleX,
					64 * GameLoop.screenScaleY);
			spriteBatch.draw(repairTexture, 800 - 64, 128, 64, 64);
			spriteBatch.draw(missileTexture, 800 - 64, 64, 64, 64);
			spriteBatch.draw(shootTexture, 800 - 64, 0, 64, 64);
			renderCD(Simulation.ship.mainGunCD, 800 - 64, 0);
			renderCD(Simulation.ship.turretCD, 64 - 10, 0);
			renderCD(Simulation.ship.repairCD, 800 - 64, 128);
			renderCD(Simulation.ship.missileCD, 800 - 64, 64);
			spriteBatch.draw(speedoTexture, 0, 128, 64, 256, 0, 1, .25f, 0);

			speedGauge();
			powerGauge();
			healthGauge();

			/*
			 * speedGaugeAndroid(); powerGaugeAndroid(); healthGaugeAndroid();
			 */
		} else {
			spriteBatch.draw(missionTexture, 800 - 64, 128, 64, 64);
			spriteBatch.draw(vendorTexture, 800 - 64, 64, 64, 64);
			spriteBatch.draw(buildTexture, 800 - 64, 0, 64, 64);
			spriteBatch.draw(thumbControlTexture, 0, 0, 128, 128);

		}
		expGauge();
	}

	/**
	 * @param simulation
	 */
	private void renderButtonsAndroid(Simulation simulation) {
		spriteBatch.draw(mapTexture, (800 - 64) * GameLoop.screenScaleX,
				(480 - 64) * GameLoop.screenScaleY, 64 * GameLoop.screenScaleX,
				64 * GameLoop.screenScaleY);
		if (Ship.canDock == true || Ship.STATUS == 0) {
			spriteBatch.draw(dockTexture, (800 - 64) * GameLoop.screenScaleX,
					(480 - 64 - 64) * GameLoop.screenScaleY,
					64 * GameLoop.screenScaleX, 64 * GameLoop.screenScaleY);
		}
		// slidemenu
		spriteBatch.draw(pulloutTexture, simulation.slideOut
				* GameLoop.screenScaleX, (256 + 128) * GameLoop.screenScaleY,
				64 * GameLoop.screenScaleX, 64 * GameLoop.screenScaleY);
		// if(simulation.pullout=true){
		spriteBatch.draw(logTexture, (simulation.slideOut - 64)
				* GameLoop.screenScaleX, (256 + 128) * GameLoop.screenScaleY,
				64 * GameLoop.screenScaleX, 64 * GameLoop.screenScaleY);
		spriteBatch.draw(exitTexture,
				(simulation.slideOut - (simulation.iconWidth * 2))
						* GameLoop.screenScaleX, (256 + 128)
						* GameLoop.screenScaleY, 64 * GameLoop.screenScaleX,
				64 * GameLoop.screenScaleY);
		spriteBatch.draw(optionsTexture,
				(simulation.slideOut - (simulation.iconWidth * 3))
						* GameLoop.screenScaleX, (256 + 128)
						* GameLoop.screenScaleY, 64 * GameLoop.screenScaleX,
				64 * GameLoop.screenScaleY);

		spriteBatch.draw(helpTexture,
				(simulation.slideOut - (simulation.iconWidth * 4))
						* GameLoop.screenScaleX, (256 + 128)
						* GameLoop.screenScaleY, 64 * GameLoop.screenScaleX,
				64 * GameLoop.screenScaleY);
		spriteBatch.draw(cashTexture,
				(simulation.slideOut - (simulation.iconWidth * 5))
						* GameLoop.screenScaleX, (256 + 128)
						* GameLoop.screenScaleY, 64 * GameLoop.screenScaleX,
				64 * GameLoop.screenScaleY);
		spriteBatch.draw(characterTexture,
				(simulation.slideOut - (simulation.iconWidth * 6))
						* GameLoop.screenScaleX, (256 + 128)
						* GameLoop.screenScaleY, 64 * GameLoop.screenScaleX,
				64 * GameLoop.screenScaleY);
		spriteBatch.draw(shipiconTexture,
				(simulation.slideOut - (simulation.iconWidth * 7))
						* GameLoop.screenScaleX, (256 + 128)
						* GameLoop.screenScaleY, 64 * GameLoop.screenScaleX,
				64 * GameLoop.screenScaleY);
		spriteBatch.draw(inventoryTexture,
				(simulation.slideOut - (simulation.iconWidth * 8))
						* GameLoop.screenScaleX, (256 + 128)
						* GameLoop.screenScaleY, 64 * GameLoop.screenScaleX,
				64 * GameLoop.screenScaleY);
		spriteBatch.draw(chatTexture,
				(simulation.slideOut - (simulation.iconWidth * 9))
						* GameLoop.screenScaleX, (256 + 128)
						* GameLoop.screenScaleY, 64 * GameLoop.screenScaleX,
				64 * GameLoop.screenScaleY);
		// }
		// spriteBatch.draw(thumbControlTexture, 0, 0,
		// 128*GameLoop.screenScaleX, 128*GameLoop.screenScaleY);
		spriteBatch.draw(shootTexture, 0, 0, 64 * GameLoop.screenScaleX,
				64 * GameLoop.screenScaleY);
		if (Ship.STATUS == 1) {
			spriteBatch.draw(repairTexture, (800 - 64) * GameLoop.screenScaleX,
					128 * GameLoop.screenScaleY, 64 * GameLoop.screenScaleX,
					64 * GameLoop.screenScaleY);
			spriteBatch.draw(missileTexture,
					(800 - 64) * GameLoop.screenScaleX,
					64 * GameLoop.screenScaleY, 64 * GameLoop.screenScaleX,
					64 * GameLoop.screenScaleY);
			spriteBatch.draw(shootTexture, (800 - 64) * GameLoop.screenScaleX,
					0 * GameLoop.screenScaleY, 64 * GameLoop.screenScaleX,
					64 * GameLoop.screenScaleY);
			renderCD(Simulation.ship.mainGunCD,
					(int) ((800 - 64) * GameLoop.screenScaleX), 0);
			renderCD(Simulation.ship.repairCD,
					(int) ((800 - 64) * GameLoop.screenScaleX),
					(int) (128 * GameLoop.screenScaleY));
			renderCD(Simulation.ship.missileCD,
					(int) ((800 - 64) * GameLoop.screenScaleX),
					(int) (64 * GameLoop.screenScaleY));
			spriteBatch.draw(speedoTexture, 0 * GameLoop.screenScaleX,
					128 * GameLoop.screenScaleY, 64 * GameLoop.screenScaleX,
					256 * GameLoop.screenScaleY, 0, 1, .25f, 0);

			speedGaugeAndroid();
			powerGaugeAndroid();
			healthGaugeAndroid();
			expGaugeAndroid();
		} else {
			spriteBatch.draw(missionTexture,
					(800 - 64) * GameLoop.screenScaleX,
					128 * GameLoop.screenScaleY, 64 * GameLoop.screenScaleX,
					64 * GameLoop.screenScaleY);
			spriteBatch.draw(vendorTexture, (800 - 64) * GameLoop.screenScaleX,
					64 * GameLoop.screenScaleY, 64 * GameLoop.screenScaleX,
					64 * GameLoop.screenScaleY);
			spriteBatch.draw(buildTexture, (800 - 64) * GameLoop.screenScaleX,
					0 * GameLoop.screenScaleY, 64 * GameLoop.screenScaleX,
					64 * GameLoop.screenScaleY);
		}

	}

	/**
	 * 
	 */
	private void renderScreens() {

		if (BattleBeasties3d.stationScreen != 0) {
			// vendorscreen 1
			vendorScreen();
			// station inventory screen 2
			stationInventory();
			// space inventory screen 3
			spaceInventory();
			// chat screen 4
			chatScreen();
			// char screen 5
			charScreen();
			// ship screen 6
			shipScreen();
			// options screen 7
			optionsScreen();
			// helpscreen 8
			helpScreen();
			// marketscreen 9
			marketScreen();
			// missionscreen 10
			missionScreen();
			// factory 11
			factoryScreen();
			// map 12
			mapScreen();
			// missionlog 13
			logScreen();
		}
		// toasts
		if (BattleBeasties3d.Error == BattleBeasties3d.REPAIR_ERROR && toastShown == false) {
			System.out.println("toast called");
			stage.addActor(Actors.bottomToast(
					"You do not have any repair gloop to repair your ship.", 3,
					skin));

			toastShown = true;
		}
		if (BattleBeasties3d.Error == BattleBeasties3d.WIP_ERROR && toastShown == false) {
			System.out.println("toast called");
			stage.addActor(Actors
					.bottomToast("This does nothing yet!", 3, skin));

			toastShown = true;
		}
		if (BattleBeasties3d.Error == BattleBeasties3d.MISSILE_ERROR && toastShown == false) {
			System.out.println("toast called");
			stage.addActor(Actors.bottomToast(
					"You do not have any missile to launch.", 3, skin));

			toastShown = true;
		}
		if (BattleBeasties3d.Error == BattleBeasties3d.FULLHP_ERROR && toastShown == false) {
			System.out.println("toast called");
			stage.addActor(Actors.bottomToast(
					"Your ship is at full hitpoints.", 3, skin));

			toastShown = true;
		}
		if (BattleBeasties3d.Error == BattleBeasties3d.MISSILE_LOCK_ERROR
				&& toastShown == false) {
			System.out.println("toast called");
			stage.addActor(Actors.bottomToast("No Target Locked.", 3, skin));

			toastShown = true;
		}
		if (BattleBeasties3d.Error == BattleBeasties3d.RANGE_ERROR && toastShown == false) {
			System.out.println("toast called");
			stage.addActor(Actors.bottomToast("Target out of range.", 3, skin));

			toastShown = true;
		}
	}

	/**
	 * 
	 */
	private void factoryScreen() {
		if (BattleBeasties3d.stationScreen == 11) {
			factoryScreen = new FactoryScreen(stage);
			BattleBeasties3d.stationScreen = 211;
		}
		if (BattleBeasties3d.stationScreen == 211) {
			// screen updates here
		}
		if (BattleBeasties3d.stationScreen == 111) {
			stage.findActor("factoryWindow").remove();
			factoryScreen = null;
			BattleBeasties3d.stationScreen = 0;
		}
	}

	/**
	 * 
	 */
	private void missionScreen() {
		if (BattleBeasties3d.stationScreen == 10) {
			missionScreen = new MissionScreen(stage);
			BattleBeasties3d.stationScreen = 210;
		}
		if (BattleBeasties3d.stationScreen == 210) {
			((Label) Renderer.stage.findActor("missionlable"))
					.setText(BattleBeasties3d.missionDefs.get(
							missionScreen.currentMission).getTitle());
			((Label) Renderer.stage.findActor("missiontextlable"))
					.setText(BattleBeasties3d.missionDefs.get(
							missionScreen.currentMission).getText()
							+ "\nRequirements:-\n"
							+ BattleBeasties3d.missionDefs.get(
									missionScreen.currentMission)
									.getConditions()
							+ "\nRewards:-\n"
							+ BattleBeasties3d.missionDefs.get(
									missionScreen.currentMission).getRewards());
		}
		if (BattleBeasties3d.stationScreen == 110) {
			stage.findActor("missionWindow").remove();
			missionScreen = null;
			BattleBeasties3d.stationScreen = 0;
		}
	}

	/**
	 * 
	 */
	private void logScreen() {
		if (BattleBeasties3d.stationScreen == 13) {
			logScreen = new MissionLog(stage);
			BattleBeasties3d.stationScreen = 213;
		}
		if (BattleBeasties3d.stationScreen == 213) {
			// if we have some mission entries
			if (!BattleBeasties3d.myMissions.isEmpty()) {
				((Label) Renderer.stage.findActor("missioncounterlable"))
						.setText((logScreen.currentMission + 1) + " of "
								+ BattleBeasties3d.myMissions.size());
				((Label) Renderer.stage.findActor("missionlable"))
						.setText(BattleBeasties3d.myMissions.get(
								logScreen.currentMission).getTitle());
				((Label) Renderer.stage.findActor("missiontextlable"))
						.setText(BattleBeasties3d.myMissions.get(
								logScreen.currentMission).getText()
								+ "\nRequirements:-\n"
								+ BattleBeasties3d.myMissions.get(
										logScreen.currentMission)
										.getConditions()
								+ "\nRewards:-\n"
								+ BattleBeasties3d.myMissions.get(
										logScreen.currentMission).getRewards());
				((Label) Renderer.stage.findActor("negotiatorlable"))
						.setText("Negotiator: "
								+ BattleBeasties3d.myMissions.get(
										logScreen.currentMission)
										.getFirstname()
								+ " "
								+ BattleBeasties3d.myMissions.get(
										logScreen.currentMission).getSurname());
			} else {// if there are no missions in log
				((Label) Renderer.stage.findActor("missioncounterlable"))
						.setText((logScreen.currentMission + 1) + " of "
								+ BattleBeasties3d.myMissions.size());
				// if player is looking at completed missions
				if (logScreen.completeflag) {
					((Label) Renderer.stage.findActor("missionlable"))
							.setText("No Missions Completed");
				} else {// if player is looking at incomplete missions
					((Label) Renderer.stage.findActor("missionlable"))
							.setText("No Missions Accepted");
				}
				((Label) Renderer.stage.findActor("missiontextlable"))
						.setText("");
				((Label) Renderer.stage.findActor("negotiatorlable"))
						.setText("Negotiator:");
			}
		}
		if (BattleBeasties3d.stationScreen == 113) {
			stage.findActor("logWindow").remove();
			logScreen = null;
			BattleBeasties3d.stationScreen = 0;
		}
	}

	/**
	 * 
	 */
	private void helpScreen() {
		if (BattleBeasties3d.stationScreen == 8) {
			helpScreen = new InGameHelp(stage, BattleBeasties3d.helptext);
			BattleBeasties3d.stationScreen = 208;
		}
		if (BattleBeasties3d.stationScreen == 208) {
			// screen updates here
		}
		if (BattleBeasties3d.stationScreen == 108) {
			stage.findActor("helpWindow").remove();
			helpScreen = null;
			BattleBeasties3d.stationScreen = 0;
		}
	}

	/**
	 * 
	 */
	private void shipScreen() {
		if (BattleBeasties3d.stationScreen == 6) {
			shipScreen = new ShipScreen(stage);
			BattleBeasties3d.stationScreen = 206;
		}
		if (BattleBeasties3d.stationScreen == 206) {
			// screen updates here

		}
		if (BattleBeasties3d.stationScreen == 106) {
			stage.findActor("shipWindow").remove();
			shipScreen = null;
			BattleBeasties3d.stationScreen = 0;
		}
	}

	/**
	 * 
	 */
	private void mapScreen() {
		if (BattleBeasties3d.stationScreen == 12) {
			mapScreen = new MapScreen(stage);
			BattleBeasties3d.stationScreen = 212;
		}
		if (BattleBeasties3d.stationScreen == 212) {
			// screen updates here

		}
		if (BattleBeasties3d.stationScreen == 112) {
			stage.findActor("mapWindow").remove();
			mapScreen = null;
			BattleBeasties3d.stationScreen = 0;
		}
	}

	/**
	 * 
	 */
	private void optionsScreen() {
		if (BattleBeasties3d.stationScreen == 7) {
			optionsScreen = new InGameOptions(stage);
			BattleBeasties3d.stationScreen = 207;
		}
		if (BattleBeasties3d.stationScreen == 207) {
			((CheckBox) stage.findActor("checkboxfont"))
					.setChecked(BattleBeasties3d.tinyfont);

		}
		if (BattleBeasties3d.stationScreen == 107) {
			stage.findActor("optionsWindow").remove();
			optionsScreen = null;
			BattleBeasties3d.stationScreen = 0;
		}
	}

	/**
	 * 
	 */
	private void charScreen() {
		if (BattleBeasties3d.stationScreen == 5) {
			charScreen = new Char(stage);
			BattleBeasties3d.stationScreen = 205;
		}
		if (BattleBeasties3d.stationScreen == 205) {
			String PrimaryStat = "Stats: " + "\n\nSta "
					+ BattleBeasties3d.myCharacter.getStamina() + "\nInt "
					+ BattleBeasties3d.myCharacter.getIntelligence() + "\nSoc "
					+ BattleBeasties3d.myCharacter.getSocial() + "\nDex "
					+ BattleBeasties3d.myCharacter.getDexterity() + "\nLed "
					+ BattleBeasties3d.myCharacter.getLeadership() + "\nRec "
					+ BattleBeasties3d.myCharacter.getRecuperation() + "\n\nExp "
					+ BattleBeasties3d.myCharacter.getExp() + "\n$D "
					+ BattleBeasties3d.myCharacter.getCredits()
					+ "                     ";
			String BonusStat = "Bonuses:" + "\n\n"
					+ BattleBeasties3d.myCharacter.getStamina() + "\n"
					+ BattleBeasties3d.myCharacter.getIntelligence() + "\n"
					+ BattleBeasties3d.myCharacter.getSocial() + "\n"
					+ BattleBeasties3d.myCharacter.getDexterity() + "\n"
					+ BattleBeasties3d.myCharacter.getLeadership() + "\n"
					+ BattleBeasties3d.myCharacter.getRecuperation() + "\n\nSystem: "
					+ BattleBeasties3d.myCharacter.getSystem() + "\nShip: "
					+ Util.asCapFirstChar(BattleBeasties3d.myCharacter.getShipname());
			((Label) stage.findActor("primary-stat")).setText(PrimaryStat);
			((Label) stage.findActor("bonus-stat")).setColor(0, 1, 0, 1);
			((Label) stage.findActor("bonus-stat")).setText(BonusStat);

		}
		if (BattleBeasties3d.stationScreen == 105) {
			stage.findActor("charWindow").remove();
			charScreen = null;
			BattleBeasties3d.stationScreen = 0;
		}
	}

	/**
	 * 
	 */
	private void chatScreen() {
		if (BattleBeasties3d.stationScreen == 4) {
			chatScreen = new Chat(BattleBeasties3d.mchatlines, stage);
			BattleBeasties3d.stationScreen = 204;
		}
		if (BattleBeasties3d.stationScreen == 204) {
			((Label) stage.findActor("labelchat"))
					.setText(BattleBeasties3d.mchatlines);

		}
		if (BattleBeasties3d.stationScreen == 104) {
			stage.findActor("chatWindow").remove();
			chatScreen = null;
			BattleBeasties3d.stationScreen = 0;
		}
	}

	/**
	 * 
	 */
	private void spaceInventory() {
		if (BattleBeasties3d.stationScreen == 3) {
			spaceInventoryScreen = new SpaceInventory(stage);
			BattleBeasties3d.stationScreen = 203;
		}
		if (BattleBeasties3d.stationScreen == 203) {
			// insert screen updates here
		}
		if (BattleBeasties3d.stationScreen == 103) {
			// stage.findActor("spaceInventoryWindow").remove();
			spaceInventoryScreen = null;
			BattleBeasties3d.stationScreen = 0;
		}
	}

	/**
	 * 
	 */
	private void stationInventory() {
		if (BattleBeasties3d.stationScreen == 2) {
			stationInventoryScreen = new StationInventory(stage);
			BattleBeasties3d.stationScreen = 202;
		}
		if (BattleBeasties3d.stationScreen == 202) {
			// insert screen updates here
		}
		if (BattleBeasties3d.stationScreen == 102) {
			// stage.findActor("stationInventoryWindow").remove();
			stationInventoryScreen = null;
			BattleBeasties3d.stationScreen = 0;
		}
	}

	/**
	 * 
	 */
	private void vendorScreen() {
		if (BattleBeasties3d.stationScreen == 1) {
			vendorScreen = new Vendor(stage);
			BattleBeasties3d.stationScreen = 201;
		}
		if (BattleBeasties3d.stationScreen == 201) {
			((Label) Renderer.stage.findActor("balancelable"))
					.setText(BattleBeasties3d.myCharacter.getCredits() + " $D");
		}
		if (BattleBeasties3d.stationScreen == 101) {
			// stage.findActor("vendorwindow").remove();
			vendorScreen = null;
			BattleBeasties3d.stationScreen = 0;
		}
	}

	private void bindTextures() {
		dustTexture = BattleBeasties3d.manager.get("data/dust.png", Texture.class);
		blackpixelTexture = BattleBeasties3d.manager.get("data/blackpixel.png",
				Texture.class);
		BattleBeasties3d.manager.get("data/planet.jpg", Texture.class).setFilter(
				TextureFilter.Linear, TextureFilter.Linear);
		planetTexture = BattleBeasties3d.manager
				.get("data/planet.jpg", Texture.class);
		BattleBeasties3d.manager.get("data/squirrel.png", Texture.class).setFilter(
				TextureFilter.Linear, TextureFilter.Linear);
		squirrelTexture = BattleBeasties3d.manager.get("data/squirrel.png",
				Texture.class);
		planetTexture02 = BattleBeasties3d.manager.get("data/planet02.jpg",
				Texture.class);
		BattleBeasties3d.manager.get("data/sky01.jpg", Texture.class).setFilter(
				TextureFilter.Linear, TextureFilter.Linear);
		lvlupTexture = BattleBeasties3d.manager.get("data/lvlup.jpg", Texture.class);
		skyTexture = BattleBeasties3d.manager.get("data/sky01.jpg", Texture.class);
		shipTexture01 = BattleBeasties3d.manager.get("data/destroyer01.png",
				Texture.class);
		BattleBeasties3d.manager.get("data/nova.png", Texture.class);
		jetTexture = BattleBeasties3d.manager.get("data/jet.png", Texture.class);
		moonTexture = BattleBeasties3d.manager.get("data/craters.jpg", Texture.class);
		shipTexture02 = BattleBeasties3d.manager.get("data/destroyer02.png",
				Texture.class);
		shipTexture03 = BattleBeasties3d.manager.get("data/ship.png", Texture.class);
		hurtflash = BattleBeasties3d.manager.get("data/hurtflash.png", Texture.class);
		swirlyTexture = BattleBeasties3d.manager.get("data/swirlyglow.png",
				Texture.class);
		shieldDisruptorTexture = BattleBeasties3d.manager.get(
				"data/shielddisruptor.png", Texture.class);
		explosionTexture = BattleBeasties3d.manager.get("data/explode.png",
				Texture.class);
		BattleBeasties3d.manager.get("data/explodewave.png", Texture.class)
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		swansongTexture = BattleBeasties3d.manager.get("data/explodewave.jpg",
				Texture.class);
		roidTexture = BattleBeasties3d.manager.get("data/roid01tex.png",
				Texture.class);
		hangarTexture = BattleBeasties3d.manager.get("data/hangar01.png",
				Texture.class);
		BattleBeasties3d.manager.get("data/grid.png", Texture.class).setFilter(
				TextureFilter.Linear, TextureFilter.Linear);
		gridTexture = BattleBeasties3d.manager.get("data/grid.png", Texture.class);
		// hud sprites
		mapTexture = BattleBeasties3d.manager.get("data/iconmap.png", Texture.class);
		thumbControlTexture = BattleBeasties3d.manager.get("data/camcontrol.png",
				Texture.class);
		shootTexture = BattleBeasties3d.manager.get("data/iconshoot.png",
				Texture.class);
		missileTexture = BattleBeasties3d.manager.get("data/iconmissile.png",
				Texture.class);
		dockTexture = BattleBeasties3d.manager
				.get("data/icondock.png", Texture.class);
		pulloutTexture = BattleBeasties3d.manager.get("data/iconpullout.png",
				Texture.class);
		stationTexture = BattleBeasties3d.manager.get("data/station01.png",
				Texture.class);
		repairTexture = BattleBeasties3d.manager.get("data/iconrepair.png",
				Texture.class);
		speedoTexture = BattleBeasties3d.manager
				.get("data/speedo.png", Texture.class);
		helpTexture = BattleBeasties3d.manager
				.get("data/iconhelp.png", Texture.class);
		characterTexture = BattleBeasties3d.manager.get("data/iconcharscreen.png",
				Texture.class);
		exitTexture = BattleBeasties3d.manager
				.get("data/iconexit.png", Texture.class);
		swapTexture = BattleBeasties3d.manager
				.get("data/iconsearch.png", Texture.class);
		logTexture = BattleBeasties3d.manager.get("data/iconlog.png", Texture.class);
		shipiconTexture = BattleBeasties3d.manager.get("data/iconship.png",
				Texture.class);
		inventoryTexture = BattleBeasties3d.manager.get("data/iconinventory.png",
				Texture.class);
		missionTexture = BattleBeasties3d.manager.get("data/iconmission.png",
				Texture.class);
		vendorTexture = BattleBeasties3d.manager.get("data/iconvendor.png",
				Texture.class);
		buildTexture = BattleBeasties3d.manager.get("data/iconbuild.png",
				Texture.class);
		cashTexture = BattleBeasties3d.manager.get("data/iconmarket.png",
				Texture.class);
		optionsTexture = BattleBeasties3d.manager.get("data/iconsetup.png",
				Texture.class);
		surrenderTexture = BattleBeasties3d.manager.get("data/iconmission.png",
				Texture.class);
		reticuleTexture = BattleBeasties3d.manager.get("data/reticule.png",
				Texture.class);
		chatTexture = BattleBeasties3d.manager
				.get("data/iconchat.png", Texture.class);
		gaugetop = new TextureRegion(speedoTexture, 64, 0, 16, 16);
		gaugemiddle = new Sprite(speedoTexture, 64, 16, 16, 32);
		gaugebottom = new TextureRegion(speedoTexture, 64, 48, 16, 16);

	}

	private void hurtflash(GL10 gl) {
		// if simulation says hurtflash is active.
		if (Simulation.hurtflash > 0) {
			// set alpha
			spriteBatch.setColor(1, 1, 1, Simulation.hurtflash);
			spriteBatch.draw(hurtflash, 0, 0, width, height);
			spriteBatch.setColor(1, 1, 1, 1);
		}

	}

	private void renderCD(int cooldown, int x, int y) {

		String message = Integer.toString(cooldown);
		if (BattleBeasties3d.fontsize) {
			fonttiny.draw(spriteBatch, message, x, y + fonttiny.getCapHeight());
		} else {
			fontsmall.draw(spriteBatch, message, x,
					y + fontsmall.getCapHeight());
		}
	}

	private void renderChat() {
		if (BattleBeasties3d.fontsize) {

			if (BattleBeasties3d.DEBUG)
				statusFontCache.draw(spriteBatch);
			minichatFontCache.draw(spriteBatch);
		} else {

			statusFontCache.draw(spriteBatch);
			minichatFontCache.draw(spriteBatch);

		}

	}

	/**
	 * @param simulation
	 */
	private void renderReticules(Simulation simulation) {
		// spriteBatch.begin();
		// spriteBatch.enableBlending();
		// spriteBatch.setBlendFunction(GL10.GL_SRC_ALPHA,
		// GL10.GL_ONE_MINUS_SRC_ALPHA);
		String actorName = "PLAYER";

		// needs synchronisd list to prevent reading list when empty
		for (int i = 0; i < BattleBeasties3d.actorsList.size(); i++) {
			try {
				Actor actor = BattleBeasties3d.actorsList.get(i);
				if (actor != null && !(actor.shipname.equals("dead"))
						&& actor.getHitpoints() > 0) {
					// only do if actor is in space
					if (actor.visible) {

						actorName = actor.get_firstname() + "\n"
								+ actor.get_surname();
						spriteBatch.setColor(0, 1, 0, 1);
						if (actor.reticulepos != null
								&& actor.reticulepos.z < 1) {

							if ((actor.getShipname().equals("aiscout"))) {
								spriteBatch.setColor(1, 0, 0, 1);
								fonttiny.setColor(1.0f, 0.0f, 0.0f, 1.0f);
								fontsmall.setColor(1.0f, 0.0f, 0.0f, 1.0f);
							} else if (actor.getShipname().equals(
									"shielddisruptor")) {
								fontsmall.setColor(1.0f, 0.75f, 0.0f, 1.0f);
								spriteBatch.setColor(1, 0.75f, 0, 1);

							} else {
								spriteBatch.setColor(0, 1, 0, 1);
							}

							if (actor.locked) {
								spriteBatch.draw(reticuleTexture,
										actor.reticulepos.x,
										actor.reticulepos.y);
							}

							for (int j = 0; j < actorNames.size(); j++) {
								// System.out.println("curr actor"+ actorName +
								// " fontcache "
								// + actorNames.get(j).getName());
								if (actorName.equals(actorNames.get(j)
										.getName())) {
									actorNames.get(j).cache.setPosition(
											actor.reticulepos.x,
											actor.reticulepos.y+60);
									actorNames.get(j).cache.draw(spriteBatch);
								}
								/*
								 * actorNames.get(j).cache.setPosition( 200,
								 * 200+(j*20));
								 * actorNames.get(j).cache.draw(spriteBatch);
								 */
							}

					/*		if (actor.cache != null) {
								actor.cache.setPosition(
										actor.reticulepos.x,
										actor.reticulepos.y + 80
												+ fonttiny.getCapHeight());
								actor.cache.draw(spriteBatch);
							}*/
						}

					}
				}

			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i < BattleBeasties3d.actorsList.size(); i++) {

			Actor actor = BattleBeasties3d.actorsList.get(i);
			if (actor != null && !(actor.shipname.equals("dead"))
					&& actor.getHitpoints() > 0) {
				// only do if actor is in space
				if (actor.visible) {
					if (actor.reticulepos != null && actor.reticulepos.z < 1) {

						if ((actor.getShipname().equals("aiscout"))) {
							spriteBatch.setColor(1, 0, 0, 1);

						} else if (actor.getShipname()
								.equals("shielddisruptor")) {

							spriteBatch.setColor(1, 0.75f, 0, 1);
						} else {
							spriteBatch.setColor(0, 1, 0, 1);
						}
						spriteBatch.draw(blackpixelTexture,
								actor.reticulepos.x, actor.reticulepos.y+60,
								actor.getHealthBar(), 2);

					}
				}
			}
		}
		fonttiny.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		fontsmall.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		spriteBatch.setColor(1, 1, 1, 1);
	}

	/**
	 * 
	 */
	private void speedGauge() {
		// normal gauge in red
		spriteBatch.setColor(Simulation.speedGauge, 1 - Simulation.speedGauge,
				0, 1);
		// top of gauge
		spriteBatch.draw(gaugetop, 14, 160 + 16 + Simulation.speedGauge * 160);
		// middle of gauge
		spriteBatch.draw(gaugemiddle, 14, 160 + 16, 16,
				Simulation.speedGauge * 160);
		// top of gauge

		spriteBatch.draw(gaugebottom, 14, 160);

	}

	private void powerGauge() {
		spriteBatch.setColor(1 - Simulation.powerGauge, Simulation.powerGauge,
				0, 1);
		// top of gauge

		spriteBatch.draw(gaugetop, 24, 160 + 16 + Simulation.powerGauge * 160);
		// middle of gauge

		spriteBatch.draw(gaugemiddle, 24, 160 + 16, 16,
				Simulation.powerGauge * 160);
		// top of gauge

		spriteBatch.draw(gaugebottom, 24, 160);
	}

	private void expGauge() {
		// exp background
		spriteBatch.setColor(.5f, .5f, .5f, .5f);
		// top of gauge
		spriteBatch.draw(gaugetop, 128, 64, 0, 0, 16, 16, 1, 1, 90);
		// middle of gauge
		spriteBatch.draw(gaugemiddle, 128, 64, 0, 0, 16, -576, 1, 1, 90);
		// bottom of gauge
		spriteBatch
				.draw(gaugebottom, 800 - 64 - 16, 64, 0, 0, 16, 16, 1, 1, 90);

		// exp rank markers
		spriteBatch.setColor(1f, 1f, 1f, .5f);
		for (int i = 0; i < 11; i++) {
			// middle of gauge
			spriteBatch.draw(gaugemiddle, 128 + (i * 57.5f), 60, 0, 0, 24, 1,
					1, 1, 90);
		}
		// exp background
		spriteBatch.setColor(.0f, .0f, .5f, .5f);
		// top of gauge
		spriteBatch.draw(gaugetop, 128, 64, 0, 0, 16, 16, 1, 1, 90);
		// middle of gauge
		spriteBatch.draw(gaugemiddle, 128, 64, 0, 0, 16, -Simulation.exp, 1, 1,
				90);
		// Stardust3d.myCharacter.getExp()/100
		// bottom of gauge
		spriteBatch.draw(gaugebottom, 128 + Simulation.exp + 16, 64, 0, 0, 16,
				16, 1, 1, 90);

		spriteBatch.setColor(1, 1, 1, 1);
	}

	private void expGaugeAndroid() {
		// exp background
		spriteBatch.setColor(.5f, .5f, .5f, .5f);
		// top of gauge
		spriteBatch.draw(gaugetop, 128 * GameLoop.screenScaleX, 64, 0, 0, 16,
				16, 1, 1, 90);
		// middle of gauge
		spriteBatch.draw(gaugemiddle, 128 * GameLoop.screenScaleX, 64, 0, 0,
				16, -(width - 64 - 16), 1, 1, 90);
		// bottom of gauge
		spriteBatch.draw(gaugebottom, width - 64 - 16, 64, 0, 0, 16, 16, 1, 1,
				90);

		// exp rank markers
		spriteBatch.setColor(1f, 1f, 1f, .5f);
		for (int i = 0; i < 11; i++) {
			// middle of gauge
			spriteBatch.draw(gaugemiddle, 128 + (i * 57.5f), 60, 0, 0, 24, 1,
					1, 1, 90);
		}
		// exp background
		spriteBatch.setColor(.0f, .0f, .5f, .5f);
		// top of gauge
		spriteBatch.draw(gaugetop, 128 * GameLoop.screenScaleX, 64, 0, 0, 16,
				16, 1, 1, 90);
		// middle of gauge
		spriteBatch.draw(gaugemiddle, 128 * GameLoop.screenScaleX, 64, 0, 0,
				16, -Simulation.exp, 1, 1, 90);
		// Stardust3d.myCharacter.getExp()/100
		// bottom of gauge
		spriteBatch.draw(gaugebottom, 128 + Simulation.exp + 16, 64, 0, 0, 16,
				16, 1, 1, 90);

		spriteBatch.setColor(1, 1, 1, 1);
	}

	private void healthGauge() {

		spriteBatch.setColor(1 - Simulation.healthGauge,
				Simulation.healthGauge, 0, 1);

		// top of gauge
		spriteBatch.draw(gaugetop, 34, 160 + 16 + Simulation.healthGauge * 160);
		// middle of gauge
		spriteBatch.draw(gaugemiddle, 34, 160 + 16, 16,
				Simulation.healthGauge * 160);
		// top of gauge

		spriteBatch.draw(gaugebottom, 34, 160);

		spriteBatch.setColor(1, 1, 1, 1);
		/*
		 * void com.badlogic.gdx.graphics.g2d.SpriteBatch.draw (Texture texture,
		 * x, y, width, height, u, v, u2, v2) Draws a rectangle with the bottom
		 * left corner at x,y having the given width and height in pixels. The
		 * portion of the Texture given by u, v and u2, v2 are used. These
		 * coordinates and sizes are given in texture size percentage. The
		 * rectangle will have the given tint Color.
		 * 
		 * Parameters: texture the Texture x the x-coordinate in screen space y
		 * the y-coordinate in screen space width the width in pixels height the
		 * height in pixels u v u2 v2
		 */
	}

	private void speedGaugeAndroid() {
		// normal gauge in red
		spriteBatch.setColor(Simulation.speedGauge, 1 - Simulation.speedGauge,
				0, 1);
		// top of gauge
		spriteBatch.draw(gaugetop, 14 * GameLoop.screenScaleX,
				(160 + 16 + Simulation.speedGauge * 160)
						* GameLoop.screenScaleY);
		// middle of gauge
		spriteBatch.draw(gaugemiddle, 14 * GameLoop.screenScaleX, (160 + 16)
				* GameLoop.screenScaleY, 16, (Simulation.speedGauge * 160)
				* GameLoop.screenScaleY);
		// top of gauge

		spriteBatch.draw(gaugebottom, 14 * GameLoop.screenScaleX,
				160 * GameLoop.screenScaleY);

	}

	private void powerGaugeAndroid() {
		spriteBatch.setColor(1 - Simulation.powerGauge, Simulation.powerGauge,
				0, 1);
		// top of gauge

		spriteBatch.draw(gaugetop, 24 * GameLoop.screenScaleX,
				(160 + 16 + Simulation.powerGauge * 160)
						* GameLoop.screenScaleY);
		// middle of gauge

		spriteBatch.draw(gaugemiddle, 24 * GameLoop.screenScaleX, (160 + 16)
				* GameLoop.screenScaleY, 16, Simulation.powerGauge * 160
				* GameLoop.screenScaleY);
		// top of gauge

		spriteBatch.draw(gaugebottom, 24 * GameLoop.screenScaleX,
				160 * GameLoop.screenScaleY);
	}

	private void healthGaugeAndroid() {

		spriteBatch.setColor(1 - Simulation.healthGauge,
				Simulation.healthGauge, 0, 1);

		// top of gauge
		spriteBatch.draw(gaugetop, 34 * GameLoop.screenScaleX,
				(160 + 16 + Simulation.healthGauge * 160)
						* GameLoop.screenScaleY);
		// middle of gauge
		spriteBatch.draw(gaugemiddle, 34 * GameLoop.screenScaleX, (160 + 16)
				* GameLoop.screenScaleY, 16, Simulation.healthGauge * 160);
		// top of gauge

		spriteBatch.draw(gaugebottom, 34 * GameLoop.screenScaleX,
				160 * GameLoop.screenScaleY);

		spriteBatch.setColor(1, 1, 1, 1);
		/*
		 * void com.badlogic.gdx.graphics.g2d.SpriteBatch.draw (Texture texture,
		 * x, y, width, height, u, v, u2, v2) Draws a rectangle with the bottom
		 * left corner at x,y having the given width and height in pixels. The
		 * portion of the Texture given by u, v and u2, v2 are used. These
		 * coordinates and sizes are given in texture size percentage. The
		 * rectangle will have the given tint Color.
		 * 
		 * Parameters: texture the Texture x the x-coordinate in screen space y
		 * the y-coordinate in screen space width the width in pixels height the
		 * height in pixels u v u2 v2
		 */
	}

	@SuppressWarnings("unused")
	private void renderRoids(GL10 gl) {
		for (int i = -6; i < 6; i++) {
			gl.glPushMatrix();
			gl.glTranslatef(-30, -10, 0 + (i * 40));
			// gl.glScalef(.025f,0.025f,.025f);
			roidTexture.bind();
			roidMesh.render(GL10.GL_TRIANGLES);
			gl.glPopMatrix();
		}
	}

	private void renderInsideStation(Application app, Simulation simulation) {
		GL10 gl = app.getGraphics().getGL10();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glViewport(0, 0, app.getGraphics().getWidth(), app.getGraphics()
				.getHeight());
		gl.glDisable(GL10.GL_DITHER);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		setStaticProjectionAndCamera(app.getGraphics(), app, gl);
		setLighting(gl);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		renderStaticShip(gl, Simulation.ship, app,
				BattleBeasties3d.myCharacter.getShipname());
		// renderDrones(gl, simulation.drones, app, Simulation.ship);
		gl.glDisable(GL10.GL_DITHER);

		gl.glEnable(GL10.GL_TEXTURE_2D);
		// do alpha models after this

		renderHangar(gl, Simulation.ship, app);
		gl.glDisable(GL10.GL_TEXTURE_2D);
		renderHud(simulation, gl);

	}

	final Vector3 dir = new Vector3();

	private void setProjectionAndCamera(Graphics graphics, Ship ship,
			Application app, GL10 gl) {
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		// x=left-right, y=up-down, z=back-forward

		camera.far = 1000000;
		// camera.near=0.01f;
		Vector3 heading = new Vector3(0, 0, -1);
		Ship.shipRot.transform(heading);
		camera.direction.set(heading.x, heading.y, heading.z);
		camera.fieldOfView = 67;
		camera.position.set(Ship.position.x, 20f + Ship.position.y,
				20f + Ship.position.z);

		float orbitRadius = 30f;
		camera.rotate(GameLoop.cameraHorizAngle, 0, 1, 0);
		Vector3 orbitReturnVector = new Vector3(0, 0, 0);
		orbitReturnVector.set(camera.direction.tmp().mul(-orbitRadius));
		camera.translate(orbitReturnVector.x, orbitReturnVector.y,
				orbitReturnVector.z);
		camera.position.set(Ship.position);
		orbitReturnVector = new Vector3(0, 0, 0);
		orbitReturnVector.set(camera.direction.tmp().mul(-orbitRadius));
		camera.translate(orbitReturnVector.x, orbitReturnVector.y,
				orbitReturnVector.z);
		if (Ship.pitchAngle > 90 && Ship.pitchAngle < 270) {
			camera.up.set(0, -1, 0);
		} else {
			camera.up.set(0, 1, 0);
		}
		camera.up.nor();
		camera.translate(0, GameLoop.cameraVertAngle, 0);
		camera.lookAt(Ship.position.x, Ship.position.y, Ship.position.z);
		camera.update();
		camera.apply(gl);
		Simulation.camera = camera.position;
	}

	private void setStaticProjectionAndCamera(Graphics graphics,
			Application app, GL10 gl) {
		gl.glMatrixMode(GL10.GL_MODELVIEW);

		gl.glLoadIdentity();
		// x=left-right, y=up-down, z=back-forward
		camera.far = 1000;
		// camera.near=0.01f;
		Vector3 heading = new Vector3(0, 0, -1);
		camera.direction.set(heading.x, heading.y, heading.z);
		camera.fieldOfView = 67;
		camera.position.set(0, 4f + 0, 9f + 0);
		float orbitRadius = 18f;
		camera.rotate(GameLoop.cameraHorizAngle, 0, 1, 0);
		Vector3 orbitReturnVector = new Vector3(0, 0, 0);
		orbitReturnVector.set(camera.direction.tmp().mul(-orbitRadius));
		camera.translate(orbitReturnVector.x, orbitReturnVector.y,
				orbitReturnVector.z);
		camera.position.set(new Vector3(0, 0, 0));
		orbitReturnVector = new Vector3(0, 0, 0);
		orbitReturnVector.set(camera.direction.tmp().mul(-orbitRadius));
		camera.translate(orbitReturnVector.x, orbitReturnVector.y,
				orbitReturnVector.z);
		camera.up.set(0, 1, 0);
		camera.up.nor();
		camera.translate(0, ((float) (GameLoop.cameraVertAngle)) / 10, 0);
		camera.lookAt(0, 0, 0);
		camera.update();
		camera.apply(gl);
	}

	float[] direction = { 0, 0, 3, 1 };
	private Vector3 looking;

	private void setLighting(GL10 gl) {
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_LIGHT0);

		// gl.glEnable(GL10.GL_LIGHT2);
		// gl.glEnable(GL10.GL_LIGHT3);
		// gl.glEnable(GL10.GL_LIGHT1);
		// gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, direction, 1);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, new float[] { 0.25f,
				0.25f, 0.29f, 1f }, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, new float[] { 0.99f,
				0.99f, 0.79f, 1f }, 0);
		// bright yellow light
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, new float[] { .9f, .9f,
				1f, 1f }, 0);

		gl.glEnable(GL10.GL_COLOR_MATERIAL);

	}

	private void renderShip(GL10 gl, Ship ship, String shipname) {
		if (ship.isExploding)
			return;

		if (shipname.equals("stickleback")) {
			gl.glDisable(GL10.GL_CULL_FACE);
			shipTexture01.bind();
		} else if (shipname.equals("salx")) {
			shipTexture02.bind();

		} else if (shipname.equals("cynomys")) {
			shipTexture03.bind();
		} else if (shipname.equals("squirrel")) {
			gl.glDisable(GL10.GL_CULL_FACE);
			squirrelTexture.bind();
		}
		gl.glPushMatrix();
		gl.glTranslatef(Ship.position.x, Ship.position.y, Ship.position.z);
		gl.glDisable(GL10.GL_BLEND);
		gl.glRotatef(Ship.yawAngle, 0, 1, 0);
		gl.glRotatef(Ship.pitchAngle, 1, 0, 0);
		if (shipname.equals("stickleback")) {
			shipMesh01.render(GL10.GL_TRIANGLES);

		} else if (shipname.equals("salx")) {
			shipMesh02.render(GL10.GL_TRIANGLES);
		} else if (shipname.equals("cynomys")) {
			shipMesh03.render(GL10.GL_TRIANGLES);
		} else if (shipname.equals("squirrel")) {
			squirrelMesh.render(GL10.GL_TRIANGLES);
		}
		renderJets(gl, shipname, Ship.SHIP_VELOCITY);
		gl.glPopMatrix();
	}

	/**
	 * @param gl
	 * @param shipname
	 * @param velocity
	 */
	private void renderJets(GL10 gl, String shipname, float velocity) {
		float noise = (float) Math.random();
		float length = velocity / 50;
		gl.glEnable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
		jetTexture.bind();
		// gl.glPushMatrix();
		for (int i = 0; i < BattleBeasties3d.shipdefs.size(); i++) {
			String currentShipdef = BattleBeasties3d.shipdefs
					.get(i)
					.getModel()
					.substring(0,
							BattleBeasties3d.shipdefs.get(i).getModel().length() - 4);
			if (BattleBeasties3d.DEEPDEBUG)
				System.out.println("shipdef " + currentShipdef);
			if (currentShipdef.equals(shipname)) {
				if (BattleBeasties3d.shipdefs.get(i).getJet1x() != 99999) {
					gl.glTranslatef(BattleBeasties3d.shipdefs.get(i).getJet1x(),
							BattleBeasties3d.shipdefs.get(i).getJet1y(),
							BattleBeasties3d.shipdefs.get(i).getJet1z());
					gl.glScalef(2f, 2f, (1f + length + noise));
					jetMesh.render(GL10.GL_TRIANGLES);
					gl.glScalef(.5f, .5f, 1 / (1f + length + noise));
					gl.glTranslatef(-BattleBeasties3d.shipdefs.get(i).getJet1x(),
							-BattleBeasties3d.shipdefs.get(i).getJet1y(),
							-BattleBeasties3d.shipdefs.get(i).getJet1z());
					// gl.glScalef(1, 1, 1);
				}
				if (BattleBeasties3d.shipdefs.get(i).getJet2x() != 99999) {
					gl.glTranslatef(BattleBeasties3d.shipdefs.get(i).getJet2x(),
							BattleBeasties3d.shipdefs.get(i).getJet2y(),
							BattleBeasties3d.shipdefs.get(i).getJet2z());
					gl.glScalef(2f, 2f, (1f + length + noise));
					jetMesh.render(GL10.GL_TRIANGLES);
				}
				if (BattleBeasties3d.shipdefs.get(i).getJet3x() != 99999) {
					gl.glTranslatef(BattleBeasties3d.shipdefs.get(i).getJet3x(),
							BattleBeasties3d.shipdefs.get(i).getJet3y(),
							BattleBeasties3d.shipdefs.get(i).getJet3z());
					gl.glScalef(2f, 2f, (1f + length + noise));
					jetMesh.render(GL10.GL_TRIANGLES);
				}
				if (BattleBeasties3d.shipdefs.get(i).getJet4x() != 99999) {
					gl.glTranslatef(BattleBeasties3d.shipdefs.get(i).getJet4x(),
							BattleBeasties3d.shipdefs.get(i).getJet4y(),
							BattleBeasties3d.shipdefs.get(i).getJet4z());
					gl.glScalef(2f, 2f, (1f + length + noise));
					jetMesh.render(GL10.GL_TRIANGLES);
				}
				if (BattleBeasties3d.shipdefs.get(i).getJet5x() != 99999) {
					gl.glTranslatef(BattleBeasties3d.shipdefs.get(i).getJet5x(),
							BattleBeasties3d.shipdefs.get(i).getJet5y(),
							BattleBeasties3d.shipdefs.get(i).getJet5z());
					gl.glScalef(2f, 2f, (1f + length + noise));
					jetMesh.render(GL10.GL_TRIANGLES);
				}
			}
		}
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glDisable(GL10.GL_BLEND);
		gl.glEnable(GL10.GL_LIGHTING);
	}

	private void renderActorShip(GL10 gl, Actor actor, String shipname) {
		if (shipname.equals("stickleback")) {
			gl.glDisable(GL10.GL_CULL_FACE);
			shipTexture01.bind();
		} else if (shipname.equals("salx")) {
			shipTexture02.bind();
		} else if (shipname.equals("cynomys")) {
			shipTexture03.bind();
		} else if (shipname.equals("squirrel")) {
			gl.glDisable(GL10.GL_CULL_FACE);
			squirrelTexture.bind();
		}
		gl.glPushMatrix();
		gl.glDisable(GL10.GL_BLEND);
		gl.glRotatef(actor.yawangle, 0, 1, 0);
		gl.glRotatef(actor.pitchangle, 1, 0, 0);
		if (shipname.equals("stickleback")) {
			shipMesh01.render(GL10.GL_TRIANGLES);
		} else if (shipname.equals("salx")) {
			shipMesh02.render(GL10.GL_TRIANGLES);
		} else if (shipname.equals("cynomys")) {
			shipMesh03.render(GL10.GL_TRIANGLES);
		} else if (shipname.equals("squirrel")) {
			squirrelMesh.render(GL10.GL_TRIANGLES);
		}

		renderJets(gl, shipname, actor.velocity);
		gl.glPopMatrix();
	}

	private void renderStaticShip(GL10 gl, Ship ship, Application app,
			String shipname) {
		if (ship.isExploding)
			return;

		if (shipname.equals("stickleback")) {
			shipTexture01.bind();
		} else if (shipname.equals("salx")) {
			shipTexture02.bind();

		} else if (shipname.equals("cynomys")) {
			shipTexture03.bind();
		} else if (shipname.equals("squirrel")) {
			gl.glDisable(GL10.GL_CULL_FACE);
			squirrelTexture.bind();
		}
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0);

		if (shipname.equals("stickleback")) {
			shipMesh01.render(GL10.GL_TRIANGLES);
		} else if (shipname.equals("salx")) {
			shipMesh02.render(GL10.GL_TRIANGLES);
		} else if (shipname.equals("cynomys")) {
			shipMesh03.render(GL10.GL_TRIANGLES);

		} else if (shipname.equals("squirrel")) {
			squirrelMesh.render(GL10.GL_TRIANGLES);
		}
		gl.glPopMatrix();
	}

	private void renderHangar(GL10 gl, Ship ship, Application app) {
		if (ship.isExploding)
			return;
		// render hangar
		hangarTexture.bind();
		gl.glPushMatrix();
		hangarMesh.render(GL10.GL_TRIANGLES);

		// do forcefield
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);

		gl.glDisable(GL10.GL_LIGHTING);

		forcefieldMesh.render(GL10.GL_TRIANGLES);
		gl.glPopMatrix();
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_CULL_FACE);
	}

	@SuppressWarnings("unused")
	private void renderDrones(GL10 gl, ArrayList<Drone> drones,
			Application app, Ship ship) {
		if (ship.isExploding)
			return;
		// invaderTexture.bind();
		for (int i = 0; i < drones.size(); i++) {
			Drone drone = drones.get(i);
			gl.glPushMatrix();
			gl.glScalef(.25f, .25f, .25f);

			gl.glTranslatef(drone.position.x, drone.position.y,
					drone.position.z);

			// invaderMesh.render(GL10.GL_TRIANGLES);
			gl.glPopMatrix();

		}

	}

	private void renderSky(GL10 gl, Ship ship, Application app) {

		gl.glDisable(GL10.GL_LIGHTING);
		skyTexture.bind();
		gl.glColor4f(1, 1, 1, 1);

		gl.glPushMatrix();
		gl.glTranslatef(Ship.position.x, Ship.position.y, Ship.position.z);
		gl.glScalef(155f, 155f, 155f);
		skyMesh.render(GL10.GL_TRIANGLES);
		gl.glPopMatrix();
		gl.glEnable(GL10.GL_LIGHTING);
	}

	private void renderPlanet(GL10 gl, String texture, boolean uvtype,
			float radius, float x, float y, float z) {
		radius = radius * worldscale;

		if (texture.equals("earth")) {
			// render earth/jupiter texture
			planetTexture.bind();
		} else if (texture.equals("sun")) {
			// render mars/sun texture
			planetTexture02.bind();
		}

		gl.glPushMatrix();
		// move away from origin
		gl.glTranslatef(x, y, z);
		// scale to 10% size of earth
		gl.glScalef(radius, radius, radius);

		if (uvtype == true) {
			// render lower planet texture
			planetMesh.render(GL10.GL_TRIANGLES);
		} else {
			// render upper planet texture
			planetMesh02.render(GL10.GL_TRIANGLES);
		}
		gl.glPopMatrix();

	}

	private void renderStation(GL10 gl, String texture, boolean uvtype,
			float radius, float x, float y, float z) {
		radius = radius * worldscale;

		if (texture.equals("station01")) {
			// render earth/jupiter texture
			stationTexture.bind();
		} else if (texture.equals("sun")) {
			// render mars/sun texture
			planetTexture02.bind();
		}
		// render station
		gl.glPushMatrix();
		// move away from origin
		gl.glTranslatef(x, y, z);
		// scale to 10% size of earth
		gl.glScalef(radius, radius, radius);
		gl.glRotatef(Station.yawAngle, 0, 1, 0);
		stationMesh.render(GL10.GL_TRIANGLES);
		gl.glPopMatrix();

		// render station forcefield
		// gl.glPushMatrix();
		// rendermode for glow

		// gl.glEnable(GL10.GL_BLEND);
		// gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		// gl.glPushMatrix();
		// gl.glDisable(GL10.GL_LIGHTING);
		// gridTexture.bind();
		// gl.glColor4f(.5f, .5f, 1f, 1f);
		// gl.glScalef(radius * 1.2f, radius * 1.2f, radius * 1.2f);
		// doubleside render
		// gl.glDisable(GL10.GL_CULL_FACE);
		// gl.glRotatef(-Station.yawAngle, 0, 1, 0);
		// moonMesh.render(GL10.GL_TRIANGLES);
		// gl.glPopMatrix();
		// gl.glColor4f(1, 1, 1, 1);
		// doubleside render off
		// gl.glEnable(GL10.GL_CULL_FACE);
		// gl.glEnable(GL10.GL_LIGHTING);
	}

	private void renderSun(GL10 gl, float radius, float x, float y, float z,
			Application app) {

		gl.glDisable(GL10.GL_LIGHTING);
		radius = radius * worldscale;
		planetTexture02.bind();
		gl.glPushMatrix();
		// move away from origin
		gl.glTranslatef(x, y, z);
		// scale to size of earth
		gl.glScalef(radius, radius, radius);
		planetMesh02.render(GL10.GL_TRIANGLES);
		gl.glPopMatrix();
		gl.glEnable(GL10.GL_LIGHTING);
	}

	@SuppressWarnings("unused")
	private void renderMoon(GL10 gl, float radius, float x, float y, float z) {
		radius = radius * worldscale;
		moonTexture.bind();
		gl.glPushMatrix();
		// move away from origin
		gl.glTranslatef(x, y, z);
		// scale to size of moon
		gl.glScalef(radius, radius, radius);
		moonMesh.render(GL10.GL_TRIANGLES);
		gl.glPopMatrix();

	}

	private void renderActors(GL10 gl) {

		for (int i = 0; i < BattleBeasties3d.actorsList.size(); i++) {
			try {
				Actor actor = BattleBeasties3d.actorsList.get(i);
				if (actor != null && !(actor.shipname.equals("dead"))
						&& actor.getHitpoints() > 0 ) {
					// only do for ships in space
					if (actor.position.dst(Ship.position) < 5000) {
						if ((actor.visible)) {
							gl.glPushMatrix();
							gl.glTranslatef(actor.position.x, actor.position.y,
									actor.position.z);

							gl.glScalef(1, 1, 1);
							String actorName = (actor.get_firstname() + actor
									.get_surname());

							if (actorName.equals("Hive AIDisruptor")) {

								gl.glRotatef(actor.yawangle, 0, 1, 0);
								gl.glRotatef(actor.pitchangle, 1, 0, 0);
								gl.glRotatef(invaderAngle, 0, 0, 1);
								shieldDisruptorTexture.bind();
								shieldDisruptorMesh.render(GL10.GL_TRIANGLES);

								gl.glEnable(GL10.GL_BLEND);
								gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
								gl.glScalef(.5f, .5f, .5f);
								gl.glDisable(GL10.GL_CULL_FACE);
								gl.glDisable(GL10.GL_LIGHTING);
								gl.glColor4f(.0f, 1f, 0f, 1);

								swirlyTexture.bind();
								// gl.glRotatef(-(invaderAngle * 20), 0, 0, 1);
								coneMesh.render(GL10.GL_TRIANGLES);
								gl.glColor4f(1f, 1f, 1f, 1f);
								gl.glDisable(GL10.GL_BLEND);
								gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
								gl.glEnable(GL10.GL_LIGHTING);
								gl.glEnable(GL10.GL_CULL_FACE);
							} else if (actorName.equals("Hive AIScout")) {

								gl.glRotatef(actor.yawangle, 0, 1, 0);
								gl.glRotatef(actor.pitchangle, 1, 0, 0);
								jetTexture.bind();
								gl.glScalef(1f, 1f, 1f);
								shieldDisruptorMesh.render(GL10.GL_TRIANGLES);
								gl.glScalef(20f, 20f, 20f);
								gl.glDisable(GL10.GL_CULL_FACE);
								gl.glEnable(GL10.GL_BLEND);
								gl.glDisable(GL10.GL_LIGHTING);
								gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
								gl.glColor4f(.0f, .5f, 1f, 1);
								gl.glRotatef(-actor.yawangle, 0, 1, 0);
								gl.glRotatef(-actor.pitchangle, 1, 0, 0);
								swansongTexture.bind();

								looking = Util.lookAt(Simulation.camera,
										actor.position);
								gl.glRotatef(looking.y, 0, 1, 0);
								gl.glRotatef(-looking.x, 1, 0, 0);

								sunMesh.render(GL10.GL_TRIANGLES);
								gl.glColor4f(1f, 1f, 1f, 1f);
								gl.glDisable(GL10.GL_BLEND);
								// gl.glBlendFunc(GL10.GL_SRC_ALPHA,
								// GL10.GL_ONE);
								gl.glEnable(GL10.GL_LIGHTING);
								gl.glEnable(GL10.GL_CULL_FACE);

							} else {
								renderActorShip(gl, actor, actor.shipname);
							}
							gl.glPopMatrix();
						}
					}
				}
			} catch (IndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void renderShots(GL10 gl, ArrayList<Shot> shots) {
		gl.glColor4f(1, 1, 0, 1);
		gl.glEnable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
		jetTexture.bind();
		for (int i = 0; i < shots.size(); i++) {
			Shot shot = shots.get(i);
			gl.glPushMatrix();

			gl.glTranslatef(shot.position.x, shot.position.y, shot.position.z);
			gl.glRotatef(shot.yawAngle, 0, 1, 0);
			gl.glRotatef(shot.pitchAngle, 1, 0, 0);
			gl.glScalef(3 + (float) (shot.shotLife) * 1f,
					(float) (3 + shot.shotLife) * 1f,
					(float) (3 + shot.shotLife) * 1f);
			jetMesh.render(GL10.GL_TRIANGLES);
			gl.glPopMatrix();
		}
		gl.glColor4f(1, 1, 1, 1);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glDisable(GL10.GL_BLEND);
		gl.glEnable(GL10.GL_LIGHTING);
	}

	private void renderMissiles(GL10 gl, ArrayList<Missile> missiles) {
		gl.glColor4f(1, 0, 0, 1);
		gl.glEnable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
		jetTexture.bind();
		for (int i = 0; i < missiles.size(); i++) {
			Missile missile = missiles.get(i);
			gl.glPushMatrix();

			gl.glTranslatef(missile.position.x, missile.position.y,
					missile.position.z);
			gl.glRotatef(missile.yawAngle, 0, 1, 0);
			gl.glRotatef(missile.pitchAngle, 1, 0, 0);
			gl.glScalef(10f, 10f, 10f);
			jetMesh.render(GL10.GL_TRIANGLES);
			gl.glPopMatrix();
		}
		gl.glColor4f(1, 1, 1, 1);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glDisable(GL10.GL_BLEND);
		gl.glEnable(GL10.GL_LIGHTING);
	}

	private void renderDusts(GL10 gl, ArrayList<Dust> dusts) {

		gl.glEnable(GL10.GL_BLEND);
		// gl.glDisable(GL10.GL_CULL_FACE);
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		dustTexture.bind();
		for (int i = 0; i < dusts.size(); i++) {
			Dust dust = dusts.get(i);
			if (camera.frustum.pointInFrustum(dust.position)) {
				gl.glPushMatrix();
				gl.glColor4f(dust.colour, dust.colour, dust.colour, 1);
				gl.glTranslatef(dust.position.x, dust.position.y,
						dust.position.z);
				gl.glRotatef(dust.yawAngle + 180, 0, 1, 0);
				gl.glRotatef(dust.pitchAngle, 1, 0, 0);
				gl.glScalef(dust.size, dust.size, dust.size);
				sunMesh.render(GL10.GL_TRIANGLES);
				gl.glPopMatrix();
			}
		}
		gl.glColor4f(1, 1, 1, 1);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glDisable(GL10.GL_BLEND);
		gl.glEnable(GL10.GL_LIGHTING);
	}

	@SuppressWarnings("unused")
	private void renderTrails(GL10 gl, ArrayList<Trail> trails) {
		gl.glColor4f(0, 1, 1, 1);
		jetTexture.bind();
		gl.glEnable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
		jetTexture.bind();
		for (int i = 0; i < trails.size(); i++) {
			Trail trail = trails.get(i);
			gl.glPushMatrix();
			gl.glRotatef(trail.yawAngle, 0, 1, 0);
			gl.glRotatef(trail.pitchAngle, 1, 0, 0);
			gl.glTranslatef(trail.position.x, trail.position.y,
					trail.position.z);

			float scale = (Trail.TRAIL_LIFE - trail.life) / 4;
			gl.glScalef(scale, scale, scale * 2.5f);
			jetMesh.render(GL10.GL_TRIANGLES);
			gl.glPopMatrix();
		}
		gl.glColor4f(1, 1, 1, 1);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glDisable(GL10.GL_BLEND);
		gl.glEnable(GL10.GL_LIGHTING);
	}

	private void renderExplosions(GL10 gl, ArrayList<Explosion> explosions) {
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glEnable(GL10.GL_BLEND);
		// gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glDisable(GL10.GL_LIGHTING);
		for (int i = 0; i < explosions.size(); i++) {
			Explosion explosion = explosions.get(i);
			gl.glPushMatrix();
			if (explosion.type == Explosion.NORMAL) {
				gl.glTranslatef(explosion.position.x, explosion.position.y,
						explosion.position.z);
				explosionTexture.bind();
				gl.glScalef(10, 10, 10);
				gl.glRotatef(explosion.yawAngle, 0, 1, 0);
				gl.glRotatef(explosion.pitchAngle, 1, 0, 0);
				explosionMesh
						.render(GL10.GL_TRIANGLE_FAN,
								(int) ((explosion.aliveTime / explosion.explosionLiveTime) * 15) * 4,
								4);

			} else if (explosion.type == Explosion.SWANSONG) {
				gl.glTranslatef(explosion.position.x, explosion.position.y,
						explosion.position.z);
				swansongTexture.bind();
				gl.glColor4f(1 - (explosion.aliveTime / 2),
						1 - (explosion.aliveTime / 2),
						1 - (explosion.aliveTime / 2),
						1 - (explosion.aliveTime / 2));
				gl.glRotatef(explosion.yawAngle, 0, 1, 0);
				gl.glRotatef(explosion.pitchAngle, 1, 0, 0);
				gl.glScalef(explosion.scale*10, explosion.scale*10, explosion.scale*10);
				sunMesh.render(GL10.GL_TRIANGLES);
				gl.glScalef(1, 1, 1);
				gl.glColor4f(1f, 1f, 1f, 1f);
			} else if (explosion.type == Explosion.RANKUP) {
				gl.glTranslatef(explosion.position.x, explosion.position.y,
						explosion.position.z);
				lvlupTexture.bind();
				gl.glColor4f(1 - (explosion.aliveTime / 2),
						1 - (explosion.aliveTime / 2),
						1 - (explosion.aliveTime / 2),
						1 - (explosion.aliveTime / 2));
				gl.glRotatef(explosion.yawAngle, 0, 1, 0);
				gl.glRotatef(explosion.pitchAngle, 1, 0, 0);
				gl.glRotatef(explosion.aliveTime * 360f, 0, 0, 1);
				gl.glScalef(explosion.scale, explosion.scale, explosion.scale);
				sunMesh.render(GL10.GL_TRIANGLES);
				gl.glScalef(1, 1, 1);
				gl.glColor4f(1f, 1f, 1f, 1f);

			} else if (explosion.type == Explosion.LEVELUP) {
				gl.glTranslatef(explosion.position.x, explosion.position.y,
						explosion.position.z);
				lvlupTexture.bind();
				gl.glColor4f(1 - (explosion.aliveTime / 4),
						.5f,//1 - (explosion.aliveTime / 2),
						.5f - (explosion.aliveTime / 2),
						1 - (explosion.aliveTime / 2));
				gl.glRotatef(explosion.yawAngle, 0, 1, 0);
				gl.glRotatef(explosion.pitchAngle, 1, 0, 0);
				gl.glRotatef(explosion.aliveTime * 360f, 0, 0, 1);
				gl.glScalef(explosion.scale, explosion.scale, explosion.scale);
				sunMesh.render(GL10.GL_TRIANGLES);
				gl.glScalef(1, 1, 1);
				gl.glColor4f(1f, 1f, 1f, 1f);
			}
			gl.glPopMatrix();
		}
		gl.glDisable(GL10.GL_BLEND);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glEnable(GL10.GL_LIGHTING);
	}

	public void dispose() {
		/** the station mesh **/
		stationMesh.dispose();
		/** the ship mesh **/
		shipMesh01.dispose();
		/** the ship mesh **/
		shipMesh02.dispose();
		/** the ship mesh **/
		shipMesh03.dispose();
		/** the planet mesh **/
		planetMesh02.dispose();
		;
		/** the planet mesh **/
		planetMesh.dispose();
		;
		/** the jet mesh **/
		jetMesh.dispose();
		/** the moon mesh **/
		moonMesh.dispose();
		/** the ship mesh **/
		skyMesh.dispose();
		/** the asteroid mesh **/
		roidMesh.dispose();
		/** the forcefield mesh **/
		forcefieldMesh.dispose();
		/** the block mesh **/
		blockMesh.dispose();
		/** the hangar mesh **/
		hangarMesh.dispose();
		/** the shot mesh **/
		shotMesh.dispose();
		/** the explosion mesh **/
		explosionMesh.dispose();
		coneMesh.dispose();
		/** the squirrel mesh **/
		squirrelMesh.dispose();
	}

	/**
	 * 
	 */
	private void marketScreen() {
		if (BattleBeasties3d.stationScreen == 9) {
			marketScreen = new Market(stage);
			BattleBeasties3d.stationScreen = 209;
		}
		if (BattleBeasties3d.stationScreen == 209) {
			// screen updates here
		}
		if (BattleBeasties3d.stationScreen == 109) {
			stage.findActor("marketWindow").remove();
			marketScreen = null;
			BattleBeasties3d.stationScreen = 0;
		}
	}
}
