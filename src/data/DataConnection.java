package data;

import entity.Admin;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataConnection<T> {
    private static final String DBDRIVER="org.gjt.mm.mysql.Driver";
    private static final String DBURL="jdbc:mysql://localhost/dc";
    private static final String DBUSER="root";
    private static final String DBPASS="admin";
    private static Connection conn=null;


    /*
    用于连接数据库
     */
    public static Connection getConn(){
        try {
            Class.forName(DBDRIVER);
            conn= DriverManager.getConnection(DBURL,DBUSER,DBPASS);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            return conn;
        }
    }

    /**
     * 用于关闭数据库连接
     */
    public static void close (ResultSet rs, PreparedStatement psta,Connection conn){
            try {
                if (rs!=null){
                    rs.close();
                }
                if (psta!=null){
                    psta.close();
                }
                if (conn!=null){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    /*
    用于执行数据库操作，接收返回的数据信息
     */

    public static boolean request(String sqlcomm,Object...para){
        boolean flag=false;
        try {
            Connection conn=getConn();
            PreparedStatement psta=conn.prepareStatement(sqlcomm);
            if (para==null){
                para=new Object[0];
            }

            for (int i=0;i<para.length;i++){
                psta.setObject(i+1,para[i]);
            }
            int i=psta.executeUpdate();
            if (i>0){
                flag=true;
            }
            close(null,psta,conn);
            return flag;
        } catch (SQLException e) {
            return flag;
        }
    }

    /*
    用于执行数据库操作
     */

    public List<T> query(Class<T> cls , String sql, Object ... para){
        List<T> list = new ArrayList<>();

        try {

            conn = getConn();

            PreparedStatement psta = conn.prepareStatement(sql);

            if(para==null){
                para=new Object[0];
            }
            for(int i=0;i<para.length;i++){
                psta.setObject(i+1, para[i]);
            }
            ResultSet rs = psta.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            while(rs.next()){

                T obj = cls.newInstance();

                int col = rsmd.getColumnCount();
                for (int i=1; i<=col; i++){

                    String DBField = rsmd.getColumnLabel(i); //获取第i列名称

                    Field field = cls.getDeclaredField(DBField); //获取cls类中定的成员变量名与列名DBField

                    field.setAccessible(true); //私有可见

                    field.set(obj,rs.getObject(i));//把rs元素中的第i个列的值，赋给对象obj的field成员变量

                }

                list.add(obj);//obj添加到list中

            }

            close(rs, psta, conn);//关闭数据库相关的对象

        } catch (Exception e) {

            e.printStackTrace();

        }

        return list;//返回数据集全

    }

}
