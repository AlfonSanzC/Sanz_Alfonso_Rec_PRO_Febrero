import java.io.*;
import java.nio.file.*;
import java.util.*;

public class conversor {

    private static Scanner teclado = new Scanner(System.in);
    private static Path carpetaSeleccionada = null;
    private static Path ficheroSeleccionado = null; 
     // Lista para almacenar las líneas del fichero
    private static List<String> datosFichero = new ArrayList<>();

    //Main
    public static void main(String[] args) {
        menuPrincipal();
    }

    //Menú
    private static void menuPrincipal() {
        int opcion;
        do {
            System.out.println("\n========== CONVERSOR ==========");
            System.out.println("Carpeta seleccionada: " + (carpetaSeleccionada != null ? carpetaSeleccionada.toString() : "Ninguna"));
            System.out.println("Fichero seleccionado: " + (ficheroSeleccionado != null ? ficheroSeleccionado.getFileName() : "Ninguno"));
            System.out.println("1. Seleccionar Carpeta");
            System.out.println("2. Leer Fichero");
            System.out.println("3. Convertir Fichero");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");
            opcion = teclado.nextInt();
            teclado.nextLine(); 

            switch (opcion) {
                case 1:
                    seleccionarCarpeta();
                    break;
                case 2:
                    leerFichero();
                    break;
                case 3:
                    convertirFichero();
                    break;
                case 4:
                    System.out.println("¡Gracias por usar el Conversor!");
                    break;
                default:
                    System.out.println("Opción inválida. Inténtalo de nuevo.");
            }
        } while (opcion != 4);
    }

    private static void seleccionarCarpeta() {
        // Pido al usuario que introduzca la ruta de la carpeta
        System.out.println("\nUn ejemplo de ruta: C:\\Users\\usuario\\Documents\\convesorPrueba");
        System.out.print("Introduce la ruta de la carpeta: ");
        // Lee la ruta ingresada por el usuario
        String ruta = teclado.nextLine();

        // Creo el Path correspondiente
        Path carpeta = Paths.get(ruta);

        // Verificación si existe y es un directorio
        if (Files.exists(carpeta) && Files.isDirectory(carpeta)) {
            carpetaSeleccionada = carpeta;
            ficheroSeleccionado = null;
            // Limpiamos los datos de cualquier fichero anterior
            datosFichero.clear(); 
            System.out.println("Carpeta seleccionada correctamente: " + carpetaSeleccionada);
        } else {
            System.out.println("La ruta ingresada no es válida o no es una carpeta.");
        }
    }

    private static void leerFichero() {
         // Verifica si se ha seleccionado una carpeta
        if (carpetaSeleccionada == null) {
            System.out.println("Primero selecciona una carpeta.");
            return;
        }

        // Pido al usuario que introduzca el nombre del fichero con extensión
        // es decir .txt o lo que sea el fichero
        System.out.print("Introduce el nombre del fichero (con extensión): ");
        String nombreFichero = teclado.nextLine();

        // Obtenemos la ruta completa del fichero
        Path fichero = carpetaSeleccionada.resolve(nombreFichero);

        // Verifica si el fichero existe
        if (Files.exists(fichero) && Files.isRegularFile(fichero)) {
            ficheroSeleccionado = fichero;
            try {
                // Lee todas las líneas del fichero
                datosFichero = Files.readAllLines(fichero);
                System.out.println("Fichero leído correctamente.");
            } catch (IOException e) {
                System.out.println("Error al leer el fichero: " + e.getMessage());
            }
        } else {
            System.out.println("El fichero no existe o no es válido.");
        }
    }

    // Verifica si hay un fichero seleccionado
    private static void convertirFichero() {
        if (ficheroSeleccionado == null) {
            System.out.println("Primero selecciona y lee un fichero.");
            return;
        }

         //Pido al usuario que seleccione un formato y lo convierte en minúscula
        System.out.println("Selecciona el formato de salida (csv, json, xml): ");
        String formato = teclado.nextLine().toLowerCase();

        // Verifica si el formato es válido, en minúsucula
        if (!formato.equals("csv") && !formato.equals("json") && !formato.equals("xml")) {
            System.out.println("Formato inválido.");
            return;
        }

         // Pide el nombre del fichero de salida
        System.out.println("Introduce el nombre del fichero de salida (sin extensión): ");
        String nombreSalida = teclado.nextLine();

        // Creo la ruta para el archivo de salida
        Path ficheroSalida = carpetaSeleccionada.resolve(nombreSalida + "." + formato);

        //Escribe el contenido en el formato seleccionado, convierte el fichero
        // BufferedWriter sirve para escribir las líneas tranformadas al archivo de salida
        try (BufferedWriter escritor = Files.newBufferedWriter(ficheroSalida)) {
            System.out.println("Convirtiendo fichero...");

            // Transformamos el contenido según el formato seleccionado
            //Formato csv -> Reemplaza espacios por comas
            //Reemplaza los espacios (" ") por comas (","), es un formato de los archivos CSV
            if (formato.equals("csv")) {
                for (String linea : datosFichero) {
                    escritor.write(linea.replace(" ", ",") + "\n");
                }
            //Formato json -> Formatea como un array de cadenas
            //Abre un objeto con { y define un array, en mi caso llamado contenido
            //Añade cada linea del archivo como un elemento del array
            //Añade una coma al final de cada elemento, menos el último
            //Cierra el array y el objeto
            } else if (formato.equals("json")) {
                escritor.write("{\n  \"contenido\": [\n");
                for (int i = 0; i < datosFichero.size(); i++) {
                    escritor.write("    \"" + datosFichero.get(i) + "\"");
                    if (i < datosFichero.size() - 1) {
                        escritor.write(",");
                    }
                    escritor.write("\n");
                }
                escritor.write("  ]\n}");
            //Formato xml -> Envuelve cada línea en etiquetas <linea>
            //Abre una etiqueda <contenido>
            //Agrega cada línea del archivo como un elemento <linea> dentro de <contenido>
            //Cierra las etiquetas
            } else if (formato.equals("xml")) {
                escritor.write("<contenido>\n");
                for (String linea : datosFichero) {
                    escritor.write("  <linea>" + linea + "</linea>\n");
                }
                escritor.write("</contenido>");
            }

            System.out.println("Fichero convertido y guardado en: " + ficheroSalida);
        } catch (IOException e) {
            System.out.println("Error al escribir el fichero de salida: " + e.getMessage());
        }
    }
}
