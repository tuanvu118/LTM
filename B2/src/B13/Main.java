package B13;

import UDP.Student;

import java.net.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DatagramSocket socket = new DatagramSocket();

        InetAddress address =
                InetAddress.getByName("36.50.135.242");

        int port = 2209;

        // a. Gui studentCode + qCode
        String request = ";B23DCCN894;rzoikuAO";

        byte[] sendData = request.getBytes();

        DatagramPacket sendPacket =
                new DatagramPacket(
                        sendData,
                        sendData.length,
                        address,
                        port
                );

        socket.send(sendPacket);

        // b. Nhan packet tu server
        byte[] buf = new byte[4096];

        DatagramPacket receive =
                new DatagramPacket(buf, buf.length);

        socket.receive(receive);

        // 8 byte dau la requestId
        String requestId =
                new String(receive.getData(), 0, 8);

        // cac byte con lai la object Student
        ByteArrayInputStream bis =
                new ByteArrayInputStream(
                        receive.getData(),
                        8,
                        receive.getLength() - 8
                );

        ObjectInputStream ois =
                new ObjectInputStream(bis);

        Student student =
                (Student) ois.readObject();

        // Lay ten ban dau
        String name = student.getName().trim();

        String[] words = name.split(" ");

        // c1. Tao email
        // nguyen van tuan nam
        // -> namnvt@ptit.edu.vn

        String email = words[words.length - 1].toLowerCase();

        for (int i = 0; i < words.length - 1; i++) {
            email += Character.toLowerCase(words[i].charAt(0));
        }

        email += "@ptit.edu.vn";

        student.setEmail(email);

        // c2. Chuan hoa ten
        String newName = "";

        for (int i = 0; i < words.length; i++) {

            String word = words[i];

            word = word.substring(0, 1).toUpperCase()
                    + word.substring(1).toLowerCase();

            newName += word + " ";
        }

        newName = newName.trim();

        student.setName(newName);

        // Chuyen Student thanh byte[]
        ByteArrayOutputStream bos =
                new ByteArrayOutputStream();

        ObjectOutputStream oos =
                new ObjectOutputStream(bos);

        oos.writeObject(student);
        oos.flush();

        byte[] objectData = bos.toByteArray();

        // Ghép:
        // 8 byte requestId + object Student
        byte[] requestIdData = requestId.getBytes();

        byte[] result =
                new byte[8 + objectData.length];

        for (int i = 0; i < 8; i++) {
            result[i] = requestIdData[i];
        }

        for (int i = 0; i < objectData.length; i++) {
            result[i + 8] = objectData[i];
        }

        // Gui lai server
        DatagramPacket resultPacket =
                new DatagramPacket(
                        result,
                        result.length,
                        address,
                        port
                );

        socket.send(resultPacket);

        // d. Dong socket
        socket.close();
    }
}
