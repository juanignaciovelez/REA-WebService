package com.reassistantws.initannotators;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypePriorities;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.uimafit.factory.AnalysisEngineFactory;

import edu.isistan.uima.unified.analysisengines.matetools.LemmaAnnotator;

public class InitMatetoolsLemmaAnnotator extends InitAnnotator{

	@Override
	public AnalysisEngine getEngine(TypeSystemDescription typeSystemDescription,TypePriorities typePriorities)throws ResourceInitializationException {
		
//		Resource resource = new ClassPathResource(getModelsPath() + "matetools/is2/model/lemma-eng.model");
		
		AnalysisEngineDescription aeDescription = 
			AnalysisEngineFactory.createPrimitiveDescription(LemmaAnnotator.class, typeSystemDescription, typePriorities, 
			"model", getModelsPath() + "matetools/is2/model/lemma-eng.model");
		AnalysisEngine analysisEngine = AnalysisEngineFactory.createPrimitive(aeDescription);
		return analysisEngine;	
	}

}
