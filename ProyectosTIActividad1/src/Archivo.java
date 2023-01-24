import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Archivo
{
    public void abrirPagina(String link) throws IOException {
        File file = new File (link);
        Desktop.getDesktop().browse(file.toURI());
    }

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
}
