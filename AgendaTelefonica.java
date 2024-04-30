import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class AgendaTelefonica {
    private static final int MAX_CONTACTOS = 10;
    private Contacto[] agenda = new Contacto[MAX_CONTACTOS]; //Arreglo de objeto de tipo Contacto
    private int numContactos = 0;
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        AgendaTelefonica agendaTelefonica = new AgendaTelefonica();
        agendaTelefonica.menuPrincipal();
    }

    public void menuPrincipal() {
        int opcion;
        do {
            System.out.println("1. Añadir");
            System.out.println("2. Buscar");
            System.out.println("3. Modificar");
            System.out.println("4. Eliminar");
            System.out.println("5. Mostrar");
            System.out.println("6. Vaciar");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            while(!scanner.hasNextInt()){
                System.out.println("Invalido, Seleccione una de las opciones!\n");
                scanner.next();
             }
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume la nueva línea después del número

            switch (opcion) {
                case 1:
                    añadirContacto();
                    break;
                case 2:
                    buscarContacto();
                    break;
                case 3:
                    modificarContacto();
                    break;
                case 4:
                    eliminarContacto();
                    break;
                case 5:
                    mostrarContactos();
                    break;
                case 6:
                    vaciarAgenda();
                    break;
                case 7:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 7);
    }

    public void añadirContacto() {
        int opcion;
        String nombre; //Variable Local
        String telefono;
        
        if (numContactos < MAX_CONTACTOS) {
            System.out.print("Ingrese el nombre del contacto: ");
            
            while(true)
            { 
                nombre = scanner.nextLine();                
                //Definir el patron para validar solo letras
                Pattern patron=Pattern.compile("^[a-zA-Z]+$"); 
                Matcher matcher= patron.matcher(nombre);

                //Verificar si la cadena contiene solo letras           
                if(matcher.matches()){
                   break;
                } else {
                    System.out.println("Invalido, ingrese un nombre correcto.");
                    
                }
            }

            System.out.print("Ingrese el teléfono del contacto: ");
            while(true)
            { 
                telefono = scanner.nextLine();
                

                //Definir el patron para validar solo letras
                Pattern patron=Pattern.compile("^\\d{8}"); 
                Matcher matcher= patron.matcher(telefono);

                //Verificar si la cadena contiene solo letras           
                if(matcher.matches()){
                   break;
                } else {
                    System.out.println("Invalido, ingrese un teléfono correcto. Ejemplo: 22772160");
                    
                }
            }
            
            
            if (buscarContactoPorNombre(nombre) == -1) {
                agenda[numContactos] = new Contacto(nombre, telefono);
                numContactos++;
                System.out.println("Contacto añadido correctamente.");
            } else {
                System.out.println("Ya existe un contacto con ese nombre.");
                System.out.println("Desea añadir otro contacto con ese nombre, presione la tecla 1 para agregar, sino presione otra tecla");
                opcion = scanner.nextInt();
                if(opcion == 1){
                    agenda[numContactos] = new Contacto(nombre, telefono);
                    numContactos++;
                    System.out.println("Contacto añadido correctamente.");
                }
                else{
                    System.out.println("No se añadio el contacto.");
                }
            }
        } else {
            System.out.println("La agenda está llena. No se pueden agregar más contactos.");
        }
    }

    public void buscarContacto() {
        System.out.print("Ingrese el nombre del contacto a buscar: ");
        String nombre = scanner.nextLine();
        boolean encontrado = false;

        for (int i = 0; i < numContactos; i++) {
            if (agenda[i].nombre.equalsIgnoreCase(nombre)) {
                System.out.println("Nombre: " + agenda[i].nombre + ", Teléfono: " + agenda[i].telefono);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron contactos con ese nombre.");
        }
    }

    public void modificarContacto() {
        System.out.print("Ingrese el nombre del contacto a modificar: ");
        String nombre = scanner.nextLine();
        int indice = buscarContactoPorNombre(nombre);
        

        if (indice != -1) {
            System.out.print("Ingrese el nuevo nombre del contacto: ");
            agenda[indice].nombre= scanner.nextLine();   
            System.out.print("Ingrese el nuevo teléfono del contacto: ");
            agenda[indice].telefono = scanner.nextLine();
            System.out.println("Contacto modificado correctamente.");
        } else {
            System.out.println("No se encontró ningún contacto con ese nombre.");
        }
    }

    public void eliminarContacto() {
        System.out.print("Ingrese el nombre del contacto a eliminar: ");
        String nombre = scanner.nextLine();
        int indice = buscarContactoPorNombre(nombre);

        if (indice != -1) {
            for (int i = indice; i < numContactos - 1; i++) {
                agenda[i] = agenda[i + 1];
            }
            numContactos--;
            System.out.println("Contacto eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún contacto con ese nombre.");
        }
    }

    public void mostrarContactos() {
        if (numContactos > 0) {
            System.out.println("Listado de contactos:");
            for (int i = 0; i < numContactos; i++) {
                System.out.println("Nombre: " + agenda[i].nombre + ", Teléfono: " + agenda[i].telefono);
            }
        } else {
            System.out.println("La agenda está vacía. No hay contactos para mostrar.");
        }
    }

    public void vaciarAgenda() {
        System.out.print("¿Está seguro que desea vaciar la agenda? (s/n): ");
        String respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("s")) {
            agenda = new Contacto[MAX_CONTACTOS];
            numContactos = 0;
            System.out.println("Agenda vaciada correctamente.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private int buscarContactoPorNombre(String nombre) {
        for (int i = 0; i < numContactos; i++) {
            if (agenda[i].nombre.equalsIgnoreCase(nombre)) {
                return i;
            }
        }
        return -1;
    }
}
