package lesson1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Team {
    private String name;
    private List<Movable> team;
    private HashMap<Movable, Boolean> results;
    private int size;
    private int maxSize;

    public Team (String name, int size, List<Movable> team) {
        this.name = name;
        this.maxSize = size;
        this.team = new ArrayList<>();
        this.results = new HashMap<>();
        addMembers(team);
    }

    public boolean addMember(Movable member) {
        if (size + 1 <= maxSize) {
            team.add(member);
            size++;
            return true;
        } else {
            return false;
        }
    }

    public boolean addMembers(List<Movable> team) {
        if (size + team.size() <= maxSize) {
            this.team.addAll(team);
            size += team.size();
            return true;
        } else {
            for (Movable member: team) {
                boolean isSuccessfullyAdded = addMember(member);
                if (!isSuccessfullyAdded)
                    break;
            }
            return false;
        }
    }

    public List<Movable> getTeam() {
        return team;
    }

    public void printInfo() {
        team.forEach(m -> System.out.println(m.getInfo()));
    }

    public void updateResult(Movable member, boolean result) {
        if (!team.contains(member))
            throw new RuntimeException(String.format("Member %s does not belong to the team %s", member.getName(), name));

        results.put(member, result);
    }

    public void printResults() {
        for (HashMap.Entry<Movable, Boolean> result: results.entrySet()) {
            String message = (result.getValue()) ? " successfully passed the course!" : " failed to pass the course!";
            System.out.println(result.getKey().getName() + message);
        }
    }

    public void printResult(Movable member) {
        if (!team.contains(member))
            throw new RuntimeException(String.format("Member %s does not belong to the team %s", member.getName(), name));

        String message = (results.get(member)) ? " successfully passed the course!" : " failed to pass the course!";
        System.out.println(member.getName() + message);
    }

    public void printSuccessfulResults() {
        results.forEach((k,v) -> { if (v) {
            System.out.println(k.getName() + " successfully passed the course!");
        }});
    }
}
