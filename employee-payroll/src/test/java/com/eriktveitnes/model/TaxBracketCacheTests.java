package com.eriktveitnes.model;

import org.junit.Test;

import java.util.NoSuchElementException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TaxBracketCacheTests {

    @Test
    public void getTaxBracketWithLowestValuePossible(){
        assertThat(TaxBracketCache.getTaxBracket(0d),
          allOf(hasProperty("lumpSum", closeTo(0, 0)),
                hasProperty("taxPercentage", closeTo(0, 0)),
                hasProperty("lowRange", closeTo(0, 0)),
                hasProperty("highRange", closeTo(18000, 18000))));
    }

    @Test
    public void getTaxBracketWithHighestValuePossible(){
        assertThat(TaxBracketCache.getTaxBracket(Double.MAX_VALUE),
          allOf(hasProperty("lumpSum", closeTo(54547, 54547)),
                hasProperty("taxPercentage", closeTo(0.45, 0.45)),
                hasProperty("lowRange", closeTo(180000, 180000)),
                hasProperty("highRange", closeTo(Double.MAX_VALUE, Double.MAX_VALUE))));
    }

    @Test
    public void getTaxBracketOnBoundary(){
        assertThat(TaxBracketCache.getTaxBracket(37000d),
                allOf(hasProperty("lumpSum", closeTo(0, 0)),
                        hasProperty("taxPercentage", closeTo(0.19, 0.19)),
                        hasProperty("lowRange", closeTo(18000, 18000)),
                        hasProperty("highRange", closeTo(37000, 37000))));
    }

    @Test(expected = NoSuchElementException.class)
    public void getTaxBracketWithInvalidNumber(){
        TaxBracketCache.getTaxBracket(-1);
    }
}
