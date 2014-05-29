package com.reassistantws.initannotators;


import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypePriorities;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.uimafit.factory.AnalysisEngineFactory;

public class InitAnnotatorDynamic extends InitAnnotator {
	
	private Class compClass;
	private Object[] configData;
	
	public InitAnnotatorDynamic(String anotador,String parametros) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		compClass = Class.forName(anotador);
		configData=parametros.split(",");
		for(int i=0;i<configData.length;i++)
			if((i % 2)==1)
				configData[i]=getModelsPath()+configData[i];
	}
	
	
	@Override
	public AnalysisEngine getEngine(TypeSystemDescription typeSystemDescription,TypePriorities typePriorities)throws ResourceInitializationException {
		AnalysisEngineDescription aeDescription = 
				AnalysisEngineFactory.createPrimitiveDescription(compClass, typeSystemDescription, typePriorities,configData);
			AnalysisEngine analysisEngine = AnalysisEngineFactory.createPrimitive(aeDescription);
			return analysisEngine;
		
	}
}
