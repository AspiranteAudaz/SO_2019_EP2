import java.util.Random;
import java.util.ArrayList;

class Area
{
    private Recurso  buffer[];
    private Random   alea = new Random();
    int leitores = 0;
    int escritor = 0;
    ArrayList<Estrutura> log;

    Area(int num_recursos)
    {
        buffer = new Recurso[num_recursos];
    }

    Area(Recurso buffer[])
    {
        this.buffer = buffer;
    }

    int NextPos()
    {
        return alea.nextInt(buffer.length);
    }

    Recurso Le(int pos) throws Exception
    {
        if(pos >= buffer.length)
            throw new Exception("Pos nao cai dentro do buffer.");

        return buffer[pos];
    }

    void Escreve(Recurso rec, int pos) throws Exception
    {
        if(pos >= buffer.length)
            throw new Exception("Pos nao cai dentro do buffer.");

        buffer[pos] = rec;
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