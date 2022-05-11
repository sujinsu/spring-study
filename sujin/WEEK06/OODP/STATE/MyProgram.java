package oodp.STATE;

import javax.naming.NameAlreadyBoundException;

public class MyProgram {
  // 특정 상태마다 할 일이 다름
  // 메서드가 실행될 때 모드도 전환된다
  public static void main(String[] args) {
    final ModeSwitch modeSwitch = new ModeSwitch();

    modeSwitch.onSwitch(); // FROM LIGHT TO DARK
    modeSwitch.onSwitch(); // FROM DARK TO LIGHT
    modeSwitch.onSwitch(); // FROM LIGHT TO DARK
    modeSwitch.onSwitch(); // FROM DARK TO LIGHT
  }
}
