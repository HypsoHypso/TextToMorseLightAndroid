package com.hypsofactory.texttomorse;

import android.util.Log;
import android.widget.Toast;

import java.util.*;

public class TextToMorseAlgorithm {

    // https://stackoverflow.com/questions/35017761/text-to-morse-translator-in-java
    public static String Encode(String plainText)
    {
        String alphabet = "abcdefghijklmnopqrstuvwxyz1234567890.,?'!/()&:;=+-_\"$@";
        String[] morseAlphabet = { ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....",
                                  "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.",
                                  "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-",
                                  "-.--", "--..", ".----", "..---", "...--", "....-", ".....",
                                  "-....", "--...", "---..", "----.", "-----", ".-.-.-", "--..--",
                                  "..--..", ".----.", "-.-.--", "-..-.", "-.--.", "-.--.-", ".-...",
                                  "---...", "-.-.-.", "-...-", ".-.-.", "-....-", "..--.-", ".-..-.",
                                  "...-..-", ".--.-." };
        String encodedText = "";
        plainText = plainText.toLowerCase();

        for (char alphabetLetter : plainText.toCharArray())
        {
            int index = -1;
            String letter = " ";
            for (int i = 0; i < alphabet.length(); i++)
            {
                if (alphabet.charAt(i) == alphabetLetter)
                {
                    index = i;
                }
            }
            if (index >=0)
            {
                letter = morseAlphabet[index];
            }
            encodedText += letter + "/";
        }

        return encodedText;
    }
}
