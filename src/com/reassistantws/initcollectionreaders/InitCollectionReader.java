package com.reassistantws.initcollectionreaders;

import org.apache.uima.collection.CollectionReader;
import org.apache.uima.resource.ExternalResourceDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypePriorities;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.uimafit.factory.ExternalResourceFactory;

import edu.isistan.uima.unified.sharedresources.ProgressMonitorResourceImpl;

public abstract class InitCollectionReader {
	
	public abstract CollectionReader getCollectionReader(String inputFile,TypeSystemDescription typeSystemDescription,TypePriorities typePriorities) throws ResourceInitializationException;

}
