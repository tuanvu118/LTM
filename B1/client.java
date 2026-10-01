import java.io.*;
import java.net.*;
public class client{
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("36.50.135.242",2208);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String request = "B23DCCN894;8dTtFRY6";
        out.write(request);
        out.newLine();
        out.flush();
        String response = in.readLine();
        String[] domain = response.split(",");
        String ans="";
        for(String x: domain){
            x=x.trim();
            if(x.substring(x.length()-4).equals(".edu")){
                ans+=x+", ";
            }
        }
        ans=ans.substring(0,ans.length()-2);
        out.write(ans);
        out.newLine();
        out.flush();
        out.close();
        socket.close();
    }
}