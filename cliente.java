// Step 5: Modify cliente class
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class cliente {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Socket socket_cliente;

        while (true) {
            try {
                socket_cliente = new Socket("localhost", 5000);
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket_cliente.getInputStream()));
                PrintWriter salida = new PrintWriter(socket_cliente.getOutputStream(), true);

                while (true) {
                    String pregunta = entrada.readLine();
                    if (pregunta == null) break;

                    System.out.println("Pregunta: " + pregunta);
                    String respuesta = scanner.nextLine();
                    salida.println(respuesta);

                    String resultado = entrada.readLine();
                    System.out.println(resultado);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}