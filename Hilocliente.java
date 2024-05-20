// Step 2, 3, 4: Modify hiloCliente class
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class hiloCliente extends Thread {
    Scanner scanner = new Scanner(System.in);
    Socket socket_cliente;
    List<Question> questions;

    public hiloCliente(Socket socket_cliente, List<Question> questions) {
        this.socket_cliente = socket_cliente;
        this.questions = questions;
    }

    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket_cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(socket_cliente.getOutputStream(), true);

            for (Question question : questions) {
                salida.println(question.getQuestion());
                String respuesta = entrada.readLine();

                if (respuesta.equals(question.getCorrectAnswer())) {
                    salida.println("Correcto");
                } else {
                    salida.println("Incorrecto. La respuesta correcta es: " + question.getCorrectAnswer());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}