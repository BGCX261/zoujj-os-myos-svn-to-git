/*     */ package com.zjj.common.properties;
/*     */ 
/*     */ import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.XMLFilterImpl;
/*     */ 
/*     */ class XMLFilterBase extends XMLFilterImpl
/*     */ {
/* 355 */   protected static final Attributes EMPTY_ATTS = new AttributesImpl();
/*     */ 
/*     */   public XMLFilterBase()
/*     */   {
/*     */   }
/*     */ 
/*     */   public XMLFilterBase(XMLReader parent)
/*     */   {
/*  94 */     super(parent);
/*     */   }
/*     */ 
/*     */   public void startElement(String uri, String localName)
/*     */     throws SAXException
/*     */   {
/* 118 */     startElement(uri, localName, "", EMPTY_ATTS);
/*     */   }
/*     */ 
/*     */   public void startElement(String localName)
/*     */     throws SAXException
/*     */   {
/* 137 */     startElement("", localName, "", EMPTY_ATTS);
/*     */   }
/*     */ 
/*     */   public void endElement(String uri, String localName)
/*     */     throws SAXException
/*     */   {
/* 155 */     endElement(uri, localName, "");
/*     */   }
/*     */ 
/*     */   public void endElement(String localName)
/*     */     throws SAXException
/*     */   {
/* 173 */     endElement("", localName, "");
/*     */   }
/*     */ 
/*     */   public void emptyElement(String uri, String localName, String qName, Attributes atts)
/*     */     throws SAXException
/*     */   {
/* 201 */     startElement(uri, localName, qName, atts);
/* 202 */     endElement(uri, localName, qName);
/*     */   }
/*     */ 
/*     */   public void emptyElement(String uri, String localName)
/*     */     throws SAXException
/*     */   {
/* 221 */     emptyElement(uri, localName, "", EMPTY_ATTS);
/*     */   }
/*     */ 
/*     */   public void emptyElement(String localName)
/*     */     throws SAXException
/*     */   {
/* 240 */     emptyElement("", localName, "", EMPTY_ATTS);
/*     */   }
/*     */ 
/*     */   public void dataElement(String uri, String localName, String qName, Attributes atts, String content)
/*     */     throws SAXException
/*     */   {
/* 270 */     startElement(uri, localName, qName, atts);
/* 271 */     characters(content);
/* 272 */     endElement(uri, localName, qName);
/*     */   }
/*     */ 
/*     */   public void dataElement(String uri, String localName, String content)
/*     */     throws SAXException
/*     */   {
/* 301 */     dataElement(uri, localName, "", EMPTY_ATTS, content);
/*     */   }
/*     */ 
/*     */   public void dataElement(String localName, String content)
/*     */     throws SAXException
/*     */   {
/* 331 */     dataElement("", localName, "", EMPTY_ATTS, content);
/*     */   }
/*     */ 
/*     */   public void characters(String data)
/*     */     throws SAXException
/*     */   {
/* 348 */     char[] ch = data.toCharArray();
/* 349 */     characters(ch, 0, ch.length);
/*     */   }
/*     */ }

/* Location:           D:\java_team\Genuitec\Workspaces\AWPServer\lib\common\awp-common.jar
 * Qualified Name:     com.ist.common.properties.XMLFilterBase
 * JD-Core Version:    0.5.4
 */