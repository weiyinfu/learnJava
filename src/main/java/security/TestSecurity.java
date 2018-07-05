package security;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

//java安全管理器
public class TestSecurity {
static class PasswordSecurityManager extends SecurityManager {
    private String password;

    PasswordSecurityManager(String password) {
        super();
        this.password = password;
    }

    private boolean accessOK() {
        int c;
        // DataInputStream dis = new DataInputStream(System.in);
        BufferedReader dis = new BufferedReader(
                new InputStreamReader(System.in));
        String response;
        System.out.println("What's the secret password?");
        try {
            response = dis.readLine();
            if (response.equals(password))
                return true;
            else
                return false;
        } catch (IOException e) {
            return false;
        }
    }

    public void checkRead(FileDescriptor filedescriptor) {
        if (!accessOK())
            throw new SecurityException("Not a Chance!");
    }

    public void checkRead(String filename) {
        if (!accessOK())
            throw new SecurityException("No Way!");
    }

    public void checkRead(String filename, Object executionContext) {
        if (!accessOK())
            throw new SecurityException("Forget It!");
    }

    public void checkWrite(FileDescriptor filedescriptor) {
        if (!accessOK())
            throw new SecurityException("Not!");
    }

    public void checkWrite(String filename) {
        if (!accessOK())
            throw new SecurityException("Not Even!");
    }
}

public static void main(String args[]) {
    try {
        System.setSecurityManager(new PasswordSecurityManager("123456"));
    } catch (SecurityException se) {
        System.out.println("SecurityManager already set!");
    }
    try {
        BufferedReader fis = new BufferedReader(
                new FileReader("input.txt"));
        BufferedWriter fos = new BufferedWriter(
                new FileWriter("output.txt"));
        String inputString;
        while ((inputString = fis.readLine()) != null) {
            fos.write(inputString + "\n");
        }
        fis.close();
        fos.close();
    } catch (IOException ioe) {
        System.out.println("I/O failed for SecurityManagerTest.");
        ioe.printStackTrace();
    } catch (Exception e) {
        System.out.println(e.toString());
        e.printStackTrace();
    }
}
}
