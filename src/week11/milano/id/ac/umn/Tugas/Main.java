package week11.milano.id.ac.umn.Tugas;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exceptions.AuthenticationException;
import exceptions.ExcessiveFailedLoginException;
import exceptions.InvalidPropertyException;

public class Main {

    private List<User> listOfUser = new ArrayList<>();

    //Inisialisasi Default user
    public void initialize() {
        User u = new User(
                "John",
                "Doe",
                'L',
                "Jl. Merpati No. 1 RT 1 RW 1, Banten",
                "admin",
                "admin"
        );
        listOfUser.add(u);
    }

    // Login
    public void handleLogin(String username, String password) throws AuthenticationException {
        boolean success = false;

        for (User u : listOfUser) {
            try {
                if (u.login(username, password)) {
                    System.out.println(u.greeting());
                    success = true;
                    break;
                }
            } catch (ExcessiveFailedLoginException e) {
                System.out.println(e.getMessage());
            }
        }

        if (!success) {
            throw new AuthenticationException("Username / Password salah");
        }
    }

    // validasi password
    private void validatePassword(String password) throws InvalidPropertyException {

        if (password.length() < 6 || password.length() > 16)
            throw new InvalidPropertyException("Password harus 6-16 karakter");

        boolean adaHurufBesar = false;
        boolean adaAngka = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) adaHurufBesar = true;
            if (Character.isDigit(c)) adaAngka = true;
        }

        if (!adaHurufBesar)
            throw new InvalidPropertyException("Password harus mengandung huruf besar");

        if (!adaAngka)
            throw new InvalidPropertyException("Password harus mengandung angka");
    }

    // sign up
    public void handleSignUp(String firstName, String lastName, char gender,
                             String address, String username, String password)
            throws InvalidPropertyException {

        validatePassword(password);

        User newUser = new User(firstName, lastName, gender, address, username, password);
        listOfUser.add(newUser);
        System.out.println("User berhasil terdaftar!");
    }

    public static void main(String[] args) {

        Main app = new Main();
        app.initialize();

        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("0. Exit");
            System.out.print("Pilih: ");

            String input = scan.nextLine();

            if (!input.matches("[0-9]")) {
                System.out.println("Input tidak valid.");
                continue;
            }

            int choice = Integer.parseInt(input);

            switch (choice) {

                case 1:
                    System.out.print("Username: ");
                    String uname = scan.nextLine();
                    System.out.print("Password: ");
                    String pass = scan.nextLine();

                    try {
                        app.handleLogin(uname, pass);
                    } catch (AuthenticationException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Nama Depan: ");
                        String fn = scan.nextLine();
                        System.out.print("Nama Belakang: ");
                        String ln = scan.nextLine();
                        System.out.print("Gender (L/P): ");
                        char g = scan.next().charAt(0);
                        scan.nextLine();
                        System.out.print("Alamat: ");
                        String addr = scan.nextLine();
                        System.out.print("Username: ");
                        String un = scan.nextLine();
                        System.out.print("Password: ");
                        String pw = scan.nextLine();

                        app.handleSignUp(fn, ln, g, addr, un, pw);

                    } catch (InvalidPropertyException e) {
                        System.out.println("Gagal daftar: " + e.getMessage());
                    }
                    break;

                case 0:
                    System.out.println("Exiting Prgram");
                    scan.close();
                    return;

                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }
}
