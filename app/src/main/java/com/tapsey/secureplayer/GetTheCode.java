package com.tapsey.secureplayer;
import android.content.Context;

import java.io.IOException;
import java.util.Scanner;

public class GetTheCode {


    static String getTheCode(Context context, String classname , String methodSignature ) {

        Scanner scanner = null;
        String source = "";
        try {
            scanner = new Scanner(context.getAssets().open(classname+".java"));



            while(scanner.hasNext()) {
                source += " "+ scanner.next();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        scanner.close();

        // extract code using the method signature

        methodSignature = methodSignature.trim();
        source = source.trim();

        //appending { to differentiate from argument as it can be matched also if in the same file
        methodSignature = methodSignature+"{";

        //making sure we find what we are looking for
        methodSignature = methodSignature.replaceAll("\\s*[(]\\s*", "(");
        methodSignature = methodSignature.replaceAll("\\s*[)]\\s*", ")");
        methodSignature = methodSignature.replaceAll("\\s*[,]\\s*", ",");
        methodSignature = methodSignature.replaceAll("\\s+", " ");


        source =source.replaceAll("\\s*[(]\\s*", "(");
        source = source.replaceAll("\\s*[)]\\s*", ")");
        source = source.replaceAll("\\s*[,]\\s*", ",");
        source = source.replaceAll("\\s+", " ");


        if(!source.contains(methodSignature)) return null;

        // trimming all text b4 method signature
        source = source.substring(source.indexOf(methodSignature));

        //getting last index, a methods ends when there are matching pairs of these {}
        int lastIndex = 0;

        int rightBraceCount = 0;
        int leftBraceCount = 0;

        char [] remainingSource = source.toCharArray();
        for (int i = 0; i < remainingSource.length ; i++
                ) {

            if(remainingSource[i] == '}'){

                rightBraceCount++;

                if(rightBraceCount == leftBraceCount){

                    lastIndex = (i + 1);
                    break;
                }

            }else if(remainingSource[i] == '{'){

                leftBraceCount++;
            }




        }


        return  source.substring(0,lastIndex);

    }

}