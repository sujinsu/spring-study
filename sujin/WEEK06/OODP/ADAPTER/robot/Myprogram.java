package oodp.ADAPTER.robot;


public class Myprogram {
    public static void main(String[] args) {
        Robotkit robotkit = new Robotkit();

        robotkit.addCommand(new MoveForwardCommand(2));
        robotkit.addCommand(new TurnCommand(Robot.Direction.LEFT));
        robotkit.addCommand(new MoveForwardCommand(1));
        robotkit.addCommand(new TurnCommand(Robot.Direction.RIGHT));
        robotkit.addCommand(new PickupCommand());

        robotkit.addCommand(
                new CommandOrderAdapter(
                        new MoveBackOrder(1)
                )
        );
        robotkit.start();
//        2칸 전진
//        왼쪽으로 방향 전환
//        1칸 전진
//        오른쪽 으로 방향 전환
//        앞의 물건 집어들기
//        왼쪽으로 방향 전환
//        왼쪽으로 방향 전환
//        1칸 전진
    }
}
