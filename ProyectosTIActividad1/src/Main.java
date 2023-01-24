import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Archivo archivo = new Archivo();

        for(byte i = 2; i < 7; i++)
        {
            archivo.abrirPagina("Files\\" + archivo.numeroAString(i) + ".html");
        }
    }
}