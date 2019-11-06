import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;
import java.util.Arrays;

/*
 * https://docs.oracle.com/javase/8/docs/api/java/io/FileReader.html
 * https://docs.oracle.com/javase/8/docs/api/java/io/Reader.html#read-char:A-
 * https://docs.oracle.com/javase/8/docs/api/java/util/List.html
 * https://docs.oracle.com/javase/7/docs/api/java/io/FileWriter.html
 */

public class ES
{

    void EscreveArquivo(String path, String dados)
    {
        try
        {
            FileWriter writter = new FileWriter(path);
            writter.write(dados, 0, dados.length());
            writter.close();
        }
        catch(Exception ex)
        {
            System.out.println("Erro critico na escrita do arquivo: " + ex.toString());
        }
    }

    Vector<String> ParsaPalavras(char[] buffer)
    {
        Vector<String> palavras = new Vector<String>();
        
        //Uma linha
        String palavra = "";

        for(int i = 0; i < buffer.length; i++)
        {
            //Testa se e espaco, nova linha, carriage return ou line feed
            if(buffer[i] == ' ' || buffer[i] == '\n' || buffer[i] == '\f' || buffer[i] == '\r')
            {
                if(palavra.length() > 0)
                {
                    palavras.addElement(palavra.toUpperCase());
                    palavra = "";
                }

                continue;
            }
            
            //Concatena valor
            palavra += buffer[i];
        }

        return palavras;
    }

    char[] CarregaArquivo(String path)
    {
        File file = null;

        try
        {
            file = new File(path);
        }
        catch(Exception ex)
        {
            System.out.print("ERRO ES, erro de localizacao de arquivo " + path + " :\n" + ex.toString() + "\n");
        }
        
        return CarregaArquivo(file);
    }

    private char[] CarregaArquivo(File file)
    {
        FileReader reader = null;

        //Abre arquivo e cria leitor
        try 
        {
            reader = new FileReader(file);
        } 
        catch (Exception ex) 
        {
            //Tomamos GG, path errado ou arquivos não existem
            System.out.print("ERRO ES, erro de localizacao de arquivo :\n" + ex.toString() + "\n");
        }

        //Buffer de leitura
        char buffer[] = new char[(int)file.length()];
        
        try
        {
            reader.read(buffer);
            reader.close();
        }
        catch(Exception ex)
        {
            //So se estiverem de zuera
            System.out.print("ERRO ES, falha ao ler arquivo :\n" + ex.toString() + "\n");
        }

        return buffer;
    }
}