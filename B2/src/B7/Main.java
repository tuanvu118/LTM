package B7;

import java.net.*;
import java.io.*;

import static java.lang.Math.max;

public class Main {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address =InetAddress.getByName("36.50.135.242");
        int port = 2208;
        String request = ";B23DCCN894;XSujsN6o";
        byte[] send = request.getBytes();
        DatagramPacket packet = new DatagramPacket(send,send.length,address,port);
        socket.send(packet);

        byte[] buf = new byte[1024];
        DatagramPacket recieve = new DatagramPacket(buf, buf.length);
        socket.receive(recieve);
        String reponse = new String(recieve.getData(),0,recieve.getLength());
        String[] arr = reponse.split(";");
        String requestId = arr[0].trim();
        String xau = arr[1].trim();
        int[] a = new int[256];
        for(int i=0; i < xau.length() ; i++){
            a[xau.charAt(i)]++;
        }
        int max = 0;
        char c = 0;

        for (int i = 0; i < xau.length(); i++) {
            char ch = xau.charAt(i);

            if (a[ch] > max) {
                max = a[ch];
                c = ch;
            }
        }
        String tmp=requestId+";"+c+":";
        for(int i=1; i<= xau.length(); i++){
            if(xau.charAt(i-1)==c){
//                int g=i+1;
                tmp+=i+",";
            }
        }
        byte[] send2 = tmp.getBytes();
        DatagramPacket packet2 = new DatagramPacket(send2,send2.length,address, port);
        socket.send(packet2);
        socket.close();
    }
}
