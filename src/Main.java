import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main
{
    public static void main(String[] args)
    {

        // Слишком длинное число
        try
        {
            LongNumber a = new LongNumber("1".repeat(230), 1);
        }

        catch (IllegalArgumentException e) // перехват попытки создания числа из нечисловой строки
        {
            System.out.println("Non-numeric string exception: ");
            System.out.println(e);
        }

        catch (ArithmeticException e) // перехват попытки создания слишком длинного числа
        {
            System.out.println("Too long number exception: ");
            System.out.println(e);
        }

        // Попытка создания числа, с использованием нечисловой строки
        try
        {
            LongNumber a = new LongNumber("-" + "1".repeat(230), 1);
        }

        catch (IllegalArgumentException e) // перехват попытки создания числа из нечисловой строки
        {
            System.out.println("Non-numeric string exception: ");
            System.out.println(e);
        }

        catch (ArithmeticException e) // перехват попытки создания слишком длинного числа
        {
            System.out.println("Too long number exception: ");
            System.out.println(e);
        }

        LongNumber[][] arr = new LongNumber[5][4];
        try
        {
            arr[3][100] = new LongNumber(1234); // индекс за пределами
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        arr[3] = new LongNumber[443];
        arr[3][100] = new LongNumber(12); // индекс не за пределами
        arr[0][0] = arr[3][100].Negate(); // -12, OK
        System.out.println("arr[0][0] = " + arr[0][0]);
        System.out.println("arr[3][100] = " + arr[3][100]);
    }
}