import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("36.50.135.242",2206);
        InputStream in =socket.getInputStream();
        OutputStream out =socket.getOutputStream();
        String request ="B23DCCN894;OxkOtqmy";
        out.write(request.getBytes());
        out.flush();

        byte[] buf= new byte[1024];
        int len = in.read(buf);
        String ans=new String(buf, 0, len);
        String[] arr=ans.split(",");
        int[] a= new int[arr.length];
        int[] b= new int[arr.length];
        for(int i=0;i<arr.length;i++){
            a[i] = Integer.parseInt(arr[i]);
            b[i] = Integer.parseInt(arr[i]);
        }
        Arrays.sort(a);
        int ans1=0,pos=0;
        for(int i=0;i<arr.length;i++){
            if(b[i]==a[a.length-2]){
                ans1=b[i];
                pos=i;
            }
        }
        String tmp = ans1 +","+pos;
        out.write(tmp.getBytes());
        socket.close();
    }
}
