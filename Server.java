
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class Server {

    public static void main(String[] args) throws IOException {
        int copy = 0;
        int total = 0;

        ServerSocketChannel serversocketchannel = ServerSocketChannel.open();
        serversocketchannel.bind(new InetSocketAddress(1111));
        SocketChannel socketchannel = serversocketchannel.accept();

        ServerSocket svsocket = new ServerSocket(2222);
        Socket sock = svsocket.accept();
        DataInputStream inputnakrub = new DataInputStream(sock.getInputStream());
        DataOutputStream outputnakrub = new DataOutputStream(sock.getOutputStream());

        String path = "C:\\server\\";
        File files = new File(path);
        File[] file = files.listFiles();

        outputnakrub.writeInt(file.length);
        for (int i = 0; i < file.length; i++) {
            outputnakrub.writeUTF(file[i].getName());
            outputnakrub.writeLong(file[i].length() / (1048576));
        }
        int Choose = inputnakrub.readInt() - 1;
        outputnakrub.writeUTF(file[Choose].getName());
        outputnakrub.writeLong(file[Choose].length());
        byte[] buffer = new byte[8192];
        FileInputStream fis = new FileInputStream(file[Choose].getPath());
        int copyy = 0;
        while ((copyy = fis.read(buffer)) != -1) {
            outputnakrub.write(buffer, 0, copyy);
        }
        boolean finish = inputnakrub.readBoolean();
    }
}
