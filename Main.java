class Main
{
    public static void main(String args[])
    {
        //num agentes
        int num_agentes  = 100;

        //num de leitores
        int num_leitores = 0;

        //Paths de entrada e saida (nao utilizado)
        String entrada = "./Entrada/bd.txt";
        String saida   = "./Saida/saida.txt";
        
        //O segundo argumento determina a implementacao usada
        Simula sim     = new Simula(entrada, Area.IMP_01);
        //Simula sim     = new Simula(entrada, Area.IMP_02);
        //Simula sim     = new Simula(entrada, Area.SEM_LE);
        
        long tempo;

        //Gera todas as proporcoes
        for(int i = 0; i < 101; i++)
        {  
            num_leitores = i;
            tempo = 0;
            
            //roda 50 vezes
            for(int j = 0; j < 50; j++)
            {
                sim.Prepara(num_agentes, num_leitores);
                tempo += sim.Roda();
            }

            //media
            tempo /= 50;
            System.out.println("Media " + (num_agentes - num_leitores) + "E" + num_leitores + "L :" + tempo + "ms");
            //System.out.println(tempo);
        }
    }
}