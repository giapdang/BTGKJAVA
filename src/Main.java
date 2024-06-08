import controller.ExpenseController;
import model.ExpenseModel;
import view.ExpenseView;

public class Main {

  public static void main(String[] args) {
    ExpenseView expenseView = new ExpenseView();
    ExpenseModel expenseModel = new ExpenseModel();
    ExpenseController expenseController = new ExpenseController(expenseModel, expenseView);
  }
}
