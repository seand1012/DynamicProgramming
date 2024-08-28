import java.util.*;

class Trio {

    long x;
    long y;
    long z;

    public Trio(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Gets first value of trio
     * In this instance, gets start time
     * @return start time
     */
    public long getStart() {
        return this.x;
    }

    /**
     * Gets second value of trio
     * In this instance, gets finish time
     * @return finish time
     */
    public long getFinish() {
        return this.y;
    }

    /**
     * Gets third value of trio
     * In this instance, gets weight
     * @return weight
     */
    public long getWeight() {
        return this.z;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }

}

class sortTrios {
    static void sortY(ArrayList<Trio> arr) {
        Comparator<Trio> comp = new Comparator<Trio>() {

            @Override
            public int compare(Trio o1, Trio o2) {
                return Long.compare(o1.getFinish(),o2.getFinish());
            }

        };

        arr.sort(comp);
    }

    static void sortZ(ArrayList<Trio> arr) {
        Comparator<Trio> comp = new Comparator<Trio>() {

            @Override
            public int compare(Trio o1, Trio o2) {
                return Long.compare(o1.getWeight(),(o2.getWeight()));
            }

        };

        arr.sort(comp);
    }
}

public class dp {

    public dp() {
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        long instances = scan.nextInt();
        long output = 0;

        for (int i = 0; i < instances; i++) {
            int numJobs = scan.nextInt();

            ArrayList<Long> start = new ArrayList<>();
            ArrayList<Long> finish = new ArrayList<>();
            ArrayList<Long> weight = new ArrayList<>();
            ArrayList<Trio> jobs = new ArrayList<>();

            for (int j = 0; j < numJobs; j++) {
                start.add(scan.nextLong());
                finish.add(scan.nextLong());
                weight.add(scan.nextLong());
            }

            if (numJobs == 1) {
                output = weight.get(0);
            } else {

                for (int j = 0; j < start.size(); j++) {
                    Trio t = new Trio(start.get(j), finish.get(j), weight.get(j));
                    jobs.add(t);
                }

                // sort by finish time
                sortTrios.sortY(jobs);

                output = findMaxWeight(jobs);

            }

            System.out.println(output);
            output = 0;
            start.clear();
            finish.clear();
            weight.clear();
            jobs.clear();
        }

        scan.close();
    }

    /**
     * Receives an array of requests sorted by finish time
     * 
     * @param jobs
     * @return
     */
    private static long findMaxWeight(ArrayList<Trio> jobs) {
        int n = jobs.size();
        sortTrios.sortY(jobs);
        ArrayList<Long> solMatrix = new ArrayList<>();
        
        solMatrix.add(jobs.get(0).getWeight());
        for(int i = 1; i < n; i++){
            // get the weight of the next job in list
            long totalWeight = jobs.get(i).getWeight();
            
            int index = findClosestJob(jobs, i);
            if(index != -1) {
                totalWeight += solMatrix.get(index);
            }
            solMatrix.add(Math.max(totalWeight, solMatrix.get(i-1)));
        }
        
        // answer will be at the (n-1)-th entry
        return solMatrix.get(n-1);
    }

    /**
     * Finds job closest to one being checked
     * Uses binary search
     * @param jobs
     * @param index
     * @return index of closest i < j s.t. i.finish <= j.start, negative otherwise
     */
    private static int findClosestJob(ArrayList<Trio> jobs, int index){
        int low = 0;
        int high = index - 1;

        while(low <= high){
            int mid = (low + high) / 2;

            // if the finish time of the middle job does NOT overlap with the next job
            if(jobs.get(mid).getFinish() <= jobs.get(index).getStart()){
                // check if the next job after mid job also doesn't overlap
                if(jobs.get(mid + 1).getFinish() <= jobs.get(index).getStart()){
                    low = mid + 1;
                }
                // if not, mid is the maximum job
                // return mid
                else{
                    return mid;
                }
            }
            // if the finish time DOES overlap the next job
            // then every job after also overlaps
            // check only jobs from low to middle - 1
            else{
                high = mid - 1;
            }
        }

        return -1;
    }

}
