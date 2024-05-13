import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Hilocliente extends Thread{

    private DatagramSocket socket;
    private DatagramPacket paqueteEntrada;

    public Hilocliente(DatagramSocket socket, DatagramPacket paqueteEntrada){
        this.socket=socket;
        this.paqueteEntrada=paqueteEntrada;

    }

    public void run (){
         //Extraer la informacion del paquete
         String mensajeRecibido = new String (paqueteEntrada.getData());
         System.out.println("El mensaje es: "+ mensajeRecibido);

         //Obtener direccion cliente
         InetAddress direccionIp_Cliente = paqueteEntrada.getAddress();
         int puerto_cliente=paqueteEntrada.getPort();

          //crear un arrglo de bytes para recibir los datos del cliente
          String mensajeSalida = "Hola soy el servidor";
          byte[]bufferSalida = mensajeSalida.getBytes();
          //crear objeto datagrama para enviar los datos del cliente
          DatagramPacket paqueteSalida = new DatagramPacket (bufferSalida,bufferSalida.length,direccionIp_Cliente,puerto_cliente);
          try {
            socket.send(paqueteSalida);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
