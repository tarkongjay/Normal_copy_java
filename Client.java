/**
 *
 * @author nongguitar
 */
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;



public class Client {

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        SocketChannel socketconnect = SocketChannel.open();
        SocketAddress socketaddress = new InetSocketAddress("192.168.1.34", 1111);
        socketconnect.connect(socketaddress);

        Socket soccer = new Socket("192.168.1.34", 2222);
        DataInputStream inputnakrub = new DataInputStream(soccer.getInputStream());
        DataOutputStream outputnakrub = new DataOutputStream(soccer.getOutputStream());

        int n = inputnakrub.readInt();
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + ". " + inputnakrub.readUTF());
            System.out.println("Size: " + inputnakrub.readLong() + " MB");
        }
        int file = scan.nextInt();
        outputnakrub.writeInt(file);
        long size = inputnakrub.readLong();
        String name = inputnakrub.readUTF();
        String sendPath = "//home//nongguitar//Documents//client//" + name;
       
            long start = System.currentTimeMillis();
            byte[] buffer = new byte[8192];
            FileOutputStream fos = new FileOutputStream(sendPath);
            long copybytes = size;
            long bytescopied = 0;
            int copy = 0;
            while ((copy = inputnakrub.read(buffer)) != -1) {
                fos.write(buffer, 0, copy);
                bytescopied += copy;
                if(bytescopied == copybytes){
                    break;
                }
            }
            outputnakrub.writeBoolean(true);
            long end = System.currentTimeMillis();
            long time = end - start;
             System.out.println("time:  " + time +" millisecond");
        }
    }