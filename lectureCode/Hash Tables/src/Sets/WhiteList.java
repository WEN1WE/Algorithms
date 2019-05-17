package Sets;

import HashFunction.String;

/** Exception filter
 *    Read in a list of words from one file.
 *    Print out all words from standard input that are in the list.
 */

public class WhiteList {
    public static void main(String[] args) {
        SET<String> set = new SET<String>();
        In in = new In(args[0]);
        while (!in.isEmpty())
            set.add(in.readString());
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            if (set.contains(word))
                StdOut.println(word);
        }
    }
}