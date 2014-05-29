package edu.isistan.uima.unified.analysisengines.opennlp;

import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

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

public class TokenAnnotator extends JCasAnnotator_ImplBase {
	@ConfigurationParameter(name="model")	
	private String modelName;
	//
	protected TokenizerModel model;
	protected Tokenizer tokenizer;
	//
	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);
		InputStream in = null;
		try {
			//modelName = (String) aContext.getConfigParameterValue("model");
//			in = new FileInputStream(modelName);
			in = this.getClass().getClassLoader().getResourceAsStream(modelName);
			model = new TokenizerModel(in);
			tokenizer = new TokenizerME(model);
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
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		if(tokenizer == null)
			return;
		//String docText = aJCas.getDocumentText();
		AnnotationIndex<Annotation> sAnnotations = aJCas.getAnnotationIndex(Sentence.type);
		for(Annotation sAnnotation : sAnnotations) {
			Sentence sentenceAnnotation = (Sentence) sAnnotation;
			
			String[] tokens = tokenizer.tokenize(sentenceAnnotation.getCoveredText());
			double[] probabilities = ((TokenizerME)tokenizer).getTokenProbabilities();
			
			int tokenPos = 0;
			for(int tokenNumber = 0; tokenNumber < tokens.length; tokenNumber++) {
				String token = tokens[tokenNumber];
				double probability = probabilities[tokenNumber];
				int tokenBegin = sentenceAnnotation.getCoveredText().indexOf(token, tokenPos);
				int tokenEnd = tokenBegin + token.length();
				
				AnnotationGenerator.generateToken(sentenceAnnotation.getBegin() + tokenBegin, sentenceAnnotation.getBegin() + tokenEnd, probability, aJCas);
				
				tokenPos = tokenEnd;
			}
		}
	}
	
	@Override
	public void destroy() {
		model = null;
		tokenizer = null;
		super.destroy();
	}
}