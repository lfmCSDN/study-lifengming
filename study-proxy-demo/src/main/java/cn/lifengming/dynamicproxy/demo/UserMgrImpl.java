package cn.lifengming.dynamicproxy.demo;

/**
 * @author lifengming
 * @date 2019-02-25 15:21
 */
public class UserMgrImpl implements UserMgr {

    @Override
    public void addUser() {
        System.out.println("添加用户.....");
    }

    @Override
    public void delUser() {
        System.out.println("删除用户.....");
    }
}
