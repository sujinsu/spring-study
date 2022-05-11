package oodp.COMMAND;

public class MyProgram {
  // 전략패턴은 비슷한 일이지만 알고리즘 or 방식을 갈아끼우는 반면
  // 커맨드 패턴은 하는 일 자체가 다름
  // So, 사용방식 다양
  public static void main(String[] args) {
    RobotKit robotKit = new RobotKit();

    robotKit.addCommand(new MoveForwardCommand(2));
    robotKit.addCommand(new TurnCommand(Robot.Direction.LEFT));
    robotKit.addCommand(new MoveForwardCommand(1));
    robotKit.addCommand(new TurnCommand(Robot.Direction.RIGHT));
    robotKit.addCommand(new PickupCommand());

    robotKit.start();
  }
}
