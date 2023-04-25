package com.sr.datagen.utilities;

import com.sr.datagen.models.State;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StatePopulator {

    private  HashMap<String, String> stateMap;
    private HashMap<String, String> stateCapital;
    private HashMap<String, String> stateNicknames;


    public StatePopulator(){
        String[] states = {"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut",
                "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas",
                "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi",
                "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York",
                "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island",
                "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington",
                "West Virginia", "Wisconsin", "Wyoming"};

        String[] stateAbbr = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA",
                "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY",
                "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI",
                "WY"};

        String[] capitals = {"Montgomery", "Juneau", "Phoenix", "Little Rock", "Sacramento", "Denver", "Hartford",
                "Dover", "Tallahassee", "Atlanta", "Honolulu", "Boise", "Springfield", "Indianapolis", "Des Moines",
                "Topeka", "Frankfort", "Baton Rouge", "Augusta", "Annapolis", "Boston", "Lansing", "Saint Paul",
                "Jackson", "Jefferson City", "Helena", "Lincoln", "Carson City", "Concord", "Trenton", "Santa Fe",
                "Albany", "Raleigh", "Bismarck", "Columbus", "Oklahoma City", "Salem", "Harrisburg", "Providence",
                "Columbia", "Pierre", "Nashville", "Austin", "Salt Lake City", "Montpelier", "Richmond", "Olympia",
                "Charleston", "Madison", "Cheyenne"};

        String[] nicknames = {"Yellowhammer State", "The Last Frontier", "Grand Canyon State", "Natural State",
                "Golden State", "Centennial State", "Constitution State", "First State", "Sunshine State", "Peach State",
                "Aloha State", "Gem State", "Prairie State", "Hoosier State", "Hawkeye State", "Sunflower State",
                "Bluegrass State", "Pelican State", "Pine Tree State", "Old Line State", "Bay State", "Wolverine State",
                "North Star State", "Magnolia State", "Show Me State", "Treasure State", "Cornhusker State",
                "Silver State", "Granite State", "Garden State", "Land of Enchantment", "Empire State", "Tar Heel State",
                "Peace Garden State", "Buckeye State", "Sooner State", "Beaver State", "Keystone State", "Ocean State",
                "Palmetto State", "Mount Rushmore State", "Volunteer State", "Lone Star State", "Beehive State",
                "Green Mountain State", "Old Dominion State", "Evergreen State", "Mountain State", "Badger State",
                "Equality State"};
        stateMap = new HashMap<>();
        stateCapital = new HashMap<>();
        stateNicknames = new HashMap<>();

        for(int i = 0; i < states.length; i++){
            stateMap.put(stateAbbr[i], states[i]);
            stateCapital.put(stateAbbr[i], capitals[i]);
            stateNicknames.put(stateAbbr[i], nicknames[i]);
        }

    }

    public State getState(String stateAbbr) {
        State state = new State();
        if(stateMap.containsKey(stateAbbr)){
            state.setStateAbbr(stateAbbr);
            state.setState(stateMap.get(stateAbbr));
            state.setCapital(stateCapital.get(stateAbbr));
            state.setNickname(stateNicknames.get(stateAbbr));
            return state;
        } else {
            return null;
        }

    }

    public boolean isValidState(String stateAbbr){
        return stateMap.containsKey(stateAbbr);
    }

}
