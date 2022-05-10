package oodp.SINGLETON.before;

public class MyProgram {
  public static void main(String[] args) {
    // 안드로이드는 페이지별로 생성
    // firstPage 의 settings != secondPage
    new FirstPage().setAndPrintSettings();
    new SecondPage().printSettings();
  }
}
