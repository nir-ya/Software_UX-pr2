package com.example.myapplication;

import java.security.SecureRandom;
import java.text.Format;
import java.text.SimpleDateFormat;

public class Randomizer {

    static final int[] SAD_SOUNDS = new int[]{R.raw.basa_whitney, R.raw.basa_sound,
            R.raw.sad_trombone, R.raw.letitgoo, R.raw.believefly_basa, R.raw.titanic};

    private static int generate(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    static Format formatter = new SimpleDateFormat("HH:mm");

    static int randomSadSound() {
        return SAD_SOUNDS[generate(0, SAD_SOUNDS.length - 1)];
    }

}