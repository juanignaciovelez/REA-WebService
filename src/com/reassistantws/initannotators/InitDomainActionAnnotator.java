package com.reassistantws.initannotators;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypePriorities;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.uimafit.factory.AnalysisEngineFactory;


import edu.isistan.uima.unified.analysisengines.domain.DomainActionAnnotator;

public class InitDomainActionAnnotator extends InitAnnotator {

	@Override
	public AnalysisEngine getEngine(TypeSystemDescription typeSystemDescription,TypePriorities typePriorities)throws ResourceInitializationException {
		AnalysisEngineDescription aeDescription = 
			AnalysisEngineFactory.createPrimitiveDescription(DomainActionAnnotator.class, typeSystemDescription, typePriorities,
			"model", getModelsPath() + "domain/domainaction.model",
			"label", getModelsPath() + "domain/domainaction.labels",
			"source", getModelsPath() + "domain/domainaction-original.source",
			"filter", getModelsPath() + "domain/domainaction-filtered.source");
		AnalysisEngine analysisEngine = AnalysisEngineFactory.createPrimitive(aeDescription);
		return analysisEngine;
	}

}
