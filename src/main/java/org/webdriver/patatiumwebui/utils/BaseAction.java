package org.webdriver.patatiumwebui.utils;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.io.InputStream;

import org.webdriver.patatiumwebui.utils.Locator.ByType;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 *@author 700sfriend
 *@method getLocatorMap 确定外部数据文件
 *@method setXmlObjectPath 返回一个文件对象路径
 *@method getBy 枚举，返回findElement.by（方式）
 *@method getElement(Locator locator)  通过“查询的信息”，返回一个元素。
 *@method getLocator 从对象库获取“查询信息”
 *@method 查询元素的方法：getElement+Waitformax(时间等待)
 *@method Waitformax(int t) 等待机制
 *@method 浏览器基本操作方法
 */
public class BaseAction extends TestBaseCase{

	//protected 只在本包之内有权限，可访问
	//protected  WebDriver driver;
	// protected Log log;
	//定位信息图（对象库存储结构）
	protected HashMap<String,Locator>  locatorMap;
	public String path=null;
	public InputStream path_inputStream_1;
	public InputStream path_inputStream_2;
	

	/**
	 * 日志对象和方法
	 */
	Log log=new Log(this.getClass());
	
	/*返回一个文件对象的路径。*/
	public  void setXmlObjectPath(String path)
	{
		this.path=path;
	}
	
	/*返回一个文件对象的路径。*/
	public  void setXmlObjectPathForLocator(InputStream path_inputStream)
	{
		this.path_inputStream_1=path_inputStream;
	}
	
	/*返回一个文件对象的路径。*/
	public  void setXmlObjectPathForPageURL(InputStream path_inputStream)
	{
		this.path_inputStream_2=path_inputStream;
	}
	
	
	
	/**
	 * 构造方法，创建BasePageOpera对象，初始化的信息.传递相关参数
	 * @param driver
	 * @param path 对象库文件位置
	 * this.getClass().getCanonicalName() 获取page类路径，也就是xml文档中的pageName
	 * @throws Exception
	 */
	public  BaseAction()
	{
		
	}
	
	
	
	/**
	 * 返回一个map集合，这个集合包含从数据文件中获取的元素信息及属性，以键值对的形式存在。
	 * 需要制定文件的路径，元素参数的名称。
	 */
	public void getLocatorMap()
	{
		XmlReadUtil xmlReadUtil=new XmlReadUtil();
		try {
			if((path==null||path.isEmpty()))
			{locatorMap = xmlReadUtil.readXMLDocument(path_inputStream_1, this.getClass().getCanonicalName());}
			else {
				locatorMap = xmlReadUtil.readXMLDocument(path, this.getClass().getCanonicalName());
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	 /**
	  * @author 700sfriend
	  * 枚举，返回findElement.by（方式）。
	  * 
	  */
	static By getBy (ByType byType,Locator locator)
	{
		switch(byType)
		{
			case id:
				return By.id(locator.getElement());
			case cssSelector:
				return By.cssSelector(locator.getElement());
			case name:
				return By.name(locator.getElement());
			case xpath:
				return By.xpath(locator.getElement());
			case className:
				return By.className(locator.getElement());
			case tagName:
				return By.tagName(locator.getElement());
			case linkText:
				return By.linkText(locator.getElement());
			case partialLinkText:
				return By.partialLinkText(locator.getElement());
			//return null也可以放到switch外面
			default:
				return null;
		}
	}
	
	
	/**
	 * 通过“查询的信息”获取元素
	 * @param locator
	 * @return webElement
	 * @throws NoSuchElementException
	 */
	public WebElement getElement(Locator locator)
	{
		/**
		 * locator.getElement(),获取对象库对象定位信息
		 */
		//locator=getLocator(locatorMap.get(key));
		WebElement webElement;
		switch (locator.getBy())
		{
			case xpath :
				//log.info("find element By xpath");
				webElement=driver.findElement(By.xpath(locator.getElement()));
				/**
				 * 出现找不到元素的时候，记录日志文件
				 */
				break;
			case id:
				//log.info("find element By xpath");
				webElement=driver.findElement(By.id(locator.getElement()));
				break;
			case cssSelector:
				//log.info("find element By cssSelector");
				webElement=driver.findElement(By.cssSelector(locator.getElement()));
				break;
			case name:
				//log.info("find element By name");
				webElement=driver.findElement(By.name(locator.getElement()));
				break;
			case className:
				//log.info("find element By className");
				webElement=driver.findElement(By.className(locator.getElement()));
				break;
			case linkText:
				//log.info("find element By linkText");
				webElement=driver.findElement(By.linkText(locator.getElement()));
				break;
			case partialLinkText:
				//log.info("find element By partialLinkText");
				webElement=driver.findElement(By.partialLinkText(locator.getElement()));
				break;
			case tagName:
				//log.info("find element By tagName");
				webElement=driver.findElement(By.partialLinkText(locator.getElement()));
				break;
			default :
				//log.info("find element By xpath");
				webElement=driver.findElement(By.xpath(locator.getElement()));
				break;

		}
		return webElement;
	}
	
	

	/**
	 * 从对象库获取“查询信息”
	 * @param locatorName 对象库名字
	 * @return
	 * @throws IOException
	 */
	public  Locator getLocator(String locatorName)
	{
		Locator locator;
		/**
		 * 在对象库通过对象名字查找定位信息
		 */
		locator=locatorMap.get(locatorName);
		/**
		 * 加入对象库，找不到该定位信息，就创建一个定位信息
		 */
		if(locator==null)
		{
			log.error("没有找到"+locatorName+"页面元素");
		}
		return locator;

	}
	
	/*获取URL*/
	public String getPageURL()
	{
		String pageURL=null;
		try {
			if(path==null||path.isEmpty())
			{pageURL=XmlReadUtil.getXmlPageURL(path_inputStream_1, this.getClass().getCanonicalName());}
			else {
				pageURL=XmlReadUtil.getXmlPageURL(path, this.getClass().getCanonicalName());
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return pageURL;
	}

	/**
	 * 打开浏览器
	 * @param url
	 */
	public void open(String url)
	{
		driver.navigate().to(url);
		log.info("打开浏览器，访问"+url+"网址!");

	}
	/***
	 * 关闭浏览器窗口
	 */
	public void close()
	{
		driver.close();
		log.info("关闭浏览器窗口");
	}
	/**
	 * 退出浏览器
	 */
	public void quit()
	{
		driver.quit();
		log.info("退出浏览器");
	}
	/**
	 * 浏览器前进
	 */
	public void forward()
	{
		driver.navigate().forward();
		log.info("浏览器前进");
	}
	/**
	 * 浏览器后退
	 */
	public void back()
	{
		driver.navigate().back();
		log.info("浏览器后退");
	}
	/**
	 * 刷新浏览器
	 */
	public void refresh()
	{
		driver.navigate().refresh();
		log.info("浏览器刷新");
	}
	
	/**
	 * 查询元素的方法：getElement+Waitformax(时间等待)
	 * @param locator
	 * @return webElement
	 * @throws IOException
	 */
	public WebElement findElement( final Locator locator) throws IOException
	{	
		Waitformax(Integer.valueOf(locator.getWaitSec()));
		WebElement webElement;
		webElement=getElement(locator);
		return webElement;
	}
	
	
	/**
	 * 查找某个元素等待几秒
	 * 
	 */
	public void Waitformax(int t)
	{
		driver.manage().timeouts().implicitlyWait(t,TimeUnit.SECONDS);
	}
	


}
