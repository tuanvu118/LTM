import java.io.*;
import java.net.*;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        Socket socket =new Socket("36.50.135.242", 2206);
        InputStream in =socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        String request ="B23DCCN894;Y5FccQGv";
        out.write(request.getBytes());
        out.flush();

        byte[] buf =new byte[1024];
        int len = in.read(buf);
        String response = new String(buf,0,len);
        String[] arr = response.split(",");
        int[] number = new int[arr.length];
        for(int i=0;i<arr.length;i++){
            number[i] = Integer.parseInt(arr[i].trim());
        }
        Arrays.sort(number);
        int ans=number[1]-number[0], l=0, r=0;
        for(int i=1;i<arr.length;i++){
            if(number[i]-number[i-1]<=ans){
                ans = number[i]-number[i-1];

                l =number[i-1];
                r=number[i];
            }
        }
        String ans1=ans+","+l+","+r;
        out.write(ans1.getBytes());
        out.flush();
        socket.close();
    }
}