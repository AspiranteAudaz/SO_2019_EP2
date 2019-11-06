import java.util.Vector;

class Simula
{
    Agente agentes[];
    ES es = new ES();

    Simula(String entrada, String saida, int num_agentes)
    {
        //Le as linhas do arquivo
        Vector<String> linhas = es.ParsaLinhas(es.CarregaArquivo(entrada));
        
        //Gera os objetos de recurso
        Recurso[] recursos = new Recurso[linhas.size()];
        for(int i = 0; i < recursos.length; i++)
            recursos[i] = new Recurso(linhas.remove(0));

        Area critica = new Area(recursos);

        //Gera os agentes
        GeraAgentes(num_agentes);
    }

    private void GeraAgentes(int num_agentes)
    {
        agentes = new Agente[num_agentes];

        for(int i = 0; i < num_agentes; i++)
            agentes[i] = new Agente();
    }

    void Roda()
    {

    }
}