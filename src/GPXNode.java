import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GPXNode {

	
	private Date time;
	private int hr;

	public GPXNode(Date time, int hr){
		this.time = time;
		this.hr = hr;
	}
	
	public void printNode(){
		System.out.println("time: "+ time+" hr: "+ hr);
	}
		
	public Date getTime(){ return time; }
	public int getHR(){	return hr;	}
}
