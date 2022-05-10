package oodp.ADAPTER.search;

interface FindAlgorithm {
  public void find(boolean global);
}

class FindMovieAlgorithm implements FindAlgorithm {
  public void find (boolean global){
    System.out.println(
      "find movie" + (global ? " globally": "")
    );
  }
}