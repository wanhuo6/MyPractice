package com.ahuo.practise.util;

/**
 * description :
 * author : LiuHuiJie
 * created on : 2017-9-28
 */
public class Test {

    private static double a=1.0323;

    public static void main(String[] args) {

        getValue(45);
    }

    public static void getValue(int i,int s){

        for (i=i;i<s;i++){
            System.out.println(a*i+"dp");
        }

    }

    public static void getValue(int i){
            System.out.println(a*i+"dp");
    }
}

