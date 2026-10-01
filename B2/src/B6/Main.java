package B6;

import java.net.*;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("36.50.135.242");
        int port = 2207;
        String request = ";B23DCCN894;I7Yj2zar";
        byte[] sendDate = request.getBytes();
        DatagramPacket packet =new DatagramPacket(sendDate,sendDate.length,address,port);
        socket.send(packet);

        // nhan du lieu tu server
        byte[] buf =new byte[1024];
        DatagramPacket receive = new DatagramPacket(buf,buf.length);

        socket.receive(receive);

        String reponse = new String(receive.getData(),0,receive.getLength());
        String[] parts = reponse.split(";");
        String requestID = parts[0];
        int n = Integer.parseInt(parts[1]);
        String[] arr = parts[2].split(",");

        boolean[] x= new boolean[n+1];
        for(int i=0; i< arr.length; i++){
            x[Integer.parseInt(arr[i])]=true;
        }
        String ans = requestID+";";
        for(int i=1;i<=n;i++){
            if(x[i]==false){
                ans+=i+",";
            }
        }
        ans=ans.substring(0,ans.length()-1);

        byte[] tmp = ans.getBytes();
        DatagramPacket send = new DatagramPacket(tmp, tmp.length, address,port);
        socket.send(send);
        socket.close();
    }
}
