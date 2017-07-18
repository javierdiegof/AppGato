package com.cursoslicad.android.appgato;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by javier on 7/18/17.
 */

public class ResActivity extends AppCompatActivity {
    private TextView vxTextView;
    private TextView voTextView;
    private TextView porcentajesTextView;
    private static final String TAG = "ResActivity";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        Log.d(TAG, "llamado onCreate()");

        vxTextView = (TextView) findViewById(R.id.txt_view_victorias_x);
        voTextView = (TextView) findViewById(R.id.txt_view_victorias_o);
        porcentajesTextView = (TextView) findViewById(R.id.txt_view_porcentajes);

        Intent intent = getIntent();
        int victoriasX = intent.getIntExtra(GatoActivity.EXTRA_VICTORIAS_X,0);
        int victoriasO = intent.getIntExtra(GatoActivity.EXTRA_VICTORIAS_O,0);


        Resources res = getResources();
        String textoVictX = String.format(res.getString(R.string.num_victorias), victoriasX);
        String textoVictO = String.format(res.getString(R.string.num_victorias), victoriasO);
        vxTextView.setText(textoVictX);
        voTextView.setText(textoVictO);
        if(victoriasO == 0 && victoriasX == 0){
            porcentajesTextView.setText(R.string.val_nulo);
            return;
        }

        float porcentajeX = (float) victoriasX/(victoriasX + victoriasO)*100;
        float porcentajeO = (float) victoriasO/(victoriasX + victoriasO)*100;
        String textoPorcentajes = String.format(
                res.getString(R.string.val_porcentajes), porcentajeX, porcentajeO);

        porcentajesTextView.setText(textoPorcentajes);
    }
    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "llamada onStart()");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "llamado onResume()");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "llamada onPause()");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "llamada onStop()");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "llamada onDestroy()");
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Log.d(TAG, "llamada onRestart()");
    }

}
