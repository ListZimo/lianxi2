package com.example.chuankou;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
//	private WebView webView;
	private ImageView tupian;
//	private Bitmap bitmap;
	
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		tupian = (ImageView) findViewById(R.id.imageView1);
		
		thread.start();

		
		
		
	}
	
	private void http_get() {
		// TODO Auto-generated method stub
		
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet("http://172.16.68.13/snapshot.cgi?user=admin&pwd=");
			HttpResponse httpResponse  = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				
				byte[] buff = EntityUtils.toByteArray(httpEntity);
				System.out.println(buff.length);

						Message msg = new Message();
						msg.obj = buff;
						handler.sendMessage(msg);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Thread thread = new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true)
			{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				http_get();
			}
			
		}
	});
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			byte[] buff = (byte[]) msg.obj;
			Bitmap bitmap = BitmapFactory.decodeByteArray(buff, 0,buff.length);
			tupian.setImageBitmap(bitmap);
			
//			jieshou.setText(msg.obj.toString());
		};
	};
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
