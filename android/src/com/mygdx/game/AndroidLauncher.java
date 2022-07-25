package com.mygdx.game;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;


public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //para que nunca gire la pantalla
		//Gyroscopio
		config.useGyroscope = true;
		config.useAccelerometer = true;
		config.useCompass = false;

		initialize(new MainGame(), config);
	}
}
