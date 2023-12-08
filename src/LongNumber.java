import java.util.Objects;
import java.lang.Math;
import java.util.function.IntPredicate;

public final class LongNumber
{
    private static final IntPredicate pr = character -> character >= '0' && character <= '9';

    // Максимально возможный модуль числа (строка состоит только из девяток)
    public static final String MAX_ABS = "9".repeat(200); // 10^200 - 1

    private final int sign;

    private final String str;

    // Удалить незначащие нули из строки
    // Если строка целиком состоит из нулей, последний нуль не удаляется
    private static String StripLeadingZeros(String str)
    {
        if (str.isEmpty() || str.charAt(0) != '0') return str;
        int zeros = 0;
        while (zeros < (str.length() - 1) && str.charAt(zeros) == '0') ++zeros;
        return str.substring(zeros);
    }

    private static boolean isNumeric(final String str)
    {
        return str.codePoints().allMatch(pr);
    }

    LongNumber(long val)
    {
        this.sign = Long.signum(val);
        this.str = val == Long.MIN_VALUE ? "9223372036854775808" : Long.toString(Math.abs(val));
    }

    LongNumber(String str, int sign)
    {
        if (!isNumeric(str)) throw new IllegalArgumentException("String must be a numeric string");
        final String stripped = StripLeadingZeros(str);
        if (stripped.length() > MAX_ABS.length()) throw new ArithmeticException("Result number is too big");
        this.str = stripped.equals("0") ? "" : stripped;
        this.sign = this.str.isEmpty() ? 0 : sign > 0 ? 1 : -1;
    }

    public int Sign()
    {
        return this.sign;
    }

    // Сравнение двух чисел
    // Возврат:
    // 1 if this > other;
    // 0 if this == other;
    // -1 if this < other.
    public int Compare(LongNumber other)
    {
        return this.sign == other.sign ? (this.CompareAbs(other) * this.sign) : (this.sign > other.sign ? 1 : -1);
    }

    public int Compare(long other)
    {
        return this.Compare(new LongNumber(other));
    }

    // Сравнение модулей двух чисел
    // Возврат:
    // 1, если |this| > |other|;
    // 0, если модули чисел равны;
    // -1, если |this| < |other|.
    private int CompareAbs(LongNumber other)
    {
        if (this.str.length() > other.str.length()) return 1;
        if (this.str.length() < other.str.length()) return -1;
        return Integer.signum(this.str.compareTo(other.str));
    }

    // Противоположное число
    public LongNumber Negate()
    {
        return new LongNumber(this.str, -this.sign);
    }

    // Модуль числа
    public LongNumber Abs()
    {
        return new LongNumber(this.str, Math.abs(this.sign));
    }

    // Сложение двух чисел
    public LongNumber Add(LongNumber other)
    {
        if (this.sign == 0) return other;
        if (other.sign == 0) return this;
        if (this.sign == other.sign) return new LongNumber(UnsignedAdd(this.str, other.str), this.sign);
        LongNumber bigger = this.CompareAbs(other) >= 0 ? this : other;
        LongNumber smaller = bigger == other ? this : other;
        return new LongNumber(UnsignedSubtract(bigger.str, smaller.str), bigger.sign);
    }

    public LongNumber Add(long other)
    {
        return this.Add(new LongNumber(other));
    }

    // Беззнаковое сложение двух числовых строк
    private static String UnsignedAdd(String a, String b)
    {
        int indexA = a.length() - 1;
        int indexB = b.length() - 1;
        int carry = 0;
        StringBuilder sb = new StringBuilder(Math.max(a.length(), b.length()) + 1);
        while (true)
        {
            final int digit1 = indexA < 0 ? 0 : a.charAt(indexA) - '0';
            final int digit2 = indexB < 0 ? 0 : b.charAt(indexB) - '0';
            final int res = digit1 + digit2 + carry;
            if (res == 0 && Math.max(indexA, indexB) < 0) return sb.reverse().toString();
            carry = res / 10;
            sb.append(res % 10);
            if (sb.length() > MAX_ABS.length()) throw new ArithmeticException("Result number is too big");
            indexA--;
            indexB--;
        }
    }

    // Беззнаковое вычитание двух числовых строк, причём a >= b
    private static String UnsignedSubtract(String a, String b)
    {
        int indexA = a.length() - 1;
        int indexB = b.length() - 1;
        int carry = 0;
        int zeros = 0;
        StringBuilder sb = new StringBuilder(Math.max(a.length(), b.length()));
        while (true)
        {
            final int digit1 = indexA < 0 ? 0 : a.charAt(indexA) - '0';
            final int digit2 = indexB < 0 ? 0 : b.charAt(indexB) - '0';
            final int res = digit1 - digit2 + carry;
            final int toAppend = res < 0 ? res + 10 : res;
            carry = res < 0 ? -1 : 0;
            if (toAppend == 0)
            {
                if (Math.max(indexA, indexB) < 0 && res != -10) return sb.reverse().toString();
                zeros++;
            }
            else
            {
                sb.append("0".repeat(zeros));
                zeros = 0;
                sb.append(toAppend);
            }
            indexA--;
            indexB--;
        }
    }

    // Беззнаковое умножение двух числовых строк
    private static String UnsignedMultiply(String a, String b)
    {
        String result;
        final String longer = a.length() > b.length() ? a : b;
        final String shorter = a.length() <= b.length() ? a : b;
        if (shorter.isEmpty() || shorter.equals("0")) return "0";
        if (shorter.equals("1")) return longer;
        if (shorter.length() == 1)
        {
            final StringBuilder sb = new StringBuilder(longer.length() + 1);
            int carry = 0;
            int index = longer.length() - 1;
            final int digitB = shorter.charAt(0) - '0';
            while (true)
            {
                final int digitA = index < 0 ? 0 : longer.charAt(index) - '0';
                final int res = digitA * digitB + carry;
                if (res == 0 && index < 0) break;
                carry = res / 10;
                sb.append(res % 10);
                index--;
            }
            result = sb.reverse().toString();
        }
        else
        {
            // karatsuba algorithm
            // x = a * 10^n + b
            // y = c * 10^n + d
            // n - натуральное число
            // xy = [ac]*[10^(2n)] + [(a+b)(c+d)-ac-bd]*[10^n] + [bd]
            final int n = longer.length() >> 1;
            final int index = Math.max(0, shorter.length() - n);
            final String A = longer.substring(0, longer.length() - n);
            final String B = StripLeadingZeros(longer.substring(longer.length() - n));
            final String C = shorter.substring(0, index);
            final String D = StripLeadingZeros(shorter.substring(index));
            final String AC = UnsignedMultiply(A, C);
            final String BD = UnsignedMultiply(B, D);
            final String AD_PLUS_BC = UnsignedSubtract(UnsignedSubtract(UnsignedMultiply(UnsignedAdd(A, B), UnsignedAdd(C, D)), AC), BD);
            result = UnsignedAdd(UnsignedAdd(AC + "0".repeat(n << 1), AD_PLUS_BC + "0".repeat(n)), BD);
        }
        if (result.length() > MAX_ABS.length()) throw new ArithmeticException("Result number is too big");
        else return result;
    }

    public LongNumber Subtract(LongNumber other)
    {
        if (this.sign == 0) return other.Negate();
        if (other.sign == 0) return this;
        if (this.sign == other.sign)
        {
            String bigger = this.CompareAbs(other) >= 0 ? this.str : other.str;
            String smaller = Objects.equals(bigger, other.str) ? this.str : other.str;
            return new LongNumber(UnsignedSubtract(bigger, smaller), bigger.equals(this.str) ? this.sign : -other.sign);
        }
        return new LongNumber(UnsignedAdd(this.str, other.str), this.sign);
    }

    public LongNumber Subtract(long other)
    {
        return this.Subtract(new LongNumber(other));
    }

    public LongNumber Multiply(LongNumber other)
    {
        if (this.sign == 0 || other.sign == 0) return new LongNumber(0);
        return new LongNumber(UnsignedMultiply(this.str, other.str), this.sign * other.sign);
    }

    public LongNumber Multiply(long other)
    {
        if (this.sign == 0 || other == 0) return new LongNumber(0);
        final String str = other == Long.MIN_VALUE ? "9223372036854775808" : Long.toString(Math.abs(other));
        return new LongNumber(UnsignedMultiply(this.str, str), this.sign * Long.signum(other));
    }


    public static LongNumber Add(LongNumber a, LongNumber b)
    {
        return a.Add(b);
    }

    public static LongNumber Subtract(LongNumber a, LongNumber b)
    {
        return a.Subtract(b);
    }

    public static LongNumber Multiply(LongNumber a, LongNumber b)
    {
        return a.Multiply(b);
    }

    @Override
    public String toString()
    {
        return this.sign == 0 ? "0" : this.sign > 0 ? this.str : "-" + this.str;
    }
}
