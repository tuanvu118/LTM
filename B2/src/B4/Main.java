package B4;

import java.io.*;
import java.net.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("36.50.135.242",2208);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String request ="B23DCCN894;HGgr7fAb";
        out.write(request);
        out.newLine();
        out.flush();

        String response = in.readLine();
        String[] arr = response.split(" ");
        String ans="";
        for(int i=0;i<arr.length;i++){
            ans+=arr[i].trim();
        }
        int[] a = new int[256];
        for(int i=0; i < ans.length(); i++){
            a[ans.charAt(i)]+=1;
        }
        String tmp="";
        for(int i=0;i<ans.length();i++){
            if(a[ans.charAt(i)]>1){
                tmp+=ans.charAt(i)+":"+a[ans.charAt(i)]+",";
                a[ans.charAt(i)]=1;
            }
        }
        out.write(tmp);
        out.newLine();
        out.flush();
        socket.close();
    }
}
