class Main
{
    public static void main(String args[])
    {
        int num_agentes  = 100;    
        int num_leitores = 80;

        String entrada = "./Entrada/bd.txt";
        String saida   = "./Saida/saida.txt";
        Simula sim     = new Simula(entrada);

        long tempo;

        
        for(int i = 0; i < 101; i++)
        {  
            num_leitores = i;
            tempo = 0;
           
            for(int j = 0; j < 50; j++)
            {
                sim.Prepara(num_agentes, num_leitores);
                tempo += sim.Roda();
            }

            tempo /= 50;
            System.out.println("Media " + (num_agentes - num_leitores) + "E" + num_leitores + "L :" + tempo + "ms");
        }

        sim.Prepara(num_agentes, num_leitores);
        sim.Roda();
    }
}