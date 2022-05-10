package oodp.STRATEGY.after;

public class Main {
  // mode 마다 동작 하나하나를 모듈로 따로 분리
  // 실행될 모듈을 갈아 끼움
  public static void main(String[] args) {
    MyProgram myProgram = new MyProgram();
    myProgram.testProgram();
  }
}
