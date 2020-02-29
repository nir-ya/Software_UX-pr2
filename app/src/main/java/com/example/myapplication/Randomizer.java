package com.example.myapplication;

import java.security.SecureRandom;
import java.text.Format;
import java.text.SimpleDateFormat;

public class Randomizer {
    public static int generate(int min,int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public static String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    public static Format formatter = new SimpleDateFormat("HH:mm");

    public static int randomSadSound(){
        int rand = generate(0,5);
        switch (rand){
            case 0:
                return R.raw.basa_sound;
            case 1:
                return R.raw.basa_whitney;
            case 2:
                return R.raw.letitgoo;
            case 3:
                return R.raw.sad_trombone;
            case 4:
                return R.raw.believefly_basa;
            case 5:
                return R.raw.titanic_basa;
        }
        return R.raw.basa_whitney;
    }

}