package com.excel;

/**
 * 输入99乘法表
 */



public class Nine {
    public static void main(String[] args) {
        for (int j=1;j<=9;j++) {
            for (int i = 1; i <= j; i++) {
                System.out.print(j + "*" + i + "=" + (i * j) + "\t");
            }
            System.out.println();

        }
    }
}
