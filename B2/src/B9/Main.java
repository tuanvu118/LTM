package B9;

import TCP.Laptop;

import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("36.50.135.242", 2209);
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject("B23DCCN894;YdiBLQA2");
        out.flush();

        Laptop laptop = (Laptop) in.readObject();
        String name =laptop.getName();
        String[] arr = name.split(" ");
        String temp = arr[0];
        arr[0]=arr[arr.length-1];
        arr[arr.length-1]=temp;
        laptop.setName(String.join(" ", arr));
        int n=laptop.getQuantity();
        String n_str = String.valueOf(n);
        laptop.setQuantity(Integer.parseInt( new StringBuilder(n_str).reverse().toString()));
        out.writeObject(laptop);
        out.flush();
        socket.close();
    }
}
