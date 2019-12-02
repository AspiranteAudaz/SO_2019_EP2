import java.util.Vector;
import java.util.Random;
import java.util.ArrayList;

class Simula
{
    private ES es       = new ES();
    private Random alea = new Random();
    
    private Agente agentes[];
    private Area   critica;

    private String entrada;

    Simula(String entrada, char implementacao)
    {
        //Le os recursos do arquivo de entrada
        PegaRecursos(entrada, implementacao);
    }

    //Carrega a area critica com o arquivo
    private void PegaRecursos(String entrada, char implementacao)
    {
        //Le as linhas do arquivo
        Vector<String> linhas = es.ParsaPalavras(es.CarregaArquivo(entrada));
        
        // DEBUG
        //DebugaLinhas(linhas);

        //Gera os objetos de recurso
        Recurso[] recursos = new Recurso[linhas.size()];
        for(int i = 0; i < recursos.length; i++)
            recursos[i] = new Recurso(linhas.remove(0));

        critica = new Area(recursos, implementacao);
    }

    //Popula vetor agentes
    private void GeraAgentes(int num_agentes, int num_leitores)
    {
        int chance         = num_leitores;
        int num_escritores = num_agentes - num_leitores;

        agentes = new Agente[num_agentes];

        for(int i = 0; i < num_agentes; i++)
        {
            if((num_leitores > 0) && (alea.nextInt(num_agentes) <= chance))
            {
                agentes[i] = new Agente(critica, Agente.LEITOR, Agente.LEITOR_STR, i); 
                num_leitores--;
            }
            else if(num_escritores > 0)
            {           
                agentes[i] = new Agente(critica, Agente.ESCRITOR, Agente.ESCRITOR_STR, i);
                num_escritores--;
            }
            else
                i--;
        }
    }

    void Prepara(int num_agentes, int num_leitores)
    {
        //Gera os agentes
        GeraAgentes(num_agentes, num_leitores);
        
        //DEBUG
        //DebugNumeroAgentes();
    }

    long Roda()
    {
        long era_inicial = System.currentTimeMillis();

        critica.log = new ArrayList<Estrutura>();

        for(int i = 0; i < agentes.length; i++)
            agentes[i].start();
        
        try
        {
            for(int i = 0; i < agentes.length; i++)
            agentes[i].join();
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }

        critica.leitores   = 0;
        critica.escritores = false;

        long era_final = System.currentTimeMillis();

        //System.out.println("Main thread terminou!");

        //critica.ordenaEPrinta();

        return era_final - era_inicial;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    // DEBUG

    private void DebugaLinhas(Vector<String> linhas)
    {
        System.out.println(linhas.size());
        for(int i = 0; i < linhas.size(); i++)
            System.out.println(linhas.get(i));
    }

    private void DebugNumeroAgentes()
    {
        if(agentes == null)
        {
            System.out.println("Objeto de agentes e null.");
            return;
        }

        int num_leitores   = 0;
        int num_escritores = 0;

        for(int i = 0; i < agentes.length; i++)
        {
            if(agentes[i].Tipo())
                num_escritores++;
            else
                num_leitores++;
        }

        System.out.println("TOTAL: " + agentes.length + " LEITORES: " + num_leitores+ " ESCRITORES: " + num_escritores);
    }
}