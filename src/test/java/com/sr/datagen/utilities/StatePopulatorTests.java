package com.sr.datagen.utilities;

import com.sr.datagen.models.State;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
public class StatePopulatorTests {


    @Test
    public void testStatePopulatorForAccurateData() {
        StatePopulator statePopulator = new StatePopulator();
        State state = statePopulator.getState("CA");
        Assertions.assertEquals("California", state.getState());
        Assertions.assertEquals("CA", state.getStateAbbr());
        Assertions.assertEquals("Sacramento", state.getCapital());
        Assertions.assertEquals("Golden State", state.getNickname());

        state = statePopulator.getState("TX");
        Assertions.assertEquals("Texas", state.getState());
        Assertions.assertEquals("TX", state.getStateAbbr());
        Assertions.assertEquals("Austin", state.getCapital());
        Assertions.assertEquals("Lone Star State", state.getNickname());

        state = statePopulator.getState("RandomData");
        Assertions.assertEquals(null, state);

    }
}
