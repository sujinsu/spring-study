package oodp.STRATEGY.after;

public class SearchButton {
  
  private MyProgram myProgram;

  public SearchButton(MyProgram _myProgram){
    myProgram = _myProgram;
  }

  // 생성시 전체 검색으로 초기화
  private SearchStrategy searchStrategy = new SearchStrategyAll();

  // setter 를 통해 다른 검색 전략으로 갈아끼울 수 있음
 public void setSearchStrategy(SearchStrategy _searchStrategy) {
    searchStrategy = _searchStrategy;
  }

  // 모든 전략은 search 메서드를 가짐
  public void onClick(){
    searchStrategy.search();
  }
}
