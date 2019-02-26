package cn.lifengming.study.framework.springmvc.lifengming.java8.demo;

import java.text.DecimalFormat;
import java.util.function.Function;

/**
 * @author lifengming
 * @version 1.0
 * @date 2019-02-21 10:00
 */
public class InterfacelDemo {
    //    public static void main(String[] args) {
    //        //常见的写法
    //        Interfacel var1 = (i)->i * 2;
    //        Interfacel var2 = i->i * 2;
    //        //         指定参数类型
    //        Interfacel var3 = (int i)->i * 2;
    //        //        如有多行代码可以这样写
    //        Interfacel var4 = (int i)->{
    //            System.out.println( i );
    //            return 1 * 2;
    //        };
    //        System.out.println("var1"+var1+"var2"+var2+"var3"+var3);
    //
    //    }
    public static void main(String[] args) {
        MyMoney me = new MyMoney( 99999999 );

        Function<Integer, String> moneyFormat = i->new DecimalFormat( "#,###" ).format( i );
        // 函数接口支持链式操作，例如增加一个字符串
        me.printMoney( moneyFormat.andThen( s->"人民币 " + s ) );
    }
}

class MyMoney {
    private final int money;

    public MyMoney(int money) {
        this.money = money;
    }

    public void printMoney(Function<Integer, String> moneyFormat) {
        System.out.println( "我的存款: " + moneyFormat.apply( this.money ) );
    }
}
