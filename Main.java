class Main
{
    public static void main(String args[])
    {
        int num_agentes  = 100;    
        int num_leitores = 20;

        String entrada = "./Entrada/bd.txt";
        String saida   = "./Saida/saida.txt";

        Simula sim = new Simula(entrada);
        sim.Prepara(num_agentes, num_leitores);
    }
}