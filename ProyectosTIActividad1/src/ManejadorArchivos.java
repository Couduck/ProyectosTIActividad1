import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManejadorArchivos      //Clase para manejar los archivos de HTML
{

    private ArrayList<String> DiccionarioLista = new ArrayList<String>();   //Lista donde se colocarán todas las palabras del proyecto completo
    public long tiempoInicio;

    private String[] files = new String[505];

    private HashMap<String, Palabra> hashcentral = new HashMap<>();

    private Hashtable<String, Palabra> hashTablecentral = new Hashtable<>(10, 0.5f);

    public ManejadorArchivos()
    {
        for(int i = 0; i < 502; i++)
        {
            files[i] = numeroAString(i+2);
        }

        files[502] = "hard";
        files[503] = "medium";
        files[504] = "simple";
    }

    public void almacenamientoHashTable() throws IOException
    {
        Sorter sort = new Sorter();

        boolean primeraVez = true;      //Variable booleana que indica si se realizará la primera escritura en el archivo de salida

        long inicioHashTableCompleta = System.currentTimeMillis();   //Empieza el conteo que indica el tiempo que el sistema tarda en generar el diccionario completo

        FileWriter salida = new FileWriter("Salidas\\DiccionarioHashTable.txt"), salida2 = new FileWriter("Salidas\\Salida8.txt");

        for(String pagina : files)
        {
            //Se genera el link del archivo del cual se van a obtener las palabras
            String linkActual = "Limpios\\" + pagina + ".txt";

            //Empieza el conteo del tiempo que se tarda en identificar las palabras del archivo actual
            long inicioLeerPagina = System.currentTimeMillis();

            HashMap<String, Palabra> coleccionPalabrasArchivo = contarPalabrasXArchivo(pagina);
            AniadirHashTableCentral(coleccionPalabrasArchivo, pagina);

            long finLeerPagina = System.currentTimeMillis();

            //Se obtiene el tiempo total de duración del conteo
            long duracionLeerPagina = finLeerPagina - inicioLeerPagina;

            //Se añade al archivo de salida el nombre de la página junto con su duración en segundos
            String entradaPagina = linkActual + "\t" + duracionLeerPagina/1000.0;

            if(primeraVez)
            {
                salida2.write(entradaPagina + "\n");
                primeraVez = false;
            }

            else
            {
                salida2.append(entradaPagina + "\n");
            }

            System.out.println("Las palabras del archivo " + pagina + " fueron procesadas.");
        }

        Set<String> llavesPalabras = hashTablecentral.keySet();

        primeraVez = true;

        System.out.print("\n----------\nGenerando archivo...");

        for(String llave : llavesPalabras)
        {
            int i = 0;

            Palabra palabra = hashTablecentral.get(llave);

            //todo: TERMINAR COMO ES QUE SE DEBE GUARDAR LA HASH TABLE EN EL ARCHIVO????

            if(primeraVez)
            {
                salida.write(llave + ", " + palabra.getFrecuencia() + ", " + llave.hashCode() + "\n");
                primeraVez = false;
            }

            else
            {
                salida.append(llave + ", " + palabra.getFrecuencia() + ", " + llave.hashCode() + "\n");
            }
        }

            HashMap<Integer, String> pruebaHashcodes = new HashMap<Integer, String>();
        HashMap<Integer, String> rechazo = new HashMap<Integer, String>();
            int colisiones = 0;

        for(String llave : llavesPalabras)
        {
            int i = 0;

            String obj = pruebaHashcodes.get(llave.hashCode());

            if(obj == null)
            {
                pruebaHashcodes.put(llave.hashCode(), llave);
            }

            else
            {
                colisiones++;
                rechazo.put(llave.hashCode(), llave + ", " +obj);
            }

        }

        salida.append("\nSe detectaron al menos " + colisiones + " colisiones.");

        long finHashTableCompleta = System.currentTimeMillis();

        long duracionHashTableCompleta = finHashTableCompleta - inicioHashTableCompleta;

        System.out.println("\n----------");

        salida2.append("\nEl tiempo requerido para crear el archivo de Diccionario sando una HashTable fue de " + duracionHashTableCompleta/1000.0 + " segundos");


        //Mismo proceso de arriba pero con la duración total del programa
        long finPrograma = System.currentTimeMillis();

        long duracionPrograma = finPrograma - tiempoInicio;

        salida2.append("\nLa duración del programa fue de " + duracionPrograma/1000.0 + " segundos");

        //Se cierra el documento
        salida.close();
        salida2.close();
    }

    public void generarPosting() throws IOException //Actividad 7 (NO COMENTADA)
    {
        hashcentral = new HashMap<>();

        Sorter sort = new Sorter();

        boolean primeraVez = true;      //Variable booleana que indica si se realizará la primera escritura en el archivo de salida

        long inicioTokenizacionCompleta = System.currentTimeMillis();   //Empieza el conteo que indica el tiempo que el sistema tarda en generar el diccionario completo

        FileWriter salida = new FileWriter("Salidas\\Posting.txt"), salida2 = new FileWriter("Salidas\\Salida7.txt");

        //Realiza con todos los archivos el siguiente ciclo
        for(String pagina : files)
        {
            //Se genera el link del archivo del cual se van a obtener las palabras
            String linkActual = "Limpios\\" + pagina + ".txt";

            //Empieza el conteo del tiempo que se tarda en identificar las palabras del archivo actual
            long inicioLeerPagina = System.currentTimeMillis();

            HashMap<String, Palabra> coleccionPalabrasArchivo = contarPalabrasXArchivo(pagina);
            AniadirAHashCentral(coleccionPalabrasArchivo, pagina);

            long finLeerPagina = System.currentTimeMillis();

            //Se obtiene el tiempo total de duración del conteo
            long duracionLeerPagina = finLeerPagina - inicioLeerPagina;

            //Se añade al archivo de salida el nombre de la página junto con su duración en segundos
            String entradaPagina = linkActual + "\t" + duracionLeerPagina/1000.0;

            if(primeraVez)
            {
                salida2.write(entradaPagina + "\n");
                primeraVez = false;
            }

            else
            {
                salida2.append(entradaPagina + "\n");
            }

            System.out.println("Las palabras del archivo " + pagina + " fueron procesadas.");
        }

        Collection<Palabra> listaObjetos = hashcentral.values();
        ArrayList<Palabra> PalabrasComoLista = new ArrayList<>(listaObjetos);

        primeraVez = true;

        System.out.print("\n----------\nGenerando archivo...");

        for(Palabra palabraCompleta : PalabrasComoLista)
        {
            int i = 0;

            for (String pagina : palabraCompleta.getArchivosAparece())
            {
                if(primeraVez)
                {
                    salida.write(pagina + ".html, " + palabraCompleta.getFrecuenciaArchivos().get(i) + "\n");
                    primeraVez = false;
                }

                else
                {
                    salida.append(pagina + ".html, " + palabraCompleta.getFrecuenciaArchivos().get(i) + "\n");
                }

                i++;
            }
        }

        long finTokenizacionCompleta = System.currentTimeMillis();

        long duracionTokenizacionCompleta = finTokenizacionCompleta - inicioTokenizacionCompleta;

        System.out.println("\n----------");

        salida2.append("\nEl tiempo requerido para crear el archivo de Posting fue de " + duracionTokenizacionCompleta/1000.0 + " segundos");

        //Mismo proceso de arriba pero con la duración total del programa
        long finPrograma = System.currentTimeMillis();

        long duracionPrograma = finPrograma - tiempoInicio;

        salida2.append("\nLa duración del programa fue de " + duracionPrograma/1000.0 + " segundos");

        //Se cierra el documento
        salida.close();
        salida2.close();
    }

    public void tokenizarTodosArchivos() throws IOException
    {
        hashcentral = new HashMap<>();

        Sorter sort = new Sorter();

        boolean primeraVez = true;      //Variable booleana que indica si se realizará la primera escritura en el archivo de salida

        long inicioTokenizacionCompleta = System.currentTimeMillis();   //Empieza el conteo que indica el tiempo que el sistema tarda en generar el diccionario completo

        FileWriter salida = new FileWriter("Salidas\\Tokenized.txt"), salida2 = new FileWriter("Salidas\\Salida6.txt");

        //Realiza con todos los archivos el siguiente ciclo
        for(String pagina : files)
        {
            //Se genera el link del archivo del cual se van a obtener las palabras
            String linkActual = "Limpios\\" + pagina + ".txt";

            //Empieza el conteo del tiempo que se tarda en identificar las palabras del archivo actual
            long inicioLeerPagina = System.currentTimeMillis();

            HashMap<String, Palabra> coleccionPalabrasArchivo = contarPalabrasXArchivo(pagina);
            AniadirAHashCentral(coleccionPalabrasArchivo, pagina);

            long finLeerPagina = System.currentTimeMillis();

            //Se obtiene el tiempo total de duración del conteo
            long duracionLeerPagina = finLeerPagina - inicioLeerPagina;

            //Se añade al archivo de salida el nombre de la página junto con su duración en segundos
            String entradaPagina = linkActual + "\t" + duracionLeerPagina/1000.0;

            if(primeraVez)
            {
                salida2.write(entradaPagina + "\n");
                primeraVez = false;
            }

            else
            {
                salida2.append(entradaPagina + "\n");
            }

            System.out.println("Las palabras del archivo " + pagina + " fueron procesadas.");
        }

        Collection<Palabra> listaObjetos = hashcentral.values();

        ArrayList<Palabra> PalabrasComoLista = new ArrayList<>(listaObjetos);

        primeraVez = true;

        System.out.print("\n----------\nGenerando archivo...");

        for(Palabra palabraCompleta : PalabrasComoLista)
        {

            if(primeraVez)
            {
                salida.write(palabraCompleta.getPalabra() + ", " + palabraCompleta.getFrecuencia() + ", " + palabraCompleta.getArchivos() + "\n");
                primeraVez = false;
            }

            else
            {
                salida.append(palabraCompleta.getPalabra() + ", " + palabraCompleta.getFrecuencia() + ", " + palabraCompleta.getArchivos() + "\n");
            }

        }

        long finTokenizacionCompleta = System.currentTimeMillis();

        long duracionTokenizacionCompleta = finTokenizacionCompleta - inicioTokenizacionCompleta;

        System.out.println("\n----------");

        salida2.append("\nEl tiempo requerido para tokenizar las palabras de todos los archivos fue de " + duracionTokenizacionCompleta/1000.0 + " segundos");

        //Mismo proceso de arriba pero con la duración total del programa
        long finPrograma = System.currentTimeMillis();

        long duracionPrograma = finPrograma - tiempoInicio;

        salida2.append("\nLa duración del programa fue de " + duracionPrograma/1000.0 + " segundos");

        //Se cierra el documento
        salida.close();
        salida2.close();
    }

    public void tokenizarArchivosActividad5() throws  IOException
    {
        String[] archivos = {"simple", "medium", "hard", "049"};

        Sorter sort = new Sorter();

        FileWriter salida1 = new FileWriter("Salidas\\Salida5_ABC.txt"), salida2 = new FileWriter("Salidas\\Salida5_FREC.txt");

        boolean primeraVez = true;      //Variable booleana que indica si se realizará la primera escritura en el archivo de salida

        long inicioCreacionDiccionario = System.currentTimeMillis();   //Empieza el conteo que indica el tiempo que el sistema tarda en generar el diccionario completo

        //Realiza con todos los archivos el siguiente ciclo
        for(String pagina : archivos)
        {
            //Se genera el link del archivo del cual se van a obtener las palabras
            String linkActual = "Limpios\\" + pagina + ".txt";

            //Empieza el conteo del tiempo que se tarda en identificar las palabras del archivo actual
            long inicioLeerPagina = System.currentTimeMillis();

            HashMap<String, Palabra> coleccionPalabrasArchivo = contarPalabrasXArchivo(pagina);

            AniadirAHashCentral(coleccionPalabrasArchivo, pagina);

            long finLeerPagina = System.currentTimeMillis();

            //Se obtiene el tiempo total de duración del conteo
            long duracionLeerPagina = finLeerPagina - inicioLeerPagina;

            //Se añade al archivo de salida el nombre de la página junto con su duración en segundos
            String entradaPagina = linkActual + "\t" + duracionLeerPagina/1000.0;

            System.out.println("Las palabras del archivo " + pagina + " fueron procesadas.");
        }

        Set<String> listaPalabras =hashcentral.keySet();
        Collection<Palabra> listaObjetos = hashcentral.values();

        ArrayList<String> PalabrasOrdenadasABC = new ArrayList<>(listaPalabras);
        ArrayList<Palabra> PalabrasOrdenadasFrec = new ArrayList<>(listaObjetos);
//
        sort.quickSortPalabrasABC(PalabrasOrdenadasABC, 0, PalabrasOrdenadasABC.size()-1);
        sort.quickSortPalabrasFrec(PalabrasOrdenadasFrec, 0, PalabrasOrdenadasFrec.size()-1);

        FileWriter ordenABC = new FileWriter("Salidas\\Salida5_ABC.txt"), ordenFREC = new FileWriter("Salidas\\Salida5_FREC.txt");

        System.out.print("\n----------\nGenerando ordenamiento alfabetico...");

        for(String palabra : PalabrasOrdenadasABC)
        {
            Palabra palabraCompleta = hashcentral.get(palabra);

            if(primeraVez)
            {
                ordenABC.write(palabraCompleta.getPalabra() + " " + palabraCompleta.getFrecuencia() + "\n");
                primeraVez = false;
            }

            else
            {
                ordenABC.append(palabraCompleta.getPalabra() + " " + palabraCompleta.getFrecuencia() + "\n");
            }

        }

        System.out.println("\n----------");


        System.out.print("\n----------\nGenerando ordenamiento por frecuencia...");
        primeraVez = true;

        for(Palabra palabraCompleta : PalabrasOrdenadasFrec)
        {
            if(primeraVez)
            {
                ordenFREC.write(palabraCompleta.getPalabra() + " " + palabraCompleta.getFrecuencia() + "\n");
                primeraVez = false;
            }

            else
            {
                ordenFREC.append(palabraCompleta.getPalabra() + " " + palabraCompleta.getFrecuencia() + "\n");
            }

        }

        System.out.println("\n----------");

        //Se cierra el documento
        ordenABC.close();
        ordenFREC.close();
    }

    public void generarDiccionarioCentral() throws  IOException
    {
        Sorter sort = new Sorter();

        FileWriter salida = new FileWriter("Salidas\\Salida4.txt"), diccionario = new FileWriter("DiccionarioCompleto.txt");

        boolean primeraVez = true;      //Variable booleana que indica si se realizará la primera escritura en el archivo de salida

        long inicioCreacionDiccionario = System.currentTimeMillis();   //Empieza el conteo que indica el tiempo que el sistema tarda en generar el diccionario completo

        //Realiza con todos los archivos el siguiente ciclo
        for(String pagina : files)
        {
            //Se genera el link del archivo del cual se van a obtener las palabras
            String linkActual = "Palabras\\" + pagina + ".txt";

            //Empieza el conteo del tiempo que se tarda en identificar las palabras del archivo actual
            long inicioLeerPagina = System.currentTimeMillis();

            combinarDiccionarios(linkActual);

            long finLeerPagina = System.currentTimeMillis();

            //Se obtiene el tiempo total de duración del conteo
            long duracionLeerPagina = finLeerPagina - inicioLeerPagina;

            //Se añade al archivo de salida el nombre de la página junto con su duración en segundos
            String entradaPagina = linkActual + "\t" + duracionLeerPagina/1000.0;

            //Dependiendo de si es la primera escritura, se escrbe o se anexa en el documento de texto
            if(primeraVez)
            {
                salida.write(entradaPagina + "\n");
                primeraVez = false;
            }

            else
            {
                salida.append(entradaPagina + "\n");
            }

            System.out.println("Las palabras del archivo " + pagina + " fueron procesadas.");
        }

        System.out.print("\n----------\nGenerando diccionario central...");

        sort.quickSortPalabrasABC(DiccionarioLista,0,DiccionarioLista.size()-1);

        System.out.println("\n----------");

        //Toda la lista de palabras se escribe dentro del
        diccionario.write(listaAString(DiccionarioLista));

        //Termina el proceso de borrado de etiquetas
        long finCreacionDiccionario = System.currentTimeMillis();

        //Se obtiene la duración total del borrado de todas las etiquetas
        long duracionCreacionDiccionario = finCreacionDiccionario - inicioCreacionDiccionario;

        //Se añade el tiempo al documento de salida
        salida.append("\nEl tiempo requerido para elaborar el diccionario central de todos los documentos fue de: " + duracionCreacionDiccionario/1000.0 + " segundos");

        //Mismo proceso de arriba pero con la duración total del programa
        long finPrograma = System.currentTimeMillis();

        long duracionPrograma = finPrograma - tiempoInicio;

        salida.append("\nLa duración del programa fue de " + duracionPrograma/1000.0 + " segundos");

        //Se cierra el documento
        salida.close();
        diccionario.close();
    }

    //Genera los diccionarios de cada archivo existente
    public void generarDiccionarios() throws IOException
    {
        FileWriter salida = new FileWriter("Salidas\\Salida3.txt");      //Se crea el archivo de salida de la información

        boolean primeraVez = true;      //Variable booleana que indica si se realizará la primera escritura en el archivo de salida

        long inicioCreacionDiccionario = System.currentTimeMillis();   //Empieza el conteo que indica el tiempo que el sistema tarda en generar el diccionario completo

        //Desde el archivo 002 hasta el 503 realiza este ciclo
        for(String pagina : files)
        {
            //Se genera el link del archivo del cual se van a obtener las palabras
            String linkActual = "Limpios\\" + pagina + ".txt";

            //Empieza el conteo del tiempo que se tarda en identificar las palabras del archivo actual
            long inicioLeerPagina = System.currentTimeMillis();
            extraerPalabrasArchivo(linkActual,pagina);
            long finLeerPagina = System.currentTimeMillis();

            //Se obtiene el tiempo total de duración del conteo
            long duracionLeerPagina = finLeerPagina - inicioLeerPagina;

            //Se añade al archivo de salida el nombre de la página junto con su duración en segundos
            String entradaPagina = linkActual + "\t" + duracionLeerPagina/1000.0;

            //Dependiendo de si es la primera escritura, se escrbe o se anexa en el documento de texto
            if(primeraVez)
            {
                salida.write(entradaPagina + "\n");
                primeraVez = false;
            }

            else
            {
                salida.append(entradaPagina + "\n");
            }


        }

        //Toda la lista de palabras se escribe dentro del
        //diccionario.write(listaAString(DiccionarioLista));

        //Termina el proceso de borrado de etiquetas
        long finCreacionDiccionario = System.currentTimeMillis();

        //Se obtiene la duración total del borrado de todas las etiquetas
        long duracionCreacionDiccionario = finCreacionDiccionario - inicioCreacionDiccionario;

        //Se añade el tiempo al documento de salida
        salida.append("\nEl tiempo requerido para elaborar el diccionario de cada documento fue de " + duracionCreacionDiccionario/1000.0 + " segundos");

        //Mismo proceso de arriba pero con la duración total del programa
        long finPrograma = System.currentTimeMillis();

        long duracionPrograma = finPrograma - tiempoInicio;

        salida.append("\nLa duración del programa fue de " + duracionPrograma/1000.0 + " segundos");

        //Se cierra el documento
        salida.close();
        //diccionario.close();
    }

    //Quita todas als etiquetas de la actividaad
    public void removerTodasLasEtiquetas() throws IOException
    {
        FileWriter salida = new FileWriter("Salidas\\Salida2.txt");       //Se crea el archivo de salida de la información

        boolean primeraVez = true;      //Variable booleana que indica si se realizará la primera escritura en el archivo de salida

        long inicioBorradoEtiquetas = System.currentTimeMillis();   //Empieza el conteo que indica el tiempo que el sistema tarda en cargar todas las páginas

        //Desde el archivo 002 hasta el 503 realiza este ciclo
        for(String pagina : files)
        {
            //Se genera el link del archivo cuyas etiquetas sevan a remover en la iteración
            String linkActual = "Files\\" + pagina + ".html";

            //Empieza el conteo del tiempo que se tarda en remover las etiquetas, se hace, se genera el archivo puro y se termina el conteo
            long inicioLeerPagina = System.currentTimeMillis();
            this.quitarEtiquetas(linkActual,pagina);
            long finLeerPagina = System.currentTimeMillis();

            //Se obtiene el tiempo total de duración del conteo
            long duracionLeerPagina = finLeerPagina - inicioLeerPagina;

            //Se añade al archivo de salida el nombre de la página junto con su duración en segundos
            String entradaPagina = linkActual + "\t" + duracionLeerPagina/1000.0;

            //Dependiendo de si es la primera escritura, se escrbe o se anexa en el documento de texto
            if(primeraVez)
            {
                salida.write(entradaPagina + "\n");
                primeraVez = false;
            }

            else
            {
                salida.append(entradaPagina + "\n");
            }
        }

        //Termina el proceso de borrado de etiquetas
        long finBorradoEtiquetas = System.currentTimeMillis();

        //Se obtiene la duración total del borrado de todas las etiquetas
        long duracionBorradoEtiqutas = finBorradoEtiquetas - inicioBorradoEtiquetas;

        //Se añade el tiempo al documento de salida
        salida.append("\nEl tiempo requerido para retirar todas las etiquetas de todas las paginas fue de " + duracionBorradoEtiqutas/1000.0 + " segundos");

        //Mismo proceso de arriba pero con la duración total del programa
        long finPrograma = System.currentTimeMillis();

        long duracionPrograma = finPrograma - tiempoInicio;

        salida.append("\nLa duración del programa fue de " + duracionPrograma/1000.0 + " segundos");

        //Se cierra el documento
        salida.close();
    }

    //Abre todas las páginas de la actividad
    public void abrirTodaslasPaginas() throws IOException
    {

        FileWriter salida = new FileWriter("Salidas\\Salida1.txt");       //Se crea el archivo de salida de la información

        boolean primeraVez = true;      //Variable booleana que indica si se realizará la primera escritura en el archivo de salida

        long inicioCargaPaginas = System.currentTimeMillis();   //Empieza el conteo que indica el tiempo que el sistema tarda en cargar todas las páginas

        //Desde el archivo 002 hasta el 503 realiza este ciclo
        for(String pagina : files)
        {
            //Se genera el link del archivo a leer en la iteración
            String linkActual = "Files\\" + pagina + ".html";

            //Empieza el conteo del tiempo que se tarda en abrir la pagina, se realiza la apertura y se termina el conteo
            long inicioLeerPagina = System.currentTimeMillis();
            this.abrirPagina(linkActual);
            long finLeerPagina = System.currentTimeMillis();

            //Se obtiene el tiempo total de duración del conteo
            long duracionLeerPagina = finLeerPagina - inicioLeerPagina;

            //Se añade al archivo de salida el nombre de la página junto con su duración en segundos
            String entradaPagina = linkActual + "\t" + duracionLeerPagina/1000.0;

            //Dependiendo de si es la primera escritura, se escrbe o se anexa en el documento de texto
            if(primeraVez)
            {
                salida.write(entradaPagina + "\n");
                primeraVez = false;
            }

            else
            {
                salida.append(entradaPagina + "\n");
            }

        }

        //Termina el conteo de la carga de páginas
        long finCargaPaginas = System.currentTimeMillis();

        //Se obtiene la duración total del conteo
        long duracionCargaPaginas = finCargaPaginas - inicioCargaPaginas;

        //Se añade el tiempo al documento de salida
        salida.append("\nEl tiempo requerido para cargar todas las paginas fue de fue de " + duracionCargaPaginas/1000.0 + " segundos");

        //Mismo proceso de arriba pero con la duración total del programa
        long finPrograma = System.currentTimeMillis();

        long duracionPrograma = finPrograma - tiempoInicio;

        salida.append("\nLa duración del programa fue de " + duracionPrograma/1000.0 + " segundos");

        //Se cierra el documento
        salida.close();
    }


    public void AniadirHashTableCentral(HashMap<String, Palabra> palabrasArchivoActual, String pagina)
    {
        Set<String> listaPalabras =palabrasArchivoActual.keySet();

        for (String palabra : listaPalabras)
        {
            Palabra nuevaPalabra = new Palabra();

            if(!hashTablecentral.isEmpty())
            {
                Palabra palabraExiste = hashTablecentral.get(palabra);

                if(palabraExiste == null)
                {
                    nuevaPalabra.setPalabra(palabra);
                    nuevaPalabra.setFrecuencia(palabrasArchivoActual.get(palabra).getFrecuencia());
                    nuevaPalabra.getArchivosAparece().add(pagina);
                    nuevaPalabra.getFrecuenciaArchivos().add(palabrasArchivoActual.get(palabra).getFrecuencia());
                    hashTablecentral.put(palabra, nuevaPalabra);
                }

                else
                {
                    palabraExiste.setFrecuencia(palabraExiste.getFrecuencia() + palabrasArchivoActual.get(palabra).getFrecuencia());
                    palabraExiste.setArchivos(palabraExiste.getArchivos()+1);
                    palabraExiste.getArchivosAparece().add(pagina);
                    palabraExiste.getFrecuenciaArchivos().add(palabrasArchivoActual.get(palabra).getFrecuencia());
                    hashTablecentral.put(palabra, palabraExiste);
                }
            }

            else
            {
                nuevaPalabra.setPalabra(palabra);
                hashTablecentral.put(palabra, nuevaPalabra);
            }
        }
    }

    public void AniadirAHashCentral(HashMap<String, Palabra> palabrasArchivoActual, String pagina) throws IOException
    {
        Set<String> listaPalabras =palabrasArchivoActual.keySet();

        for (String palabra : listaPalabras)
        {
            Palabra nuevaPalabra = new Palabra();

            if(!hashcentral.isEmpty())
            {
                Palabra palabraExiste = hashcentral.get(palabra);

                if(palabraExiste == null)
                {
                    nuevaPalabra.setPalabra(palabra);
                    nuevaPalabra.setFrecuencia(palabrasArchivoActual.get(palabra).getFrecuencia());
                    nuevaPalabra.getArchivosAparece().add(pagina);
                    nuevaPalabra.getFrecuenciaArchivos().add(palabrasArchivoActual.get(palabra).getFrecuencia());
                    hashcentral.put(palabra, nuevaPalabra);
                }

                else
                {
                    palabraExiste.setFrecuencia(palabraExiste.getFrecuencia() + palabrasArchivoActual.get(palabra).getFrecuencia());
                    palabraExiste.setArchivos(palabraExiste.getArchivos()+1);
                    palabraExiste.getArchivosAparece().add(pagina);
                    palabraExiste.getFrecuenciaArchivos().add(palabrasArchivoActual.get(palabra).getFrecuencia());
                    hashcentral.put(palabra, palabraExiste);
                }
            }

            else
            {
                nuevaPalabra.setPalabra(palabra);
                hashcentral.put(palabra, nuevaPalabra);
            }
        }
    }

    public HashMap<String, Palabra> contarPalabrasXArchivo(String pagina) throws IOException
    {
        HashMap<String, Palabra> coleccion = new HashMap<String, Palabra>();

        FileReader fileLectura = new FileReader("Limpios\\" + pagina + ".txt");  //Se guarda el lector del archivo en cuestión
        BufferedReader bufred = new BufferedReader(fileLectura); // BufferedReader para el análisis de linea
        StringBuilder temporal = new StringBuilder(); // En esta se almacenará poco a poco el texto del archivo
        String archivoCompleto, linea;  //Almacenará el archivo completo con el texto completo

        while ((linea = bufred.readLine()) != null)                               //Mientras no se haya llegado al final del archivo
        {
            if (linea.equals(""))    //Si la linea está vacía, se deja un salto de linea, si no lo está, se pone la linea en cuestión y se deja un salto de linea
            {
                temporal.append('\n');
            } else {
                temporal.append(linea + '\n');
            }
        }

        //Todos los caracteres que no sean letras (del abecedario inglés, acentuadas y Ñ's), números, guiones o saltos de lineas son eliminados de la string del archivo completo
        archivoCompleto = temporal.toString().replaceAll("[^a-zA-ZáéíóúÁÉÍÓÚñÑüÜ'0-9-\n ]", "");
        archivoCompleto = archivoCompleto.replaceAll("\n", " "); //Todos los saltos de linea son reemplazados por espacios
        String[] palabras = archivoCompleto.split(" "); //Se genera un arreglo de Strings en el que se separa el archivo completo a base de espacios
        List<String> arrayAlista = Arrays.asList(palabras); //El arreglo se vuelve una lista y luego pasa a un arraylist
        ArrayList<String> listapalabras = new ArrayList<>(arrayAlista), listaFiltrada = new ArrayList<String>();    //Se copia la lista de palabras encontradas a un arraylist, además se genera un arraylist en el que se colocarán las palabras del archivo qu epasen los filtros impuestos

        for (String palabra:listapalabras)
        {
            if(!palabra.isBlank())  //¿La palabra actual es un campo vacío? Rechazada
            {
                if(filtradoPalabras(palabra))   //¿La palabra no pasa el filtrado creado para palabras? Rechazada
                {
                    if(!listaFiltrada.isEmpty())    //Si la lista está vacía, se añade como la primera de la misma, caso contrario, se busca para ver que no sea una palabra ya existente, en caso de que no se encuentre, se añade a la lista
                    {
                        listaFiltrada.add(palabra);
                    }

                    else
                    {
                        listaFiltrada.add(palabra);
                    }
                }
            }
        }

        boolean encontradoEnArchivo = false;

        //Ciclo para sacar palabras del archivo
        for (String palabra : listaFiltrada)
        {
            Palabra nuevaPalabra = new Palabra();

            if(!coleccion.isEmpty())
            {
                Palabra palabraExiste = coleccion.get(palabra);

                if(palabraExiste == null)
                {
                    nuevaPalabra.setPalabra(palabra);
                    coleccion.put(palabra, nuevaPalabra);

                    if(!encontradoEnArchivo)
                    {
                        nuevaPalabra.setArchivos(nuevaPalabra.getArchivos()+1);
                        encontradoEnArchivo = true;
                    }
                }

                else
                {
                    palabraExiste.setFrecuencia(palabraExiste.getFrecuencia()+1);
                    coleccion.put(palabra, palabraExiste);
                }


            }

            else
            {
                nuevaPalabra.setPalabra(palabra);
                coleccion.put(palabra, nuevaPalabra);
            }



        }

        return coleccion;
    }

    public void combinarDiccionarios(String diccionarioActual) throws IOException
    {
        FileReader fileLectura = new FileReader(diccionarioActual);  //Se guarda el lector del archivo en cuestión
        BufferedReader bufred = new BufferedReader(fileLectura); // BufferedReader para el análisis de linea
        String linea;  //Almacenará el archivo completo con el texto completo
        ArrayList<String> listaAmeter = new ArrayList<>();

        while((linea = bufred.readLine())!= null)       //Mientras no se haya llegado al final del archivo
        {
            listaAmeter.add(linea);
        }

        for (String palabra: listaAmeter)       //Cada palabra se introduce en la lista de diccionario
        {
            palabra = palabra.toLowerCase();
            DiccionarioLista.add(palabra);
        }

    }

    //Método encargado de generar una lista de palabras irrepetibles por cada archivo de texto para colocarlo dentro del diccionario
    public void extraerPalabrasArchivo(String link, String pagina) throws IOException
    {
        /*
         * Lo que ocurre dentro de este método es lo siguiente:
         *
         *
         * 1° Abrir N archivo (obtenerlo y convertirlo en una sola String)
         * 2° Pasa filtro de solo cosas que pueda haber en palabras: [^a-zA-ZáéíóúÁÉÍÓÚñÑüÜ'0-9-]
         * 3° Quitar lineas nuevas por espacios
         * 4° Substrings del archivo completo por espacios
         * 5° Filtrar cada palabra con
         *      5.1° ¿Empieza con numeros?
         *      5.2° ¿Ya existe en la lista?
         * 6° Comparar palabras de la lista con palabras del diccionario completo
         *      6.1° ¿Ya existe en diccionario?
         *      6.2° ¿Donde va?
         * 7° Pasar a siguiente archivo
         * 8° FIN
         */

        FileWriter fileEscritura = new FileWriter("Palabras\\" + pagina + ".txt");  //Se crea el archivo de salida del texto filtrado
        FileReader fileLectura = new FileReader(link);  //Se guarda el lector del archivo en cuestión
        BufferedReader bufred = new BufferedReader(fileLectura); // BufferedReader para el análisis de linea
        StringBuilder temporal = new StringBuilder(); // En esta se almacenará poco a poco el texto del archivo
        String archivoCompleto, linea;  //Almacenará el archivo completo con el texto completo

        while((linea = bufred.readLine())!= null)                               //Mientras no se haya llegado al final del archivo
        {
            if(linea.equals(""))    //Si la linea está vacía, se deja un salto de linea, si no lo está, se pone la linea en cuestión y se deja un salto de linea
            {
                temporal.append('\n');
            }

            else
            {
                temporal.append(linea + '\n');
            }
        }

        //Todos los caracteres que no sean letras (del abecedario inglés, acentuadas y Ñ's), números, guiones o saltos de lineas son eliminados de la string del archivo completo
        archivoCompleto = temporal.toString().replaceAll("[^a-zA-ZáéíóúÁÉÍÓÚñÑüÜ'0-9-\n ]", "");
        archivoCompleto = archivoCompleto.replaceAll("\n"," "); //Todos los saltos de linea son reemplazados por espacios
        String[] palabras = archivoCompleto.split(" "); //Se genera un arreglo de Strings en el que se separa el archivo completo a base de espacios
        List<String> arrayAlista = Arrays.asList(palabras); //El arreglo se vuelve una lista y luego pasa a un arraylist
        ArrayList<String> listapalabras = new ArrayList<>(arrayAlista), listaFiltrada = new ArrayList<String>();    //Se copia la lista de palabras encontradas a un arraylist, además se genera un arraylist en el que se colocarán las palabras del archivo qu epasen los filtros impuestos

        //Ciclo para sacar palabras del archivo
        for (String palabra:listapalabras)
        {
            if(!palabra.isBlank())  //¿La palabra actual es un campo vacío? Rechazada
            {
                if(filtradoPalabras(palabra))   //¿La palabra no pasa el filtrado creado para palabras? Rechazada
                {
                    if(!listaFiltrada.isEmpty())    //Si la lista está vacía, se añade como la primera de la misma, caso contrario, se busca para ver que no sea una palabra ya existente, en caso de que no se encuentre, se añade a la lista
                    {
                        if(!palabraEnLista(palabra, listaFiltrada))
                        {
                            listaFiltrada.add(palabra);
                        }
                    }

                    else
                    {
                        listaFiltrada.add(palabra);
                    }
                }
            }
        }

        //Ciclo para meter palabras de ordenar lista filtrada

        ArrayList<String> listaFiltradaArreglada = new ArrayList<>();

        for(String palabra : listaFiltrada)
        {
            if(!listaFiltradaArreglada.isEmpty()) //Si el diccionario está vacío, se añade al diccionario, caso contrario, se busca ordenar la palabra de forma ortográfica
            {
                if(!palabraEnLista(palabra, listaFiltradaArreglada))
                {
                    ordenarPalabra(palabra, listaFiltradaArreglada);
                }
            }

            else
            {
                listaFiltradaArreglada.add(palabra);
            }
        }

        fileEscritura.write(listaAString(listaFiltradaArreglada));
        fileEscritura.close();

        //Ciclo para meter palabras de lista filtrada a diccionario
//        for(String palabra : listaFiltradaArreglada)
//        {
//            if(!DiccionarioLista.isEmpty()) //Si el diccionario está vacío, se añade al diccionario, caso contrario, se busca ordenar la palabra de forma ortográfica
//            {
//                if(!palabraEnLista(palabra, DiccionarioLista))
//                {
//                    ordenarPalabra(palabra, DiccionarioLista);
//                }
//            }
//
//            else
//            {
//                DiccionarioLista.add(palabra);
//            }
//        }
    }

    //Metodo que comprueba si una palabra en particular ya se encuentra dentro de la lista de palabras de un solo archivo a modo de evitar repeticiones
    public boolean palabraEnLista(String palabraNueva, ArrayList<String> lista)
    {
        boolean palabraEnLista = false; //Desde el principio, se asume que la palabra es nueva

        for(String palabra : lista)    //Por cada palabra de la lista ya existente se comprueba con la palabra nueva, si al compararla detecta una igualdad (no se toman en cuenta diferencias entre mayúsculas ni minúsculas) se rompe el ciclo y se cambia el valor del boolean a regresar, indicando que la palabra no ha sido aceptada
        {
            if(palabraNueva.equalsIgnoreCase(palabra))
            {
                palabraEnLista = true;
                break;
            }
        }

        return palabraEnLista;      //Se regresa el boolean
    }

    //Quita las etiquetas de la página del link correspondiente
    public void quitarEtiquetas(String link, String pagina) throws IOException
    {
        FileWriter fileEscritura = new FileWriter("Limpios\\" + pagina + ".txt");  //Se crea el archivo de salida del texto filtrado
        FileReader fileLectura = new FileReader(link);  //Se guarda el lector del archivo en cuestión
        BufferedReader bufred = new BufferedReader(fileLectura); // BufferedReader para el análisis de linea
        StringBuilder temporal = new StringBuilder(); // En esta se almacenará poco a poco el texto del archivo
        String archivoCompleto, linea;  //Almacenará el archivo completo con el texto completo

        while((linea = bufred.readLine())!= null)                               //Mientras no se haya llegado al final del archivo
        {
            if(linea.equals(""))    //Si la linea está vacía, se deja un salto de linea, si no lo está, se pone la linea en cuestión y se deja un salto de linea
            {
                temporal.append('\n');
            }

            else
            {
                temporal.append(linea + '\n');
            }
        }

        //Se borran todas las etiquetas de la string que contiene el archivo completo
        archivoCompleto = temporal.toString().replaceAll("<.*?>|<.*?(?:\n.*?)+>"," ");

        //Se reemplazan los caracteres especiales como letras acentuadas y ñ's
        archivoCompleto = reemplazarCaracteresEspeciales(archivoCompleto);

        //archivoCompleto = temporal.toString();

        //Se guarda en el archivo de escritura el texto filtrado
        fileEscritura.write(archivoCompleto);

        //Cierre de archivos
        fileEscritura.close();
        fileLectura.close();

    }

    //Reemplaza caracteres especiales de páginas HTML para que sean legibles en los documentos de texto
    public String reemplazarCaracteresEspeciales(String archivoCompleto)
    {
        //Reemplaza minusculas acentuadas y ñ's
        archivoCompleto = archivoCompleto.replaceAll("&aacute;", "á");
        archivoCompleto = archivoCompleto.replaceAll("&eacute;", "é");
        archivoCompleto = archivoCompleto.replaceAll("&iacute;", "í");
        archivoCompleto = archivoCompleto.replaceAll("&oacute;", "ó");
        archivoCompleto = archivoCompleto.replaceAll("&uacute;", "ú");
        archivoCompleto = archivoCompleto.replaceAll("&uuml;", "ü");
        archivoCompleto = archivoCompleto.replaceAll("&nacute;", "ñ");

        //Reemplaza mayusculas acentuadas y ñ's
        archivoCompleto = archivoCompleto.replaceAll("&Aacute;", "Á");
        archivoCompleto = archivoCompleto.replaceAll("&Eacute;", "É");
        archivoCompleto = archivoCompleto.replaceAll("&Iacute;", "Í");
        archivoCompleto = archivoCompleto.replaceAll("&Oacute;", "Ó");
        archivoCompleto = archivoCompleto.replaceAll("&Uacute;", "Ú");
        archivoCompleto = archivoCompleto.replaceAll("&Uuml;", "Ü");
        archivoCompleto = archivoCompleto.replaceAll("&Nacute;", "Ñ");

        //Reemplaza referencias a ASCII por nada
        archivoCompleto = archivoCompleto.replaceAll("&#[0-9]+;", "");
        return archivoCompleto;
    }

    //Abre con el buscador predeterminado la página con el link que recibe de parametro
    public void abrirPagina(String link) throws IOException
    {
        File file = new File (link);
        Desktop.getDesktop().browse(file.toURI());
    }

    //Se usa para crear la String que cambie el numero de la iteración por el nombre real de la página de HTML
    public String numeroAString(int numero)
    {
        String regresar="";

        if(numero < 10)
        {
            regresar = "00";
            regresar += numero;
        }

        else
        {
            if(numero < 100)
            {
                regresar = "0";
                regresar += numero;
            }

            else
            {
                regresar = String.valueOf(numero);
            }
        }

        return regresar;
    }

    //(MÉTODO RECUPERADO Y MODIFICADO DE UNA ENTREGA DE UNA MATERIA ANTERIOR) Inserta la palabra en la lista del diccionario completo, está basado en el método de busqueda binaria
    public void ordenarPalabra(String palabraNueva, ArrayList<String> lista)
    {
        boolean colocada = false;   //Evalua si la palabra ya encontró su sitio
        int limInf = 0, limSup = lista.size()-1;  //Se generan los limites inferiores y superiores, empezando siempre con el primer valor de la lista y con el último asignados respectivamente
        int puntoMedio = 0;     //El punto medio sobre el cual partir

        while(!colocada)    //Mientras el programa no haya notificado que la palabra no fue insertada en la lista
        {
            puntoMedio = (limInf + limSup)/2;   //Se define el punto medio

            String aComparar = lista.get(puntoMedio); //Se obtiene el elemento del punto medio en base a los límites,
            int decision = palabraNueva.compareToIgnoreCase(aComparar);   //La palabra del punto medio se compara con la palabra a meter para comprobar si la nueva va antes o despues que la del punto medio en un sentido alfabético

            if(puntoMedio-1 < limInf || puntoMedio+1 > limSup)  //En caso de que el punto medio no se encuentre entre 2 valores (Ya sea el borde derecho o izquiero o incluso, el único valor en la lista. Esto es para casos en los que la lista se ha reducido tanto que ya solo queda un valor a evaluar)
            {
                if(puntoMedio-1 < limInf && puntoMedio+1 > limSup)  //Si el punto medio no esta rodeado de nada
                {
                    if(decision < 0)    //Si la palabra nueva va antes del punto medio, se coloca detrás sin preguntar mas
                    {
                        //Un if hecho para manejar el comportamiento del add dependiendo del valor del punto medio
                        if(puntoMedio == 0)
                        {
                            lista.add(0, palabraNueva);
                        }

                        else
                        {
                            lista.add(puntoMedio, palabraNueva);
                        }

                        colocada = true;    //La palabra se ha colocado, de modo que el ciclo no vuelve a repetirse
                    }

                    else    //Si la palabra nueva va despues del punto medio, se coloca delante sin preguntar mas
                    {
                        //Un if hecho para manejar el comportamiento del add dependiendo del valor del punto medio
                        if(puntoMedio == lista.size()-1)
                        {
                            lista.add(palabraNueva);
                        }

                        else
                        {
                            lista.add(puntoMedio+1, palabraNueva);
                        }

                        colocada = true;//La palabra se ha colocado, de modo que el ciclo no vuelve a repetirse
                    }
                }

                else    //Si el punto medio posee mas valores pero solo de un lado
                {
                    if(puntoMedio-1 < limInf)   //Si el punto medio es el extremo izquierdo de acuerdo a los límites
                    {
                        if(decision < 0)    //Debido a que no hay nada detrás del punto medio, si la palabra nueva va antes, se coloca sin preguntar
                        {
                            //Procedimiento de inserción visto arriba
                            if(puntoMedio == 0)
                            {
                                lista.add(0, palabraNueva);
                            }

                            else
                            {
                                lista.add(puntoMedio, palabraNueva);
                            }

                            colocada = true;
                        }

                        else    //Va despues del punto medio, sin embargo, se compara también la palabra que le sigue. Puede que encontremos el lugar final de la nueva palabra
                        {
                            aComparar = lista.get(puntoMedio+1); //Se compara la nueva palabra con la palabra siguiente al punto medio

                            //¿Va despues del punto medio, pero antes de la palabra siguiente? Hemos encontrado su lugar
                            if(palabraNueva.compareToIgnoreCase(aComparar) < 0)
                            {
                                lista.add(puntoMedio+1, palabraNueva);
                                colocada = true;
                            }

                            //¿Va despues del punto medio y tambien despues de la palabra siguiente? Repitamos de nuevo con un nuevo límite inferior (y por obviedad, un nuevo punto medio)
                            else
                            {
                                limInf = puntoMedio+1;
                            }
                        }
                    }

                    else    //Si el punto medio es el extremo derecho de acuerdo a los límites
                    {
                        if(decision < 0)    //Va antes del punto medio, sin embargo, se compara también la palabra que le antecede. Puede que encontremos el lugar final de la nueva palabra
                        {
                            aComparar = lista.get(puntoMedio-1); //Se compara la nueva palabra con la palabra siguiente al punto medio

                            //¿Va antes del punto medio, pero despues de la palabra anterior? Hemos encontrado su lugar
                            if(palabraNueva.compareToIgnoreCase(aComparar) > 0)
                            {
                                lista.add(puntoMedio, palabraNueva);
                                colocada = true;
                            }

                            //¿Va antes del punto medio y tambien antes de la palabra anterior? Repitamos de nuevo con un nuevo límite superior (y por obviedad, un nuevo punto medio)
                            else
                            {
                                limSup = puntoMedio-1;
                            }
                        }

                        else    //Debido a que no hay nada después del punto medio, si la palabra nueva va despues, se coloca sin preguntar
                        {
                            //Procedimiento de inserción visto arriba
                            if(puntoMedio == lista.size()-1)
                            {
                                lista.add(palabraNueva);
                            }

                            else
                            {
                                lista.add(puntoMedio+1, palabraNueva);
                            }

                            colocada = true;
                        }
                    }
                }
            }

            else    //El punto medio se encuentra entre 2 valores si o si
            {
                if(decision < 0)    //Si la palabra nueva va antes del punto medio
                {
                    //La palabra nueva se compara con la palabra anterior al punto medio
                    aComparar = lista.get(puntoMedio-1);
                    decision = palabraNueva.compareToIgnoreCase(aComparar);

                    //¿Va antes del punto medio y tambien antes de la palabra anterior? Repitamos de nuevo con un nuevo límite superior (y por obviedad, un nuevo punto medio)
                    if(decision < 0)
                    {
                        limSup = puntoMedio-1;
                    }

                    //¿Va antes del punto medio, pero despues de la palabra anterior? Hemos encontrado su lugar
                    if(decision > 0)
                    {
                        lista.add(puntoMedio, palabraNueva);
                        colocada = true;
                        decision = 0;
                    }
                }

                else    //Si la palabra nueva va despues del punto medio
                {
                    //La palabra nueva se compara con la palabra siguiente al punto medio
                    aComparar = lista.get(puntoMedio+1);
                    decision = palabraNueva.compareToIgnoreCase(aComparar);

                    //¿Va despues del punto medio, pero antes de la palabra siguiente? Hemos encontrado su lugar
                    if(decision < 0)
                    {
                        lista.add(puntoMedio+1, palabraNueva);
                        colocada = true;
                    }

                    //¿Va despues del punto medio y tambien despues de la palabra siguiente? Repitamos de nuevo con un nuevo límite inferior (y por obviedad, un nuevo punto medio)
                    if(decision > 0)
                    {
                        limInf = puntoMedio+1;
                    }
                }
            }
        }
    }

    //Transforma todos los valores de una ArrayList de Strings a una sola cadena, los valores se encuentran separados por un caracter de linea nueva
    String listaAString(ArrayList<String> lista)
    {
        String listaStrings="";

        for(String palabra : lista)
        {
            listaStrings += palabra + "\n";
        }

        return listaStrings;
    }

    //Se encarga de evaluar una palabra en base a una serie de filtros
    public boolean filtradoPalabras(String palabra)
    {
        boolean pasaFiltro = true;      //Boolean con el resultado de si la palabra es válida o no

        /*
         * Para quw una palabra sea aceptada, debe cumplir con los siguientes filtros
         *
         * 1° Tiene letras, por lo menos una
         * 2° No tiene numeros
         * 3° Empieza con una letra (Abecedario Inglés, letras acentuadas y ñ's)
         * todo: ¿Hay que añadir mas filtros?
         */

        //Las reglas y los resultados que deben tener para ser aceptados como una palabra válida para el diccionario
        String[] regexes = {"[a-zA-Z]", "[0-9]", "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ]"};
        boolean[] resultadosEsperados = {true, false, true};

        //Se evalua la palabra por cada regex. En caso de haber alguna diferencia en cualquiera de los resultados esperados y los resultados reales, el boolean pasa a false y se termina la evaluación, indicando que la palabra ha sido rechazada
        for(int i = 0; i < regexes.length; i++)
        {
            Pattern strPat = Pattern.compile(regexes[i]);
            Matcher m = strPat.matcher(palabra);

            if(m.find() != resultadosEsperados[i])
            {
                pasaFiltro = false;
                break;
            }
        }

        return  pasaFiltro;     //Se devuelve el boolean
    }
}
