package B16;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;
import java.util.zip.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("36.50.135.242",2210);
        OutputStream outputStream = socket.getOutputStream();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);

        String request = "B23DCCN894;RenLzHbD\n";
        gzipOutputStream.write(request.getBytes());
        gzipOutputStream.finish();

        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.flush();

        ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
        GZIPInputStream gzipInputStream = new GZIPInputStream(socket.getInputStream());

        int b;
        while(true){
            b=gzipInputStream.read();
            if(b=='\n') break;
            byteArrayOutputStream1.write(b);
        }

        String line = new String(byteArrayOutputStream1.toByteArray());
        char [] ans = line.toCharArray();
        Arrays.sort(ans);
        String result = new String(ans);
        result += "\n";

        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream1 = new GZIPOutputStream(byteArrayOutputStream2);
        gzipOutputStream1.write(result.getBytes());
        gzipOutputStream1.finish();
        outputStream.write(byteArrayOutputStream2.toByteArray());
        outputStream.flush();
        socket.close();
    }
}
