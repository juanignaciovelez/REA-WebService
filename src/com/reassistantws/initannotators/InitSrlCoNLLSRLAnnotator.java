package com.reassistantws.initannotators;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypePriorities;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.uimafit.factory.AnalysisEngineFactory;
import edu.isistan.uima.unified.analysisengines.srl.CoNLLSRLAnnotator;

public class InitSrlCoNLLSRLAnnotator extends InitAnnotator {

	@Override
	public AnalysisEngine getEngine(TypeSystemDescription typeSystemDescription,TypePriorities typePriorities)throws ResourceInitializationException {
		AnalysisEngineDescription aeDescription = 
				AnalysisEngineFactory.createPrimitiveDescription(CoNLLSRLAnnotator.class, typeSystemDescription, typePriorities,
				"model", getModelsPath() + "srl/se/lth/cs/srl/model/srl-eng.model",
				"propbank", getModelsPath() + "srl/propbank/",
				"nombank", getModelsPath() + "srl/nombank/");
		AnalysisEngine analysisEngine = AnalysisEngineFactory.createPrimitive(aeDescription);
		return analysisEngine;
	}
}
