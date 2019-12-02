import java.util.Random;
import java.util.ArrayList;

class Area
{
    static final char SEM_LE  = 'S';
    static final char IMP_01  = '1';
    static final char IMP_02  = '2';
    static final int  ACESSOS = 100;
    char IMPLEMENTACAO;

    private Recurso  buffer[];
    private Random   alea = new Random();
    ArrayList<Estrutura> log;
    boolean escritores = false;
    int     leitores   = 0;

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

    int NextPos()
    {
        return alea.nextInt(buffer.length);
    }

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

    synchronized void Le(String name) throws Exception
    {
        LogC(name);

        while (!PodeLer()) 
        {
            try 
            {
                wait();
            } catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
        }
        leitores++;

        LeDesync();
    }

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

    synchronized void PararLer(String name)
    {
        leitores--;
        LogS(name);
        notifyAll();
    }

    synchronized void PararEscrever(String name)
    {
        escritores = false;
        LogS(name);
        notifyAll();
    }

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