package com.owera.xapsws.rawxml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class XMLClient {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public static void main(String[] args) throws HttpException, IOException {

		//		HttpConnectionManager connectionManager = new SimpleHttpConnectionManager();
		HttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		//		hcmp = new HttpConnectionManagerParams();
		//		hcmp.setStaleCheckingEnabled(true);
		//		int totConn = args.getNumberOfThreadsPrStep()*args.getNumberOfSteps();
		//		hcmp.setMaxTotalConnections(totConn);
		HttpClient client = new HttpClient(connectionManager);
		//		client.getParams().setParameter(HttpConnectionParams.STALE_CONNECTION_CHECK, new Boolean(false));
		client.getParams().setParameter(HttpClientParams.USE_EXPECT_CONTINUE, new Boolean(true));
		client.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 2000);
//		String url = "http://localhost:8082/xapsws/services/xAPSWS";
		String url = "http://pingcom.xaps-hosting.net/xapsws/services/xAPSWS";
		String xml = retrieveXmlFromFile("perl-client-test-modified.xml");
//		String xml = retrieveXmlFromFile("owera-java-client-test.xml");
		PostMethod pm = new PostMethod(url);
		if (xml != null) {
			RequestEntity requestEntity = new StringRequestEntity(xml, "text/xml", "ISO-8859-1");
			pm.setRequestEntity(requestEntity);
			pm.setRequestHeader(new Header("SOAPAction", "http://http://xapsws.owera.com/xapsws/soap"));
		}
		int statusCode = client.executeMethod(pm);

		Reader reader = new InputStreamReader(pm.getResponseBodyAsStream(), pm.getResponseCharSet());
		BufferedReader br = new BufferedReader(reader);
		StringBuilder sb = new StringBuilder();
		while (true) {
			String line = br.readLine();
			if (line == null)
				break;
			sb.append(line);
		}
		String response = sb.toString();

		System.out.println("Status-code: " + statusCode);
		System.out.println("Response: " + prettyFormat(response));
	}

	public static String retrieveXmlFromFile(String filename) throws IOException {
		String xml = "";
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = null;
		while ((line = br.readLine()) != null) {
			xml += line;
		}
		return xml;
	}
	
	public static String prettyFormat(String unformattedXml) {
		try {
			final Document document = parseXmlFile(unformattedXml);
			OutputFormat format = new OutputFormat(document);
			format.setLineWidth(65);
			format.setIndenting(true);
			format.setIndent(2);
			Writer out = new StringWriter();
			XMLSerializer serializer = new XMLSerializer(out, format);
			serializer.serialize(document);
			return out.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static Document parseXmlFile(String in) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(in));
			return db.parse(is);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
