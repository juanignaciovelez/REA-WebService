package com.reassistantws.initcollectionreaders;

import org.apache.uima.collection.CollectionReader;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypePriorities;
import org.apache.uima.resource.metadata.TypeSystemDescription;

public class InitRawTextCollectionReader extends InitCollectionReader {

	@Override
	public CollectionReader getCollectionReader(String inputFile,TypeSystemDescription typeSystemDescription,TypePriorities typePriorities)throws ResourceInitializationException {
		return null;
	}

}
