import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ManejadorArchivos      //Clase para manejar los archivos de HTML
{
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
