package cn.lifengming.java8.demo;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

/**
 * @author lifengming
 * @version 1.0
 * @date 2019-02-21 10:38
 * Predicate接口和Consumer接口的使用
 */
public class PredicateAndConsumerDemo {
    public static void main(String[] args) {
        // 断言函数接口
        Predicate<Integer> predicate = i -> i > 0;
        System.out.println(predicate.test(-9));

        // 消费函数接口
        Consumer<String> consumer = System.out::println;
        consumer.accept("这是输入的数据");

        // 消费函数接口
        IntConsumer intConsumer = (value) -> System.out.println("输入的数据是：" + value);
        intConsumer.accept(123);
    }
}
