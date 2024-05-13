import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class cliente {
    public static void main(String[] args) {
        int puerto = 5000;

        try {
            DatagramSocket socket = new DatagramSocket();

            // Dirección IP del servidor
            InetAddress direccionIP_servidor = InetAddress.getByName("172.31.115.147");

            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Leer mensaje desde el teclado
                System.out.print("Ingrese su mensaje: ");
                String mensajeSalida = scanner.nextLine();

                // Crear arreglo de bytes para enviar los datos del cliente
                byte[] bufferSalida = mensajeSalida.getBytes();

                // Crear objeto datagrama para enviar los datos
                DatagramPacket paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length, direccionIP_servidor, puerto);
                socket.send(paqueteSalida);

                // Comprobar si el cliente desea cerrar la conexión
                if (mensajeSalida.equalsIgnoreCase("cerrar")) {
                    System.out.println("Cerrando la conexión...");
                    break; // Salir del bucle
                }

                // Crear arreglo de bytes para recibir los datos del cliente
                byte[] bufferEntrada = new byte[1024];

                // Crear objeto datagrama para recibir los datos
                DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);

                // Recibir el paquete
                socket.receive(paqueteEntrada);

                // Extraer la información del paquete
                String mensajeRecibido = new String(paqueteEntrada.getData(), 0, paqueteEntrada.getLength());
                System.out.println("Respuesta del servidor: " + mensajeRecibido);
            }

            // Cerrar el socket al salir del bucle
            socket.close();

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}