import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Formatter;
import exceptions.ExcessiveFailedLoginException;
import exceptions.InvalidPropertyException;

public class User {
    private String firstName;
    private String lastName;
    private Character gender;
    private String address;
    private String userName;
    private String password;
    private MessageDigest digest;

    private static final int maxLoginAttempts = 3;
    private static int LoginAttempts;

    private String hash (String strToHash) {
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(strToHash.getBytes (StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(byte b: hash)
                sb.append(String.format("%02x", b));
            return sb.toString();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public User(String firstName, String lastName, Character gender, String address, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.address = address;
        this.userName = userName;
        this.password = hash (password);
    }

    public boolean login(String username, String password) throws ExcessiveFailedLoginException{
        if(this.userName.equals(username )) {
            if(LoginAttempts == maxLoginAttempts) {
                LoginAttempts++;
                throw new ExcessiveFailedLoginException();
            } else if (LoginAttempts>maxLoginAttempts){
                throw new ExcessiveFailedLoginException("Anda telah mencapai batas login");
            }
            if(this.password.equals(hash (password))){
                LoginAttempts = 0;
                return true;
            } else {
                System.out.println("Password yang anda masukkan salah");
                System.out.print("Kesempatan Anda Login"+(maxLoginAttempts-LoginAttempts));
                System.out.println(" Kali Lagi");
                LoginAttempts++;
            }
        }
        return false;
    }

    public String greeting() {
        String greet = "Selamat Datang!";
        switch(gender) {
            case 'L' : greet+=" Tuan "; break;
            case 'P' : greet+=" Nona "; break;
        }
        greet += this.firstName + " " + this.lastName;
        return greet;
    }
    public String getUserName() {
        return userName;
    }
}

