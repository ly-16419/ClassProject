package service;

import data.DataConnection;
import entity.Admin;

import java.util.List;

public class AdminService {

    /*
    添加一条新纪录
     */
    public static boolean insert(Admin admin){
        String sql="insert into Admin(name,pass,phone) values(?,?,?)";
        Object[] param=new Object[]{new String(admin.getName()),new String(admin.getPass()),new String(admin.getPhone())};

        return DataConnection.request(sql,param);
    }

    /*
    进行数据更新
     */
    public static boolean update(Admin admin){
        String sql="update Admin set name=?,pass=?,phone=?  where id=?";
        Object[] param=new Object[]{new String(admin.getName()),new String(admin.getPass()),new String(admin.getPhone()),new Long(admin.getId())};

        return DataConnection.request(sql,param);
    }

    /*
    删除数据
     */
    public static boolean del(long id){
        String sql="delete from  Admin   where id=?";
        Object[] param=new Object[]{new Long(id)};
        return DataConnection.request(sql,param);
    }
    /*
    查询用户信息
     */
    public static List<Admin> select(){
        String sql="select id,name,pass,phone from Admin";
        Admin admin=new Admin();
        return new DataConnection().query(admin. getClass(),sql,null);

    }

}
