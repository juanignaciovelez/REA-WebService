package edu.isistan.uima.unified.analysisengines.nlp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.component.JCasAnnotator_ImplBase;
import org.uimafit.descriptor.ConfigurationParameter;
import edu.isistan.uima.unified.analysisengines.AnnotationGenerator;
import edu.isistan.uima.unified.typesystems.nlp.Sentence;
import edu.isistan.uima.unified.typesystems.nlp.Token;

public class StopWordAnnotator extends JCasAnnotator_ImplBase {
	@ConfigurationParameter(name="model")
	private String modelName;
	//
	protected StopWord stopWord;
	//
	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);
		//modelName = (String) aContext.getConfigParameterValue("model");
		stopWord = StopWord.getInstance();
		if(modelName != null && !modelName.isEmpty()) {
			InputStream in = null;
			try {
				in = this.getClass().getClassLoader().getResourceAsStream(modelName);
				stopWord.read(new BufferedReader(new InputStreamReader(in)));
			}
			catch (Exception e) { 
				e.printStackTrace();
			}
			finally {
				if (in != null) {
					try {
						in.close();
					}
					catch (IOException e) {
					}
				}
			}
		}
	}
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		if(stopWord == null)
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
				@SuppressWarnings("static-access")
				boolean isStopword = stopWord.isStopword(token.getCoveredText());
				AnnotationGenerator.generateStopword(token, isStopword, aJCas);
			}
		}
	}
	
	@Override
	public void destroy() {
		stopWord.clear();
		stopWord = null;
		super.destroy();
	}
}
