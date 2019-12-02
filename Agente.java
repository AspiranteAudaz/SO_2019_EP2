import java.util.ArrayList;

class Agente extends Thread
{

    static final boolean ESCRITOR     = true;
    static final boolean LEITOR       = false;
    static final String  ESCRITOR_STR = "ESCRITOR";
    static final String  LEITOR_STR   = "LEITOR";
    static final String  DADO_ESCREVE = "MODIFICADO";
    static final int     SLEEP_TIME   = 1;
    static final int     ACESSOS      = 100;

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

    void Escreve(Recurso rec, int pos) throws Exception
    {
        if(!tipo)
            throw new Exception("Este agente nao e um escritor.");

        try
        {
            for(int i = 0; i < ACESSOS; i++)
                area.Escreve(rec, pos);
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }

    void Le(int pos) throws Exception
    {
        if(tipo)
            throw new Exception("Este agente nao e um leitor.");

        try
        {   
            for(int i = 0; i < ACESSOS; i++)
                area.Le(pos);
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }

    void LogL()
    {
        long   ltime = System.currentTimeMillis();
        String time  = ltime + "";
        //area.log.add(new Estrutura(name + " LOCK [" + time.substring(time.length() - 6, time.length()) + "]", ltime));
    }

    void LogC()
    {
        long   ltime = System.currentTimeMillis();
        String time  = ltime + "";
        //area.log.add(new Estrutura(name + " Chegou [" + time.substring(time.length() - 6, time.length()) + "]", ltime));
    }

    void LogS()
    {
        long   ltime = System.currentTimeMillis();
        String time  = ltime + "";
        //area.log.add(new Estrutura(name + " Saida [" + time.substring(time.length() - 6, time.length()) + "]", ltime));
    }

    //Implementacao sem LE
    public void SemLE()
    {
        LogL();
        synchronized(area)
        {
            if(tipo)
            {
                LogC();
                try
                {
                    Escreve(rec, area.NextPos());
                    try{Thread.sleep(SLEEP_TIME);}catch(Exception ex){System.out.println(ex.toString());};
                }
                catch(Exception ex)
                {
                    System.out.println(ex.toString());
                }
                LogS();
            }
            else
            {
                LogC();
                try
                {
                    Le(area.NextPos());
                    try{Thread.sleep(SLEEP_TIME);}catch(Exception ex){System.out.println(ex.toString());};
                }
                catch(Exception ex)
                {
                    System.out.println(ex.toString());
                }
                LogS();
            }
        }
    }

    public void ComLE_I()
    {
        if(tipo)
        {
            LogL();
            synchronized(area)
            {
                LogC();
                try
                {
                    Escreve(rec, area.NextPos());
                    try{Thread.sleep(SLEEP_TIME);}catch(Exception ex){System.out.println(ex.toString());};
                }
                catch(Exception ex)
                {
                    System.out.println(ex.toString());
                }
                LogS();
            }
        }
        else
        {
            if(area.leitores == 0)
            {
                LogL();
                synchronized(area)
                {
                    area.leitores++;
                    LogC();
                    try
                    {
                        Le(area.NextPos());
                        try{Thread.sleep(SLEEP_TIME);}catch(Exception ex){System.out.println(ex.toString());};
                    }
                    catch(Exception ex)
                    {
                        System.out.println(ex.toString());
                    }
                    LogS();
                    area.leitores--;
                }
            }
            else
            {
                area.leitores++;
                LogC();
                try
                {
                    Le(area.NextPos());
                    try{Thread.sleep(SLEEP_TIME);}catch(Exception ex){System.out.println(ex.toString());};
                }
                catch(Exception ex)
                {
                    System.out.println(ex.toString());
                }
                LogS();
                area.leitores--;
            }
        }
    }

    public void run()
    {
        //SemLE();
        ComLE_I();
        //System.out.println(name + " terminou.");
    }
}