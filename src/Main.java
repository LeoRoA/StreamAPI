import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Main {

    public static Supplier<Stream<Integer>> stream;
    public static void main(String[] args) {
        System.out.println("Hello world!");
        stream = ()-> Stream.generate(()->ThreadLocalRandom.current().nextInt(11,111)).limit(10);

//        Supplier<Stream<Integer>> stream = Stream.generate(()->ThreadLocalRandom.current().nextInt(11,111)).limit(10);
        findMinMax(stream.get(),
                Comparator.naturalOrder(),
                (min, max) -> System.out.println("min= " + min + "\nmax=" + max));
        System.out.println("__________");
        findCountNumbers(stream.get());

    }

    private static <T> void findMinMax(Stream<? extends T> stream,
                                       Comparator<? super T> order,
                                       BiConsumer<? super T, ? super T> minMaxConsumer){

        List<T> items = (List<T>) stream
                .sorted(order)
                .toList();
        items.forEach(System.out::println);

        if (!items.isEmpty()) {
            minMaxConsumer.accept(items.get(0), items.get(items.size() - 1));
        } else {
            minMaxConsumer.accept(null, null);
        }
    }

    private static <T> void findCountNumbers(Stream<? extends T> stream){
        AtomicInteger oddNumbersCount= new AtomicInteger(0);
        AtomicInteger evenNumbersCount= new AtomicInteger(0);
        stream
                .forEach((i)->{
                    if (i instanceof Integer) {

                        Integer number = (Integer) i;
                        System.out.println(number);
                        if (number % 2 == 0) {
                            evenNumbersCount.getAndIncrement();
                        } else {
                            oddNumbersCount.getAndIncrement();
                        }
                    }
                });
        System.out.println("Кол-во четных чисел - " + evenNumbersCount);
        System.out.println("Кол-во нечетных чисел - " + oddNumbersCount);
    }



}
