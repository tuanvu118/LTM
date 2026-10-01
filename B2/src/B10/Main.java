package B10;
import TCP.Customer;

import java.io.*;
import java.util.*;
import java.net.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("36.50.135.242", 2209);

        ObjectOutputStream out =
                new ObjectOutputStream(socket.getOutputStream());
        out.flush();

        ObjectInputStream in =
                new ObjectInputStream(socket.getInputStream());

        // 1. Gửi mã sinh viên + mã câu hỏi
        out.writeObject("B23DCCN894;8WPEnNJh");
        out.flush();

        // 2. Nhận Customer
        Customer customer = (Customer) in.readObject();

        // Lấy tên ban đầu
        String name = customer.getName().trim();
        String[] a = name.split("\\s+");// tach nhieu dau cach

        // 3a. Tạo username
        String userName = "";

        for (int i = 0; i < a.length - 1; i++) {
            userName += a[i].charAt(0);
        }

        userName += a[a.length - 1];
        userName = userName.toLowerCase();

        customer.setUserName(userName);

        // 3b. Chuẩn hóa tên
        String newName =
                a[a.length - 1].toUpperCase() + ", ";

        for (int i = 0; i < a.length - 1; i++) {

            newName += Character.toUpperCase(a[i].charAt(0))
                    + a[i].substring(1).toLowerCase();

            if (i < a.length - 2) {
                newName += " ";
            }
        }

        customer.setName(newName);

        // 3c. Đổi ngày sinh mm-dd-yyyy -> dd/mm/yyyy
        String[] date =
                customer.getDayOfBirth().split("-");

        String newDate =
                date[1] + "/" + date[0] + "/" + date[2];

        customer.setDayOfBirth(newDate);

        // 4. Gửi lại object đã sửa
        out.writeObject(customer);
        out.flush();

        socket.close();
    }
}
