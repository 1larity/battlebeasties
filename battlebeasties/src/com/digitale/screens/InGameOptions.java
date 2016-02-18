/*
 * Copyright 2010 Mario Zechner (contact@badlogicgames.com), Nathan Sweet (admin@esotericsoftware.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.digitale.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.ValueChangedListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.digitale.mygdxgame.Renderer;
import com.digitale.mygdxgame.SoundManager;
import com.digitale.mygdxgame.BattleBeasties3d;
import com.digitale.sim.Simulation;
import com.digitale.utils.DataOp;
import com.digitale.utils.DataOpImpl;

/**
 * The main menu screen showing a background, the logo of the game and a label
 * telling the user to touch the screen to start the game. Waits for the touch
 * and returns isDone() == true when it's done so that the ochestrating
 * GdxInvaders class can switch to the next screen.
 * 
 * @author mzechner
 */
public class InGameOptions extends StardustScreen {
	String[] listEntries = { null, null, null, null, null, null, null, null,
			null, null };
	public DataOp MyDataOp = new DataOpImpl();
	Skin skin;

	Texture texture1;
	Texture texture2;

	Actor root;
	protected boolean doneflag;
	private float originalsfxVolume;
	private float originalMusicVolume;
	private float originalaccellXsensitivity;
	private float originalaccellYsensitivity;
	private float originalDeadzoneX;
	private float originalDeadzoneY;
	public InGameOptions(Stage stage) {
		originalsfxVolume = BattleBeasties3d.sfxVolume;
		originalMusicVolume = BattleBeasties3d.musicVolume;
		originalaccellXsensitivity=BattleBeasties3d.accelXsensitivity;
		originalaccellYsensitivity=BattleBeasties3d.accelYsensitivity;
		originalDeadzoneX=BattleBeasties3d.deadzoneX;
		originalDeadzoneY=BattleBeasties3d.deadzoneY;
		skin = new Skin(Gdx.files.internal("data/uiskin.json"),
				Gdx.files.internal("data/uiskin.png"));

		Gdx.input.setInputProcessor(stage);

		// Group.debug = true;

		final Button buttonClose = new TextButton("Close",
				skin.getStyle(TextButtonStyle.class), "buttonClose");
		final Button buttonCancel = new TextButton("Cancel",
				skin.getStyle(TextButtonStyle.class), "buttonCancel");
		;
		final CheckBox checkBoxSFX = new CheckBox("Sound Effects",
				skin.getStyle(CheckBoxStyle.class), "checkbox");
		final CheckBox checkBoxFontsize = new CheckBox("Use Small Font",
				skin.getStyle(CheckBoxStyle.class), "checkboxfont");
		
		final Slider slidersfx = new Slider(0, 2, .1f,
				skin.getStyle(SliderStyle.class), "slidersfx");
		
		final Slider sliderdzy = new Slider(.1f, 2, .1f,
				skin.getStyle(SliderStyle.class), "sliderdzy");
		final Slider sliderdzx = new Slider(.1f, 2, .1f,
				skin.getStyle(SliderStyle.class), "sliderdzx");
		//sensitivty
		final Slider slidersy = new Slider(.1f, 2, .1f,
				skin.getStyle(SliderStyle.class), "sliderdsy");
		final Slider slidersx = new Slider(.1f, 2, .1f,
				skin.getStyle(SliderStyle.class), "sliderdsx");
		//stardust
		final Slider sliderdust = new Slider(20, 500, 10f,
				skin.getStyle(SliderStyle.class), "sliderdust");
		
		final Slider slidermusic = new Slider(0, 1, .01f,
				skin.getStyle(SliderStyle.class), "slidersmusic");
		final Slider slidergfxquality = new Slider(0, 10, 1,
				skin.getStyle(SliderStyle.class), "slidergfx");
		
		slidersfx.setValue(BattleBeasties3d.sfxVolume);
		slidermusic.setValue(BattleBeasties3d.musicVolume);
		sliderdzx.setValue(BattleBeasties3d.deadzoneX);
		sliderdzy.setValue(BattleBeasties3d.deadzoneY);
		sliderdust.setValue(BattleBeasties3d.dustsize);
		slidersx.setValue(BattleBeasties3d.accelXsensitivity);
		slidersy.setValue(BattleBeasties3d.accelYsensitivity);
		final Label lablesfx = new Label("Sound effect volume",
				skin.getStyle(LabelStyle.class), "lablesfx");
		final Label lablemusic = new Label("Music volume",
				skin.getStyle(LabelStyle.class), "lablemusic");
		final Label lablegfx = new Label("Graphics quality",
				skin.getStyle(LabelStyle.class), "lablegfx");

		final Label lablesliderdzx = new Label("Horizontal Deadzone",
				skin.getStyle(LabelStyle.class), "lablesliderdzx");
		final Label lablesliderdzy = new Label("Vertical Deadzone",
				skin.getStyle(LabelStyle.class), "lablesliderdzy");
		final Label lableslidersx = new Label("Horizontal Sensitivity",
				skin.getStyle(LabelStyle.class), "lableslidersx");
		final Label lableslidersy = new Label("Vertical Sensitivity",
				skin.getStyle(LabelStyle.class), "lableslidersy");
		final Label lablesliderdust = new Label("Spacedust Density",
				skin.getStyle(LabelStyle.class), "lablesliderdust");
		final Preferences prefs = Gdx.app.getPreferences("stardustpreferences");
		final Label lablestilt = new Label("Android Tilt Settings",
				skin.getStyle(LabelStyle.class), "labletilt");
		
		Window window = new Window("Options Screen",
				skin.getStyle(WindowStyle.class), "optionsWindow");
		if (BattleBeasties3d.DEBUG)
			window.debug();
		window.x = window.y = 0;
		window.defaults().pad(2);
		window.setFillParent(true);
		window.setMovable(false);
		// build table
		window.row().fill().expandX().maxHeight(28);
		window.add();
		window.add();
		window.add(buttonCancel).colspan(1);
		window.add(buttonClose).colspan(1);
		// audio slider lables
		window.row().fill().expandX().maxHeight(28);
		window.add(checkBoxFontsize);
		window.add();
		window.add(lablestilt).maxWidth(350).fillX().colspan(2);
		
		window.row().fill().expandX().maxHeight(28);
		//top row 
		window.add(lablesfx).maxWidth(350).fillX().colspan(2);
		window.add(lablesliderdzx).maxWidth(350).fillX().colspan(2);
		window.row().fill().expandX().maxHeight(28);
		window.add(slidersfx).fillX().colspan(2);
		window.add(sliderdzx).fillX().colspan(2);
		window.row().fill().expandX().maxHeight(28);
	
		window.add(lablemusic).maxWidth(350).fillX().colspan(2);
		window.add(lablesliderdzy).maxWidth(350).fillX().colspan(2);
		window.row().fill().expandX().maxHeight(28);
		window.add(slidermusic).fillX().colspan(2);
		window.add(sliderdzy).fillX().colspan(2);
		window.row().fill().expandX().maxHeight(28);
		
		window.add(lablesliderdust ).maxWidth(350).fillX().colspan(2);
		window.add(lableslidersx).maxWidth(350).fillX().colspan(2);
		window.row().fill().expandX().maxHeight(28);
		window.add(sliderdust ).fillX().colspan(2);
		window.add(slidersx).fillX().colspan(2);
		window.row().fill().expandX().maxHeight(28);
		
		window.add( ).minWidth(100).maxWidth(350).fillX().colspan(2);
		window.add(lableslidersy).maxWidth(350).fillX().colspan(2);
		window.row().fill().expandX().maxHeight(28);
		window.add( ).fillX().colspan(2);
		window.add(slidersy).fillX().colspan(2);
		window.row();
		// audio sliders
		

		window.pack();
		stage.addActor(window);

		buttonClose.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("Options Close");
				SoundManager.playuiclick();
				prefs.putFloat("musicvolume", BattleBeasties3d.musicVolume);
				prefs.putFloat("sfxvolume", BattleBeasties3d.sfxVolume);

				prefs.putFloat("dzx", BattleBeasties3d.deadzoneX);
				prefs.putFloat("dzy", BattleBeasties3d.deadzoneY);
				prefs.putFloat("sx", BattleBeasties3d.accelXsensitivity);
				prefs.putFloat("sy", BattleBeasties3d.accelYsensitivity);
				
				prefs.putFloat("dustsize", BattleBeasties3d.dustsize);
				Simulation.populateDust();
				prefs.flush();
				BattleBeasties3d.stationScreen = 107;
			}
		});

		checkBoxFontsize.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				SoundManager.playuiclick();
				if (BattleBeasties3d.DEBUG)
					System.out.println("smallfont");
				BattleBeasties3d.tinyfont = checkBoxFontsize.isChecked();
				// if user wants to change fontsize
				prefs.putBoolean("fontsize", BattleBeasties3d.tinyfont);
				prefs.flush();
			}
		});

		buttonCancel.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("Options Close");
				SoundManager.playuiclick();
				resetSettings();
				BattleBeasties3d.stationScreen = 107;
			}
		});
		
		slidersfx.setValueChangedListener(new ValueChangedListener() {
			public void changed(Slider slider, float value) {
				if (value == 0)
					value = 0.001f;
				BattleBeasties3d.sfxVolume = value;
				SoundManager.setSFXVolume();
				SoundManager.playconfirm();
			}
		});
		
		slidermusic.setValueChangedListener(new ValueChangedListener() {
			public void changed(Slider slider, float value) {
				if (value == 0)
					value = 0.001f;
				BattleBeasties3d.musicVolume = value;
				
				SoundManager.setMusicVolume();

			}
		});
		
		sliderdzx.setValueChangedListener(new ValueChangedListener() {
			public void changed(Slider slider, float value) {
				if (value == 0)
					value = 0.001f;
				BattleBeasties3d.deadzoneX = value;
				}
		});
		sliderdzy.setValueChangedListener(new ValueChangedListener() {
			public void changed(Slider slider, float value) {
				if (value == 0)
					value = 0.001f;
				BattleBeasties3d.deadzoneY = value;
				}
		});
		
		slidersx.setValueChangedListener(new ValueChangedListener() {
			public void changed(Slider slider, float value) {
				if (value == 0)
					value = 0.001f;
				BattleBeasties3d.accelXsensitivity = value;
				}
		});
		
		slidersy.setValueChangedListener(new ValueChangedListener() {
			public void changed(Slider slider, float value) {
				if (value == 0)
					value = 0.001f;
				BattleBeasties3d.accelYsensitivity = value;
				}
		});
		sliderdust.setValueChangedListener(new ValueChangedListener() {
			public void changed(Slider slider, float value) {
				if (value == 0)
					value = 0.001f;
				BattleBeasties3d.dustsize = value;
				SoundManager.setMusicVolume();
			}
		});
	}

	protected void resetSettings() {
		BattleBeasties3d.sfxVolume = originalsfxVolume;
		BattleBeasties3d.musicVolume = originalMusicVolume;
		SoundManager.setMusicVolume();

	}

	@Override
	public void render(float delta) {

		if (BattleBeasties3d.DEBUG)
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
