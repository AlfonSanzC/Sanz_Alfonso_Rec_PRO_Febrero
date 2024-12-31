import java.util.Scanner;
import java.util.Random;

/**
 * Clase principal del juego Cuatro en Raya.
 * Implementa las funcionalidades del juego para jugar contra otro jugador o una IA.
 */
public class cuatroenRaya {
    //Variables estáticas del tablero
    private static final int FILAS = 6;
    private static final int COLUMNAS = 7;
    private static final char VACIO = '.';
    // Variables del juego
    private static char[][] tablero = new char[FILAS][COLUMNAS];
    private static Scanner scanner = new Scanner(System.in);
    private static char simboloJugador1 = 'X'; // Jugador 1
    private static char simboloJugador2 = 'O'; // Jugador 2
    private static int numeroPartidas = 3; 
    private static String ordenDeSalida = "Jugador 1"; // Define quien empieza en este caso jugador 1
    private static boolean contraIA = false;
    private static int partidasJugadas = 0;
    private static int partidasGanadasJugador1 = 0;
    private static int partidasGanadasJugador2 = 0;

    /**
     * Método principal. Inicia el programa.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        inicializarTablero();
        mostrarMenuPrincipal();
    }

     /**
     * Inicializa el tablero con celdas vacías.
     */
    //Primero relleno el tablero vacío
    private static void inicializarTablero() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                tablero[i][j] = VACIO;
            }
        }
    }

    /**
     * Muestra el menú principal y gestiona las opciones seleccionadas por el usuario.
     */
    //Menú principal, que redirecciona a las distintas opciones
    private static void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("\n¡Bienvenido a Cuatro en Raya!");
            System.out.println("1. Vista de Bienvenida");
            System.out.println("2. Vista de Instrucciones");
            System.out.println("3. Vista de Configuración");
            System.out.println("4. Vista de Juego");
            System.out.println("5. Vista de Resultados");
            System.out.println("6. Créditos");
            System.out.println("7. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1 -> mostrarVistaBienvenida();
                case 2 -> mostrarVistaInstrucciones();
                case 3 -> mostrarVistaConfiguracion();
                case 4 -> mostrarVistaJuego();
                case 5 -> mostrarVistaResultados();
                case 6 -> mostrarCreditos();
                case 7 -> System.out.println("¡Gracias por jugar!");
                default -> System.out.println("Opción Inválida. Inténtalo de nuevo.");
            }
        } while (opcion != 7);
    }

    /**
     * Comprueba si un jugador ha ganado.
     *
     * @param jugador El símbolo del jugador ('X' o 'O').
     * @return true si el jugador ha ganado, false en caso contrario.
     */
    // Comprobación de si tenemos ganador
    private static boolean comprobarGanador(char jugador) {
        // Comprobar filas horizontales
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS - 3; j++) {
                if (tablero[i][j] == jugador && tablero[i][j + 1] == jugador &&
                    tablero[i][j + 2] == jugador && tablero[i][j + 3] == jugador) {
                    return true;
                }
            }
        }
    
        // Comprobar filas verticales
        for (int i = 0; i < FILAS - 3; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (tablero[i][j] == jugador && tablero[i + 1][j] == jugador &&
                    tablero[i + 2][j] == jugador && tablero[i + 3][j] == jugador) {
                    return true;
                }
            }
        }
    
        // Verificar diagonales principales 
        for (int i = 0; i < FILAS - 3; i++) {
            for (int j = 0; j < COLUMNAS - 3; j++) {
                if (tablero[i][j] == jugador && tablero[i + 1][j + 1] == jugador &&
                    tablero[i + 2][j + 2] == jugador && tablero[i + 3][j + 3] == jugador) {
                    return true;
                }
            }
        }
    
        // Verificar diagonales secundarias 
        for (int i = 0; i < FILAS - 3; i++) {
            for (int j = 3; j < COLUMNAS; j++) {
                if (tablero[i][j] == jugador && tablero[i + 1][j - 1] == jugador &&
                    tablero[i + 2][j - 2] == jugador && tablero[i + 3][j - 3] == jugador) {
                    return true;
                }
            }
        }
    
        return false;
    }
    
    /**
     * Muestra la vista de bienvenida.
     * Ofrece opciones al usuario para elegir entre varias acciones:
     * - Elegir modalidad de juego.
     * - Iniciar juego.
     * - Configurar parámetros.
     * - Ver los créditos.
     * El usuario debe ingresar una letra ('A', 'B', 'C', 'D').
     */
    // Vista de bienvenida
    // Aquí hay que usar las letras a, b ,c o d 
    // Indistantemente entre mayúscula o minúscula
    private static void mostrarVistaBienvenida() {
        System.out.println("\n--- Vista de Bienvenida ---");
        System.out.println("A. Elegir Modalidad");
        System.out.println("B. Iniciar Juego");
        System.out.println("C. Configuración");
        System.out.println("D. Créditos");
        System.out.print("Elige una opción: ");
        char opcion = scanner.nextLine().toUpperCase().charAt(0);

        switch (opcion) {
            case 'A' -> elegirModalidad();
            case 'B' -> iniciarJuego();
            case 'C' -> mostrarVistaConfiguracion();
            case 'D' -> mostrarCreditos();
            default -> System.out.println("Opción Inválida. Inténtalo de nuevo.");
        }
    }

    /**
     * Permite al usuario elegir la modalidad de juego.
     * Modalidades disponibles:
     * - Contra la IA.
     * - Contra otro jugador.
     */
    // Esto es cuando eliges la modalidad de las opciones anteriores
    private static void elegirModalidad() {
        System.out.println("\n--- Elegir Modalidad ---");
        System.out.println("1. Jugar contra la IA");
        System.out.println("2. Jugar contra otro jugador");
        System.out.print("Elige una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine(); 

        if (opcion == 1) {
            contraIA = true;
            System.out.println("Has elegido jugar contra la IA.");
        } else if (opcion == 2) {
            contraIA = false;
            System.out.println("Has elegido jugar contra otro jugador.");
        } else {
            System.out.println("Opción Inválida. Vuelve a intentarlo.");
        }
    }

     /**
     * Muestra las instrucciones del juego.
     * Explica las reglas básicas y el objetivo del juego Cuatro en Raya.
     */
    // Vista con instrucciones
    private static void mostrarVistaInstrucciones() {
        System.out.println("\n--- Vista de Instrucciones ---");
        System.out.println("El objetivo de Conecta 4 es alinear cuatro fichas sobre un tablero formado por seis filas y siete columnas.");
        System.out.println("Por turnos, los jugadores deben introducir una ficha en la columna que prefieran (siempre que no esté completa) y ésta caerá a la posición más baja.");
        System.out.println("Gana la partida el primero que consiga alinear cuatro fichas consecutivas de un mismo color en horizontal, vertical o diagonal.");
        System.out.println("Si todas las columnas están llenas pero nadie ha hecho una fila válida, hay empate.");
    }

     /**
     * Muestra la configuración del juego y permite al usuario personalizar:
     * - Símbolo de los jugadores.
     * - Número de partidas (mejor de 3 o mejor de 5).
     * - Orden de salida (aleatorio, ganador, perdedor, siempre Jugador 1).
     */
    //Muestra la vista de configuración con sus diferentes opciones
    private static void mostrarVistaConfiguracion() {
        System.out.println("\n--- Configuración ---");

        // Símbolo de los jugadores
        System.out.print("Elige el símbolo para el Jugador 1 (X o O): ");
        simboloJugador1 = scanner.nextLine().toUpperCase().charAt(0);
        simboloJugador2 = (simboloJugador1 == 'X') ? 'O' : 'X';
        System.out.println("El Jugador 2 usará el símbolo " + simboloJugador2);

        // Número de partidas
        System.out.println("Cuántas partidas se jugarán? 1. Al mejor de 3 o Al mejor de 5");
        int opcionPartidas = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea
        numeroPartidas = (opcionPartidas == 1) ? 3 : 5;

        // Orden de salida
        System.out.println("Orden de salida:");
        System.out.println("1. Aleatorio");
        System.out.println("2. Sale Ganador");
        System.out.println("3. Sale Perdedor");
        System.out.println("4. Sale Siempre Jugador 1");
        int opcionOrden = scanner.nextInt();
        scanner.nextLine();
        switch (opcionOrden) {
            case 1 -> ordenDeSalida = "Aleatorio";
            case 2 -> ordenDeSalida = "Sale Ganador";
            case 3 -> ordenDeSalida = "Sale Perdedor";
            case 4 -> ordenDeSalida = "Jugador 1";
            default -> System.out.println("Opción no válida. Se mantendrá el orden actual.");
        }

        System.out.println("Configuración guardada.");
    }

    /**
     * Muestra la vista de juego y el tablero inicial.
     * Inicia el proceso de la partida llamando a otros métodos.
     */
    private static void mostrarVistaJuego() {
        inicializarTablero();
        System.out.println("\n--- Vista de Juego ---");
        mostrarTablero();
    }

     /**
     * Inicia el proceso de juego por rondas.
     * El ganador se decide al alcanzar la cantidad requerida de partidas ganadas.
     */
    private static void iniciarJuego() {
        while (partidasGanadasJugador1 < numeroPartidas / 2 + 1 && partidasGanadasJugador2 < numeroPartidas / 2 + 1) {
            inicializarTablero();
            jugarPartida();
        }

        mostrarVistaResultados();
    }

    /**
     * Juega una partida individual.
     * Alterna entre los turnos de los jugadores o la IA.
     * Declara al ganador o un empate al final de la partida.
     */
    // Para jugar la partida
    private static void jugarPartida() {
        char turnoActual = (ordenDeSalida.equals("Aleatorio") && new Random().nextBoolean()) ? simboloJugador2 : simboloJugador1;

        System.out.println("\n--- Nueva Partida ---");
        boolean ganador = false;

        while (!tableroLleno() && !ganador) {
            mostrarTablero();
            if (turnoActual == simboloJugador2 && contraIA) {
                System.out.println("Turno de la IA...");
                realizarMovimientoIA(simboloJugador2);
            } else {
                System.out.println("Turno de " + (turnoActual == simboloJugador1 ? "Jugador 1" : "Jugador 2"));
                realizarMovimiento(turnoActual);
            }

            ganador = comprobarGanador(turnoActual);
            if (ganador) {
                mostrarTablero();
                System.out.println("¡Ha ganado " + (turnoActual == simboloJugador1 ? "Jugador 1" : "Jugador 2") + "!");
                if (turnoActual == simboloJugador1) {
                    partidasGanadasJugador1++;
                } else {
                    partidasGanadasJugador2++;
                }
                break;
            }

            turnoActual = (turnoActual == simboloJugador1) ? simboloJugador2 : simboloJugador1;
        }

        if (!ganador) {
            System.out.println("¡Empate!");
        }

        partidasJugadas++;
    }

    /**
     * Vista de resultados con partidas jugadas/partidas ganadas cada jugador
     */
    // Vista de resultados
    private static void mostrarVistaResultados() {
        System.out.println("\n--- Vista de Resultados ---");
        System.out.println("Partidas jugadas: " + partidasJugadas);
        System.out.println("Partidas ganadas por Jugador 1: " + partidasGanadasJugador1);
        System.out.println("Partidas ganadas por Jugador 2: " + partidasGanadasJugador2);
        System.out.println("¡Gracias por jugar Conecta 4!");
    }

    /**
     * Muestra los créditos
     * Quién ha realizado el programa
     */
    // Muestra los créditos
    private static void mostrarCreditos() {
        System.out.println("\n--- Créditos ---");
        System.out.println("Realizado por Alfonso Sanz 2º DAW B.");
    }

    /**
     * Coloca movimientos aleatorios para la ia, usando el random
     * En las columnas para jugar partidas contra otro jugador o IA
     * @param jugador
     */
    // Primero genero las columnas aleatorias con ia, con un random
    // Me aseguro que se encuentra dentro del rango, osea que no esté llena
    // Y en el for se coloca la ficha de la ia abajo del todo
    private static void realizarMovimientoIA(char simboloIA) {
        Random random = new Random();
        int columna;
        boolean valido;

        do {
            columna = random.nextInt(COLUMNAS);
            valido = columna >= 0 && columna < COLUMNAS && tablero[0][columna] == VACIO;
        } while (!valido);

        for (int i = FILAS - 1; i >= 0; i--) {
            if (tablero[i][columna] == VACIO) {
                tablero[i][columna] = simboloIA;
                break;
            }
        }
    }

    /**
     * Muestra el tablero en consola
     */
    // Muestra el tablero en consola
    private static void mostrarTablero() {
        for (char[] fila : tablero) {
            for (char celda : fila) {
                System.out.print(celda + " ");
            }
            System.out.println();
        }
    }

    /**
     * Coloca movimientos del jugador
     * En las columnas para jugar partidas contra otro jugador o IA
     * @param jugador
     */
    // Solicitamos la columna al jugador por consola
    // Validamos y verificamos, si no mostrará el mensaje de error
    // Y por último colocará la ficha en la fila más baja de la columna
    private static void realizarMovimiento(char jugador) {
        int columna;
        boolean valido;
        do {
            System.out.print("Elige una columna (1-" + COLUMNAS + "): ");
            columna = scanner.nextInt() - 1;
            valido = columna >= 0 && columna < COLUMNAS && tablero[0][columna] == VACIO;
            if (!valido) {
                System.out.println("Movimiento inválido. Inténtalo de nuevo.");
            }
        } while (!valido);

        for (int i = FILAS - 1; i >= 0; i--) {
            if (tablero[i][columna] == VACIO) {
                tablero[i][columna] = jugador;
                break;
            }
        }
    }

    /**
     * Comprueba si el tablero está lleno
     * @return
     */
    // Esto comprueba si el tablero se encuentra lleno
    private static boolean tableroLleno() {
        for (char[] fila : tablero) {
            for (char celda : fila) {
                if (celda == VACIO) {
                    return false;
                }
            }
        }
        return true;
    }
}
