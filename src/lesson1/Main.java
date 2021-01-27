package lesson1;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        /*
            Создайте три класса Человек, Кот, Робот, которые не наследуются от одного класса.
            Эти классы должны уметь бегать и прыгать (методы просто выводят информацию о действии в консоль).

            Создайте два класса: беговая дорожка и стена, при прохождении через которые,
            участники должны выполнять соответствующие действия (бежать или прыгать),
            результат выполнения печатаем в консоль (успешно пробежал, не смог пробежать и т.д.).
            У препятствий есть длина (для дорожки) или высота (для стены), а участников ограничения на бег и прыжки.

            Создайте два массива: с участниками и препятствиями, и заставьте всех участников пройти этот набор
            препятствий. Если участник не смог пройти одно из препятствий, то дальше по списку он препятствий не идет.
         */

        List<Movable> movableSubjects = new ArrayList<>();
        movableSubjects.add(new Human("Some person", 500, 1000));
        movableSubjects.add(new Robot("Good robot", 10000, 2000));
        movableSubjects.add(new Cat("Some cat", 500, 1500));
        movableSubjects.add(new Robot("Bad robot", 1000, 500));

        List<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(new Treadmill(300));
        obstacles.add(new Wall(500));
        obstacles.add(new Treadmill(750));
        obstacles.add(new Wall(1000));

        overcomeObstacles(movableSubjects, obstacles);

        /*
            2. Добавить класс Team, который будет содержать название команды,
            массив из четырех участников (в конструкторе можно сразу указывать всех участников ),
            метод для вывода информации о членах команды, прошедших дистанцию,
            метод вывода информации обо всех членах команды.

            3. Добавить класс Course (полоса препятствий), в котором будут находиться массив препятствий и метод,
            который будет просить команду пройти всю полосу;
         */

        System.out.println(); // empty line as divider

        Team team = new Team("The Team", 4, movableSubjects);
        Course course = new Course(obstacles);
        team.printInfo();

        course.passCourse(team);

        System.out.println(); // empty line as divider
        team.printResults();

        System.out.println(); // empty line as divider
        team.printSuccessfulResults();

        System.out.println(); // empty line as divider
        team.printResult(team.getTeam().get(0));
    }

    public static void overcomeObstacles(List<Movable> movableSubjects, List<Obstacle> obstacles) {
        for (Movable subject: movableSubjects) {
            for (Obstacle obstacle: obstacles) {
                if (obstacle.overcome(subject)) {
                    System.out.println(subject.getName() + " has successfully overcome " + obstacle.getName()
                            + " with distance " + obstacle.getDistance() + "m.");
                } else {
                    System.out.println(subject.getName() + " has failed to overcome " + obstacle.getName()
                            + " with distance " + obstacle.getDistance() + "m.");
                    break;
                }
            }
        }
    }
}
