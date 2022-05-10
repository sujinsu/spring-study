package oodp.ADAPTER.search;

public class MyProgram {
  private SearchButton searchButton = new SearchButton(this);

  public void setModeAll (){
      searchButton.setSearchStrategy(new SearchStrategyAll());
  }

  public void setModeImage (){
      searchButton.setSearchStrategy(new SearchStrategyImage());
  }

  public void setModeNews (){
      searchButton.setSearchStrategy(new SearchStrategyNews());
  }

  public void setModeMap (){
      searchButton.setSearchStrategy(new SearchStrategyMap());
  }

  // 어댑터에 FindMovieAlgorithm 객체 넣어주어 어댑터 사용
  public void setModeMovie (){
      searchButton.setSearchStrategy(
              new SearchFindAdapter(new FindMovieAlgorithm())
      );
  }

  public void testProgram (){
      searchButton.onClick();
      setModeImage();
      searchButton.onClick();
      setModeNews();
      searchButton.onClick();
      setModeMap();
      searchButton.onClick();
      setModeMovie();
      searchButton.onClick();
  }
}



