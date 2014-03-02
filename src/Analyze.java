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
	public void distribute(){
		GPXNode prev = null;
		for(int i = 0; i < nodes.size(); i ++){
			GPXNode g = nodes.get(i);
			if(prev != null){
				arr[g.getHR()] = dateDifference(g, prev) + arr[g.getHR()];
			}
			prev = g;
		}
		for(int i = 0; i < arr.length; i++){
			System.out.println(i+": "+arr[i]);
		}
	}
	
	public void createZones(){
		GPXNode prev = null;
		for(int i = 0; i < nodes.size(); i ++){
			GPXNode g = nodes.get(i);
			if(prev != null){
				if(g.getHR() < 120)
					z1.add(dateDifference(g, prev));
				else if(g.getHR() >120 && g.getHR() < 135)
					z2.add(dateDifference(g, prev));
				else if(g.getHR() >135 && g.getHR() < 150)
					z3.add(dateDifference(g, prev));
				else if(g.getHR() >150 && g.getHR() <168 )
					z4.add(dateDifference(g, prev));
				else if(g.getHR() >168)
					z5.add(dateDifference(g, prev));
			}
			prev = g;
		}
	}
	
	public void normalize(){
		for(int i = 0; i < z1.size(); i++){
			if(z1.get(i) > 10)
				z1.remove(i);
		}
		for(int i = 0; i < z2.size(); i++){
			if(z2.get(i) > 10)
				z2.remove(i);
		}
		for(int i = 0; i < z3.size(); i++){
			if(z3.get(i) > 10)
				z3.remove(i);
		}
		for(int i = 0; i < z4.size(); i++){
			if(z4.get(i) > 10)
				z4.remove(i);
		}
		for(int i = 0; i < z5.size(); i++){
			if(z5.get(i) > 10)
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
	
	public void routine(){
		createZones();
		normalize();
		formatPrint(tally());
	}
	
	
	private int dateDifference(GPXNode a, GPXNode b){	
		return (int) ((a.getTime().getTime() - b.getTime().getTime())/1000);
	}
	
}
