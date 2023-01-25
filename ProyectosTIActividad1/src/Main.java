import java.io.FileWriter;
import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        long inicioPrograma = System.currentTimeMillis();
        FileWriter salida = new FileWriter("Salida.txt");
        Archivo archivo = new Archivo();
        boolean primeraVez = true;

        long inicioCargaPaginas = System.currentTimeMillis();

        for(int i = 2; i < 504; i++)
        {
            String linkActual = "Files\\" + archivo.numeroAString(i) + ".html";

            long inicioLeerPagina = System.currentTimeMillis();
            archivo.abrirPagina(linkActual);
            long finLeerPagina = System.currentTimeMillis();

            long duracionLeerPagina = finLeerPagina - inicioLeerPagina;

            String entradaPagina = linkActual + "\t" + duracionLeerPagina/1000.0;

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

        long finCargaPaginas = System.currentTimeMillis();

        long duracionCargaPaginas = finCargaPaginas - inicioCargaPaginas;

        salida.append("\nEl tiempo requerido para cargar todas las paginas fue de fue de " + duracionCargaPaginas/1000.0 + " segundos");

        long finPrograma = System.currentTimeMillis();

        long duracionPrograma = finPrograma - inicioPrograma;

        salida.append("\nLa duración del programa fue de " + duracionPrograma/1000.0 + " segundos");

        salida.close();
    }
}