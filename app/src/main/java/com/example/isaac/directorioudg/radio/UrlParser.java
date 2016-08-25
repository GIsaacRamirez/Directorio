package com.example.isaac.directorioudg.radio;

import android.annotation.SuppressLint;
import com.example.isaac.directorioudg.radio.parser.M3UParser;
import java.util.LinkedList;

public class UrlParser {
	@SuppressLint("DefaultLocale") // Does not matter, no text but URL
	public static String getUrl(String url)
	{
		String mUrl = url.toUpperCase();
		if(mUrl.endsWith(".FLAC"))
		{
			return url;
		} else if(mUrl.endsWith(".MP3")) {
			return url;
		} else if(mUrl.endsWith(".WAV")) {
			return url;
		} else if (mUrl.endsWith(".M4A")) {
			return url;
		} else if(mUrl.endsWith(".M3U")) {
			M3UParser mM3u = new M3UParser();
			LinkedList<String> urls = mM3u.getRawUrl(url);
			if((urls.size()>0)) {
				return urls.get(0);
			}
		}
		return url;
	}
}
