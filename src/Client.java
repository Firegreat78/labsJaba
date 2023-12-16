public class Client extends Connection
{
    protected String username;

    public Client(String username)
    {
        super();
        this.username = username;
    }

    public String getUsername()
    {
        return this.username;
    }
}
