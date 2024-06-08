package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseModel {

  private ArrayList<Expense> expenseArrayList;


  public ExpenseModel(ArrayList<Expense> expenseArrayList) {
    this.expenseArrayList = expenseArrayList;
  }

  public ExpenseModel() {
    this.expenseArrayList = new ArrayList<>();
  }

  //method created
  public void createExpense(String title, Double amount, Date date) {
    Expense expense = new Expense(title, amount, date);
    expenseArrayList.add(expense);
  }

  //method delete
  public void deleteExpense(int index) {
    expenseArrayList.remove(index);
  }

  //method update
  public void updateExpense(int index, String title, Double amount, Date date) {
    if (index >= 0 && index < expenseArrayList.size()) {
      Expense expense = expenseArrayList.get(index);
      if (!title.isEmpty() && title != null) {
        expense.setTitle(title);
      }
      if (amount != null) {
        expense.setAmount(amount);
      }
      if (date != null) {
        expense.setDate(date);
      }
    }
  }


  //method tinh tong tien
  public double calculateTotal() {
    double total = 0.0;
    for (Expense expense : expenseArrayList) {
      total += expense.getAmount();
    }
    return total;
  }

  //method tìm kiếm theo tháng và năm
  public List<Expense> searchExpense(int month, int year) {
    List<Expense> searchResults = new ArrayList<>();
    for (Expense expense : expenseArrayList) {
      if (month == expense.getDate().getMonth() + 1 && year == expense.getDate().getYear() + 1900) {
        searchResults.add(expense);
      }
    }
    return searchResults;
  }


  //method hiển thị tất cả danh sách
  public List<Expense> getAllExpenses() {
    return new ArrayList<>(expenseArrayList);
  }

}

