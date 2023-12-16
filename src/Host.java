import java.util.Hashtable;

public class Host
{
    public final Hashtable<String, Connection> connections;

    public final int maxConnections = 200;

    public Host()
    {
        this.connections = new Hashtable<>();
    }

    public int connectionCount()
    {
        return connections.size();
    }
}
