import java.io.FileWriter;
import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        ManejadorArchivos manejadorArchivos = new ManejadorArchivos();      //Se crea objeto de tipo manejadorArchivos
        //manejadorArchivos.abrirTodaslasPaginas();
        //manejadorArchivos.removerTodasLasEtiquetas();
        manejadorArchivos.generarDiccionario();
    }
}