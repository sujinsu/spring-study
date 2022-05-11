package oodp.SINGLETON.before;

public class SecondPage {
  
  private Settings settings = new Settings();

  public void printSettings (){
    System.out.println(settings.getDarkMode() + " " + settings.getFontSize());
  }
}
