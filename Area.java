import java.util.Random;
import java.util.ArrayList;

class Area
{
    //Flags de implementacao
    static final char SEM_LE  = 'S';
    static final char IMP_01  = '1';
    static final char IMP_02  = '2';
    static final int  ACESSOS = 100;
    char IMPLEMENTACAO;

    //Buffer de dados
    private Recurso  buffer[];

    //Gerador de num aleatorios
    private Random   alea = new Random();

    //Lista de estruturas do log (so para debug)
    ArrayList<Estrutura> log;

    //Variaveis do algoritmo
    boolean escritores = false;
    int     leitores   = 0;

    //Logs auxiliares
    void Log(String val)
    {
        System.out.println(val);
    }

    void LogL(String name)
    {
        long   ltime = System.currentTimeMillis();
        String time  = ltime + "";
        log.add(new Estrutura(name + " LOCK [" + time.substring(time.length() - 6, time.length()) + "]", ltime));
        //Log(name + " LOCK [" + time.substring(time.length() - 6, time.length()) + "]");
    }

    void LogC(String name)
    {
        long   ltime = System.currentTimeMillis();
        String time  = ltime + "";
        log.add(new Estrutura(name + " Chegou [" + time.substring(time.length() - 6, time.length()) + "]", ltime));
        //Log(name + " CHEGOU [" + time.substring(time.length() - 6, time.length()) + "]");
    }

    void LogS(String name)
    {
        long   ltime = System.currentTimeMillis();
        String time  = ltime + "";
        log.add(new Estrutura(name + " Saida [" + time.substring(time.length() - 6, time.length()) + "]", ltime));
        //Log(name + " Saida [" + time.substring(time.length() - 6, time.length()) + "]");
    }

    Area(int num_recursos, char implementacao)
    {
        this.IMPLEMENTACAO = implementacao;
        buffer = new Recurso[num_recursos];
    }

    Area(Recurso buffer[], char implementacao)
    {
        this.IMPLEMENTACAO = implementacao;
        this.buffer = buffer;
    }

    //Gera posicao aleatoria do buffer
    int NextPos()
    {
        return alea.nextInt(buffer.length);
    }

    //Condicao (true) se no momento um leitor pode ler a area
    boolean PodeLer()
    {
        if(IMPLEMENTACAO == '1')
        {
            return (!escritores);
        }
        else if(IMPLEMENTACAO == '2')
        {
            return (!escritores);
        }
        else
        {
            //SEM LE
            return true;
        }
    }

    //Condicao (true) se no momento um escritor pode escrever na area
    boolean PodeEscrever()
    {
        if(IMPLEMENTACAO == '1')
        {
            return ((leitores == 0) && (!escritores));
        }
        else if(IMPLEMENTACAO == '2')
        {
            return (!escritores);
        }
        else
        {
            //SEM LE
            return true;
        }
    }

    //Le a area normalmente ACESSO vezes
    void LeDesync()
    {
        Recurso rec;
        try
        {   
            for(int i = 0; i < ACESSOS; i++)
                rec = buffer[NextPos()];
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }

    //Escreve na area normalmente ACESSO vezes
    void EscreveDesync(Recurso rec)
    {   
        try
        {
            for(int i = 0; i < ACESSOS; i++)
                buffer[NextPos()] = rec;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }

    //Le a area ACESSO vezes de forma sincronizada
    synchronized void Le(String name) throws Exception
    {
        LogC(name);

        while (!PodeLer()) 
        {
            try 
            {
                //Dorme
                wait();
            } catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
        }
        leitores++;

        LeDesync();
    }
     
    //Escreve na area ACESSO vezes de forma sincronizada
    synchronized void Escreve(Recurso rec, String name) throws Exception
    {
        LogC(name);

        while (!PodeEscrever()) 
        {
            try 
            {
                wait();
            } catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
        }
        
        escritores = true;
    
        EscreveDesync(rec);
    }

    //Saida da area critica (leitor) e acorda as outras threads
    synchronized void PararLer(String name)
    {
        leitores--;
        LogS(name);
        notifyAll();
    }

    //Saida da area critica (escritor) e acorda as outras threads
    synchronized void PararEscrever(String name)
    {
        escritores = false;
        LogS(name);
        notifyAll();
    }

    //Metodo auxiliar de debug para ordenar a saida linearmente pelo tempo
    void ordenaEPrinta()
    {
        ArrayList<Estrutura> array = log;
        int p = 0;
        while (p < array.size())
        {
            for (int i = 0; i < array.size()-1-p; i++)
            {
                if (array.get(i).time > array.get(i+1).time)
                {
                    Estrutura aux       = new Estrutura(array.get(i+1).line, array.get(i+1).time);
                    array.get(i+1).line = array.get(i).line;
                    array.get(i+1).time = array.get(i).time;
                    array.get(i).line   = aux.line;
                    array.get(i).time   = aux.time;
                }
            }
            p++;
        }

        long time = 0;
        for (int i = 0; i < array.size(); i++)
        {
            if(array.get(i).time < time)
            {
                System.out.println("ERROR");
            }
            System.out.println(array.get(i).line);

            time = array.get(i).time;
        }
    }
}