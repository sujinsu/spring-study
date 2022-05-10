package oodp.ADAPTER.robot;
// 같은 로봇, 다른 로봇킷 >> 다른 사용자가 로봇이 뒤를 돌아 이동하게끔 자동화 명령어로 만들어 공유
// 1. command 라는 추상클래스 상속 X
// 2. Robot 을 내부 변수X, run 명령어에 인자로
public interface Order {
    public void run (Robot robot);
}

class MoveBackOrder implements Order {
    private int block;

    public MoveBackOrder(int _block){
        block = _block;
    }

    public void run (Robot robot){
        robot.turn(Robot.Direction.LEFT);
        robot.turn(Robot.Direction.LEFT);
        robot.moveForward(block);
    }
}
