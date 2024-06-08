package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Expense;
import model.ExpenseModel;
import view.ExpenseView;

public class ExpenseController {

  private ExpenseModel expenseModel;
  private ExpenseView expenseView;

  private List<Expense> searchResults;
  private int month;
  private int year;

  private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
  private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

  public ExpenseController(ExpenseModel expenseModel, ExpenseView expenseView) {
    this.expenseModel = expenseModel;
    this.expenseView = expenseView;
    add();
    deleteById();
    updateById();
    sum();
    quitTheProgram();
    filter();
    backToFullList();
    sumSearchResults();
    clickMouse();
  }

  //method them 1 chi tieu
  public void add() {
    expenseView.getAddButton().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String title = expenseView.getTitleInput();
        Double amount = expenseView.getAmountInput();
        Date date = expenseView.getDateInput();

        if (title == null || title.isEmpty() || amount == null || date == null) {
          JOptionPane.showMessageDialog(expenseView, "Vui lòng điền đầy đủ các trường.",
              "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        } else {
          int selectedRow = expenseView.getTable().getSelectedRow();
          expenseModel.createExpense(title, amount, date);
          expenseView.getTableModel()
              .addRow(new Object[]{title, currencyFormat.format(amount), dateFormat.format(date)});
          expenseView.clearInputFields();
          sortTable();

          if (selectedRow != -1) {
            expenseView.getTable().setRowSelectionInterval(selectedRow, selectedRow);
          }

          JOptionPane.showMessageDialog(expenseView, "Thêm chi phí thành công.", "Thành công",
              JOptionPane.INFORMATION_MESSAGE);
        }
      }
    });
  }

  //method sap xep theo thoi gian
  private void sortTable() {
    DefaultTableModel tableModel = expenseView.getTableModel();
    List<Expense> expenses = expenseModel.getAllExpenses();

    Collections.sort(expenses, new Comparator<Expense>() {
      @Override
      public int compare(Expense o1, Expense o2) {
        return o2.getDate().compareTo(o1.getDate());
      }
    });

    tableModel.setRowCount(0);
    for (Expense expense : expenses) {
      tableModel.addRow(new Object[]{expense.getTitle(), currencyFormat.format(expense.getAmount()),
          dateFormat.format(expense.getDate())});
    }
  }

  //method xoa
  public void deleteById() {
    expenseView.getDeleteButton().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int selectedRow = expenseView.getTable().getSelectedRow();
        if (selectedRow != -1) {
          expenseModel.deleteExpense(selectedRow);
          expenseView.getTableModel().removeRow(selectedRow);
          expenseView.clearInputFields();
          JOptionPane.showMessageDialog(expenseView, "Xóa chi phí thành công.", "Thành công",
              JOptionPane.INFORMATION_MESSAGE);
        } else {
          JOptionPane.showMessageDialog(expenseView, "Chọn một hàng để xóa.", "Xóa chi phí",
              JOptionPane.WARNING_MESSAGE);
        }
      }
    });
  }

  //method update
  public void updateById() {
    expenseView.getEditButton().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int selectedRow = expenseView.getTable().getSelectedRow();
        if (selectedRow != -1) {
          String title = expenseView.getTitleInput();
          Double newAmount = expenseView.getAmountInput();
          Double oldAmount = expenseModel.getAllExpenses().get(selectedRow).getAmount();
          Date date = expenseView.getDateInput();

          expenseModel.updateExpense(selectedRow, title, newAmount, date);

          // Update the table
          DefaultTableModel tableModel = expenseView.getTableModel();

          if (title != null) {
            tableModel.setValueAt(title, selectedRow, 0);
          }

          if (newAmount != null) {
            tableModel.setValueAt(currencyFormat.format(newAmount), selectedRow, 1);
          }
          if (date != null) {
            tableModel.setValueAt(dateFormat.format(date), selectedRow, 2);
          }
          double amountDifference = newAmount - oldAmount;
          // Update the total
          double total = expenseModel.calculateTotal() + amountDifference;

          expenseView.clearInputFields();
          JOptionPane.showMessageDialog(expenseView, "Cập nhật chi phí thành công.", "Thành công",
              JOptionPane.INFORMATION_MESSAGE);
        } else {
          JOptionPane.showMessageDialog(expenseView, "Chọn một hàng để cập nhật.",
              "Cập nhật chi phí", JOptionPane.WARNING_MESSAGE);
        }
      }
    });
  }


  //method tinh tong tien toan bo
  public void sum() {
    expenseView.getCalculateTotalButton().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        double total = expenseModel.calculateTotal();
        JOptionPane.showMessageDialog(expenseView, "Tổng chi phí: " + currencyFormat.format(total),
            "Tổng chi phí", JOptionPane.INFORMATION_MESSAGE);
      }
    });
  }

  //thoat chuong trinh
  public void quitTheProgram() {
    expenseView.getExitButton().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });
  }

  //loc danh sach theo thang va nam
  public void filter() {
    expenseView.getSearchButton().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JPanel panel = new JPanel();
        JTextField monthField = new JTextField(5);
        JTextField yearField = new JTextField(5);
        panel.add(new JLabel("Tháng:"));
        panel.add(monthField);
        panel.add(new JLabel("Năm:"));
        panel.add(yearField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Nhập Tháng và Năm",
            JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
          try {
            month = Integer.parseInt(monthField.getText());
            year = Integer.parseInt(yearField.getText());

            searchResults = expenseModel.searchExpense(month, year);
            DefaultTableModel tableModel = expenseView.getTableModel();
            tableModel.setRowCount(0);

            for (Expense expense : searchResults) {
              tableModel.addRow(
                  new Object[]{expense.getTitle(), currencyFormat.format(expense.getAmount()),
                      dateFormat.format(expense.getDate())});
            }
            sortTable();
            JOptionPane.showMessageDialog(expenseView, "Tìm kiếm hoàn tất.", "Tìm kiếm",
                JOptionPane.INFORMATION_MESSAGE);
          } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(expenseView, "Tháng hoặc năm không hợp lệ.",
                "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
          }
        }
      }
    });
  }

  //method quay lai danh sach chinh
  public void backToFullList() {
    expenseView.getBackButton().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<Expense> allExpenses = expenseModel.getAllExpenses();
        DefaultTableModel tableModel = expenseView.getTableModel();
        tableModel.setRowCount(0);

        for (Expense expense : allExpenses) {
          tableModel.addRow(
              new Object[]{expense.getTitle(), currencyFormat.format(expense.getAmount()),
                  dateFormat.format(expense.getDate())});
          sortTable();
        }

        JOptionPane.showMessageDialog(expenseView, "Quay lại danh sách đầy đủ.", "Danh sách",
            JOptionPane.INFORMATION_MESSAGE);
      }
    });
  }

  //method tinh tong tien tim kiem
  public void sumSearchResults() {
    expenseView.getCalculateSearchTotalButton().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (searchResults != null && !searchResults.isEmpty()) {
          double total = 0;
          for (Expense expense : searchResults) {
            total += expense.getAmount();
          }

          JOptionPane.showMessageDialog(expenseView,
              "Tổng chi phí cho " + month + "-" + year + ": " + currencyFormat.format(total),
              "Tổng chi phí tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
        } else {
          JOptionPane.showMessageDialog(expenseView, "Không có kết quả để tính tổng.",
              "Tổng chi phí tìm kiếm", JOptionPane.WARNING_MESSAGE);
        }
      }
    });
  }

  //method click mouse
  public void clickMouse() {
    expenseView.getTable().addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int selectedRow = expenseView.getTable().getSelectedRow();
        if (selectedRow != -1) {
          String title = (String) expenseView.getTableModel().getValueAt(selectedRow, 0);
          String amountString = (String) expenseView.getTableModel().getValueAt(selectedRow, 1);
          String dateString = (String) expenseView.getTableModel().getValueAt(selectedRow, 2);

          expenseView.getTitleField().setText(title);
          expenseView.getAmountField()
              .setText(amountString.replace("₫", "").replace(".", ""));
          try {
            Date date = dateFormat.parse(dateString);
            expenseView.getDateSpinner().setValue(date);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }
      }
    });
  }

}
