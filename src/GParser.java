import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class GParser {
	
	private String activity;

	public GParser(String activity){
		this.activity = activity;
	}

	public ArrayList<GPXNode> runParse(){
		ArrayList<GPXNode> gNodeList = new ArrayList<GPXNode>();
		try{
			File xml = new File(activity);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xml);
		
			NodeList hrList = doc.getElementsByTagName("gpxtpx:hr");
			NodeList timeList = doc.getElementsByTagName("time");
	        String pattern = "yyyy-MM-dd'T'hh:mm:ss";
	        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			
			for(int i = 0; i <hrList.getLength(); i++){
				NodeList hList = hrList.item(i).getChildNodes();
				NodeList tList = timeList.item(i+1).getChildNodes();
				for(int j = 0; j < hList.getLength(); j++){
					String t = parse(tList.item(j).toString());
					int h = Integer.parseInt(parse(hList.item(j).toString()));
					gNodeList.add(new GPXNode(sdf.parse(t), h));
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return gNodeList;
	}
	
	private String parse(String t){
		boolean read = false;
		String temp = "";
		for(char c : t.toCharArray()){
			if(c == ' ')
				read = true;
			else if(c == ']')
				read = false;
			else if(read)
				temp = temp + c;
		}
		return temp;
	}

	
}
