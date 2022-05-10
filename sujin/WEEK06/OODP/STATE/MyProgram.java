package oodp.STATE;

import javax.naming.NameAlreadyBoundException;

public class MyProgram {
  
  public static void main(String[] args) {
    final ModeSwitch modeSwitch = new ModeSwitch();

    modeSwitch.onSwitch(); // FROM LIGHT TO DARK
    modeSwitch.onSwitch(); // FROM DARK TO LIGHT
    modeSwitch.onSwitch(); // FROM LIGHT TO DARK
    modeSwitch.onSwitch(); // FROM DARK TO LIGHT
  }
}
