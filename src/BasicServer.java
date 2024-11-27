import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BasicServer {
    public static void main(String[] args) {
        System.out.println("Serveur multi-exercices démarré...\n");

        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Le serveur écoute sur le port 5000.");

            while (true) {
                Socket socket = server.accept();
                System.out.println("Client connecté : " + socket.getInetAddress());

                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader input = new BufferedReader(isr);

                OutputStream os = socket.getOutputStream();
                PrintWriter output = new PrintWriter(os, true);

                String message;
                while ((message = input.readLine()) != null) {
                    if (message.equalsIgnoreCase("bye")) {
                        System.out.println("Fin de communication avec le client.");
                        output.println("Au revoir !");
                        break;
                    }

                    // Gestion des différents exercices
                    if (message.startsWith("CAPITALIZE:")) {
                        // Exercice 2 : Capitaliser le message
                        String toCapitalize = message.substring(11).trim();
                        String capitalized = toCapitalize.toUpperCase();
                        System.out.println("Message capitalisé : " + capitalized);
                        output.println("Capitalisé : " + capitalized);

                    } else if (message.startsWith("CALCUL:")) {
                        // Exercice 3 : Calculer une opération
                        String operation = message.substring(7).trim();
                        try {
                            String result = evaluateOperation(operation);
                            System.out.println("Opération : " + operation + " = " + result);
                            output.println("Résultat : " + result);
                        } catch (Exception e) {
                            System.out.println("Erreur dans le calcul : " + operation);
                            output.println("Erreur : opération invalide.");
                        }

                    } else {
                        // Exercice 1 : Réponse générique
                        System.out.println("Message reçu : " + message);
                        output.println("Serveur a reçu : " + message);
                    }
                }

                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour évaluer une opération arithmétique
    private static String evaluateOperation(String operation) {
        String[] parts = operation.split(" ");
        double num1 = Double.parseDouble(parts[0]);
        String operator = parts[1];
        double num2 = Double.parseDouble(parts[2]);

        switch (operator) {
            case "+":
                return String.valueOf(num1 + num2);
            case "-":
                return String.valueOf(num1 - num2);
            case "*":
                return String.valueOf(num1 * num2);
            case "/":
                return num2 != 0 ? String.valueOf(num1 / num2) : "Division par zéro!";
            default:
                return "Opérateur non valide!";
        }
    }
}
