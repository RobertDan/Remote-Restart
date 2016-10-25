package com.company;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Bobby on 9/20/2016.
 */
public class TokenManip {
    /*
    * *access methods:
    *public void setDefaultDelimiter(String delim)
    *public void setNewString(String userString)
    *public void setNewString(String userString, String delim)
    *public String returnString()
    *
    * * public methods:
    *public String get(int index)
    *public int find(String findString)
    *public void set(int index, String setValue)
    *public int size()
    *
    * * Known Issues:
    * When a string is submitted to the object, it tokenizes it by using String.split(), which uses
    * regex expressions to parse. This means that certain characters must be escaped in order to work.
    * As this was coded to work with | and $, both of which are special characters in regex, the delimiter(s)
    * are auto-escaped when given to the .split() method. This causes non-special characters to attempt to be escaped,
    * which regex won't recognize, and will read it as simply "\a", for example. A lookup table would be needed to
    * decide if the delimiter needed to be escaped, but is too time-consuming at this time to implement.
    */

    private String defaultDelimiter;
    private String storedString;
    private ArrayList<String> allTokens;

    //Constructor - default constructor sets default delimiter to "|", creates an empty string.
    public TokenManip()
    {
        setDefaultDelimiter("|");
        setNewString("");
        ArrayList<String> allTokens = new ArrayList<String>();
    }

    //Constructor - sets storedString to user specified String
    public TokenManip(String userString)
    {
        setDefaultDelimiter("|");
        setNewString(userString);
        //outdate- use buildTokenArrayList() method instead
        //ArrayList<String> allTokens = new ArrayList<String>(Arrays.asList(storedString.split(defaultDelimiter)));
        buildTokenArrayList();
    }

    //Constructor - sets storedString to user specified String, and the current delimiter
    public TokenManip(String userString, String delim)
    {
        setDefaultDelimiter(delim);
        setNewString(userString);
        buildTokenArrayList();
    }

    //Constructor - accepts a String ArrayList
    public TokenManip(ArrayList<String> array)
    {
        setDefaultDelimiter("|");
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<array.size(); i++)
        {
            sb.append(array);
            if(i != array.size()-1)
                sb.append("|");
        }
        setNewString(sb.toString());
        buildTokenArrayList();
    }

    //access method to private variable
    public void setDefaultDelimiter(String delim)
    {
        defaultDelimiter = delim;
    }

    //access method to private variable
    public void setNewString(String userString)
    {
        storedString = userString;
        buildTokenArrayList();
    }

    //access method to private variable, also changes the default delimiter at same time
    public void setNewString(String userString, String delim)
    {
        storedString = userString;
        defaultDelimiter = delim;
        buildTokenArrayList();
    }

    //access method for private variable
    public String returnString()
    {
        return storedString;
    }

    //method to rebuild ArrayList of storedString's tokens. Used internally only.
    private void buildTokenArrayList()
    {
        //if there is more than one delimiter, build the regex string before splitting.
        if(defaultDelimiter.length()>1)
        {
            //System.out.println(defaultDelimiter);
            //System.out.println(defaultDelimiter.charAt(0));
            //build a regex string to split on
            StringBuilder sb = new StringBuilder();
            sb.append("\\" + defaultDelimiter.charAt(0));
            for(int i=1; i<defaultDelimiter.length(); i++)
            {
                sb.append("\\");
                sb.append(defaultDelimiter.charAt(i));
            }
            //sb.append("]");
            //System.out.println(sb.toString());
            allTokens = new ArrayList<String>(Arrays.asList(storedString.split(sb.toString())));
        }
        else {
            allTokens = new ArrayList<String>(Arrays.asList(storedString.split("\\" + defaultDelimiter)));
        }
    }


    //returns the string at a specified index.
    public String get(int index)
    {
        if(index==-1)
            return null;
        return allTokens.get(index);
    }

    //search the token array for a specific string, return the first occurrence of that string. -1 if not found.
    public int find(String findString)
    {
        //declare default index value.
        int currentIndex = -1;

        //search for the specified token. if found, it overwrites the currentIndex variable.
        //if it doesn't find the token, then the variable won't get overwritten, and will stay default (-1).
        for(int i=0; i<allTokens.size();i++)
        {
            if(findString.equals(allTokens.get(i)))
            {
                currentIndex = i;
                break;
            }
        }

        //return the index of the specified token. if a token wasn't found, it returns the default -1.
        return currentIndex;
    }

    //search the token array for a specific prefix of a substring, return the first occurrence of that substring. -1 if not found.
    public int findSubstring(String findString)
    {
        //declare default index value.
        int currentIndex = -1;

        //search for the specified token. if found, it overwrites the currentIndex variable.
        //if it doesn't find the token, then the variable won't get overwritten, and will stay default (-1).
        for(int i=0; i<allTokens.size();i++)
        {
            if(allTokens.get(i).contains(findString))
            {
                currentIndex = i;
                break;
            }
        }

        //return the index of the specified token. if a token wasn't found, it returns the default -1.
        return currentIndex;
    }

    //replace the token at a given index with a new token
    public void set(int index, String setValue)
    {
        //replace the token at the specified index
        allTokens.set(index, setValue);

        //create StringBuilder to recompile the stored string
        StringBuilder sb = new StringBuilder();

        //recompile ArrayList back into single string
        sb.append(allTokens.get(0));
        for(int i=1; i<allTokens.size();i++)
        {
            sb.append(defaultDelimiter);
            sb.append(allTokens.get(i));
        }

        //replace the stored string with the newly built string
        setNewString(sb.toString());
    }

    public void delete(int index)
    {
        int target = 0;
        for(int i=0; i<index; i++)
        {
            target += get(i).length();
        }

        String firstPart = storedString.substring(0,target);
        String secondPart = storedString.substring(get(index).length()+1);

        storedString = firstPart + secondPart;
        buildTokenArrayList();
    }

    //returns the number of tokens in the currently stored string
    public int size()
    {
        return allTokens.size();
    }
}
