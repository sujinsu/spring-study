package oodp.ADAPTER.search;

// 원래 쓰던 SearchStrategy 인터페이스의 search 메서드와 이름, 형식 다름 & 글로벌 검색 여부 넣어줘야함
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