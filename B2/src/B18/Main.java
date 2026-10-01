package B18;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void writeFully(SocketChannel channel, ByteBuffer buffer) throws IOException {
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }
    public static void readFully(SocketChannel channel, ByteBuffer buffer) throws IOException {
        while (buffer.hasRemaining()) {
            int n = channel.read(buffer);
            if (n == -1) throw new EOFException();
        }
    }
    public static void sendFrame(SocketChannel channel, String data) throws IOException {
        byte[] payload = data.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(4 + payload.length);
        buffer.putInt(payload.length);
        buffer.put(payload);
        buffer.flip();
        writeFully(channel, buffer);
    }
    public static String readFrame(SocketChannel channel) throws IOException {
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        readFully(channel, lengthBuffer);
        lengthBuffer.flip();
        int length = lengthBuffer.getInt();
        ByteBuffer payloadBuffer = ByteBuffer.allocate(length);
        readFully(channel, payloadBuffer);
        payloadBuffer.flip();
        return StandardCharsets.UTF_8.decode(payloadBuffer).toString();
    }
    public static void main(String[] args) throws IOException {
            String studentCode = "B23DCCN894";
            String qCode = "FDzJonLj";
            SocketChannel channel = SocketChannel.open(new InetSocketAddress("36.50.135.242", 2211));
            sendFrame(channel, studentCode + ";" + qCode);
            String frame1 = readFrame(channel);
            String frame2 = readFrame(channel);
            String frame3 = readFrame(channel);
            String httpRequest = frame1 + frame2 + frame3;

            System.out.println(httpRequest);
            String[] lines = httpRequest.split("\r\n");
            String[] firstLine = lines[0].split(" ");
            String method = firstLine[0];
            String path = firstLine[1];
            String host = "";

            for (String line : lines) {
                if (line.toLowerCase().startsWith("host:")) {
                    host = line.substring(5).trim();
                    break;
                }
            }
            String result = method + ";" + path + ";" + host;
            System.out.println("Result: " + result);
            sendFrame(channel, result);
    }
}