package oodp.STRATEGY.before;

public class SearchButton {
  
  private MyProgram myProgram;

  public SearchButton(MyProgram _myProgram){
    myProgram = _myProgram;
  }

  public void onClick(){
    if (myProgram.mode == Mode.ALL){
      System.out.println("Search ALL");
      // 전체 검색하는 코드
    } else if (myProgram.mode == Mode.IMAGE) {
      System.out.println("Search IMAGE");
      // 이미지 검색하는 코드
    } else if (myProgram.mode == Mode.NEWS) {
      System.out.println("Search NEWS");
      // 뉴스 검색하는 코드
    } else if (myProgram.mode == Mode.MAP) {
      System.out.println("Search MAP");
      // 지도 검색하는 코드
    }
  }
}
