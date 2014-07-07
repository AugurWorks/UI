package com.augurworks.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.augurworks.alfred.FixedNeuron;
import com.augurworks.alfred.InputImpl;

/**
 * A testing suite for the FixedNeuron class.
 *
 * @author saf
 *
 */
public class FixedNeuronTest {
    private FixedNeuron f;
    private static int SIZE = 10;
    private Random random = new Random();
    private static double EPSILON = 0.000001;

    /**
     * Provides a random legal index based on SIZE
     */
    private int randomLegalIndex() {
        return random.nextInt(SIZE - 1);
    }

    /**
     * Sigmoid function for confirming getOutputTest
     *
     * @param input
     * @return sigmoid(input)
     */
    private double sigmoid(double input) {
        return 1.0 / (1.0 + Math.exp(-3.0 * input));
    }

    /**
     * Sets up the test fixture. (Called before every test case method.)
     */
    @Before
    public void setUp() {
        f = new FixedNeuron(SIZE);
    }

    /**
     * Tears down the test fixture. (Called after every test case method.)
     */
    @After
    public void tearDown() {
        f = null;
    }

    /**
     * Simple constructor test.
     */
    @Test
    public void testFixedNeuron() {
        assertNotNull(f);
    }

    /**
     * Test that name of FixedNeuron is successfully changed.
     */
    @Test
    public void testSetGetName() {
        assertEquals("Name should be empty after constructor.", f.getName(), "");

        String randomName = Long.toHexString(Double.doubleToLongBits(Math
                .random()));
        f.setName(randomName);
        assertEquals("Name should change.", f.getName(), randomName);

        String nullName = null;
        f.setName(nullName);
        assertNull(f.getName(), nullName);
    }

    /**
     * Tests the getting and setting of a weight.
     */
    @Test
    public void testGetSetWeight() {
        double weight = f.getWeight(randomLegalIndex());
        assertEquals("Initial weights should be 0", weight, 0, EPSILON);

        int idx = randomLegalIndex();
        double val = random.nextDouble();
        f.setWeight(idx, val);
        weight = f.getWeight(idx);
        assertEquals("Would should have changed", weight, val, EPSILON);

        // illegal locations
        try {
            idx = -1;
            f.setWeight(idx, 0);
            fail("Should not be able to set weight of negative index");
        } catch (Exception e) {
            // should go here
            assertTrue(true);
        }
        try {
            idx = SIZE;
            f.setWeight(idx, 0);
            fail("Should not be able to set weight of too large index");
        } catch (Exception e) {
            // should go here
            assertTrue(true);
        }
    }

    /**
     * Tests the filling of inputs. This is just a sanity check that there's no
     * exceptions thrown; in FixedNeurons, complaints don't occur when you try
     * to add too many inputs.
     */
    @Test
    public void testAddInput() {
        // adding Input objects
        for (int i = 0; i < SIZE + 1; i++) {
            f.addInput(new InputImpl(), random.nextDouble());
        }
        // adding FixedNeuron objects
        f = new FixedNeuron(SIZE);
        for (int i = 0; i < SIZE + 1; i++) {
            f.addInput(new FixedNeuron(SIZE), random.nextDouble());
        }
    }

    /**
     * Tests that the weight change function works.
     */
    @Test
    public void testChangeWeight() {
        // initial
        double w = f.getWeight(randomLegalIndex());
        assertEquals("Initial weight should be zero", w, 0, EPSILON);

        // simple change weight and confirm
        double newWeight = random.nextDouble();
        int idx = randomLegalIndex();
        f.changeWeight(idx, newWeight);
        assertEquals("Weight should have changed", f.getWeight(idx), newWeight,
                EPSILON);

        // now add inputs and try again
        double weightDelta = random.nextDouble();
        idx = randomLegalIndex();
        double initialWeight = random.nextDouble();
        for (int i = 0; i < SIZE; i++) {
            f.addInput(new FixedNeuron(SIZE), initialWeight);
        }
        f.changeWeight(idx, weightDelta);
        assertEquals("Weight should have changed", f.getWeight(idx),
                initialWeight + weightDelta, EPSILON);

        // now try illegal indices
        try {
            // too small
            f.changeWeight(-1, random.nextDouble());
            fail("Should not be able to change weight from -1");
        } catch (Exception e) {
            assertTrue(true);
        }
        try {
            // too big
            f.changeWeight(SIZE + 1, random.nextDouble());
            fail("Should not be able to change weight from index outside range");
        } catch (Exception e) {
            assertTrue(true);
        }
        try {
            // too small
            f.getWeight(-1);
            fail("Should not be able to get weight from -1");
        } catch (Exception e) {
            assertTrue(true);
        }
        try {
            // too big
            f.getWeight(SIZE + 1);
            fail("Should not be able to get weight from index outside range");
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    /**
     * Tests the getOutput function.
     */
    @Test
    public void testGetOutput() {
        f = new FixedNeuron(4);
        assertEquals("Initial lastOutput should be zero", f.getLastOutput(), 0,
                EPSILON);

        int code = 0;
        // for initial settings, 0 should be lastCode, lastOutput should be 0.
        assertEquals("Initial output is zero", f.getOutput(code), 0, EPSILON);

        // now try with actual inputs
        for (int i = 0; i < 4; i++) {
            InputImpl input = new InputImpl();
            input.setValue(i / 10.0);
            f.addInput(input, 1);
        }
        double output = f.getOutput(0);
        assertEquals("Last code should still be 0", output, 0, EPSILON);
        assertEquals("Lastoutput should be same as output", output,
                f.getLastOutput(), EPSILON);

        output = f.getOutput(1);
        double desired = sigmoid(0 * 1 + .1 * 1 + .2 * 1 + .3 * 1);
        assertEquals("Output of neuron was incorrect", output, desired, EPSILON);
        assertEquals("Lastoutput should be same as output", desired,
                f.getLastOutput(), EPSILON);

        // change the weights
        for (int i = 0; i < 4; i++) {
            f.changeWeight(i, 0.5);
        }
        output = f.getOutput(1);
        assertEquals("Should get same result because code is the same", output,
                desired, EPSILON);
        assertEquals("Lastoutput should be same as output", output,
                f.getLastOutput(), EPSILON);

        desired = sigmoid(0.0 * 1.5 + 0.1 * 1.5 + 0.2 * 1.5 + 0.3 * 1.5);
        assertEquals("Output of neuron was incorrect", f.getOutput(2), desired,
                EPSILON);
        assertEquals("Lastoutput should be same as output", desired,
                f.getLastOutput(), EPSILON);

        // now try with the getOutput(double[] ins) method
        double[] ins = { 0, 0.1, 0.2, 0.3 };
        output = f.getOutput(ins);
        desired = sigmoid(0.0 * 1.5 + 0.1 * 1.5 + 0.2 * 1.5 + 0.3 * 1.5);
        assertEquals("Output of neuron was incorrect", output, desired, EPSILON);
        assertEquals("Lastoutput should be same as output", desired,
                f.getLastOutput(), EPSILON);

        // change the weights
        for (int i = 0; i < 4; i++) {
            f.changeWeight(i, -0.5);
        }
        assertEquals("Lastoutput should be same as output", desired,
                f.getLastOutput(), EPSILON);
        desired = sigmoid(0 * 1 + .1 * 1 + .2 * 1 + .3 * 1);
        output = f.getOutput(ins);
        assertEquals("Output of neuron was incorrect", output, desired, EPSILON);
        assertEquals("Lastoutput should be same as output", desired,
                f.getLastOutput(), EPSILON);

    }
}
