package com.reassistantws.rest;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.impl.XmiCasSerializer;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypePriorities;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.XMLSerializer;
import org.uimafit.factory.JCasFactory;
import org.xml.sax.SAXException;

import com.reassistantws.initannotators.InitAnnotator;
import com.reassistantws.initannotators.InitAnnotatorDynamic;
import com.reassistantws.initcollectionreaders.InitCollectionReader;

import edu.isistan.uima.unified.UIMAFactory;



@Path("/broker")
public class Broker {
		
	private JCas jcas;
	private UIMAFactory factory = UIMAFactory.getInstance();
	private TypeSystemDescription typeSystemDescription;
	TypePriorities typePriorities;
//	ExternalResourceDescription resource;
	private static String inputFile;
	
	public Broker() throws IOException, UIMAException{
		typeSystemDescription = factory.getTypeSystemDescription();
		typePriorities = factory.getTypePriorities();
//		resource = factory.getProgressMonitorResource();
		jcas = JCasFactory.createJCas();
	}
		
	public void cargarInput(String contenido) throws IOException {
		File tempFile = File.createTempFile("input",null);
		BufferedWriter out = new BufferedWriter(new FileWriter(tempFile));
		String aux = new String(contenido.getBytes(), "UTF-8");
		out.write(aux);
		out.close();
		System.out.println("rutaA: "+tempFile.getAbsolutePath());
		inputFile = tempFile.getAbsolutePath();
	}
	
	public String exec(String anot) throws ResourceInitializationException, ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, CASException, CollectionException, AnalysisEngineProcessException, SAXException{
		anot=decodePath(anot);
		anot.replace("#", "/");
		Object[] anotadores =anot.split("_");
		//CollectionReader
		String crName= (String) anotadores[0];		
		InitCollectionReader initCR = crearInitCR(crName);
		CollectionReader cr = initCR.getCollectionReader(inputFile, typeSystemDescription, typePriorities);
		AdapterCR aCReader = new AdapterCR(cr);
		aCReader.getNext(jcas);
		//Vector de AnalysisEngine
		Vector <AnalysisEngine> analysisEngines= new Vector<AnalysisEngine>();
		for(int i=1;i<anotadores.length;i++){
			InitAnnotator iAnot=crearInit(anotadores[i].toString());
			if(iAnot==null){
				InitAnnotator dinamico= crearInitDynamic(anotadores[i].toString());
				analysisEngines.add(dinamico.getEngine(typeSystemDescription, typePriorities));
			}else{
				analysisEngines.add(iAnot.getEngine(typeSystemDescription, typePriorities));
			}
		}	
		//Procesamiento de JCAS
		for (int i = 0; i < analysisEngines.size(); i++) {
			AnalysisEngine ae = analysisEngines.elementAt(i);
			ae.process(jcas);
			ae.destroy();
		}
		File ruta = new File(inputFile); 
		if ( ruta.delete() ) 
		 System.out.println("Eliminado"); 
		else 
		System.out.println("No eliminado"); 
		cr.destroy();
		aCReader.destroy();
		return serializar(jcas);
	}
	
	@POST
	@Path("/exec/{anotador}")
	@Produces(MediaType.TEXT_PLAIN)
	public String ejecutar(@FormParam("input")String contenido,@PathParam("anotador")String anot) throws IOException, AnalysisEngineProcessException, ResourceInitializationException, CASException, CollectionException, ClassNotFoundException, InstantiationException, IllegalAccessException, SAXException{
		cargarInput(contenido);
		return exec(anot);
	}	

	private InitAnnotator crearInitDynamic(String anotadores) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
		String[] anotD=anotadores.replace("}", "").replace("{", "_").split("_");
		Vector<String> sol=getAllClasses("org.uimafit.component.JCasAnnotator_ImplBase");
		for(String anot: sol ){
			if (anot.contains(anotD[0]))
				return new InitAnnotatorDynamic(anot,anotD[1]);
		}
		return null;
	}
	
	private InitAnnotator crearInit(String anot) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
		Vector<String> Clases= getAllClasses("InitAnnotator");
		 for(String c: Clases){
			if(c.contains(anot)){
				 Class<?> cls = Class.forName(c);
				 Object obj = cls.newInstance();
				 return  (InitAnnotator)obj ;			
			 }
		 }
		 return null;
	}
	
	private InitCollectionReader crearInitCR(String cr) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
		Vector<String> clases= getAllClasses("InitCollectionReader");
		System.out.println("-:"+clases.size());
		 for(String c: clases){
			if(c.contains(cr)){
				 Class<?> cls = Class.forName(c);
				 Object obj = cls.newInstance();
				 return  (InitCollectionReader)obj ;			
			 }
		 }
		 return null;
	}

	private Vector<String> getAllClasses(String superclase) throws ClassNotFoundException, IOException {
		Package[] packages = Package.getPackages();
		Vector<String> Sol=new Vector<String>();
		for (int i = 0; i < packages.length; i++) {
			Vector<Class> clases = getClasses(packages[i].getName());
			for (int j = 0; j < clases.size(); j++) {
				Class<?> cls = Class.forName(clases.elementAt(j).getName());
				if(cls.getSuperclass()!=null && cls.getSuperclass().getName().contains(superclase)){
					Sol.add( cls.getName());
				}
			}
		}
		return Sol;
	}
	
	@GET
	@Path("/listar")
	@Produces(MediaType.TEXT_XML)
	public String listarServicios() throws ClassNotFoundException, IOException{
		System.out.print(	InetAddress.getLocalHost().getHostAddress());
		Package[] packages = Package.getPackages();
		String sol ="<listado>\n";
		for (int i = 0; i < packages.length; i++) {
			Vector<Class> clases = getClasses(packages[i].getName());
			for (int j = 0; j < clases.size(); j++) {
				Class<?> cls = Class.forName(clases.elementAt(j).getName());
				if(cls.getSuperclass()!=null && cls.getSuperclass().getName().equals("org.uimafit.component.JCasAnnotator_ImplBase")){
					sol+="<anotador>"+cls.getSimpleName()+"</anotador>\n";
				}
			}
		}
		return sol+"</listado>";
	}
	
	private static Vector<Class> getClasses(String packageName)throws ClassNotFoundException, IOException {
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	     assert classLoader != null;
	    String path = packageName.replace('.', '/');
	    Enumeration<URL> resources = classLoader.getResources(path);
	    List<File> dirs = new ArrayList<File>();
	    while (resources.hasMoreElements()) {
	    	URL resource = resources.nextElement();
	        dirs.add(new File(resource.getFile()));
	    }
	    Vector<Class> classes = new Vector<Class>();
	    for (File directory : dirs) {
	        classes.addAll(findClasses(directory, packageName));
	    }
	    return classes;
	}
	
	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
	    List<Class> classes = new ArrayList<Class>();
	    if (!directory.exists()) {
	        return classes;
	    }
	    File[] files = directory.listFiles();
	    for (File file : files) {
	    	if (file.isDirectory()) {
	                assert !file.getName().contains(".");
	                classes.addAll(findClasses(file, packageName + "." + file.getName()));
	        } else if (file.getName().endsWith(".class")) {
	                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
	        }
	    }
	    return classes;
	}
	
	public String decodePath(String inPath) throws UnsupportedEncodingException{
		String model =URLDecoder.decode(inPath,"UTF-8");
		String aux=model.replace('#', '/');
		return aux;
	}
 
	public String serializar(JCas jcas) throws SAXException, IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		XmiCasSerializer ser = new XmiCasSerializer(jcas.getTypeSystem());
		ser.serialize(jcas.getCas(), (new XMLSerializer(out,false)).getContentHandler());
		out.close();
		String xmiContent="";
		xmiContent = out.toString();
		return xmiContent;
	}
	
}
