package com.reassistantws.initannotators;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypePriorities;
import org.apache.uima.resource.metadata.TypeSystemDescription;

public abstract class InitAnnotator {
	
	
	public abstract AnalysisEngine getEngine(TypeSystemDescription typeSystemDescription, TypePriorities typePriorities) throws ResourceInitializationException  ;

	public String getModelsPath() {
		String modelsPath = null;
		if(modelsPath == null || modelsPath.isEmpty())
			modelsPath = System.getenv("MODELS_PATH");
		if(modelsPath == null || modelsPath.isEmpty())
			modelsPath = System.getProperty("MODELS_PATH");
		return modelsPath;
	}

}