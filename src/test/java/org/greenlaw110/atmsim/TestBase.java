package org.greenlaw110.atmsim;

import org.junit.Assert;
import org.junit.runner.JUnitCore;
import org.osgl._;
import org.osgl.util.S;

/**
 * Provides unit test utility methods
 */
public abstract class TestBase extends Assert {
    protected void neq(Object o1, Object o2) {
        no(_.eq(o1, o2));
    }

    protected void eq(Object o1, Object o2) {
        assertEquals(o1, o2);
    }

    protected void yes(Boolean expr, String msg, Object... args) {
        assertTrue(S.fmt(msg, args), expr);
    }

    protected void yes(Boolean expr) {
        assertTrue(expr);
    }

    protected void no(Boolean expr, String msg, Object... args) {
        assertFalse(S.fmt(msg, args), expr);
    }

    protected void no(Boolean expr) {
        assertFalse(expr);
    }

    protected void fail(String msg, Object... args) {
        assertFalse(S.fmt(msg, args), true);
    }

    protected static void run(Class<? extends TestBase> cls) {
        new JUnitCore().run(cls);
    }
}
