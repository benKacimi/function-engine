package org.rusteek.engine.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.rusteek.engine.lexer.execption.InvalidVariableSyntaxException;

class VariableCreateInstanceTest {
    @Test
    void testCreateInstanceWithNullLexem()
    {
        Variable aVar = new Variable();
        try {
            aVar.initInstance(null);
            assertTrue(false);
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(true);
        }
    }    
    
    @ParameterizedTest
    @ValueSource(strings = {""," ","   ","$","${","${}","${ }"})
    void testCreateVariableInstanceWithWrongSyntax(String str)
    {
        Variable aVar = new Variable();
    
        try {
            aVar.initInstance(str);
            assertTrue(false);
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(true);
        }
    }
   
    @ParameterizedTest
    @CsvSource({
        "${ aVar },' aVar '",
        "${aVariable},aVariable",
         "${foo.bar},foo.bar"
    })
    void testCreateSimpleVariableInstance(String str, String expectedKeyName)
    {
        Variable aVar = new Variable();
         try {
            aVar.initInstance(str);
            assertEquals(expectedKeyName,(aVar.getKeyName()));
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(false);
        }    
    } 
   
    @Test
    void testCreateVariableInvalidInstanceWithParenthesis()
    {
        String str = "${foo(bar}";
        Variable aVar = new Variable();
        try {
            aVar.initInstance(str);
            assertTrue(false);
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(true);
        }
    }   
    @Test
    void testCreateVariableInvalidInstanceWithSpaceIntoKeyName()
    {
        String str = "${foo bar}";
        Variable aVar = new Variable();
        try {
            aVar.initInstance(str);
            assertTrue(false);
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(true);
        }
    }      
    @ParameterizedTest
    @ValueSource(strings = {"${foo bar","${}","${ }","${}a"})
    void testValidateAnInValidVariable(String str)
    {
        boolean isValid =Variable.isAValidVariable(str);
        assertTrue(!isValid);
    }  
    @Test
    void testValidateAValidVariable()
    {
        String str = "${foo}";
        boolean isValid =Variable.isAValidVariable(str);
        assertTrue(isValid);
    }   
    @Test
    void testValidateAValidVariableWithOneChar()
    {
        String str = "${a}";
        boolean isValid =Variable.isAValidVariable(str);
        assertTrue(isValid);
    } 
    @Test
    void testNextLexem()
    {
        String lexem = "${foo}bar";
        Variable aVar = new Variable();
         try {
            aVar.initInstance(lexem);
            assertEquals("foo",(aVar.getKeyName()));
            String nextLexem = aVar.getNextLexem(lexem);
            assertEquals("bar",nextLexem);
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(false);
        }
    } 
}
