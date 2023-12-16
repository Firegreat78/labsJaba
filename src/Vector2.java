public class Vector2
{
    public float x, y;

    public Vector2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public static float distanceSquared(Vector2 a, Vector2 b)
    {
        final float dx = b.x - a.x;
        final float dy = b.y - a.y;
        return dx*dx + dy*dy;
    }

    @Override
    public String toString()
    {
        return "(" + this.x + ", " + this.y + ")";
    }
}
