package service;

import entity.Admin;

import java.util.List;

public class Test {
    public static void main(String[] args) {

        //创建数据表user的实体类User的对象

        Admin user = new Admin();

        user.setName("bb");

        user.setPass("bb");

        user.setPhone("18776789099");

        //使用数据表的业务类UserService的insert()方法向数据表user添加一条记录
        if (AdminService.insert(user)) {

            System.out.println("添加数据成功");

        } else {

            System.out.println("添加数据失败");

        }


        //通过数据表的业务类UserService的select（）访求读取数据表中所有的数据

        List<Admin> list = AdminService.select();

        //遍历输出数据。

        for (Admin us : list) {

            System.out.println("id=" + us.getId() + ",name=" + us.getName() + ",pass=" + us.getPass()+",phone="+us.getPhone());

        }


    }
}
