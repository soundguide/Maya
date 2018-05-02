package com.soundguide.maya;
//ca-app-pub-7224060706498311~6590590943  BannerBlock

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.soundguide.maya.Maia;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.soundguide.maya.AdHandler;
import com.soundguide.maya.Maia;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.soundguide.maya.Screens.GameOverScreen;
import com.soundguide.maya.Screens.GameWonScreen;
import com.soundguide.maya.Screens.PlayScreen;
import com.soundguide.maya.Sprites.Maya;
import com.startapp.android.publish.ads.banner.Banner;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

public class AndroidLauncher extends AndroidApplication  {
	private static final String TAG = "AndroidLauncher";
	protected AdView adView;


	Handler handler = new Handler() {


	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StartAppSDK.init(this, "208275821", true);
		RelativeLayout layout = new RelativeLayout(this);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new Maia(), config);

		layout.addView(gameView);

		setContentView(layout);

	}
	@Override
	public void onBackPressed() {
		StartAppAd.onBackPressed(this);
		super.onBackPressed();
	}
//https://github.com/StartApp-SDK/Documentation/wiki/android-advanced-usage
	}
