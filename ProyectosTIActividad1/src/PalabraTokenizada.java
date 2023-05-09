public class PalabraTokenizada
{
    String palabra;

    int repeticiones;

    int archivosAparece;

    int posicionPosting;

    public PalabraTokenizada(String palabra, int repeticiones, int archivosAparece, int posicionPosting)
    {
        this.palabra = palabra;
        this.repeticiones = repeticiones;
        this.archivosAparece = archivosAparece;
        this.posicionPosting = posicionPosting;
    }

    public String getPalabra() {
        return palabra;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public int getArchivosAparece() {
        return archivosAparece;
    }

    public int getPosicionPosting() {
        return posicionPosting;
    }
}
