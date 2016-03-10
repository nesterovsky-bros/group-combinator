<h2>group-combinator</h2>
<p>Our recent task required us to find all sets of not intersecting rectangles for a rectangle list.</p>
<p>At first glance it did not look like a trivial task. Just consider that for a list of <code>N</code> rectangles you can form 
<code>2^N</code> different subsets. So, even result list, theoretically, can be enormous.</p>
<p>Fortunately, we knew that our result will be manageable in size. But nevertheless, suppose you have a list of 
couple of hundred rectangles, how would you enumerate all different sets of rectangles?</p>
<p>We didn't even dare to think of brute-force solution: to enumerate all sets and then check each one whether it fits our needs.</p>
<p>Instead we used induction:</p>
<ul>
  <li>Suppose S(N) - is an solution for our task for N rectangles R(n), where S(N) is a set of sets of rectangles;</li>
  <li>Then solution for S(N+1) will contain whole S(N), R(N+1) - a set consisting of single rectangle, and 
  some sets of rectangles from S(N) combinded with R(N+1) provided they fit the condition;</li>
  <li>S(0) - is an empty set.</li>
</ul>
<p>The algorithm was implemented in java, and at first it was using
<a href="http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html">Streaming</a> and recursion.</p>
<p>Then we have figured out that we can use 
<a href="https://docs.oracle.com/javase/tutorial/collections/streams/reduction.html">Stream.reduce or Stream.collect</a> to implement
the same algorithm. That second implementation was a little bit longer but probably faster, and besides it used standard idioms.</p>
<p>But then at last step we reformulated the algorithms in terms of
<a href="https://docs.oracle.com/javase/tutorial/collections/">Collections</a>.</p>
<p>Though the final implementation is the least similar to original induction algorithm, 
it's straightforward and definitely fastest among all implementations we tried.</p>
<p>So, here is the code:</p>
<blockquote><pre>/**
 * For a sequence of items builds a list of matching groups.
 * @param identity and identity instance used for the group.
 * @param items original sequence of items.
 * @param matcher a group matcher of item against a group.
 * @param combiner creates a new group from a group (optional) and an item.
 * @return a list of matching groups.
 */
public static &lt;T, G> List&lt;G> matchingGroups(
  G identity,
  Iterable&lt;T> items, 
  BiPredicate&lt;G, T> matcher,
  BiFunction&lt;G, T, G> combiner)
{
  ArrayList&lt;G> result = new ArrayList<>();
  
  for(T item: items)
  {
    int size = result.size();
    
    result.add(combiner.apply(identity, item));
   
    for(int i = 0; i &lt; size; ++i)
    {
      G group = result.get(i);
      
      if (matcher.test(group, item))
      {
        result.add(combiner.apply(group, item));
      }
    }
  }
    
  return result;
}</pre></blockquote>

<p>This sample project contains implementation and a tests of this algorithm.</p>
