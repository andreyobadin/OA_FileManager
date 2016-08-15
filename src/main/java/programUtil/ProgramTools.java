package programUtil;




import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import entity.ConfigEntity;
import entity.FileEntity;
import utils.HibernateUtil;

public class ProgramTools {
	public static ConfigEntity configFolder(ConfigEntity tC,String patchInputFolder,String patchOutputFolder,String patchErrorFolder){ 
			
			new File(patchInputFolder+"OA_FileReader_Input_Files/").mkdirs();
			new File(patchOutputFolder+"OA_FileReader_Output_Files/").mkdirs();
			new File(patchErrorFolder+"OA_FileReader_Error_Files/").mkdirs();
			
		 tC.setpatchInputFolder(patchInputFolder+"OA_FileReader_Input_Files/");
		 tC.setpatchOutputFolder(patchOutputFolder+"OA_FileReader_Output_Files/");
		 tC.setpatchErorFolder(patchErrorFolder+"OA_FileReader_Error_Files/");
		 
		 System.out.println("Create folder -"+tC.getpatchInputFolder());
			System.out.println("Create folder -"+tC.getpatchOutputFolder());
			System.out.println("Create folder -"+tC.getpatchErorFolder());
		 return tC;
	 } 
	
	public static ConfigEntity config_obj(String patch) throws ParserConfigurationException, SAXException, IOException {

		ConfigEntity tC = new ConfigEntity();
				try {
					File file = new File(patch);
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					DocumentBuilder db = dbf.newDocumentBuilder();
					Document doc = db.parse(file);
					doc.getDocumentElement().normalize();
					NodeList nodeLst = doc.getElementsByTagName("configuration");
					for (int s = 0; s < nodeLst.getLength(); s++) {
						Node fstNode = nodeLst.item(s);
						if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
							Element fstElmnt = (Element) fstNode;
							
							NodeList patchInFolderLst = fstElmnt.getElementsByTagName("patchInputFolder");
							NodeList  patchInFolderNm = patchInFolderLst.item(0).getChildNodes();
							tC.setpatchInputFolder(( patchInFolderNm.item(0)).getNodeValue());
							
							NodeList patchOuFolderLst = fstElmnt.getElementsByTagName("patchOutputFolder");
							NodeList patchOuFolderNm = patchOuFolderLst.item(0).getChildNodes();
							tC.setpatchOutputFolder((patchOuFolderNm.item(0)).getNodeValue());

							NodeList  patchErFolderLst = fstElmnt.getElementsByTagName("patchErorFolder");
							NodeList patchErFolderLstNm = patchErFolderLst.item(0).getChildNodes();
							tC.setpatchErorFolder((patchErFolderLstNm.item(0)).getNodeValue());

							NodeList timeLst = fstElmnt.getElementsByTagName("timeUpdate");
							NodeList timeNm = timeLst.item(0).getChildNodes();
							tC.setUpgrade_time(Integer.parseInt(((timeNm.item(0)).getNodeValue())));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return configFolder(tC, tC.getpatchInputFolder(), tC.getpatchOutputFolder(),tC.getpatchErorFolder());
				
			}

	public static FileEntity file_obj(File file)throws ParserConfigurationException, SAXException, IOException {
		FileEntity fObj =  new FileEntity();

			try {
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nodeLst = doc.getElementsByTagName("Entry");
			for (int s = 0; s < nodeLst.getLength(); s++) {
				Node fstNode = nodeLst.item(s);
				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
					Element fstElmnt = (Element) fstNode;

					NodeList contentLst = fstElmnt.getElementsByTagName("content");
					NodeList contentNm = contentLst.item(0).getChildNodes();
					fObj.setContent((contentNm.item(0)).getNodeValue());

					NodeList DateLst = fstElmnt.getElementsByTagName("creationDate");
					NodeList DateNm = DateLst.item(0).getChildNodes();
					String date = (DateNm.item(0)).getNodeValue();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					fObj.setCreation_Date(formatter.parse(date));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return fObj;
			}

	public static void getListFiles(ConfigEntity config,ArrayList<File>fileList, String inStr) {
		File f = new File(inStr);
		
		for (File s : f.listFiles()) {
			if (s.isFile()) {
				if ((s.getName().substring(s.getName().length() - 4, s.getName().length())).equals(".xml")) {
								
					fileList.add(s);
					
				} else {
					s.renameTo(new File(config.getpatchErorFolder() +"("+new Random().nextInt(100)+")"+ s.getName()));
					System.out.println("File "+s.getName()+" Wrong Format(NOT XML)");
				}
			} else if (s.isDirectory()) {
				s.renameTo(new File(config.getpatchErorFolder() +"("+new Random().nextInt(100)+")"+ s.getName()));
				System.out.println("File "+s.getName()+" Wrong Format(IS DIR)");
			}
		}
		

	}

	public static void addFileList(ConfigEntity config,ArrayList<File>fileList,String str ) throws InterruptedException {
		File f = new File(str);
		System.out.println("Start scan");
		while(true){
			Thread.sleep(config.getUpgrade_time());
		for (File s : f.listFiles()) {
			if (s.isFile()) {
				if ((s.getName().substring(s.getName().length() - 4, s.getName().length())).equals(".xml")) {
					if (!fileList.contains(s)) {
						fileList.add(s);
						
					}
				} else {
					s.renameTo(new File(config.getpatchErorFolder() +"("+new Random().nextInt(100)+")"+ s.getName()));
					System.out.println("File "+s.getName()+" Wrong Format(NOT XML)");
					
				}
			} else if (s.isDirectory()) {
				s.renameTo(new File(config.getpatchErorFolder() +"("+new Random().nextInt(100)+")"+ s.getName()));
				System.out.println("File "+s.getName()+" Wrong Format(IS DIR)");
			}
		}
	}
	}

	public static void write_in_DB(Object obj){ 
		 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		 session.beginTransaction();   
		 session.save(obj);
	     session.getTransaction().commit();
	    }  

	public static void scanlist(ConfigEntity config , ArrayList<File>fileList) throws ParserConfigurationException, SAXException, IOException, InterruptedException {
		while (true) {
			Thread.sleep(1000);
			
					if (!fileList.isEmpty()) {
					for (int i = 0; i < fileList.size(); i++) {
						FileEntity d = file_obj(fileList.get(i));
							write_in_DB(d);
							fileList.get(i).renameTo(new File(config.getpatchOutputFolder()  +"("+new Random().nextInt(1000)+")"+ fileList.get(i).getName()));
							System.out.println("The files are processed");
							
						}
					fileList.clear();
					}else {
						
					}System.out.println("SCAN");
				}	
			}
	
	public void ranScanPars(ConfigEntity config,String inputFolder) throws ParserConfigurationException, SAXException, IOException, InterruptedException{
		
		ArrayList<File> fileList = new ArrayList<File>();

		Thread myThready = new Thread(new Runnable() {
			public void run() 
			{
				try {
					addFileList(config,fileList, inputFolder);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		getListFiles(config,fileList, inputFolder);
		
		myThready.start(); 
		scanlist(config, fileList);
	}
	public static void ifNameClon(File file,String dirPach){
		ArrayList<File>fileList = new ArrayList<File>();
		File f = new File(dirPach);
		
		for (File s : f.listFiles()) {
				fileList.add(s);
				}
		
			if (!fileList.contains(file)) {
				file.renameTo(new File(dirPach + file.getName()));
				
			}else{
				file.renameTo(new File(dirPach + new Random().nextInt()+file.getName()));
			}
	}
	}
		
		
		