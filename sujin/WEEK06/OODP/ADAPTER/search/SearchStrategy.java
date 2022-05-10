package oodp.ADAPTER.search;

interface SearchStrategy {
  public void search();
}

class SearchStrategyAll implements SearchStrategy{
  public void search (){
    System.out.println("SEARCH ALL");
  }
}

class SearchStrategyImage implements SearchStrategy{
  public void search (){
    System.out.println("SEARCH IMAGE");
  }
}


class SearchStrategyNews implements SearchStrategy{
  public void search (){
    System.out.println("SEARCH NEWS");
  }
}


class SearchStrategyMap implements SearchStrategy{
  public void search (){
    System.out.println("SEARCH MAP");
  }
}

class SearchFindAdapter implements SearchStrategy {
  private FindAlgorithm findAlgorithm;

  public SearchFindAdapter(FindAlgorithm _findAlgorithm){
    findAlgorithm = _findAlgorithm;
  }

  public void search(){
    findAlgorithm.find(true);
  }
}

