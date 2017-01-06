package org.webdriver.patatiumwebui.utils;
/**
 * This is for element library
 * 定位信息：
 * @author zhengshuheng
 *
 */
public class Locator {
  private String element;
  private int waitSec;
  private String locatorName;
  /**
   * create a enum variable for By
   * 
   * @author zhengshuheng
   *
   */
  public enum ByType {
    xpath, id, linkText, name, className, cssSelector, partialLinkText, tagName
  }
  private ByType byType;
  public Locator() {
  }
  /**
   * defaut Locator ,use Xpath
   * 重构了四个locator的构造函数，所使用的参数都不同
   * 每个locator方法包含3大信息：元素名、等待时间、查询类型
   * getElement方法返回了元素名
   * getLocalorName方法返回了外部传入的“定位信息名”，区别于元素名
   * @author zhengshuheng
   * @param element
   */
  public Locator(String element) {
    this.element = element;
    this.waitSec = 3;
    this.byType = ByType.xpath;
  }
  public Locator(String element, int waitSec) {
    this.waitSec = waitSec;
    this.element = element;
    this.byType = ByType.xpath;
  }
  public Locator(String element, int waitSec, ByType byType) {
    this.waitSec = waitSec;
    this.element = element;
    this.byType = byType;
  }
  public Locator(String element, int waitSec, ByType byType,String locatorName) {
	    this.waitSec = waitSec;
	    this.element = element;
	    this.byType = byType;
	    this.locatorName=locatorName;
	  }
  public String getElement() {
    return element;
  }
  public int getWaitSec() {
    return waitSec;
  }
  
  /*获取查询类型*/
  public ByType getBy() {
    return byType;
  }
  
  /*设置查询类型*/
  public void setBy(ByType byType) {
    this.byType = byType;
  }
  
  /*获取外部传入的定位名称*/
  public String getLocalorName()
  {
	  return locatorName;
  }
}