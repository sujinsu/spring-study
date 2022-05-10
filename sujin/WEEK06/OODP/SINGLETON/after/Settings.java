package oodp.SINGLETON.after;

public class Settings {
  // 생성자를 private >> new 로 생성 불가
  private Settings (){};
  // static >> 정적 공간 차지

  private static Settings settings = null;
  // 이미 존재한다면 새로 생성 X
  public static Settings getSettings (){
    if (settings == null){
      settings = new Settings();
    }
    return settings;
  }

  private boolean darkMode = false;
  private int fontSize = 13;

  public boolean getDarkMode(){return darkMode;}
  public int getFontSize(){return fontSize;}

  public void setDarkMode(boolean _darkMode){
    darkMode = _darkMode;
  }
  public void setFontSize(int _fontSize){
    fontSize = _fontSize;
  }
}
