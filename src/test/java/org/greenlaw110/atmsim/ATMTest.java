package org.greenlaw110.atmsim;

import org.junit.Before;
import org.junit.Test;
import org.osgl.util.C;
import org.osgl.util.N;

import java.util.List;

/**
 * Unit Test ATM
 */
public class ATMTest extends TestBase {
    protected ATM atm;
    protected Bucket twentieth;
    protected Bucket fiftieth;

    protected C.List<Bucket> buckets() {
        return C.list(twentieth, fiftieth);
    }

    @Before
    public void setUp() {
        twentieth = Bucket.of(NoteType.twentieth, 3);
        fiftieth = Bucket.of(NoteType.fiftieth, 4);
        atm = new ATM(buckets());
    }

    protected int preparedValue() {
        return 3 * 20 + 4 * 50;
    }

    protected int valuesOf(List<Bucket> buckets) {
        return C.list(buckets).reduce(0, N.F.adder(Bucket.F.VALUE_OF, Integer.class));
    }

    @Test
    public void testConstructWithBuckets() {
        yes(atm.value() == preparedValue());
    }

    @Test
    public void testSuccess0() {
        try {
            for (NoteType type: NoteType.values()) {
                setUp();
                int v = type.value();
                List<Bucket> buckets = atm.dispense(v);
                yes(valuesOf(buckets) == v);
                yes(atm.value() == preparedValue() - v);
            }
        } catch (Exception e) {
            fail();
        }
    }

    public void testSuccess1() {
        try {
            int v = preparedValue();
            List<Bucket> buckets = atm.dispense(v);
            yes(valuesOf(buckets) == v);
            yes(atm.value() == 0);
        } catch (Exception e) {
            fail();
        }
    }

    public void testSuccess2() {
        try {
            List<Bucket> buckets = atm.dispense(100);
            yes(buckets.size() == 1);
            yes(valuesOf(buckets) == 100);
            yes(atm.value() == preparedValue() - 100);

            int remaining = atm.value();

            buckets = atm.dispense(40);
            yes(valuesOf(buckets) == 40);
            yes(atm.value() == remaining - 40);

            remaining = atm.value();
            buckets = atm.dispense(70);
            yes(valuesOf(buckets) == 70);
            yes(atm.value() == remaining - 70);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testSuccess3() {
        try {
            List<Bucket> buckets = atm.dispense(140);
            yes(valuesOf(buckets) == 140);
            yes(atm.value() == preparedValue() - 140);
        } catch (NoteDispenseException e) {
            fail();
        }

        try {
            atm.dispense(preparedValue() - 140 - 1);
            fail("There should be an NoteDispenseException thrown out");
        } catch (NoteDispenseException e) {
            yes(atm.value() == preparedValue() - 140);
        }
    }

    @Test
    public void testFail1() {
        try {
            atm.dispense(30);
            fail();
        } catch (NoteDispenseException e) {
            yes(atm.value() == preparedValue());
        }

        // make sure the max value check is still working
        try {
            atm.dispense(preparedValue());
        } catch (NoteDispenseException e) {
            fail();
        }
    }

    @Test
    public void testFail2() {
        try {
            atm.dispense(10000);
            fail();
        } catch (NoteDispenseException e) {
            yes(atm.value() == preparedValue());
        }
    }
}
