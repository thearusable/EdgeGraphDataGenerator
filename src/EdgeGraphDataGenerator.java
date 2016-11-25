import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

//Original file by:
//Tomasz Lubiński (c)2007
//www.algorytm.org
//"Generowanie gafu z cyklem Eulera"

public class EdgeGraphDataGenerator {
    
     /**
     * @param args
     * @throws java.io.IOException
     * @throws java.net.URISyntaxException
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        int n;
        int b;
        short graph[][];
        boolean fancy = false;
        
        for(String s : args){
            if (("-f".equals(s)) || "-fancy".equals(s)) fancy = true;
        }
        
        System.out.println("Vertex number (min: 1): ");
        String in = System.console().readLine();
        
        n = Integer.parseInt(in);
        
        System.out.println("Saturation in % (1-100%):");
        String in2 = System.console().readLine();
        b = Integer.parseInt(in2);
        
        graph = genGraph(n, b);
        
        saveToFile(n, b, graph, fancy);

    }
    
    public static void saveToFile(int wierzcholki, int nasycenie,  short[][] graph, boolean fancy) throws IOException, URISyntaxException{
        String filename = "Graph_" + wierzcholki + "_" + nasycenie + ".txt";
        String path = new java.io.File(EdgeGraphDataGenerator.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getAbsolutePath();
        path = path.substring(0, path.lastIndexOf(File.separator) + 1);
        
        int EdgeCounter = 0;
        for(int i = 1; i < graph[0].length; i++){
            for(int j = 0; j < i; j++){
                if(graph[i][j] == 1) EdgeCounter += 1;
            }
        }
        
        try{
            FileWriter file = new FileWriter(path + filename);
            
            if(fancy){ 
                file.write("# Vertex count: " + wierzcholki + System.lineSeparator());
                file.write("# Edge count: " + EdgeCounter + System.lineSeparator());
                file.write("# Saturation: " + nasycenie + System.lineSeparator());
                file.write("#" + System.lineSeparator());
                file.write("# <Edges_number>" + System.lineSeparator());
                file.write("# <Edge_index> <Vertex> <Vertex>" + System.lineSeparator());
                file.write("#" + System.lineSeparator());
            }
            
            file.write(EdgeCounter + System.lineSeparator());
            System.out.println(EdgeCounter);
            
            int currentEdge = 0;
            for(int i = 1; i < graph[0].length; i++){
                for(int j = 0; j < i; j++){
                    if(graph[i][j] == 1) {
                        file.write(currentEdge + " " + i + " " + j + System.lineSeparator());
                        System.out.println(currentEdge + " " + i + " " + j);
                        currentEdge += 1;
                    }
                }
            }
            
            file.close();
            
        } catch (IOException ioe){
            System.err.println("IOException: " + ioe.getMessage());
        }
        
        
    }
    
    public static short[][] genGraph(int n, int b) {
        short result[][] = new short[n][n];
        Random rand = new Random();
        
        //clear graph
        for (int i=0; i<n; i++)
            for (int j=0; j<n; j++)
                result[i][j] = 0;
        
        //generate graph with given n and b
        for (int i=1; i<n; i++)
             for (int j=0; j<i; j++)
              if (rand.nextInt(100)<b) {
                  result[i][j] = 1;
                  result[j][i] = 1;
              }

        return result;
    }
}