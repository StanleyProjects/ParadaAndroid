package ru.parada.app.json;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JSONParser
{
    static public JSONParser newParser()
    {
        return new JSONParser();
    }

    static public final int S_INIT = 0;
    static public final int S_IN_FINISHED_VALUE = 1;
    static public final int S_IN_OBJECT = 2;
    static public final int S_IN_ARRAY = 3;
    static public final int S_PASSED_PAIR_KEY = 4;
    static public final int S_IN_PAIR_VALUE = 5;
    static public final int S_END = 6;
    static public final int S_IN_ERROR = -1;

    private LinkedList handlerStatusStack;
    private Yylex lexer = new Yylex((Reader)null);
    private Yytoken token = null;
    private int status = S_INIT;

    private JSONParser()
    {

    }

    private int peekStatus(LinkedList statusStack)
    {
        if(statusStack.size() == 0)
            return -1;
        Integer status = (Integer)statusStack.getFirst();
        return status.intValue();
    }
    public void reset(Reader in)
    {
        lexer.yyreset(in);
        token = null;
        status = S_INIT;
        handlerStatusStack = null;
    }

    public Object parse(String s) throws IOException, ParseException
    {
        reset(new StringReader(s));
        LinkedList statusStack = new LinkedList();
        LinkedList valueStack = new LinkedList();
        try
        {
            do
            {
                nextToken();
                switch(status)
                {
                case S_INIT:
                    switch(token.type)
                    {
                    case Yytoken.TYPE_VALUE:
                        status = S_IN_FINISHED_VALUE;
                        statusStack.addFirst(new Integer(status));
                        valueStack.addFirst(token.value);
                        break;
                    case Yytoken.TYPE_LEFT_BRACE:
                        status = S_IN_OBJECT;
                        statusStack.addFirst(new Integer(status));
                        valueStack.addFirst(new HashMap());
                        break;
                    case Yytoken.TYPE_LEFT_SQUARE:
                        status = S_IN_ARRAY;
                        statusStack.addFirst(new Integer(status));
                        valueStack.addFirst(new ArrayList());
                        break;
                    default:
                        status = S_IN_ERROR;
                    }//inner switch
                    break;
                case S_IN_FINISHED_VALUE:
                    if(token.type == Yytoken.TYPE_EOF)
                        return valueStack.removeFirst();
                    else
                        throw new ParseException(getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
                case S_IN_OBJECT:
                    switch(token.type)
                    {
                    case Yytoken.TYPE_COMMA:
                        break;
                    case Yytoken.TYPE_VALUE:
                        if(token.value instanceof String)
                        {
                            String key = (String)token.value;
                            valueStack.addFirst(key);
                            status = S_PASSED_PAIR_KEY;
                            statusStack.addFirst(new Integer(status));
                        }
                        else
                        {
                            status = S_IN_ERROR;
                        }
                        break;
                    case Yytoken.TYPE_RIGHT_BRACE:
                        if(valueStack.size() > 1)
                        {
                            statusStack.removeFirst();
                            valueStack.removeFirst();
                            status = peekStatus(statusStack);
                        }
                        else
                        {
                            status = S_IN_FINISHED_VALUE;
                        }
                        break;
                    default:
                        status = S_IN_ERROR;
                        break;
                    }//inner switch
                    break;
                case S_PASSED_PAIR_KEY:
                    switch(token.type)
                    {
                    case Yytoken.TYPE_COLON:
                        break;
                    case Yytoken.TYPE_VALUE:
                        statusStack.removeFirst();
                        String key = (String)valueStack.removeFirst();
                        Map parent = (Map)valueStack.getFirst();
                        parent.put(key, token.value);
                        status = peekStatus(statusStack);
                        break;
                    case Yytoken.TYPE_LEFT_SQUARE:
                        statusStack.removeFirst();
                        key = (String)valueStack.removeFirst();
                        parent = (Map)valueStack.getFirst();
                        List newArray = new ArrayList();
                        parent.put(key, newArray);
                        status = S_IN_ARRAY;
                        statusStack.addFirst(new Integer(status));
                        valueStack.addFirst(newArray);
                        break;
                    case Yytoken.TYPE_LEFT_BRACE:
                        statusStack.removeFirst();
                        key = (String)valueStack.removeFirst();
                        parent = (Map)valueStack.getFirst();
                        Map newObject = new HashMap();
                        parent.put(key, newObject);
                        status = S_IN_OBJECT;
                        statusStack.addFirst(new Integer(status));
                        valueStack.addFirst(newObject);
                        break;
                    default:
                        status = S_IN_ERROR;
                    }
                    break;
                case S_IN_ARRAY:
                    switch(token.type)
                    {
                    case Yytoken.TYPE_COMMA:
                        break;
                    case Yytoken.TYPE_VALUE:
                        List val = (List)valueStack.getFirst();
                        val.add(token.value);
                        break;
                    case Yytoken.TYPE_RIGHT_SQUARE:
                        if(valueStack.size() > 1)
                        {
                            statusStack.removeFirst();
                            valueStack.removeFirst();
                            status = peekStatus(statusStack);
                        }
                        else
                        {
                            status = S_IN_FINISHED_VALUE;
                        }
                        break;
                    case Yytoken.TYPE_LEFT_BRACE:
                        val = (List)valueStack.getFirst();
                        Map newObject = new HashMap();
                        val.add(newObject);
                        status = S_IN_OBJECT;
                        statusStack.addFirst(new Integer(status));
                        valueStack.addFirst(newObject);
                        break;
                    case Yytoken.TYPE_LEFT_SQUARE:
                        val = (List)valueStack.getFirst();
                        List newArray = new ArrayList();
                        val.add(newArray);
                        status = S_IN_ARRAY;
                        statusStack.addFirst(new Integer(status));
                        valueStack.addFirst(newArray);
                        break;
                    default:
                        status = S_IN_ERROR;
                    }//inner switch
                    break;
                case S_IN_ERROR:
                    throw new ParseException(getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
                }//switch
                if(status == S_IN_ERROR)
                {
                    throw new ParseException(getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
                }
            }
            while(token.type != Yytoken.TYPE_EOF);
        }
        catch(IOException ie)
        {
            throw ie;
        }
        throw new ParseException(getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
    }

    private void nextToken() throws ParseException, IOException
    {
        token = lexer.yylex();
        if(token == null)
            token = new Yytoken(Yytoken.TYPE_EOF, null);
    }
    public int getPosition()
    {
        return lexer.getPosition();
    }
}