package com.digitale.stardust;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.digitale.mygdxgame.BattleBeasties3d;
public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
             AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
         cfg.useGL20 = false; 
          initialize(new BattleBeasties3d(null),     cfg);  
    }
} 