package com.cursoslicad.android.appgato;

/**
 * Created by javier on 7/18/17.
 */

public class Tablero {
    private static final int NUM_COLUMN = 3;
    private static final int NUM_RENGLON = 3;
    public static final String CELDA_VACIA = "V";
    public static final String JUGADOR_X = "X";
    public static final String JUGADOR_O = "O";
    public static final String EMPATE = "=";
    private String mTurnoInicio;
    private String mTurnoActual;
    private int mCeldasOcupadas;
    private int mVictoriasX = 0;
    private int mVictoriasO = 0;
    private Boton[] mArrBotones;

    // Getters y setters
    public String getTurnoActual() {
        return mTurnoActual;
    }
    public int getVictoriasX() {
        return mVictoriasX;
    }

    public int getVictoriasO() {
        return mVictoriasO;
    }

    public void incVictoriasX(){
        mVictoriasX ++;
    }

    public void incVictoriasO(){
        mVictoriasO ++;
    }

    public String getTurnoInicio() {
        return mTurnoInicio;
    }

    public void setTurnoInicio(String turnoInicio) {
        mTurnoInicio = turnoInicio;
    }

    public void setTurnoActual(String turnoActual) {
        mTurnoActual = turnoActual;
    }

    public int getCeldasOcupadas() {
        return mCeldasOcupadas;
    }

    public void setCeldasOcupadas(int celdasOcupadas) {
        mCeldasOcupadas = celdasOcupadas;
    }

    public static String getJugadorX() {
        return JUGADOR_X;
    }

    public static String getJugadorO() {
        return JUGADOR_O;
    }

    public void setVictoriasX(int victoriasX) {
        mVictoriasX = victoriasX;
    }

    public void setVictoriasO(int victoriasO) {
        mVictoriasO = victoriasO;
    }


    public Boton[] getArrBotones() {
        return mArrBotones;
    }


    public void setMarcaHijo(int indice, String marcaCelda){
        mArrBotones[indice].setMarcaCelda(marcaCelda);
    }

    //*Getters y setters


    // Constructor del tablero
    public Tablero(){
        if(mArrBotones == null){
            iniciarTablero();
        }
        resetTablero();
    }

    // En un inicio, se debe de clear el arreglo que alojará a los botones (modelo)
    public void iniciarTablero(){
        mArrBotones = new Boton[NUM_COLUMN*NUM_RENGLON];
        for(int i = 0; i < NUM_COLUMN*NUM_RENGLON; i++){
            mArrBotones[i] = new Boton();
        }
    }

    // Reinicia los valores del tablero a default
    public void resetTablero(){
        mTurnoInicio = cambiarTurno(mTurnoInicio);
        mTurnoActual = mTurnoInicio;
        mCeldasOcupadas = 0;
        for(int i = 0; i < NUM_COLUMN*NUM_RENGLON; i++){
            iniciarBoton(i);
        }
    }

    // Asigna marca de vacío al botón
    public void iniciarBoton(int posicion){
        Boton boton = mArrBotones[posicion];
        boton.setMarcaCelda(CELDA_VACIA);
    }

    // Se debe de alternar el turno de inicio con cada partida
    public String cambiarTurno(String turnoACambiar){
        if(turnoACambiar == null){
            turnoACambiar = JUGADOR_X;
        }
        else if(turnoACambiar == JUGADOR_X){
            turnoACambiar = JUGADOR_O;
        }
        else{
            turnoACambiar = JUGADOR_X;
        }
        return turnoACambiar;
    }

    // Se configura el botón presionado y se cambia el turno
    public String clickBoton(int posicion){
        mCeldasOcupadas ++;
        Boton actual = mArrBotones[posicion];
        actual.setMarcaCelda(mTurnoActual);
        String hayGanador = checarGanador();
        mTurnoActual = cambiarTurno(mTurnoActual);
        return hayGanador;
    }

    // Checa si alguien ha ganado con el movimiento
    public String checarGanador() {
        // Primero se checa que los renglones sean iguales
        for (int i = 0; i < NUM_RENGLON * NUM_COLUMN; i += 3) {
            String res = mArrBotones[i].getMarcaCelda();
            if (
                    mArrBotones[i].getMarcaCelda().equals(mArrBotones[i+1].getMarcaCelda()) &&
                            mArrBotones[i + 1].getMarcaCelda().equals(mArrBotones[i+2].getMarcaCelda()) &&
                            ! mArrBotones[i].getMarcaCelda().equals(CELDA_VACIA)
                    ) {
                return mTurnoActual;
            }
        }

        // Checando columnas
        for (int i = 0; i < NUM_COLUMN; i++) {
            if (
                    mArrBotones[i].getMarcaCelda().equals(mArrBotones[i+ 3].getMarcaCelda()) &&
                            mArrBotones[i+ 3].getMarcaCelda().equals(mArrBotones[i+ 6].getMarcaCelda())
                            && ! mArrBotones[i].getMarcaCelda().equals(CELDA_VACIA)
                    ) {
                return mTurnoActual;
            }
        }

        // Checando una diagonal
        if (
                mArrBotones[0].getMarcaCelda().equals(mArrBotones[4].getMarcaCelda()) &&
                        mArrBotones[4].getMarcaCelda().equals(mArrBotones[8].getMarcaCelda())
                        && ! mArrBotones[0].getMarcaCelda().equals(CELDA_VACIA)
                ) {
            return mTurnoActual;
        }

        // Checando otra diagonal
        if (
                mArrBotones[2].getMarcaCelda().equals(mArrBotones[4].getMarcaCelda()) &&
                        mArrBotones[4].getMarcaCelda().equals(mArrBotones[6].getMarcaCelda())
                        && ! mArrBotones[2].getMarcaCelda().equals(CELDA_VACIA)
                ) {
            return mTurnoActual;
        }
        if(mCeldasOcupadas == 9){
            return EMPATE;
        }

        return null;
    }

}
