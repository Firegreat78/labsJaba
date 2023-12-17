import java.time.Instant;

public class Connection
{
    private boolean isConnectedToInternet()
    {
        return true;
    }

    private String currentIpAddress()
    {
        if (!isConnectedToInternet()) throw new IllegalStateException("Can't get IP address: no Internet connection");
        return "127.0.0.1";
    }

    private int getFreePort()
    {
        return 8000;
    }

    public Instant connection_time;

    protected String ipAddress;

    protected int port;

    public Connection()
    {
        this.ipAddress = currentIpAddress();
        this.port = getFreePort();
        this.connection_time = Instant.now();
    }

    public void connect(Host host)
    {
        if (host.connections.containsKey(this.ipAddress))
            throw new GameException("Cannot connect: this instance is already connected to the host.");
        host.connections.put(this.ipAddress, this);
    }

    public void disconnect(Host host)
    {
        if (!host.connections.containsKey(this.ipAddress))
            throw new GameException("Cannot disconnect: this instance is not connected to the host.");
        host.connections.remove(this.ipAddress);
    }
}
