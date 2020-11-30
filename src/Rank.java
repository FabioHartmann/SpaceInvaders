package src;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

class Rank implements Serializable {
    private ArrayList<Integer> ranking;

    private Rank() {
        ranking = new ArrayList<Integer>();
    }

    public ArrayList<Integer> getRanking() {
        return ranking;
    }

    public static Rank createRank() {
        try {
            FileInputStream arquivoLeitura = new FileInputStream ("./rank.txt");
            ObjectInputStream objLeitura =
                    new ObjectInputStream(arquivoLeitura);
            Object readRank = objLeitura.readObject();

            if(readRank != null){
                return (Rank) readRank;
            }

            objLeitura.close();
            arquivoLeitura.close();
        } catch(Exception e) {
            return new Rank();
        }
        return new Rank();
    }


    public void changeRank(int score) throws Exception {
        try{
            ArrayList<Integer> newRank = ranking;
            newRank.add(score);
            Collections.sort(newRank, Collections.reverseOrder());
            if(newRank.size() > 10){
                newRank.remove(10);
            }
            ranking = newRank;
            writeRank();
        } catch(Exception e){
            throw new Exception("Errrrrrrou");
        }

    }

    public void writeRank() throws Exception {
        try{
            FileOutputStream arquivoGrav =
                    new FileOutputStream("./rank.txt");
            ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);
            objGravar.writeObject(this);
            objGravar.flush();
            objGravar.close();
            arquivoGrav.flush();
            arquivoGrav.close();
        } catch(Exception e) {
            throw new Exception("Ranking criado");
        }
    }



}