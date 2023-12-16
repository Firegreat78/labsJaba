import java.time.Instant;
import java.util.*;

public class Main
{
    public static void main(String[] args) throws CloneNotSupportedException {
        // перегрузка метода баз. класса:
        Player player1 = new Player("Firegreat", 100, new Vector2(1, 54));
        Pistol p1 = new Pistol(new Vector2(1, 53));
        Sword s1 = new Sword(new Vector2(1, 53));
        player1.pickupWeapon(p1);
        player1.pickupWeapon(s1);

        // клонирование:
        cloneTest();

        // toString:
        LongNumber num1 = new LongNumber(-6858423424434331243L);
        LongNumber num2 = new LongNumber(32213);
        System.out.println(num1 + "|" + num2);
    }

    public static void cloneTest() throws CloneNotSupportedException
    {
        Player player = new Player("Fire", 100, new Vector2(0, 1));
        Pistol pistol = new Pistol(new Vector2(0, 0));
        Sword sword = new Sword(new Vector2(1, 1));
        Pistol pistolClone = pistol.clone();
        Sword swordClone = sword.shallowCopy();
        player.pickupWeapon(pistolClone);
        player.pickupWeapon(swordClone);
    }
}