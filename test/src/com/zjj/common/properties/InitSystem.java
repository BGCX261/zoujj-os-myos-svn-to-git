/*    */ package com.zjj.common.properties;
/*    */ 
/*    */ import java.net.URLDecoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
/**
 * 
 *     初始化系统的时候告诉系统要去那个文件夹下面读取配置文件
 *     
 *     */ 

/*    */ public class InitSystem extends HttpServlet
/*    */ {
/* 30 */   private static String SysHomeDir = "";
/*    */ 
/*    */   public String getSysHomeDirFromProperties()
/*    */   {
/*    */     try {
/* 35 */       String strClasspath = super.getClass().getResource("").getPath();
/* 36 */       String SysHomeDir = URLDecoder.decode(strClasspath, "UTF-8");
/*    */ 
/* 38 */       SysHomeDir = SysHomeDir.substring(0, SysHomeDir.indexOf("/com/zjj/common/properties/"));
/* 39 */       return SysHomeDir;
/*    */     }
/*    */     catch (Exception ex) {
/* 42 */       ex.printStackTrace();
/*    */     }
/* 44 */     return "";
/*    */   }
/*    */ 
/*    */   public void init()
/*    */     throws ServletException
/*    */   {
/* 54 */     ServletConfig conf = getServletConfig();
/* 55 */     SysHomeDir = conf.getInitParameter("SysHomeDir");
/* 56 */     if ((SysHomeDir != null) && (!SysHomeDir.equals(""))) {
/* 57 */       SysHomeDir = SysHomeDir.trim();
/* 58 */       while ((SysHomeDir.endsWith("/")) || (SysHomeDir.endsWith("\\")))
/* 59 */         SysHomeDir = SysHomeDir.substring(0, SysHomeDir.length() - 1);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static String getSysHomeDir()
/*    */   {
/* 69 */     if (SysHomeDir.equals("")) {
/* 70 */       SysHomeDir = null;
/*    */     }
/* 72 */     if (SysHomeDir == null) {
/* 73 */       InitSystem init = new InitSystem();
/* 74 */       SysHomeDir = init.getSysHomeDirFromProperties();
/*    */     }
/* 76 */     return SysHomeDir;
/*    */   }
/*    */ }

/* Location:           D:\java_team\Genuitec\Workspaces\AWPServer\lib\common\awp-common.jar
 * Qualified Name:     com.ist.common.properties.InitSystem
 * JD-Core Version:    0.5.4
 */