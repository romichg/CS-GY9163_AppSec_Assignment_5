package edu.nyu.appsec.assignment5;

//import android.Manifest;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
import android.net.Uri;
//import android.net.http.SslError;
//import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
//import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//import java.io.BufferedInputStream;
//import java.io.IOException;
//import java.io.SerializablePermission;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String SPELL_CHECK_URL = "http://appsecclass.report:8080/";
    private static final String KNOWN_HOST = "appsecclass.report";

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = String.valueOf(request.getUrl());
            String host = Uri.parse(url).getHost();

            if (KNOWN_HOST.equals(host)) {
                return false;
            }

            /* RG>> We should not be opening a browser to a URL we don't recognize.
             * We just return false and don't do nothing

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            */
            return true;
        }
    }

    // RG>> No need for the spying location service! We don't spy on people!

    /* Get location data to provide language localization
    *  Supported languages ar-DZ zh-CN en-US en-IN en-AU fr-FR
    */
    /* RG Commenting out spying location service
    @Override
    public void onLocationChanged(Location location) {
        URL url = null;
        try {
            url = new URL(SPELL_CHECK_URL + "metrics"
                    +"?lat="
                    +location.getLatitude()+"&long=" + location.getLongitude()
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

    /* Necessary to implement the LocationListener interface
    */
    /* RG>> Commenting out spying location service
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {}

    @Override
    public void onProviderDisabled(String s) {}
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView view = new WebView(this);
        view.setWebViewClient(new MyWebViewClient());

        // WebSettings settings = view.getSettings(); RG>> And since we commented out all below, we don't need this
        // settings.setAllowFileAccessFromFileURLs(true); RG>> No need to allow file access
        // settings.setJavaScriptEnabled(true); RG>> No need for java script. There is no JS in our pages
        // settings.setAllowUniversalAccessFromFileURLs(true); RG >> No need for file access

        /* RG>> There is no need for the spying location service.. naughty
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        */

        setContentView(view);
        view.loadUrl(SPELL_CHECK_URL + "register");
    }
}
