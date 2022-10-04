package com.liu.utils;

import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.Base64;

public class Base64Util {
    public static void convertBase64ToImage(String base64Code,String path){
        base64Code = base64Code.replace("data:image/png;base64,","");
        base64Code = base64Code.replace("data:image/jpeg;base64,","");
        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64Code);
            fos = new java.io.FileOutputStream(path);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
