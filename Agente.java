class Agente
{

    static final boolean ESCRITOR = true;
    static final boolean LEITOR   = false;

    final boolean tipo;

    Agente(boolean tipo)
    {
        this.tipo = tipo;
    }

    String TipoString()
    {
        if(tipo)
            return "Leitor";
        
        return "Escritor";
    }
    
    boolean Tipo()
    {
        return tipo;
    }

    void Escreve(Area area, Recurso rec, int pos) throws Exception
    {
        if(tipo)
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

    Recurso Le(Area area, int pos) throws Exception
    {
        if(!tipo)
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
}