package com.reassistantws.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.uima.UIMAException;
import org.apache.uima.cas.impl.XmiCasDeserializer;
import org.apache.uima.cas.impl.XmiCasSerializer;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.XMLSerializer;
import org.xml.sax.SAXException;

public class ProxyServer {
	
	public void deserializar(InputStream is, JCas jcas) throws SAXException, IOException{
		XmiCasDeserializer.deserialize(is, jcas.getCas());
	}
	
	public String serializar(JCas jcas) throws SAXException, IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		XmiCasSerializer ser = new XmiCasSerializer(jcas.getTypeSystem());
		ser.serialize(jcas.getCas(), (new XMLSerializer(out,false)).getContentHandler());
		out.close();
		String xmlContent = out.toString();
		return xmlContent;
	}	
	
	public void registrarServicio(){
		
	}
	
	public void process(JCas jcas, AdapterAE ae) throws UIMAException, IOException{
		ae.process(jcas);
	}

}
