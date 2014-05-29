package com.reassistantws.initannotators;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypePriorities;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.uimafit.factory.AnalysisEngineFactory;

public class InitStanfordSentenceTokenAnnotator extends InitAnnotator{

	@Override
	public AnalysisEngine getEngine(TypeSystemDescription typeSystemDescription,TypePriorities typePriorities)throws ResourceInitializationException {
		AnalysisEngineDescription aeDescription = 
			AnalysisEngineFactory.createPrimitiveDescription(edu.isistan.uima.unified.analysisengines.stanfordnlp.POSAnnotator.class, typeSystemDescription, typePriorities, 
			"model", getModelsPath() + "stanford-corenlp/edu/stanford/nlp/models/pos-tagger/wsj3t0-18-left3words/left3words-distsim-wsj-0-18.tagger");
		AnalysisEngine analysisEngine = AnalysisEngineFactory.createPrimitive(aeDescription);
		return analysisEngine;
	}
}