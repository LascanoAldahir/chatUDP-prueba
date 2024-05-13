import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class servidor {

    public static void main(String[] args) {
        int puerto = 5000;
        try {
            // Crear un objeto socket
            DatagramSocket socket = new DatagramSocket(puerto);
            System.out.println("Servidor esperando conexiones......");

            while (true) {
                // Crear arreglo de bytes para recibir los datos del cliente
                byte[] bufferEntrada = new byte[1024];
                // Crear objeto DatagramPacket para recibir los datos
                DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);

                // Recibir el paquete
                socket.receive(paqueteEntrada);

                // Convertir los datos recibidos a String
                String mensajeRecibido = new String(paqueteEntrada.getData(), 0, paqueteEntrada.getLength());
                System.out.println("Cliente dice: " + mensajeRecibido);

                // Leer entrada del usuario en el servidor
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Servidor: ");
                String mensajeServidor = br.readLine();

                // Convertir el mensaje del servidor a bytes
                byte[] bufferSalida = mensajeServidor.getBytes();

                // Obtener la dirección IP y el puerto del cliente
                InetAddress direccionIpCliente = paqueteEntrada.getAddress();
                int puertoCliente = paqueteEntrada.getPort();

                // Crear el DatagramPacket para enviar al cliente
                DatagramPacket paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length,
                        direccionIpCliente, puertoCliente);

                // Enviar el paquete al cliente
                socket.send(paqueteSalida);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}