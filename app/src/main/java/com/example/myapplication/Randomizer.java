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

}