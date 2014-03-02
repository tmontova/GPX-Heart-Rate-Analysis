import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String activity = "activity_438423190.gpx";
		
		GParser g = new GParser(activity);
		Analyze a = new Analyze(g.runParse());
		
	    JFrame window = new JFrame();
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    window.setBounds(30, 30, 600, 600);
	    window.setTitle("Heart Rate Data");
	    window.getContentPane().add(new Swing(a.routine()));
	    window.setVisible(true);
	}

}
