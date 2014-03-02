
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String activity = "activity.gpx";
		
		GParser g = new GParser(activity);
		Analyze a = new Analyze(g.runParse());
		a.routine();
		
		Swing s = new Swing();
		s.show();
	}

}
