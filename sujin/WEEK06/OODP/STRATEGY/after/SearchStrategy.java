package oodp.STRATEGY.after;

interface SearchStrategy {
  public void search();
}

class SearchStrategyAll implements SearchStrategy {
  public void search () {
    System.out.println("Search ALL");
  }
}

class SearchStrategyImage implements SearchStrategy {
  public void search () {
    System.out.println("Search Image");
  }
}

class SearchStrategyNews implements SearchStrategy {
  public void search () {
    System.out.println("Search News");
  }
}

class SearchStrategyMap implements SearchStrategy {
  public void search () {
    System.out.println("Search Map");
  }
}