import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

public class servidor {
    public static void main(String[] args) {
        try {
            DatagramSocket socket_servidor = new DatagramSocket(5000);
            System.out.println("Esperando conexión...");

            List<Question> questions = Arrays.asList(
                    new Question("¿Cual es la raiz cuadrada de 4?, responda en numeros", "2"),
                    new Question("¿Cuales son las siglas de la Escuela Politecnica Nacional? Responda con numeros", "epn"),
                    new Question("¿Organo encargado de la respiracion?", "pulmon"),
                    new Question("¿Cual es el tercer planeta del sistema solar?", "tierra"),
                    new Question("Amigo de nobita", "doraemon")
            );

            byte[] buf = new byte[1024]; // Aumentar el tamaño del búfer
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            while (true) {
                // Limpiar el búfer antes de recibir
                buf = new byte[1024];
                packet = new DatagramPacket(buf, buf.length);
                socket_servidor.receive(packet);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();

                int score = 0;
                for (Question question : questions) {
                    String questionText = question.getQuestion();
                    buf = questionText.getBytes();
                    packet = new DatagramPacket(buf, buf.length, address, port);
                    socket_servidor.send(packet);

                    // Limpiar el búfer antes de recibir
                    buf = new byte[1024];
                    packet = new DatagramPacket(buf, buf.length);
                    socket_servidor.receive(packet);
                    String respuesta = new String(packet.getData(), 0, packet.getLength()).trim();

                    String resultado;
                    if (respuesta.equalsIgnoreCase(question.getCorrectAnswer())) {
                        resultado = "Correcto";
                        score += 4;
                    } else {
                        resultado = "Incorrecto. La respuesta correcta es: " + question.getCorrectAnswer();
                    }

                    buf = resultado.getBytes();
                    packet = new DatagramPacket(buf, buf.length, address, port);
                    socket_servidor.send(packet);
                }

                String puntaje = "Puntaje final: " + score;
                buf = puntaje.getBytes();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket_servidor.send(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
