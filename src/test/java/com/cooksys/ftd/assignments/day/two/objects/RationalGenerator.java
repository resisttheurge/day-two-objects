package com.cooksys.ftd.assignments.day.two.objects;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class RationalGenerator extends Generator<Rational> {

    @Target({PARAMETER, FIELD, ANNOTATION_TYPE, TYPE_USE})
    @Retention(RUNTIME)
    @From(RationalGenerator.class)
    public @interface GenRat {}

    protected RationalGenerator(Class<Rational> type) {
        super(type);
    }

    protected RationalGenerator(List<Class<Rational>> types) {
        super(types);
    }

    @Override
    public Rational generate(SourceOfRandomness random, GenerationStatus status) {
        int n = random.nextInt();
        ;
        int d;
        do {
            d = random.nextInt();
        } while (d == 0);
        return new Rational(n, d);
    }
}
