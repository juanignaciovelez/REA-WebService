package com.reassistantws.initcollectionreaders;

import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypePriorities;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.uimafit.factory.CollectionReaderFactory;
//import org.uimafit.factory.ExternalResourceFactory;

import org.uimafit.factory.ExternalResourceFactory;

import edu.isistan.uima.unified.collectionreaders.UCSCollectionReader;

public class InitUCSCollectionReader extends InitCollectionReader{

	@Override
	public CollectionReader getCollectionReader(String inputFile,TypeSystemDescription typeSystemDescription,TypePriorities typePriorities) throws ResourceInitializationException {
		System.out.println("Entra a crear ucs");
		CollectionReaderDescription crDescription = 
				CollectionReaderFactory.createDescription(UCSCollectionReader.class, typeSystemDescription, typePriorities, 
				"input", "file:\\"+inputFile);
			return CollectionReaderFactory.createCollectionReader(crDescription);
	}

}
