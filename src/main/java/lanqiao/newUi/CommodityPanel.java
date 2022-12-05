package lanqiao.newUi;

import lanqiao.bean.Commodity;
import lanqiao.bean.Order;
import lanqiao.service.CommodityService;
import lanqiao.service.OrderService;
import lanqiao.service.impl.CommodityServiceImpl;
import lanqiao.service.impl.OrderServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * @Author 李冰冰
 * @Date 2022/12/04
 * @Version 17.0.5
 */

public class CommodityPanel {
    // 商品订单管理面板
    CommodityService commodityService=new CommodityServiceImpl();
    OrderService orderService=new OrderServiceImpl();
    List<Commodity> commodityList;
    {
        try {
            commodityList = commodityService.getAllCommodity();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    JLabel commdityLabels[];
    DefaultTableModel ordertableModel;    //点餐的表格模型
    JTable orderTable;//点餐的表格
    DefaultTableModel tableModel;// 订单的表格模型
    JTable Table;//订单的表格
    int commodityNumber;
    Object[][] commoditySellPurchaser; //名字，售价，数量，售价
    JLabel totalpricelabel;//订单合计价格
    JLabel orderLabel;// 点餐合计价格
    int id=10;
    Order[] orders;// 已点餐的数据
    public CommodityPanel(){
        try {
            this.commodityNumber=commodityService.getCommodityNumber();
            this.commoditySellPurchaser=commodityService.getCommodityNameSellNumber();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* 获取点餐的价格 */
    public double getOrderPrice(){
        int row = ordertableModel.getRowCount(); // 表格行数
        double OrderPrice=0;
        // value数组存放表格中的所有数据
        Object[][] value = new Object[row][2];
        for(int i = 0; i < row; i++){
            double price = (double) ordertableModel.getValueAt(i, 1);
            int number=Integer.parseInt(ordertableModel.getValueAt(i,2).toString());
            OrderPrice=OrderPrice+price*number;
        }
        return OrderPrice;
    }

    /* 获取订单总价 */
    public double getTotalPrice(){
        int row = tableModel.getRowCount(); // 表格行数
        double TotalPrice=0;
        for(int i = 0; i < row; i++){
            double price = (double) tableModel.getValueAt(i, 3);
            int number=Integer.parseInt(tableModel.getValueAt(i,2).toString());
            TotalPrice=TotalPrice+price*number;
        }
        return TotalPrice;
    }


    public JPanel init(){
        /* 基本框架内容 */
        JPanel commodityPanel=new JPanel();
        Color color1=new Color(51, 161, 201);
        commodityPanel.setBackground(color1);
        Font ChineseFont = new Font("宋体", Font.BOLD, 16);
        commodityPanel.setBounds(0,0,860,690);
        commodityPanel.setLayout(null);
        JLabel usernameLable =new JLabel("搜索:");
        JTextField usernameField= new JTextField();

        JButton payBtn= new JButton();
        JButton orderBtn= new JButton();
        JButton deleteBtn= new JButton();

        payBtn.setText("收款");
        commodityPanel.add(payBtn);
        payBtn.setBounds(770, 30, 80, 25);
        orderBtn.setText("下单");
        commodityPanel.add(orderBtn);
        orderBtn.setBounds(680, 30, 80, 25);
        deleteBtn.setText("删除");
        commodityPanel.add(deleteBtn);
        deleteBtn.setBounds(590, 30, 80, 25);

        usernameLable.setBounds(250, 610, 50, 25);
        usernameLable.setFont(ChineseFont);
        commodityPanel.add(usernameLable);

        usernameField.setBounds(300,610,200,25);
        commodityPanel.add(usernameField);

        JLabel jLabel=new JLabel();
        jLabel.setBounds(15,30,100,25);
        jLabel.setText("商品管理界面");
        jLabel.setFont(ChineseFont);
        commodityPanel.add(jLabel);

        /* 新增内容 */
        // 删除按钮监听
        deleteBtn.addActionListener(
            e->{
                try {
                    int[] selectedRows=orderTable.getSelectedRows();//被选中行的索引集合
                    if(selectedRows.length !=0) { //如果被选中行不为0
                        for(int i=selectedRows.length-1;i>=0;i--){//这是关键代码
                            ordertableModel.removeRow(selectedRows[i]);//删除任意被选中行
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "请选择数据后再删除", "警告", JOptionPane.ERROR_MESSAGE);
                    }
                    orderLabel.setText("合计 : "+getOrderPrice()+"元");
                } catch (ArrayIndexOutOfBoundsException e1){
                    JOptionPane.showMessageDialog(null, "请数据数据后再删除", "警告", JOptionPane.ERROR_MESSAGE);
                }
            }
        );

        // 下单按钮监听
        orderBtn.addActionListener(e->{
            id++;
            int orderTableCount=orderTable.getRowCount();
            System.out.println(orderTableCount);
            if(orderTableCount==0){
                JOptionPane.showMessageDialog(null,"下单失败，当前未点餐","警告",JOptionPane.ERROR_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null,"下单成功！","提醒",JOptionPane.INFORMATION_MESSAGE );
                orders=new Order[orderTableCount];
                 //将数据存入订单表
                Date currentDate = new Date(System.currentTimeMillis());
                for(int i=0;i<orderTableCount;i++){
                    String commodity= (String) orderTable.getValueAt(i,0);
                    double sell=(double) orderTable.getValueAt(i,1);
                    int number=(int)orderTable.getValueAt(i,2);
                    double purchase=0;
                    for(Commodity com:commodityList){
                        if(Objects.equals(commodity, com.getName())){
                            purchase=com.getPurchase();
                        }
                    }
                    orders[i]=new Order(id,commodity,number,purchase,sell,currentDate);
                    Object[] arr={id,commodity,number,purchase,sell,currentDate};
                    //加入订单表
                    tableModel.addRow(arr);
                }
                totalpricelabel.setText("合计:"+getTotalPrice()+"元");

                try {
                    // 点餐信息加入数据库中
                    orderService.addOrder(orders);
                    // 减去库存里已点的商品数量
                    commodityService.updateCommoditynumber(orders);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // 付款按钮
        payBtn.addActionListener(e->{
            JOptionPane.showMessageDialog(null,"需要付款"+getOrderPrice(),"支付",JOptionPane.INFORMATION_MESSAGE );
            int orderTableCount=orderTable.getRowCount();
            // 清空点餐表
            for(int i=orderTableCount-1;i>=0;i--){
                ordertableModel.removeRow(i);
            }
            orderLabel.setText("合计:"+getOrderPrice()+"元"); //更新点餐合计
        });

        //商品信息滚动面板
        JScrollPane jscrollpane=new JScrollPane();
        jscrollpane.setBounds(0,80,250,500);
        JPanel commodityNamePanel=new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        commodityNamePanel.setPreferredSize(new Dimension(250,1000));
        commdityLabels=new JLabel[commodityNumber];

        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);//表格内容居中

        // 已点菜单表格
        JLabel orderTableTitle=new JLabel("已点餐表");
        orderTableTitle.setBounds(260,35,100,100);
        commodityPanel.add(orderTableTitle);
        String orderhead[] = {"菜名", "单价", "数量"};
        Object[][] orderdata = new Object[0][0];
        ordertableModel = new DefaultTableModel(orderdata, orderhead){ // 只允许向第三列可以修改
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 2) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        orderTable=new JTable(ordertableModel);
        orderTable.setDefaultRenderer(Object.class, r);// 每行内容居中显示
        JScrollPane jScrollOrderPane = new JScrollPane(); //菜单的滚动面板
        jScrollOrderPane.setViewportView(orderTable);
        jScrollOrderPane.setBounds(260,95,590,200);
        commodityPanel.add(jScrollOrderPane);
        orderLabel=new JLabel("合计：");
        orderLabel.setText("合计："+getOrderPrice()+"元");
        orderLabel.setBounds(700,75,100,20);
        commodityPanel.add(orderLabel);

        // 已点菜单表格监听  表格内容修改监听
        ordertableModel.addTableModelListener(e -> {
            orderLabel.setText("合计:"+getOrderPrice()+"元");//更新已点菜单的合计价格
        });

        // 订单表格
        JLabel TableTitle=new JLabel("订单表");
        TableTitle.setBounds(260,270,100,100);
        commodityPanel.add(TableTitle);
        String head[] = {"订单号","商品名", "数量", "售价","购价","日期"};
        Object[][] data = new Object[0][0];
        tableModel = new DefaultTableModel(data, head);
        Table=new JTable(tableModel);
        Table.setDefaultRenderer(Object.class, r);// 每行内容居中显示
        JScrollPane  jScrollPane = new JScrollPane(); //菜单的滚动面板
        jScrollPane.setViewportView(Table);
        jScrollPane.setBounds(260,330,590,250);
        totalpricelabel=new JLabel("合计：0元");
        totalpricelabel.setText("合计:"+getTotalPrice()+"元");
        totalpricelabel.setBounds(700,310,100,20);
        commodityPanel.add(totalpricelabel);
        commodityPanel.add(jScrollPane);

        // 商品信息
        for (int i = 0; i < commodityNumber; i++) {
            JLabel iconType = new JLabel();
            String dishName= (String) commoditySellPurchaser[i][0];
            double price=(double)commoditySellPurchaser[i][1];
            int number=(int) commoditySellPurchaser[i][2];
            iconType.setSize(100,100);
            iconType.setFont(new Font("微软雅黑",Font.BOLD,15));
            iconType.setBorder (BorderFactory.createTitledBorder (
                    BorderFactory.createLineBorder (Color.CYAN,2)));
            iconType.setText(dishName+":"+price+"元");
            commdityLabels[i]=iconType;
            commodityNamePanel.add(iconType);
            commdityLabels[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            final boolean[] flag = {false};
            commdityLabels[i].addMouseListener(new MouseListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                Object[] selectedArr={dishName,price,1};

                @Override
                public void mousePressed(MouseEvent e) {
                    if(number==0){ //当前商品数量不够
                        JOptionPane.showMessageDialog(null,"非常抱歉, "+dishName+"的库存为 "+number,"提醒",JOptionPane.ERROR_MESSAGE );
                    }else{
                        int row = ordertableModel.getRowCount(); // 表格行数
                        for(int j = 0; j < row; j++){
                            String ordername = (String) ordertableModel.getValueAt(j, 0);
                            // 判断当前商品是否已点过，点过，则再点后直接更改表格上的数量
                            // 如果点的数量大于库存数量，则提示库存不够
                            if(ordername==dishName){
                                int n= (int) ordertableModel.getValueAt(j, 2)+1;
                                if(n>number){
                                    JOptionPane.showMessageDialog(null,"非常抱歉,"+dishName+"的库存仅剩 "+number,"提醒",JOptionPane.ERROR_MESSAGE);
                                }else{
                                    ordertableModel.setValueAt(n,j,2);
                                    flag[0] =true;
                                    break;
                                }
                            }
                        }
                        // 判断当前商品是否已点过，没点过，则点后表格增加一行
                        if(!flag[0]){
                            ordertableModel.addRow(selectedArr);
                        }
                        orderLabel.setText("合计:"+getOrderPrice()+"元");//更新已点菜单的合计价格
                    }
                }
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
        }
        jscrollpane.setViewportView(commodityNamePanel);
        jscrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);  //设置垂直滚动条在窗格上
        jscrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // 不设置水平滚动条
        commodityPanel.add(jscrollpane);
        return commodityPanel;
    }
}
