import java.util.ArrayList;

class Agente extends Thread
{
    //flags de escritor/leitor
    static final boolean ESCRITOR     = true;
    static final boolean LEITOR       = false;

    //descricao de escritor/leitor
    static final String  ESCRITOR_STR = "ESCRITOR";
    static final String  LEITOR_STR   = "LEITOR";

    //o que escrever
    static final String  DADO_ESCREVE = "MODIFICADO";

    //quantos ms dormir
    static final int     SLEEP_TIME   = 1;

    ArrayList<Estrutura> log;

    //dados de cada agente
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

    //retorna o tipo string do agente
    String TipoString()
    {
        if(tipo)
            return ESCRITOR_STR;
        
        return LEITOR_STR;
    }
    
    //retorna o tipo do arquivo
    boolean Tipo()
    {
        return tipo;
    }

    //Logs auxiliares
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
        //Execucao com implementacao Sem leitores e escritores
        if(area.IMPLEMENTACAO == area.SEM_LE)
        {
            SemLE();
            return;
        }

        //Execucao com implementacoes 1 (readers) e 2 (writers)
        if(tipo)
        {
            try
            {
                //Escreve na base
                area.Escreve(rec, name);
                //Dorme (validacao especificada)
                try{Thread.sleep(SLEEP_TIME);}catch(Exception ex){System.out.println(ex.toString());};
                //Sai da base
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
                //Escreve na base
                area.Le(name);
                //Dorme (validacao especificada)
                try{Thread.sleep(SLEEP_TIME);}catch(Exception ex){System.out.println(ex.toString());};
                //Sai da base
                area.PararLer(name);
            }
            catch(Exception ex)
            {
                System.out.println(ex.toString());
            }
        }
    }
}