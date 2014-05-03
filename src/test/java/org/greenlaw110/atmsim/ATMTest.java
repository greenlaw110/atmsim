package org.greenlaw110.atmsim;

import org.junit.Before;
import org.junit.Test;
import org.osgl.util.C;
import org.osgl.util.N;

import java.util.List;

/**
 * Unit Test ATM include simple test cases of atm dispense.
 *
 * <p>For more sophisticated dispense test case please
 * check out {@link org.greenlaw110.atmsim.dispense.StrategyTest}</p>
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
    public void testGetBucketView() throws NoteDispenseException {
        atm.dispense(100);
        List<BucketView> l = atm.buckets();
        eq(l.toString(), "[20th X 3 = 60, 50th X 2 = 100]");
    }

    @Test
    public void testDispenseNoteTypeValues() {
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

    @Test
    public void testDispenseValueOfATM() {
        try {
            int v = preparedValue();
            List<Bucket> buckets = atm.dispense(v);
            yes(valuesOf(buckets) == v);
            yes(atm.value() == 0);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testFailWithIllegalCombination() {
        try {
            atm.dispense(30);
            fail();
        } catch (NoteDispenseException e) {
            yes(atm.value() == preparedValue());
        }
    }

    @Test
    public void testFailWithTooLargeAmount() {
        try {
            atm.dispense(10000);
            fail();
        } catch (NoteDispenseException e) {
            yes(atm.value() == preparedValue());
        }
    }
}
