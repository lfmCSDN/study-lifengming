package cn.lifengming.java8.demo;

import java.util.stream.IntStream;

/**
 * @author lifengming
 * @version 1.0
 * @date 2019-02-21 09:44
 */
public class LambdaDemo {
    public static void main(String[] args) {
        int[] nums = new int[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        int min = IntStream.of( nums ).min().getAsInt();
        System.out.println( min );

        //()->sout 左边是输入，右边是输出
        new Thread( ()->System.out.println( Thread.currentThread().getName() ) ).start();
    }

}
