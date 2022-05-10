package oodp.STRATEGY.before;

public class MyProgram {
  
  private SearchButton searchButton = new SearchButton(this);

  public Mode mode = Mode.ALL;
  public void setModeAll () {mode = Mode.ALL;}
  public void setModeImage () {mode = Mode.IMAGE;}
  public void setModeNews () {mode = Mode.NEWS;}
  public void setModeMap () {mode = Mode.MAP;}

}
