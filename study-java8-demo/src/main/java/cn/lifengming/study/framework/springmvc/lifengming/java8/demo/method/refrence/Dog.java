package cn.lifengming.study.framework.springmvc.lifengming.java8.demo.method.refrence;

/**
 * @author lifengming
 * @version 1.0
 * @date 2019-02-21 10:53
 */
public class Dog {
    private String name = "二哈";
    private int food = 10;

    public Dog(String name) {
        this.name = name;
    }

    public Dog() {
    }

    public static void bark(Dog dog) {
        System.out.println(dog + "叫了");
    }

    public int eat(int num) {
        System.out.println("吃了" + num + "斤");
        this.food -= num;
        return this.food;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
