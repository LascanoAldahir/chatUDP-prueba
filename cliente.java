import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class cliente {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DatagramSocket socket_cliente;

        try {
            socket_cliente = new DatagramSocket();
            InetAddress address = InetAddress.getByName("localhost");

            byte[] buf = new byte[1024]; // Aumentar el tamaño del búfer
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 5000);
            socket_cliente.send(packet); // Enviar mensaje inicial

            while (true) {
                // Limpiar el búfer antes de recibir
                buf = new byte[1024];
                packet = new DatagramPacket(buf, buf.length);
                socket_cliente.receive(packet);
                String pregunta = new String(packet.getData(), 0, packet.getLength()).trim();
                if (pregunta.isEmpty()) break;

                System.out.println("Pregunta: " + pregunta);
                String respuesta = scanner.nextLine();

                buf = respuesta.getBytes();
                packet = new DatagramPacket(buf, buf.length, address, 5000);
                socket_cliente.send(packet);

                // Limpiar el búfer antes de recibir
                buf = new byte[1024];
                packet = new DatagramPacket(buf, buf.length);
                socket_cliente.receive(packet);
                String resultado = new String(packet.getData(), 0, packet.getLength()).trim();
                System.out.println(resultado);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
