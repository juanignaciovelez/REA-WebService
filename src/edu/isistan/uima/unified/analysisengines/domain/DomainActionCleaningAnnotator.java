package edu.isistan.uima.unified.analysisengines.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.component.JCasAnnotator_ImplBase;
import edu.isistan.uima.unified.typesystems.domain.DomainAction;

public class DomainActionCleaningAnnotator extends JCasAnnotator_ImplBase {      
	
	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);
	}
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		//
		AnnotationIndex<Annotation> daAnnotations = aJCas.getAnnotationIndex(DomainAction.type);
		List<DomainAction> toRemove = new ArrayList<DomainAction>();
		//
		for(Annotation daAnnotation : daAnnotations) {
			DomainAction domainAction = (DomainAction) daAnnotation;
			toRemove.add(domainAction);
		}
		for(DomainAction domainAction : toRemove)
			aJCas.removeFsFromIndexes(domainAction);
	}

	@Override
	public void destroy() {
		super.destroy();
	}
}