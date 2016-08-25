package com.example.isaac.directorioudg.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
    
}
