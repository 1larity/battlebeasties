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

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.FlickScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.ValueChangedListener;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane.SplitPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;

/** The main menu screen showing a background, the logo of the game and a label telling the user to touch the screen to start the
 * game. Waits for the touch and returns isDone() == true when it's done so that the ochestrating GdxInvaders class can switch to
 * the next screen.
 * @author mzechner */
public class UIStuff extends StardustScreen {
	   String[] listEntries = {"This is a list entry", "And another one", "The meaning of life", "Is hard to come by",
               "This is a list entry", "And another one", "The meaning of life", "Is hard to come by", "This is a list entry",
               "And another one", "The meaning of life", "Is hard to come by", "This is a list entry", "And another one",
               "The meaning of life", "Is hard to come by", "This is a list entry", "And another one", "The meaning of life",
               "Is hard to come by"};

       Skin skin;
       Stage stage;
       SpriteBatch batch;
       Texture texture1;
       Texture texture2;
       Actor root;

       public UIStuff(Application app) {
	
               batch = new SpriteBatch();
               skin = new Skin(Gdx.files.internal("data/uiskin.json"), Gdx.files.internal("data/uiskin.png"));
               texture1 = new Texture(Gdx.files.internal("data/badlogicsmall.jpg"));
               texture2 = new Texture(Gdx.files.internal("data/badlogic.jpg"));
               TextureRegion image = new TextureRegion(texture1);
               TextureRegion image2 = new TextureRegion(texture2);
               stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
               Gdx.input.setInputProcessor(stage);

               // Group.debug = true;

               final Button button = new TextButton("Single", skin.getStyle(TextButtonStyle.class), "button-sl");
               final Button buttonMulti = new TextButton("Multi\nLine\nToggle", skin.getStyle("toggle", TextButtonStyle.class),
                       "button-ml-tgl");
               final Button imgButton = new Button(new Image(image), skin.getStyle(ButtonStyle.class));
               final Button imgToggleButton = new Button(new Image(image), skin.getStyle("toggle", ButtonStyle.class));
               final CheckBox checkBox = new CheckBox("Check me", skin.getStyle(CheckBoxStyle.class), "checkbox");
               final Slider slider = new Slider(0, 10, 1, skin.getStyle(SliderStyle.class), "slider");
               final TextField textfield = new TextField("", "Click here!", skin.getStyle(TextFieldStyle.class), "textfield");
               final SelectBox dropdown = new SelectBox(new String[] {"Android", "Windows", "Linux", "OSX"},
                       skin.getStyle(SelectBoxStyle.class), "combo");
               final Image imageActor = new Image(image2);
               final FlickScrollPane scrollPane = new FlickScrollPane(imageActor, "flickscroll");
               final List list = new List(listEntries, skin.getStyle(ListStyle.class), "list");
               final ScrollPane scrollPane2 = new ScrollPane(list, skin.getStyle(ScrollPaneStyle.class), "scroll");
               final SplitPane splitPane = new SplitPane(scrollPane, scrollPane2, false, skin.getStyle("default-horizontal",
                       SplitPaneStyle.class), "split");
               final Label fpsLabel = new Label("fps:", skin.getStyle(LabelStyle.class), "label");

               // configures an example of a TextField in password mode.
               final Label passwordLabel = new Label("Textfield in password mode: ", skin);
               final TextField passwordTextField = new TextField("", "password", skin);
               passwordTextField.setPasswordCharacter('*');
               passwordTextField.setPasswordMode(true);

               // window.debug();
               Window window = new Window("Dialog", skin.getStyle(WindowStyle.class), "window");
               window.x = window.y = 0;
               window.defaults().spaceBottom(5);
               window.row().fill().expandX();
               window.add(button).fill(0f, 0f);
               window.add(buttonMulti);
               window.add(imgButton);
               window.add(imgToggleButton);
               window.row();
               window.add(checkBox);
               window.add(slider).minWidth(100).fillX().colspan(3);
               window.row();
               window.add(dropdown);
               window.add(textfield).minWidth(100).expandX().fillX().colspan(3);
               window.row();
               window.add(splitPane).fill().expand().colspan(4).maxHeight(150);
               window.row();
               window.add(passwordLabel).colspan(2);
               window.add(passwordTextField).minWidth(100).expandX().fillX().colspan(2);
               window.row();
               window.add(fpsLabel).colspan(4);
               window.pack();

               // stage.addActor(new Button("Behind Window", skin));
               stage.addActor(window);

               textfield.setTextFieldListener(new TextFieldListener() {
                       public void keyTyped (TextField textField, char key) {
                               if (key == '\n') textField.getOnscreenKeyboard().show(false);
                       }
               });

               slider.setValueChangedListener(new ValueChangedListener() {
                       public void changed (Slider slider, float value) {
                               Gdx.app.log("UITest", "slider: " + value);
                       }
               });
       }

       @Override
       public void render (float delta) {
               Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
               Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

               ((Label)stage.findActor("label")).setText("fps: " + Gdx.graphics.getFramesPerSecond());

               stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
               stage.draw();
               Table.drawDebug(stage);
       }

       @Override
       public void resize (int width, int height) {
               stage.setViewport(width, height, false);
       }

       @Override
       public void dispose () {
               stage.dispose();
               skin.dispose();
               texture1.dispose();
               texture2.dispose();
       }

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
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

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	

}
