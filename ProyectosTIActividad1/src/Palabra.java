import java.util.ArrayList;
import java.util.List;

public class Palabra
{
    String palabra;
    int frecuencia;

    int archivos;

    List<String> archivosAparece;

    List<Integer> frecuenciaArchivos;

    int posicionPosting;

    public Palabra()
    {
        palabra = "";
        frecuencia = 1;
        archivos = 1;
        archivosAparece = new ArrayList<>();
        frecuenciaArchivos = new ArrayList<>();
    }

    public List<String> getArchivosAparece() {
        return archivosAparece;
    }

    public void setArchivosAparece(List<String> archivosAparece) {
        this.archivosAparece = archivosAparece;
    }

    public List<Integer> getFrecuenciaArchivos() {
        return frecuenciaArchivos;
    }

    public void setFrecuenciaArchivos(List<Integer> frecuenciaArchivos) {
        this.frecuenciaArchivos = frecuenciaArchivos;
    }

    public int getArchivos() {
        return archivos;
    }

    public void setArchivos(int archivos) {
        this.archivos = archivos;
    }

    public String getPalabra() {
        return palabra;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }
}
