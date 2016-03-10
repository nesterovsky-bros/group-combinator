package com.nesterovskyBros;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

/**
 * A group combinator to build all matched group permutations.
 */
public class GroupCombinator
{
  /**
   * For a sequence of items builds a list of matching groups.
   * @param identity an identity instance used for the group.
   * @param items original sequence of items.
   * @param matcher a group matcher of item against a group.
   * @param combiner creates a new group from a group (optional) and an item.
   * @return a list of matching groups.
   */
  public static <T, G> List<G> matchingGroups(
    G identity,
    Iterable<T> items, 
    BiPredicate<G, T> matcher,
    BiFunction<G, T, G> combiner)
  {
    ArrayList<G> result = new ArrayList<>();
    
    for(T item: items)
    {
      int size = result.size();
      
      result.add(combiner.apply(identity, item));

      for(int i = 0; i < size; ++i)
      {
        G group = result.get(i);
        
        if (matcher.test(group, item))
        {
          result.add(combiner.apply(group, item));
        }
      }
    }
    
    return result;
  }

  /**
   * For a sequence of items builds a list of matching groups.
   * @param identity an identity instance used for the group.
   * @param items original sequence of items.
   * @param matcher an item matcher.
   * @return a list of matching groups.
   */
  public static <T> List<T[]> matchingGroups(
    T[] identity,
    Iterable<T> items, 
    BiPredicate<T, T> matcher)
  {
    return matchingGroups(
     identity,
     items, 
     (group, item) ->
     {
       for(T value: group)
       {
         if (!matcher.test(value, item))
         {
           return false;
         }
       }
       
       return true;
     },
     (group, item) ->
     {
       group = Arrays.copyOf(group, group.length + 1);
       group[group.length - 1] = item;

       return group;
     });
  }
}
