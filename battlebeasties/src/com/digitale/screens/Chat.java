/*
 * Copyright 2012 Richard Beech
 */

package com.digitale.screens;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectionListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.digitale.mygdxgame.Renderer;
import com.digitale.mygdxgame.SoundManager;
import com.digitale.mygdxgame.BattleBeasties3d;
import com.digitale.utils.Actors;

/**
 * chat screen
 * @author RP Beech
 */
public class Chat extends StardustScreen {
	public static boolean fadeOut;
	public static boolean fadeIn;
	public static float fadeTimer;
	public static int i = 0;
	private Timer timer = new Timer();
	Skin skin;
	String[] listEntries = { "", "", "", "", "", " ", "", "", "", "", "" };
	Stage stage;
	SpriteBatch batch;
	/** the cross fade texture **/
	private Texture xfadeTexture;
	Texture background;
	Actor root;
	private String text;
	protected boolean doneflag = false;
	private int selectedChannel = 4;
	private int selectedChannelType = 4;
	final Preferences prefs = Gdx.app.getPreferences("stardustpreferences");

	public Chat(String chatlines, Stage stage) {
		// populate channel dropdown
		for (int i = 0; i < BattleBeasties3d.channelList.size(); i++) {
					listEntries[i] = BattleBeasties3d.channelList.get(i).getChannelname();
		}
		//get prefs, if no prefs (-1) use factory setting
		int temp = prefs.getInteger("selectedchatchannel", -1);
		if (temp > 0)
		{
			selectedChannel = temp;
		}else if (temp <-1){
			selectedChannel=8;
		}
			temp = prefs.getInteger("chatchanneltype", -1);
		if (temp != -1)
			selectedChannelType = temp;
		
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("data/uiskin.json"),
				Gdx.files.internal("data/uiskin.png"));
		xfadeTexture = new Texture(Gdx.files.internal("data/blackpixel.png"),
				Format.RGB565, true);
		xfadeTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
		fadeIn();
		

		Gdx.input.setInputProcessor(stage);

		// Group.debug = true;

		final Button buttonSend = new TextButton("Send",
				skin.getStyle(TextButtonStyle.class), "buttonsend");
		final Button buttonClose = new TextButton("Close",
				skin.getStyle(TextButtonStyle.class), "buttonClose");
		final TextField textfieldText = new TextField("", "Type message here",
				skin.getStyle(TextFieldStyle.class), "textfield");
		final SelectBox dropdown = new SelectBox(listEntries,
				skin.getStyle(SelectBoxStyle.class), "combo");

		// final List list = new List(listEntries,
		// skin.getStyle(ListStyle.class), "list");
		final Label labelchatlines = new Label("Chat:", skin.getStyle(LabelStyle.class), "labelchat");
		final Label labelchattitle = new Label("Chat:", skin.getStyle(LabelStyle.class), "labelchattitle");
		
		labelchatlines.setWrap(true);
		final ScrollPane scrollPane2 = new ScrollPane(labelchatlines,
		skin.getStyle(ScrollPaneStyle.class), "scroll");
		dropdown.setSelection(selectedChannel);
		final Label lableNews = new Label("Chat: ", skin);
		
		Window window = new Window("Chat Screen",
				skin.getStyle(WindowStyle.class), "chatWindow");
		if (BattleBeasties3d.DEBUG)
			window.debug();
		window.x = window.y = 0;
		window.setFillParent(true);
		window.setMovable(false);
		window.defaults().pad(5);
		// build table
		window.row().fill().expandX();
		window.add(dropdown).align("Centre");
		window.add();
		
		window.add(buttonSend).colspan(1).align("right");
		window.add(buttonClose).colspan(1).align("center");
				window.row();
		window.add(textfieldText).minWidth(100).expandX().fillX().colspan(4);
		
		window.row();
		window.add(labelchattitle).align("left");
		window.row();
		window.add(scrollPane2).fill().expand().colspan(4);
		window.row();
		window.pack();
		stage.addActor(window);

		textfieldText.setTextFieldListener(new TextFieldListener() {
			public void keyTyped(TextField textField, char key) {
				if (key == '\n')
					textField.getOnscreenKeyboard().show(false);
			}
		});
		dropdown.setSelectionListener(new SelectionListener() {
			@Override
			public void selected(Actor actor, int index, String value) {
				SoundManager.playuiclick();
				selectedChannelType = (BattleBeasties3d.channelList.get(index)
						.getChanneltype());
				selectedChannel = (BattleBeasties3d.channelList.get(index).getUid());
				
				BattleBeasties3d.chatChannel=selectedChannel;
				BattleBeasties3d.chatChannelName=BattleBeasties3d.channelList.get(index)
						.getChannelname();
				prefs.putInteger("chatchanneltype", selectedChannelType);
				prefs.putInteger("selectedchatchannel", selectedChannel);
				prefs.flush();
				BattleBeasties3d.chatListReset=true;
				BattleBeasties3d.refreshChat(BattleBeasties3d.chatChannel,false);

			}
		});
		buttonClose.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				if (BattleBeasties3d.DEBUG)
					System.out.println("close chat button pressed");
				SoundManager.playuiclick();
				BattleBeasties3d.stationScreen=104;
				doneflag = true;
				isDone();
			}
		});

		buttonSend.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				SoundManager.playuiclick();

				if (BattleBeasties3d.DEBUG)
					System.out.println("send button pressed");
				text = textfieldText.getText().toString();
				if (selectedChannelType == 0 || selectedChannelType == 8) {
					Renderer.stage.addActor(Actors.bottomToast(
							"You cannot send messages to this channel.", 4, skin));
					SoundManager.playError();
			
				} else if (!text.isEmpty()){
					String passed = BattleBeasties3d.MyDataOp.postChat(text,
							selectedChannel, BattleBeasties3d.myCharacter.getUid());
					textfieldText.setText("");
					BattleBeasties3d.refreshChat(selectedChannel,false);
					
				}
			}
		});

	}

	/**
	 * 
	 */
	private void fadeIn() {
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				i++;

				// detect and set white fade in
				if (i < 10 && fadeIn == false) {
					fadeIn = false;
					fadeTimer = 1;
				}
				if (fadeIn) {
					fadeTimer = fadeTimer - 0.05f;
					if (BattleBeasties3d.DEBUG)
						System.out.println("fade " + fadeTimer);
				}
				if (fadeIn && fadeTimer > 10) {
					fadeIn = false;
					fadeTimer = 0;
				}

				if (fadeTimer < 0) {
					fadeTimer = 0;
					timer.cancel();
				}

			}
		}, 0, 100);
	}

	@Override
	public void render(float delta) {


		if (BattleBeasties3d.DEBUG)
			Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, false);
	}

	@Override
	public void dispose() {
	
		
	
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDone() {
		return doneflag;

	}

	public void callOptions() {
		// TODO

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
