package B8;

import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("36.50.135.242", 2207);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF("B23DCCN894;T3VgpY8S");
        out.flush();

        String ca = in.readUTF();
        int s = in.readInt();
        String ans = "";
        for(int i=0;i<ca.length();i++){
            char c =ca.charAt(i);
            if(c>='A'&& c<='Z') {
                ans += (char) ((c - 'A' - s + 26) % 26 + 'A');
            }
            else ans+=c;
        }
        out.writeUTF(ans);
        out.flush();

        socket.close();
    }
}
