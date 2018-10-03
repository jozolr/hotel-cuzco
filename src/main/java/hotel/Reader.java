package hotel;

import java.util.Scanner;

public class Reader {

    private final Scanner scanner;

    public Reader() {
        this.scanner = new Scanner(System.in);
    }

    public String next() {
        return scanner.next();
    }
}
