// typeinfo/PetCount3.java
// (c)2017 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://OnJava8.com for more book information.
// Using isInstance()
import java.util.*;
import java.util.stream.*;
import onjava.*;
import typeinfo.pets.*;

public class PetCount3 {
  static class Counter extends
  LinkedHashMap<Class<? extends Pet>, Integer> {
    Counter() {
      super(LiteralPetCreator.ALL_TYPES.stream()
        .map(lpc -> Pair.make(lpc, 0))
        .collect(
          Collectors.toMap(Pair::key, Pair::value)));
    }
    public void count(Pet pet) {
      // Class.isInstance() eliminates instanceofs:
      entrySet().stream()
        .filter(pair -> pair.getKey().isInstance(pet))
        .forEach(pair ->
          put(pair.getKey(), pair.getValue() + 1));
    }
    @Override
    public String toString() {
      String result = entrySet().stream()
        .map(pair -> String.format("%s=%s",
          pair.getKey().getSimpleName(),
          pair.getValue()))
        .collect(Collectors.joining(", "));
      return "{" + result + "}";
    }
  }
  public static void main(String[] args) {
    Counter petCount = new Counter();
    Pets.stream()
      .limit(20)
      .peek(petCount::count)
      .forEach(p -> System.out.print(
        p.getClass().getSimpleName() + " "));
    System.out.println("\n" + petCount);
  }
}
/* Output:
Rat Manx Cymric Mutt Pug Cymric Pug Manx Cymric Rat
EgyptianMau Hamster EgyptianMau Mutt Mutt Cymric Mouse Pug
Mouse Cymric
{Hamster=1, Manx=7, EgyptianMau=2, Mutt=3, Rodent=5,
Mouse=2, Cymric=5, Pug=3, Dog=6, Rat=2, Pet=20, Cat=9}
*/
