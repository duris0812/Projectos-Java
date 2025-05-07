import java.util.ArrayList;
import java.util.Scanner;

class Contacto {
    String nombre;
    String telefono;

    Contacto(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String toString() {
        return "Nombre: " + nombre + ", Teléfono: " + telefono;
    }
}

public class Agenda {
    static ArrayList<Contacto> contactos = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer
            switch (opcion) {
                case 1 -> agregarContacto();
                case 2 -> mostrarContactos();
                case 3 -> buscarContacto();
                case 4 -> eliminarContacto();
                case 5 -> System.out.println("Saliendo... ¡Hasta luego, Churris!");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    static void mostrarMenu() {
        System.out.println("\n--- AGENDA DE CONTACTOS ---");
        System.out.println("1. Agregar contacto");
        System.out.println("2. Mostrar contactos");
        System.out.println("3. Buscar contacto");
        System.out.println("4. Eliminar contacto");
        System.out.println("5. Salir");
        System.out.print("Elige una opción: ");
    }

    static void agregarContacto() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        contactos.add(new Contacto(nombre, telefono));
        System.out.println("✅ Contacto agregado.");
    }

    static void mostrarContactos() {
        if (contactos.isEmpty()) {
            System.out.println("📭 No hay contactos.");
        } else {
            System.out.println("📒 Lista de contactos:");
            for (Contacto c : contactos) {
                System.out.println(c);
            }
        }
    }

    static void buscarContacto() {
        System.out.print("Nombre a buscar: ");
        String nombre = scanner.nextLine();
        boolean encontrado = false;
        for (Contacto c : contactos) {
            if (c.nombre.equalsIgnoreCase(nombre)) {
                System.out.println("🔍 " + c);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("❌ No se encontró el contacto.");
        }
    }

    static void eliminarContacto() {
        System.out.print("Nombre a eliminar: ");
        String nombre = scanner.nextLine();
        contactos.removeIf(c -> c.nombre.equalsIgnoreCase(nombre));
        System.out.println("🗑 Contacto eliminado (si existía).");
    }
}
