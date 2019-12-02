class Agente extends Thread
{
    static final boolean ESCRITOR     = true;
    static final boolean LEITOR       = false;
    static final String  ESCRITOR_STR = "ESCRITOR";
    static final String  LEITOR_STR   = "LEITOR";

    private final boolean tipo;
    private final String  name;
    private final int     id;

    Area area;

    Agente(Area area, boolean tipo, String tipo_str, int num)
    {
        this.area = area;
        this.tipo = tipo;
        this.id   = num;
        this.name = tipo_str + "_" + num;
    }

    String TipoString()
    {
        if(tipo)
            return ESCRITOR_STR;
        
        return LEITOR_STR;
    }
    
    boolean Tipo()
    {
        return tipo;
    }

    void Escreve(Recurso rec, int pos) throws Exception
    {
        if(!tipo)
            throw new Exception("Este agente nao e um escritor.");

        try
        {
            area.Escreve(rec, pos);
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }

    Recurso Le(int pos) throws Exception
    {
        if(tipo)
            throw new Exception("Este agente nao e um leitor.");

        try
        {   
            return area.Le(pos);
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    public void run()
    {
        //System.out.println(name + " terminou.");
    }
}