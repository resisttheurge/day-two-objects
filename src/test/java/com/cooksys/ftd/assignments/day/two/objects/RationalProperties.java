package com.cooksys.ftd.assignments.day.two.objects;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.When;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static com.cooksys.ftd.assignments.day.two.objects.RationalGenerator.GenRat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(JUnitQuickcheck.class)
public class RationalProperties {
    @Rule
    ExpectedException thrown = ExpectedException.none();

    @Property
    public void constructorFail(int n) {
        thrown.expect(IllegalArgumentException.class);
        new Rational(n, 0);
    }

    @Property
    public void constructorSuccess(int n, @When(satisfies = "#_ != 0") int d) {
        Rational r = new Rational(n, d);
        assertEquals(n, r.getNumerator());
        assertEquals(d, r.getDenominator());
    }

    @Property
    public void constructFail(@GenRat Rational r, int n) {
        thrown.expect(IllegalArgumentException.class);
        r.construct(n, 0);
    }

    @Property
    public void constructSuccess(@GenRat Rational _r, int n, @When(satisfies = "#_ != 0") int d) {
        Rational r = _r.construct(n, d);
        assertEquals(n, r.getNumerator());
        assertEquals(d, r.getDenominator());
    }

    @Property
    public void equals(@GenRat Rational r1) {
        assertNotEquals(r1, 1);
        assertNotEquals(r1, "");
        assertNotEquals(r1, null);

        Rational r2 = new Rational(r1.getNumerator(), r1.getDenominator());
        assertEquals(r1, r2);

        Rational r3 = new Rational(r1.getNumerator() + 1, r1.getDenominator());
        assertNotEquals(r1, r3);
    }

    @Property
    public void toString(@GenRat Rational r) {
        int n = r.getNumerator();
        int d = r.getDenominator();

        assertEquals(n < 0 != d < 0 ? "-" : "" + n + "/" + d, r.toString());
    }

    @Property
    public void negate(@GenRat Rational r) {
        assertEquals(new Rational(-r.getNumerator(), -r.getDenominator()), r.negate());
    }

    @Property
    public void invertFail(@When(satisfies = "#_ != 0") int d) {
        thrown.expect(IllegalStateException.class);
        new Rational(0, d).invert();
    }

    @Property
    public void invert(@GenRat Rational r) {
        assertEquals(new Rational(r.getDenominator(), r.getNumerator()), r.invert());
    }

    @Property
    public void addFail(@GenRat Rational r) {
        thrown.expect(IllegalArgumentException.class);
        r.add(null);
    }

    @Property
    public void add(@GenRat Rational r1, @GenRat Rational r2) {
        int n1 = r1.getNumerator();
        int d1 = r1.getDenominator();
        int n2 = r2.getNumerator();
        int d2 = r2.getDenominator();
        assertEquals(new Rational((n1 * d2) + (n2 * d1), d1 * d2), r1.add(r2));
    }

    @Property
    public void subFail(@GenRat Rational r) {
        thrown.expect(IllegalArgumentException.class);
        r.sub(null);
    }

    @Property
    public void sub(@GenRat Rational r1, @GenRat Rational r2) {
        int n1 = r1.getNumerator();
        int d1 = r1.getDenominator();
        int n2 = r2.getNumerator();
        int d2 = r2.getDenominator();
        assertEquals(new Rational((n1 * d2) - (n2 * d1), d1 * d2), r1.sub(r2));
    }

    @Property
    public void mulFail(@GenRat Rational r) {
        thrown.expect(IllegalArgumentException.class);
        r.mul(null);
    }

    @Property
    public void mul(@GenRat Rational r1, @GenRat Rational r2) {
        int n1 = r1.getNumerator();
        int d1 = r1.getDenominator();
        int n2 = r2.getNumerator();
        int d2 = r2.getDenominator();
        assertEquals(new Rational(n1 * n2, d1 * d2), r1.mul(r2));
    }

    @Property
    public void divFail(@GenRat Rational r) {
        thrown.expect(IllegalArgumentException.class);
        r.div(null);
    }

    @Property
    public void divZeroFail(@GenRat Rational r) {
        thrown.expect(IllegalArgumentException.class);
        r.div(new Rational(0, 1));
    }

    @Property
    public void div(@GenRat Rational r1, @When(satisfies = "#_ != 0") int n2, @When(satisfies = "#_ != 0") int d2) {
        int n1 = r1.getNumerator();
        int d1 = r1.getDenominator();
        assertEquals(new Rational(n1 * d2, d1 * n2), r1.div(new Rational(n2, d2)));
    }
}