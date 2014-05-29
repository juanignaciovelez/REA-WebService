package com.reassistantws.initannotators;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypePriorities;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.uimafit.factory.AnalysisEngineFactory;
import edu.isistan.uima.unified.analysisengines.srl.SDSRLAnnotator;

public class InitSrlSDRLannotator extends InitAnnotator {

	@Override
	public AnalysisEngine getEngine(TypeSystemDescription typeSystemDescription,TypePriorities typePriorities)throws ResourceInitializationException {
		AnalysisEngineDescription aeDescription = 
				AnalysisEngineFactory.createPrimitiveDescription(SDSRLAnnotator.class, typeSystemDescription, typePriorities);
		AnalysisEngine analysisEngine = AnalysisEngineFactory.createPrimitive(aeDescription);
		return analysisEngine;
	}

}
