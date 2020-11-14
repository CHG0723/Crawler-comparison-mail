package com.excel;


public class XiShu {
    public static void main(String[] args) {
      //  1、创建一个二维数组11*11   0:没有棋子   1：黑棋  2：白棋
        int[][] array = new int[11][11];
        array[1][2] = 1;
        array[2][3] = 2;
        System.out.println("输出原始的数组:");

        for (int[] ints:array){
            for (int anInt : ints) {
                System.out.print(anInt+"\t");
            }
            System.out.println();
        }

        System.out.println("--------------------------------");
        //转换为稀疏数组保存
        //获取有效值的个数
        int sum = 0;
        for (int i = 0; i <11 ; i++) {
            for (int j = 0; j <11 ; j++) {

                if (array[i][j]!=0){
                    sum++;
                }
            }
        }
        System.out.println("有效值的个数为:"+sum);

        //创建一个稀疏数组的数组
        int[][] array2 = new int[sum+1][3];
        //存的行数
        array2[0][0] = 11;
        //存的列数
        array2[0][1] = 11;
        //存的个数
        array2[0][2] = sum;

        //遍历二维数组，将非0 的值，存放到稀疏数组中
        int count = 0;
        for (int i = 0;i<array.length;i++){
            for (int j = 0;j<array[i].length;j++){
                if (array[i][j]!=0){
                    count++;
                    array2[count][0] = i;
                    array2[count][1] = j;
                    array2[count][2] = array[i][j];

                }
            }
        }
        //输出稀疏数组
        System.out.println("稀疏数组为:");
        for (int i=0;i<array2.length;i++){
            System.out.println(array2[i][0]+"\t"
            +array2[i][1]+"\t"
            +array2[i][2]+"\t");
        }
        System.out.println("--------------------------------");

        System.out.println("还原稀疏数组");
        //1.读取稀疏数组
        //数组的长宽
        int[][] array3 = new int[array2[0][0]][array2[0][1]];

        //2给其中的元素还原值
        for (int i=1;i<array2.length;i++){
            array3[array2[i][0]][array2[i][1]] = array2[i][2];
        }
        //3.打印
        System.out.println("输出还原的数组:");

        for (int[] ints:array3){
            for (int anInt : ints) {
                System.out.print(anInt+"\t");
            }
            System.out.println();
        }
     }
}
