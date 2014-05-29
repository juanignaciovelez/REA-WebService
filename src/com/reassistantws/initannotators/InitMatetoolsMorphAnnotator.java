package com.reassistantws.initannotators;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypePriorities;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.uimafit.factory.AnalysisEngineFactory;
import edu.isistan.uima.unified.analysisengines.matetools.MorphAnnotator;

public class InitMatetoolsMorphAnnotator extends InitAnnotator {

	@Override
	public AnalysisEngine getEngine(TypeSystemDescription typeSystemDescription,TypePriorities typePriorities)throws ResourceInitializationException {
		AnalysisEngineDescription aeDescription = 
			AnalysisEngineFactory.createPrimitiveDescription(MorphAnnotator.class, typeSystemDescription, typePriorities, 
			"model", getModelsPath() + "matetools/is2/model/morph-eng.model");
		AnalysisEngine analysisEngine = AnalysisEngineFactory.createPrimitive(aeDescription);
		return analysisEngine;
	}

}
