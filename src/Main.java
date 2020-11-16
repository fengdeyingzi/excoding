import java.io.*;
import java.util.*;

//import org.apache.commons.io.FileUtils;

public class Main {

	
    /**
     * 将制定目录下的所有Java源文件的编码格式从GBK修改为UTF-8
     */
    public static void main(String[] args) throws Exception {
    	int type_index=0;
    	String type=null;
    	String path=null;
    	String coding=null; //编码
    	String ocodeing=null; //目标编码
    	String types=".java|.c|.h|.txt|.md|.php|.py|.bat|.sh|.cpp|.cc|.js|.ts|.htm|.html|.xml|.fxml|.json|.swift|.kt|.dart|.s"; //删除文件的类型
    	Main main = new Main();
        
        for(int i=0;i<args.length;i++){
        	String item = args[i];
        	switch (type_index) {
			case 0:
				if(item.equals("-t")){ //类型  transcoding 转码   removefiles 删文件
					type = args[i+1];
				}
				else if(item.equals("-f")){
					
				}
				else if(item.equals("-i")){ //输入
					path = args[i+1];
				}
				else if(item.equals("-o")){
					
				}
				else if(item.equals("-coding")){ //编码
					coding = args[i+1];
				}
				else if(item.equals("-ocoding")){ //目标编码
					ocodeing = args[i+1];
				}
				else if(item.equals("-types")){
					types = args[i+1];
				}
				else if(item.equals("-h")){
					System.out.print("-t 类型，取值如下 \n    transcoding 转码\n    removefiles 删文件\n"
				+"-i 输入文件夹路径\n"
				+"-coding 转换编码\n"
				+"-ocoding 目标编码\n"
				+"风的影子 制作");
				}
				break;

			default:
				break;
			}
        }
        if(type==null){
        	System.out.print("输入转换文件夹的路径来进行转换\n本应用支持命令行操作，输入-h查看控制台命令\n"
    				+"风的影子 制作\n");
        	Scanner input = new Scanner(System.in);
        	path = input.nextLine();
        	if(path.equals("-h")){
        		main.outHelp();
        		Thread.sleep(5000);
        	}
        	else{
        		System.out.println("请输入文件夹编码（输入前缀数字即可）\n    1.GBK\n    2.UTF-8\n");
        	String num = input.nextLine();
        	if(num.equals("1")){
        		coding = "GBK";
        	}
        	else if(num.equals("2")){
        		coding = "UTF-8";
        	}
        	else{
        		System.out.println("你的输入有误");
        	}
        	System.out.println("请输入目标编码（输入前缀数字即可）\n    1.GBK\n    2.UTF-8\n");
        	num = input.nextLine();
        	if(num.equals("1")){
        		ocodeing = "GBK";
        	}
        	else if(num.equals("2")){
        		ocodeing = "UTF-8";
        	}
        	else {
        		System.out.println("你的输入有误");
        	}
        	if(path!=null && coding!=null && ocodeing!=null){
        		main.encodeFiles(path,coding,ocodeing,types.split("\\|"));
        	}
        	}
        	
        }
        else if(type.equals("transcodeing")){
        	if(path==null){
        		System.out.println("-i 未知，请输入文件夹路径");
        	}
        	else if(coding==null){
        		System.out.println("-coding 编码未知，请输入文件编码");
        	}
        	else if(ocodeing==null){
        		System.out.println("-ocoding 未知，请输入目标编码");
        	}
        	else
        	main.encodeFiles(path,coding,ocodeing,types.split("\\|"));
        }
        else if(type.equals("removefiles")){
        	if(path==null){
        		System.out.println("-i 未知，请输入文件夹路径");
        	}
        	else if(types==null){
        		System.out.println("-types 未知，请输入删除类型");
        	}
        	else{
        		main.deleteFilesForType(path, types.split("\\|"));
        	}
        }

      

    }
    
    void outHelp(){
    	System.out.print("-t 类型，取值如下 \n    transcoding 转码\n    removefiles 删文件\n"
				+"-i 输入文件夹路径\n"
				+"-coding 转换编码\n"
				+"-ocoding 目标编码\n"
				+"风的影子 制作");
    	Scanner input = new Scanner(System.in);
    	String num = input.nextLine();
    }
    
    //删除某个文件夹下所有的指定文件类型
    void deleteFilesForType(String path,String[] types){
        // 获取所有java文件
        Collection<File> javaGbkFileCol = FileUtils.listFiles(new File(path),types, true);
        FileUtils.removeFiles(new File(path),types);
        System.out.println("删除"+path+"目录下"+types+"成功");
    }
    
    //转换某个文件夹下的文件
    void encodeFiles(String path,String coding, String encoding,String[] types){
    	System.out.println("转换编码"+coding+" to "+encoding);
    	 // 获取所有java文件
        Collection<File> javaGbkFileCol = FileUtils.listFiles(new File(path),types, true);
        for (File javaGbkFile : javaGbkFileCol) {
            // UTF8格式文件路径
            String utf8FilePath2 = javaGbkFile.getAbsolutePath();// utf8DirPath + javaGbkFile.getAbsolutePath().substring(srcDirPath.length());
//            String srcDirPath2 = srcDirPath + javaGbkFile.getAbsolutePath().substring(srcDirPath.length());
            // 使用GBK读取数据，然后用UTF-8写入数据
            String charsetName = getFileEncode(javaGbkFile.getPath()); 
            String text;
			try {
				text = FileUtils.read(javaGbkFile,coding);
				 if(!encoding.equals(charsetName)){
                System.out.println(javaGbkFile.getName() +":"+ charsetName); 
				FileUtils.writeText(utf8FilePath2,text,encoding);
                //FileUtils.writeLines(new File(utf8FilePath2), "UTF-8", FileUtils.readLines(javaGbkFile, charsetName));
            }
			else
			{
				System.out.println("无需转换->"+javaGbkFile.getName()+":"+charsetName);
			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
        }
        System.out.println("编码转换完成");
    }

    /**
     * 利用第三方开源包cpdetector获取文件编码格式
     *
     * @param path
     *            要判断文件编码格式的源文件的路径
     * @author huanglei
     * @version 2012-7-12 14:05
     */
    public static String getFileEncode(String path) {
        /*
         * detector是探测器，它把探测任务交给具体的探测实现类的实例完成。
         * cpDetector内置了一些常用的探测实现类，这些探测实现类的实例可以通过add方法 加进来，如ParsingDetector、
         * JChardetFacade、ASCIIDetector、UnicodeDetector。
         * detector按照“谁最先返回非空的探测结果，就以该结果为准”的原则返回探测到的
         * 字符集编码。使用需要用到三个第三方JAR包：antlr.jar、chardet.jar和cpdetector.jar
         * cpDetector是基于统计学原理的，不保证完全正确。
         */
       // CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
        /*
         * ParsingDetector可用于检查HTML、XML等文件或字符流的编码,构造方法中的参数用于
         * 指示是否显示探测过程的详细信息，为false不显示。
         */
       // detector.add(new ParsingDetector(false));
        /*
         * JChardetFacade封装了由Mozilla组织提供的JChardet，它可以完成大多数文件的编码
         * 测定。所以，一般有了这个探测器就可满足大多数项目的要求，如果你还不放心，可以
         * 再多加几个探测器，比如下面的ASCIIDetector、UnicodeDetector等。
         */
     //   detector.add(JChardetFacade.getInstance());// 用到antlr.jar、chardet.jar
        // ASCIIDetector用于ASCII编码测定
     //   detector.add(ASCIIDetector.getInstance());
        // UnicodeDetector用于Unicode家族编码的测定
    //    detector.add(UnicodeDetector.getInstance());
        java.nio.charset.Charset charset = null;
        File f = new File(path);
		FileInputStream input=null;
		byte [] buf=null;
		try
		{
			input =  new FileInputStream(f);
		}
		catch (FileNotFoundException e)
		{
			return "GBK";
		}

		try
		{
			buf = new byte[input.available()];
		}
		catch (IOException e)
		{}
		try
		{
			input.read(buf);
		}
		catch (IOException e)
		{}
        boolean isu = isUTF8(buf,buf.length-1);
        if (isu)
            return "UTF-8";
        else
            return "GBK";
    }
	
    /**
     *
     URL url = CreateStationTreeModel.class.getResource("/resource/" +
     * "配置文件"); URLConnection urlConnection = url.openConnection();
     * inputStream=urlConnection.getInputStream(); String charsetName =
     * getFileEncode(url); System.out.println(charsetName); BufferedReader in =
     * new BufferedReader(new InputStreamReader(inputStream, charsetName));
     * **/
	 

//判断utf编码，0为成功，-1失败
	public static boolean isUTF8(byte[] pBuffer, int size)
	{
		boolean IsUTF8 = true;
		int start = 0;
		int end = size;
		int c=0;
		while (start < end)
		{
			c = pBuffer[start]&0xff;
			if (c < 0x80) // (10000000): 值小于0x80的为ASCII字符
			{
				start++;
			}
			else if (c < (0xC0)) // (11000000): 值介于0x80与0xC0之间的为无效UTF-8字符
			{
				IsUTF8 = false;
				break;
			}
			else if (c < (0xE0)) // (11100000): 此范围内为2字节UTF-8字符
			{
				if (start >= end - 1)
					break;
				if ((pBuffer[start+1] & (0xC0)) != 0x80)
				{
					IsUTF8 = false;
					break;
				}
				start += 2;
			}
			else if (c < (0xF0)) // (11110000): 此范围内为3字节UTF-8字符
			{
				if (start >= end - 2)
					break;
				if ((pBuffer[start+1] & (0xC0)) != 0x80 || (pBuffer[start+2] & (0xC0)) != 0x80)
				{
					IsUTF8 = false;
					break;
				}
				start += 3;
			}
			else
			{
				IsUTF8 = false;
				break;
			}
		}
		return IsUTF8;
	}






}
 
