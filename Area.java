class Area
{
    static final int SLEEP_TIME   = 1;
    private Recurso  buffer[];

    Area(int num_recursos)
    {
        buffer = new Recurso[num_recursos];
    }

    Area(Recurso buffer[])
    {
        this.buffer = buffer;
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
}