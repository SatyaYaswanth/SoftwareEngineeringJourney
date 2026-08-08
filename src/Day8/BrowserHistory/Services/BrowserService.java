package Day8.BrowserHistory.Services;
import Day8.BrowserHistory.Model.Page;
import java.util.Stack;
import java.util.Scanner;

public class BrowserService {
    private Stack<Page> history = new Stack<>();
    Scanner sc = new Scanner(System.in);
    String name;
    public void visitPage(){
        System.out.print("Enter page you want to visit : ");
        System.out.print("Enter page name : ");
        Page temp = new Page(name = sc.nextLine());
        history.push(temp);

    }
    public void currentPage() {

        if (history.empty()) {
            System.out.println("No page currently open.");
            return;
        }

        System.out.println("Viewing " + history.peek());
    }
    public void goBack(){
        if(history.size()<=1){
            System.out.print("No history");
            return;
        }
        history.pop();
        System.out.println("Viewing " + history.peek());

    }
    public void displayHistory(){
        System.out.print(history);
    }
    public void clearHistory(){
        history.clear();
        System.out.print("History cleared.");
    }



}
