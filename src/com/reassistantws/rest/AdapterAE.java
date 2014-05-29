package com.reassistantws.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ExternalResourceDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.ResourceMetaData;
import org.apache.uima.resource.metadata.TypePriorities;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.CasCreationUtils;
import org.apache.uima.util.InvalidXMLException;

import java.lang.reflect.Method;

import edu.isistan.uima.unified.UIMAFactory;
import edu.isistan.uima.unified.typesystems.srs.Document;
import edu.isistan.uima.unified.typesystems.srs.Section;

public class AdapterAE {

	AnalysisEngine ae;
	JCas jcas;
	
	
	public AdapterAE() throws ResourceInitializationException, IOException, InvalidXMLException{
		UIMAFactory factory = UIMAFactory.getInstance();
		TypeSystemDescription typeSystemDescription = factory.getTypeSystemDescription();
		TypePriorities typePriorities = factory.getTypePriorities();
		ExternalResourceDescription resource = factory.getProgressMonitorResource();
		ae = factory.getOpenNLPTokenAA(typeSystemDescription, typePriorities, resource);
	}
	
	public AdapterAE(AnalysisEngine a) throws ResourceInitializationException, CASException{
		ae=a;
		final List<ResourceMetaData> metaData = new ArrayList<ResourceMetaData>();
		metaData.add(ae.getMetaData());
		CAS cas = CasCreationUtils.createCas(metaData);
		jcas = cas.getJCas();
	}
		
	public void process(JCas jcas) throws UIMAException, IOException{
		System.out.println("doc: "+jcas.getAnnotationIndex(Document.type).size());
		System.out.println("sec: "+jcas.getAnnotationIndex(Section.type).size());
		ae.process(jcas);
	}
	
	public Class<? extends AdapterAE> obtenerClase(){
		return this.getClass();
	}
	
	public Method[] listarMetodos(){
		Class<? extends AdapterAE> c = this.getClass();
		return c.getMethods();
	}
}
