/*     */ package com.zjj.common.properties;
/*     */ 
/*     */ import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
/*     */ 
/*     */ class DataUnformatFilter extends XMLFilterBase
/*     */ {
/* 265 */   private static final Object SEEN_NOTHING = new Object();
/* 266 */   private static final Object SEEN_ELEMENT = new Object();
/* 267 */   private static final Object SEEN_DATA = new Object();
/*     */ 
/* 274 */   private Object state = SEEN_NOTHING;
/* 275 */   private Stack stateStack = new Stack();
/*     */ 
/* 277 */   private StringBuffer whitespace = new StringBuffer();
/*     */ 
/*     */   public DataUnformatFilter()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DataUnformatFilter(XMLReader xmlreader)
/*     */   {
/*  59 */     super(xmlreader);
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*  74 */     this.state = SEEN_NOTHING;
/*  75 */     this.stateStack = new Stack();
/*  76 */     this.whitespace = new StringBuffer();
/*     */   }
/*     */ 
/*     */   public void startDocument()
/*     */     throws SAXException
/*     */   {
/*  95 */     reset();
/*  96 */     super.startDocument();
/*     */   }
/*     */ 
/*     */   public void startElement(String uri, String localName, String qName, Attributes atts)
/*     */     throws SAXException
/*     */   {
/* 114 */     clearWhitespace();
/* 115 */     this.stateStack.push(SEEN_ELEMENT);
/* 116 */     this.state = SEEN_NOTHING;
/* 117 */     super.startElement(uri, localName, qName, atts);
/*     */   }
/*     */ 
/*     */   public void endElement(String uri, String localName, String qName)
/*     */     throws SAXException
/*     */   {
/* 133 */     if (this.state == SEEN_ELEMENT)
/* 134 */       clearWhitespace();
/*     */     else {
/* 136 */       emitWhitespace();
/*     */     }
/* 138 */     this.state = this.stateStack.pop();
/* 139 */     super.endElement(uri, localName, qName);
/*     */   }
/*     */ 
/*     */   public void characters(char[] ch, int start, int length)
/*     */     throws SAXException
/*     */   {
/* 155 */     if (this.state != SEEN_DATA)
/*     */     {
/* 158 */       int end = start + length;
/* 159 */       while (end-- > start) {
/* 160 */         if (!isXMLWhitespace(ch[end]))
/*     */         {
/*     */           break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 169 */       if (end < start) {
/* 170 */         saveWhitespace(ch, start, length);
/*     */       } else {
/* 172 */         this.state = SEEN_DATA;
/* 173 */         emitWhitespace();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 178 */     if (this.state == SEEN_DATA)
/* 179 */       super.characters(ch, start, length);
/*     */   }
/*     */ 
/*     */   public void ignorableWhitespace(char[] ch, int start, int length)
/*     */     throws SAXException
/*     */   {
/* 196 */     emitWhitespace();
/*     */   }
/*     */ 
/*     */   public void processingInstruction(String target, String data)
/*     */     throws SAXException
/*     */   {
/* 212 */     emitWhitespace();
/* 213 */     super.processingInstruction(target, data);
/*     */   }
/*     */ 
/*     */   protected void saveWhitespace(char[] ch, int start, int length)
/*     */   {
/* 227 */     this.whitespace.append(ch, start, length);
/*     */   }
/*     */ 
/*     */   protected void emitWhitespace()
/*     */     throws SAXException
/*     */   {
/* 237 */     char[] data = new char[this.whitespace.length()];
/* 238 */     if (this.whitespace.length() > 0) {
/* 239 */       this.whitespace.getChars(0, data.length, data, 0);
/* 240 */       this.whitespace.setLength(0);
/* 241 */       super.characters(data, 0, data.length);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void clearWhitespace()
/*     */   {
/* 249 */     this.whitespace.setLength(0);
/*     */   }
/*     */ 
/*     */   private boolean isXMLWhitespace(char c)
/*     */   {
/* 258 */     return (c == ' ') || (c == '\t') || (c == '\r') || (c == '\n');
/*     */   }
/*     */ }

/* Location:           D:\java_team\Genuitec\Workspaces\AWPServer\lib\common\awp-common.jar
 * Qualified Name:     com.ist.common.properties.DataUnformatFilter
 * JD-Core Version:    0.5.4
 */