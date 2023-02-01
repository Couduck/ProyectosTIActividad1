import java.awt.*;
import java.io.*;

public class ManejadorArchivos      //Clase para manejar los archivos de HTML
{
    //Quita todas als etiquetas de la actividaad
    public void removerTodasLasEtiquetas() throws IOException
    {
        long inicioPrograma = System.currentTimeMillis();

        FileWriter salida = new FileWriter("Salidas\\Salida2.txt");       //Se crea el archivo de salida de la información

        boolean primeraVez = true;      //Variable booleana que indica si se realizará la primera escritura en el archivo de salida

        long inicioBorradoEtiquetas = System.currentTimeMillis();   //Empieza el conteo que indica el tiempo que el sistema tarda en cargar todas las páginas

        //Desde el archivo 002 hasta el 503 realiza este ciclo
        for(int i = 2; i < 504; i++)
        {
            //Se genera el link del archivo cuyas etiquetas sevan a remover en la iteración
            String linkActual = "Files\\" + this.numeroAString(i) + ".html";

            //Empieza el conteo del tiempo que se tarda en remover las etiquetas, se hace, se genera el archivo puro y se termina el conteo
            long inicioLeerPagina = System.currentTimeMillis();
            this.quitarEtiquetas(linkActual,i);
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

        long duracionPrograma = finPrograma - inicioPrograma;

        salida.append("\nLa duración del programa fue de " + duracionPrograma/1000.0 + " segundos");

        //Se cierra el documento
        salida.close();
    }

    //Abre todas las páginas de la actividad
    public void abrirTodaslasPaginas() throws IOException
    {
        //Empieza el conteo de los milisegundos que pasan en el programa completo
        long inicioPrograma = System.currentTimeMillis();

        FileWriter salida = new FileWriter("Salidas\\Salida1.txt");       //Se crea el archivo de salida de la información

        boolean primeraVez = true;      //Variable booleana que indica si se realizará la primera escritura en el archivo de salida

        long inicioCargaPaginas = System.currentTimeMillis();   //Empieza el conteo que indica el tiempo que el sistema tarda en cargar todas las páginas

        //Desde el archivo 002 hasta el 503 realiza este ciclo
        for(int i = 2; i < 504; i++)
        {
            //Se genera el link del archivo a leer en la iteración
            String linkActual = "Files\\" + this.numeroAString(i) + ".html";

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

        long duracionPrograma = finPrograma - inicioPrograma;

        salida.append("\nLa duración del programa fue de " + duracionPrograma/1000.0 + " segundos");

        //Se cierra el documento
        salida.close();
    }

    //Quita las etiquetas de la página del link correspondiente
    public void quitarEtiquetas(String link, int num) throws IOException {
        FileWriter fileEscritura = new FileWriter("Limpios\\" + this.numeroAString(num) + ".txt");  //Se crea el archivo de salida del texto filtrado
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
        archivoCompleto = temporal.toString().replaceAll("<.*?>|<.*?(?:\n.*?)+>","");

        //Se reemplazan los caracteres especiales como letras acentuadas y ñ's
        archivoCompleto = reemplazarCaracteresEspeciales(archivoCompleto);

        //archivoCompleto = temporal.toString();

        //Se guarda en el archivo de escritura el texto filtrado
        fileEscritura.write(archivoCompleto);

        //Cierre de archivos
        fileEscritura.close();
        fileLectura.close();

    }

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

    public String numeroAString(int numero)     //Se usa para crear la String que cambie el numero de la iteración por el nombre real de la página de HTML
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
}
