package lanqiao.newUi;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * @Author 李冰冰
 * @Date 2022/12/04
 * @Version 17.0.5
 */

public class UserPanel extends JPanel {
    // 用户管理面板
    public static JPanel init(){
        JPanel UserPanel=new JPanel();
        Color color1=new Color(51, 161, 201);
        Font ChineseFont = new Font("宋体", Font.BOLD, 16);
        UserPanel.setBackground(color1);
        UserPanel.setBounds(0,0,860,690);
        UserPanel.setLayout(null);

        JLabel usernameLable =new JLabel("搜索:");
        JTextField usernameField= new JTextField();

        JButton button1= new JButton();
        JButton button2= new JButton();
        JButton button3= new JButton();

        button1.setText("text");
        UserPanel.add(button1);
        button1.setBounds(770, 30, 80, 25);
        button2.setText("text");
        UserPanel.add(button2);
        button2.setBounds(680, 30, 80, 25);
        button3.setText("text");
        UserPanel.add(button3);
        button3.setBounds(590, 30, 80, 25);

        usernameLable.setBounds(250, 610, 50, 25);
        usernameLable.setFont(ChineseFont);
        UserPanel.add(usernameLable);

        usernameField.setBounds(300,610,200,25);
        UserPanel.add(usernameField);

        JLabel jLabel=new JLabel();
        jLabel.setBounds(15,30,100,25);
        jLabel.setText("用户管理界面");
        jLabel.setFont(ChineseFont);
        UserPanel.add(jLabel);
        String head[] = {"id", "用户名", "密码"};
        Object[][] data = new Object[5][3];

        JTable jTable = new JTable(data, head);
        jTable.getTableHeader().setPreferredSize(new Dimension(1, 30));

        // 滚动面板
        JScrollPane jscrollpane = new JScrollPane();
        jscrollpane.setBounds(0, 80, 860, 500);
        jscrollpane.setViewportView(jTable);// 把表格添加到滚动面板中
        jTable.setRowHeight(20);// 行高

        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        jTable.setDefaultRenderer(Object.class, r);// 每行内容居中显示

        UserPanel.add(jscrollpane);
        return UserPanel;
    }
}
