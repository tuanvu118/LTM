package B17;

import java.io.EOFException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void writeFully(SocketChannel channel, ByteBuffer buffer) throws Exception {
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }

    public static void readFully(SocketChannel channel, ByteBuffer buffer) throws Exception {
        while (buffer.hasRemaining()) {
            int n = channel.read(buffer);
            if (n == -1) throw new EOFException();
        }
    }

    public static void sendFrame(SocketChannel channel, String data) throws Exception {
        byte[] payload = data.getBytes(StandardCharsets.UTF_8);

        ByteBuffer buffer = ByteBuffer.allocate(4 + payload.length);

        buffer.putInt(payload.length);
        buffer.put(payload);

        buffer.flip();

        writeFully(channel, buffer);
    }

    public static String readFrame(SocketChannel channel) throws Exception {

        // Đọc 4 byte độ dài
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        readFully(channel, lengthBuffer);

        lengthBuffer.flip();

        int length = lengthBuffer.getInt();

        // Đọc payload
        ByteBuffer payloadBuffer = ByteBuffer.allocate(length);
        readFully(channel, payloadBuffer);

        payloadBuffer.flip();

        return StandardCharsets.UTF_8.decode(payloadBuffer).toString();
    }
    public static String getString(String json, String key) {
        String start = "\"" + key + "\":\"";
        int a = json.indexOf(start) + start.length();
        int b = json.indexOf("\"", a);
        return json.substring(a, b);
    }

    public static boolean getBoolean(String json, String key) {
        String start = "\"" + key + "\":";
        int a = json.indexOf(start) + start.length();
        return json.startsWith("true", a);
    }

    public static void main(String[] args) throws Exception {

        SocketChannel channel = SocketChannel.open(
                new InetSocketAddress("36.50.135.242", 2211)
        );

        // 1. Gửi studentCode;qCode
        sendFrame(channel, "B23DCCN894;Q0De4Mc9");

        // 2. Nhận đúng 2 frame
        String frame1 = readFrame(channel);
        String frame2 = readFrame(channel);

        // 3. Nối 2 payload
        String json = frame1 + frame2;

        System.out.println("JSON: " + json);

        String event = getString(json, "event");
        String user = getString(json, "user");
        String ok = getBoolean(json, "ok") ? "1" : "0";

        String result = "event=" + event + ";user=" + user + ";ok=" + ok;

        System.out.println("Result: " + result);

        // 8. Gửi lại server dưới dạng frame
        sendFrame(channel, result);

        channel.close();
    }
}