package view;

import entity.Admin;
import service.AdminService;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Vector;

public class AdminTableModel extends AbstractTableModel {

    Vector col;//存放数据表列标题，要根据数据库数据表的字段决定的

    Vector rowData;//存放数据表的数据行，是根据数据表记录数决定

    Vector row;//存放数据表中的某一行数据

    public AdminTableModel(){

        initCol();

        initRowData();

    }



    /**

     * 表格头进行初始化

     */

    private void  initCol(){

        col=new Vector();

        col.add("id");

        col.add("name");

        col.add("pass");

        col.add("phone");

    }

    /**

     * 对表格数据行进行初始化

     */

    private void initRowData(){

        //通过数据表users的业务类UsersService.getData()方法获取数据表的所有记录

        List<Admin> li= AdminService.select();

        //创建数据表数据行的对象

        rowData=new Vector();

        //利用遍历方式把数据表中记录逐条读取出来放到row对象中，然后再把放到rowData对象中构成二维表

        for (Admin ad : li) {

            row=new Vector();

            row.add(ad.getId());

            row.add(ad.getName());

            row.add(ad.getPass());

            row.add(ad.getPhone());

            rowData.add(row);

        }



    }

    /**

     * 获取表格列的总数

     */

    @Override

    public int getColumnCount() {

        return col.size();

    }



    /**

     * 获取表格数据行的总数

     */

    @Override

    public int getRowCount() {

        return rowData.size();

    }



    /**

     * 获取第argo行和第arg1列单元格的内容

     */

    @Override

    public Object getValueAt(int arg0, int arg1) {

        return ((Vector)rowData.get(arg0)).get(arg1);

    }



    /**

     * 获取第column列的名称

     */

    @Override

    public String getColumnName(int column) {

        return col.get(column).toString();

    }

}