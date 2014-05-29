package edu.isistan.uima.unified.analysisengines.nlp;

import java.util.Iterator;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.tartarus.snowball.ext.englishStemmer;
import org.uimafit.component.JCasAnnotator_ImplBase;
import edu.isistan.uima.unified.analysisengines.AnnotationGenerator;
import edu.isistan.uima.unified.typesystems.nlp.Sentence;
import edu.isistan.uima.unified.typesystems.nlp.Token;

public class StemmerAnnotator  extends JCasAnnotator_ImplBase {
	protected englishStemmer stemmer;
	//
	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);
		stemmer = new englishStemmer();
	}
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		if(stemmer == null)
			return;

		//String docText = aJCas.getDocumentText();
		AnnotationIndex<Annotation> sAnnotations = aJCas.getAnnotationIndex(Sentence.type);
		AnnotationIndex<Annotation> tAnnotations = aJCas.getAnnotationIndex(Token.type);

		for(Annotation sAnnotation : sAnnotations) {
			//Sentence sentenceAnnotation = (Sentence) sAnnotation;
			//String sentence = sAnnotation.getCoveredText();
			
			Iterator<Annotation> tokenIterator = tAnnotations.subiterator(sAnnotation);
			while(tokenIterator.hasNext()) {
				Annotation tAnnotation = tokenIterator.next();
				Token token = (Token) tAnnotation;
				stemmer.setCurrent(token.getCoveredText());
				stemmer.stem();
				String stem = stemmer.getCurrent();
				AnnotationGenerator.generateStem(token, stem, aJCas);
			}

		}

	}
	
	@Override
	public void destroy() {
		stemmer = null;
		super.destroy();
	}
}