package strzlee.webviewapp;

import android.os.Bundle;
import android.os.Build;
import android.app.Activity;
import android.view.View;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.GeolocationPermissions;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	WebView mainWebView;
	LinearLayout mainLayout;
		
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mainLayout = (LinearLayout)findViewById(R.id.mainLayout);
        mainWebView = (WebView) findViewById(R.id.mainWebView);
        
        WebSettings webSettings = mainWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        
        mainWebView.setWebViewClient(new MyCustomWebViewClient());
        mainWebView.setWebChromeClient(new MyChromeClient());
        mainWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mainWebView.setBackgroundColor(0);
        if (Build.VERSION.SDK_INT >= 11){
            mainWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        
        mainWebView.loadUrl("http://www.jausentest.at/");
    }
	
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK) && mainWebView.canGoBack()) {
	        mainWebView.goBack();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
    }
	
	
    private class MyCustomWebViewClient extends WebViewClient {
    	
       @Override
       public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        } 
    	
        @Override
        public void onPageFinished(WebView view, String url) {
            findViewById(R.id.splashScreen).setVisibility(View.GONE);
            findViewById(R.id.mainWebView).setVisibility(View.VISIBLE);
        }
    }
    
    final class MyChromeClient extends WebChromeClient {
    	
    	public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
        }
        
    }
      
}
