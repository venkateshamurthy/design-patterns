package com.github.venkateshamurthy.designpatterns.builders.lombok;

import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Builder;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

/**
 * Type Example.
 * @author vemurthy
 *
 */
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log
public class Example {
    private int foo;    //This is private final
    private String bar; //This is private final
    
    /**
     * Type Builder for validating parameters.
     * @author vemurthy
     *
     */
    @Data(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Builder extends ExampleBuilder {
        private static final Pattern BAR_PATTERN = Pattern.compile("[a-zA-z0-9]+");
        @Override
        public ExampleBuilder bar(String bar) {
            if (bar == null || !BAR_PATTERN.matcher(bar).matches()) {
                throw new IllegalArgumentException("bar variable needs to follow pattern:" + BAR_PATTERN);
            }
            return super.bar(bar);
        }
        
        @Override
        public ExampleBuilder foo(int foo) {
            if (foo < 0) {
                throw new IllegalArgumentException("foo needs to be positive");
            }
            return super.foo(foo);
        }
        
        /* (non-Javadoc)
         * @see com.github.venkateshamurthy.designpatterns.builders.lombok.Example.ExampleBuilder#build()
         */
        /**{@inheritDoc}.*/
        public Example build() {
            log.info("Building with lombok...");
            return super.build();
        }
    }
    /**
     * Builder creator.
     * @return {@link Builder}
     */
    public static Builder builder() {
        return Builder.of();
    }
}
