import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

class HiloCliente extends Thread {
    //crear variable para el puerto
    private int puerto;

    //arreglo para almacenar las preguntas y la respuesta
    private Map<String, String> preguntas;

    public HiloCliente(int puerto) {
        this.puerto = puerto;
        this.preguntas = new HashMap<>();
        this.preguntas.put("Iniciales de escuela politecnica", "epn");
        this.preguntas.put("tercer planeta del sistema solar", "tierra");
        this.preguntas.put("amigo de doraemon", "nobita");
        this.preguntas.put("capital de ecuador", "quito");
        this.preguntas.put("Religión de jesus", "cristiana");
    }

    @Override
    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket(puerto);
            //mensaje para ver la conexión
            System.out.println("Esperando la conexión del cliente...");

            while (true) {
                byte[] bufferEntrada = new byte[1024];
                DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                socket.receive(paqueteEntrada);

                //obtener la dirección del cliente
                InetAddress direccionCliente = paqueteEntrada.getAddress();
                int puertoCliente = paqueteEntrada.getPort();

                System.out.println("Conexión establecida con el cliente en la dirección IP: " + direccionCliente.getHostAddress());

                int puntajeTotal = 0;
                //evaluar las preguntas
                for (Map.Entry<String, String> entry : preguntas.entrySet()) {
                    String pregunta = entry.getKey();
                    String respuestaCorrecta = entry.getValue();

                    // Enviar pregunta al cliente
                    byte[] bufferPregunta = pregunta.getBytes();
                    DatagramPacket paquetePregunta = new DatagramPacket(bufferPregunta, bufferPregunta.length, direccionCliente, puertoCliente);
                    socket.send(paquetePregunta);

                    // Recibir respuesta del cliente
                    byte[] bufferRespuesta = new byte[1024];
                    DatagramPacket paqueteRespuesta = new DatagramPacket(bufferRespuesta, bufferRespuesta.length);
                    socket.receive(paqueteRespuesta);
                    String respuesta = new String(paqueteRespuesta.getData(), 0, paqueteRespuesta.getLength());

                    // Verificar respuesta
                    String mensajeSalida;
                    if (respuesta.trim().equalsIgnoreCase(respuestaCorrecta)) {
                        mensajeSalida = "Respuesta correcta";
                        puntajeTotal += 4;
                    } else {
                        mensajeSalida = "Respuesta incorrecta. La respuesta correcta era: " + respuestaCorrecta;
                    }

                    // Enviar confirmación al cliente
                    byte[] bufferSalida = mensajeSalida.getBytes();
                    DatagramPacket paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length, direccionCliente, puertoCliente);
                    socket.send(paqueteSalida);
                }

                // Enviar puntaje final al cliente
                String puntajeFinal = "Puntaje final: " + puntajeTotal;
                byte[] bufferPuntaje = puntajeFinal.getBytes();
                DatagramPacket paquetePuntaje = new DatagramPacket(bufferPuntaje, bufferPuntaje.length, direccionCliente, puertoCliente);
                socket.send(paquetePuntaje);

                System.out.println("Puntaje final enviado al cliente: " + puntajeTotal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
