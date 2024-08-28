import java.util.*;

class Pairs {
    int x;
    int y;

    public Pairs (int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getWeight(){
        return this.x;
    }

    public int getValue(){
        return this.y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}

public class Knapsack{
    
    public static void main (String[] args){
        Scanner scan = new Scanner(System.in);
        int instances = scan.nextInt();

        for(int i = 0; i < instances; i++){
            int items = scan.nextInt();
            int capacity = scan.nextInt();
            int indicator = 0;
            //int output = 0;
            ArrayList<Pairs> pairs = new ArrayList<>();

            int[][] v = new int[items][capacity];
            v[0][0] = 0;
            for(int j = 0; j < items; j++){
                int weight = scan.nextInt();
                int value = scan.nextInt();
                Pairs p = new Pairs(weight, value);
                pairs.add(p);
            }

            for(int k = 0; k < items; k++){
                v[k][0] = 0;
                for(int w = 0; w < capacity; w++){
                    

                    v[0][w] = 0;
                   
                }
            }

            for(int k = 1; k <= items; k++){
                for(int w = 1; w <= capacity; w++){
                    if(pairs.get(w).getWeight()<= capacity)
                        indicator = 1;
                    else
                        indicator = 0;
                    System.out.println("k-1: " + (k-1));
                    System.out.println("w stuff: " + (w-pairs.get(w).getWeight()));
                    v[k][w] = Math.max(v[k-1][w], indicator * (v[k-1][w-pairs.get(w).getWeight()] + pairs.get(w).getWeight()));
                }
            }
            System.out.println(v[items][capacity]);
        }


        scan.close();
    }
}