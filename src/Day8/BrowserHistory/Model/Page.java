package Day8.BrowserHistory.Model;

public class Page {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Page(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
