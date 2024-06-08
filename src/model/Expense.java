package model;

import java.util.Date;

public class Expense {
  private String title;
  private Double amount;
  private Date date;

  public Expense(String title, Double amount, Date date) {
    this.title = title;
    this.amount = amount;
    this.date = date;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}
