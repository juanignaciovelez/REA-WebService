package com.reassistantws.initannotators;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypePriorities;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.uimafit.factory.AnalysisEngineFactory;

import edu.isistan.uima.unified.analysisengines.wordnet.JAWSWordNetAnnotator;

public class InitJAWSWordNetAnnotator extends InitAnnotator {

	@Override
	public AnalysisEngine getEngine(TypeSystemDescription typeSystemDescription,TypePriorities typePriorities)throws ResourceInitializationException {
		AnalysisEngineDescription aeDescription = 
			AnalysisEngineFactory.createPrimitiveDescription(JAWSWordNetAnnotator.class, typeSystemDescription, typePriorities, 
			"wordnet", getModelsPath() + "wordnet/unix/2.0/dict");
			AnalysisEngine analysisEngine = AnalysisEngineFactory.createPrimitive(aeDescription);
		return analysisEngine;
	}

}
