package com.example.isaac.directorioudg.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.example.isaac.directorioudg.R;

public class Helper {

	Context context;
	public Helper(Context context) {
		this.context=context;
	}

	public Boolean isConect() {

		ConnectivityManager connectivity = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
		if (activeNetwork != null) { // connected to the internet
			if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
				// connected to wifi
				//Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();

			} else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
				// connected to the mobile provider's data plan
				//Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
			}
			return true;
		}
		return false;
	}
	public void changeTheme(Context context, String nametheme,Toolbar toolbar,Window window) {
		int theme;
		int colorPrimary;
		int colorPrimaryDark;
		if(nametheme.equalsIgnoreCase("AppTheme")) {
			theme= R.style.AppTheme;
			colorPrimary=context.getResources().getColor(R.color.colorPrimary);
			colorPrimaryDark=context.getResources().getColor(R.color.colorPrimaryDark);
		}else {
			theme=R.style.AppThemeNight;
			colorPrimary=context.getResources().getColor(R.color.colorPrimaryNight);
			colorPrimaryDark=context.getResources().getColor(R.color.colorPrimaryDarkNight);
		}
		context.setTheme(theme);
		toolbar.setBackgroundColor(colorPrimary);
		// clear FLAG_TRANSLUCENT_STATUS flag:
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		// finally change the color
		window.setStatusBarColor(colorPrimaryDark);
	}
	public void changeTheme(Context context, String nametheme) {
		int theme;
		int colorPrimary;
		int colorPrimaryDark;
		if(nametheme.equalsIgnoreCase("AppTheme")) {
			theme= R.style.AppTheme;
			colorPrimary=context.getResources().getColor(R.color.colorPrimary);
			colorPrimaryDark=context.getResources().getColor(R.color.colorPrimaryDark);
		}else {
			theme=R.style.AppThemeNight;
			colorPrimary=context.getResources().getColor(R.color.colorPrimaryNight);
			colorPrimaryDark=context.getResources().getColor(R.color.colorPrimaryDarkNight);
		}
		context.setTheme(theme);
	}
    
}
