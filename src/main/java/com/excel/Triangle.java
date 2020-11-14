package com.excel;

//打印三角形  5行
public class Triangle {
    public static void main(String[] args) {

        for(int i=1;i<=5;i++){
            for (int j=5;j>=i;j--){
                System.out.print(" ");
            }
           for (int j=1;j<=i;j++){
               System.out.print("*");
           }
           for (int j=1;j<i;j++){
               System.out.print("*");
           }
            System.out.println();
        }

    }
}
