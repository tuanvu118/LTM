package B12;

import java.io.IOException;
import java.net.*;

public class Main {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();

        InetAddress address =
                InetAddress.getByName("36.50.135.242");

        int port = 2208;

        // a. Gui request
        String request = ";B23DCCN894;gSeL8Bd1";

        byte[] sendData = request.getBytes();

        DatagramPacket packet =
                new DatagramPacket(
                        sendData,
                        sendData.length,
                        address,
                        port
                );

        socket.send(packet);

        // b. Nhan du lieu
        byte[] buf = new byte[1024];

        DatagramPacket receive =
                new DatagramPacket(buf, buf.length);

        socket.receive(receive);

        String response = new String(
                receive.getData(),
                0,
                receive.getLength()
        );

        // response: requestId;data
        String[] parts = response.split(";");

        String requestId = parts[0];
        String data = parts[1];

        // c. Chuan hoa chuoi
        String[] words = data.trim().split(" ");

        String ans = "";

        for (int i = 0; i < words.length; i++) {

            String word = words[i];

            word = word.substring(0, 1).toUpperCase()
                    + word.substring(1).toLowerCase();

            ans += word + " ";
        }

        ans = ans.trim();

        // Ghep requestId;data
        String result = requestId + ";" + ans;

        byte[] send2 = result.getBytes();

        DatagramPacket packet2 =
                new DatagramPacket(
                        send2,
                        send2.length,
                        address,
                        port
                );

        socket.send(packet2);

        // d. Dong socket
        socket.close();
    }
}
