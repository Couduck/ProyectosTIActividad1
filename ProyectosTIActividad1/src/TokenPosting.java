public class TokenPosting   //Objeto que representa un registro dentro del archivo PostingConIDs
{
    private String archivo;

    private String numRepeticion;

    private String peso;

    public TokenPosting(String archivo, String numRepeticion, String peso)
    {
        this.archivo = archivo;
        this.numRepeticion = numRepeticion;
        this.peso = peso;
    }

    public String getArchivo() {
        return archivo;
    }

    public String getNumRepeticion() {
        return numRepeticion;
    }

    public String getPeso() {
        return peso;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }
}
