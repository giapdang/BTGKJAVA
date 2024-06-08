package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.ExpenseModel;

public class ExpenseView extends JFrame {

  private ExpenseModel expenseModel;

  private DefaultTableModel model = new DefaultTableModel();
  private JTable jTable = new JTable(model);
  private JTextField title = new JTextField(15);
  private JTextField amount = new JTextField(10);
  private JSpinner date = new JSpinner(new SpinnerDateModel());

  // Khai báo các nút
  private JButton addButton = new JButton("Thêm");
  private JButton editButton = new JButton("Sửa");
  private JButton deleteButton = new JButton("Xóa");
  private JButton calculateTotalButton = new JButton("Tính tổng tiền");
  private JButton exitButton = new JButton("Thoát");

  //news
  private JButton searchButton = new JButton("Tìm kiếm");
  private JButton backButton = new JButton("Quay lại");
  private JButton calculateSearchTotalButton = new JButton("Tính tổng tiền tìm kiếm");

  public ExpenseView(ExpenseModel expenseModel) {
    this.expenseModel = expenseModel;
  }

  public ExpenseView() {
    this.init();
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }

  public void init() {
    this.setTitle("Quản lý chi tiêu");
    this.setSize(850, 400);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //add các cột vào bảng
    model.addColumn("NỘI DUNG");
    model.addColumn("SỐ TIỀN");
    model.addColumn("NGÀY");

    // Đặt kiểu chữ, độ dày và màu sắc cho tiêu đề cột
    JTableHeader header = jTable.getTableHeader();
    header.setFont(new Font("Arial", Font.BOLD, 12)); // Đặt font chữ, đậm, kích thước 12
    header.setForeground(Color.BLACK); // Đặt màu chữ
    header.setBackground(Color.ORANGE);

    //color text button
    addButton.setForeground(Color.RED);
    editButton.setForeground(Color.RED);
    deleteButton.setForeground(Color.RED);
    calculateTotalButton.setForeground(Color.RED);
    exitButton.setForeground(Color.RED);
    searchButton.setForeground(Color.RED);
    backButton.setForeground(Color.RED);
    calculateSearchTotalButton.setForeground(Color.RED);

    //add các ô label
    JPanel inputPanel = new JPanel();
    inputPanel.add(new JLabel("NỘI DUNG:"));
    inputPanel.add(title);
    inputPanel.add(new JLabel("SỐ TIỀN:"));
    inputPanel.add(amount);
    inputPanel.add(new JLabel("NGÀY:"));
    date.setEditor(new JSpinner.DateEditor(date, "dd-MM-yyyy"));
    inputPanel.add(date);

    //add các but button
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    buttonPanel.add(addButton);
    buttonPanel.add(editButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(calculateTotalButton);
    buttonPanel.add(searchButton);
    buttonPanel.add(calculateSearchTotalButton);
    buttonPanel.add(backButton);
    buttonPanel.add(exitButton);

    //set layout
    setLayout(new BorderLayout());
    add(inputPanel, BorderLayout.NORTH);
    add(new JScrollPane(jTable), BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);
  }

  //method lấy giá trị trường title
  public String getTitleInput() {
    return title.getText();
  }

  // Method lấy giá trị trường amount, trả về null nếu trống hoặc không hợp lệ
  public Double getAmountInput() {
    String amountText = amount.getText();
    if (amountText.isEmpty()) {
      return null; // Trả về null nếu amount trống
    }
    try {
      return Double.parseDouble(amountText);
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(null, "Invalid amount format.", "Input Error",
          JOptionPane.ERROR_MESSAGE);
      return null;
    }
  }

  //method lấy giá trị trường Date
  public Date getDateInput() {
    return (Date) date.getValue();
  }

  //methos clear lại thông tin
  public void clearInputFields() {
    title.setText("");
    amount.setText("");
    date.setValue(new Date());
  }

  // Các phương thức để truy cập các nút
  public JButton getAddButton() {
    return addButton;
  }

  public JButton getEditButton() {
    return editButton;
  }

  public JButton getDeleteButton() {
    return deleteButton;
  }

  public JButton getCalculateTotalButton() {
    return calculateTotalButton;
  }

  public JButton getExitButton() {
    return exitButton;
  }

  public DefaultTableModel getTableModel() {
    return model;
  }

  public JTable getTable() {
    return jTable;
  }

  public JButton getSearchButton() {
    return searchButton;
  }

  public JButton getBackButton() {
    return backButton;
  }

  public JButton getCalculateSearchTotalButton() {
    return calculateSearchTotalButton;
  }

  public JTextField getTitleField() {
    return title;
  }

  public JTextField getAmountField() {
    return amount;
  }

  public JSpinner getDateSpinner() {
    return date;
  }

}