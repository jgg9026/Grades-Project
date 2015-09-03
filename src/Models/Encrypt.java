/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
     
    private static String toHex(byte[] digest){
        String hash = "";
        for(byte aux : digest){
            int b = aux & 0xff;
            if(Integer.toHexString(b).length() ==1)
                hash += "0";
            hash += Integer.toHexString(b);
        }
        return hash;
    }
    
    public String getStringMenssageDigest(String msj, String alg) throws NoSuchAlgorithmException{
        byte[] digest = null;
        byte[] buffer = msj.getBytes();
        try{
            MessageDigest messageDigest = MessageDigest.getInstance(alg);
            messageDigest.reset();
            messageDigest.update(buffer);
            digest = messageDigest.digest();
        }catch (NoSuchAlgorithmException ex){
            ex.printStackTrace();
        } return toHex(digest);
    }
}
