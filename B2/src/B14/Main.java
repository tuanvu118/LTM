package B14;

import UDP.Customer;

import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("36.50.135.242");
        int port = 2209;
        String request = ";B23DCCN894;VHGuQUwa";
        byte[] send = request.getBytes();
        DatagramPacket packet = new DatagramPacket(send,send.length,address,port);
        socket.send(packet);
        byte[] buf =new byte[1024];
        DatagramPacket recieve = new DatagramPacket(buf,buf.length);
        socket.receive(recieve);
        String requestId = new String(recieve.getData(),0,8);
        ByteArrayInputStream  bis = new ByteArrayInputStream(recieve.getData(),8, recieve.getLength()-8);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Customer customer = (Customer) ois.readObject();

        String[] arr = customer.getName().trim().split("\\s+");
        String userName = "";

        for (int i = 0; i < arr.length - 1; i++) {
            userName += arr[i].charAt(0);
        }

        userName += arr[arr.length - 1];
        customer.setUserName(userName.toLowerCase());

        String name = arr[arr.length - 1].toUpperCase() + ", ";

        for (int i = 0; i < arr.length - 1; i++) {
            name += Character.toUpperCase(arr[i].charAt(0))
                    + arr[i].substring(1).toLowerCase();

            if (i < arr.length - 2) {
                name += " ";
            }
        }

        customer.setName(name);


// Doi ngay sinh
        String[] day = customer.getDayOfBirth().split("-");

        String dayCh = day[1] + "/" + day[0] + "/" + day[2];

        customer.setDayOfBirth(dayCh);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(customer);
        oos.flush();

        byte[] objectData = bos.toByteArray();
        byte[] requestData = requestId.getBytes();
        byte[] results = new byte[8+objectData.length];
        for(int i=0;i<8;i++){
            results[i] = requestData[i];
        }
        for (int i=0;i<objectData.length;i++){
            results[i+8]=objectData[i];
        }
        DatagramPacket packet1 = new DatagramPacket(results,results.length,address,port);
        socket.send(packet1);
        socket.close();
    }
}
