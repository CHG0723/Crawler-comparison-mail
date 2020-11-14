package com.excel;

import java.io.File;

/**
 *  编写一个程序，完成目录的删除。
 *  例如：
 * 	将  D:\test文件夹删除 文件夹中有一个 a.txt文件
 *   提示：
 * 	需要使用递归！！！（方法的递归调用。)
 */
public class Test02 {
    public static void main(String[] args) {
        File file  = new File("D:\\test\\a.txt");
        delete(file);
    }

    //删除方法
    public static boolean delete(File file){
        //判断文件是否存在
        if (!file.exists()){
            return false;
        }
        //判断是否为目录
        if (!file.isDirectory()){
            String[]  dir = file.list();
           //递归删除
            for (String d:dir){
                File files = new File(file,d);
                files.delete();
            }
        }
        return true;
    }
}
