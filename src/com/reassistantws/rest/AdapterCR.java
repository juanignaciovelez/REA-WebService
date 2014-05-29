package com.reassistantws.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.ResourceMetaData;
import org.apache.uima.util.CasCreationUtils;
import org.apache.uima.util.Progress;
import org.uimafit.component.JCasCollectionReader_ImplBase;

public class AdapterCR extends JCasCollectionReader_ImplBase {

	CollectionReader cr;
	JCas jcas;
	
	public AdapterCR(CollectionReader c) throws ResourceInitializationException, CASException{
		cr = c;
		final List<ResourceMetaData> metaData = new ArrayList<ResourceMetaData>();
		metaData.add(cr.getMetaData());
		CAS cas = CasCreationUtils.createCas(metaData);
		jcas = cas.getJCas();
	}
	
	@Override
	public Progress[] getProgress() {
		// TODO Auto-generated method stub
		return cr.getProgress();
	}

	@Override
	public boolean hasNext() throws IOException, CollectionException {
		// TODO Auto-generated method stub
		return cr.hasNext();
	}

	@Override
	public void getNext(JCas j) throws IOException, CollectionException {
		// TODO Auto-generated method stub
		cr.getNext(j.getCas());				
	}
	
	public void destroy(){
		super.destroy();
	}

}
