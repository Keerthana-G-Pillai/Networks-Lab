
import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer{
    static Vector <PrintWriter> v=new Vector<>();
    public static void main(String args[] )throws Exception{
            ServerSocket ss=new ServerSocket(5000);
            while(true){
                Socket s=ss.accept();
                Thread t = new Thread(()->{
                    try{

                        BufferedReader in=new BufferedReader(new InputStreamReader(s.getInputStream()));
                        PrintWriter out=new PrintWriter(s.getOutputStream(),true);
                        String name=in.readLine();
                        String msg;
                        v.add(out);
                        System.out.println(name+"joined");
                        while((msg=in.readLine())!=null){
                            if (msg.equals("/exit")) break;
                            for(PrintWriter p:v){
                                if(p!=out) {
                                    p.println(msg+"result");
                                }
                            }
                        }
                        v.remove(out);
                        System.out.println(name+"left");

                    }
                    catch(Exception e){}
                    finally{
                        try{
                            s.close();
                        }
                        catch(Exception e){}
                    }
                });
                t.start();
            }
    }
}
