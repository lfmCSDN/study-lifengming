package cn.lifengming.dynamicproxy.demo;

/**
 * @author lifengming
 * @date 2019-02-25 15:20
 */
public class Test {
    public static void main(String[] args){
        UserMgr userMgr=(UserMgr)new DynamicProxy().bind(new UserMgrImpl());
        userMgr.delUser();
    }
}
