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

public class SimplifiedRationalGenerator extends Generator<SimplifiedRational> {

    public static int euclid(int n, int m) {
        while (m != 0) {
            int t = m;
            m = n % m;
            n = t;
        }
        return n;
    }

    public static int[] collapse(int t, int b) {
        int e = euclid(Math.abs(t), Math.abs(b));
        return new int[] { t / e, b / e};
    }

    @Target({PARAMETER, FIELD, ANNOTATION_TYPE, TYPE_USE})
    @Retention(RUNTIME)
    @From(SimplifiedRationalGenerator.class)
    public @interface GenSim {}

    protected SimplifiedRationalGenerator(Class<SimplifiedRational> type) {
        super(type);
    }

    protected SimplifiedRationalGenerator(List<Class<SimplifiedRational>> types) {
        super(types);
    }

    @Override
    public SimplifiedRational generate(SourceOfRandomness random, GenerationStatus status) {
        int n = random.nextInt();
        int d;
        do {
            d = random.nextInt();
        } while (d == 0);
        return new SimplifiedRational(n, d);
    }
}
