package zjj.excel;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 读取excel gui 原型。
 * @author Administrator
 *
 */
public class BeforSwingJuiExcel {

	private static JTextField jft = new JTextField();
	public static void main(String[] args) {
		//实现jfranme
		final JFrame jf = new JFrame("excel列转行");
		//获取面板控制�?
		Container container = jf.getContentPane();
		jf.setLocationRelativeTo(null);
		//获取面板
		final JPanel jpanel = new JPanel();
		//把面板放入面板控制器
		container.add(jpanel);
		//设置面板布局格式
		jpanel.setLayout(null);
		
		final JLabel  jl = new JLabel("文件位置:");
		jl.setBounds(20, 40, 60, 25);
		
		
		JButton jb = new JButton("开始转换");
		jb.setSize(80, 25);
		jb.setBounds(80, 120, 100, 25);
		JButton jbResult = new JButton("重置");
		jbResult.setSize(80, 25);
		jbResult.setBounds(200, 120, 100, 25);
		
		
		
		JButton jb2 = new JButton("浏览");
		jb2.setSize(80, 25);
		jb2.setBounds(310, 40, 60, 25);
		
		
		jft.setSize(180, 25);
		jft.setBounds(80, 40, 220, 25);
		
		jpanel.add(jl);
		jpanel.add(jb);
		jpanel.add(jb2);
		jpanel.add(jbResult);
		jpanel.add(jft);
		
		//上传文件
		jb.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String strcontext= jft.getText();
				File file = new File(strcontext);
				try {
					Map<String,String> map = BeforExcel2007.readExcel(file);
					BeforExcel2007.creatExcelByPoi(BeforExcel2007.rowCloumMap,map,"C:/AA.xls");
					jft.setText("转换完毕……");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		jb2.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent a) {
				FileDialog fd = null;
				//InputStreamReader isr = null;
				fd = new FileDialog(jf,"请选择要上传的文件",FileDialog.LOAD);
				fd.setVisible(true);
				//获得要读取文件的绝对路径
				String str = fd.getDirectory()+fd.getFile();
				jft.setText(str);
			}			
		});
		
		jbResult.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			jft.setText("");	
			}
		});
		
		
		jf.addWindowListener(new WindowAdapter() {
			public void  windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		jf.setSize(400, 200);
		jf.setVisible(true);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(3);
	}

}
