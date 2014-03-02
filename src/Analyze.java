import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class Analyze {

	private ArrayList<GPXNode> nodes;
	int[] arr = new int[200];
	ArrayList<Integer> z1 = new ArrayList<Integer>();
	ArrayList<Integer> z2 = new ArrayList<Integer>();
	ArrayList<Integer> z3 = new ArrayList<Integer>();
	ArrayList<Integer> z4 = new ArrayList<Integer>();
	ArrayList<Integer> z5 = new ArrayList<Integer>();


	public Analyze(ArrayList<GPXNode> nodes){
		this.nodes = nodes;
	}
	
	public void printDifferences(){
		GPXNode prev = null;
		for(GPXNode g : nodes){
			if(prev != null){
				System.out.println(dateDifference(g, prev) + " "+ g.getHR());
			}
			prev = g;
		}
	}

	/**
	 * Creates the zones that the data will fit in
	 * Should take in array of crucial points,
	 * where hr zone changes to next
	 */
	public void createZones(int[] z){
		GPXNode prev = null;
		for(int i = 0; i < nodes.size(); i ++){
			GPXNode g = nodes.get(i);
			if(prev != null){
				if(g.getHR() < z[0])
					z1.add(dateDifference(g, prev));
				else if(g.getHR() > z[0] && g.getHR() < z[1])
					z2.add(dateDifference(g, prev));
				else if(g.getHR() > z[1] && g.getHR() < z[2])
					z3.add(dateDifference(g, prev));
				else if(g.getHR() > z[2] && g.getHR() < z[3])
					z4.add(dateDifference(g, prev));
				else if(g.getHR() > z[3])
					z5.add(dateDifference(g, prev));
			}
			prev = g;
		}
	}
	/**
	 * Removes any data points that are greater than the threshold;
	 * threshold = length of time in between data points;
	 * if it is beyond this threshold, it is likely the function
	 * is not continuous i.e. paused Garmin
	 */
	public void normalize(int threshold){
		for(int i = 0; i < z1.size(); i++){
			if(z1.get(i) > threshold)
				z1.remove(i);
		}
		for(int i = 0; i < z2.size(); i++){
			if(z2.get(i) > threshold)
				z2.remove(i);
		}
		for(int i = 0; i < z3.size(); i++){
			if(z3.get(i) > threshold)
				z3.remove(i);
		}
		for(int i = 0; i < z4.size(); i++){
			if(z4.get(i) > threshold)
				z4.remove(i);
		}
		for(int i = 0; i < z5.size(); i++){
			if(z5.get(i) > threshold)
				z5.remove(i);
		}
	}
	
	public int[] tally(){

		int[] zones = new int[5];
		
		for(int i = 0; i < z1.size(); i++)
			zones[0] = zones[0] + z1.get(i);
		for(int i = 0; i < z2.size(); i++)
			zones[1] = zones[1] + z2.get(i);
		for(int i = 0; i < z3.size(); i++)
			zones[2] = zones[2] + z3.get(i);
		for(int i = 0; i < z4.size(); i++)
			zones[3] = zones[3] + z4.get(i);
		for(int i = 0; i < z5.size(); i++)
			zones[4] = zones[4] + z5.get(i);
				
		return zones;
	}
	public void formatPrint(int[] zones){
		double total = 0;
        DecimalFormat df = new DecimalFormat("##");
		for(int i : zones){
			total = total + i;
		}
		for(int i=0; i < zones.length; i++){
			int min = zones[i]/60;
			int sec = zones[i] - (min*60);
			System.out.println("Zone "+(i+1)+": "+ min+"m, "+sec+"s. "+df.format((zones[i]/total)*100)+"%");
		}
	}
	
	public int[] routine(){
		int[] z = {111, 129, 148, 166};
		createZones(z);
		normalize(10);
		formatPrint(tally());
		return tally();
	}
	
	
	private int dateDifference(GPXNode a, GPXNode b){	
		return (int) ((a.getTime().getTime() - b.getTime().getTime())/1000);
	}
	
}
