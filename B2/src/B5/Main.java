package B5;

import java.io.*;
import java.net.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
//        Socket socket = new Socket("36.50.135.242", 2207);
//        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
//        DataInputStream in = new DataInputStream(socket.getInputStream());
//        out.writeUTF("B23DCCN894;K3R17i0b");
//        out.flush();
//        int a = in.readInt();
//        int b = in.readInt();
//        out.writeInt(a+b);
//        out.writeInt(a*b);
//        out.flush();
//        socket.close();
        Socket socket = new Socket("36.50.135.242", 2207);

        DataOutputStream out =
                new DataOutputStream(socket.getOutputStream());

        DataInputStream in =
                new DataInputStream(socket.getInputStream());

        String request = "B23DCCN894;K3R17iOb";

        out.writeUTF(request);
        out.flush();

        System.out.println("Da gui request");

        int a = in.readInt();
        System.out.println("a = " + a);

        int b = in.readInt();
        System.out.println("b = " + b);

        out.writeInt(a + b);
        out.writeInt(a * b);
        out.flush();

        System.out.println("Da gui ket qua");

        socket.close();
    }
}
