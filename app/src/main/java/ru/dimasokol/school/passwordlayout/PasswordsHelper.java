package ru.dimasokol.school.passwordlayout;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordsHelper {

    private final String[] mRussians;
    private final String[] mLatins;

    public PasswordsHelper(String[] russians, String[] latins) {
        if (russians.length != latins.length) {
            throw new IllegalArgumentException();
        }

        mRussians = russians;
        mLatins = latins;
    }

    public String convert(CharSequence source) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            String key = String.valueOf(Character.toLowerCase(c));

            for (int dict = 0; dict < mRussians.length; dict++) {
                if (key.equals(mRussians[dict])) {
                    result.append(Character.isUpperCase(c)?
                            mLatins[dict].toUpperCase() : mLatins[dict]);
                }
            }

            if (result.length() <= i) {
                result.append(c);
            }
        }

        return result.toString();
    }

    public int getQuality(CharSequence password)
    {
        int score = password.length();
//                Math.min(10,password.length());

if (password.toString().matches("(.*[0-9]+.*)"))
    score=score+10;
if (password.toString().matches("(.*[A-Z]+.*)"))
            score=score+10;
Pattern p = Pattern.compile("[^A-Za-z0-9]");
Matcher m = p.matcher(password.toString());
if (m.find())
    score=score+10;
return Math.min(score/10,4);
    }
    public String gen_password(int length, Boolean flag_uppercase,Boolean flag_digits,Boolean flag_chars)
    {  String uppercase="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digits="0123456789";
        String chars="!@#$%^&*()_+=-±§<>?.,/`'|;:";
        String dict="abcdefghijklmnopqrstuvwxyz";
        if (flag_uppercase)
            dict=dict+uppercase;
        if (flag_digits)
            dict=dict+digits;
        if (flag_chars)
            dict=dict+chars;
        StringBuilder s=new StringBuilder();
        Random random = new Random();
        for (int i=0;i<length;i++)
            s.append (dict.charAt(random.nextInt(dict.length())));
        return(s.toString());
    }
}
