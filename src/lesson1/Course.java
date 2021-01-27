package lesson1;

import java.util.ArrayList;
import java.util.List;

public class Course {
    List<Obstacle> obstacles;

    public Course(List<Obstacle> obstacles) {
        this.obstacles = new ArrayList<>();
        this.obstacles.addAll(obstacles);
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }

     public void passCourse(Team team) {
        List<Movable> members = team.getTeam();

         for (Movable member: members) {
             boolean result = false;
             for (Obstacle obstacle: obstacles) {
                 result = obstacle.overcome(member);
                 if (!result)
                     break;
             }
             team.updateResult(member, result);
         }
     }
}
