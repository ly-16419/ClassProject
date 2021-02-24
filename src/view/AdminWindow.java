package view;

import entity.Admin;
import javafx.scene.control.DialogPane;
import netscape.javascript.JSObject;
import service.AdminService;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.IOError;
import java.nio.file.attribute.UserDefinedFileAttributeView;

import static service.AdminService.update;

public class AdminWindow implements ActionListener {
    JFrame fram;
    JMenuBar menuBar=new JMenuBar();

    JMenu cpgl=new JMenu("餐品管理");
    JMenu ddgl=new JMenu("订单管理");
    JMenu xxgl=new JMenu("信息管理");
    JMenu yhgl=new JMenu("用户管理");

    JMenuItem userlist=new JMenuItem("用户列表");
    JMenuItem adduser=new JMenuItem("添加用户");
    JMenuItem updateuser=new JMenuItem("更新用户");
    JMenuItem deleteuser=new JMenuItem("删除用户");

    JDesktopPane desktopPane=new JDesktopPane();
    JInternalFrame viewTable=null;
    JInternalFrame userinfomation=null;//添加用户信息窗口
    JInternalFrame userinformationUpdate=null;//更新用户信息窗口
    AdminTableModel adminTableModel=new AdminTableModel();
    JTable table=null;

    JLabel username=new JLabel("用户名");
    JLabel userpass=new JLabel("密码");
    JLabel userphone=new JLabel("电话");
    JTextField textname;
    JPasswordField textpass;
    JTextField textphone;
    JButton addbutton=new JButton("添加");
    JButton nobutton=new JButton("取消");

    public AdminWindow(){
        fram=new JFrame("管理员信息表");
        fram.setSize(500,250);
        fram.setLocationRelativeTo(null);

        menuBar.add(cpgl);
        menuBar.add(ddgl);
        menuBar.add(xxgl);
        menuBar.add(yhgl);

        yhgl.add(userlist);
        yhgl.add(adduser);
        yhgl.add(updateuser);
        yhgl.add(deleteuser);

        userlist.addActionListener(this);
        adduser.addActionListener(this);
        updateuser.addActionListener(this);
        deleteuser.addActionListener(this);

        fram.setJMenuBar(menuBar);

        desktopPane=new JDesktopPane();
        fram.add(desktopPane, BorderLayout.CENTER);

        fram.setVisible(true);
        fram.setDefaultCloseOperation(fram.DISPOSE_ON_CLOSE);

    }

    public static void main(String[] args){
        new AdminWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(userlist)){
            userTableList();
        }

        if (e.getSource().equals(adduser)){
            inserUser();
        }

        if (e.getSource().equals(updateuser)){
            /*
            判断 若是单击菜单“更新用户”，则先检测用户列表的内部窗口是否打开，
            若没有打开则先打开，然后提示用户选择要更新的数据行
             */
            if (viewTable==null){
                userTableList();
                JOptionPane.showConfirmDialog(fram,"请选择你要更新的数据进行更新操作","提示",JOptionPane.CLOSED_OPTION);
                return;
            }
            /*
            若用户已经选择了要更新的数据行，则把选择的数据行的行号记录下来
             */
            int i=table.getSelectedRow();
            Admin admins=new Admin();

            admins.setId((int) Long.parseLong(adminTableModel.getValueAt(i, 0).toString()));

            admins.setName(adminTableModel.getValueAt(i, 1).toString());

            admins.setPass(adminTableModel.getValueAt(i, 2).toString());

            admins.setPhone(adminTableModel.getValueAt(i,3).toString());

            //调用update（）方法显示内部更新窗口

            update(admins);
        }

        if (e.getSource().equals(deleteuser)){
            if (viewTable==null){
                userTableList();

                JOptionPane.showConfirmDialog(fram,"请选择你要删除的数据，进行删除操作","提示",JOptionPane.CLOSED_OPTION);
                return;
            }
            int i=table.getSelectedRow();//获得需要进行删除的用户所在的数据行
            if (i<0){
                JOptionPane.showMessageDialog(fram,"请在用户列表中选择需要删除的数据行,否则无法进行操作","提示",JOptionPane.CLOSED_OPTION);
                return;
            }

            int  yesNo=JOptionPane.showConfirmDialog(fram,"是否确认删除所选中的数据行","提示",JOptionPane.CLOSED_OPTION);
            if (yesNo==0){
                //获取选中行的id关键字
                int id=Integer.parseInt(adminTableModel.getValueAt(i, 0).toString());
                if (AdminService.del(id)){
                    userTableList();
                }else {
                    JOptionPane.showMessageDialog(fram,"无法删除所选中的数据","提示",JOptionPane.CLOSED_OPTION);

                }
            }
        }
    }

    //用于在窗口中显示表格
    private void userTableList(){
        if (viewTable!=null){
            viewTable.dispose();
        }

        viewTable=new JInternalFrame("用户列表",true,true,true,true);
        viewTable.setSize(fram.getWidth()-20,fram.getHeight()-20);
        viewTable.setLocation(5,5);
        adminTableModel=new AdminTableModel();
        table=new JTable(adminTableModel);

        JScrollPane scrollPane=new JScrollPane(table);
        JPanel panel=new JPanel();

        panel.add(scrollPane);

        viewTable.add(panel,BorderLayout.CENTER);
        viewTable.setVisible(true);

        desktopPane.add(viewTable,BorderLayout.CENTER);

        try {
            viewTable.setSelected(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        fram.add(desktopPane,BorderLayout.CENTER);
    }

    private void inserUser(){
        if (userinfomation!=null){
            userinfomation.dispose();
        }

        userinfomation=new JInternalFrame("添加用户",true,true,false,false);
        userinfomation.setLayout(null);

        userinfomation.setSize(200,200);
        userinfomation.setLocation(20,20);
        textname=new JTextField(20);
        textpass=new JPasswordField(20);
        textphone=new JTextField(20);
        addbutton=new JButton("添加");
        nobutton=new JButton("取消");

        username.setBounds(5,5,60,25);
        textname.setBounds(75,5,100,25);
        userpass.setBounds(5,40,60,25);
        textpass.setBounds(75,40,100,25);
        userphone.setBounds(5,75,60,25);
        textphone.setBounds(75,75,100,25);
        addbutton.setBounds(30,110,60,25);
        nobutton.setBounds(95,110,60,25);

        userinfomation.add(username);
        userinfomation.add(textname);
        userinfomation.add(userpass);
        userinfomation.add(textpass);
        userinfomation.add(userphone);
        userinfomation.add(textphone);
        userinfomation.add(addbutton);
        userinfomation.add(nobutton);

        addbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textname.getText()==""||textname.getText()==null){
                    JOptionPane.showConfirmDialog(fram,"用户名不能为空");
                    textname.requestFocus();
                    return;
                }
                if (textpass.getText()==""||textpass.getText()==null){
                    JOptionPane.showConfirmDialog(fram, "密码不能为空");
                    textpass.requestFocus();
                    return;
                }
                if (textphone.getText()==""||textphone.getText()==null){
                    JOptionPane.showConfirmDialog(fram, "电话不能为空");
                    textphone.requestFocus();
                    return;
                }
                Admin admin=new Admin();
                admin.setName(textname.getText());
                admin.setPass(textpass.getText());
                admin.setPhone(textphone.getText());

                if (AdminService.insert(admin)){
                    userTableList();//添加用户成功则实时显示用户列表

                    userinfomation.dispose();//关闭当前添加用户窗口
                }
            }
        });
        nobutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textname.setText("");//把用户名输入框设置为空

                textpass.setText("");//把密码输入框设置为空

                textphone.setText("");

                textname.requestFocus();//把用户名输入框设置为焦点
            }
        });
        userinfomation.setVisible(true);
        desktopPane.add(userinfomation, BorderLayout.CENTER);

        try {
            userinfomation.setSelected(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        fram.add(desktopPane, BorderLayout.CENTER);
    }

    /*
    该方法用于更新用户所悬着的用户信息
     */
    private void update(Admin admin){

        if (userinformationUpdate != null) {//判断更新用户的内部窗口是否存在，若存在则关闭

            userinformationUpdate.dispose();//关闭内部窗口

        }

        //实例化内部窗口，该窗口不可以拖动，可以关闭、不可以最大化，不可以最小化

        userinformationUpdate = new JInternalFrame("更新用户", false, true, false, false);

        //设置内部窗口布局，布局为绝对定位布局

        userinformationUpdate.setLayout(null);

        //设置内部窗口的大小

        userinformationUpdate.setSize(200, 200);

        //设置内部窗口相对于主窗口位置显示的位置

        userinformationUpdate.setLocation(20, 20);

        //实例化文本输入框

        textname = new JTextField(20);

        //把用于选择要更新的用户名赋给文本输入框的text属性

        textname.setText(admin.getName());

        //实例化密码输入框

        textpass = new JPasswordField(20);
        textphone = new JTextField(20);

        //实例化更新按钮

        addbutton = new JButton("更新");

        //实例化取消

        nobutton = new JButton("取消");

        //设置更新内部窗口中用到组件显示的位置

        username.setBounds(5, 5, 60, 25);

        textname.setBounds(75, 5, 100, 25);

        userpass.setBounds(5, 40, 60, 25);

        textpass.setBounds(75, 40, 100, 25);

        userphone.setBounds(5, 75, 60, 25);

        textphone.setBounds(75, 75, 100, 25);

        addbutton.setBounds(30, 110, 60, 25);

        nobutton.setBounds(95, 110, 60, 25);



        //把更新内部窗口所需的组件添加到内部窗口中

        userinformationUpdate.add(username);

        userinformationUpdate.add(textname);

        userinformationUpdate.add(textpass);

        userinformationUpdate.add(textpass);

        userinformationUpdate.add(userphone);

        userinformationUpdate.add(textphone);

        userinformationUpdate.add(addbutton);

        userinformationUpdate.add(nobutton);

        //注册更新按钮的事件

        addbutton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                //判断用户名是否为空，若为空则提示，并把光标设置到用户输入框中，最后返回

                if (username.getText() == "" || username.getText() == null) {

                    JOptionPane.showConfirmDialog(fram, "用户名不能为空");

                    username.requestFocus();

                    return;

                }



                //判断密码是否为空，若为空则提示，并把光标设置到密码输入框中，最后返回

                if (textpass.getText() == "" || textpass.getText() == null) {

                    JOptionPane.showConfirmDialog(fram, "密码不能为空");

                    textpass.requestFocus();

                    return;

                }

                //若用户名和密码都不为空，则实例化users数据表实体类

                Admin admins = new Admin();

                admins.setId((int) admin.getId());//把用户的uid

                admins.setName(textname.getText());

                admins.setPass(textpass.getText());

                admins.setPhone(textphone.getText());

                if (AdminService.update(admins)) {//利用users数据表业务类的update()方法，对用户信息进行更新操作

                    userTableList();//若更新成功返回用户信息列表

                    userinformationUpdate.dispose();//关闭当前更新内部窗口

                }

            }

        });



        //取消按钮注册事件

        nobutton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                userinformationUpdate.dispose();//关闭更新内部窗口

            }

        });



        //设置更新内部窗口为可见

        userinformationUpdate.setVisible(true);

        //把更新内部窗口添加到桌面中

        desktopPane.add(userinformationUpdate, BorderLayout.CENTER);

        try {

            //把内部窗口设置为当前窗口

            userinformationUpdate.setSelected(true);

        } catch (Exception e1) {

            e1.printStackTrace();

        }

        //把桌面添加到主窗口中

        fram.add(desktopPane, BorderLayout.CENTER);

    }
}
