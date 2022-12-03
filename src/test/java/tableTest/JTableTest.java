package tableTest;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.List;

public class JTableTest extends Component {
    static String url = "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    static Connection conn;
    static{
        try {
            conn= DriverManager.getConnection(url, "root", "THEO1126");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static  PreparedStatement pstmt = null;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    init();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static  void init() {
        JFrame jFrame = new JFrame("??????");
        jFrame.setSize(600, 600);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String head[] = {"?????", "????"};
        Object[][] data;
        data=queryData();
        DefaultTableModel tableModel;// ??????
        tableModel = new DefaultTableModel(data, head);
        JTable jTable=new JTable(tableModel);
        // ???????
        JScrollPane jscrollpane = new JScrollPane();
        jscrollpane.setPreferredSize(new Dimension(1200, 610));

        jscrollpane.setViewportView(jTable);// ?????????????????
        jTable.setRowHeight(35);// ?и?

        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        jTable.setDefaultRenderer(Object.class, r);// ?????????????

        JPanel jPanel = (JPanel) jFrame.getContentPane();
        jPanel.setLayout(new BorderLayout());
        jPanel.add(jscrollpane, BorderLayout.NORTH);

        JPanel btnPanel=new JPanel();
        btnPanel.setLayout(new GridLayout(1, 3));
        JButton addBtn=new JButton("????");
        JButton saveBtn=new JButton("????");
        JButton deleteBtn=new JButton("???");

        btnPanel.add(addBtn);
        btnPanel.add(saveBtn);
        btnPanel.add(deleteBtn);

        jPanel.add(btnPanel,BorderLayout.SOUTH);
        jFrame.add(jscrollpane);


        deleteBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int SelectedRow=jTable.getSelectedRow();
                String sql="DELETE FROM USER WHERE username=?";
                try {
                    String value=jTable.getValueAt(SelectedRow,0).toString();
                    tableModel.removeRow(jTable.getSelectedRow());//????????
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1,value);
                    pstmt.executeUpdate();
                    System.out.println("????????");
                } catch (ArrayIndexOutOfBoundsException e1){
                    JOptionPane.showMessageDialog(jFrame, "??????????????????", "????", JOptionPane.ERROR_MESSAGE);
                }catch (SQLException ex) {
                    System.out.println("???????");
                }
            }});


        /* ??? */
        addBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addRow(new Vector());
            }
        });

        /* ???? */
        saveBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int column = jTable.getColumnCount();// ???????
                int row = jTable.getRowCount();       // ???????
                // value?????????е?????????
                String[][] value = new String[row][column];
                for(int i = 0; i < row; i++){
                    for(int j = 0; j < column; j++){
                        value[i][j] = jTable.getValueAt(i, j).toString().trim();
                    }
                }
                try {
                    for (int i = 0; i < row; i++){
                        String sql="REPLACE INTO USER(username,password) VALUES(?,?)";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1,value[i][0]);
                        pstmt.setString(2,value[i][1]);
                        pstmt.executeUpdate();
                    }
                    System.out.println("????????");
                    JOptionPane.showMessageDialog(jFrame, "??????", "????", JOptionPane.INFORMATION_MESSAGE);
                } catch (NullPointerException e1){
                    JOptionPane.showMessageDialog(jFrame, "????д???????????????棡", "???????", JOptionPane.INFORMATION_MESSAGE);
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    // ????????????
    public static Object[][] queryData() {
        List<Users> list=new ArrayList<Users>();
        String sql = "SELECT * FROM user";
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery(sql);
            while (rs.next()) {
                //??????ξ???????????????????????????List??????????????Set??????????????Map??key??value????
                Users user=new Users();
                user.setUsername(rs.getString("USERNAME"));
                user.setPassword(rs.getString("PASSWORD"));
                list.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String head[] = {"?????", "????"};
        Object [][]data = new Object[list.size()][head.length];
        //???????????????Obejct??????????
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < head.length; j++) {
                data[i][0] = list.get(i).getUsername();
                data[i][1] = list.get(i).getPassword();
            }
        }
        return data;
    }

    static class Users{
        String username;
        String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}