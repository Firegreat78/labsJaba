import java.util.Vector;
import java.util.Random;
import java.util.function.Consumer;

public class Main
{
    static final Random rnd = new Random();
    static final Host host = new Host();
    public static void main(String[] args) throws InterruptedException
    {
        // Поиск
        Vector<Connection> vector = new Vector<>(6);
        addConn(vector);
        var filtered = filterByPort(vector, 8006);

        // Сортировка
        Vector<LongNumber> longNumbers = new Vector<>(5);
        for (int i = 0; i < 20; ++i) longNumbers.add(randLongNum());
        System.out.println("Unsorted vector:");
        for (var num : longNumbers) System.out.println(num);
        longNumbers.sort(LongNumber::compare);
        System.out.println("Sorted vector:");
        for (var num : longNumbers) System.out.println(num);
    }

    private static void addConn(Vector<Connection> v) throws InterruptedException
    {
        v.add(new Player("Firegreat", 150, new Vector2(1, 1)));
        Thread.sleep(50); // 0.05 seconds
        v.add(new Player("WOLF", 200, new Vector2(0, 0)));
        Thread.sleep(50); // 0.05 seconds
        v.add(new Client("Dan"));
        Thread.sleep(50); // 0.05 seconds
        v.add(new Connection());
    }

    private static Vector<Connection> filterByPort(Vector<Connection> v, final int port)
    {
        Vector<Connection> filtered = new Vector<>(v.capacity());
        v.forEach(new Consumer<Connection>()
        {
            @Override
            public void accept(Connection connection)
            {
                if (connection.port == port) filtered.add(connection);
            }
        });
        return filtered;
    }

    private static LongNumber randLongNum()
    {
        final int len = rnd.nextInt(1, 51);
        final StringBuilder sb = new StringBuilder(len);
        sb.append(rnd.nextInt(1, 10));
        for (int i = 0; i < len; ++i) sb.append(rnd.nextInt(0, 10));
        return new LongNumber(sb.toString(), rnd.nextBoolean() ? 1 : -1);
    }
}