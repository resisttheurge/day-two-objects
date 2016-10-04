package com.cooksys.ftd.assignments.day.two.objects;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.When;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assume.*;
import static org.junit.Assert.*;
import static com.cooksys.ftd.assignments.day.two.objects.SimplifiedRationalGenerator.*;
import static com.cooksys.ftd.assignments.day.two.objects.SimplifiedRational.*;

@RunWith(JUnitQuickcheck.class)
public class SimplifiedRationalProperties {
    @Rule
    ExpectedException thrown = ExpectedException.none();

    @Property
    public void gcdFailA(@When(satisfies = "#_ < 0") int a, @When(satisfies = "#_ >= 0") int b) {
        thrown.expect(IllegalArgumentException.class);
        gcd(a, b);
    }

    @Property
    public void gcdFailB(@When(satisfies = "#_ >= 0") int a, @When(satisfies = "#_ < 0") int b) {
        thrown.expect(IllegalArgumentException.class);
        gcd(a, b);
    }

    @Property
    public void gcdFail(@When(satisfies = "#_ < 0") int a, @When(satisfies = "#_ < 0") int b) {
        thrown.expect(IllegalArgumentException.class);
        gcd(a, b);
    }

    @Property
    public void gcdSuccess(@When(satisfies = "#_ >= 0") int a, @When(satisfies = "#_ >= 0") int b) {
        assertEquals(euclid(a, b), gcd(a, b));
    }

    @Property
    public void simplifyFail(int n) {
        thrown.expect(IllegalArgumentException.class);
        simplify(n, 0);
    }

    @Property
    public void simplifySuccess(int n, @When(satisfies = "#_ != 0") int d) {
        assertEquals(collapse(n, d), simplify(n, d));
    }

    @Property
    public void constructorFail(int n) {
        thrown.expect(IllegalArgumentException.class);
        new SimplifiedRational(n, 0);
    }

    @Property
    public void constructorSuccess(int n, @When(satisfies = "#_ != 0") int d) {
        int[] expected = collapse(n, d);

        SimplifiedRational r = new SimplifiedRational(n, d);
        assertEquals(expected[0], r.getNumerator());
        assertEquals(expected[1], r.getDenominator());
    }

    @Property
    public void constructFail(@GenSim SimplifiedRational r, int n) {
        thrown.expect(IllegalArgumentException.class);
        r.construct(n, 0);
    }

    @Property
    public void constructSuccess(@GenSim SimplifiedRational _r, int n, @When(satisfies = "#_ != 0") int d) {
        int[] expected = collapse(n, d);

        SimplifiedRational r = _r.construct(n, d);
        assertEquals(expected[0], r.getNumerator());
        assertEquals(expected[1], r.getDenominator());
    }

    @Property
    public void equals(@GenSim SimplifiedRational r1) {
        assertNotEquals(r1, 1);
        assertNotEquals(r1, "");
        assertNotEquals(r1, null);

        SimplifiedRational r2 = new SimplifiedRational(r1.getNumerator() * 2, r1.getDenominator() * 2);
        assertEquals(r1, r2);

        SimplifiedRational r3 = new SimplifiedRational(r2.getNumerator() * 3, r2.getDenominator() * 3);
        assertEquals(r2, r3);

        assertEquals(r3, r1);
    }

    @Property
    public void toString(@GenSim SimplifiedRational r) {
        int n = r.getNumerator();
        int d = r.getDenominator();

        assertEquals(n < 0 != d < 0 ? "-" : "" + n + "/" + d, r.toString());
    }

    @Property
    public void negate(@GenSim SimplifiedRational sr) {
        assertEquals(new SimplifiedRational(-sr.getNumerator(), -sr.getDenominator()), sr.negate());
    }

    @Property
    public void invertFail(@When(satisfies = "#_ != 0") int d) {
        thrown.expect(IllegalStateException.class);
        new SimplifiedRational(0, d).invert();
    }

    @Property
    public void invert(@GenSim SimplifiedRational sr) {
        assertEquals(new SimplifiedRational(sr.getDenominator(), sr.getNumerator()), sr.invert());
    }

    @Property
    public void addFail(@GenSim SimplifiedRational sr) {
        thrown.expect(IllegalArgumentException.class);
        sr.add(null);
    }

    @Property
    public void add(@GenSim SimplifiedRational r1, @GenSim SimplifiedRational r2) {
        int n1 = r1.getNumerator();
        int d1 = r1.getDenominator();
        int n2 = r2.getNumerator();
        int d2 = r2.getDenominator();
        assertEquals(new SimplifiedRational((n1 * d2) + (n2 * d1), d1 * d2), r1.add(r2));
    }

    @Property
    public void subFail(@GenSim SimplifiedRational sr) {
        thrown.expect(IllegalArgumentException.class);
        sr.sub(null);
    }

    @Property
    public void sub(@GenSim SimplifiedRational r1, @GenSim SimplifiedRational r2) {
        int n1 = r1.getNumerator();
        int d1 = r1.getDenominator();
        int n2 = r2.getNumerator();
        int d2 = r2.getDenominator();
        assertEquals(new SimplifiedRational((n1 * d2) - (n2 * d1), d1 * d2), r1.sub(r2));
    }

    @Property
    public void mulFail(@GenSim SimplifiedRational sr) {
        thrown.expect(IllegalArgumentException.class);
        sr.mul(null);
    }

    @Property
    public void mul(@GenSim SimplifiedRational r1, @GenSim SimplifiedRational r2) {
        int n1 = r1.getNumerator();
        int d1 = r1.getDenominator();
        int n2 = r2.getNumerator();
        int d2 = r2.getDenominator();
        assertEquals(new SimplifiedRational(n1 * n2, d1 * d2), r1.mul(r2));
    }

    @Property
    public void divFail(@GenSim SimplifiedRational sr) {
        thrown.expect(IllegalArgumentException.class);
        sr.div(null);
    }

    @Property
    public void divZeroFail(@GenSim SimplifiedRational sr) {
        thrown.expect(IllegalArgumentException.class);
        sr.div(new SimplifiedRational(0, 1));
    }

    @Property
    public void div(@GenSim SimplifiedRational r1, @When(satisfies = "#_ != 0") int n2, @When(satisfies = "#_ != 0") int d2) {
        int n1 = r1.getNumerator();
        int d1 = r1.getDenominator();
        assertEquals(new SimplifiedRational(n1 * d2, d1 * n2), r1.div(new SimplifiedRational(n2, d2)));
    }
}
