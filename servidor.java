import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class servidor {
    public static void main(String[] args) {
        try {
            ServerSocket socket_servidor = new ServerSocket(5000);
            System.out.println("Esperando conexión...");

            // Create a list of questions
            List<Question> questions = Arrays.asList(
                    new Question(
                        "¿Cual es la raiz cuadrada de 4?, responda en numeros", "2"),
                    new Question(
                        "¿Cuales son las siglas de la Escuela Politecnica Nacional? Responda con números", "epn"),
                    new Question(
                        "¿Organo encargado de la respiracion?", "pulmon"),
                    new Question(
                        "¿Cual es el tercer planeta del sistema solar?", "tierra"),
                    new Question(
                        "Amigo de nobita", "doraemon")
            );

            while (true) {
                Socket socket_cliente = socket_servidor.accept();

                // Pass the list of questions to the hiloCliente constructor
                hiloCliente hilo = new hiloCliente(socket_cliente, questions);
                hilo.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}