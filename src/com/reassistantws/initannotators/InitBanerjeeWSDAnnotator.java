package com.reassistantws.initannotators;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypePriorities;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.uimafit.factory.AnalysisEngineFactory;
import edu.isistan.uima.unified.analysisengines.wsd.BanerjeeWSDAnnotator;

public class InitBanerjeeWSDAnnotator extends InitAnnotator{

	@Override
	public AnalysisEngine getEngine(TypeSystemDescription typeSystemDescription,TypePriorities typePriorities)throws ResourceInitializationException {
		AnalysisEngineDescription aeDescription =  
			AnalysisEngineFactory.createPrimitiveDescription(BanerjeeWSDAnnotator.class, typeSystemDescription, typePriorities, 
			"jwnl", getModelsPath() + "jwnl/jwnl-properties.xml",
			"wordnet", getModelsPath() + "wordnet/win/2.0/dict/",
			"similarity", "Lesk");
		AnalysisEngine analysisEngine = AnalysisEngineFactory.createPrimitive(aeDescription);
		return analysisEngine;
		}

}
