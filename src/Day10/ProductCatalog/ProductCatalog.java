package Day10.ProductCatalog;

import Day10.StudentRankingSystem.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class ProductCatalog {
    public static void menu(){
        System.out.println("1. Add Product");
        System.out.println("2. Display Products");
        System.out.println("3. Sort by Price");
        System.out.println("4. Sort by Rating");
        System.out.println("5. Sort by Name");
        System.out.println("6. Search by Category");
        System.out.println("7. Product Ranking");
        System.out.println("8.Exit");
    }

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Product> products = new ArrayList<>();


    public static void main(String[] args){
        while (true){
            menu();
            System.out.print("Enter your choice : ");
            int choice = sc.nextInt();
            switch(choice){
                case 1:
                    addProduct();
                    break;
                case 2:
                    displayProducts();
                    break;
                case 3:
                    sortByPrice();
                    break;
                case 4:
                    sortByRating();
                    break;
                case 5:
                    sortByName();
                    break;
                case 6:
                    searchByCategory();
                    break;
                case 7:
                    productRanking();
                    break;
                case 8:
                    return;
            }

        }
    }

    public static void addProduct(){
        System.out.print("Enter id : ");
        int id = sc.nextInt();
        System.out.print("Enter name : ");
        String name = sc.next();
        System.out.print("Enter Category : ");
        String category = sc.next();
        System.out.print("Enter price : ");
        double price  = sc.nextDouble();
        System.out.print("Enter Rating : ");
        double rating = sc.nextDouble();

        Product product = new Product(id, name, category, price, rating);
        products.add(product);
    }

    public static void displayProducts(){
        for(Product item: products){
            System.out.println(item);
        }
    }

    public static void sortByPrice(){
        Comparator<Product> byPrice = Comparator.comparing(Product::getPrice);
        products.sort(byPrice);
    }

    public static void sortByRating(){
        Comparator<Product> byRating = Comparator.comparing(Product::getRating).reversed();
        products.sort(byRating);
    }

    public static void sortByName(){
        Comparator<Product> byName = Comparator.comparing(Product::getName);
        products.sort(byName);
    }

    public static void searchByCategory(){
        System.out.print("Enter category : ");
        String category = sc.next();
        for(Product item: products){
            if(category.equalsIgnoreCase(item.getCategory())){
                System.out.println(item);
            }
        }
    }

    public static void productRanking(){
        Comparator<Product> ranking =
                Comparator.comparingDouble(Product::getRating)
                        .reversed()
                        .thenComparingDouble(Product::getPrice)
                        .thenComparing(Product::getName);
        products.sort(ranking);
        for (Product item: products){
            System.out.println(item);
        }

    }


}
