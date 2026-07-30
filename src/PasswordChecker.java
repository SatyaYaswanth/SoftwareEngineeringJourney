import java.util.Scanner;

public class PasswordChecker {
    public static boolean hasUpperCase(String ps){
        for(int i=0;i<ps.length()-1;i++){
            if((ps.charAt(i) <= 90) & (ps.charAt(i) >= 65)){
                return true;
            }
        }
        return false;
    }

    public static boolean hasLowerCase(String ps){
        for(int i=0;i<ps.length()-1;i++){
            if((ps.charAt(i) <= 122) & (ps.charAt(i) >= 97)){
                return true;
            }
        }
        return false;
    }

    public static boolean hasDigit(String ps){
        for(int i=0;i<ps.length()-1;i++){
            if((ps.charAt(i) <= 9) & (0 <= ps.charAt(i))){
                return true;
            }
        }
        return false;
    }

    public static boolean hasSpecialCharacter(String ps){
        for(int i=0;i<ps.length()-1;i++){
            if((ps.charAt(i) == '@') || (ps.charAt(i) == '@')|| (ps.charAt(i) == '!')|| (ps.charAt(i) == '#')){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        String password;
        System.out.println("===Welcome to Password Checker===");
        System.out.print("Enter password : ");
        password = sc.next();
        if(password.length()<8){
            System.out.println("Should contain at least 8 Characters.");
        }
        if(!hasUpperCase(password)){
            System.out.println("Should contain at least one Upper character!");
        }
        if(!hasLowerCase(password)){
            System.out.println("Should contain at least one Lower character!");
        }
        if(!hasDigit(password)){
            System.out.println("Should contain at least one Digit!");
        }
        if(!hasSpecialCharacter(password)){
            System.out.println("Should contain at least one Special character!");
        }

    }
}


//Has at least 8 characters
//Contains at least one uppercase letter
//Contains at least one lowercase letter
//Contains at least one digit
//Contains at least one special character (such as @, #, $, !)