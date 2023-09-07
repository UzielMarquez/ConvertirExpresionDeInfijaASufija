import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

public class InfijaASufija {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.print("\nExpresion (Escribe quit para salir): ");
            String expresion = input.nextLine().toLowerCase();

            if (expresion.equals("quit")) {
                input.close();
                break;
            }

            try {
                String expresionPosfija = convertInfToPost(expresion);
                System.out.println("Expresion Posfija: " + expresionPosfija);
            } catch (Exception e) {
                System.out.println("Error en la expresion: " + e.getMessage());
            }
        }
    }

    // Metodo que convierte la expresión infija en una expresión posfija.
    public static String convertInfToPost(String expresionInfija) throws Exception {
        StringBuilder expresionPosfija = new StringBuilder(); 
        Stack<Character> pila = new Stack<>();
        StringTokenizer st = new StringTokenizer(expresionInfija, "()[}{}+-*/", true);

        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            char caracter = token.charAt(0); // Convierte los token a caracteres

            if (caracter == '(') {
                pila.push(caracter); // Hace un push del paréntesis de apertura en la pila
            } else if (caracter == ')') {
                while (!pila.isEmpty() && pila.peek() != '(') {
                    expresionPosfija.append(pila.pop()).append(" "); // No toma en cuenta los operadores hasta encontrar un parentesis de apertura
                }
                if (pila.isEmpty() || pila.peek() != '(') {
                    throw new Exception("Paréntesis desequilibrados"); // Genera una excepción si los paréntesis están desequilibrados
                }
                pila.pop(); // Pop el paréntesis de apertura '('
            } else if (Character.isDigit(caracter) || Character.isLetter(caracter)) {
                expresionPosfija.append(token).append(" "); // Agrega operandos a la expresión posfija
            } else if (esOperador(caracter)) {
                while (!pila.isEmpty() && pila.peek() != '(' && prioridad(caracter) <= prioridad(pila.peek())) {
                    expresionPosfija.append(pila.pop()).append(" "); // Desapila operadores de mayor prioridad
                }
                pila.push(caracter); // Empuja el operador actual en la pila
            }
        }

        while (!pila.isEmpty()) {
            expresionPosfija.append(pila.pop()).append(" "); // Quita de la pila los operadores restantes
        }

        return expresionPosfija.toString().trim();
    }

    // Verifica si el carácter es un operador
    public static boolean esOperador(char caracter) {
        return caracter == '+' || caracter == '-' || caracter == '*' || caracter == '/';
    }

    // Obtiene la prioridad de un operador
    public static int prioridad(char operador) {
        if (operador == '+' || operador == '-') {
            return 1;
        } else if (operador == '*' || operador == '/') {
            return 2;
        }
        return 0; // Devuelve 0 para otros tokens
    }
}