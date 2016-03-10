package com.nesterovskyBros;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class GroupCombinatorTest
{
  public static class Rectangle
  {
    public Rectangle(int left, int top, int right, int bottom)
    {
      this.left = left;
      this.top = top;
      this.right = right;
      this.bottom = bottom;
    }
    
    public boolean intersects(Rectangle that)
    {
      return (right >= that.left) && 
        (left <= that.right) && 
        (bottom >= that.top) && 
        (top <= that.bottom);
    }
    
    public final int left;
    public final int top;
    public final int right;
    public final int bottom;
  }
  
  @Test
  public void test()
  {
    Rectangle[] bounds = new Rectangle[]
    {
      new Rectangle(0, 0, 80, 1),
      new Rectangle(0, 2, 80, 25),
      new Rectangle(0, 3, 80, 25)
    };
    
    List<Rectangle[]> groups = GroupCombinator.matchingGroups(
      new Rectangle[0],
      Arrays.asList(bounds), 
      (x, y) -> !x.intersects(y)); 
    
    assertEquals(groups.size(), 5);
  }
}
