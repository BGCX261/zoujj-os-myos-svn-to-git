/*     */ package com.zjj.common.properties;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
/*     */ 
/*     */ public class XMLProperties
/*     */ {
/*     */   private File file;
/*     */   private Document doc;
/*  58 */   private Map propertyCache = new HashMap();
/*     */ 
/*     */   public XMLProperties(String file)
/*     */   {
/*  66 */     this.file = new File(file);
/*     */     try {
/*  68 */       SAXBuilder builder = new SAXBuilder();
/*     */ 
/*  70 */       DataUnformatFilter format = new DataUnformatFilter();
/*  71 */       builder.setXMLFilter(format);
/*     */ 
/*  73 */       this.doc = builder.build(new File(file));
/*     */     }
/*     */     catch (Exception e) {
/*  76 */       System.err.println("在XMLProperties类中建立XML剖析器失败。File=" + file);
/*  77 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getProperty(String name)
/*     */   {
/*  88 */     if (this.propertyCache.containsKey(name)) {
/*  89 */       return (String)this.propertyCache.get(name);
/*     */     }
/*     */ 
/*  92 */     String[] propName = parsePropertyName(name);
/*     */ 
/*  94 */     Element element = this.doc.getRootElement();
/*  95 */     for (int i = 0; i < propName.length; ++i) {
/*  96 */       element = element.getChild(propName[i]);
/*  97 */       if (element == null)
/*     */       {
/* 100 */         return null;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 105 */     String value = element.getText();
/* 106 */     if ("".equals(value)) {
/* 107 */       return null;
/*     */     }
/*     */ 
/* 111 */     value = value.trim();
/* 112 */     this.propertyCache.put(name, value);
/* 113 */     return value;
/*     */   }
/*     */ 
/*     */   public String[] getChildrenProperties(String parent)
/*     */   {
/* 128 */     String[] propName = parsePropertyName(parent);
/*     */ 
/* 130 */     Element element = this.doc.getRootElement();
/* 131 */     for (int i = 0; i < propName.length; ++i) {
/* 132 */       element = element.getChild(propName[i]);
/* 133 */       if (element == null)
/*     */       {
/* 136 */         return new String[0];
/*     */       }
/*     */     }
/*     */ 
/* 140 */     List children = element.getChildren();
/* 141 */     int childCount = children.size();
/* 142 */     String[] childrenNames = new String[childCount];
/* 143 */     for (int i = 0; i < childCount; ++i) {
/* 144 */       childrenNames[i] = ((Element)children.get(i)).getName();
/*     */     }
/* 146 */     return childrenNames;
/*     */   }
/*     */ 
/*     */   public void setProperty(String name, String value)
/*     */   {
/* 158 */     this.propertyCache.put(name, value);
/*     */ 
/* 160 */     String[] propName = parsePropertyName(name);
/*     */ 
/* 162 */     Element element = this.doc.getRootElement();
/* 163 */     for (int i = 0; i < propName.length; ++i)
/*     */     {
/* 166 */       if (element.getChild(propName[i]) == null) {
/* 167 */         element.addContent(new Element(propName[i]));
/*     */       }
/* 169 */       element = element.getChild(propName[i]);
/*     */     }
/*     */ 
/* 172 */     element.setText(value);
/*     */ 
/* 174 */     saveProperties();
/*     */   }
/*     */ 
/*     */   public void deleteProperty(String name)
/*     */   {
/* 183 */     String[] propName = parsePropertyName(name);
/*     */ 
/* 185 */     Element element = this.doc.getRootElement();
/* 186 */     for (int i = 0; i < propName.length - 1; ++i) {
/* 187 */       element = element.getChild(propName[i]);
/*     */ 
/* 189 */       if (element == null) {
/* 190 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 194 */     element.removeChild(propName[(propName.length - 1)]);
/*     */ 
/* 196 */     saveProperties();
/*     */   }
/*     */ 
/*     */   private synchronized void saveProperties()
/*     */   {
/* 204 */     OutputStream out = null;
/* 205 */     boolean error = false;
/*     */ 
/* 207 */     File tempFile = null;
/*     */     try {
/* 209 */       tempFile = new File(this.file.getParentFile(), this.file.getName() + ".tmp");
/*     */ 
/* 215 */       Format format = Format.getCompactFormat();
/* 216 */       format.setIndent("    ");
/* 217 */       XMLOutputter outputter = new XMLOutputter(format);
/* 218 */       out = new BufferedOutputStream(new FileOutputStream(tempFile));
/* 219 */       outputter.output(this.doc, out);
/*     */     }
/*     */     catch (Exception e) {
/* 222 */       e.printStackTrace();
/*     */ 
/* 224 */       error = true;
/*     */     } finally {
/*     */       try {
/* 227 */         out.close();
/*     */       } catch (Exception e) {
/* 229 */         e.printStackTrace();
/* 230 */         error = true;
/*     */       }
/*     */     }
/*     */ 
/* 234 */     if (error)
/*     */       return;
/* 236 */     this.file.delete();
/*     */ 
/* 240 */     tempFile.renameTo(this.file);
/*     */   }
/*     */ 
/*     */   private String[] parsePropertyName(String name)
/*     */   {
/* 255 */     int size = 1;
/* 256 */     for (int i = 0; i < name.length(); ++i) {
/* 257 */       if (name.charAt(i) == '.') {
/* 258 */         ++size;
/*     */       }
/*     */     }
/* 261 */     String[] propName = new String[size];
/*     */ 
/* 263 */     StringTokenizer tokenizer = new StringTokenizer(name, ".");
/* 264 */     int i = 0;
/* 265 */     while (tokenizer.hasMoreTokens()) {
/* 266 */       propName[i] = tokenizer.nextToken();
/* 267 */       ++i;
/*     */     }
/* 269 */     return propName;
/*     */   }
/*     */ }

/* Location:           D:\java_team\Genuitec\Workspaces\AWPServer\lib\common\awp-common.jar
 * Qualified Name:     com.ist.common.properties.XMLProperties
 * JD-Core Version:    0.5.4
 */