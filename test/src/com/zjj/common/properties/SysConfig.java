/*     */ package com.zjj.common.properties;
/*     */ 
/*     */ import java.io.File;

/**
 * 系统配置文件的读取
 * @author Administrator
 *
 */
/*     */ public class SysConfig
/*     */ {
/*     */   private static final String SYSTEM_CONFIG_FILENAME = "system_config.xml";
/*  24 */   public static String SysHomeDir = null;
/*     */ 
/*  27 */   private static XMLProperties properties = null;
/*     */ 
/*     */   public static String getSysHomeDir()
/*     */   {
/*  34 */     if (SysHomeDir == null) {
/*  35 */       SysHomeDir = InitSystem.getSysHomeDir();
/*  36 */       if (SysHomeDir == null) {
/*  37 */         SysHomeDir = System.getProperty("SysHomeDir");
/*     */       }
/*     */     }
/*  40 */     return SysHomeDir;
/*     */   }
/*     */ 
/*     */   public static boolean isSysHomeDirReadable()
/*     */   {
/*  48 */     return new File(getSysHomeDir()).canRead();
/*     */   }
/*     */ 
/*     */   public static boolean isSysHomeDirWritable()
/*     */   {
/*  56 */     return new File(getSysHomeDir()).canWrite();
/*     */   }
/*     */ 
/*     */   public static String getProperty(String name)
/*     */   {
/*  64 */     loadProperties();
/*  65 */     return properties.getProperty(name);
/*     */   }
/*     */ 
/*     */   public static void setProperty(String name, String value)
/*     */   {
/*  74 */     loadProperties();
/*  75 */     properties.setProperty(name, value);
/*     */   }
/*     */ 
/*     */   public static void deleteProperty(String name)
/*     */   {
/*  83 */     loadProperties();
/*  84 */     properties.deleteProperty(name);
/*     */   }
/*     */ 
/*     */   private static synchronized void loadProperties()
/*     */   {
/*  91 */     if (properties == null) {
/*  92 */       if (SysHomeDir == null) {
/*  93 */         SysHomeDir = getSysHomeDir();
/*     */       }
/*     */ 
/*  96 */       properties = new XMLProperties(SysHomeDir + File.separator + "system_config.xml");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getDbType()
/*     */   {
/* 107 */     String dbType = "";
/*     */     try {
/* 109 */       dbType = getProperty("database.dbtype").toString();
/*     */     }
/*     */     catch (Exception e) {
/* 112 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 115 */       return dbType;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\java_team\Genuitec\Workspaces\AWPServer\lib\common\awp-common.jar
 * Qualified Name:     com.ist.common.properties.SysConfig
 * JD-Core Version:    0.5.4
 */