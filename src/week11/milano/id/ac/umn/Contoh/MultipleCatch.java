package week11.milano.id.ac.umn.Contoh;

import java.util.Scanner;

public class MultipleCatch {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        try
        {
            int n = Integer.parseInt(scn.nextLine());
            int result = 10/n;
            System.out.println(result);
        }
        catch (ArithmeticException ex)
        {
            System.out.println("Arithmetic "+ ex);
        }
        catch (NumberFormatException ex)
        {
            System.out.println("Number Format Exception " + ex);
        }
    }
}
