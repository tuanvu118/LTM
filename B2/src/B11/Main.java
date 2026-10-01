package B11;

import java.net.*;
import java.io.*;
import java.util.*;

import static java.lang.Math.*;

public class Main {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("36.50.135.242");
        int port = 2207;
        String request = ";B23DCCN894;LkqnmwQb";
        byte[] b =request.getBytes();
        DatagramPacket packet = new DatagramPacket(b,b.length,address,port);
        socket.send(packet);

        byte[] buf = new byte[1024];
        DatagramPacket recieve = new DatagramPacket(buf,buf.length);
        socket.receive(recieve);
        String response =new String(recieve.getData(),0,recieve.getLength());
        String[] arr = response.split(";");
        String requestid = arr[0];
        String[] num = arr[1].split(",");
        int maxAns=0,minAns= 100000000;
        for(int i=0;i< num.length;i++){
            maxAns=max(maxAns,Integer.parseInt(num[i]));
            minAns=min(minAns,Integer.parseInt(num[i]));
        }
        String ans=requestid+";"+ maxAns +"," + minAns;
        byte[] kq=ans.getBytes();
        DatagramPacket packet1 = new DatagramPacket(kq,kq.length,address,port);
        socket.send(packet1);
        socket.close();
    }
}
