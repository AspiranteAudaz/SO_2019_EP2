import java.util.Vector;
import java.util.Random;

class Simula
{
    private ES es       = new ES();
    private Random alea = new Random();

    private Agente agentes[];
    private Area   critica;

    private String entrada;

    Simula(String entrada)
    {
        //Le os recursos do arquivo de entrada
        PegaRecursos(entrada);
    }

    private void PegaRecursos(String entrada)
    {
        //Le as linhas do arquivo
        Vector<String> linhas = es.ParsaPalavras(es.CarregaArquivo(entrada));

        // DEBUG
        //DebugaLinhas(linhas);

        //Gera os objetos de recurso
        Recurso[] recursos = new Recurso[linhas.size()];
        for(int i = 0; i < recursos.length; i++)
            recursos[i] = new Recurso(linhas.remove(0));

        critica = new Area(recursos);
    }

    private void GeraAgentes(int num_agentes, int num_leitores)
    {
        int chance         = num_leitores;
        int num_escritores = num_agentes - num_leitores;

        agentes = new Agente[num_agentes];

        for(int i = 0; i < num_agentes; i++)
        {
            if((num_leitores > 0) && (alea.nextInt(num_agentes) <= chance))
            {
                agentes[i] = new Agente(Agente.LEITOR); 
                num_leitores--;
            }
            else if(num_escritores > 0)
            {
                agentes[i] = new Agente(Agente.ESCRITOR);
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

    void Roda()
    {

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
            if(agentes[i].tipo)
                num_escritores++;
            else
                num_leitores++;
        }

        System.out.println("TOTAL: " + agentes.length + " LEITORES: " + num_leitores+ " ESCRITORES: " + num_escritores);
    }
}