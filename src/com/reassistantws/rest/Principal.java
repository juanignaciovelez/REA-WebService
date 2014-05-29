package com.reassistantws.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.impl.XmiCasSerializer;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ExternalResourceDescription;
import org.apache.uima.resource.metadata.TypePriorities;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.XMLSerializer;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.uimafit.factory.JCasFactory;
import org.xml.sax.SAXException;

import edu.isistan.uima.unified.UIMAFactory;
import edu.isistan.dal.ucs.model.UCSModelPackage;




@Path("/principal")
public class Principal {
		
	@GET
	@Path("/ejecutar")
	@Produces(MediaType.TEXT_XML)
	public String ejecutar(@PathParam("nombre")String text) throws UIMAException, IOException, SAXException{
		UIMAFactory factory = UIMAFactory.getInstance();
		TypeSystemDescription typeSystemDescription = factory.getTypeSystemDescription();
		TypePriorities typePriorities = factory.getTypePriorities();
		ExternalResourceDescription resource = factory.getProgressMonitorResource();
	  	
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		EPackage.Registry.INSTANCE.put(UCSModelPackage.eNS_URI, UCSModelPackage.eINSTANCE);
		
		//CREA CR y AE
		CollectionReader cr = factory.getUCSCR(typeSystemDescription, typePriorities, resource, "CRS.ucs");
		AnalysisEngine ae = factory.getOpenNLPSentenceAA(typeSystemDescription,typePriorities, resource);
		AnalysisEngine ae1 = factory.getOpenNLPTokenAA(typeSystemDescription, typePriorities, resource);
		
		//JCAS 
		JCas jcas = JCasFactory.createJCas();
		//CREA ADAPTER C READER
		AdapterCR aCReader = new AdapterCR(cr);
		//CREA ADAPTER A ENGINE
//		AdapterAE AESentence = new AdapterAE(ae);
//		AdapterAE AEToken = new AdapterAE(ae1);

		//EJECUTA
//		aCReader.getNext(jcas);
//		AESentence.process(jcas);
//		AEToken.process(jcas);
//		System.out.println("clase: "+AESentence.obtenerClase());
//		for (Method m : AESentence.listarMetodos()){
//			System.out.println("metodo: "+m.getName());
//		}
		
		//SERIALIZA
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		XmiCasSerializer ser = new XmiCasSerializer(jcas.getTypeSystem());
		ser.serialize(jcas.getCas(), (new XMLSerializer(out,false)).getContentHandler());
		out.close();
		String xmiContent = out.toString();
//		
		return xmiContent;
	}
	
}
