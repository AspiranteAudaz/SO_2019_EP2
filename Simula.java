class Simula
{
    Agente agentes[];

    Simula(int num_agentes, int num_recursos)
    {
        Area critica = new Area(num_recursos);
        GeraAgentes(num_agentes);
    }

    Simula(int num_agentes, Recurso[] recursos)
    {
        Area critica = new Area(recursos);
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