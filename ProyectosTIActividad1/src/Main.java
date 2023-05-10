import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        ManejadorArchivos manejadorArchivos = new ManejadorArchivos();      //Se crea objeto de tipo manejadorArchivos
        manejadorArchivos.tiempoInicio = System.currentTimeMillis();
//        manejadorArchivos.abrirTodaslasPaginas();
//        manejadorArchivos.removerTodasLasEtiquetas();
//        manejadorArchivos.generarDiccionarios();
//        manejadorArchivos.generarDiccionarioCentral();
//        manejadorArchivos.tokenizarArchivosActividad5();
//        manejadorArchivos.tokenizarTodosArchivos();
        manejadorArchivos.generarPosting();
        manejadorArchivos.almacenamientoHashTable();
        manejadorArchivos.tokenizarTodosArchivosStopList();
        manejadorArchivos.pesarTokens();
        int i=1;
    }
}