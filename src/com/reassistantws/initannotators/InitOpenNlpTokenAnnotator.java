package com.reassistantws.initannotators;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypePriorities;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.uimafit.factory.AnalysisEngineFactory;


import edu.isistan.uima.unified.analysisengines.opennlp.TokenAnnotator;

public class InitOpenNlpTokenAnnotator extends InitAnnotator {

	@Override
	public AnalysisEngine getEngine(TypeSystemDescription typeSystemDescription,TypePriorities typePriorities)throws ResourceInitializationException {
		AnalysisEngineDescription aeDescription = 
			AnalysisEngineFactory.createPrimitiveDescription(TokenAnnotator.class, typeSystemDescription, typePriorities, 
			"model", getModelsPath() + "opennlp/models/en-token.bin");
		AnalysisEngine analysisEngine = AnalysisEngineFactory.createPrimitive(aeDescription);
		return analysisEngine;
	}

}
