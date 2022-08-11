package org.accelerate.tool.interpreter.rules.engine.lexer;

import java.util.regex.Pattern;

public abstract class AbstractFunction  implements INode {

    private static final Pattern FUNCTION_NAME_REGEX = Pattern.compile("^[a-zA-Z0-9]+$");
    
    protected AbstractFunction(){}

    protected static String calculateAnnotationFunctionName(final String phrase) {
        if(phrase == null)
            return "";
        int pointIndex = phrase.indexOf(".");
        String calculatedClassName = "";
        if (pointIndex != -1){
            calculatedClassName = phrase.substring(1, pointIndex);
            if (null == calculatedClassName || "".equals(calculatedClassName))
                return "";   
            if (checkFunctionNameSyntax(calculatedClassName))
                return calculatedClassName;
        }
        return "";
    }

    protected static String calculateFunctionName(final String phrase){
        if(phrase == null)
            return null;
        
        int openingParenthesisIndex = phrase.indexOf("(");
        String calculatedName = null;
        if (openingParenthesisIndex != -1){
            calculatedName= phrase.substring(1, openingParenthesisIndex);
            if (null == calculatedName || "".equals(calculatedName))
                return null;
            else 
                calculatedName = calculatedName.trim();
            if (checkFunctionNameSyntax(calculatedName))
                return calculatedName;
        }
        return null;
    }
    private static boolean checkFunctionNameSyntax(final String functionName){
        if (functionName == null || "".equals(functionName))
            return false;
        return  (FUNCTION_NAME_REGEX.matcher(functionName)).matches() ;
    }
}
