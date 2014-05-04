package org.greenlaw110.atmsim.dispense;

import org.greenlaw110.atmsim.*;
import org.junit.Test;
import org.osgl._;
import org.osgl.util.C;
import org.osgl.util.N;

/**
 * Test ATM dispense with different Strategies
 */
public class StrategyTest extends TestBase {
    protected ATM atm;
    protected Bucket b20;
    protected Bucket b50;

    private void setup(DispenseStrategy strategy, int b20Cnt, int b50Cnt) {
        b20 = Bucket.of(NoteType.twentieth, b20Cnt);
        b50 = Bucket.of(NoteType.fiftieth, b50Cnt);
        atm = new ATM(C.list(b20, b50), strategy);
        b20 = atm._byType(NoteType.twentieth);
        b50 = atm._byType(NoteType.fiftieth);
    }

    @Test
    public void testBigNoteFirst() throws NoteDispenseException {
        setup(BigNoteFirst.INSTANCE, 1000, 1000);

        int b20Cnt1 = b20.noteCount(), b50Cnt1 = b50.noteCount();
        atm.dispense(100);
        atm.dispense(100);
        int b20Cnt2 = b20.noteCount(), b50Cnt2 = b50.noteCount();
        yes(b20Cnt1 == b20Cnt2 && b50Cnt1 != b50Cnt2);
    }

    @Test
    public void testBalancedNoteCount() throws NoteDispenseException {
        setup(BalancedNoteCount.INSTANCE, 1000, 1000);

        int b20Cnt1 = b20.noteCount(), b50Cnt1 = b50.noteCount();
        atm.dispense(100);
        atm.dispense(100);
        int b20Cnt2 = b20.noteCount(), b50Cnt2 = b50.noteCount();
        yes(b20Cnt1 != b20Cnt2 && b50Cnt1 != b50Cnt2);

        setup(BalancedNoteCount.INSTANCE, 100, 40);
        atm.dispense(150);
        eq(b20.noteCount(), 100 - 5);
        eq(b50.noteCount(), 40 -1);
    }

    @Test
    public void testBalancedValue() throws NoteDispenseException {
        setup(BalancedValue.INSTANCE, 10000 / 20, 10000 / 50);

        int b20Cnt1 = b20.noteCount(), b50Cnt1 = b50.noteCount();
        atm.dispense(100);
        atm.dispense(100);
        int b20Cnt2 = b20.noteCount(), b50Cnt2 = b50.noteCount();
        yes(b20Cnt1 != b20Cnt2 && b50Cnt1 != b50Cnt2);
    }

    /**
     * This will give out an ATM configuration and a sequence
     * of dispense value to makes the ATM failed to dispense
     * at last while the same configuration and dispense
     * sequence should be able to meet with
     * {@link org.greenlaw110.atmsim.dispense.BalancedNoteCount} strategies
     */
    @Test(expected = NoteDispenseException.class)
    public void testBigNoteFirstFail() throws NoteDispenseException {
        setup(BigNoteFirst.INSTANCE, 10, 10);
        C.List<Integer> sequence = C.list(400, 100, 150);
        for (Integer i : sequence) {
            atm.dispense(i);
        }
    }

    /**
     * The same combination won't fail in balanced notes count strategy
     *
     * @see #testBigNoteFirstFail()
     */
    @Test
    public void testBalancedNoteCountSuccess() throws NoteDispenseException {
        setup(BalancedNoteCount.INSTANCE, 10, 10);
        C.List<Integer> sequence = C.list(400, 100, 150);
        for (Integer i : sequence) {
            atm.dispense(i);
        }
    }

    /**
     * The same combination won't fail in balanced value strategy
     *
     * @see #testBigNoteFirstFail()
     */
    @Test
    public void testBalancedValueSuccess() throws NoteDispenseException {
        setup(BalancedValue.INSTANCE, 10, 10);
        C.List<Integer> sequence = C.list(400, 100, 150);
        for (Integer i : sequence) {
            atm.dispense(i);
        }
    }

    /**
     * Monkey test prepare an ATM with relatively big amount of notes of
     * both $20 and $50. And then randomly generate 1000 dispense sequence
     * with each of them are combination of multiplication of $20 and $50
     */
    private void monkeyTest(DispenseStrategy strategy) throws NoteDispenseException {
        int MAX_VALUE = Integer.MAX_VALUE;
        setup(strategy, MAX_VALUE / (2 * 20), MAX_VALUE / (2 * 50));
        for (int i = 0; i < 10000; ++i) {
            int t2 = _.random(C.range(0, 250)), t5 = _.random(C.range(0, 100));
            int value = t2 * 20 + t5 * 50;
            int atmValue = atm.value();
            C.List<Bucket> cash = C.list(atm.dispense(value));
            eq(cash.reduce(0, N.F.adder(Bucket.F.VALUE_OF, Integer.class)), value);
            eq(atmValue, atm.value() + value);
        }
    }

    @Test
    public void monkeyTestBigNoteFirst() throws NoteDispenseException {
        monkeyTest(BigNoteFirst.INSTANCE);
    }

    @Test
    public void monkeyTestBalancedNoteCount() throws NoteDispenseException {
        monkeyTest(BalancedNoteCount.INSTANCE);
    }

    @Test
    public void monkeyTestBalancedValue() throws NoteDispenseException {
        monkeyTest(BalancedValue.INSTANCE);
    }

    @Test
    public void bigMonkeyTest() throws NoteDispenseException {
        for (int i = 0; i < 20; ++i) {
            monkeyTestBigNoteFirst();
            monkeyTestBalancedNoteCount();
            monkeyTestBalancedValue();
        }
    }

    /**
     * This failure tests captured for BigNoteFirst strategy
     *
     * @throws NoteDispenseException
     */
    @Test
    public void BigNoteFirstIssueCases() throws NoteDispenseException {
        setup(BigNoteFirst.INSTANCE, Integer.MAX_VALUE / (2 * 20), Integer.MAX_VALUE / (2 * 50));
        atm.dispense(20 * 219 + 50 * 41);
    }

    /**
     * This failure tests captured for BalancedNoteCount strategy
     *
     * @throws NoteDispenseException
     */
    @Test
    public void BalancedNoteCountIssueCases() throws NoteDispenseException {
        setup(BigNoteFirst.INSTANCE, 20, 9);
        atm.dispense(20 * 1 + 50 * 10);
        setup(BalancedNoteCount.INSTANCE, 100, 40);
        atm.dispense(20 * 87 + 50 * 40);
        setup(BalancedNoteCount.INSTANCE, 100, 100);
        atm.dispense(20 * 114 + 50 * 1);
    }

    /**
     * This failure tests captured for BalancedValue strategy
     *
     * @throws NoteDispenseException
     */
    @Test
    public void BalancedValueIssueCases() throws NoteDispenseException {
        setup(BalancedValue.INSTANCE, Integer.MAX_VALUE / (2 * 20), Integer.MAX_VALUE / (2 * 50));
        atm.dispense(7670);
    }

}
