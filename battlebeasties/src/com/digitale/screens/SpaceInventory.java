/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com

 */

package com.digitale.screens;

import com.badlogic.gdx.Gdx;
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
import com.digitale.utils.Actors;
import com.digitale.utils.Actors.DialogListener;
import com.digitale.utils.InventoryIcon;
import com.digitale.utils.InventoryUtils;

/**
 * inventory screen needs 2 forms one for when ship is docked that allows
 * trading of items and one for when ship is in space that only displays ship
 * cargo
 * 
 * @author rbeech
 */
public class SpaceInventory extends StardustScreen {
	String[] listEntries = { null, null, null, null, null, null, null, null,
			null, null };

	Skin skin;
	Actor dialog;
	Actor root;
	
	/** the cross fade texture **/

	protected boolean doneflag;

	protected String selectedCharacter;

	public SpaceInventory(Stage stage) {
		
		BattleBeasties3d.myInventory.clear();
		BattleBeasties3d.MyDataOp.get3dChar(Integer.valueOf(BattleBeasties3d.currencharacteruid));
		BattleBeasties3d.MyDataOp.getInventory(Integer
				.valueOf(BattleBeasties3d.currencharacteruid),false);		
		skin = new Skin(Gdx.files.internal("data/uiskin.json"),
				Gdx.files.internal("data/uiskin.png"));
		Gdx.input.setInputProcessor(stage);

		// Group.debug = true;
		
		final Label cargoLabel = new Label("Cargo",
				skin.getStyle(LabelStyle.class), "cargolable");
		final Button buttonClose = new TextButton("Close",
				skin.getStyle(TextButtonStyle.class), "button-close");
		final Label balance = new Label(BattleBeasties3d.myCharacter.getCredits()+" $D",
				skin.getStyle(LabelStyle.class), "balancelable");
		final Table gridCargo = initGridCargo();
		final ScrollPane paneCargo = new ScrollPane(gridCargo,
				skin.getStyle(ScrollPaneStyle.class), "cargo");
		

		Window window = new Window("Space Inventory Screen",
				skin.getStyle(WindowStyle.class), "spaceInventoryWindow");
		if (BattleBeasties3d.DEBUG)
			window.debug();
		window.x = window.y = 0;
		window.setFillParent(true);
		window.setMovable(false);
		window.defaults().pad(5);
		window.defaults().spaceBottom(5);
		window.row().fill().expandX();
		window.add(balance);
		window.add();
		window.add();
		window.add(buttonClose).minWidth(200);
		window.row();
		window.add(cargoLabel).colspan(2);
		window.add().colspan(2);
		window.row().fill().expandY();
		window.add(paneCargo).colspan(4);
		window.pack();

		stage.addActor(window);
		buttonClose.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				
				System.out.println("inventory Close");
				SoundManager.playuiclick();
				BattleBeasties3d.MyDataOp.getInventory(Integer
						.valueOf(BattleBeasties3d.currencharacteruid),false);	
				BattleBeasties3d.stationScreen=103;
				Renderer.stage.clear();
			}
		});
	}

	/**
	 * @return
	 */
	private Table initGridCargo() {
		final Table gridCargo = new Table();
		if (BattleBeasties3d.DEBUG)
			gridCargo.debug();
		String itemIcon;
		int itemcounter = 0;
		gridCargo.defaults().height(64).width(64).align("topleft");
		for (int x = 0; x < BattleBeasties3d.myInventory.size(); x++) {

			final Inventory item = BattleBeasties3d.myInventory.get(x);
			
			if (item.getSlot_id() == 0) {
				itemIcon = "data/" + item.getIcon() + ".png";

				if (BattleBeasties3d.DEBUG) {
					System.out.println("icon " + itemIcon);
				}
				String uidString =""+ item.getInventoryid();
				final Image newItem = new Image(new TextureRegion(
						InventoryIcon.generateDynamicTexture(itemIcon, item.getCategory(),
								item.getQuality(),false,item.getCount())), Scaling.none,
						Align.CENTER, uidString);
				newItem.setClickListener(new ClickListener() {
					public void click(Actor actor, float x, float y) {
						SoundManager.playuiclick();
						if (BattleBeasties3d.DEBUG) {
							System.out.println("inventory pick" + newItem.name);
							
						}
						
						String dialogTitle = (item.getItem());
						String dialogTexts = InventoryUtils.makeItemInfo(item);

						DialogListener dialogListener = (new DialogListener() {
							@Override
							public void optionSelected(int option) {
								SoundManager.playuiclick();
								System.out.println("option " + option);
								//if close
								if (option == 1) {
								Renderer.stage.removeActor(dialog);
								}
								//if sell
								if (option == 0) {
									if(item.getBind() !=2){
									SoundManager.playboom();
									System.out.println("destroy "
											+ newItem.name );
									BattleBeasties3d.MyDataOp.newRequest("destroy#"
											+newItem.name);
									
									Renderer.stage.removeActor(newItem);
									Renderer.stage.removeActor(dialog);
									}else{
										Renderer.stage.addActor(Actors.bottomToast(
												"You cannot destroy this item.", 3,
												skin));
									}
								}
							}

							
						});

						dialog = (Actors.twoOptionsDialog(dialogTexts,
								dialogListener, dialogTitle, "Destroy", "Close",
								skin));
						Renderer.stage.addActor(dialog);
						SoundManager.playuiclick();

					}

				});
				gridCargo.add(newItem);
				itemcounter++;
			}
			if (itemcounter % 10 == 9) {
				gridCargo.row();
			}
		}
		gridCargo.pack();
		return gridCargo;
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
