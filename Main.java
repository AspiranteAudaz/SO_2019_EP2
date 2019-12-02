class Main
{
    public static void main(String args[])
    {
        int num_agentes  = 100;
        int num_leitores = 0;

        String entrada = "./Entrada/bd.txt";
        String saida   = "./Saida/saida.txt";
        Simula sim     = new Simula(entrada, Area.IMP_01);
        //Simula sim     = new Simula(entrada, Area.IMP_02);
        //Simula sim     = new Simula(entrada, Area.SEM_LE);
        
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
            //System.out.println(tempo);
        }

        sim.Prepara(num_agentes, num_leitores);
        sim.Roda();
    }
}