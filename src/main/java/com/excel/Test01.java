package com.excel;

import java.util.ArrayList;

/**
 * 定义一个方法listTest(ArrayList<Integer> al, Integer s)，
 * 要求返回s在al里面第一次出现的索引，如果s没出现过返回-1。
 */

public class Test01 {
    public static void main(String[] args) {
        //new一个数组
        ArrayList<Integer> list = new ArrayList<Integer>();
        int a =0 ;
        //调用方法
        listTest(list,a);
    }


public static int listTest(ArrayList<Integer> al,Integer s){
    //遍历循环数组al
    for(int i=0;i<al.size();i++){
        //判断s是否与al中的元素相等,如果相等，返回下标值
        if (s==al.get(i)){
            return i;
        }
    }
    //不相等返回-1
    return -1;
}
}

