package ru.parada.app.json;

public class ParseException
	extends Exception
{
    public static final int ERROR_UNEXPECTED_CHAR = 0;
    public static final int ERROR_UNEXPECTED_TOKEN = 1;
    public static final int ERROR_UNEXPECTED_EXCEPTION = 2;

    private int errorType;
    private Object unexpectedObject;
    private int position;

    public ParseException(int position, int errorType, Object unexpectedObject)
    {
        this.position = position;
        this.errorType = errorType;
        this.unexpectedObject = unexpectedObject;
    }

    public String getMessage()
    {
        StringBuffer sb = new StringBuffer();
        switch(errorType)
        {
        case ERROR_UNEXPECTED_CHAR:
            sb.append("Unexpected character (").append(unexpectedObject).append(") at position ").append(position).append(".");
            break;
        case ERROR_UNEXPECTED_TOKEN:
            sb.append("Unexpected token ").append(unexpectedObject).append(" at position ").append(position).append(".");
            break;
        case ERROR_UNEXPECTED_EXCEPTION:
            sb.append("Unexpected exception at position ").append(position).append(": ").append(unexpectedObject);
            break;
        default:
            sb.append("Unkown error at position ").append(position).append(".");
            break;
        }
        return sb.toString();
    }
}