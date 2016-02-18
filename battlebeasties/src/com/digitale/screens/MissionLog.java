/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com

 */

package com.digitale.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane.SplitPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.badlogic.gdx.utils.Scaling;
import com.digitale.connex.Inventory;
import com.digitale.mygdxgame.Renderer;
import com.digitale.mygdxgame.SoundManager;
import com.digitale.mygdxgame.BattleBeasties3d;
import com.digitale.sim.Ship;
import com.digitale.utils.Actors;
import com.digitale.utils.Actors.DialogListener;

/**
 * inventory screen needs 2 forms one for when ship is docked that allows
 * trading of items and one for when ship is in space that only displays ship
 * cargo
 * 
 * @author rbeech
 */
public class MissionLog extends StardustScreen {
	String[] listEntries = { null, null, null, null, null, null, null, null,
			null, null };

	Skin skin;
	Actor dialog;
	Actor root;
	public int currentMission = 0;

	public boolean completeflag = false;
	protected boolean doneflag;

	protected String selectedCharacter;

	public MissionLog(Stage stage) {

		BattleBeasties3d.myMissions.clear();
		BattleBeasties3d.MyDataOp.getMissionLog(0);

		skin = new Skin(Gdx.files.internal("data/uiskin.json"),
				Gdx.files.internal("data/uiskin.png"));
		Gdx.input.setInputProcessor(stage);

		// Group.debug = true;
		final Label missionTitleLabel = new Label("No Missions Accepted", skin.getStyle(LabelStyle.class), "missionlable");
		final Label missionTextLabel =new Label("",
				skin.getStyle(LabelStyle.class), "missiontextlable");
		final Label missionCounter =new Label("",
				skin.getStyle(LabelStyle.class), "missioncounterlable");
		
		missionTextLabel.setWrap(true);
		final Label negotiatorLabel = new Label("Negotiator:",
				skin.getStyle(LabelStyle.class), "negotiatorlable");
		final Label negotiatorInfoLabel = new Label("Negotiator Info",
				skin.getStyle(LabelStyle.class), "negotiatorinfolable");
		negotiatorInfoLabel.setWrap(true);
		// buttons
		final Button buttonClose = new TextButton("Close",
				skin.getStyle(TextButtonStyle.class), "button-close");
		final Button buttonPrev = new TextButton("Previous",
				skin.getStyle(TextButtonStyle.class), "buttonprevious");
		final Button buttonNext = new TextButton("Next",
				skin.getStyle(TextButtonStyle.class), "buttonnext");
		final Button buttonCompleted = new TextButton("Incomplete",
				skin.getStyle(TextButtonStyle.class), "buttoncompleted");
		final Button buttonComplete = new TextButton("Complete",
				skin.getStyle(TextButtonStyle.class), "buttoncomplete");

		String uidString = "bob";
		final Image newItem = new Image(new TextureRegion(new Texture(
				Gdx.files.internal("data/avatar02.jpg"))), Scaling.none,
				Align.CENTER, uidString);

		final ScrollPane paneCargo = new ScrollPane(newItem,
				skin.getStyle(ScrollPaneStyle.class), "cargo");
		// paneCargo.addActor(negotiatorInfoLabel);
		final ScrollPane paneMission = new ScrollPane(missionTextLabel,
				skin.getStyle(ScrollPaneStyle.class), "warehouse");
		final SplitPane splitPane = new SplitPane(paneCargo, paneMission,
				false,
				skin.getStyle("default-horizontal", SplitPaneStyle.class),
				"split");

		Window window = new Window("Mission Log Screen",
				skin.getStyle(WindowStyle.class), "logWindow");
		if (BattleBeasties3d.DEBUG)
			window.debug();
		window.x = window.y = 0;
		window.setFillParent(true);
		window.setMovable(false);
		window.defaults().pad(5);
		window.defaults().spaceBottom(5);
		window.row().fill().expandX();
		window.add(buttonPrev).minWidth(150);
		window.add(buttonNext).minWidth(150);
		window.add(buttonComplete).minWidth(150);
		//window.add(buttonCompleted).minWidth(150);
		window.add(buttonClose).minWidth(150);
		window.row();
		window.add(negotiatorLabel).colspan(2);
		window.add(missionCounter);
		window.add(missionTitleLabel).colspan(2);
		window.row().fill().expandY();
		window.add(splitPane).colspan(5);
		window.pack();

		stage.addActor(window);
		if (currentMission == 0) {
			buttonPrev.visible=false;
		}
		if (currentMission == BattleBeasties3d.myMissions.size()-1) {
			buttonNext.visible=false;
		}
		buttonClose.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("mission log Close");
				SoundManager.playuiclick();
				BattleBeasties3d.stationScreen = 113;
				// Renderer.stage.clear();
			}
		});
		buttonPrev.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("show previous mission");
				SoundManager.playuiclick();
				if (currentMission > 0) {
					currentMission = currentMission - 1;
					System.out.println("missionID" + currentMission);
				}
				if (currentMission == 0) {
					buttonPrev.visible=false;
				}
				if (currentMission < BattleBeasties3d.myMissions.size()-1) {
					buttonNext.visible=true;
				}
				
			}
		});
		buttonNext.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("show next mission");
				if (currentMission < BattleBeasties3d.myMissions.size() - 1) {
					currentMission = currentMission + 1;
					System.out.println("missionID" + currentMission);
				}
				SoundManager.playuiclick();
				if (currentMission == BattleBeasties3d.myMissions.size()-1) {
					buttonNext.visible=false;
				}
				if (currentMission != 0) {
					buttonPrev.visible=true;
				}
			}
		});
		buttonCompleted.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				currentMission = 0;
				SoundManager.playuiclick();
				if (completeflag == true) {
					((TextButton) buttonCompleted).setText("Incomplete");
					completeflag = false;
					System.out.println("show incomplete");
					BattleBeasties3d.myMissions.clear();
					BattleBeasties3d.MyDataOp.getMissionLog(0);
					buttonComplete.visible=true;
					System.out.println(BattleBeasties3d.myMissions.isEmpty());
					//missionTitleLabel.setText(Stardust3d.myMissions.get(
					//		currentMission).getTitle());
				} else {
					((TextButton) buttonCompleted).setText("Completed");
					completeflag = true;
					System.out.println("show completed");
					BattleBeasties3d.myMissions.clear();
					BattleBeasties3d.MyDataOp.getMissionLog(1);
					buttonComplete.visible=false;
					System.out.println(BattleBeasties3d.myMissions.isEmpty());
					//missionTitleLabel.setText(Stardust3d.myMissions.get(
					//		currentMission).getTitle());
				}
				
					buttonPrev.visible=false;
				
				
				if (currentMission == BattleBeasties3d.myMissions.size()-1) {
					buttonNext.visible=false;
				}else{
					buttonNext.visible=true;
				}
			}
		});
		buttonComplete.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("complete mission");
				// check requsites have been fulfilled
				// flag mission as completed
				// award rewards
				SoundManager.playuiclick();

			}
		});
	}

	@Override
	public void render(float delta) {

		Table.drawDebug(Renderer.stage);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void dispose() {

		skin.dispose();

	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDone() {
		return doneflag;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(float delta) {
		// TODO Auto-generated method stub

	}

}
