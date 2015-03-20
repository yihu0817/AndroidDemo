package com.example.dampview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ImageView img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupView();
	}

	public void setupView() {
		img = (ImageView) findViewById(R.id.img);
		DampView view = (DampView) findViewById(R.id.dampview);
		view.setImageView(img);
	}
	

}
