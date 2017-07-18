package com.cursoslicad.android.appgato;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GatoActivity extends AppCompatActivity {
    public static final String EXTRA_VICTORIAS_X = "com.cursoslicad.android.appgato.VICTORIAS_X";
    public static final String EXTRA_VICTORIAS_O = "com.cursoslicad.android.appgato.VICTORIAS_O";
    public static final String TURNO_INICIO = "turno_inicio";
    public static final String TURNO_ACTUAL = "turno_actual";
    public static final String CELDAS_OCUPADAS = "celdas_ocupadas";
    public static final String VICTORIAS_X = "victorias_x";
    public static final String VICTORIAS_O = "victorias_o";
    public static final String ESTADO_TABLERO = "estado_tablero";
    public static final String TEXTV_GANADOR = "textv_ganador";
    private static final String TAG = "GatoActivity";
    private Tablero tablero = new Tablero();

    GridLayout tableroGridLayout;
    TextView ganadorTextview;
    Button resetButton;
    Button resultadosButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gato);
        Log.d(TAG, "llamada onCreate()");

        ganadorTextview = (TextView) findViewById(R.id.text_view);
        resetButton = (Button) findViewById(R.id.reset_button);
        tableroGridLayout= (GridLayout) findViewById(R.id.grid_layout);
        resultadosButton = (Button) findViewById(R.id.boton_resultados);

        if(savedInstanceState != null){
            restablecerEstado(savedInstanceState, tableroGridLayout, ganadorTextview);
        }


        // Se configura el click para los botones del tablero
        int childCount = tableroGridLayout.getChildCount();
        for(int i = 0; i < childCount; i++){
            Button boton = (Button) tableroGridLayout.getChildAt(i);
            boton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            botonPresionado(v);
                        }
                    }
            );
        }

        // Se configura el click para el botón de reseteo
        resetButton.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Log.d(TAG, "Boton de reset presionado");
                        resetVista();
                    }
                }
        );

        resultadosButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mostrarResultados(GatoActivity.this);
                    }
                }
        );

    }


    /*
  * Cuando se presiona un botón se debe de cambiar el texto del mismo y realizar las operaciones
  * correspondientes en el modelo.
  * */
    private void botonPresionado(View v){
        Button boton = (Button) v;
        int posicion = Integer.parseInt(v.getTag().toString());

        // Se coloca el texto correspondiente al turno actual
        if(tablero.getTurnoActual().equals(Tablero.JUGADOR_X)){
            boton.setText("X");
        }
        else{
            boton.setText("O");
        }
        // Se bloquea el boton para que ya no se pueda hacer click
        boton.setEnabled(false);
        String ganador = tablero.clickBoton(posicion);

        // Si hubo algún ganador, debe de colocar el texto en la etiqueta correspondiente
        if(ganador != null){
            if(ganador.equals(Tablero.JUGADOR_X)){
                ganadorTextview.setText("Ganador: " + Tablero.JUGADOR_X);
                tablero.incVictoriasX();
            }
            else if(ganador.equals(Tablero.JUGADOR_O)){
                ganadorTextview.setText("Ganador: " + Tablero.JUGADOR_O);
                tablero.incVictoriasO();
            }
            // Hubo un empate
            else{
                ganadorTextview.setText("Empate!");
            }
            desactivarBotones();
        }
    }

    // En caso de que alguien haya ganado (o sea un empate), se deben de bloquear todos los botones
    private void desactivarBotones(){
        for(int i = 0; i < tableroGridLayout.getChildCount(); i++){
            Button boton = (Button) tableroGridLayout.getChildAt(i);
            boton.setEnabled(false);
        }
    }

    // Se regresan los botones a la configuración inicial
    private void resetVista(){
        tablero.resetTablero();
        ganadorTextview.setText("");
        for(int i = 0; i < tableroGridLayout.getChildCount(); i++){
            Button boton = (Button) tableroGridLayout.getChildAt(i);
            boton.setText("");
            boton.setEnabled(true);
        }
    }

    private void mostrarResultados(Context context){
        Intent intent = new Intent(context, ResActivity.class);
        intent.putExtra(EXTRA_VICTORIAS_X, tablero.getVictoriasX());
        intent.putExtra(EXTRA_VICTORIAS_O, tablero.getVictoriasO());
        startActivity(intent);
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
        resetVista();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "llamada onSaveInstanceState()");
        outState.putString(TURNO_INICIO, tablero.getTurnoInicio());
        outState.putString(TURNO_ACTUAL, tablero.getTurnoActual());
        outState.putInt(CELDAS_OCUPADAS, tablero.getCeldasOcupadas());
        outState.putInt(VICTORIAS_X, tablero.getVictoriasX());
        outState.putInt(VICTORIAS_O, tablero.getVictoriasO());

        TextView textView = (TextView) findViewById(R.id.text_view);
        outState.putString(TEXTV_GANADOR, (String) textView.getText());

        Boton[] arregloBotones = tablero.getArrBotones();
        String[] valoresCeldas =  new String[arregloBotones.length];

        for(int i = 0; i < arregloBotones.length; i++){
            valoresCeldas[i] = arregloBotones[i].getMarcaCelda();
        }
        outState.putStringArray(ESTADO_TABLERO, valoresCeldas);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "llamada onRestoreInstanceState()");
    }

    private void restablecerEstado(Bundle savedIS,
                                   GridLayout tableroGridLayout,
                                   TextView ganadorTextview){
        tablero.setTurnoInicio(savedIS.getString(TURNO_INICIO));
        tablero.setTurnoActual(savedIS.getString(TURNO_ACTUAL));
        tablero.setCeldasOcupadas(savedIS.getInt(CELDAS_OCUPADAS));
        tablero.setVictoriasX(savedIS.getInt(VICTORIAS_X));
        tablero.setVictoriasO(savedIS.getInt(VICTORIAS_O));

        String textoGanador = savedIS.getString(TEXTV_GANADOR);
        if(textoGanador != ""){
            ganadorTextview.setText(textoGanador);
            desactivarBotones();
        }

        String[] valoresCeldas = savedIS.getStringArray(ESTADO_TABLERO);
        for(int i = 0; i < tableroGridLayout.getChildCount(); i++){
            String marcaBoton = valoresCeldas[i];
            tablero.setMarcaHijo(i, marcaBoton);
            if(marcaBoton != Tablero.CELDA_VACIA){
                Button boton = (Button) tableroGridLayout.getChildAt(i);
                boton.setText(valoresCeldas[i]);
            }
        }
    }



}
