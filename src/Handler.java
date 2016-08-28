import java.io.*;
import java.net.Socket;

/**
 * Created by jellyBananas on 2016/8/28.
 */
public class Handler implements Runnable {
    private Socket socket;
    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader request = null;
        OutputStream out = null;
        try {
            request = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            out = socket.getOutputStream(); //!!!
            out = new OutputStream() {
                @Override
                public void write(int b) throws IOException {

                }
            };
            out = socket.getOutputStream();
            String string = "";
            string = request.readLine(); //读HTTP请求的第一行
            if (string.indexOf("GET") > -1){
                String fileName = string.replace("HTTP/1.1","").trim();
                fileName = fileName.replace("GET","").trim();
                if (fileName.length()==1){
                    fileName = "index.html";
                }
                response(out,fileName);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                socket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    private void response (OutputStream out, String fileName){
        FileInputStream in = null;
        try {
            in = new FileInputStream("webapps/"+fileName);
            byte[] buf = new byte[1024];
            int temp = 0;

            out.write("HTTP/1.1 200 OK\r\n".getBytes());
            out.write("Content-Type:text/html\r\n\r\n".getBytes());
            while ((temp = in.read(buf))!=-1){
                out.write(buf,0,temp);
                out.flush();
            }
        }catch (IOException e){
            e.printStackTrace();
            try {
                in.close();
                out.close();
            }catch (IOException e2){
                e2.printStackTrace();
            }
        }

    }
}
