package server;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ExitThread extends Thread
{
    public void run()
    {
        while(true) {
            Scanner input = new Scanner(System.in, StandardCharsets.UTF_8);
            String exit = input.nextLine();
            if (exit.equals("/exit")) {
                System.exit(0);
            }
        }
    }
}