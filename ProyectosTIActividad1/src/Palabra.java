public class Palabra
{
    String palabra;
    int frecuencia;

    int archivos;

    public Palabra()
    {
        palabra = "";
        frecuencia = 1;
        archivos = 1;
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
