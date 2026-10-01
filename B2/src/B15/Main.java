package B15;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.*;

public class Main {
    public static void main(String[] args) throws IOException {
            Socket socket = new Socket("36.50.135.242",2210);
            OutputStream outputStream = socket.getOutputStream();
            ByteArrayOutputStream req = new ByteArrayOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(req);
            String request = "B23DCCN894;dMzrGgPk\n";
            gzipOutputStream.write(request.getBytes());
            gzipOutputStream.finish();
            outputStream.write(req.toByteArray());
            outputStream.flush();

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            GZIPInputStream gzipInputStream = new GZIPInputStream(socket.getInputStream());
            int b;
            while((b=gzipInputStream.read())!=-1){
                if(b == '\n') break;
                buffer.write(b);
            }

            String line = new String(buffer.toByteArray()).trim();

            String reversed = new StringBuilder(line).reverse().toString();
            String base64 = Base64.getEncoder().encodeToString(reversed.getBytes());

            String ans = reversed + "|" + base64 + "\n";

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            GZIPOutputStream gzipOutputStream1 = new GZIPOutputStream(byteArrayOutputStream);
            gzipOutputStream1.write(ans.getBytes());
            gzipOutputStream1.finish();
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.flush();
            socket.close();
    }
}
