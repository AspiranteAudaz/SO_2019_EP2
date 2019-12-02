import java.util.ArrayList;

class Agente extends Thread
{
    static final boolean ESCRITOR     = true;
    static final boolean LEITOR       = false;
    static final String  ESCRITOR_STR = "ESCRITOR";
    static final String  LEITOR_STR   = "LEITOR";
    static final String  DADO_ESCREVE = "MODIFICADO";
    static final int     SLEEP_TIME   = 1;

    ArrayList<Estrutura> log;
    private final boolean tipo;
    private final String  name;
    private final int     id;
    private final Recurso rec;

    Area area;

    Agente(Area area, boolean tipo, String tipo_str, int num)
    {
        this.area = area;
        this.tipo = tipo;
        this.id   = num;
        this.name = tipo_str + "_" + num;
        rec = new Recurso(DADO_ESCREVE);
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

    void LogL()
    {
        area.LogS(name);
    }

    void LogC()
    {
        area.LogS(name);
    }

    void LogS()
    {
        area.LogS(name);
    }

    //Implementacao sem LE
    public void SemLE()
    {
        //LogL();
        synchronized(area)
        {
            if(tipo)
            {
                //LogC();
                try
                {
                    area.EscreveDesync(rec);
                    try{Thread.sleep(SLEEP_TIME);}catch(Exception ex){System.out.println(ex.toString());};
                }
                catch(Exception ex)
                {
                    System.out.println(ex.toString());
                }
                //LogS();
            }
            else
            {
                //LogC();
                try
                {
                    area.LeDesync();
                    try{Thread.sleep(SLEEP_TIME);}catch(Exception ex){System.out.println(ex.toString());};
                }
                catch(Exception ex)
                {
                    System.out.println(ex.toString());
                }
                //LogS();
            }
        }
    }

    public void run()
    {
        if(area.IMPLEMENTACAO == area.SEM_LE)
        {
            SemLE();
            return;
        }

        if(tipo)
        {
            try
            {
                area.Escreve(rec, name);
                try{Thread.sleep(SLEEP_TIME);}catch(Exception ex){System.out.println(ex.toString());};
                area.PararEscrever(name);
            }
            catch(Exception ex)
            {
                System.out.println(ex.toString());
            }
        }
        else
        {
            try
            {
                area.Le(name);
                try{Thread.sleep(SLEEP_TIME);}catch(Exception ex){System.out.println(ex.toString());};
                area.PararLer(name);
            }
            catch(Exception ex)
            {
                System.out.println(ex.toString());
            }
        }
    }
}