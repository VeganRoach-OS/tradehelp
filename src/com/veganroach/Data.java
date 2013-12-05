package com.veganroach;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author 2mac
 */
public class Data {
	static File recipesXml, matsXml;
	static DocumentBuilderFactory dbFactory;
	static DocumentBuilder dBuilder;
	static Document recipesDoc, matsDoc;
	public static ArrayList<Element> items;
	public static ArrayList<String> names;
	public static void initVariables(){
		System.out.print("Initializing XML...");
		recipesXml = new File("lists/recipes.xml");
		matsXml = new File("lists/mats.xml");
		dbFactory = DocumentBuilderFactory.newInstance();
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			recipesDoc = dBuilder.parse(recipesXml);
			matsDoc = dBuilder.parse(matsXml);
		} catch (Exception e) {
			System.out.println("An error has occurred. Contact Developer.");
			e.printStackTrace();
		}
		items = new ArrayList<Element>();
		names = new ArrayList<String>();
		System.out.println(" done.");
	}
	public static void initData(){
		System.out.print("Initializing item list...");
		recipesDoc.getDocumentElement().normalize();
		NodeList recipesNList = recipesDoc.getElementsByTagName("item");
		for (int i = 0; i < recipesNList.getLength(); i++){
			Node node = recipesNList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE){
				Element element = (Element) node;
				items.add(element);
			}
		}
		for (Element e : items) names.add(e.getAttribute("id"));
		Collections.sort(names);
		System.out.println(" done.");
	}
	public static double getPrice(String item, int subLevel){
		if (subLevel > 0) for (int i = 0; i < subLevel; i++) System.out.print("\t");
		System.out.println("Retrieving price of " + item + ".");
		double price = 0;
		matsDoc.getDocumentElement().normalize();
		NodeList matsNList = matsDoc.getElementsByTagName("item");
		for (int i = 0; i < matsNList.getLength(); i++){
			Node node = matsNList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE){
				Element element = (Element) node;
				if (element.getAttribute("id").equalsIgnoreCase(item)){
					return Double.valueOf(element.getElementsByTagName("price").item(0).getTextContent());
				}
			}
		}
		NodeList recipesNList = recipesDoc.getElementsByTagName("item");
		for (int i = 0; i < recipesNList.getLength(); i++){
			Node node = recipesNList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE){
				Element element = (Element) node;
				if (element.getAttribute("id").equalsIgnoreCase(item)){
					NodeList specNList = element.getElementsByTagName("component");
					for (int j = 0; j < specNList.getLength(); j++){
						Node specNode = specNList.item(j);
						if (specNode.getNodeType() == Node.ELEMENT_NODE){
							Element specEl = (Element) specNode;
							price += getPrice(specEl.getAttribute("id"),subLevel+1) *
								Double.valueOf(specEl.getElementsByTagName("quantity").item(0).getTextContent());
						}
					}
					try {
						String check = element.getElementsByTagName("opquantity").item(0).getTextContent();
						if (check != null){
							price = price / Double.valueOf(check);
						}
					}catch (NullPointerException ignored){}
				}
			}
		}
		return price;
	}
	public static double getExchangeRate(String item1, String item2, int qty){
		System.out.println("\n");
		double price1 = getPrice(item1,0), price2 = getPrice(item2,0);
		double rate = (price1 * qty) / price2;
		return rate;
	}
	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
	public static String getXmlVersions(){
		matsDoc.getDocumentElement().normalize();
		String matsVersion = matsDoc.getElementsByTagName("version").item(0).getTextContent();
		recipesDoc.getDocumentElement().normalize();
		String recipesVersion = recipesDoc.getElementsByTagName("version").item(0).getTextContent();
		return "mats.xml version " + matsVersion + "\nrecipes.xml version " + recipesVersion;
	}
	public static String getMatsXmlVersion(){
		matsDoc.getDocumentElement().normalize();
		return matsDoc.getElementsByTagName("version").item(0).getTextContent();
	}
	public static String getRecipesXmlVersion(){
		recipesDoc.getDocumentElement().normalize();
		return recipesDoc.getElementsByTagName("version").item(0).getTextContent();
	}
}
