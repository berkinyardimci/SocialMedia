package com.socialmedia.utility;


public class CodeGenerator {

    public static String generateCode(String value){
        String [] data=value.split("-");
        StringBuilder newCode=new StringBuilder();
        for ( String s : data ){
            newCode.append(s.charAt(0));
        }
        return newCode.toString();
    }
}
