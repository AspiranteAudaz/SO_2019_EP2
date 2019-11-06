class Main
{
    public static void main(String args[])
    {
        int num_agentes = 100;    
        String entrada  = "./Entrada/entrada.txt";
        String saida    = "./Saida/saida.txt";

        Simula sim = new Simula(entrada, saida, num_agentes);
    }
}