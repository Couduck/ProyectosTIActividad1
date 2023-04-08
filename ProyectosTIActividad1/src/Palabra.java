public class Palabra
{
    String palabra;
    int frecuencia;

    public Palabra()
    {
        palabra = "";
        frecuencia = 1;
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
